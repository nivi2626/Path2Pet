package huji.post_pc.path2pet

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.SliderView

class MyLostPetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val foundButton: Button = itemView.findViewById(R.id.found)
    val imageSlider: SliderView = itemView.findViewById(R.id.imageSlider)
    val image: ImageView = itemView.findViewById(R.id.image)
    val petType: TextView = itemView.findViewById(R.id.type)
    val colors: TextView = itemView.findViewById(R.id.colors)
    val breed: TextView = itemView.findViewById(R.id.breed)
    val city: TextView = itemView.findViewById(R.id.city)
    val date: TextView = itemView.findViewById(R.id.date)
}