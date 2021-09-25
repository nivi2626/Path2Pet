package huji.post_pc.path2pet.FoundProcess

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R

class Fragment_g_Details : Fragment() {

    lateinit var foundPetActivityInstance: FoundPetProcess

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.found_fragment_g_details, container, false)
        foundPetActivityInstance = activity as FoundPetProcess
        var allFilled : Boolean

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val nameTxt: EditText = view.findViewById(R.id.name)
        val emailTxt: EditText = view.findViewById(R.id.email)
        val phoneTxt: EditText = view.findViewById(R.id.phone)

        // get data from sp
        var name = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_NAME, "").toString()
        var email = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_EMAIL, "").toString()
        var phone = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_PHONE, "").toString()

        // set UI by sp
        nameTxt.setText(name)
        emailTxt.setText(email)
        phoneTxt.setText(phone)

        // next listener
        nextButton.setOnClickListener {
            foundPetActivityInstance.progressBar.incrementProgressBy(1)

            with(foundPetActivityInstance.sp.edit())
            {
                putString(AppPath2Pet.SP_NAME, nameTxt.text.toString())
                putString(AppPath2Pet.SP_EMAIL, emailTxt.text.toString())
                putString(AppPath2Pet.SP_PHONE, phoneTxt.text.toString())
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            foundPetActivityInstance.onBackPressed()
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        // TODO - currently the lost fragment
        Navigation.findNavController(view).navigate(R.id.fragment_h_End)
    }

    private fun isEmailValid(str : String): Boolean {
        return !TextUtils.isEmpty(str) && android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

}

