package huji.post_pc.path2pet;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
    public ArrayList<Integer> images;

    public Pet(String id, String status, String petType, String breed, String size, String color,
               LatLng latLng, Date date, String comments, ArrayList<Integer> images) {
        this.id = id;
        this.petType = petType;
        this.status = status;
        this.breed = breed;
        this.size = size;
        this.latLng = latLng;
        this.reportDate = date;
        this.lastSeenDate = date;
        this.color = color;

        if (comments != null ){
            this.comments = comments;
        }
        if (images!=null){
            this.images = images;
        }
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

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setLastSeenDate(Date lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }
}
