package huji.post_pc.path2pet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Feed extends AppCompatActivity {
    private Animal dog1 = new Animal("lost", "dog1", "husky", "L", new LatLng(-122.0,23.9), new Date(), "bla", R.drawable.download);
    private Animal dog2 = new Animal("lost", "dog2", "german", "L", new LatLng(-122.0,23.9), new Date(), "bla", R.drawable.download);
    private Animal dog3 = new Animal("found", "dog3", "lab", "L", new LatLng(-122.0,23.9), new Date(), "bla", R.drawable.download);
    private List<Animal> animalList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        readAnimalList();
        RecyclerAdapter adapter = new RecyclerAdapter(animalList);
        recyclerView.setAdapter(adapter);
    }
    private void readAnimalList()
    {
        //todo here we should read the list from fire base
        //we should have 1 list with all animals
        //one list for lost
        //one list for found
        //creating all in one read from firebase
        animalList.add(dog1);
        animalList.add(dog2);
        animalList.add(dog3);
        animalList.add(dog1);
        animalList.add(dog2);
        animalList.add(dog3);
        animalList.add(dog1);
        animalList.add(dog2);
        animalList.add(dog3);
        animalList.add(dog1);
        animalList.add(dog2);
        animalList.add(dog3);
        animalList.add(dog1);
        animalList.add(dog2);
        animalList.add(dog3);
    }
}
