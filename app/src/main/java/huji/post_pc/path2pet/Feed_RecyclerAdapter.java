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

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "";
        if (pet.getLastSeenDate() != null) {
            strDate = dateFormat.format(pet.getLastSeenDate());
        }

        if (pet.getImages().size() > 0) {
            holder.image.setRotation(90);
            Picasso
                    .get()
                    .load(pet.getImages().get(0))
                    .into(holder.image);
        }

        holder.date.setText(strDate);
        holder.status.setText(pet.getStatus());
        holder.petType.setText(pet.getPetType());
        holder.city.setText("Jerusalem");   // todo - add a city (according to tha location)
        holder.breed.setText(String.format("%s %s", pet.getBreed(), pet.getColors()));

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
            TextView description = popupView.findViewById(R.id.description);
            TextView sex = popupView.findViewById(R.id.sex);
            TextView city = popupView.findViewById(R.id.city);
            TextView collar = popupView.findViewById(R.id.with_or_without_collar);
            TextView laseSeenDate = popupView.findViewById(R.id.last_seen_date);
            TextView reportDate = popupView.findViewById(R.id.report_date);
            TextView comments = popupView.findViewById(R.id.comments_edit);
            SliderView imageSlider = popupView.findViewById(R.id.imageSlider);
            TextView nameText = popupView.findViewById(R.id.name_text);
            TextView nameEdit = popupView.findViewById(R.id.name_edit);
            TextView emailText = popupView.findViewById(R.id.email_text);
            TextView emailEdit = popupView.findViewById(R.id.email_edit);
            TextView phoneText = popupView.findViewById(R.id.phone_text);
            TextView phoneEdit = popupView.findViewById(R.id.phone_edit);

            //set popUp UI
            status.setText(pet.getStatus());
            description.setText(String.format("%s %s %s", pet.getColors(), pet.getBreed(), pet.getPetType()));
            sex.setText(pet.getSex());
            // todo - put google location
            city.setText("Jerusalem");
//            collar.setText(pet.collar) // todo - when collar attribute is added;
            Date laseSeen = pet.getLastSeenDate();
            laseSeenDate.setText(dateFormat.format(laseSeen).toString());
            Date report = pet.getReportDate();
            reportDate.setText(dateFormat.format(report).toString());
            comments.setText(pet.getComments());

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
                nameEdit.setText(pet.name);
                emailText.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                emailEdit.setText(pet.email);
                phoneText.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);
                phoneEdit.setText(pet.phone);
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