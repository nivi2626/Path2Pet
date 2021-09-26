package huji.post_pc.path2pet.FoundProcess

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.HomeScreen
import huji.post_pc.path2pet.R


class FoundPetProcess : AppCompatActivity() {
    lateinit var sp : SharedPreferences
    lateinit var spFoundPets : SharedPreferences
    lateinit var progressBar: ProgressBar
    var openPopUp: PopupWindow? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.sp = this.getSharedPreferences("local_found_db", Context.MODE_PRIVATE)
        sp.edit().clear().apply()
        setContentView(R.layout.activity_found_pet_process)
        // find views
        progressBar = findViewById(R.id.progressBar)
    }

    override fun onBackPressed() {
        if (openPopUp != null) {
            openPopUp!!.dismiss()
            openPopUp = null
            return
        }
        if (progressBar.progress < 7)
        {
            super.onBackPressed()
            progressBar.incrementProgressBy(-1)
            if (progressBar.progress < 0){
                sp.edit().clear().apply()
            }
        }
        else
        {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intentHomeScreen = Intent(this, HomeScreen::class.java)
            startActivity(intentHomeScreen)
            return
        }
    }

    fun exitDialog(context: Context, sp: SharedPreferences) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(View.inflate(context, R.layout.alert_dialog, null))

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you want to exit?\nAll data will be lost")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                // clear sp
                sp.edit().clear().apply()
                // go back to main activity
                val intentMainActivity = Intent(context, HomeScreen::class.java)
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

    fun string2UriList(images:String?): MutableList<Uri> {
        if (images == null) {
            return mutableListOf<Uri>()
        }
        val stringList = images.split(AppPath2Pet.SP_DELIMITER).toTypedArray()
        val uriList = mutableListOf<Uri>()
        for (image in stringList) {
            if (image != ""){
                uriList.add(Uri.parse(image))
            }
        }
        return uriList
    }

    fun uriList2string(images: MutableList<Uri>): String {
        var newStr = ""
        for (image in images) {
            newStr = newStr + AppPath2Pet.SP_DELIMITER + image.toString()
        }
        return newStr
    }
}