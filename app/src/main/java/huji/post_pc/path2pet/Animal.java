package huji.post_pc.path2pet;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Animal
{
    String status;
    String type;
    String breed;
    String size;
    LatLng latLng;
    Date date;
    String comments;
    int imageURL;

    public Animal(String stsus, String type, String category, String address, LatLng latLng, Date date, String description, int imageUrl)
    {
        this.status = stsus;
        this.type = type;
        this.breed = category;
        this.size = address;
        this.latLng = latLng;
        this.date = date;
        this.comments = description;
        this.imageURL = imageUrl;
    }
    public String getStatus()
    {
        return status;
    }
    public String getType()
    {
        return type;
    }
    public LatLng getLatLng()
    {
        return latLng;
    }
    public Date getDate()
    {
        return date;
    }
    public int getImageURL()
    {
        return imageURL;
    }

}
