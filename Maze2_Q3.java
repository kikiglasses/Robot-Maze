import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.*;
import java.lang.Object;
import java.awt.geom.Point2D;
import java.awt.Point ;

/**    <b>Version 5<b>
* Explores loopy mazes.
*@author Kai Meller
*@version 5,Ex3
*/
public class Ex3{
  private int initialHeading;
  private int direction;
  private int finalHeading;
  private int nonWalls;
  private int passages;
  private int beenBefore;
  private ArrayList<Integer> nonWallDires = new ArrayList<Integer>();
  private ArrayList<Integer> passageDires = new ArrayList<Integer>();
  private ArrayList<Integer> beenBeforeDires = new ArrayList<Integer>();

  
  public void controlRobot(IRobot robot){
  
    initialHeading = robot.getHeading();
    
    nonWallDires.clear();			// clears arrays between steps.
    passageDires.clear();
    beenBeforeDires.clear();
    
    nonWalls = nonwallExits(robot);		// calculates number of each type of exit and puts them in arraylists.
    passages = passageExits(robot);
    beenBefore = beenbeforeExits(robot);
    
    switch(nonWalls){					// switch decides which situation the robot is in, and
      case 4:						// sends the controller to the necessary method.
	direction = crossroads(robot);
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
	
	  System.out.println("No valid direction to move in");
	
    }
    robot.setHeading(direction);			// sets the finalHeading of the program.
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
  
  
  private int junction(IRobot robot){			// method for junction situation
    passageDires.remove(Integer.valueOf(IRobot.BEHIND));		// remove BEHIND
    beenBeforeDires.remove(Integer.valueOf(IRobot.BEHIND));
    this.passages = passageDires.size();
    this.beenBefore = beenBeforeDires.size();
    if (passages>0){
      this.direction = passageDires.get((int) Math.round(Math.random()*passages-0.5)); // choose random between options
      robot.face(direction);
    }
    
    else {
      this.direction = beenBeforeDires.get((int) Math.round(Math.random()*beenBefore-0.5)); // choose random between options
      robot.face(direction);
    }
  return (robot.getHeading());
  }
  
  
  
  private int crossroads(IRobot robot){			// method for crossroads situation
    passageDires.remove(Integer.valueOf(IRobot.BEHIND));		// remove BEHIND
    beenBeforeDires.remove(Integer.valueOf(IRobot.BEHIND));
    this.passages = passageDires.size();
    this.beenBefore = beenBeforeDires.size();
    if (passages>0){
      this.direction = passageDires.get((int) Math.round(Math.random()*passages-0.5)); // choose random between options
      robot.face(direction);
    }
    
    else {
      this.direction = beenBeforeDires.get((int) Math.round(Math.random()*beenBefore-0.5)); // choose random between options
      robot.face(direction);
    }
    return (robot.getHeading());
    }
}