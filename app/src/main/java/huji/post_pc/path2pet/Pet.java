package huji.post_pc.path2pet;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Pet
{
    public String petType;
    public String status;
    public String breed;
    public String size;
    public LatLng latLng;
    public Date reportDate;
    public Date lastSeenDate;
    public String comments;
    public String color;
    public ArrayList<Integer> images;

    public Pet(String status, String petType, String breed, String size, String color, LatLng latLng, Date date,
               String comments, ArrayList<Integer> images) {
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

    public Integer getFirstImage() {
        if (images.size()>0){
            return images.get(0);
        }
        return null;
    }

    public void setLastSeenDate(Date lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }
}