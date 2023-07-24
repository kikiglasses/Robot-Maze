import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.*;
import java.lang.Object;
import java.awt.geom.Point2D;
import java.awt.Point ;

/**    <b>Version 7<b>
* Grand Finale
*@author Kai Meller
*@version 6, Grand Finale
*/
public class GrandFinale{

  public static int initialHeading;		// Heading at the start of the step, and variable used to carry the direction the 
  private static int direction;
  
  private int nonWalls;				// variables holding size of direction arraylists.
  private int passages;
  private int beenBefore;
  
  private ArrayList<Integer> nonWallDires = new ArrayList<Integer>();
  private ArrayList<Integer> passageDires = new ArrayList<Integer>();
  private ArrayList<Integer> beenBeforeDires = new ArrayList<Integer>();
  
  private int pollRun = 0;					// Incremented after each pass.
  private RobotData robotData;				// Data store for junctions.
  
  public void controlRobot(IRobot robot){
    
    initialHeading = robot.getHeading();
    
    nonWallDires.clear();				// clears arrays between steps.
    passageDires.clear();
    beenBeforeDires.clear();
    
							// On the first move of the first run of a new maze.
    if ((robot.getRuns() == 0) && (pollRun == 0)){
      robotData = new RobotData();			// reset the data store.
      pollRun++;
    }
    
    nonWalls = nonwallExits(robot);			// calculates number of each type of exit and puts them in arraylists.
    passages = passageExits(robot);
    beenBefore = beenbeforeExits(robot);
      
      
    switch(nonWalls){					// switch decides which situation the robot is in, and
    case 4:						// sends the controller to the necessary method.
      direction = junction(robot);
      break;
    case 3:
      direction = junction(robot);
      break;
    case 2:
      direction = corridor(robot);
      break;
    case 1:
      direction = deadend(robot);
      break;
    default:
      while (true){
        System.out.println("No valid direction to move in");
      }
    }
    robot.setHeading(direction);			// sets the final heading of the program.
  }
  
  
  
  
  private int nonwallExits(IRobot robot){		// method that adds directions to an arraylist if they aren't a wall.
    for (int i=0; i<4; i++){
      if (robot.look(IRobot.AHEAD+i) != IRobot.WALL){
	      nonWallDires.add(IRobot.AHEAD+i);
      }
    }
    return nonWallDires.size();
  }
  
  
    private int passageExits(IRobot robot){		// method that adds directions to an arraylist if they are a passage.
      for (int i=0; i<4; i++){
        if (robot.look(IRobot.AHEAD+i) == IRobot.PASSAGE){
          passageDires.add(IRobot.AHEAD+i);
        }
      }
      return passageDires.size();
    }
  
  
    private int beenbeforeExits(IRobot robot){		// method that adds directions to an arraylist if they are a beenbefore.
      for (int i=0; i<4; i++){
        if (robot.look(IRobot.AHEAD+i) == IRobot.BEENBEFORE){
          beenBeforeDires.add(IRobot.AHEAD+i);
	      }
      }
      return beenBeforeDires.size();
    }
  
  
  
  
  private int deadend(IRobot robot){			// method for deadend situation.
    for (int i=0; i<4; i++){
      if (robot.look(IRobot.AHEAD+i) != IRobot.WALL){
        robot.face(IRobot.AHEAD+i);
        return (robot.getHeading());
      }
    }
    return (robot.getHeading());
  }
  
  
  private int corridor(IRobot robot){			// method for corridor situation.
    for (int i=1; i<5; i=i*2){					// AHEAD, RIGHT and LEFT only.
      if (robot.look(IRobot.AHEAD+i-1) != IRobot.WALL){
        robot.face(IRobot.AHEAD+i-1);
        return (robot.getHeading());
      }
    }
    return (robot.getHeading());
  }
  
  
  private int junction(IRobot robot){			// method for junction situation.
    int direRel = IRobot.AHEAD;
    int direAbs = IRobot.NORTH;
    nonWalls = nonwallExits(robot);
    passages = passageDires.size();
    beenBefore = beenBeforeDires.size();
    if (Collections.frequency(robotData.junctionRepeats, robot.getLocation()) > 7){			// random mode because stuck in loop
      if (passages>0){
        direRel = passageDires.get((int) Math.round(Math.random()*passages-0.5));			// choose random between options.
        robot.face(direRel);
      }
      else {
        direRel = beenBeforeDires.get((int) Math.round(Math.random()*beenBefore-0.5));
        robot.face(direRel);
      }
    }
    else if (robot.getRuns() == 0){
      if (passages>0){
        direRel = passageDires.get((int) Math.round(Math.random()*passages-0.5));			// choose random between options.
        robot.face(direRel);
      }
      
      else {
        direAbs = ((((robotData.junctionInitialDire.get(robotData.junctionXY.indexOf(robot.getLocation())))+2)%4)+IRobot.NORTH);
        robot.setHeading(direAbs);		// sets dire to opposite of the heading that first took the robot into this junction
      }
    robotData.recordJunction(robot, initialHeading);
    }
    else {
      robot.setHeading(robotData.junctionLastDire.get((robotData.junctionXY.indexOf(robot.getLocation()))));
      robotData.junctionRepeats.add(robot.getLocation());
    }
  return (robot.getHeading());
  }
  
  
  
  
  private void removeBehind(){
    passageDires.remove(Integer.valueOf(IRobot.BEHIND));
    beenBeforeDires.remove(Integer.valueOf(IRobot.BEHIND));
  }

  
  
  public void reset() {
    pollRun = 0;
    robotData.junctionRepeats.clear();
  }
}
  
  
  
class RobotData{
    
    public ArrayList<Point> junctionXY;
    public ArrayList<Integer> junctionInitialDire;
    public ArrayList<Integer> junctionLastDire;
    public ArrayList<Point> junctionRepeats;
    

    
    public RobotData(){

      junctionXY = new ArrayList<Point>();
      junctionInitialDire = new ArrayList<Integer>();
      junctionLastDire = new ArrayList<Integer>();
      junctionRepeats = new ArrayList<Point>();
    }
    
    
    public void recordJunction(IRobot robot, int initialHeading){
      junctionRepeats.add(robot.getLocation());
      if (junctionXY.contains(robot.getLocation())==false){	//adding to the arrays.
        junctionXY.add(robot.getLocation());
        junctionInitialDire.add(initialHeading);
        junctionLastDire.add(robot.getHeading());
      }
      else {
	junctionLastDire.set((junctionXY.indexOf(robot.getLocation())),robot.getHeading());
      }

    }
}