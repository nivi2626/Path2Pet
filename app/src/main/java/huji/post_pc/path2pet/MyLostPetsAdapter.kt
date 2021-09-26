package huji.post_pc.path2pet

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.SliderView
import java.lang.String.format
import java.text.Format
import java.text.SimpleDateFormat


class MyLostPetsAdapter : RecyclerView.Adapter<MyLostPetsViewHolder>() {
    private lateinit var petList: MutableList<Pet>
    private lateinit var context: Context
    var popupWindow: PopupWindow? = null

    fun setPetList(petList: MutableList<Pet>) {
        this.petList = petList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLostPetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.my_lost_pets_item, parent, false)
        return MyLostPetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyLostPetsViewHolder, position: Int) {
        this.context = holder.city.context
        val curPet = petList[position]
        var matchedScore = 0
        var matchedPet : Pet? = null
        val adapterContext: Context = holder.petType.context
        val adapter = photosAdapter()
        val lostPetSp:SharedPreferences = adapterContext.getSharedPreferences(
            AppPath2Pet.SP_MY_LOST, android.content.Context.MODE_PRIVATE)

        // set UI
        holder.petType.text = curPet.getPetType()
        holder.breed.text = curPet.getBreed()
        holder.city.text = curPet.getCityByLocation(holder.city.context)
        holder.matchButton.visibility = View.INVISIBLE
        holder.matchButton.isEnabled = false

        holder.date.text = curPet.stringDate

        // set colors
        val petsColors = StringBuilder()
        for (c in curPet.getColors()) {
            if (c != "") {
                petsColors.append(c).append(", ")
            }
        }
        if (petsColors.isNotEmpty()) {
            holder.colors.text = petsColors.subSequence(0, petsColors.length - 2)
        }

        // set photos
        val photos: MutableList<Uri> = curPet.getImages()
            if (photos.size > 0) {
                holder.imageSlider.visibility = View.VISIBLE
                holder.imageSlider.setSliderAdapter(adapter)
                adapter.renewItems(photos)
            }
            else {
                holder.imageSlider.visibility = View.INVISIBLE
        }

        holder.foundButton.setOnClickListener {
            exitDialog(adapterContext, curPet, lostPetSp)
        }

        if (AppPath2Pet.bestMatches.size > 0)
        {
            for (item in AppPath2Pet.bestMatches)
                if (parseMatchedList(item.keyValueMap.values.toString()).myID() != curPet.getId())
                {
                    continue
                }
                else
                {
                    val matchedPetID = parseMatchedList(item.keyValueMap.values.toString()).otherID()
                    matchedScore = parseMatchedList(item.keyValueMap.values.toString()).getMatchScore()
                    if (matchedScore < AppPath2Pet.MIN_MATCH_VAL)
                    {
                        continue
                    }
                    matchedPet = AppPath2Pet.getPetsDB().getPetByID(matchedPetID)
                    holder.matchButton.text = "$matchedScore% Match Found!"
                    holder.matchButton.visibility = View.VISIBLE
                    holder.matchButton.isEnabled = true
                }
        }

        holder.matchButton.setOnClickListener {
            if (matchedPet != null) {
                // start popUp
                val popupView = LayoutInflater.from(context).inflate(R.layout.match_pet_popup, null)
                val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                this.popupWindow = popupWindow

                // find popUp views
                val closeButton = popupView.findViewById<Button>(R.id.close)
                val title = popupView.findViewById<TextView>(R.id.title)
                val type = popupView.findViewById<TextView>(R.id.type)
                val breed = popupView.findViewById<TextView>(R.id.breed)
                val colors = popupView.findViewById<TextView>(R.id.colors)
                val sex = popupView.findViewById<TextView>(R.id.sex)
                val city = popupView.findViewById<TextView>(R.id.city)
                val collar = popupView.findViewById<TextView>(R.id.with_or_without_collar)
                val reportDate = popupView.findViewById<TextView>(R.id.date_text)
                val comments = popupView.findViewById<TextView>(R.id.comments_edit)
                val nameEdit = popupView.findViewById<TextView>(R.id.name_edit)
                val emailEdit = popupView.findViewById<TextView>(R.id.email_edit)
                val phoneEdit = popupView.findViewById<TextView>(R.id.phone_edit)

                //set popUp UI:
                // set pet's description, report date, and city
                title.text = "We found a$matchedScore% match for your pet!"
                type.text = matchedPet.getPetType()
                breed.text = matchedPet.getBreed()
                colors.text = matchedPet.stringColors
                sex.text = matchedPet.getSex()
                city.text = matchedPet.getCityByLocation(context)
                if (matchedPet.getHasCollar()) {
                    collar.text = AppPath2Pet.COLLAR_WITH
                }
                else {
                    collar.setText(AppPath2Pet.COLLAR_WITHOUT)
                }
                reportDate.setText(matchedPet.stringDate)
                comments.setText(matchedPet.getComments())
                nameEdit.setText(matchedPet.getName())
                emailEdit.setText(matchedPet.getEmail())
                phoneEdit.setText(matchedPet.getPhone())

                closeButton.setOnClickListener { v1: View? ->
                    popupWindow.dismiss()
                    this.popupWindow = null
                }

                popupWindow.showAsDropDown(popupView, 0, 0)
            }
        }
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    private fun exitDialog(context: Context, pet: Pet, sp: SharedPreferences) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(View.inflate(context, R.layout.alert_dialog, null))

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you wish to delete this entry?\npet's data will be lost")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->

                // delete from storage
                if (pet.imageNum > 0) {
                    AppPath2Pet.getPetsDB().deleteFromStorage(pet.getId(), pet.getImages().size)
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
        alert.setTitle("Pet Found!")
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


    private fun parseMatchedList(currentMatchStr : String): MatchedPet {
        var myPetId = ""
        var otherPetId = ""
        var score = 0.0
        var attr = currentMatchStr.split(",")
        myPetId = attr[0].split(":")[1].split("\"")[1]
        otherPetId = attr[1].split(":")[1].split("\"")[1]
        score = attr[2].split(":")[1].split("}")[0].toDouble()*100
        return MatchedPet(myPetId, otherPetId, score)
    }

}