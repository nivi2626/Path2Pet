package huji.post_pc.path2pet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Switch;
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
    private ArrayList<Pet> petList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter = null;
    String TypeFilterFlag = null;
    String StatusFilterFlag = null;

    private Pet dog1 = new Pet("1", "lost", "dog", "husky", "large",
            "blond",new LatLng(-22.0,33.9), new Date(), "bla", null);
    private Pet dog2 = new Pet("2","lost", "cat", "german", "small",
            "white", new LatLng(-125.0,63.9), new Date(), "seems tied", null);
    private Pet dog3 = new Pet("3", "found", "dog", "mixed", "medium",
            "black", new LatLng(-122.0,23.9), new Date(), null, null);
    private Context context;
    public PopupWindow openPopUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        readAnimalList();
        adapter = new RecyclerAdapter(petList);
        recyclerView.setAdapter(adapter);
        this.context = recyclerView.getContext();
        ImageButton filter = findViewById(R.id.filter);

        filter.setOnClickListener(v ->{
            View popupView = LayoutInflater.from(Feed.this).inflate(R.layout.filter_popup, null);
            PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            openPopUp = popupWindow;

            Button close = popupView.findViewById(R.id.close);
            CheckBox dogFlag = popupView.findViewById(R.id.dogFilter);
            dogFlag.setChecked(TypeFilterFlag=="dog");
            CheckBox catFlag = popupView.findViewById(R.id.catFilter);
            catFlag.setChecked(TypeFilterFlag == "cat");
            CheckBox lostFlag = popupView.findViewById(R.id.lostFilter);
            lostFlag.setChecked(StatusFilterFlag == "lost");
            CheckBox foundFilter = popupView.findViewById(R.id.foundFilter);
            foundFilter.setChecked(StatusFilterFlag == "found");
            readAnimalList();
            close.setOnClickListener(v1-> {
                if (dogFlag.isChecked() && !catFlag.isChecked()) {
                    TypeFilterFlag = "dog";
                }
                if(!dogFlag.isChecked() && catFlag.isChecked()){
                        TypeFilterFlag = "cat";
                    }
                if (lostFlag.isChecked() && !foundFilter.isChecked()) {
                    StatusFilterFlag = "lost";
                }
                if(!lostFlag.isChecked() && foundFilter.isChecked()){
                        StatusFilterFlag = "found";
                }
                if(!dogFlag.isChecked() && !catFlag.isChecked())
                {
                    TypeFilterFlag = null;
                }
                if (!lostFlag.isChecked() && !foundFilter.isChecked())
                {
                    StatusFilterFlag = null;
                }
                checkFiltering();
                popupWindow.dismiss();
                openPopUp = null;
            });
            popupWindow.showAsDropDown(popupView, 0, 0);

        });

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

    private void checkFiltering()
    {
        if(TypeFilterFlag== "dog")
        {
            petList = filterByDog(petList);
        }
        else{
            if(TypeFilterFlag == "cat")
            {
                petList = filterByCat(petList);
            }
        }
        if(StatusFilterFlag == "lost")
        {
            petList = filterByLost(petList);
        }
        else
        {
            if(StatusFilterFlag == "found")
            {
                petList = filterByFound(petList);
            }
        }
        adapter = new RecyclerAdapter(petList);
        recyclerView.setAdapter(adapter);
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

    private ArrayList<Pet> filterByLost(ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.status.equals("lost")){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByFound(ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.status.equals("found")){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByDog(ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.petType.equals("dog")){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByCat(ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.petType.equals("cat")){
                filtered.add(p);
            }
        }
        return filtered;
    }

}
