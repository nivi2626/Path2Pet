package huji.post_pc.path2pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.activity.viewModels


class LostPetProcess : AppCompatActivity() {
    private val lostPetViewModel: LostPetViewModel by viewModels()
    lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_pet_process)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        // set an observer for progress
        setLostPetProgressObserver()

        // TODO - was in Ron's project, don't know if we need it here
        // set an observer for lost pet process to be notified when user finished it
//        setLostPetDoneObserver()
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


    // TODO - was in Ron's project, don't know if we need it here
//    private fun setLostPetDoneObserver() {
//        val lostPetDoneObserver = Observer<Boolean> {
//
//        }
//
//        val lostPetDoneObserver = Observer<Boolean> { lostPetDone ->
//            (applicationContext as ShoeNotificationsApp).lostPetDone = lostPetDone
//            if (lostPetDone) {
//                // kill this activity and start the post-published activity
//                    // TODO - implement post report activity
//                this.finish()
//            }
//        }
//        lostPetViewModel.lostPetDoneLiveData.observe(this, lostPetDoneObserver)
//    }

}