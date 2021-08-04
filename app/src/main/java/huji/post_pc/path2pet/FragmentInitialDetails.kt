package huji.post_pc.path2pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FragmentInitialDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_details, container, false)

        // find views
        val nextButton: Button = view.findViewById(R.id.next)

        //set UI
        //TODO - if we already have the info - show it
//        (activity as LostPetProcess?)?.incrementProgressBar()

//    // next listener
//    nextButton.setOnClickListener {
//        Navigation.findNavController(view).navigate(R.id.???)
//    }
        return view
    }
}