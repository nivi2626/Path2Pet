package huji.post_pc.path2pet

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class Fragment_c_TypeGender : Fragment() {

    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_type_gender, container, false)

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val dogButton: Button = view.findViewById(R.id.dog)
        val catButton: Button = view.findViewById(R.id.cat)
        val femaleButton: Button = view.findViewById(R.id.female)
        val maleButton: Button = view.findViewById(R.id.male)
        var petType: String? = null

        // set UI
        // get data from sp
        //TODO - if we already have the info - show it

        // next listener
        nextButton.setOnClickListener {
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            prevButtonOnClick(it)
        }

        // type colors
        dogButton.setBackgroundColor(Color.parseColor("#FF737E75"))
        catButton.setBackgroundColor(Color.parseColor("#FF737E75"))

        // gender colors
        femaleButton.setBackgroundColor(Color.parseColor("#FF737E75"))
        maleButton.setBackgroundColor(Color.parseColor("#FF737E75"))


        // type listeners
        dogButton.setOnClickListener(){
            catButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            dogButton.setBackgroundColor(Color.parseColor("#46A556"))
            petType = "dog"
        }

        catButton.setOnClickListener(){
            dogButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            catButton.setBackgroundColor(Color.parseColor("#46A556"))
            petType = "cat"
        }

        // gender listeners
        femaleButton.setOnClickListener(){
            maleButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            femaleButton.setBackgroundColor(Color.parseColor("#46A556"))
        }

        maleButton.setOnClickListener(){
            femaleButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            maleButton.setBackgroundColor(Color.parseColor("#46A556"))
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