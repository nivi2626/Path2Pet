package huji.post_pc.path2pet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lostPet = findViewById<Button>(R.id.lost_pet)
        val foundPet = findViewById<Button>(R.id.found_pet)

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
    }
}