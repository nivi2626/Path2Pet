package huji.post_pc.path2pet.FoundProcess

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.smarteist.autoimageslider.SliderView
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
import huji.post_pc.path2pet.photosAdapter
import java.io.ByteArrayOutputStream
import java.util.*

class Fragment_a_Camera : Fragment() {
    lateinit var foundPetActivityInstance: FoundPetProcess
    lateinit var cameraContext: Context
    lateinit var thisView: View
    private lateinit var adapter: photosAdapter
    lateinit var uriImages: MutableList<Uri>
    private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val Image_Capture_Code = 102
    private val GALLERY_REQUEST_CODE = 200
    private val REQUEST_IMAGE_CAPTURE = 100
    private val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.found_fragment_a_camera, container, false)
        this.foundPetActivityInstance = activity as FoundPetProcess
        this.thisView = view
        this.cameraContext = view.context
        this.adapter = photosAdapter()
        this.uriImages = ArrayList()

        // find views
        val nextButton = view.findViewById<Button>(R.id.next)
        val prevButton = view.findViewById<Button>(R.id.previous)
        val galleryButton = view.findViewById<Button>(R.id.gallery_button)
        val imageSlider: SliderView = view.findViewById(R.id.imageSlider)
        val camera = view.findViewById<Button>(R.id.open_camera)
        val placeHolder: ImageView = view.findViewById(R.id.place_holder)

        // set UI
        val photos = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_PHOTOS, null)
        if (photos != null && photos != "") {
            imageSlider.visibility = View.VISIBLE
            placeHolder.visibility = View.INVISIBLE
            this.uriImages = foundPetActivityInstance.string2UriList(photos)
            showPhotos(this.uriImages)
        }
        else {
            imageSlider.visibility = View.INVISIBLE
            placeHolder.visibility = View.VISIBLE
        }

        // camera listener
        camera.setOnClickListener {
            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
            //                cInt.putExtra(MediaStore.EXTRA_OUTPUT,
            //                        Uri.fromFile(photo));
            //                imageUri = Uri.fromFile(photo);
            startActivityForResult(cInt, 100)
        }

        // gallery listener
        galleryButton.setOnClickListener {
            if (askForPermissions()) {
                openPhoto()
            }
        }

        // next button listener
        nextButton.setOnClickListener {
            val strImages:String = uriList2string(uriImages)
            with(foundPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_PHOTOS, strImages)
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev or cancel listener
        prevButton.setOnClickListener {
            foundPetActivityInstance.progressBar.progress = 0
            foundPetActivityInstance.exitDialog(view.context, foundPetActivityInstance.sp)
        }

        return view
    }

    //        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
    //            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 100);
    //        }else
    //        {
    //            openCamera();
    //        }
    //    }
    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        if(requestCode == 100){
    //            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
    //                openCamera();
    //            }else
    //            {
    //                Toast.makeText(getActivity(), "Camera permission is required to use camera", Toast.LENGTH_LONG).show();
    //            }
    //        }
    //    }
    //    private void openCamera()
    //    {
    //        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //        startActivityForResult(cInt, 100);
    //    }


    // handle fragment A photo opening
    private fun openPhoto(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, 200);
    }

    private fun showPhotos(uriImages: MutableList<Uri>) {
        if (uriImages.size > 0) {
            val imageSlider: SliderView = thisView.findViewById(R.id.imageSlider)
            val placeHolder: ImageView = thisView.findViewById(R.id.place_holder)
            imageSlider.visibility = View.VISIBLE
            placeHolder.visibility = View.INVISIBLE
            adapter.renewItems(uriImages)
            imageSlider.setSliderAdapter(adapter)
        }
    }

    private fun uriList2string(images: MutableList<Uri>): String {
        var newStr = ""
        for (image in images) {
            newStr = newStr + AppPath2Pet.SP_DELIMITER + image.toString()
        }
        return newStr
    }

    // handle permissions
    private fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            cameraContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    cameraContext as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(
                    cameraContext as Activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    GALLERY_REQUEST_CODE
                )
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
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
        AlertDialog.Builder(cameraContext)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", cameraContext.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel", null)
            .show()
    }


    // handle photos
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && (requestCode == 200 || requestCode == 100)){
            // if multiple images are selected
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount

                for (i in 0 until count) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    uriImages.add(imageUri)
                }
            }
            // if single image is selected
            else if (data?.data != null) {
                var imageUri: Uri = data.data!!
                uriImages.add(imageUri)
            }
        }

        else {
            Log.i(
                "onActivityResult: ",
                "requestCode=$requestCode, resultCode=$resultCode, data$data"
            )
        }
        showPhotos(uriImages)
    }

    // next button
    private fun nextButtonOnClick(view: View) {
        foundPetActivityInstance.progressBar.incrementProgressBy(1)
        Navigation.findNavController(view).navigate(R.id.fragment_b_Map)
    }


}