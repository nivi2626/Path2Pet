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
import java.util.Arrays;
import java.util.List;


public class Feed extends AppCompatActivity {
    private ArrayList<Pet> petList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private RecyclerAdapter adapter = null;
    private PopupWindow popupWindow = null;
    private List<String> statusList = new ArrayList();
    private List<String> typeList = new ArrayList();
    private List<String> colorList = new ArrayList();
    private List<String> sizeList = new ArrayList();
    private List<String> sexList = new ArrayList();
    private List<String> collarList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter(petList);
        updatePetsList();
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
            this.popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            // find popup views
            Button apply = popupView.findViewById(R.id.apply);
            CheckBox lost = popupView.findViewById(R.id.lostFilter);
            CheckBox found = popupView.findViewById(R.id.foundFilter);
            CheckBox dog = popupView.findViewById(R.id.dogFilter);
            CheckBox cat = popupView.findViewById(R.id.catFilter);
            CheckBox black = popupView.findViewById(R.id.black);
            CheckBox brown = popupView.findViewById(R.id.brown);
            CheckBox blond = popupView.findViewById(R.id.blonde);
            CheckBox ginger = popupView.findViewById(R.id.ginger);
            CheckBox white = popupView.findViewById(R.id.white);
            CheckBox small = popupView.findViewById(R.id.small);
            CheckBox medium = popupView.findViewById(R.id.medium);
            CheckBox large = popupView.findViewById(R.id.large);
            CheckBox female = popupView.findViewById(R.id.female);
            CheckBox male = popupView.findViewById(R.id.male);
            CheckBox withCollar = popupView.findViewById(R.id.with_collar);
            CheckBox withoutCollar = popupView.findViewById(R.id.without_collar);

            // set UI
            for (CheckBox i: Arrays.asList(lost, found)) {
                i.setChecked(statusList.contains(i.getText().toString()));
            }
            for (CheckBox i: Arrays.asList(cat, dog)) {
                i.setChecked(typeList.contains(i.getText().toString()));
            }
            for (CheckBox i: Arrays.asList(black, brown, white, ginger, blond)) {
                i.setChecked(colorList.contains(i.getText().toString()));
            }
            for (CheckBox i: Arrays.asList(small, medium, large)) {
                i.setChecked(sizeList.contains(i.getText().toString()));
            }
            for (CheckBox i: Arrays.asList(female, male)) {
                i.setChecked(sexList.contains(i.getText().toString()));
            }
            for (CheckBox i: Arrays.asList(withCollar, withoutCollar)) {
                i.setChecked(collarList.contains(i.getText().toString()));
            }

            apply.setOnClickListener(v1-> {
                statusList = new ArrayList();
                for (CheckBox i: Arrays.asList(lost, found)) {
                    if (i.isChecked()) {
                        statusList.add(i.getText().toString());
                    }
                }

                typeList = new ArrayList();
                for (CheckBox i: Arrays.asList(dog, cat)) {
                    if (i.isChecked()) {
                        typeList.add(i.getText().toString());
                    }
                }
                colorList = new ArrayList();
                for (CheckBox i: Arrays.asList(black, brown, blond, ginger, white)) {
                    if (i.isChecked()) {
                        colorList.add(i.getText().toString());
                    }
                }

                sizeList = new ArrayList();
                for (CheckBox i: Arrays.asList(small, medium, large)) {
                    if (i.isChecked()) {
                        sizeList.add(i.getText().toString());
                    }
                }

                sexList = new ArrayList();
                for (CheckBox i: Arrays.asList(female, male)) {
                    if (i.isChecked()) {
                        sexList.add(i.getText().toString());
                    }
                }

                collarList = new ArrayList();
                for (CheckBox i: Arrays.asList(withCollar, withoutCollar)) {
                    if (i.isChecked()) {
                        collarList.add(i.getText().toString());
                    }
                }
                doFiltering(statusList, typeList, colorList, sizeList, sexList, collarList);
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(popupView, 0, 0);
        });
    }

    private void updatePetsList() {
        this.petList = AppPath2Pet.getPetsDB().getAllPets();
        this.adapter.setPetList(this.petList);
    }

    private void doFiltering(List<String> statusList, List<String> typeList, List<String> colorList,
                             List<String> sizeList, List<String> sexList, List<String> collarList) {
        ArrayList<Pet> filtered = this.petList;
        if (!statusList.isEmpty()) {
            filtered = filterByStatus(statusList, filtered);
        }
        if (!typeList.isEmpty()) {
            filtered = filterByType(typeList, filtered);
        }
        if (!colorList.isEmpty()) {
            filtered = filterByColor(colorList, filtered);
        }
        if (!sizeList.isEmpty()) {
            filtered = filterBySize(sizeList, filtered);
        }
        if (!sexList.isEmpty()) {
            filtered = filterBySex(sexList, filtered);
        }
//        if (!collarList.isEmpty()) {
//            filtered = filterByCollar(collarList, filtered);
//        }

        // set items in adapter
        adapter.setPetList(filtered);
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

    private ArrayList<Pet> filterByStatus(List<String> statusList, ArrayList<Pet> pets) {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: pets){
            if (statusList.contains(p.status)){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByType(List<String> typesList, ArrayList<Pet> pets) {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: pets){
            if (typesList.contains(p.petType)){
                filtered.add(p);
            }
        }
        return filtered;
    }

    // todo - uncomment when Pet have a collar attribute
//    private ArrayList<Pet> filterByCollar(List<String> collarList, ArrayList<Pet> pets) {
//        ArrayList<Pet> filtered = new ArrayList<>();
//        for(Pet p: pets){
//            if (collarList.isEmpty() || collarList.contains(p.collar)){
//                filtered.add(p);
//            }
//        }
//        return filtered;
//    }

    private ArrayList<Pet> filterBySex(List<String> sexList, ArrayList<Pet> pets) {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: pets){
            if (sexList.contains(p.sex)){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterBySize(List<String> sizeList, ArrayList<Pet> pets) {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: pets){
            if (sizeList.contains(p.size)){
                filtered.add(p);
            }
        }
        return filtered;
    }

    private ArrayList<Pet> filterByColor(List<String> colorList, ArrayList<Pet> pets) {
        ArrayList<Pet> filtered = new ArrayList<>();
        for( Pet p: pets){
            if (colorList.contains(p.color)){
                filtered.add(p);
            }
        }
        return filtered;
    }

}
