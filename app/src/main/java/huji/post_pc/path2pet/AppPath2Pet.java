package huji.post_pc.path2pet;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AppPath2Pet extends Application {
    private static PetsDB petsDB = null;
    private static FirebaseStorage storage = null;
    private static FirebaseFirestore fireStore = null;

    public static MutableLiveData<Boolean> loadingFlag = new MutableLiveData<Boolean>();

    // constants:
    // fireStore
    final static String COLLECTION = "Pets";

    // sp keys
    final public static String SP_PHOTOS = "Photos";
    final public static String SP_LATITUDE = "Latitude";
    final public static String SP_LONGITUDE = "Longitude";
    final public static String SP_TYPE = "Type";
    final public static String SP_SEX = "Sex";
    final public static String SP_BREED = "Breed";
    final public static String SP_SIZE = "Size";
    final public static String SP_COLORS = "Colors";
    final public static String SP_COLLAR = "Collar";
    final public static String SP_COMMENTS = "Comments";
    final public static String SP_NAME = "Name";
    final public static String SP_EMAIL = "Email";
    final public static String SP_PHONE = "Phone";

    // sp lost pets
    final public static String SP_LOST_ID = "ID";

    // pet data
    final static String TYPE_DOG = "Dog";
    final static String TYPE_CAT = "Cat";
    final static String SIZE_SMALL = "Small";
    final static String SIZE_MEDIUM = "Medium";
    final static String SIZE_LARGE = "Large";
    final static String SEX_FEMALE = "Female";
    final static String SEX_MALE = "Male";
    final static String COLOR_WHITE = "White";
    final static String COLOR_BLACK = "Black";
    final static String COLOR_BROWN = "Brown";
    final static String COLOR_GRAY = "Gray";
    final static String COLOR_GINGER = "Ginger";
    final static String COLOR_BLOND = "Blond";
    final static String COLLAR_WITH = "With";
    final static String COLLAR_WITHOUT = "Without";



    // others
    final static String CHOSEN_COLOR = "#67CD8B";
    final static String NOT_CHOSEN_COLOR = "#909190";
    final static String SP_DELIMITER = "#####";


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        loadingFlag.setValue(true);
        fireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        petsDB = new PetsDB();
    }


    public static PetsDB getPetsDB() {
        return petsDB;
    }

    public static FirebaseFirestore getFireStore() {
        return fireStore;
    }

    public static FirebaseStorage getStorage() {
        return storage;
    }

}
