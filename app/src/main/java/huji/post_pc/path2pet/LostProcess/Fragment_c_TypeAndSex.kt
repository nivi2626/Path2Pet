package huji.post_pc.path2pet.LostProcess

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R


class Fragment_c_TypeAndSex : Fragment() {
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
        val mandatoryComment: TextView = view.findViewById(R.id.mandatory_comment)
        var petType: String? = null
        var petGender: String? = null
        var clickedType: Boolean = false

        // set UI
        mandatoryComment.visibility = View.INVISIBLE

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
            mandatoryComment.visibility = View.INVISIBLE
            clickedType = true
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
            mandatoryComment.visibility = View.INVISIBLE
            clickedType = true
        }

        catButton.setOnClickListener(){
            dogButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            catButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            petType = AppPath2Pet.TYPE_CAT
            mandatoryComment.visibility = View.INVISIBLE
            clickedType = true
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
            if (!clickedType)
            {
                mandatoryComment.visibility = View.VISIBLE
                return@setOnClickListener
            }
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