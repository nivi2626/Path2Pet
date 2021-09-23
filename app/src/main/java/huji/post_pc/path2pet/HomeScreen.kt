package huji.post_pc.path2pet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import huji.post_pc.path2pet.LostProcess.LostPetProcess
import huji.post_pc.path2pet.FoundProcess.FoundPetProcess

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)
        val feed = findViewById<Button>(R.id.feed)
        val loading  = findViewById<ProgressBar>(R.id.loadingPanel)
        val lostPetButton = findViewById<Button>(R.id.myLostPets)
        loading.visibility = View.INVISIBLE

        // Set UI
        loading.visibility = View.VISIBLE
        lostPet.isEnabled = false
        foundPet.isEnabled = false
        feed.isEnabled = false

        AppPath2Pet.loadingFlag.observe(this, androidx.lifecycle.Observer {
            if (AppPath2Pet.loadingFlag.value == false)
            {
                loading.visibility = View.GONE
                lostPet.isEnabled = true
                foundPet.isEnabled = true
                feed.isEnabled = true
            }
        })

        // lost pet
        lostPet.setOnClickListener(){
            val intentLost = Intent(this, LostPetProcess::class.java)
            startActivity(intentLost)
        }

        // found pet
        foundPet.setOnClickListener(){
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
}