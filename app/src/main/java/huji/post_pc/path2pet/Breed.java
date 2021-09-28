package huji.post_pc.path2pet;

import android.net.Uri;

public class Breed {
    public String breedName;
    public Uri imageNum;

    public Breed (String breedName, Uri imageNum)
    {
        this.breedName = breedName;
        this.imageNum = imageNum;
    }

}
