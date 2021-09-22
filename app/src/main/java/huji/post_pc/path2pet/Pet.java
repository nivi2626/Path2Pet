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
    public String petType;
    public String sex;
    public String status;
    public String breed;
    public String size ;
    public LatLng latLng;
    public Date reportDate;
    public Date lastSeenDate;
    public String comments;
    public String color;
    @Exclude public List<Uri> images;

    public Pet(String id, String status, String petType, String breed, String size, String color,
               LatLng latLng, Date date, String comments,  List<Uri> images) {
        this.id = id;
        this.status = status;
        this.images = images;
        this.latLng = latLng;
        this.petType = petType;
        this.sex = "MALE"; // TODO - ADD ATTRIBUTE
        this.breed = breed;
        this.size = size;
        this.color = color;
        this.comments = comments;
        this.reportDate = date;
        this.lastSeenDate = date;
    }

    public Pet(){
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

    public String getComments() {
        return comments;
    }

    public LatLng getLatLng() {
        return latLng;
    }

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
