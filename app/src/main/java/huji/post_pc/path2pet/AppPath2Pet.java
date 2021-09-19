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
    final static String SP_PHOTO = "Photos";
    final static String SP_LOCATION = "Location";
    final static String SP_TYPE = "Type";
    final static String SP_SEX = "Sex";
    final static String SP_BREED = "Breed";
    final static String SP_SIZE = "Size";
    final static String SP_COLOR = "Color";
    final static String SP_COLLAR = "Collar";
    final static String SP_COMMENTS = "Comments";
    final static String SP_DETAILS = "Details";

    // pet data
    final static String TYPE_DOG = "dog";
    final static String TYPE_CAT = "cat";
    final static String SIZE_SMALL = "small";
    final static String SIZE_MEDIUM = "medium";
    final static String SIZE_LARGE = "large";
    final static String SEX_FEMALE = "female";
    final static String SEX_MALE = "MALE";


    // others
    final static String CHOSEN_COLOR =  "#46A556";
    final static String NOT_CHOSEN_COLOR = "#909190";


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
