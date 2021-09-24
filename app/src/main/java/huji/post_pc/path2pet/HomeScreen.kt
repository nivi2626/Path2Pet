package huji.post_pc.path2pet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

class HomeScreen : AppCompatActivity() {

    lateinit var myPetID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)
        val feed = findViewById<Button>(R.id.feed)
        val loading = findViewById<ProgressBar>(R.id.loadingPanel)
        val lostPetButton = findViewById<Button>(R.id.myLostPets)
        val spLostPets = getSharedPreferences("my_lost_pets", Context.MODE_PRIVATE)
        loading.visibility = View.INVISIBLE

        // Set UI
        loading.visibility = View.VISIBLE
        lostPet.isEnabled = false
        foundPet.isEnabled = false
        feed.isEnabled = false

        AppPath2Pet.loadingFlag.observe(this, androidx.lifecycle.Observer {
            if (AppPath2Pet.loadingFlag.value == false) {
                // get String with all the IDs of pets the user lost
                val userLostPetsIDs = spLostPets.getString(AppPath2Pet.SP_LOST_ID, "")
                // set worker to work
                val findMatchesWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<CrosscheckWorker>()
                        // transfer user lost pets id
                        .setInputData(workDataOf(AppPath2Pet.WM_USER_LOST_PETS to userLostPetsIDs))
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
                                Log.d("TAG", "Success????")
                                // TODO - check value
                            }
                            workInfo.state == WorkInfo.State.FAILED -> {
                                val output = workInfo.outputData
                                // handle failure…
                            }
                            else -> {
                                Log.d("TAG", "Do nothing")

                            }
                        }
                    })

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

    override fun onResume() {
        super.onResume()
    }

    private fun openPopUpWindow(petID : String)
    {
        var curPet : Pet? = null
        for (pet in AppPath2Pet.getPetsDB().allPets)
        {
            if (pet.getId() == petID)
            {
                curPet = pet
                break
            }
        }
        if (curPet == null)
        {
            return
        }

        // start popUp
        val popupView = LayoutInflater.from(this).inflate(R.layout.pet_details_popup, null)
        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val adapter = photosAdapter()
        var openPopUp = popupWindow

        // find popUp views
        val closeButton = popupView.findViewById<Button>(R.id.close)
        val showDetailsButton = popupView.findViewById<Button>(R.id.show_details)
        val status = popupView.findViewById<TextView>(R.id.lost_or_found)
        val type = popupView.findViewById<TextView>(R.id.type)
        val breed = popupView.findViewById<TextView>(R.id.breed)
        val colors = popupView.findViewById<TextView>(R.id.colors)
        val sex = popupView.findViewById<TextView>(R.id.sex)
        val city = popupView.findViewById<TextView>(R.id.city)
        val collar = popupView.findViewById<TextView>(R.id.with_or_without_collar)
//            TextView laseSeenDate = popupView.findViewById(R.id.last_seen_date);
        //            TextView laseSeenDate = popupView.findViewById(R.id.last_seen_date);
        val reportDate = popupView.findViewById<TextView>(R.id.date_text)
        val comments = popupView.findViewById<TextView>(R.id.comments_edit)
        val imageSlider: SliderView = popupView.findViewById(R.id.imageSlider)
        val nameText = popupView.findViewById<TextView>(R.id.name_text)
        val nameEdit = popupView.findViewById<TextView>(R.id.name_edit)
        val emailText = popupView.findViewById<TextView>(R.id.email_text)
        val emailEdit = popupView.findViewById<TextView>(R.id.email_edit)
        val phoneText = popupView.findViewById<TextView>(R.id.phone_text)
        val phoneEdit = popupView.findViewById<TextView>(R.id.phone_edit)

        //set popUp UI:
        // set pet's description, report date, and city
        setStatusBreedColorsDateAndLocation(curPet, status, type, breed, colors, reportDate, city)
        // set pet's sex, comments and collar
        // set pet's sex, comments and collar
        sex.setText(curPet.getSex())
        comments.setText(curPet.getComments())
        if (curPet.hasCollar) {
            collar.text = AppPath2Pet.COLLAR_WITH
        } else {
            collar.text = AppPath2Pet.COLLAR_WITHOUT
        }
        // set photos
        // set photos
        val photos: List<Uri> = curPet.getImages()
        if (photos.isNotEmpty()) {
            imageSlider.visibility = View.VISIBLE
            imageSlider.setSliderAdapter(adapter)
            adapter.renewItems(photos as MutableList<Uri>)
        } else {
            imageSlider.visibility = View.INVISIBLE
        }

        closeButton.setOnClickListener { v1: View? ->
            popupWindow.dismiss()
        }
        popupWindow.showAsDropDown(popupView, 0, 0)
    }

    private fun setStatusBreedColorsDateAndLocation(
        pet: Pet,
        status: TextView,
        type: TextView,
        breed: TextView,
        colors: TextView,
        reportDate: TextView,
        city: TextView
    ) {
        // set pet's description
        status.text = pet.getStatus()

        // set pet's description
        type.text = pet.getPetType()

        // set pet's description - colors and breed
        val petsColors = StringBuilder()
        for (c in pet.getColors()) {
            if (c != "") {
                petsColors.append(c).append(", ")
            }
        }
        if (petsColors.length > 0) {
            colors.text = petsColors.subSequence(0, petsColors.length - 2)
        }
        breed.text = pet.getBreed()

        // set pets report and last seen dates
        val dataFormat: Format = SimpleDateFormat("dd/MM/yyyy")
        val report = pet.getReportDate()
        reportDate.text = dataFormat.format(report)

        // set pet's location
        try {
            val petsLocation = pet.getCityByLocation(city.context)
            city.text = petsLocation
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}