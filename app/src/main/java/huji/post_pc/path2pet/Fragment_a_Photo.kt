package huji.post_pc.path2pet

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val TAG = "MyActivity"

class Fragment_a_Photo : Fragment() {
    lateinit var photoContext: Context
    val REQUEST_CODE = 200


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

        // gallery listener
        galleryButton.setOnClickListener {

            var answer : Boolean  = askForPermissions()
            if (answer) {
                lostPetActivityInstance!!.photo_open()
            }
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

    // permissions
    private fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(photoContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(photoContext as Activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(photoContext as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                }
                else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(photoContext)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", photoContext.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }



}