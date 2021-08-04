package huji.post_pc.path2pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentColorPattern.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentColorPattern : Fragment() {
    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_color_pattern, container, false)

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
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
        return view

    }

        private fun nextButtonOnClick(view: View) {
            // todo - change next screen
            onboardingViewModel.increaseProgress()
            Navigation.findNavController(view).navigate(R.id.fragmentBreedSize)
        }

        private fun prevButtonOnClick(view: View) {
            onboardingViewModel.decreaseProgress()
            Navigation.findNavController(view).navigate(R.id.fragmentBreedSize)
        }
}