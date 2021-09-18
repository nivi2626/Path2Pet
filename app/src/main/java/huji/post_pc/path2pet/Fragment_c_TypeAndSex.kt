package huji.post_pc.path2pet

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class Fragment_c_TypeGender : Fragment() {

    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_c_type_sex, container, false)
        val sp = this.activity?.getSharedPreferences("local_lost_db", Context.MODE_PRIVATE)

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
        dogButton.setBackgroundColor(Color.parseColor("#909190"))
        catButton.setBackgroundColor(Color.parseColor("#909190"))

        // gender colors
        femaleButton.setBackgroundColor(Color.parseColor("#909190"))
        maleButton.setBackgroundColor(Color.parseColor("#909190"))

        // get data from sp
        if (sp != null) {
            petType = sp.getString("PET_TYPE", null)
            petGender = sp.getString("PET_GENDER", null)
        }

        // set data by sp
        if (petType != null)
        {
            if (petType == "dog")
            {
                catButton.setBackgroundColor(Color.parseColor("#909190"))
                dogButton.setBackgroundColor(Color.parseColor("#46A556"))
            }
            else if (petType == "cat")
            {
                dogButton.setBackgroundColor(Color.parseColor("#909190"))
                catButton.setBackgroundColor(Color.parseColor("#46A556"))
            }
        }

        if (petGender != null)
        {
            if (petGender == "male")
            {
                femaleButton.setBackgroundColor(Color.parseColor("#909190"))
                maleButton.setBackgroundColor(Color.parseColor("#46A556"))
            }
            else if (petGender == "female")
            {
                maleButton.setBackgroundColor(Color.parseColor("#909190"))
                femaleButton.setBackgroundColor(Color.parseColor("#46A556"))
            }
        }

        // next listener
        nextButton.setOnClickListener {
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            prevButtonOnClick(it)
        }

        // type listeners
        dogButton.setOnClickListener(){
            catButton.setBackgroundColor(Color.parseColor("#909190"))
            dogButton.setBackgroundColor(Color.parseColor("#46A556"))
            petType = "dog"

            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_TYPE", petType)
                    apply()
                }
            }
        }

        catButton.setOnClickListener(){
            dogButton.setBackgroundColor(Color.parseColor("#909190"))
            catButton.setBackgroundColor(Color.parseColor("#46A556"))
            petType = "cat"

            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_TYPE", petType)
                    apply()
                }
            }
        }

        // gender listeners
        femaleButton.setOnClickListener(){
            maleButton.setBackgroundColor(Color.parseColor("#909190"))
            femaleButton.setBackgroundColor(Color.parseColor("#46A556"))
            petGender = "female"
            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_GENDER", petGender)
                    apply()
                }
            }
        }

        maleButton.setOnClickListener(){
            femaleButton.setBackgroundColor(Color.parseColor("#909190"))
            maleButton.setBackgroundColor(Color.parseColor("#46A556"))
            petGender = "male"
            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_GENDER", petGender)
                    apply()
                }
            }
        }

        return view
    }

    private fun nextButtonOnClick(view:View) {
        onboardingViewModel.increaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentBreedSize)
    }

    private fun prevButtonOnClick(view:View) {
        onboardingViewModel.decreaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentMap)
    }
}