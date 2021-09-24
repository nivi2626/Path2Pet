package huji.post_pc.path2pet;

import android.content.Context;
import android.net.Uri;
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

import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Adapter for the feed
 */
public class Feed_RecyclerAdapter extends RecyclerView.Adapter<Feed_RecyclerAdapter.AnimalViewHolder> {
    private List<Pet> petList = null;
    private Context context;
    public PopupWindow openPopUp = null;

    public Feed_RecyclerAdapter(List<Pet> petList) {
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
        setStatusBreedColorsDateAndLocation(pet, holder.status, holder.petType, holder.breed, holder.colors, holder.date, holder.city);

        if (pet.getImages().size() > 0) {
            Picasso
                    .get()
                    .load(pet.getImages().get(0))
                    .into(holder.image);
        }
        else{
            holder.image.setImageResource(R.drawable.place_holder);
        }


        // details listener - show popUp with
        holder.detailsButton.setOnClickListener(v ->
        {
            // start popUp
            View popupView = LayoutInflater.from(context).inflate(R.layout.pet_details_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            photosAdapter adapter = new photosAdapter();
            openPopUp = popupWindow;

            // find popUp views
            Button closeButton = popupView.findViewById(R.id.close);
            Button showDetailsButton = popupView.findViewById(R.id.show_details);
            TextView status = popupView.findViewById(R.id.lost_or_found);
            TextView type = popupView.findViewById(R.id.type);
            TextView breed = popupView.findViewById(R.id.breed);
            TextView colors = popupView.findViewById(R.id.colors);
            TextView sex = popupView.findViewById(R.id.sex);
            TextView city = popupView.findViewById(R.id.city);
            TextView collar = popupView.findViewById(R.id.with_or_without_collar);
            TextView size = popupView.findViewById(R.id.size);
//            TextView laseSeenDate = popupView.findViewById(R.id.last_seen_date);
            TextView reportDate = popupView.findViewById(R.id.date_text);
            TextView comments = popupView.findViewById(R.id.comments_edit);
            SliderView imageSlider = popupView.findViewById(R.id.imageSlider);
            TextView nameText = popupView.findViewById(R.id.name_text);
            TextView nameEdit = popupView.findViewById(R.id.name_edit);
            TextView emailText = popupView.findViewById(R.id.email_text);
            TextView emailEdit = popupView.findViewById(R.id.email_edit);
            TextView phoneText = popupView.findViewById(R.id.phone_text);
            TextView phoneEdit = popupView.findViewById(R.id.phone_edit);

            //set popUp UI:
            // set pet's description, report date, and city
            setStatusBreedColorsDateAndLocation(pet, status, type, breed, colors, reportDate, city);

            // set pet's sex and size
            if (!pet.getSex().equals("")){
                sex.setText(pet.getSex());
            }
            if (!pet.getSize().equals("")) {
                size.setText(pet.getSize());
            }

            // set comments
            String orgString = pet.getComments();
            StringBuilder finalString = new StringBuilder();
            int space = 0;
            int index = 0;
            for (int i = 1; i < orgString.length(); i++) {
                if (orgString.substring(i, i + 1).equals(" ")) {
                    space = i;
                }
                if (i % 20 == 0 ) {
                    finalString.append(orgString.substring(index, space)).append("\n");
                    index = space;
                }
                if (i == orgString.length()-1){
                    finalString.append(orgString.substring(index));
                }
            }
            comments.setText(finalString);

            // set collar
            if (pet.hasCollar) {
                collar.setText(AppPath2Pet.COLLAR_WITH); }
            else {
                collar.setText(AppPath2Pet.COLLAR_WITHOUT);
            }
            // set photos
            List<Uri> photos = pet.getImages();
            if (photos != null) {
                if (photos.size() > 0) {
                    imageSlider.setVisibility(View.VISIBLE);
                    imageSlider.setSliderAdapter(adapter);
                    adapter.renewItems(photos);
                } else {
                    imageSlider.setVisibility(View.INVISIBLE);
                }
            }

            closeButton.setOnClickListener(v1 -> {
                popupWindow.dismiss();
                openPopUp = null;
            });

            showDetailsButton.setOnClickListener(v1 -> {
                nameText.setVisibility(View.VISIBLE);
                nameEdit.setVisibility(View.VISIBLE);
                nameEdit.setText(pet.getName());
                emailText.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                emailEdit.setText(pet.getEmail());
                phoneText.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);
                phoneEdit.setText(pet.getPhone());
                showDetailsButton.setClickable(false);
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
        notifyDataSetChanged();
    }

    /**
     * feed item View holder
     */
    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView status;
        TextView petType;
        TextView breed;
        TextView colors;
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
            colors = itemView.findViewById(R.id.colors);
            breed = itemView.findViewById(R.id.breed);
            city = itemView.findViewById(R.id.city);
            date = itemView.findViewById(R.id.date);
            detailsButton = itemView.findViewById(R.id.details);
        }
    }

    private void setStatusBreedColorsDateAndLocation(Pet pet, TextView status, TextView type, TextView breed, TextView colors, TextView reportDate, TextView city){
        // set pet's description
        status.setText(pet.getStatus());

        // set pet's description
        type.setText(pet.getPetType());

        // set pet's description - colors and breed
        StringBuilder petsColors = new StringBuilder();
        for (String c: pet.getColors()) {
            if (!c.equals("")) {
                petsColors.append(c).append(", ");
            }
        }
        if (petsColors.length() >0) {
            colors.setText(petsColors.subSequence(0,petsColors.length()-2));
        }
        breed.setText(pet.getBreed());

        // set pets report and last seen dates
        Format dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date report = pet.getReportDate();
        reportDate.setText(dataFormat.format(report));

        // set pet's location
        try {
            String petsLocation = pet.getCityByLocation(city.getContext());
            city.setText(petsLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}