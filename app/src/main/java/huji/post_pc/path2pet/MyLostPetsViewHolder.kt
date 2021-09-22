package huji.post_pc.path2pet

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyLostPetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var petType = itemView.findViewById<TextView>(R.id.type)
    var breed: TextView = itemView.findViewById<TextView>(R.id.breed)
    var city: TextView = itemView.findViewById<TextView>(R.id.city)
    var date: TextView = itemView.findViewById<TextView>(R.id.last_seen_date)
    var image: ImageView = itemView.findViewById<ImageView>(R.id.image)
    var foundButton: Button = itemView.findViewById<Button>(R.id.found)
    var editButton: Button = itemView.findViewById<Button>(R.id.edit)

}