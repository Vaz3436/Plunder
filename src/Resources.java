import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resources {
    // to add an image to the environment:
    // 1. put the file into the res folder.
    // 2. Declare a variable before the static block.
    // 3. Initialize the variable by copying and pasting and modifying the
    //    ImageIO line.


    public static BufferedImage skull, medkit, bomb;
    public static BufferedImage skull, medkit, pellets;

    static{
        try{
            skull = ImageIO.read(new File("./res/skull.png"));
            medkit = ImageIO.read(new File("./res/medkit.png"));
            bomb = ImageIO.read(new File("./res/bomb.png"));
            pellets = ImageIO.read(new File("./res/pellets.png"));


        }catch(Exception e){e.printStackTrace();}
    }


}

