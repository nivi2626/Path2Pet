package huji.post_pc.path2pet.FoundProcess

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.Feed
import huji.post_pc.path2pet.Pet
import huji.post_pc.path2pet.R
import java.util.*


class Fragment_h_End : Fragment() {
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.found_fragment_h_end, container, false)
        val foundPetActivityInstance: FoundPetProcess = activity as FoundPetProcess

        // find views
        val feedButton : Button = view.findViewById(R.id.feed)

        // get data from SP
        val latitude = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_LATITUDE, "")
        val longitude = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_LONGITUDE, "")
        val type = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, "")
        val sex = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_SEX, "")
        val breed = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, "")
        val size = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, "")
        val color = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_COLORS, "")
        val collar = foundPetActivityInstance.sp.getBoolean(AppPath2Pet.SP_COLLAR, false)
        val comments = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_COMMENTS, "")
        val name = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_NAME, "")
        val email = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_EMAIL, "")
        val phone = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_PHONE, "")

        // parse photos
        val photos = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_PHOTOS, null)
        val uriImages: List<Uri> = foundPetActivityInstance.string2UriList(photos)

        val colors = color!!.split(AppPath2Pet.SP_DELIMITER)

        //create a new Pet object
        val id = UUID.randomUUID().toString()

        val pet = Pet(id, AppPath2Pet.FOUND, latitude, longitude, type, sex,
            breed, size, colors, collar, comments, name, email, phone, Date(), uriImages,
            uriImages.size)

        AppPath2Pet.getPetsDB().addPet(pet)
        foundPetActivityInstance.sp.edit().clear().apply()

        // move to feed
        feedButton.setOnClickListener {
            val intentFeed = Intent(view.context, Feed::class.java)
            startActivity(intentFeed)
        }
        return view
    }


}