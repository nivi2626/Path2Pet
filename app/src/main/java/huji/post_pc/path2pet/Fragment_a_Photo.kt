package huji.post_pc.path2pet

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.smarteist.autoimageslider.SliderView

class Fragment_a_Photo : Fragment() {
    lateinit var photoContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_photo, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?
        photoContext = view.context

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val galleryButton: Button = view.findViewById(R.id.gallery_button)
//        val placeHolder: ImageView = view.findViewById(R.id.place_holder)

//        placeHolder.visibility = View.VISIBLE

        // gallery listener
        galleryButton.setOnClickListener {
            lostPetActivityInstance!!.photo_open()
        }

        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance?.progressBar?.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev or cancel listener
        prevButton.setOnClickListener {
            if (lostPetActivityInstance != null) {
                lostPetActivityInstance.progressBar.progress = 0
                exitDialog(view.context, lostPetActivityInstance.sp)
            }
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentMap)
    }

    fun exitDialog(context: Context, sp: SharedPreferences) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(View.inflate(view?.context, R.layout.alert_dialog, null))

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you want to leave?\nreport data will be lost")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                // clear sp
                sp.edit().clear().apply()
                // go back to main activity
                val intentMainActivity = Intent(context, MainActivity::class.java)
                startActivity(intentMainActivity)
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Cancel Report")
        // show alert dialog
        alert.show()
    }

}