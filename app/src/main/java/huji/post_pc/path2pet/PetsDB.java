package huji.post_pc.path2pet;

import android.content.Context;
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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PetsDB {
    private ArrayList<Pet> allPets;
    int count;
    FirebaseFirestore fireStore;
    FirebaseStorage storage;

    public PetsDB() {
        this.allPets = new ArrayList<>();
        fireStore = AppPath2Pet.getFireStore();
        storage = AppPath2Pet.getStorage();
        initializePetList();
    }

    private void initializePetList() {
        fireStore.collection(AppPath2Pet.COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Pet pet = document.toObject(Pet.class);
                                if (pet != null) {
                                    pet.images = new ArrayList<Uri>();
                                    StorageReference storageRef = AppPath2Pet.getStorage().getReference(pet.id);
                                    storageRef.listAll()
                                            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                                @Override
                                                public void onSuccess(ListResult listResult) {
                                                    for (StorageReference item : listResult.getItems()) {
                                                        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                pet.images.add(uri);
                                                                count += 1;
                                                                if (count == task.getResult().size()) {
                                                                    AppPath2Pet.loadingFlag = false;
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
                                allPets.add(pet);
                            }
                        }
                    }
                });
    }

    public void addPet(Pet newPet) {
        allPets.add(newPet);
        uploadToFireStore(newPet);
        uploadPetImages(newPet);
    }

    private void uploadPetImages(Pet pet) {
        for (int i = 0; i < pet.getImages().size(); i++) {
            Uri imageUri = pet.getImages().get(i);
            uploadImage(pet.id, String.valueOf(i), imageUri);
        }

    }

    private void uploadImage(String ref, String childID, Uri uri) {
        StorageReference storageRef = storage.getReference(ref);
        storageRef.child(childID).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Adding image %s" + childID + "succeed");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error adding image", e);
            }
        });
    }


    private void uploadToFireStore(Pet pet) {
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


