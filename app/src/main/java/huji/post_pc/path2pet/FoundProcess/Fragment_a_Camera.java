package huji.post_pc.path2pet.FoundProcess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import huji.post_pc.path2pet.AppPath2Pet;
import huji.post_pc.path2pet.R;


public class Fragment_a_Camera extends Fragment {
    FoundPetProcess foundPetActivityInstance;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.foundPetActivityInstance = (FoundPetProcess) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.found_fragment_a_camera, container, false);

        // find views
        Button nextButton = view.findViewById(R.id.next);
        Button prevButton = view.findViewById(R.id.previous);
        Button galleryButton = view.findViewById(R.id.gallery_button);

        nextButton.setOnClickListener(v -> {
            String strImages = ""; // todo - get and parse images
            foundPetActivityInstance.sp.edit().putString(AppPath2Pet.SP_PHOTOS, strImages).apply();
            foundPetActivityInstance.progressBar.incrementProgressBy(1);
            Navigation.findNavController(view).navigate(R.id.fragment_b_Map);
        });



        return view;



    }



}