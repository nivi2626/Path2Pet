package huji.post_pc.path2pet

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson

val BASIC_FACTOR = 60
val COLLAR_FACTOR = 1
val SEX_FACTOR = 2
val BREED_FACTOR = 3
val SIZE_FACTOR = 4
val COLORS_FACTOR = 5
val LOCATION_FACTOR = 6
val MAX_DISTANCE = 20000
val MIN_MATCH_VAL = 0.70

// set maxEstimator
val maxEstimator =
    BASIC_FACTOR * BREED_FACTOR + BASIC_FACTOR * COLLAR_FACTOR + BASIC_FACTOR * COLORS_FACTOR +
            BASIC_FACTOR * SIZE_FACTOR + BASIC_FACTOR * LOCATION_FACTOR + BASIC_FACTOR * SEX_FACTOR


class CrosscheckWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    // key is id, double is match score
//    val petsMap = mutableMapOf<String, Double>()

    override fun doWork(): Result {

        val allPets = AppPath2Pet.getPetsDB().allPets

        val userPetsString =
            // on failure return empty map
            inputData.getString(AppPath2Pet.WM_USER_LOST_PETS) ?: return Result.failure(
                workDataOf(
                    AppPath2Pet.WM_ON_FAILURE to mutableMapOf<String, Double>()
                )
            )
        if (userPetsString == "") {
            return Result.success(workDataOf(
                AppPath2Pet.WM_ON_SUCCESS to mutableMapOf<String, Double>()))
        }

        val userPets: MutableList<Pet> = ArrayList()
        val userLostPetsIDs = userPetsString.split(AppPath2Pet.SP_DELIMITER)

        // set all userLostPets
        for (pet in allPets) {
            if (userLostPetsIDs.contains(pet.getId())) {
                userPets.add(pet)
            }
        }

        // iterate over all pets and see if there is a match
        for (pet in allPets) {
            // if pet from db is also lost it's not helpful
            if (pet.getStatus() == AppPath2Pet.SP_LOST) {
                continue
            }
            for (lostPet in userPets) {
                // if types differ not helpful
                if (pet.getPetType() != lostPet.getPetType()) {
                    continue
                }
                val matchValue = calcMatchPercentage(lostPet, pet)
                if (matchValue > MIN_MATCH_VAL) {
                    val obj: MatchedPet = MatchedPet(pet.getId(), matchValue)
                    val gson = Gson()
                    val objAsJson: String = gson.toJson(obj)
                    return Result.success(workDataOf(AppPath2Pet.WM_ON_SUCCESS to objAsJson))
                    // TODO - continue here

//                    petsMap[pet.getId()] = matchValue
                }
            }
        }
        // return the matching pets dictionary
        return Result.failure()
    }


    private fun calcMatchPercentage(lostPet: Pet, candidatePet: Pet): Double {
        var accumulatedMatchScore = 0.0

        var missingAttributesScore = 0.0

        var lostPetHasColor = false
        var lostPetHasSize = false
        var lostPetHasSex = false

        // count how many attributes given by user: location,
        // collar and breed are automatically counter for
        var countAttributes = 3.0

        var lostPetColorList = lostPet.getColors()

        if (lostPetColorList.size > 0) {
            countAttributes++
            lostPetHasColor = true
        } else {
            missingAttributesScore += BASIC_FACTOR * COLORS_FACTOR
        }
        if (lostPet.getSex() != "") {
            countAttributes++
            lostPetHasSex = true
        } else {
            missingAttributesScore += BASIC_FACTOR * SEX_FACTOR
        }
        if (lostPet.getSize() != "") {
            countAttributes++
            lostPetHasSize = true
        } else {
            missingAttributesScore += BASIC_FACTOR * SIZE_FACTOR
        }

        val missingAttrReminder = missingAttributesScore / countAttributes

        val result = FloatArray(1)
        // calculates distance between two dots in meters
        android.location.Location.distanceBetween(
            lostPet.getLatitude().toDouble(), lostPet.getLongitude().toDouble(),
            candidatePet.getLatitude().toDouble(), candidatePet.getLongitude().toDouble(),
            result
        )
        if (result[0] < MAX_DISTANCE) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * LOCATION_FACTOR)
        }

        if (lostPet.getHasCollar() == candidatePet.getHasCollar()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * COLLAR_FACTOR)
        }

        if (lostPet.getBreed() == candidatePet.getBreed()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * BREED_FACTOR)
        }

        if (candidatePet.getSex() != "" && lostPetHasSex && candidatePet.getSex() == lostPet.getSex()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * SEX_FACTOR)
        }

        if (candidatePet.getSize() != "" && lostPetHasSize && candidatePet.getSize() == lostPet.getSize()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * SIZE_FACTOR)
        }

        if (lostPetHasColor) {
            val numOfColors = lostPetColorList.size
            lostPetColorList.retainAll(candidatePet.getColors())
            accumulatedMatchScore += ((missingAttrReminder + BASIC_FACTOR * COLORS_FACTOR) *
                    (lostPetColorList.size / numOfColors))
        }

        return accumulatedMatchScore / maxEstimator
    }

    data class MatchedPet(val otherPetID: String, val score:Double)
    {
        val otherId = otherPetID
        val matchScore = score
    }
}
