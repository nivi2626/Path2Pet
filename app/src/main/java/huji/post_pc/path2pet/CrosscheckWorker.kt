package huji.post_pc.path2pet

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson

const val BASIC_FACTOR = 60
const val COLLAR_FACTOR = 1
const val SEX_FACTOR = 2
const val BREED_FACTOR = 3
const val SIZE_FACTOR = 4
const val COLORS_FACTOR = 5
const val LOCATION_FACTOR = 6
const val CLOSE_DISTANCE = 1000
const val MEDIUM_DISTANCE = 5000
const val FAR_DISTANCE = 20000

// set maxEstimator
val maxEstimator =
    BASIC_FACTOR * BREED_FACTOR + BASIC_FACTOR * COLLAR_FACTOR + BASIC_FACTOR * COLORS_FACTOR +
            BASIC_FACTOR * SIZE_FACTOR + BASIC_FACTOR * LOCATION_FACTOR + BASIC_FACTOR * SEX_FACTOR


class CrosscheckWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    companion object {
        var lostPetNum = 0
    }

    // key is id, double is match score
//    val petsMap = mutableMapOf<String, Double>()

    override fun doWork(): Result {
        val currentNum = lostPetNum
        lostPetNum += 1
        val allPets = AppPath2Pet.getPetsDB().allPets
        // set default return value on fail
        val failurePet = createJson("", "", 0.0)

        val userPetId =
            // on failure return empty map
            inputData.getString(currentNum.toString()) ?: return Result.failure(
                workDataOf(AppPath2Pet.WM_ON_FAILURE to failurePet)
            )

        // get user pet from id
        val userPet = findPetByID(userPetId, allPets)
            ?: return Result.failure(
                workDataOf(AppPath2Pet.WM_ON_FAILURE to failurePet)
            )

        // keep the best match
        var (bestMatchedPetID, matchBestScore) = Pair("", 0.0)

        // iterate over all pets and see if there is a match
        for (pet in allPets) {
            // if pet from db is also lost it's not helpful
            if (pet.getStatus() == AppPath2Pet.SP_LOST) {
                continue
            }

            // if types differ not helpful
            if (pet.getPetType() != userPet.getPetType()) {
                continue
            }
            val matchValue = calcMatchPercentage(userPet, pet)
            if (matchValue-0.05 > AppPath2Pet.MIN_MATCH_VAL && matchValue > matchBestScore) {
                bestMatchedPetID = pet.getId()
                matchBestScore = matchValue - 0.05
            }

        }
        // return the matching pets dictionary
        return Result.success(
            workDataOf(AppPath2Pet.WM_ON_SUCCESS to createJson(userPet.getId(),
                bestMatchedPetID, matchBestScore)
            )
        )
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
        val lostPetColorList = lostPet.getColors()

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

        // calc distance match
        val result = FloatArray(1)
        android.location.Location.distanceBetween(  // calculates distance between two dots (in meters)
        lostPet.getLatitude().toDouble(), lostPet.getLongitude().toDouble(),
            candidatePet.getLatitude().toDouble(), candidatePet.getLongitude().toDouble(),
            result
        )

        // if distance is 0 < 1,000 meters - all 6 points
        // if distance is 1.000 < 5,000 meters - 4 points
        // if distance is 5,000 < 20,000 meters - 2 points
        if (result[0] < CLOSE_DISTANCE) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * LOCATION_FACTOR)
        }
        else if (result[0] < MEDIUM_DISTANCE) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * LOCATION_FACTOR*2/3)
        }
        else if (result[0] < FAR_DISTANCE) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * LOCATION_FACTOR*1/3)
        }

        // calc collar match
        if (lostPet.getHasCollar() == candidatePet.getHasCollar()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * COLLAR_FACTOR)
        }

        // calc breed match
        if (lostPet.getBreed() == candidatePet.getBreed()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * BREED_FACTOR)
        }

        // calc sex match
        if (candidatePet.getSex() != "" && lostPetHasSex && candidatePet.getSex() == lostPet.getSex()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * SEX_FACTOR)
        }

        // calc size match
        if (candidatePet.getSize() != "" && lostPetHasSize && candidatePet.getSize() == lostPet.getSize()) {
            accumulatedMatchScore += (missingAttrReminder + BASIC_FACTOR * SIZE_FACTOR)
        }

        // calc colors match
        if (lostPetHasColor) {
            val numOfColors = lostPetColorList.size
            lostPetColorList.retainAll(candidatePet.getColors())
            accumulatedMatchScore += ((missingAttrReminder + BASIC_FACTOR * COLORS_FACTOR) *
                    (lostPetColorList.size / numOfColors))
        }

        return accumulatedMatchScore / maxEstimator
    }

    private fun createJson(myPetID : String, otherPetID: String, score: Double): String {
        val obj = MatchedPet(myPetID, otherPetID, score)
        val gson = Gson()
        return gson.toJson(obj)
    }

    private fun findPetByID(id: String, pets: List<Pet>): Pet? {
        for (pet in pets) {
            if (pet.getId() == id) {
                return pet
            }
        }
        return null
    }
}
