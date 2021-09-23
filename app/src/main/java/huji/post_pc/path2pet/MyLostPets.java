package huji.post_pc.path2pet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyLostPets extends AppCompatActivity {
    private ArrayList<Pet> lostPetList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyLostPetsAdapter adapter = null;
    private SharedPreferences spLostPets;
    // TODO - change "my_lost_pets" to constant string, add it to LostPetProcess class


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spLostPets = getSharedPreferences("my_lost_pets", Context.MODE_PRIVATE);
        setContentView(R.layout.my_lost_pets);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyLostPetsAdapter();
        readAnimalListByUserID();
        recyclerView.setAdapter(adapter);
    }

    private void readAnimalListByUserID() {
        List<Pet> pets = AppPath2Pet.getPetsDB().getAllPets();
        List<Pet> userPets = new ArrayList<Pet>();
        List<String> userLostPetsIDs = Arrays.asList(spLostPets.getString(AppPath2Pet.SP_LOST_ID, "").split(AppPath2Pet.SP_DELIMITER));
        if (userLostPetsIDs.size() == 0) {
            return;
        }
        for (Pet pet : pets) {
            if (userLostPetsIDs.contains(pet.getId()))
            {
                userPets.add(pet);
            }
        }
        adapter.setPetList(userPets);
    }


//    @Override
//    public void onBackPressed() {
//        if (adapter.openPopUp !=null) {
//            adapter.openPopUp.dismiss();
//            adapter.openPopUp = null;
//        } else
//        {
//            super.onBackPressed();
//        }
//
//    }
}
