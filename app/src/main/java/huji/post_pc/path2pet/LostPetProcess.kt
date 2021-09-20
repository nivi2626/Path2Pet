package huji.post_pc.path2pet

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.smarteist.autoimageslider.SliderView


class LostPetProcess : AppCompatActivity() {
    lateinit var sp : SharedPreferences
    lateinit var progressBar: ProgressBar
    lateinit var bitmapImages: MutableList<Bitmap>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.bitmapImages =  mutableListOf<Bitmap>()
        setContentView(R.layout.activity_lost_pet_process)
        sp = this.getSharedPreferences("local_lost_db", Context.MODE_PRIVATE)
        progressBar = findViewById(R.id.progressBar)


//        // location
//        val requestPermissionLauncher =
//            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your
//                    // app.
//                }
//                else
//                {
//                    // // request permission (ActivityCompat#requestPermissions)
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                }
//            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        progressBar.incrementProgressBy(-1)
        if (progressBar.progress == 0){
            sp.edit().clear().apply()
        }
    }

    fun exitDialog(context : Context)
    {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(View.inflate(context, R.layout.alert_dialog, null))

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you want to leave?\nReport data will be lost")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id ->
                // clear sp
                sp.edit().clear().apply()
                // go back to main activity
                onBackPressed()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setTitle("Cancel Report")
        alert.show()
    }

    // handle fragment A photo opening
    public fun photo_open(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 200);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200){
            // if multiple images are selected
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount

                for (i in 0 until count) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    bitmapImages.add(MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri))
                }

            }
            // if single image is selected
            else if (data?.data != null) {
                var imageUri: Uri = data.data!!
                bitmapImages.add(MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri))
            }
        }
        else {
            Log.i("onActivityResult: ",
                "requestCode=$requestCode, resultCode=$resultCode, data$data"
            )
        }
        // show photos
        if (bitmapImages.size > 0){
            val adapter = Fragment_a_photosAdapter()
            adapter.renewItems(bitmapImages)
            val imageSlider: SliderView = findViewById(R.id.imageSlider)
            imageSlider.setSliderAdapter(adapter)
            imageSlider.visibility = View.VISIBLE
        }
    }

}