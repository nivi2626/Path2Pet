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
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

private const val TAG = "MyActivity"

class Fragment_a_Photo : Fragment() {

    lateinit var photoContext: Context
    lateinit var imgView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    val REQUEST_CODE = 200


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_photo, container, false)
        photoContext = view.context
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val galleryButton: Button = view.findViewById(R.id.gallery_button)
        imgView = view.findViewById(R.id.petImageView)


        // gallery listener
        galleryButton.setOnClickListener {

            // TODO - trying to handle permissions - move it all to function
            var answer : Boolean  = askForPermissions()
            if (answer)
            {
                // TODO - commented temp, uncomment if multiple photos fails
                //  val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                // startActivityForResult(gallery, pickImage)
                openGalleryForImages()

            }

            // TODO - trying to handle permissions - move it all to function
        }


        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance!!.progressBar.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener{
            lostPetActivityInstance!!.progressBar.progress = 0
            lostPetActivityInstance.exitDialog(view.context)
//            lostPetActivityInstance.onBackPressed()
    }
        // TODO - implement later - get photo and save it
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


    // TODO - commented temp, uncomment if multiple photos fails
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            imageUri = data?.data
//            imgView.setImageURI(imageUri)
//        }
//    }

    // ask permissions code

    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(photoContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
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
                } else {
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


    // multiple photos code

    private fun openGalleryForImages() {

        // For latest versions API LEVEL 19+
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE);
    }

    // override onActivityForResult

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

            // if multiple images are selected
            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                if (count != null) {
                    for (i in 0..count - 1) {
                        var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                        Log.d(TAG, "multiple photos")
                        //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                    }
                }

            } else if (data?.data != null) {
                // if single image is selected
                var imageUri: Uri = data.data!!
                Log.d(TAG, "single photo")
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview

            }
        }
    }


}