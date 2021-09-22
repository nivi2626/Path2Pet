package huji.post_pc.path2pet

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*


class Fragment_h_End : Fragment() {
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_h_end, container, false)
        val lostPetActivityInstance: LostPetProcess = activity as LostPetProcess

        // get data from SP
        val latitude = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LATITUDE, "")
        val longitude = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LONGITUDE, "")
        val type = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, "")
        val sex = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SEX, "")
        val breed = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, "")
        val size = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, "")
        val color = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLOR, "")
        val collar = lostPetActivityInstance.sp.getBoolean(AppPath2Pet.SP_COLLAR, false)
        val comments = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COMMENTS, "")
        val name = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_NAME, "")
        val email = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_EMAIL, "")
        val phone = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_PHONE, "")

        // get last_reports from lostPetSP
        var lostPetIDs = lostPetActivityInstance.spLostPets.getString(AppPath2Pet.SP_LOST_ID, "")


        // parse photos
        val photos = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_PHOTOS, null)
        val uriImages: List<Uri> = lostPetActivityInstance.string2UriList(photos)

        // todo - parse location from SP


        //create a new Pet object
        val id = UUID.randomUUID().toString()

        var pet = Pet(
            id,
            "Lost",
            latitude,
            longitude,
            type,
            sex,
            breed,
            size,
            color,
            collar,
            comments,
            name,
            email,
            phone,
            Date(),
            uriImages
        )
        AppPath2Pet.getPetsDB().addPet(pet)
        with(lostPetActivityInstance.spLostPets.edit())
        {
            if (lostPetIDs != "")
            {
                lostPetIDs = lostPetIDs + AppPath2Pet.SP_DELIMITER + id
            }
            else
            {
                lostPetIDs = id
            }
            putString(AppPath2Pet.SP_LOST_ID, lostPetIDs)
            apply()
        }
        lostPetActivityInstance.sp.edit().clear().apply()
        return view

    }


}