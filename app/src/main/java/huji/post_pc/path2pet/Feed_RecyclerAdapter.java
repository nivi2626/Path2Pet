package huji.post_pc.path2pet;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        if (pet.getImages().size() >0) {
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
        holder.breed.setText(String.format("%s %s", pet.getBreed(), pet.getColor()));

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