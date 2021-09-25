package huji.post_pc.path2pet;

public class Breed {
    String name;
    public int imageNum;

    public Breed (String name, int image)
    {
        this.name = name;
        this.imageNum = image;
    }
    public String getName()
    {
        return name;
    }
    public int getImageNum()
    {
        return imageNum;
    }
}
