package joona.enbuska.Logic;

/**
 * Created by WinNabuska on 27.12.2015.
 */
public class Point{

    public final int x;
    public final int y;

    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Point p = (Point)obj;
        return p.x == x && p.y == y;
    }

    @Override
    public String toString() {
        return "x: "+x+" y:"+y;
    }
}
