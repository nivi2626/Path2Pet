package huji.post_pc.path2pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class FragmentCamera : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        // find views
        val nextButton: Button = view.findViewById(R.id.next)

        (activity as lostPetProcess?)?.incrementProgressBar()

        // next listener
        nextButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.fragmentMap)
        }

        return view
    }
}