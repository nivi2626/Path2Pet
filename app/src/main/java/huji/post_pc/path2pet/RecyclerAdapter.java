package huji.post_pc.path2pet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AnimalViewHolder>
{
    private List<Animal> animalList;

    public RecyclerAdapter(List<Animal> animalList)
    {
        this.animalList = animalList;
    }
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_list, parent, false);

        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.animalStatus.setText(animal.getStatus());
        holder.animalImage.setImageResource(animal.getImageURL());
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder
    {
        public TextView animalStatus;
        //add more necessary info we want to show in feed
        public CircleImageView animalImage;
        public AnimalViewHolder(@NonNull View itemView)
        {
            super(itemView);
            animalStatus = itemView.findViewById(R.id.profile_name);
            animalImage = itemView.findViewById(R.id.profile_image);
        }
    }
}