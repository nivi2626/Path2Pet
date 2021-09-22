package huji.post_pc.path2pet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * Adapter for feed
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AnimalViewHolder> {
    private List<Pet> petList = null;
    private Context context;
    public PopupWindow openPopUp = null;

    public RecyclerAdapter(List<Pet> petList) {
        if (this.petList == null) {
            this.petList = petList;
        }
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_pet_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Pet pet = petList.get(position);
        this.context = holder.status.getContext();

        // set pet UI
        holder.status.setText(pet.getStatus());
        holder.petType.setText(pet.getPetType());
        holder.city.setText("Jerusalem");   // todo - add a city (according to tha location)
        holder.date.setText(pet.getLastSeenDate().toString());

        String petBreed = pet.getBreed();
        if (petBreed.equals("mixed")) {   //todo - constants
            holder.breed.setText(String.format("%s %s", pet.getBreed(), pet.getColor()));
        }

        // todo - set image

        // details listener - show popUp with
        holder.detailsButton.setOnClickListener(v ->
        {
            // start popUp
            View popupView = LayoutInflater.from(context).inflate(R.layout.pet_details_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            openPopUp = popupWindow;

            // find views
            Button close_button = popupView.findViewById(R.id.close);
            TextView status = popupView.findViewById(R.id.lost_or_found);
            TextView description = popupView.findViewById(R.id.description);
            TextView city = popupView.findViewById(R.id.city);
            TextView date = popupView.findViewById(R.id.last_seen_date);
            ImageView image = popupView.findViewById(R.id.image);

            //set UI
            status.setText(pet.getStatus());
            // todo - add photo
            description.setText(String.format("%s %s %s", pet.getColor(), pet.getBreed(), pet.getPetType()));

            city.setText("Jerusalem");   // todo - replace the city with the google maps location
            date.setText(pet.getLastSeenDate().toString());

            close_button.setOnClickListener(v1 -> {
                popupWindow.dismiss();
                openPopUp = null;
            });

            popupWindow.showAsDropDown(popupView, 0, 0);
        });

    }


    @Override
    public int getItemCount() {
        return petList.size();
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    /**
     * View holder
     */
    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView status;
        TextView petType;
        TextView breed;
        TextView city;
        TextView date;
        ImageView image;
        Button detailsButton;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            status = itemView.findViewById(R.id.lost_or_found);
            image = itemView.findViewById(R.id.image);
            petType = itemView.findViewById(R.id.type);
            breed = itemView.findViewById(R.id.breed);
            city = itemView.findViewById(R.id.city);
            date = itemView.findViewById(R.id.last_seen_date);
            detailsButton = itemView.findViewById(R.id.found);

        }
    }

}