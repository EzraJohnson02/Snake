import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;  //for Applet
import java.net.*;  //for URL

//remaining things to do:
//leaderboard

//The Display is the region in the window where drawing occurs.
public class Display extends JComponent implements KeyListener  //need for keyboard input
{
  //main method for testing
  
  public static void main(String[] args) throws IOException
  {
    Display display1 = new Display();
    display1.run();
    while (true)
    {
      display1.run();
    }
  }
  
  public void restart()
  {
    d2 = true;
    d3 = false;
    angle = 0.0;
    level = 1;
    snake = new ArrayList<Point>();
    avY = 200;
    avX = 200;
    foodcount = 0;
  }
  
  boolean d2 = true;
  boolean d3 = false;
  double angle = 0;
  double speed;
  double angleSpeed = Math.PI/8;
  ArrayList<Point> points;
  int foodpointX;
  int foodpointY;
  int foodcount;
  int level = 1;
  JLabel label1;
  ArrayList<Point> snake = new ArrayList<Point>();
  Point moveable = new Point(200,200);
  String fileName = "crunch.wav";
  URL url = getClass().getResource(fileName);
  int avX = 200;
  int avY = 200;
  
  public Display()
  {
    
    points = new ArrayList<Point>();
    for (int x = 0; x < 400; x++)
    {
        points.add(new Point(x,0));
        points.add(new Point(x,400));
    }
    for (int y = 0; y < 400; y++)
    {
      points.add(new Point(0,y));
      points.add(new Point(400,y));
    }
    foodpointX = (int)(Math.random() * 380) + 10;
    foodpointY = (int)(Math.random() * 380) + 10;
    JFrame frame = new JFrame();  //create window
    frame.setTitle("3D Snake");  //set title of window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //closing window will exit program

    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

    label1 = new JLabel("Amount of Food Eaten: " + foodcount + "" + "         Level:" + level + "");
    frame.getContentPane().add(label1);

    //big drawing part
    setPreferredSize(new Dimension(400, 400));  //set size of drawing region
    
    //need for keyboard input
    setFocusable(true);  //indicates that Display can process key presses
    addKeyListener(this);  //will notify Display when a key is pressed
    
    frame.getContentPane().add(this);  //add drawing region to window
    frame.pack();  //adjust window size to fit drawing region
    frame.setVisible(true);  //show window
    JOptionPane.showMessageDialog(null, "Welcome to 3D Snake! \nHow to play: \n1. You will automatically move.\n2. USE THE LEFT AND RIGHT ARROW KEYS to move your snake.\n3. Eat food to get bigger.\n4. Press the spacebar to transition between 3D and 2D\nPress OK when ready");
  }
  
  //called automatically when Java needs to draw the Display
  public void paintComponent(Graphics g)
  {
    int width = getWidth();  //get width of drawing region
    int height = getHeight();  //get height of drawing region
    g.setColor(Color.BLACK);  //set pen color to white
    g.fillRect(0, 0, width, height);  //fill with white rectangle
    g.setColor(Color.GREEN);
    for (Point s: snake)
    {
      if (d2)
        g.drawOval(200 + s.getX() - avX, 200 + s.getY() - avY,5,5);
      double onHoriz = (s.getX() - avX) * Math.cos(angle) + (s.getY() - avY) * Math.sin(angle);
      double toHoriz = (s.getX() - avX) * Math.sin(angle) - (s.getY() - avY) * Math.cos(angle);
      if (toHoriz > 0)
      {
        double appHeight = 2000 / toHoriz;
        double screenx = 100 * onHoriz / toHoriz;
        if (d3)
          g.drawLine(200 + ((int)screenx), 200 - ((int)appHeight) , 200 + ((int)screenx), 200 + ((int)appHeight));
      }
    }
    g.setColor(Color.WHITE);
    for (Point p: points)
    {
      int relx = p.getX() - avX;
      int rely = p.getY() - avY;
      if (d2)
        g.drawOval(200 + relx, 200 + rely, 5,5);
      double onHoriz = relx * Math.cos(angle) + rely * Math.sin(angle);
      double toHoriz = relx * Math.sin(angle) - rely * Math.cos(angle);
      if (toHoriz > 0)
      {
        double appHeight = 2000 / toHoriz;
        double screenx = 200 * onHoriz / toHoriz;
        if (d3)
          g.drawLine(200 + ((int)screenx), 200 - ((int)appHeight) , 200 + ((int)screenx), 200 + ((int)appHeight));
      }
    }
    g.setColor(Color.RED); 
    int relx = foodpointX - avX;
    int rely = foodpointY - avY;
    if (d2)
      g.drawOval(200 + relx, 200 + rely, 5,5);
    double onHoriz = relx * Math.cos(angle) + rely * Math.sin(angle);
    double toHoriz = relx * Math.sin(angle) - rely * Math.cos(angle);
    if (toHoriz > 0)
    {
      double appHeight = 2000 / toHoriz;
      double screenx = 200 * onHoriz / toHoriz;
      if (d3)
      {
        //g.drawLine(200 + ((int)screenx), 200 - ((int)appHeight) , 200 + ((int)screenx), 200 + ((int)appHeight));
        //appHeight is radius
        g.fillOval((int)(200 + screenx - appHeight), 200 - (int)appHeight, (int)(appHeight * 2) - 5, (int)(appHeight * 2) - 5);
      }
    }
    g.setColor(Color.GREEN);
    if (d2)
      g.drawOval(200,200,5,5);
 }
  //need for keyboard input
  public void keyPressed(KeyEvent e)
  {
    int key = e.getKeyCode();  //indicates which key was pressed
    //System.out.println("angle:  " + angle);  //shows you key code values for other keys
    /*if (key == 38)  //tests if "up" arrow was pressed
    {
      avX += speed * Math.sin(angle);
      avY -= speed * Math.cos(angle);
      //imageY -= 10;  //image should now be drawn 10 pixels higher
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
    }*/
    if (key == 32)//space
    {
      if (d3)
      {
        d3 = false;
        d2 = true;
      }
      else
      {
        d3 = true;
        d2 = false;
      }
      repaint();
    }
    if (key == 37)  //tests if "left" arrow was pressed
    {
      angle -= angleSpeed;
      //imageY -= 10;  //image should now be drawn 10 pixels higher
      repaint();
    }
    /*if (key == 40)  //tests if "down" arrow was pressed
    {
      avX -= speed * Math.sin(angle);
      avY += speed * Math.cos(angle);
      //imageY -= 10;  //image should now be drawn 10 pixels higher
      repaint();
    }*/
    if (key == 39)  //tests if "right" arrow was pressed
    {
      angle += angleSpeed;
      //imageY -= 10;  //image should now be drawn 10 pixels higher
      repaint();
    }
  }
  
