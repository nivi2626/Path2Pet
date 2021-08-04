package huji.post_pc.path2pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar

class lostPetProcess : AppCompatActivity() {
    lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_pet_process)
    }

    fun incrementProgressBar(){
//        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        // set UI
//        progressBar.progress++
    }


}