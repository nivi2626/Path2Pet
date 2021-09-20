package huji.post_pc.path2pet

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import java.util.*


class Fragment_a_photosAdapter() :
    SliderViewAdapter<Fragment_a_photosAdapter.VH>() {
    private lateinit var bitMapImages: MutableList<Bitmap>

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.image_holder, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        // load image into view
        val bitmap = bitMapImages[position]
        val imageView: ImageView = viewHolder.imageView
        imageView.setImageBitmap(bitmap);
    }

    override fun getCount(): Int {
        return bitMapImages.size
    }


    fun renewItems(sliderItems: MutableList<Bitmap>) {
        bitMapImages = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Bitmap) {
        bitMapImages.add(sliderItem)
        notifyDataSetChanged()
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image)

    }


}