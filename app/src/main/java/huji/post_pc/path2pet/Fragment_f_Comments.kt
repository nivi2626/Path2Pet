package huji.post_pc.path2pet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation


class Fragment_f_Comments : Fragment() {
    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f_comments, container, false)
        val sp = this.activity?.getSharedPreferences("local_lost_db", Context.MODE_PRIVATE)
        var userComments : String? = ""

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val userCommentsText: EditText = view.findViewById(R.id.comments)

        // get data from sp
        if (sp != null) {
            userComments = sp.getString("COMMENTS", "")
        }

        // set data by sp
        if (userComments!=null)
        {
            if (userComments.isNotEmpty())
            {
                userCommentsText.setText(userComments)
            }
        }

        // next listener
        nextButton.setOnClickListener {
            userComments = userCommentsText.text.toString()
            if (sp != null) {
                with(sp.edit())
                {
                    putString("COMMENTS", userComments)
                    apply()
                }
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            prevButtonOnClick(it)
        }

        return view

    }

    private fun nextButtonOnClick(view: View) {
        onboardingViewModel.increaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentDetails)
    }

    private fun prevButtonOnClick(view: View) {
        onboardingViewModel.decreaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentColorPattern)
    }
}