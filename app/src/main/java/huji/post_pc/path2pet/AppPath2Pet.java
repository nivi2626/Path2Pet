package huji.post_pc.path2pet;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class AppPath2Pet extends Application {
    private static PetsDB petsDB = null;
    private static FirebaseStorage storage = null;
    private static FirebaseFirestore fireStore = null;

    public static MutableLiveData<Boolean> loadingFlag = new MutableLiveData<Boolean>();
    public static Boolean workerWorked = false;
    public static Boolean userNotified = false;
    public static List<Data> bestMatches = new ArrayList<>();

    // constants:
    // fireStore
    final static String COLLECTION = "Pets";
    final public static String LOST = "Lost";
    final public static String FOUND = "Found";

    // workManager keys
    final public static String WM_USER_LOST_PETS = "User Lost Pets";
    final public static String WM_ON_SUCCESS = "Match Success";
    final public static String WM_ON_FAILURE = "Match Fail";
    final public static Double MIN_MATCH_VAL = 0.70;


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
    final public static String SP_FOUND = "Found";
    final public static String SP_LOST = "Lost";

    // sp lost pets
    final public static String SP_MY_LOST = "my_lost_pets";
    final public static String SP_LOST_ID = "ID";

    // pet data
    final public static String TYPE_DOG = "Dog";
    final public static String TYPE_CAT = "Cat";
    final public static String SIZE_SMALL = "Small";
    final public static String SIZE_MEDIUM = "Medium";
    final public static String SIZE_LARGE = "Large";
    final public static String SEX_FEMALE = "Female";
    final public static String SEX_MALE = "Male";
    final public static String COLOR_WHITE = "White";
    final public static String COLOR_BLACK = "Black";
    final public static String COLOR_BROWN = "Brown";
    final public static String COLOR_GRAY = "Gray";
    final public static String COLOR_GINGER = "Ginger";
    final public static String COLOR_BLOND = "Blond";
    final public static String COLLAR_WITH = "With";
    final public static String COLLAR_WITHOUT = "Without";

    // others
    final public static String CHOSEN_COLOR = "#67CD8B";
    final public static String NOT_CHOSEN_COLOR = "#909190";
    final public static String SP_DELIMITER = "#####";


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
