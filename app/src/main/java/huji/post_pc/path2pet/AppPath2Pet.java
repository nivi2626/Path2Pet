package huji.post_pc.path2pet;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AppPath2Pet extends Application {
    private static PetsDB petsDB = null;
    private static FirebaseStorage storage = null;
    private static FirebaseFirestore fireStore = null;
    public static Boolean loadingFlag = true;
    // constants:
    // fireStore
    final static String COLLECTION = "Pets";

    // sp keys
    final static String SP_PHOTOS = "Photos";
    final static String SP_LATITUDE = "Latitude";
    final static String SP_LONGITUDE = "Longitude";
    final static String SP_TYPE = "Type";
    final static String SP_SEX = "Sex";
    final static String SP_BREED = "Breed";
    final static String SP_SIZE = "Size";
    final static String SP_COLOR = "Color";
    final static String SP_COLLAR = "Collar";
    final static String SP_COMMENTS = "Comments";
    final static String SP_NAME = "Name";
    final static String SP_EMAIL = "Email";
    final static String SP_PHONE = "Phone";

    // sp lost pets
    final static String SP_LOST_ID = "ID";

    // pet data
    final static String TYPE_DOG = "Dog";
    final static String TYPE_CAT = "Cat";
    final static String SIZE_SMALL = "Small";
    final static String SIZE_MEDIUM = "Medium";
    final static String SIZE_LARGE = "Large";
    final static String SEX_FEMALE = "Female";
    final static String SEX_MALE = "Male";


    // others
    final static String CHOSEN_COLOR = "#67CD8B";
    final static String NOT_CHOSEN_COLOR = "#909190";
    final static String SP_DELIMITER = "#####";


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
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
