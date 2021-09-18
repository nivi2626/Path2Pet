package huji.post_pc.path2pet

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import pl.utkala.searchablespinner.SearchableSpinner

private var DOG_BREED_ARRAY = arrayOf(
    "Mixed Breed",
    "Affenpinscher",
    "Afghan Hound",
    "Airedale Terrier",
    "Akita",
    "Alaskan Malamute",
    "American Hairless Terrier",
    "American Staffordshire Terrier",
    "Anatolian Shepherd Dog",
    "Australian Cattle Dog",
    "Australian Heeler Cross",
    "Australian Kelpie",
    "Australian Silky Terrier",
    "Australian Stumpy Tail Cattle Dog",
    "Australian Terrier",
    "Azawakh",
    "Basenji",
    "Basset Fauve de Bretagne",
    "Basset Hound",
    "Beagle",
    "Bearded Collie",
    "Bedlington Terrier",
    "Belgian Shepherd Dog",
    "Bergamasco Shepherd Dog",
    "Bernese Mountain Dog",
    "Bichon Frise",
    "Black and Tan Coonhound",
    "Bloodhound",
    "Bluetick Coonhound",
    "Border Collie",
    "Border Terrier",
    "Borzoi",
    "Boston Terrier",
    "Bouvier des Flandres",
    "Boxer",
    "Bracco Italiano",
    "Briard",
    "British Bulldog",
    "Brittany",
    "Bull Terrier",
    "Bullmastiff",
    "Cairn Terrier",
    "Canaan Dog",
    "Canadian Eskimo Dog",
    "Cane Corso",
    "Caucasian Shepherd Dog",
    "Cavalier King Charles Spaniel",
    "Cavoodle",
    "Central Asian Shepherd Dog",
    "Cesky Terrier",
    "Chesapeake Bay Retriever",
    "Chihuahua",
    "Chinese Crested Dog",
    "Chow Chow",
    "Clumber Spaniel",
    "Cockapoo",
    "Cocker Spaniel",
    "Coton De Tulear",
    "Curly Coated Retriever",
    "Dachshund",
    "Dalmatian",
    "Dandie Dinmont Terrier",
    "Deerhound",
    "Dobermann",
    "Dogue de Bordeaux",
    "Dutch Shepherd Dog",
    "English Setter",
    "English Springer Spaniel",
    "English Toy Terrier (Black and Tan)",
    "Estrela Mountain Dog",
    "Eurasier",
    "Field Spaniel",
    "Finnish Lapphund",
    "Finnish Spitz",
    "Flat Coated Retriever",
    "Fox Terrier",
    "Fox Terrier (Smooth) Fox Terrier (Wire)",
    "Foxhound",
    "French Bulldog",
    "German Hunting Terrier",
    "German Pinscher",
    "German Shepherd Dog",
    "German Shorthaired Pointer",
    "German Spitz",
    "German Wirehaired Pointer",
    "Glen of Imaal Terrier",
    "Golden Retriever",
    "Gordon Setter",
    "Grand Basset Griffon Vendeen",
    "Great Dane",
    "Greyhound",
    "Griffon Bruxellois",
    "Groodle",
    "Hamiltonstovare",
    "Harrier",
    "Havanese",
    "Hungarian Vizsla",
    "Hungarian Wirehaired Vizsla",
    "Ibizan Hound",
    "Icelandic Sheepdog",
    "Irish Red and White Setter",
    "Irish Setter",
    "Irish Terrier",
    "Irish Water Spaniel",
    "Irish Wolfhound",
    "Italian Greyhound",
    "Italian Spinone",
    "Jack Russell Terrier",
    "Japanese Chin",
    "Japanese Spitz",
    "Kangal Shepherd Dog",
    "Keeshond",
    "Kerry Blue Terrier",
    "King Charles Spaniel",
    "Komondor",
    "Kuvasz",
    "Labradoodle",
    "Labrador",
    "Labrador Retriever",
    "Lagotto Romagnolo",
    "Lakeland Terrier",
    "Landseer (European Continental Type)",
    "Large Munsterlander",
    "Leonberger",
    "Lhasa Apso",
    "Lowchen",
    "Maltese",
    "Maltipoo",
    "Manchester Terrier",
    "Maremma Sheepdog",
    "Mastiff",
    "Miniature Schnauzer",
    "Moodle",
    "Neapolitan Mastiff",
    "Newfoundland",
    "Norfolk Terrier",
    "Norwegian Buhund",
    "Norwegian Elkhound",
    "Norwich Terrier",
    "Nova Scotia Duck Tolling Retriever",
    "Old English Sheepdog",
    "Otterhound",
    "Papillon",
    "Parson Russell Terrier",
    "Pekingese",
    "Peruvian Hairless Dog",
    "Petit Basset Griffon Vendeen",
    "Pharaoh Hound",
    "Pinscher (Miniature)",
    "Pointer",
    "Polish Lowland Sheepdog",
    "Pomeranian",
    "Poodle",
    "Portuguese Podengo",
    "Portuguese Water Dog",
    "Pug",
    "Puli",
    "Pumi",
    "Pyrenean Mastiff",
    "Pyrenean Mountain Dog",
    "Rhodesian Ridgeback",
    "Rottweiler",
    "Russian Black Terrier",
    "Russian Toy (Long Haired, Smooth haired)",
    "Saluki",
    "Samoyed",
    "Schipperke",
    "Schnoodle",
    "Scottish Terrier",
    "Sealyham Terrier",
    "Shar Pei",
    "Shetland Sheepdog",
    "Shiba Inu",
    "Shih Tzu",
    "Shih-Poo",
    "Siberian Husky",
    "Skye Terrier",
    "Sloughi",
    "Soft Coated Wheaten Terrier",
    "Spanish Mastiff",
    "Spanish Water Dog",
    "Spoodle",
    "St Bernard",
    "Staffordshire Bull Terrier",
    "Standard Schnauzer",
    "Sussex Spaniel",
    "Swedish Lapphund",
    "Swedish Vallhund",
    "Tatra Shepherd Dog",
    "Tenterfield Terrier",
    "Tibetan Mastiff",
    "Tibetan Spaniel",
    "Tibetan Terrier",
    "Weimaraner",
    "Welsh Corgi",
    "Welsh Springer Spaniel",
    "Welsh Terrier",
    "West Highland White Terrier",
    "Whippet",
    "White Swiss Shepherd Dog",
    "Wirehaired Slovakian Pointer",
    "Xoloitzcuintle",
    "Yorkshire Terrier",
    "Zuchon"
)
private var CAT_BREED_ARRAY = arrayOf(
    "Mixed Breed",
    "Abyssinian",
    "Aegean",
    "American Bobtail",
    "American Curl",
    "American Ringtail",
    "American Shorthair",
    "American Wirehair",
    "Aphrodite Giant",
    "Arabian Mau",
    "Asian",
    "Asian Semi-longhair",
    "Australian Mist",
    "Balinese",
    "Bambino",
    "Bengal",
    "Birman",
    "Bombay",
    "Brazilian Shorthair",
    "British Longhair",
    "British Shorthair",
    "Burmese",
    "Burmilla",
    "California Spangled",
    "Chantilly-Tiffany",
    "Chartreux",
    "Chausie",
    "Colorpoint Shorthair",
    "Cornish Rex",
    "Cymric, Manx Longhair or Long-haired Manx",
    "Cyprus",
    "Devon Rex",
    "Donskoy",
    "Don Sphynx",
    "Dragon Li",
    "Chinese Li Hua",
    "Dwelf",
    "Egyptian Mau",
    "European Shorthair",
    "Exotic Shorthair",
    "Foldex",
    "German Rex",
    "Havana Brown",
    "Highlander",
    "Himalayan",
    "Colorpoint Persian",
    "Japanese Bobtail",
    "Javanese",
    "Colorpoint Longhair",
    "Kanaani",
    "Khao Manee",
    "Kinkalow",
    "Korat",
    "Korean Bobtail",
    "Korn Ja",
    "Kurilian Bobtail",
    "Kuril Islands Bobtail",
    "Lambkin",
    "LaPerm",
    "Lykoi",
    "Maine Coon",
    "Manx",
    "Mekong Bobtail",
    "Minskin",
    "Napoleon",
    "Munchkin",
    "Nebelung",
    "Norwegian Forest Cat",
    "Ocicat",
    "Ojos Azules",
    "Oregon Rex",
    "Oriental Bicolor",
    "Oriental Longhair",
    "Oriental Shorthair",
    "Persian",
    "Peterbald",
    "Pixie-bob",
    "Ragamuffin",
    "Liebling",
    "Ragdoll",
    "Raas",
    "Russian Blue",
    "Russian White, Russian Black and Russian Tabby",
    "Sam Sawet",
    "Savannah",
    "Scottish Fold",
    "Selkirk Rex",
    "Serengeti",
    "Serrade Petit",
    "Siamese",
    "Siberian",
    "Siberian Forest Cat",
    "Neva Masquerade",
    "Singapura",
    "Snowshoe",
    "Sokoke",
    "Somali",
    "Sphynx",
    "Suphalak",
    "Thai",
    "Wichien Maat",
    "Thai Lilac, Thai Blue Point and Thai Lilac Point",
    "Tonkinese",
    "Toyger",
    "Turkish Angora",
    "Turkish Van",
    "Turkish Vankedisi",
    "Ukrainian Levkoy",
    "York Chocolate"
)


