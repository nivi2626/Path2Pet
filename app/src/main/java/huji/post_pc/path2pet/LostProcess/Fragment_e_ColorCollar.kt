package huji.post_pc.path2pet.LostProcess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Switch
import androidx.navigation.Navigation
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
import pl.utkala.searchablespinner.SearchableSpinner

private var COLOR_ARRAY = arrayOf(
    "Black",
    "Blonde",
    "Brown",
    "Ginger",
    "White",
    "Mixed"
)

class Fragment_e_ColorPattern : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_e_color_collar, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        var color: String? = null
        var hasCollar: Boolean = false

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val searchableSpinner: SearchableSpinner = view.findViewById(R.id.spinner)
        val hasCollarSwitch: Switch = view.findViewById(R.id.collar_choose)

        // set UI
        // spinner text
        searchableSpinner.setDialogTitle("Choose Pet Breed: ")
        searchableSpinner.setDismissText("Dismiss")

        // if there are saved details - get data from sp
        if (lostPetActivityInstance != null)
        {
            color = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_COLOR, null)
            hasCollar = lostPetActivityInstance.sp.getBoolean(AppPath2Pet.SP_COLLAR, false)
        }

        searchableSpinner.adapter = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_dropdown_item,
            COLOR_ARRAY
        )

        // set UI
        if (color != null) {
            searchableSpinner.setSelection(COLOR_ARRAY.indexOf(color))
        }

        if (hasCollar) {
            hasCollarSwitch.isChecked = true
        }

        searchableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {
                // set color
                color = searchableSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        // set switch listener
        hasCollarSwitch.setOnCheckedChangeListener { _, isChecked ->
            hasCollar = isChecked
        }

        // next listener
        nextButton.setOnClickListener {
            if (lostPetActivityInstance != null)
            {
                with(lostPetActivityInstance.sp.edit())
                {
                    putBoolean(AppPath2Pet.SP_COLLAR, hasCollar)
                    putString(AppPath2Pet.SP_COLOR, color)
                    apply()
                }
                lostPetActivityInstance.progressBar.incrementProgressBy(1)
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance!!.onBackPressed()
        }
        return view

    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentcomments)
    }

}