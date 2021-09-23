package huji.post_pc.path2pet.FoundProcess

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.smarteist.autoimageslider.SliderView
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
import huji.post_pc.path2pet.photosAdapter


class Fragment_a_Photo : Fragment() {
    lateinit var foundPetActivityInstance: FoundPetProcess
    lateinit var photoContext: Context
    lateinit var thisView: View
    private lateinit var adapter: photosAdapter
    lateinit var uriImages: MutableList<Uri>


    val REQUEST_CODE = 200


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_photo, container, false)
        this.foundPetActivityInstance = activity as FoundPetProcess
        this.thisView = view
        this.photoContext = view.context
        this.adapter = photosAdapter()
        this.uriImages =  mutableListOf<Uri>()


        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val galleryButton: Button = view.findViewById(R.id.gallery_button)
        val imageSlider: SliderView = view.findViewById(R.id.imageSlider)
        val placeHolder: ImageView = view.findViewById(R.id.place_holder)

        // set UI
        val photos = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_PHOTOS, null)
        if (photos != null) {
            imageSlider.visibility = View.VISIBLE
            placeHolder.visibility = View.INVISIBLE
            this.uriImages = foundPetActivityInstance.string2UriList(photos)
            showPhotos(this.uriImages)
        }
        else {
            imageSlider.visibility = View.INVISIBLE
            placeHolder.visibility = View.VISIBLE
        }

        // gallery listener
        galleryButton.setOnClickListener {
            if (askForPermissions()) {
                openPhoto()
            }
        }

        // next listener
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
            photoContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    photoContext as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(
                    photoContext as Activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE
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
            .setNegativeButton("Cancel", null)
            .show()
    }


    // handle photos
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200){
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


    // next button
    private fun nextButtonOnClick(view: View) {
        foundPetActivityInstance.progressBar.incrementProgressBy(1)
        Navigation.findNavController(view).navigate(R.id.fragmentMap)
    }


}