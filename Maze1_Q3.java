
import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex3{
						    //initialize randno, direction, initialHeading and direname
    int randno;					// used for random generation
    int direction;				// used for changing current direction
    
    
  public void controlRobot(IRobot robot){
    
    int initialHeading=robot.getHeading();		// used to save initial direction to go back to 
    
    int finalHeading=headingController(robot);	// calling headingController
    
    ControlTest.test(finalHeading, robot);
    
    robot.setHeading(finalHeading);
    
    if (robot.getHeading()==IRobot.NORTH){
    System.out.print("I'm going North");}
    
    else if (robot.getHeading()==IRobot.EAST){
    System.out.print("I'm going East");}
    
    else if (robot.getHeading()==IRobot.SOUTH){
    System.out.print("I'm going South");}
    
    else {
    System.out.print("I'm going West");}
    
    
    int walls=0;			//counter for number of walls
				  //separate checker for each relative direction
    if(robot.look(IRobot.AHEAD)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.RIGHT)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.BEHIND)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.LEFT)==IRobot.WALL){
      walls=walls+1;}
    
    
    if(walls==0){				//using the number of walls to determine what situation the robot is in
      System.out.println(" at a crossroad");}
    else if(walls==1){
      System.out.println(" at a junction");}
    else if(walls==2){
      System.out.println(" down a corridor");}
    else if(walls==3){
      System.out.println(" at a deadend");}
  }

  public void reset() {
    ControlTest.printResults();
  }
  


  private byte isTargetNorth(IRobot robot){
    byte result;
    if (robot.getLocation().y >  robot.getTargetLocation().y){			//		!!!DIFFERENT!!!
      result=1;}								//
    else if (robot.getLocation().y == robot.getTargetLocation().y){		// 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
      result=0;}								// 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
    else {									// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 < 0 0 0 0
      result=-1;}								//-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1
    return result;								//-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1
  }										//-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1
  
  
  
  
  
  private byte isTargetEast(IRobot robot){
    byte result;
    if (robot.getLocation().x < robot.getTargetLocation().x){			//		!!!DIFFERENT!!!
      result=1;}								//
    else if (robot.getLocation().x == robot.getTargetLocation().x){		//-1 -1 -1 -1 -1 -1 -1 -1 -1 0 1 1 1 1 1 1 1
      result=0;}								//-1 -1 -1 -1 -1 -1 -1 -1 -1 0 1 1 1 1 1 1 1
    else {									//-1 -1 -1 -1 -1 -1 -1 -1 -1 0 1 1 1 1 1 1 1
      result=-1;}								//-1 -1 -1 -1 -1 -1 -1 -1 -1 0 1 1 1 1 1 1 1
    return result;								//-1 -1 -1 -1 -1 -1 -1 -1 -1 < 1 1 1 1 1 1 1
  }										//-1 -1 -1 -1 -1 -1 -1 -1 -1 0 1 1 1 1 1 1 1

  
  
  
  
  private int lookHeading(IRobot robot, int heading){
    int initialHeading=robot.getHeading();
    robot.setHeading(heading);
    int result=robot.look(IRobot.AHEAD);
    robot.setHeading(initialHeading);
    return result;
  }
  
  
  private int randoDire(IRobot robot){
  int initialHeading=robot.getHeading();
    do{
		//reset direction if looped so that change in direction is correct and then select a random number
      
      robot.setHeading(initialHeading);
      randno = (int) Math.round(4*Math.random()+0.5);

      
	// Convert this to a direction

      if (randno == 1){			// 0.5 < 4*Math.random()+0.5 < 1.5	=> p = 1/4
	direction = IRobot.NORTH;}
      
      else if (randno == 2){		// 1.5 < 4*Math.random()+0.5 < 2.5	=> p = 1/4
	direction = IRobot.EAST;}
      
      else if (randno == 3){		// 2.5 < 4*Math.random()+0.5 < 3.5	=> p = 1/4
	direction = IRobot.SOUTH;}
      
      else{				// 3.5 < 4*Math.random()+0.5 < 4.5	=> p = 1/4
	direction = IRobot.WEST;}

	
      robot.setHeading(direction); //face the robot in this direction 
    }while(robot.look(IRobot.AHEAD)==IRobot.WALL); //checks if wall is in direction, if so, regenerates direction*/
    return direction;
   }
  
  
  private int headingController(IRobot robot){
  
    randno = (int) Math.round(Math.random());
    
    if ((isTargetNorth(robot)==-1)&&(isTargetEast(robot)==-1)){						// target lies in robot's lower left quadrant
      if ((lookHeading(robot, IRobot.SOUTH)!=IRobot.WALL)&&(lookHeading(robot, IRobot.WEST)!=IRobot.WALL)){
	if (randno==0){			// flip a coin
	  direction=IRobot.WEST;}
	else{
	  direction=IRobot.SOUTH;}
      }
      else if (lookHeading(robot, IRobot.SOUTH)!=IRobot.WALL){
	direction=IRobot.SOUTH;}
      else if (lookHeading(robot, IRobot.WEST)!=IRobot.WALL){
	direction=IRobot.WEST;}
      else{
	direction=randoDire(robot);}
    }
    
    
    else if ((isTargetNorth(robot)==-1)&&(isTargetEast(robot)==1)){					// target lies in robot's lower right quadrant
      if ((lookHeading(robot, IRobot.SOUTH)!=IRobot.WALL)&&(lookHeading(robot, IRobot.EAST)!=IRobot.WALL)){
	if (randno==0){			// flip a coin
	  direction=IRobot.EAST;}
	else{
	  direction=IRobot.SOUTH;}
      }
      else if (lookHeading(robot, IRobot.SOUTH)!=IRobot.WALL){
	direction=IRobot.SOUTH;}
      else if (lookHeading(robot, IRobot.EAST)!=IRobot.WALL){
	direction=IRobot.EAST;}
      else{
	direction=randoDire(robot);}
    }
    
    
    else if ((isTargetNorth(robot)==1)&&(isTargetEast(robot)==-1)){					// target lies in robot's upper left quadrant
      if ((lookHeading(robot, IRobot.NORTH)!=IRobot.WALL)&&(lookHeading(robot, IRobot.WEST)!=IRobot.WALL)){
	if (randno==0){			// flip a coin
	  direction=IRobot.WEST;}
	else{
	  direction=IRobot.NORTH;}
      }
      else if (lookHeading(robot, IRobot.NORTH)!=IRobot.WALL){
	direction=IRobot.NORTH;}
      else if (lookHeading(robot, IRobot.WEST)!=IRobot.WALL){
	direction=IRobot.WEST;}
      else{
	direction=randoDire(robot);}
    }
    
    
    else if ((isTargetNorth(robot)==1)&&(isTargetEast(robot)==1)){					// target lies in robot's upper left quadrant
      if ((lookHeading(robot, IRobot.NORTH)!=IRobot.WALL)&&(lookHeading(robot, IRobot.EAST)!=IRobot.WALL)){
	if (randno==0){			// flip a coin
	  direction=IRobot.EAST;}
	else{
	  direction=IRobot.NORTH;}
      }
      else if (lookHeading(robot, IRobot.NORTH)!=IRobot.WALL){
	direction=IRobot.NORTH;}
      else if (lookHeading(robot, IRobot.EAST)!=IRobot.WALL){
	direction=IRobot.EAST;}
      else{
	direction=randoDire(robot);}
    }

    else if (isTargetNorth(robot)==0){									// on same y as target
      if (isTargetEast(robot)==1){
	if (lookHeading(robot, IRobot.EAST)!=IRobot.WALL){
	  direction=IRobot.EAST;}
	else{
	  direction=randoDire(robot);}
      }
      else{
	if (lookHeading(robot, IRobot.WEST)!=IRobot.WALL){
	  direction=IRobot.WEST;}
	else{
	  direction=randoDire(robot);}
      }
    }
    else if (isTargetEast(robot)==0){									// on same x as target
     if (isTargetNorth(robot)==1){
	if (lookHeading(robot, IRobot.NORTH)!=IRobot.WALL){
	  direction=IRobot.NORTH;}
	else{
	  direction=randoDire(robot);}
      }
      else{
	if (lookHeading(robot, IRobot.SOUTH)!=IRobot.WALL){
	  direction=IRobot.SOUTH;}
	else{
	  direction=randoDire(robot);}
      }
    }
    
    
  return direction;
  }
}