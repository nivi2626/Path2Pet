package huji.post_pc.path2pet.LostProcess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import android.widget.CheckBox
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
import kotlin.collections.ArrayList


class Fragment_e_ColorCollar : Fragment() {

    lateinit var lostPetActivityInstance: LostPetProcess

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_e_color_collar, container, false)
        val lostPetActivityInstance = activity as LostPetProcess

        var colorList: ArrayList<String> = ArrayList()
        var colorListString : String = ""

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val whiteBox: CheckBox = view.findViewById(R.id.checkBoxWhite)
        val blackBox: CheckBox = view.findViewById(R.id.checkBoxBlack)
        val grayBox: CheckBox = view.findViewById(R.id.checkBoxGray)
        val blondBox: CheckBox = view.findViewById(R.id.checkBoxBlond)
        val gingerBox: CheckBox = view.findViewById(R.id.checkBoxGinger)
        val brownBox: CheckBox = view.findViewById(R.id.checkBoxBrown)

        val hasCollarSwitch: Switch = view.findViewById(R.id.collar_choose)

        // get data from sp
        val colors: String = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLORS, "")!!
        var hasCollar: Boolean = lostPetActivityInstance.sp.getBoolean(AppPath2Pet.SP_COLLAR, false)

        // set UI
        if (colors != "") {
            colorList = colors.split(AppPath2Pet.SP_DELIMITER) as ArrayList<String>
            for (i in listOf(whiteBox, blackBox, grayBox, brownBox, blondBox, gingerBox)) {
                if (i.text.toString() in colorList) {
                    i.isChecked
                }
            }
        }

        if (hasCollar) {
            hasCollarSwitch.isChecked = true
        }

        // set switch listener
        hasCollarSwitch.setOnCheckedChangeListener { _, isChecked ->
            hasCollar = isChecked
        }

        // next listener
        nextButton.setOnClickListener {

            // add selected colors to colorList
            for (i in listOf(whiteBox, blackBox, grayBox, brownBox, blondBox, gingerBox)) {
                if (i.isChecked) {
                    colorList.add(i.text.toString())
                    colorListString+=i.text.toString()+ AppPath2Pet.SP_DELIMITER
                }
            }

            // edit sp
            with(lostPetActivityInstance.sp.edit())
            {
                putBoolean(AppPath2Pet.SP_COLLAR, hasCollar)
                putString(AppPath2Pet.SP_COLORS, colorListString)
                apply()
            }
            lostPetActivityInstance.progressBar.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()
        }
        return view

    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentcomments)
    }

}