package huji.post_pc.path2pet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LostPetViewModel : ViewModel() {

    val progressLiveData = MutableLiveData(MIN_PROGRESS)
    val lostPetDoneLiveData = MutableLiveData(false)

    fun increaseProgress() {
        if (progressLiveData.value!! < MAX_PROGRESS) {
            progressLiveData.value = progressLiveData.value?.plus(1)
            lostPetDoneLiveData.value = progressLiveData.value == MAX_PROGRESS
        }

    }

    fun decreaseProgress() {
        if (progressLiveData.value!! > MIN_PROGRESS) {
            progressLiveData.value = progressLiveData.value?.minus(1)
            lostPetDoneLiveData.value = progressLiveData.value == MAX_PROGRESS
        }
    }

    companion object {
        private const val MIN_PROGRESS = 0
        private const val MAX_PROGRESS = 5

    }
}