/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1{

  public void controlRobot(IRobot robot) {
    //initialize randno, direction, prevdirection and direname
    int randno;
    int direction;
    int prevdirection=robot.getHeading();
    String direname;
      //start a loop to check for walls
    do{
		//reset direction if looped so that change in direction is correct and then select a random number
      robot.setHeading(prevdirection);
      randno = (int) Math.round(Math.random()*3);

      
	// Convert this to a direction

      if (randno == 0){			// 0 < 3*Math.random < 0.5	=> p = 1/6
	direction = IRobot.LEFT;
	direname="left";}
      
      else if (randno == 1){		// 0.5 < 3*Math.random < 1.5	=> p = 2/6
	direction = IRobot.RIGHT;
	direname="right";}
      
      else if (randno == 2){		// 1.5 < 3*Math.random < 2.5	=> p = 2/6
	direction = IRobot.BEHIND;
	direname="backwards";}
      
      else{				// 2.5 < 3*Math.random < 3	=> p = 1/6
	direction = IRobot.AHEAD;
	direname="forwards";}

	
      robot.face(direction); //face the robot in this direction 
    }while(robot.look(IRobot.AHEAD)==IRobot.WALL); //checks if wall is in direction, if so, regenerates direction
    System.out.print("I'm going "+direname);
    
    int walls=0;
    //counter for number of walls
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

}