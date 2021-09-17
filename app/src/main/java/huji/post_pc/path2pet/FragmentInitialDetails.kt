package huji.post_pc.path2pet

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class FragmentInitialDetails : Fragment() {

    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_details, container, false)

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val femaleButton: Button = view.findViewById(R.id.female)
        val maleButton: Button = view.findViewById(R.id.male)
        val prevButton: Button = view.findViewById(R.id.previous)

        // set UI
        //TODO - if we already have the info - show it

        // next listener
        nextButton.setOnClickListener {
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            prevButtonOnClick(it)
        }

        femaleButton.setBackgroundColor(Color.parseColor("#FF737E75"))
        maleButton.setBackgroundColor(Color.parseColor("#FF737E75"))

        // male&female listeners
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