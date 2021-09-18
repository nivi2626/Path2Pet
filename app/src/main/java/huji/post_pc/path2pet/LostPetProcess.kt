package huji.post_pc.path2pet

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class LostPetProcess : AppCompatActivity() {
    private val lostPetViewModel: LostPetViewModel by viewModels()
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_pet_process)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // set an observer for progress
        setLostPetProgressObserver()

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

    // TODO - change to either backPress or clicking "Prev"
    override fun onBackPressed() {
        super.onBackPressed()
        lostPetViewModel.decreaseProgress()
    }

    private fun setLostPetProgressObserver() {
        val progressBarObserver = Observer<Int> { progressInt ->
            progressBar.progress = progressInt
        }
        lostPetViewModel.progressLiveData.observe(this, progressBarObserver)
    }

}