// This file defines class "Car".

// This code uses
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the cars.

public class Car extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor.



  // This is the constructor for class Car.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Car(int name) {
    // copy the parameter value to local variable "MyName"
    myName = name;

    // Call threadStart to let the timeSim scheduler know that another
    // thread is starting.  timeSim needs to know how many threads there
    // are, so that it can accurately judge when all threads have finished
    // their current computation, so that simulated time can be advanced.
    Synch.timeSim.threadStart();
  }  // end of the constructor for class "Car"



  public void run () 
  {
	//We use a loop to allow a given car to make 3 tower i.e., the trip from Berriefiled (Eastbound) to petrol station at Kingston 
	//(Westbound) then going back to Barriefiled (Eastbound) is performed three times.
    for (int I=1;  I<= 3; I++) 
    {

      // Simulate driving around Barriefield.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " is driving around Barriefield.\n");
      Synch.timeSim.doSleep(1, 500);
      
      

      // Now cross the causeway westbound, into Kingston.  This might
      // involve some waiting (if the westbound light is red).
      System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " wants to cross westbound.\n");


      // *** Put synchronization code here, to make cars wait if the westbound
      // *** light is red.
      
      //get Mutex on traffic light and westcounter
      Synch.Mutex.acquire();
      
      //Wait if the traffic light is red, or if there are cars waiting
      if ((TrafficLightsController.WestBound_Light_is_red==true) || (Synch.Westbound_Car_Counter>0)) 
      {
    	  System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " waits at Westbound red light.\n");
    	  //Decrease the number of cars in the westbound stop
    	  Synch.Westbound_Car_Counter++;  
    	  //Releae traffic light and Westbound counter mutex 
    	  Synch.Mutex.release();
    	  //Since the light for Westbound entrance is red, cars should stop (block) and wait 
    	  Synch.WestEntrance.acquire(); 
    	  
      }
      else 
      {
    	 //Releae traffic light and Westbound counter mutex 
    	  Synch.Mutex.release();
  
      }
      // You can choose how detailed to make your simulation:
	  // (1) In a less-detailed simulation, all waiting cars start crossing
	  //     instantly, as soon as the light turns green.
	  // or
	  // (2) In a more-detailed simulation, there is some reaction time, so when
	  //     the light turns green the first car starts crossing.  Then after a
	  //     short pause (simulated, for example, by "sleep(1)"), the second car
	  //     starts crossing, and so on.  This is more realistic than simulation (1):
	  //     when you are waiting in a long line of cars at a traffic light, you see the
	  //     light turn green up there, but it takes a while before all the cars ahead
	  //     of you get going, so you have to keep waiting even though the light is green.
	  //     In some cases, you don't even make it across on this green; you have to 
	  //     wait through another red cycle for the next green.
	  // Full marks will be awarded for either of these levels of detail in the simulation.


      // Now we have permission to cross the causeway.  Crossing is simulated
      // by a sleep.  The sleep time is chosen to be relatively long (compared
      // to the sleep times for driving around and getting donuts), so that
      // it frequently happens that several cars are on the bridge.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " is starting to cross westbound.\n");

      Synch.timeSim.doSleep(100);

      // Simulate driving to petrol station, filling the tank, leaving the petrol station, and
      // driving back to the causeway.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " is filling the tank at petrol station.\n");
 
      Synch.timeSim.doSleep(1, 500);

      // Now cross the causeway eastbound, back into Barriefield.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " wants to cross eastbound.\n");
  


      // *** Put synchronization code here, to make cars wait if the eastbound
      // *** light is red..
      
      //get Mutex on traffic light and Eascounter
      Synch.Mutex.acquire();
      //Wait if the traffic light is red, or if there are cars waiting
      if ((TrafficLightsController.EastBound_Light_is_red==true) || (Synch.Eastbound_Car_Counter>0)) 
      {
    	  System.out.println("At time " + Synch.timeSim.curTime() + " Car " + myName + " waits at Eastbound red light.\n");
    	//Decrease the number of cars in the Eastbound stop
    	  Synch.Eastbound_Car_Counter++;
    	//Releae traffic light mutex
    	  Synch.Mutex.release();
   
    	//Since the light for Eastbound entrance is red, cars should stop (block) and wait 
    	Synch.EastEntrance.acquire();
      
    	
    	  
      }
      else 
      {
    	//Releae traffic light mutex when the light is green 
    	 Synch.Mutex.release();
    	     
      }
      
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "+ myName + " is starting to cross eastbound.\n");
     
      Synch.timeSim.doSleep(100);

    } // end of "for" loop
    System.out.println("At time " + Synch.timeSim.curTime() + " Car "+ myName + " has stoped and its driver went out\n");
   
    Synch.timeSim.threadEnd();  // Let timeSim know that this thread
                                // has ended.
  }  // end of "run" method
}  // end of class "Car"
