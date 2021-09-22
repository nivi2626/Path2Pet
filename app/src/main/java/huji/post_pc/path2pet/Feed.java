package huji.post_pc.path2pet;

import android.os.Bundle;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class Feed extends AppCompatActivity {
    private List<Pet> petList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter = null;

    private Pet dog1 = new Pet("1", "lost", "dog", "husky", "large",
            "blond","32.109333", "34.855499", new Date(), "bla", null);
    private Pet dog2 = new Pet("2","lost", "cat", "german", "small",
            "white", "34.855499", "32.109333", new Date(), "seems tired", null);
    private Pet dog3 = new Pet("3", "found", "dog", "mixed", "medium",
            "black", "34.855499", "34.855499", new Date(), null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        readAnimalList();
        adapter = new RecyclerAdapter(petList);
        recyclerView.setAdapter(adapter);
    }

    private void readAnimalList()
    {
        //todo here we should read the list from firebase
        //we should have 1 list with all animals
        //one list for lost
        //one list for found
        //creating all in one read from firebase
        petList.add(dog1);
        petList.add(dog2);
        petList.add(dog3);
        petList.add(dog1);
        petList.add(dog2);
        petList.add(dog3);
        petList.add(dog1);
        petList.add(dog2);
        petList.add(dog3);
        petList.add(dog1);
        petList.add(dog2);
        petList.add(dog3);
        petList.add(dog1);
        petList.add(dog2);
        petList.add(dog3);
    }

    @Override
    public void onBackPressed() {
        if (adapter.openPopUp !=null) {
            adapter.openPopUp.dismiss();
            adapter.openPopUp = null;
        } else
            {
            super.onBackPressed();
        }

    }


}
