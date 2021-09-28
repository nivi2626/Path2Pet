package huji.post_pc.path2pet

import android.net.Uri
import java.util.ArrayList


fun initializeDogBreedList(): ArrayList<Breed> {
    val breedList = ArrayList<Breed>()

    val uri_mixed: Uri = Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_mixed")
    breedList.add(Breed("Mixed Breed", uri_mixed))
    val uri_german_shepherd: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_german_shepherd")
    breedList.add(Breed("German Shepherd", uri_german_shepherd))
    val uri_dog_akita: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_akita")
    breedList.add(Breed("Akita", uri_dog_akita))
    val uri_alaskan_malamute: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_alaskan_malamute")
    breedList.add(Breed("Alaskan Malamute", uri_alaskan_malamute))
    val uri_border_collie: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_border_collie")
    breedList.add(Breed("Border Collie", uri_border_collie))
    val uri_beagle: Uri = Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_beagle")
    breedList.add(Breed("Beagle", uri_beagle))
    val uri_boxer: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_boxer")
    breedList.add(Breed("Boxer", uri_boxer))
    val uri_chihuahua: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_chihuahua")
    breedList.add(Breed("Chihuahua", uri_chihuahua))
    val uri_dalmatian: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_dalmatian")
    breedList.add(Breed("Dalmatian", uri_dalmatian))
    val uri_greyhound: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_greyhound")
    breedList.add(Breed("Greyhound", uri_greyhound))
    val uri_jack_russell_terrier: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_jack_russell_terrier")
    breedList.add(Breed("Jack Russell Terrier", uri_jack_russell_terrier))
    val uri_mastiff: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_mastiff")
    breedList.add(Breed("Mastiff", uri_mastiff))
    val uri_pomeranian: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_pomeranian")
    breedList.add(Breed("Pomeranian", uri_pomeranian))
    val uri_poodle: Uri = Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_poodle")
    breedList.add(Breed("Poodle", uri_poodle))
    val uri_samoyed: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_samoyed")
    breedList.add(Breed("Samoyed", uri_samoyed))
    val uri_shi_tzu: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_shi_tzu")
    breedList.add(Breed("Shih Tzu", uri_shi_tzu))
    val uri_siberian_husky: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_siberian_husky")
    breedList.add(Breed("Siberian Husky", uri_siberian_husky))
    val uri_tibetan_mastiff: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_tibetan_mastiff")
    breedList.add(Breed("Tibetan Mastiff", uri_tibetan_mastiff))
    val uri_tibetan_terrier: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_tibetan_terrier")
    breedList.add(Breed("Tibetan Terrier", uri_tibetan_terrier))
    val uri_yorkshire_terrier: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/dog_yorkshire_terrier")
    breedList.add(Breed("Yorkshire Terrier", uri_yorkshire_terrier))

    return breedList
}

fun initializeCatBreedList(): ArrayList<Breed> {
    val breedList = ArrayList<Breed>()

    val uri_mixed: Uri = Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_mixed")
    breedList.add(Breed("Mixed Breed", uri_mixed))
    val uri_cat_asian: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_asian")
    breedList.add(Breed("Asian", uri_cat_asian))
    val uri_cat_balinese: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_balinese")
    breedList.add(Breed("Balinese", uri_cat_balinese))
    val uri_cat_bengal: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_bengal")
    breedList.add(Breed("Bengal", uri_cat_bengal))
    val uri_cat_chartreux: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_chartreux")
    breedList.add(Breed("Chartreux", uri_cat_chartreux))
    val uri_cat_chausie: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_chausie")
    breedList.add(Breed("Chausie", uri_cat_chausie))
    val uri_cat_cyprus: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_cyprus")
    breedList.add(Breed("Cyprus", uri_cat_cyprus))
    val uri_cat_dwelf: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_dwelf")
    breedList.add(Breed("Dwelf", uri_cat_dwelf))
    val uri_cat_foldex: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_foldex")
    breedList.add(Breed("Foldex", uri_cat_foldex))
    val uri_cat_german_rex: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_german_rex")
    breedList.add(Breed("German Rex", uri_cat_german_rex))
    val uri_cat_highlander: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_highlander")
    breedList.add(Breed("Highlander", uri_cat_highlander))
    val uri_cat_himalayan: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_himalayan")
    breedList.add(Breed("Himalayan", uri_cat_himalayan))
    val uri_cat_lambkin: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_lambkin")
    breedList.add(Breed("Lambkin", uri_cat_lambkin))
    val uri_cat_laperm: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_laperm")
    breedList.add(Breed("LaPerm", uri_cat_laperm))
    val uri_cat_minskin: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_minskin")
    breedList.add(Breed("Minskin", uri_cat_minskin))
    val uri_cat_napoleon: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_napoleon")
    breedList.add(Breed("Napoleon", uri_cat_napoleon))
    val uri_cat_persian: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_persian")
    breedList.add(Breed("Persian", uri_cat_persian))
    val uri_cat_siberian: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_siberian")
    breedList.add(Breed("Siberian", uri_cat_siberian))
    val uri_cat_thai: Uri = Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_thai")
    breedList.add(Breed("Thai", uri_cat_thai))
    val uri_cat_york_chocolate: Uri =
        Uri.parse("android.resource://huji.post_pc.path2pet/drawable/cat_york_chocolate")
    breedList.add(Breed("York Chocolate", uri_cat_york_chocolate))

    return breedList
}
