import java.awt.*;

public class Shotgun extends Sprite{
    private int x;
    private int y;
    private Point loc;
    private Rectangle hitBox;

    public Shotgun(int x, int y) {
        super(Resources.pellets, new Point(x, y), false);
        this.x = x;
        this.y = y;
        Point loc = new Point(x, y);

    }

}

