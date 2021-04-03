import java.util.*;
public class Point
{
  int xcomp;
  int ycomp;
  
  public Point (int x, int y)
  {
    xcomp = x;
    ycomp = y;
  }
  
  public int getX()
  {
    return xcomp;
  }
  
  public int getY()
  {
    return ycomp;
  }
  
  public void setX(int x)
  {
    xcomp = x;
  }
  
  public void setY(int y)
  {
    ycomp = y;
  }
  
  public Point copy()
  {
    return new Point(xcomp, ycomp);
  }
}
