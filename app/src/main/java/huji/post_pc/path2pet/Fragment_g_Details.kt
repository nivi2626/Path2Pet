package huji.post_pc.path2pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class Fragment_g_Details : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_g_details, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)

        // get data from sp
        if (lostPetActivityInstance != null) {
            val details = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_DETAILS, "")
        }
        // todo - set UI with details from SP
        // todo - save new details to SP


        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance?.progressBar?.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance?.progressBar?.incrementProgressBy(-1)
            prevButtonOnClick(it)
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentEnd)
    }

    private fun prevButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentcomments)
    }
}