  public void keyReleased(KeyEvent e) { }
  
  public void keyTyped(KeyEvent e) { }
  
  //need for automation (graphical changes not prompted by the keyboard or mouse)
  public void run() throws IOException
  {
    label1.setText("Amount of Food Eaten: " + foodcount + "" + "         Level:" + level + "");
    snake.add(new Point(200,201));
    snake.add(new Point(200,202));
    snake.add(new Point(200,203));
    snake.add(new Point(200,204));
    String code = "";
    boolean running = true;
    while (running)
    {
      if (level == 1 || level == 2)
        speed = level * 5;
      else 
        speed++; 
      int avXC = avX;
      int avYC = avY;
      if ((Math.abs(foodpointX - avX) <= 5) && (Math.abs(foodpointY - avY) <= 5))
      {
          //snake size increases
          Applet.newAudioClip(url).play();
          foodcount++;
          if ((foodcount % 10) == 0)
            level++;
          label1.setText("Amount of Food Eaten: " + foodcount + "" + "         Level:" + level + "");
          foodpointX = (int)(Math.random() * 380) + 10;
          foodpointY = (int)(Math.random() * 380) + 10;
          avX += (speed + 5)* Math.sin(angle);
          avY -= (speed + 5) * Math.cos(angle);
          snake.add(new Point(avXC,avYC));
      }
      else
      {
          avX += speed * Math.sin(angle);
          avY -= speed * Math.cos(angle);
          if (snake.size() > 1)
          {
            //Point p = 
            for (int x = snake.size() - 1; x >0 ; x--)
              {
                  snake.set(x,snake.get(x-1));
                }
              snake.set(0,new Point(avXC,avYC));
            //snake.remove(snake.size()-1);
          }
          else if (snake.size() > 0)
          {
            snake.set(0,new Point(avXC,avYC));
          }
      }
      try{Thread.sleep(100);}catch(Exception e){}  //give Java 100ms to run paintComponent
      for(int z = 0; z < snake.size(); z++)
      {
       //System.out.print(snake.get(z));
        //System.out.println("difference is:");
        //System.out.print((Math.abs((snake.get(z).getX() - avX))));
        if ((Math.abs((snake.get(z).getX() - avX)) <= 2.5) && (Math.abs((snake.get(z).getY() - avY)) <= 2.5))
        {
          running = false;
          code = "Snake";
        }
      }
      for(int x = 0; x < points.size(); x++)
      {
        if ((Math.abs((points.get(x).getX() - avX)) <= 2.5) && (Math.abs((points.get(x).getY() - avY)) <= 2.5))
        {
          running = false;
          code = "Wall";
        }
      }
      repaint();
     } //indicates Display must be redrawn (Java will call paintComponent)
     String nAme = "Bob";
     if (code.equals("Snake")){
        String name = JOptionPane.showInputDialog(null, "Game over: You ran into yourself!\nYour score is: " + foodcount + "\nEnter your name");
        nAme = name;
    }
     else{
        String name = JOptionPane.showInputDialog(null, ("Game over: You ran into the wall!\nYour score is: " + foodcount + "\nEnter your name"));
        nAme = name;
    }
     BufferedReader in = new BufferedReader(new FileReader("Leaderboard.txt"));
     String leaderboard = "";
     String line = in.readLine();
     leaderboard = "" + line;
     line = in.readLine();
     boolean changed1 = false;
     boolean changed2 = false;
     while (line != null) 
     {
        if (Integer.parseInt(line.substring(3,5))<foodcount && changed1 ==false)
        {
            int number = Integer.parseInt(line.substring(0,1));
            String score = "" + foodcount;
            if (foodcount < 10)
                score = "0" + foodcount;
            leaderboard += "\n" + number + ". " + score + " - " + nAme;
            changed1 = true;
        }
        else if (Integer.parseInt(line.substring(3,5))==foodcount && changed1 == false)
        {
            leaderboard += "\n"+line + " & " + nAme;
            changed2 = true;
            changed1 = true;
            line = in.readLine();
        }
        else
        {
          if(changed1)
          { if (changed2)
              {leaderboard += "\n" + line;}
             else
             {
             int number = Integer.parseInt(line.substring(0,1))+1;
             if (number <=5)
                leaderboard += "\n" + number + line.substring(1);
            }
             line = in.readLine();
          }
          else
          {
             leaderboard += "\n" + line;
             line = in.readLine();
          }
        }
    }
    in.close();
    JOptionPane.showMessageDialog(null, leaderboard);
    PrintWriter out = new PrintWriter(new FileWriter("Leaderboard.txt"), true);
    out.println(leaderboard);
    out.close();
    restart();
  }
}
