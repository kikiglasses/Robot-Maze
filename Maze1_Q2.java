/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex2{

  public void controlRobot(IRobot robot) {
    //initialize randno, direction, prevdirection and direname
    int randno;
    int direction;
    int prevdirection=robot.getHeading();
    String direname;
    
    if ((Math.random() < 0.125) || (robot.look(IRobot.AHEAD)==IRobot.WALL)){
      //start a loop to generate random direction and check for no walls
      do{
		  //reset direction if looped so that change in direction is correct and then select a random number
	robot.setHeading(prevdirection);
	randno = (int) (Math.round(Math.random()*3)+0.5);

	
	  // Convert this to a direction

	if (randno == 1){			// 0.5 < 3*Math.random + 0.5 < 1.5	=> p = 1/4
	  direction = IRobot.LEFT;
	  direname="left";}
	
	else if (randno == 2){		// 1.5 < 3*Math.random + 0.5 < 2.5	=> p = 1/4
	  direction = IRobot.RIGHT;
	  direname="right";}
	
	else{		// 2.5 < 3*Math.random + 0.5 < 3.5	=> p = 1/4
	  direction = IRobot.BEHIND;
	  direname="backwards";}
	  
	robot.face(direction); //face the robot in this direction 
      }while(robot.look(IRobot.AHEAD)==IRobot.WALL); //checks if wall is in direction, if so, regenerates direction
     }
   else{
    direction = IRobot.AHEAD;
    direname="forwards";}
    
    int walls=0;
	    // counter for number of walls with separate checker for each relative direction
    if(robot.look(IRobot.AHEAD)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.RIGHT)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.BEHIND)==IRobot.WALL){
      walls=walls+1;}
      
    if(robot.look(IRobot.LEFT)==IRobot.WALL){
      walls=walls+1;}
    
    System.out.print("I'm going "+direname);
    
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