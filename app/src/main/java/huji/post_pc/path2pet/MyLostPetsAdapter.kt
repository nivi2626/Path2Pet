package huji.post_pc.path2pet

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class MyLostPetsAdapter : RecyclerView.Adapter<MyLostPetsViewHolder>() {
    private lateinit var petList: MutableList<Pet>

    fun setPetList(petList: MutableList<Pet>) {
        this.petList = petList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLostPetsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_lost_pets_item, parent, false)
        return MyLostPetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyLostPetsViewHolder, position: Int) {
        val curPet = petList[position]
        val adapterContext: Context = holder.petType.context
        val lostPetSp:SharedPreferences = adapterContext.getSharedPreferences("my_lost_pets", android.content.Context.MODE_PRIVATE)
//        holder.itemId.text = curPet.getId()
        holder.petType.text = curPet.getPetType()
        holder.breed.text = curPet.getBreed()
        holder.city.text = "Jerusalem" // TODO - implement later
        holder.date.text = curPet.getLastSeenDate().toString()
//        holder.image!!.text = curPet.getPetType() // TODO implement later
        holder.foundButton.setOnClickListener {
            exitDialog(adapterContext, curPet, lostPetSp)
            // TODO - delete sp

        }
    }


    override fun getItemCount(): Int {
        return petList.size
    }

    fun exitDialog(context: Context, pet: Pet, sp:SharedPreferences) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(View.inflate(context, R.layout.alert_dialog, null))

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you wish to delete this entry?\npet's data will be lost")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->

                // delete from storage
                if (pet.imageNum > 0)
                {
                    AppPath2Pet.getPetsDB().deleteFromStorage(pet.getId())
                }

                // delete data from fireStore
                AppPath2Pet.getPetsDB().deleteFromFireStore(pet.getId())

                // delete from sp
                spUpdater(sp, pet.getId())
                this.petList.remove(pet)

                setPetList(this.petList)
                notifyDataSetChanged()

                // hurray message
                val toast = Toast.makeText(
                    context,
                    "Hurray!\nWe are glad to hear you found your pet!",
                    Toast.LENGTH_LONG
                )
                toast.show()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Cancel Report")
        // show alert dialog
        alert.show()
    }

    private fun spUpdater(sp: SharedPreferences, idToDelete: String) {
        var newIDsList : String = ""
        val userLostPetsIDs: Array<String> =
            sp.getString(AppPath2Pet.SP_LOST_ID, "")!!.split(AppPath2Pet.SP_DELIMITER)
                .toTypedArray()
        for (id in userLostPetsIDs)
        {
            if (id != idToDelete)
            {
                newIDsList += id+AppPath2Pet.SP_DELIMITER
            }
        }
        sp.edit().putString(AppPath2Pet.SP_LOST_ID, newIDsList).apply()
    }

}