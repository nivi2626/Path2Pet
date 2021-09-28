package huji.post_pc.path2pet.FoundProcess
import android.graphics.Color
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
import huji.post_pc.path2pet.*
import pl.utkala.searchablespinner.SearchableSpinner
import java.util.ArrayList



class Fragment_d_BreedSize : Fragment() {
    private lateinit var foundPetActivityInstance: FoundPetProcess
    private var breed_adapter: BreedRecyclerAdapter? = null
    private lateinit var breedList: ArrayList<Breed>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.found_fragment_d_breed_size, container, false)
        foundPetActivityInstance = activity as FoundPetProcess

        // find views
        val smallButton: Button = view.findViewById(R.id.Small)
        val mediumButton: Button = view.findViewById(R.id.Medium)
        val largeButton: Button = view.findViewById(R.id.Large)
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val breeds_choice: Button = view.findViewById(R.id.breeds);
        val breedText: TextView = view.findViewById(R.id.breedText)

        // set UI
        // button colors
        smallButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        mediumButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))
        largeButton.setBackgroundColor(Color.parseColor(AppPath2Pet.NOT_CHOSEN_COLOR))

        // get data from sp
        val petType: String? = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, null)
        var selectedBreed: String? = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, AppPath2Pet.BREED_MIXED)
        var size: String? = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, null)

        // get pet's type and set items for breed list
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
        else
        {
            selectedBreed =  breedList[0].breedName
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
            foundPetActivityInstance.openPopUp = popupWindow

            // find popUp views
            val closeButton = popupView.findViewById<Button>(R.id.close_breeds)
            val recycler = popupView.findViewById<RecyclerView>(R.id.recycler);
            breed_adapter = BreedRecyclerAdapter(breedList, selectedBreed)

            // set recycle viewer
            recycler!!.layoutManager = LinearLayoutManager(context)
            recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            recycler.adapter = breed_adapter

            closeButton.setOnClickListener { v1: View? ->
                selectedBreed = breed_adapter!!.selectedItem
                breedText.text = selectedBreed
                foundPetActivityInstance.openPopUp = null
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
            with(foundPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_SIZE, size)
                putString(AppPath2Pet.SP_BREED, selectedBreed)
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            foundPetActivityInstance.onBackPressed()
        }
        return view
    }

    private fun nextButtonOnClick(view: View) {
        foundPetActivityInstance.progressBar.incrementProgressBy(1)
        Navigation.findNavController(view).navigate(R.id.fragment_e_ColorCollar)
    }
}
