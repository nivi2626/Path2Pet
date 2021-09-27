package huji.post_pc.path2pet.LostProcess

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
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.Breed
import huji.post_pc.path2pet.BreedRecyclerAdapter
import huji.post_pc.path2pet.R
import pl.utkala.searchablespinner.SearchableSpinner
import java.util.ArrayList


class Fragment_d_BreedSize : Fragment() {
    private lateinit var lostPetActivityInstance: LostPetProcess
    private var breed_adapter: BreedRecyclerAdapter? = null
    private lateinit var breedList: ArrayList<Breed>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d_breed_size, container, false)
        this.lostPetActivityInstance = activity as LostPetProcess

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
        val petType: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_TYPE, null)
        var selectedBreed: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_BREED, AppPath2Pet.BREED_MIXED)
        var size: String? = lostPetActivityInstance.sp.getString(AppPath2Pet.SP_SIZE, null)

        // get pet's type and set items for breed list
        if (petType != null) {
            if (petType == AppPath2Pet.TYPE_DOG) {
                initializeDogBreedList()
            }
            else if (petType == AppPath2Pet.TYPE_CAT) {
                initializeCatBreedList()
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

    private fun initializeDogBreedList() {
        breedList = ArrayList<Breed>()
        breedList.add(Breed("Mixed Breed", R.drawable.dog_mixed))
        breedList.add(Breed("German Shepherd", R.drawable.dog_german_shepherd))
        breedList.add(Breed("Akita", R.drawable.dog_akita))
        breedList.add(Breed("Alaskan Malamute", R.drawable.dog_alaskan_malamute))
        breedList.add(Breed("Border Collie", R.drawable.dog_border_collie))
        breedList.add(Breed("Beagle", R.drawable.dog_beagle))
        breedList.add(Breed("Boxer", R.drawable.dog_boxer))
        breedList.add(Breed("Chihuahua", R.drawable.dog_chihuahua))
        breedList.add(Breed("Dalmatian", R.drawable.dog_dalmatian))
        breedList.add(Breed("Greyhound", R.drawable.dog_greyhound))
        breedList.add(Breed("Jack Russell Terrier", R.drawable.dog_jack_russell_terrier))
        breedList.add(Breed("Mastiff", R.drawable.dog_mastiff))
        breedList.add(Breed("Pomeranian", R.drawable.dog_pomeranian))
        breedList.add(Breed("Poodle", R.drawable.dog_poodle))
        breedList.add(Breed("Samoyed", R.drawable.dog_samoyed))
        breedList.add(Breed("Shih Tzu", R.drawable.dog_shi_tzu))
        breedList.add(Breed("Siberian Husky", R.drawable.dog_siberian_husky))
        breedList.add(Breed("Tibetan Mastiff", R.drawable.dog_tibetan_mastiff))
        breedList.add(Breed("Tibetan Terrier", R.drawable.dog_tibetan_terrier))
        breedList.add(Breed("Yorkshire Terrier", R.drawable.dog_yorkshire_terrier))
    }

    private fun initializeCatBreedList()
    {
        breedList = ArrayList<Breed>()
        breedList.add(Breed("Mixed Breed", R.drawable.cat_mixed))
        breedList.add(Breed("Asian", R.drawable.cat_asian))
        breedList.add(Breed("Balinese", R.drawable.cat_balinese))
        breedList.add(Breed("Bengal", R.drawable.cat_bengal))
        breedList.add(Breed("Chartreux", R.drawable.cat_chartreux))
        breedList.add(Breed("Chausie", R.drawable.cat_chausie))
        breedList.add(Breed("Cyprus", R.drawable.cat_cyprus))
        breedList.add(Breed("Dwelf", R.drawable.cat_dwelf))
        breedList.add(Breed("Foldex", R.drawable.cat_foldex))
        breedList.add(Breed("German Rex", R.drawable.cat_german_rex))
        breedList.add(Breed("Highlander", R.drawable.cat_highlander))
        breedList.add(Breed("Himalayan", R.drawable.cat_himalayan))
        breedList.add(Breed("Lambkin", R.drawable.cat_lambkin))
        breedList.add(Breed("LaPerm", R.drawable.cat_laperm))
        breedList.add(Breed("Minskin", R.drawable.cat_minskin))
        breedList.add(Breed("Napoleon", R.drawable.cat_napoleon))
        breedList.add(Breed("Persian", R.drawable.cat_persian))
        breedList.add(Breed("Siberian", R.drawable.cat_siberian))
        breedList.add(Breed("Thai", R.drawable.cat_thai))
        breedList.add(Breed("York Chocolate", R.drawable.cat_york_chocolate))
    }
}
