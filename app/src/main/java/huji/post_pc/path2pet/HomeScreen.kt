package huji.post_pc.path2pet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.smarteist.autoimageslider.SliderView
import huji.post_pc.path2pet.LostProcess.LostPetProcess
import huji.post_pc.path2pet.FoundProcess.FoundPetProcess
import java.io.IOException
import java.lang.StringBuilder
import java.text.Format
import java.text.SimpleDateFormat
import java.util.function.Predicate
import kotlin.math.max

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)
        val feed = findViewById<Button>(R.id.feed)
        val loading = findViewById<ProgressBar>(R.id.loadingPanel)
        val lostPetButton = findViewById<Button>(R.id.myLostPets)
        val spLostPets = getSharedPreferences(AppPath2Pet.SP_MY_LOST, Context.MODE_PRIVATE)
        loading.visibility = View.INVISIBLE

        // Set UI
        loading.visibility = View.VISIBLE
        lostPet.isEnabled = false
        foundPet.isEnabled = false
        feed.isEnabled = false

        // load list of pet ids from sp
        val userLostPetsIDsString = spLostPets.getString(AppPath2Pet.SP_LOST_ID, "")
        var userLostPetsIDsList = mutableListOf<String>()
        if (userLostPetsIDsString != "") {
            userLostPetsIDsList = userLostPetsIDsString!!.split(AppPath2Pet.SP_DELIMITER) as MutableList<String>
        }

        AppPath2Pet.loadingFlag.observe(this, androidx.lifecycle.Observer {
            if (AppPath2Pet.loadingFlag.value == false) {

                if (userLostPetsIDsList.isNotEmpty() && !AppPath2Pet.workerWorked) {
                    setMyWorker(userLostPetsIDsList, userLostPetsIDsList.size - 1, 0)
                    AppPath2Pet.workerWorked = true
                }
                loading.visibility = View.GONE
                lostPet.isEnabled = true
                foundPet.isEnabled = true
                feed.isEnabled = true
            }
        })

        // lost pet
        lostPet.setOnClickListener() {
            val intentLost = Intent(this, LostPetProcess::class.java)
            startActivity(intentLost)
        }

        // found pet
        foundPet.setOnClickListener() {
            val intentFound = Intent(this, FoundPetProcess::class.java)
            startActivity(intentFound)
        }

        lostPetButton.setOnClickListener {
            val intentMyLostPets = Intent(this, MyLostPets::class.java)
            startActivity(intentMyLostPets)
        }

        // feed
        feed.setOnClickListener()
        {
            val intentFeed = Intent(this, Feed::class.java)
            startActivity(intentFeed)
        }

    }

    override fun onBackPressed() {
        return
    }

    private fun setMyWorker(ids: List<String>, maxIDNum: Int, curIDNum: Int){
        if (curIDNum > maxIDNum) {
            return
        }
        else {
            val findMatchesWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<CrosscheckWorker>()
                    // transfer user lost pets id
                    .setInputData(workDataOf(curIDNum.toString() to ids[curIDNum]))
                    .build()

            // enqueue work
            WorkManager.getInstance(this).enqueue(findMatchesWorkRequest)
            // get results from observer
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(findMatchesWorkRequest.id)
                .observe(this, Observer { workInfo ->
                    when {
                        workInfo == null -> return@Observer
                        workInfo.state == WorkInfo.State.SUCCEEDED -> {
                            val output = workInfo.outputData
                            AppPath2Pet.bestMatches.add(output)
                            Log.d("TAG", "Success????")
                            setMyWorker(ids, maxIDNum, curIDNum + 1)
                        }
                        workInfo.state == WorkInfo.State.FAILED -> {
                            setMyWorker(ids, maxIDNum, curIDNum + 1)
                        }
                        else -> {
                            Log.d("TAG", "Do nothing")
                        }
                    }
                })
        }
    }
}