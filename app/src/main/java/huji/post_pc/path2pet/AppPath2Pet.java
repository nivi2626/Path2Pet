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
    final static String SP_DETAILS = "Details";

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
    final static String URI_IMAGES_DELIMITER = "#####";


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        appInstance = this;
        petsDB = new PetsDB();
    }

    public static PetsDB getPetsDB() {
        return petsDB;
    }

//    public static AppPath2Pet getAppInstance() {
//        return appInstance;
//    }




}
