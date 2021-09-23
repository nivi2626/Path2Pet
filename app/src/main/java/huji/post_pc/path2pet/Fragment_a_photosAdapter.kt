package huji.post_pc.path2pet

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter


class Fragment_a_photosAdapter() :
    SliderViewAdapter<Fragment_a_photosAdapter.VH>() {
    private lateinit var imagesList: MutableList<Uri>

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.image_holder, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        // load image into view
        val image = imagesList[position]
        val imageView: ImageView = viewHolder.imageView
        imageView.setImageURI(image)

        
    }

    override fun getCount(): Int {
        return imagesList.size
    }


    fun renewItems(sliderItems: MutableList<Uri>) {
        imagesList = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Uri) {
        imagesList.add(sliderItem)
        notifyDataSetChanged()
    }


    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image)
    }


}