package huji.post_pc.path2pet;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    public int imageNum;
    @Exclude
    public List<Uri> images = new ArrayList<Uri>();

    public Pet(String id, String status, String latitude, String longitude, String petType,
               String sex, String breed, String size, String color, Boolean hasColor,
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
        this.color = color;
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
        // fireStore need default constructor
        // get images from FireBase
//        StorageReference storageRef = AppPath2Pet.getStorage().getReference().child(this.id + "/");
//        StorageReference storageRef = AppPath2Pet.getStorage().getReference().child(this.id + "/");
//        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for (StorageReference item : listResult.getItems()) {
//                    images.add(Uri.parse(item.toString()));
//                }
//            }
//        });
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

//    void setImages() {
//        final Uri[] uriL = {null};
//        StorageReference storageRef = AppPath2Pet.getStorage().getReference(this.id);
//        storageRef.listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        for (StorageReference item : listResult.getItems()) {
//                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    // Do whatever you need here.
//                                    uriL[0] = uri;
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle any errors
//                                }
//                            });
//                        }
//                    }
//                });
//    }
}




//        for (int i = 0; i < this.imageNum; i++) {
//            storageRef.child(String.valueOf(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    final Uri firebaseImage = uri;
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });
//            this.images.add()

//        this.images = Arrays.asList(firebaseImages);


