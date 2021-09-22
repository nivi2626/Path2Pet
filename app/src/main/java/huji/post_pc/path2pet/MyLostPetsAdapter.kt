package huji.post_pc.path2pet

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyLostPetsAdapter : RecyclerView.Adapter<MyLostPetsViewHolder>() {
    private lateinit var petList: List<Pet>

    fun setPetList(petList: List<Pet>) {
        this.petList = petList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLostPetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_lost_pets_item, parent, false)
        return MyLostPetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyLostPetsViewHolder, position: Int) {
        val curPet = petList[position]
        holder.petType.text = curPet.getPetType()
        holder.breed.text = curPet.getBreed()
        holder.city.text = "Jerusalem" // TODO - implement later
        holder.date.text = curPet.getLastSeenDate().toString()
//        holder.image!!.text = curPet.getPetType() // TODO implement later
        holder.foundButton.setOnClickListener {
            // TODO - add pop up - "are you sure you want to delete this entry?"
            // TODO - delete sp
            // TODO - update FIREBASE
            // TODO - remove log.d
            Log.d("TAG", "found found found!")
        }
//        holder.editButton.setOnClickListener {
//            // TODO - implement, add Done button, change visibility, change other
//            //  text views to edit text views
//            Log.d("TAG", "edit edit edit!")
//        }
    }


    override fun getItemCount(): Int {
        return petList.size
    }

}