package huji.post_pc.path2pet

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class Fragment_a_Photo : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_photo, container, false)
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)

        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance?.progressBar?.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev or cancel listener
        prevButton.setOnClickListener{
            if (lostPetActivityInstance != null)
            {
                lostPetActivityInstance.progressBar.progress = 0
                exitDialog(view.context, lostPetActivityInstance.sp)
            }
        }
        // TODO - implement later

        return view
    }

    private fun nextButtonOnClick(view:View) {
        Navigation.findNavController(view).navigate(R.id.fragmentMap)
    }

    fun exitDialog(context : Context, sp : SharedPreferences)
    {
        val dialogBuilder = AlertDialog.Builder(context)

        // set message of alert dialog
        dialogBuilder.setMessage("Do you want to close this report ?\n(report data will be lost)")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                    dialog, id ->
                // clear sp
                sp.edit().clear().apply()
                // go back to main activity
                val intentMainActivity = Intent(context, MainActivity::class.java)
                startActivity(intentMainActivity)
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Cancel Report")
        // show alert dialog
        alert.show()
    }


}