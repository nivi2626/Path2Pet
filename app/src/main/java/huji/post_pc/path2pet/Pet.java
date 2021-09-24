package huji.post_pc.path2pet;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@IgnoreExtraProperties
public class Pet implements Serializable {
    public String id;
    public String status;
    public String latitude;
    public String longitude;
    public String petType;
    public String sex;
    public String breed;
    public String size;
    public List<String> colors;
    public Boolean hasCollar;
    public String comments;
    public String name;
    public String email;
    public String phone;
    public Date reportDate;
    public Date lastSeenDate;
    public int imageNum = 0;
    @Exclude
    public List<Uri> images = new ArrayList<Uri>();

    public Pet(String id, String status, String latitude, String longitude, String petType,
               String sex, String breed, String size, List<String> colors, Boolean hasColor,
               String comments, String name, String email, String phone, Date date,
               List<Uri> images, int imageNum) {
        this.id = id;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.petType = petType;
        this.sex = sex;
        this.breed = breed;
        this.size = size;
        this.colors = colors;
        this.hasCollar = hasColor;
        this.comments = comments;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reportDate = date;
        this.lastSeenDate = date;
        this.images = images;
        this.imageNum = imageNum;
    }

    public Pet() {
    }

    public String getStatus() {
        return status;
    }

    public String getPetType() {
        return petType;
    }

    public String getBreed() {
        return breed;
    }

    public String getSex() {
        return sex;
    }

    public String getSize() {
        return size;
    }

    public String getComments() {
        return comments;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getLastSeenDate() {
        return lastSeenDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public List<String> getColors() {
        return colors;
    }

    public String getId(){return id;}

    public Boolean getHasCollar(){return hasCollar;}

    @Exclude
    public List<Uri> getImages() {
        return images;
    }

    public void setLastSeenDate(Date lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public String getCityByLocation(Context context) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        double lat = Double.parseDouble(this.latitude);
        double longit = Double.parseDouble(this.longitude);
        List<Address> addresses = gcd.getFromLocation(lat, longit, 1);
        String country = "";
        String city = "";
        String retValue = "";
        if (addresses.size() > 0) {
            if (addresses.get(0).getCountryName() != null) {
                country = addresses.get(0).getCountryName();
            }
            if (addresses.get(0).getLocality() != null) {
                city = addresses.get(0).getLocality();
            } else {
                city = addresses.get(0).getAdminArea();
            }
            retValue = String.format("%s, %s", city, country);
        } else {
            retValue = "";
        }
        return retValue;
    }
}


