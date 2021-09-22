package huji.post_pc.path2pet;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PetsDB {
    private ArrayList<Pet> allPets;
    FirebaseFirestore fireStore;
    FirebaseStorage storage;

    public PetsDB() {
        this.allPets = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        initializePetList();
    }

    Pet getPetByPosition(int position) {
        return allPets.get(position);
    }


    private void initializePetList() {
        fireStore.collection(AppPath2Pet.COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Pet pet = document.toObject(Pet.class);
                                allPets.add(pet);
                            }
                        }
                    }
                });
    }

    int getCount() {
        return allPets.size();
    }

    public void addPet(Pet newPet) {
        allPets.add(newPet);
        uploadToFireStore(newPet);
        uploadPetImages(newPet);
    }

    private void uploadPetImages(Pet pet) {
        for (int i = 0; i < pet.images.size(); i++) {
            Uri imageUri = pet.images.get(i);
            uploadImage(pet.id, String.valueOf(i), imageUri);
        }

    }
    private void uploadImage(String ref, String childID, Uri uri) {
        StorageReference storageRef = storage.getReference(ref);
        storageRef.child(childID).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Adding image %s" + childID + "succeed" );
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error adding image", e);
            }
        });
    }


//    private getImages() {
//        StorageReference storageRef = storage.getReference(pet.id);
//        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                @SuppressWarnings("VisibleForTests")
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                DatabaseReference storageRef = storage.push();
//                newPost.child("title").setValue(title_val);
//                newPost.child("desc").setValue(desc_val);
//                newPost.child("image").setValue(downloadUrl.toString());
//                newPost.child("uid").setValue(PreferenceManager
//                        .getDefaultSharedPreferences(PostActivity.this)
//                        .getString("ID", "userid"));
//                mProgress.dismiss();
//                startActivity(new Intent(PostActivity.this, FeedPage.class));
//            }
//        });
//    }
//    else
//
//    {
//        Toast.makeText(PostActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
//    }



        private void uploadToFireStore (Pet pet){
            fireStore.collection(AppPath2Pet.COLLECTION).document(pet.id)
                    .set(pet)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }

    public ArrayList<Pet> getAllPets() {
        return allPets;
    }
}



