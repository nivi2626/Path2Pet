package huji.post_pc.path2pet

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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


        femaleButton.isEnabled = true
        maleButton.isEnabled = false
        nextButton.isClickable = false

        // male&female listeners
        femaleButton.setOnClickListener(){
            maleButton.isEnabled = false
        }

        maleButton.setOnClickListener(){
            femaleButton.isEnabled = false
        }

        return view
    }

    private fun nextButtonOnClick(view:View) {
        onboardingViewModel.increaseProgress()
        // TODO - change to next fragment when one is available
        Navigation.findNavController(view).navigate(R.id.fragmentInitialDetails)
    }

    private fun prevButtonOnClick(view:View) {
        onboardingViewModel.decreaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentMap)
    }
}