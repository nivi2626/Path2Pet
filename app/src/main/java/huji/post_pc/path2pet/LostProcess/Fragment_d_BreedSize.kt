package huji.post_pc.path2pet.LostProcess

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import huji.post_pc.path2pet.*
import pl.utkala.searchablespinner.SearchableSpinner
import java.io.File
import java.util.ArrayList


class Fragment_d_BreedSize : Fragment() {
    private lateinit var lostPetActivityInstance: LostPetProcess
    private var breed_adapter: BreedRecyclerAdapter? = null
    private lateinit var breedList: ArrayList<Breed>
    private lateinit var myContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d_breed_size, container, false)
        this.lostPetActivityInstance = activity as LostPetProcess
        myContext = view.context

        // find views
        val smallButton: Button = view.findViewById(R.id.Small)
        val mediumButton: Button = view.findViewById(R.id.Medium)
        val largeButton: Button = view.findViewById(R.id.Large)
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val breeds_choice: Button = view.findViewById(R.id.breeds)
        val breedText: TextView = view.findViewById(R.id.breedText)

        // set UI
        // button colors
        smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))

        // get data from sp
        val petType: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, null)
        var selectedBreed: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, AppPath2Pet.BREED_MIXED)
        var size: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, null)

        // get pet's type and set items for breed list
        // init breedsSetup

//        val myBreedsSetup : BreedsSetup = BreedsSetup()

        if (petType != null) {
            if (petType == AppPath2Pet.TYPE_DOG) {
                this.breedList = initializeDogBreedList()
            }
            else if (petType == AppPath2Pet.TYPE_CAT) {
                this.breedList = initializeCatBreedList()
            }
        }

        // set breed
        if(selectedBreed != null){
            breedText.text = selectedBreed
        }
        else {
            selectedBreed = breedList[0].breedName
            breedText.text = selectedBreed
        }

        // set size
        if (size != null) {
            when (size) {
                AppPath2Pet.SIZE_SMALL -> {
                    smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
                }
                AppPath2Pet.SIZE_MEDIUM -> {
                    mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
                }
                else -> {
                    largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
                }
            }
        }

        breeds_choice.setOnClickListener(){
            // start popUp
            val popupView = LayoutInflater.from(context).inflate(R.layout.breed_popup, null)
            val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            lostPetActivityInstance.openPopUp = popupWindow

            // find popUp views
            val closeButton = popupView.findViewById<Button>(R.id.close_breeds)
            val recycler = popupView.findViewById<RecyclerView>(R.id.recycler);
            breed_adapter = BreedRecyclerAdapter(breedList, selectedBreed)

            // set recycler view
            recycler!!.layoutManager = LinearLayoutManager(context)
            recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            recycler.adapter = breed_adapter

            closeButton.setOnClickListener {v1: View? ->
                selectedBreed = breed_adapter!!.selectedItem
                breedText.setText(selectedBreed)
                lostPetActivityInstance.openPopUp = null
                popupWindow.dismiss()
            }
            popupWindow.showAsDropDown(popupView, 0, 0)
        }

        // size listeners
        smallButton.setOnClickListener() {
            mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            size = AppPath2Pet.SIZE_SMALL
        }

        mediumButton.setOnClickListener() {
            smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            size = AppPath2Pet.SIZE_MEDIUM
        }

        largeButton.setOnClickListener() {
            smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
            largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.CHOSEN_COLOR))
            size = AppPath2Pet.SIZE_LARGE
        }

        // next listener
        nextButton.setOnClickListener {
            with(lostPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_SIZE, size)
                putString(AppPath2Pet.SP_BREED, selectedBreed)
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()
        }
        return view
    }

    private fun nextButtonOnClick(view: View) {
        lostPetActivityInstance.progressBar.incrementProgressBy(1)
        Navigation.findNavController(view).navigate(R.id.fragmentColorPattern)
    }

}
