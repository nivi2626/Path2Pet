package huji.post_pc.path2pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*


class Fragment_h_End : Fragment() {
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_h_end, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // get data from SP
        val photos = lostPetActivityInstance!!.sp.getString(AppPath2Pet.SP_PHOTOS, "")
        val location = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LOCATION, "")
        val type = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, "")
        val sex = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SEX, "")
        val breed = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, "")
        val size = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, "")
        val color = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLOR, "")
        val collar = lostPetActivityInstance.sp.getBoolean(AppPath2Pet.SP_COLLAR, false)
        val comments = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COMMENTS, "")
        val details = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_DETAILS, "")

        val id = UUID.randomUUID().toString()
        // todo - parse location and photos from SP

        var pet = Pet(id, "Lost", type, breed, size, color, null, Date(), comments,null)
        AppPath2Pet.getPetsDB().addPet(pet)
        lostPetActivityInstance.sp.edit().clear().apply()
        return view

    }




}