class Fragment_d_BreedSize : Fragment() {
    private val onboardingViewModel: LostPetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d_breed_size, container, false)
        val sp = this.activity?.getSharedPreferences("local_lost_db", Context.MODE_PRIVATE)
        var petType: String? = null
        var items : Array<String> = emptyArray()
        var breed : String?= null
        var size : String?= null

        // find views
        val breedText : TextView = view.findViewById(R.id.petBreedText)
        val sizeText : TextView = view.findViewById(R.id.petSizeText)
        val smallButton: Button = view.findViewById(R.id.Small)
        val mediumButton: Button = view.findViewById(R.id.Medium)
        val largeButton: Button = view.findViewById(R.id.Large)
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val searchableSpinner: SearchableSpinner = view.findViewById(R.id.spinner)

        // set UI
        // spinner text
        searchableSpinner.setDialogTitle("Choose Pet Breed: ")
        searchableSpinner.setDismissText("Dismiss")

        // button colors
        smallButton.setBackgroundColor(Color.parseColor("#FF737E75"))
        mediumButton.setBackgroundColor(Color.parseColor("#FF737E75"))
        largeButton.setBackgroundColor(Color.parseColor("#FF737E75"))

        // get data from sp
        if (sp != null) {
            petType = sp.getString("PET_TYPE", null)
            breed = sp.getString("PET_BREED", null)
            size = sp.getString("PET_SIZE", null)
        }

        // set data by sp
        if (petType != null)
        {
            if (petType == "dog")
            {
                items = DOG_BREED_ARRAY
            }
            else if (petType == "cat")
            {
                items = CAT_BREED_ARRAY
            }
        }
        searchableSpinner.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_dropdown_item, items)

        if (breed != null)
        {
            searchableSpinner.setSelection(items.indexOf(breed))
        }

        if (size != null)
        {
            if (size == "small")
            {
                smallButton.setBackgroundColor(Color.parseColor("#46A556"))
                mediumButton.setBackgroundColor(Color.parseColor("#FF737E75"))
                largeButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            }
            else if (size == "medium")
            {
                smallButton.setBackgroundColor(Color.parseColor("#FF737E75"))
                mediumButton.setBackgroundColor(Color.parseColor("#46A556"))
                largeButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            }
            else
            {
                smallButton.setBackgroundColor(Color.parseColor("#FF737E75"))
                mediumButton.setBackgroundColor(Color.parseColor("#FF737E75"))
                largeButton.setBackgroundColor(Color.parseColor("#46A556"))
            }
        }

        // next listener
        nextButton.setOnClickListener {
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            prevButtonOnClick(it)
        }

        searchableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {
                // Do what you want
                breed = searchableSpinner.selectedItem.toString()
                if (sp != null) {
                    with(sp.edit())
                    {
                        putString("PET_BREED", breed)
                        apply()
                    }
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        // size listeners
        smallButton.setOnClickListener(){
            mediumButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            largeButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            smallButton.setBackgroundColor(Color.parseColor("#46A556"))
            size = "small"

            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_SIZE", size)
                    apply()
                }
            }
        }

        mediumButton.setOnClickListener(){
            smallButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            largeButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            mediumButton.setBackgroundColor(Color.parseColor("#46A556"))
            size = "medium"

            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_SIZE", size)
                    apply()
                }
            }
        }

        largeButton.setOnClickListener(){
            smallButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            mediumButton.setBackgroundColor(Color.parseColor("#FF737E75"))
            largeButton.setBackgroundColor(Color.parseColor("#46A556"))
            size = "large"

            if (sp != null) {
                with(sp.edit())
                {
                    putString("PET_SIZE", size)
                    apply()
                }
            }
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        onboardingViewModel.increaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentColorPattern)
    }

    private fun prevButtonOnClick(view: View) {
        onboardingViewModel.decreaseProgress()
        Navigation.findNavController(view).navigate(R.id.fragmentTypeSex)
    }
}
