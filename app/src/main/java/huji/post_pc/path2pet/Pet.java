package huji.post_pc.path2pet;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String color;
    public Boolean hasCollar;
    public String comments;
    public String name;
    public String email;
    public String phone;
    public Date reportDate;
    public Date lastSeenDate;
    @Exclude
    public List<Uri> images;

    public Pet(String id, String status, String latitude, String longitude, String petType,
               String sex, String breed, String size, String color, Boolean hasColor,
               String comments, String name, String email, String phone, Date date,
               List<Uri> images) {
        this.id = id;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.petType = petType;
        this.sex = sex;
        this.breed = breed;
        this.size = size;
        this.color = color;
        this.hasCollar = hasColor;
        this.comments = comments;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.images = images;
        this.reportDate = date;
        this.lastSeenDate = date;
    }

    public Pet() {
        // fireStore need default constructor
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

    public String getSex(){return sex;}

    public String getSize(){return size;}

    public String getComments() {
        return comments;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName(){return name;}

    public String getEmail(){return email;}

    public String getPhone(){return phone;}

    public Date getLastSeenDate() {
        return lastSeenDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getColor() {
        return color;
    }

    @Exclude
    public List<Uri> getImages() {
        return images;
    }

    public void setLastSeenDate(Date lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }
}
