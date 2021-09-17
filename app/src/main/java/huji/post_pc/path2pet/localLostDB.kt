package huji.post_pc.path2pet

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.io.Serializable

class localLostDB internal constructor(context: Context) : Serializable {
    private var myContext = context
    private var sp: SharedPreferences =
        context.getSharedPreferences("local_db_orders", Context.MODE_PRIVATE)
    var gson = Gson()

    fun initialize_db() {
        /**
         * initializes the database with default data
         */
        with (sp.edit()) {
            putString("IMAGE_DATA",null)
            putString("LOCATION_LAT", null)
            putString("LOCATION_LON", null)
            putString("LOCATION_PROVIDER", null)
            putString("PET_TYPE", null)
            putString("PET_GENDER", null)
            putString("PET_BREED", "")
            putString("PET_SIZE", null)
            putString("PET_COLOR", null)
            putString("PET_PATTERN", null)
            putBoolean("PET_COLLAR", false)
            putString("COLLAR_COLOR", null)
            putString("COMMENTS", "")
            putString("NAME", "")
            putString("EMAIL", "")
            putString("PHONE_NUMBER", "")
            apply()
        }
    }

    fun edit_location(
        location: String // Todo - change to location object
    )
    /**
     * edit the location and saves in sp
     */
    {
        with (sp.edit()) {
            if (location == null) {
                remove("LOCATION_LAT")
                remove("LOCATION_LON")
                remove("LOCATION_PROVIDER")
            } else {
                putString("LOCATION_LAT", "latitude_check") // change to get location latitude
                putString("LOCATION_LON", "longitude_check") // change to get location longitude
                putString("LOCATION_PROVIDER", "provider_check") // change to get location provider
                apply()
// TODO - values of original data in stackoverflow answer:
//                putString("LOCATION_LAT", String.valueOf(location.getLatitude())).apply();
//                putString("LOCATION_LON", String.valueOf(location.getLongitude())).apply();
//                putString("LOCATION_PROVIDER", location.getProvider()).apply();
            }
        }
    }

    fun editImage(encodedImage: String)
    /**
     * Base64 image - TODO - change type from String to Base64 Image if there is one
     */
    {
        with(sp.edit())
        {
            putString("IMAGE_DATA",encodedImage);
            apply()
        }
    }

    fun editTypeAndGender(petType: String, petGender: String)
    /**
     * edits the pet's type and gender and saves to sp
     */
    {
        with(sp.edit())
        {
            putString("PET_TYPE", petType)
            putString("PET_GENDER", petGender)
            apply()
        }
    }

    fun editBreedAndSize(petBreed: String, petSize: String)
    /**
     * edits the pet's breed and size and saves to sp
     */
    {
        with(sp.edit())
        {
            putString("PET_BREED", petBreed)
            putString("PET_SIZE", petSize)
            apply()
        }
    }

    fun editColorAndPattern(petColor:String, petPattern:String)
    /**
     * edits the pet's fur color and fur pattern and saves to sp
     */
    {
        with(sp.edit())
        {
            putString("PET_COLOR", petColor)
            putString("PET_PATTERN", petPattern)
            apply()
        }
    }

    fun editCollarInfo(Collar:Boolean, CollarColor:String)
    /**
     * edits the pet's collar information and saves to sp
     */
    {
        with(sp.edit())
        {
            putBoolean("PET_COLLAR", Collar)
            putString("COLLAR_COLOR", CollarColor)
            apply()
        }
    }

    fun editComments(Comments:String)
    /**
     * edits the user comments and saves to sp
     */
    {
        with(sp.edit())
        {
            putString("COMMENTS", Comments)
            apply()
        }
    }

    fun editUserInfo(Name:String, Email:String, Phone:String)
            /**
             * edits the user comments and saves to sp
             */
    {
        with(sp.edit())
        {
            putString("NAME", Name)
            putString("EMAIL", Email)
            putString("PHONE_NUMBER", Phone)
            apply()
        }
    }

    fun clearSP()
    /**
     * clears the sharedPreferences object
     */
    {
        with(sp.edit())
        {
            clear()
            apply()
        }
    }
}