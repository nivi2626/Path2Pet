package huji.post_pc.path2pet.LostProcess

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
class Fragment_g_Details : Fragment() {

    lateinit var lostPetActivityInstance: LostPetProcess

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_g_details, container, false)
        lostPetActivityInstance = activity as LostPetProcess
        var allFilled : Boolean

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val nameTxt: EditText = view.findViewById(R.id.name)
        val emailTxt: EditText = view.findViewById(R.id.email)
        val phoneTxt: EditText = view.findViewById(R.id.phone)

        // get data from sp
        var name = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_NAME, "").toString()
        var email = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_EMAIL, "").toString()
        var phone = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_PHONE, "").toString()

        // set UI by sp
        nameTxt.setText(name)
        emailTxt.setText(email)
        phoneTxt.setText(phone)

        // next listener
        nextButton.setOnClickListener {
            allFilled = true

            if (nameTxt.text.toString() == "")
            {
                nameTxt.error = "This is a mandatory field"
                allFilled = false
            }
            if (emailTxt.text.toString() == "")
            {
                emailTxt.error = "This is a mandatory field"
                allFilled = false
            }
            else if (!isEmailValid(emailTxt.text.toString()))
            {
                emailTxt.error = "Email is not valid"
                allFilled = false
            }
            if (phoneTxt.text.toString() == "")
            {
                phoneTxt.error = "This is a mandatory field"
                allFilled = false
            }

            if (!allFilled)
            {
                return@setOnClickListener
            }

            lostPetActivityInstance.progressBar.incrementProgressBy(1)

            with(lostPetActivityInstance.sp.edit())
            {
                putString(AppPath2Pet.SP_NAME, nameTxt.text.toString())
                putString(AppPath2Pet.SP_EMAIL, email)
                putString(AppPath2Pet.SP_PHONE, phone)
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentEnd)
    }

    private fun isEmailValid(str : String): Boolean {
        return !TextUtils.isEmpty(str) && android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

}

