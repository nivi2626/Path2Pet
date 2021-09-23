package huji.post_pc.path2pet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)
        val feed = findViewById<Button>(R.id.feed)
        val loading  = findViewById<ProgressBar>(R.id.loadingPanel)
        loading.visibility = View.INVISIBLE

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

        // feed
        feed.setOnClickListener()
        {
            loading.visibility = View.VISIBLE
            if (!AppPath2Pet.loadingFlag) {
                loading.visibility = View.GONE
                val intentFeed = Intent(this, Feed::class.java)
                startActivity(intentFeed)
            }
        }
    }
}