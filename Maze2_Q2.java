import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.*;
import java.lang.Object;
import java.awt.geom.Point2D;
import java.awt.Point ;

/**    <b>Version 4<b>
* Explores passages and stores data and backtracks and doesn't require the location of each junction to be recorded.
*@author Kai Meller
*@version 4,Ex2
*/
public class Ex2{

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
							// On the first move of the first run of a new maze.
    if ((robot.getRuns() == 0) && (pollRun == 0)){
    robotData = new RobotData();			// reset the data store.
    pollRun++;
    }
    
    
    nonWallDires.clear();				// clears arrays between steps.
    passageDires.clear();
    beenBeforeDires.clear();
    
    
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
    passages = passageDires.size();
    beenBefore = beenBeforeDires.size();
    int direAbs = IRobot.NORTH;
    if (beenBefore == 1){
      robotData.junctionDire.push(robot.getHeading());		// pushes to stack if it is the first time the robot has been to this junction.
    }
    if (passages>0){
      direAbs = passageDires.get((int) Math.round(Math.random()*passages-0.5));			// choose random between options.
      robot.face(direAbs);
    }
    
    else {
      direAbs = (((robotData.junctionDire.pop()+2)%4)+IRobot.NORTH);
      robot.setHeading(direAbs);		// sets dire to opposite of the heading that first took the robot into this junction.
    }
  return (robot.getHeading());
  }


  public void reset() {
    robotData.junctionDire.clear();
  }
}


class RobotData{
    public Stack<Integer> junctionDire;
    
    
    public RobotData(){
      junctionDire = new Stack<Integer>();
  }
}