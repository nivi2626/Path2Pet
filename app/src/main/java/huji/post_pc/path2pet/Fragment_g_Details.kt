package huji.post_pc.path2pet

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class Fragment_g_Details : Fragment() {
    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_g_details, container, false)
        val sp = this.activity?.getSharedPreferences("local_lost_db", Context.MODE_PRIVATE)
        var name : String? = ""
        var email : String? = ""
        var phone : String? = ""

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val nameText: EditText = view.findViewById(R.id.name)
        val emailText: EditText = view.findViewById(R.id.email)
        val phoneText: EditText = view.findViewById(R.id.phone)

        // get data from sp
        if (sp != null) {
            name = sp.getString("NAME", "")
            email = sp.getString("EMAIL", "")
            phone = sp.getString("PHONE_NUMBER", "")
        }

        // set data by sp
        if (name!=null)
        {
            if (name.isNotEmpty())
            {
                nameText.setText(name)
            }
        }
        if (email != null) {
            if (email.isNotEmpty())
            {
                emailText.setText(email)
            }
        }
        if (phone != null) {
            if (phone.isNotEmpty())
            {
                phoneText.setText(phone)
            }
        }



        // next listener
        nextButton.setOnClickListener {
            name = nameText.text.toString()
            email = emailText.text.toString()
            phone = phoneText.text.toString()
            if (sp != null) {
                with(sp.edit())
                {
                    putString("NAME", name)
                    putString("EMAIL", email)
                    putString("PHONE_NUMBER", phone)
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
        Navigation.findNavController(view).navigate(R.id.fragmentEnd)
    }

    private fun prevButtonOnClick(view: View) {
        onboardingViewModel.decreaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentcomments)
    }


    // TODO - use this when validate email
    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}