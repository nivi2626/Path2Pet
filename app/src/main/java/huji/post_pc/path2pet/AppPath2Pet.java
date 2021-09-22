package huji.post_pc.path2pet;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AppPath2Pet extends Application {
    private static AppPath2Pet appInstance = null;
    private static PetsDB petsDB = null;

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
    final public static String SP_COLOR = "Color";
    final public static String SP_COLLAR = "Collar";
    final public static String SP_COMMENTS = "Comments";
    final public static String SP_NAME = "Name";
    final public static String SP_EMAIL = "Email";
    final public static String SP_PHONE = "Phone";

    // sp lost pets
    final public static String SP_LOST_ID = "ID";

    // pet data
    final public static String TYPE_DOG = "dog";
    final public static String TYPE_CAT = "cat";
    final public static String SIZE_SMALL = "small";
    final public static String SIZE_MEDIUM = "medium";
    final public static String SIZE_LARGE = "large";
    final public static String SEX_FEMALE = "female";
    final public static String SEX_MALE = "MALE";


    // others
    final public static String CHOSEN_COLOR = "#67CD8B";
    final public static String NOT_CHOSEN_COLOR = "#909190";
    final public static String SP_DELIMITER = "#####";


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        petsDB = new PetsDB();
        FirebaseApp.initializeApp(this);
    }

    public static PetsDB getPetsDB() {
        return petsDB;
    }

//    public static AppPath2Pet getAppInstance() {
//        return appInstance;
//    }




}
