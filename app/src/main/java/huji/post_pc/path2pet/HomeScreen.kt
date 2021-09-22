package huji.post_pc.path2pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)
        val feed = findViewById<Button>(R.id.feed)
        val myLostPetsButton = findViewById<Button>(R.id.myLostPets)

        // lost pet
        lostPet.setOnClickListener(){
            val intentLost = Intent(this, LostPetProcess::class.java)
            startActivity(intentLost)
        }

        // found pet
        foundPet.setOnClickListener(){
//            val intentFound = Intent(this, lostPetProcess::class.java)
//            startActivity(intentFound)
        }

        // my lost pets button
        myLostPetsButton.setOnClickListener()
        {
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
}