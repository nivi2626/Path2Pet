package huji.post_pc.path2pet

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation


class Fragment_c_TypeGender : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_c_type_sex, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val dogButton: Button = view.findViewById(R.id.dog)
        val catButton: Button = view.findViewById(R.id.cat)
        val femaleButton: Button = view.findViewById(R.id.female)
        val maleButton: Button = view.findViewById(R.id.male)
        var petType: String? = null
        var petGender: String? = null

        // set UI
        // type colors
        dogButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        catButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))

        // gender colors
        femaleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        maleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))

        // get data from sp
        petType = lostPetActivityInstance!!.sp.getString(AppPath2Pet.SP_TYPE, null)
        petGender = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SEX, null)

        // set data by sp
        if (petType != null)
        {
            if (petType == AppPath2Pet.TYPE_DOG)
            {
                dogButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            }
            else if (petType == AppPath2Pet.TYPE_CAT)
            {
                catButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            }
        }

        if (petGender != null)
        {
            if (petGender == AppPath2Pet.SEX_MALE)
            {
                maleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            }
            else if (petGender == AppPath2Pet.SEX_FEMALE)
            {
                femaleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            }
        }

        // type listeners
        dogButton.setOnClickListener(){
            catButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            dogButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            petType = AppPath2Pet.TYPE_DOG
        }

        catButton.setOnClickListener(){
            dogButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            catButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            petType = AppPath2Pet.TYPE_CAT
        }

        // gender listeners
        femaleButton.setOnClickListener(){
            maleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            femaleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            petGender = AppPath2Pet.SEX_FEMALE
        }

        maleButton.setOnClickListener(){
            femaleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            maleButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            petGender = AppPath2Pet.SEX_MALE
        }

        // next listener
        nextButton.setOnClickListener {
            with(lostPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_TYPE, petType)
                putString(AppPath2Pet.SP_SEX, petGender)
                apply()
            }
            lostPetActivityInstance.progressBar.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()

        }
        return view

    }

    private fun nextButtonOnClick(view:View) {
        Navigation.findNavController(view).navigate(R.id.fragmentBreedSize)
    }

}