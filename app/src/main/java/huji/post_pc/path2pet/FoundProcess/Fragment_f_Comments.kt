package huji.post_pc.path2pet.FoundProcess

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R


class Fragment_f_Comments : Fragment() {

    lateinit var cameraContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.found_fragment_f_comments, container, false)
        cameraContext = view.context
        val foundPetActivityInstance: FoundPetProcess? = activity as FoundPetProcess?
        var userComments : String? = ""

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val userCommentsText: EditText = view.findViewById(R.id.comments)

        // get data from sp
        userComments = foundPetActivityInstance!!.sp.getString(AppPath2Pet.SP_COMMENTS, "")

        // set data by sp
        if (userComments!=null) {
            if (userComments.isNotEmpty())
            {
                userCommentsText.setText(userComments)
            }
        }

        // next listener
        nextButton.setOnClickListener {
            userComments = userCommentsText.text.toString()
            with(foundPetActivityInstance.sp.edit())
            {
                putString(AppPath2Pet.SP_COMMENTS, userComments)
                apply()
            }
            foundPetActivityInstance.progressBar.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            foundPetActivityInstance.onBackPressed()
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragment_g_Details)
    }

}