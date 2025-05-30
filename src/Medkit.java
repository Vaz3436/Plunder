import java.awt.*;

public class Medkit extends Sprite{
    private int x;
    private int y;
    private Point loc;
    private Rectangle hitBox;

    public Medkit(int x, int y) {
        super(Resources.medkit, new Point(x, y), false);
        this.x = x;
        this.y = y;
        Point loc = new Point(x, y);

    }

    public void addHealth(Player player){
        if(player.health+20>100)
            player.health = 100;
        else
            player.health += 20;
    }
}
