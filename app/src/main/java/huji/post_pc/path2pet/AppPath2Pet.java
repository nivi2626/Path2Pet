package huji.post_pc.path2pet;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AppPath2Pet extends Application {
    private static AppPath2Pet appInstance = null;
    private static PetsDB petsDB = null;

    // constants
    final static String COLLECTION = "Pets";
    // todo - here?


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
