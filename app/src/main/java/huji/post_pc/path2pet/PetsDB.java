package huji.post_pc.path2pet;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PetsDB {
    private ArrayList<Pet> allPets;
    FirebaseFirestore fireStore;
//    private AppPath2Pet appInstance;

    public PetsDB(){
//        appInstance = AppPath2Pet.getAppInstance();
        this.allPets = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        initialize();
    }

    Pet getPetByPosition(int position) {
        return allPets.get(position);
    }


    private void initialize() {
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

    public void addDoctor(Pet newPet) {
        allPets.add(newPet);
        uploadToFireStore(newPet);
    }


    private void uploadToFireStore(Pet pet){
        FirebaseFirestore.getInstance().collection(AppPath2Pet.COLLECTION).document(pet.id).set(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, successMsg, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, failureMsg, Toast.LENGTH_LONG).show();
                    }
                });
    }


}

