package huji.post_pc.path2pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Fragment_h_End : Fragment() {
    override fun onCreateView(
        // Inflate the layout for this fragment
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_h_end, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // get data from SP
        val photo = lostPetActivityInstance!!.sp.getString(AppPath2Pet.SP_PHOTO, "")
        val location = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LOCATION, "")
        val type = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, "")
        val sex = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SEX, "")
        val breed = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, "")
        val size = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, "")
        val color = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLOR, "")
        val collar = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLLAR, "")
        val Comments = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COMMENTS, "")
        val details = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_DETAILS, "")

        // todo - create Pet object
        return view
    }




}