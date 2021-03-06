package huji.post_pc.path2pet;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BreedRecyclerAdapter extends RecyclerView.Adapter<BreedRecyclerAdapter.BreedViewHolder>{
    private List<Breed> breedList = null;
    private String selectedItem = null;
    public int checkedPosition = 0;

    public BreedRecyclerAdapter(List<Breed> breedList, String selectedBreed) {
        if (this.breedList == null) {
            this.breedList = breedList;
        }
        this.selectedItem = selectedBreed;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.breed_type_item, parent, false);
        return new BreedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        int tempPosition = position;
        Breed breed = breedList.get(position);

        // set UI
        holder.bind(breedList.get(position));
        holder.breed_type.setText(breed.breedName);
        holder.breed_image.setImageURI(breed.imageNum);

        if (breed.breedName.equals(selectedItem)) {
            holder.checkedIcon.setVisibility(View.VISIBLE);
            checkedPosition = position;
        }
        else {
            holder.checkedIcon.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v ->
        {
            holder.bind(breed);
            notifyItemChanged(checkedPosition);
            selectedItem = holder.breed_type.getText().toString();
            checkedPosition = tempPosition;
            holder.checkedIcon.setVisibility(View.VISIBLE);
            if(checkedPosition != holder.getAdapterPosition()){
                notifyItemChanged(checkedPosition);
                checkedPosition = holder.getAdapterPosition();
                selectedItem = breed.breedName;
            }
        });
    }

    @Override
    public int getItemCount() {
        return breedList.size();
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    /**
     * View holder
     */
    public class BreedViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView breed_type;
        public ImageView breed_image;
        public ImageView checkedIcon;
        public BreedViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            breed_type = itemView.findViewById(R.id.breed_type);
            breed_image = itemView.findViewById(R.id.breed_image);
            checkedIcon = itemView.findViewById(R.id.checkedIcon);
        }

        public void bind(final Breed breed){
            if(checkedPosition == -1)
            {
                checkedIcon.setVisibility(View.GONE);
            }else {
                if(checkedPosition == getAdapterPosition()){
                    checkedIcon.setVisibility(View.VISIBLE);
                }
                else
                {
                    checkedIcon.setVisibility(View.GONE);
                }
            }
            breed_type.setText(breed.breedName);
        }
    }
}
