package huji.post_pc.path2pet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Feed extends AppCompatActivity {
    private ArrayList<Pet> petList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private RecyclerAdapter adapter = null;
    String typeFilterFlag = "";
    String statusFilterFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter(petList);
        readAnimalList();
        setContentView(R.layout.feed);

        // find views
        ImageButton filter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerView);

        // set recycle viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        filter.setOnClickListener(v ->{
            // set popup window
            View popupView = LayoutInflater.from(Feed.this).inflate(R.layout.filter_popup, null);
            PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            // find popup views
            Button close = popupView.findViewById(R.id.close);
            CheckBox dogFlag = popupView.findViewById(R.id.dogFilter);
            CheckBox catFlag = popupView.findViewById(R.id.catFilter);
            CheckBox lostFlag = popupView.findViewById(R.id.lostFilter);
            CheckBox foundFilter = popupView.findViewById(R.id.foundFilter);

            // set UI
            dogFlag.setChecked(typeFilterFlag.equals("dog"));
            catFlag.setChecked(typeFilterFlag.equals("cat"));
            lostFlag.setChecked(statusFilterFlag.equals("lost"));
            foundFilter.setChecked(statusFilterFlag.equals("found"));

            close.setOnClickListener(v1-> {
                if (dogFlag.isChecked() && !catFlag.isChecked()) {
                    typeFilterFlag = "dog";
                }
                if(!dogFlag.isChecked() && catFlag.isChecked()){
                    typeFilterFlag = "cat";
                    }
                if (lostFlag.isChecked() && !foundFilter.isChecked()) {
                    statusFilterFlag = "lost";
                }
                if(!lostFlag.isChecked() && foundFilter.isChecked()){
                        statusFilterFlag = "found";
                }
                if(!dogFlag.isChecked() && !catFlag.isChecked())
                {
                    typeFilterFlag = null;
                }
                if (!lostFlag.isChecked() && !foundFilter.isChecked())
                {
                    statusFilterFlag = null;
                }
                doFiltering();
                popupWindow.dismiss();
            });
            popupWindow.showAsDropDown(popupView, 0, 0);
        });
    }

    private void readAnimalList() {
        this.petList = AppPath2Pet.getPetsDB().getAllPets();
        adapter.setPetList(this.petList);
    }

    private void doFiltering() {
        this.petList = filterByType(typeFilterFlag, petList);
        this.petList = filterByStatus(statusFilterFlag, petList);
        adapter = new RecyclerAdapter(this.petList);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        if (adapter.openPopUp !=null) {
            adapter.openPopUp.dismiss();
            adapter.openPopUp = null;
        }
        else {
            super.onBackPressed();
        }
    }

    private ArrayList<Pet> filterByStatus(String status, ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.status.equals(status)){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByType(String petType, ArrayList<Pet> petList)
    {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: petList){
            if(p.petType.equals(petType)){
                filtered.add(p);
            }
        }
        return filtered;
    }

}
