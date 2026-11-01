package Hospital;

import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom{

   private final int room_size; 
   private final Queue<Patient> patient_queue = new LinkedList<> ();
   private final Semaphore full_seats;  //no of seated patients
   private final Semaphore mutex;  //lock for race conditios, 0->lock
   private final Semaphore empty_seats;  //curr free seats

   public WaitingRoom(int sz){
    this.room_size = sz;
    empty_seats = new Semaphore(sz); 
    full_seats = new Semaphore(0);
    mutex = new Semaphore(1);
}

public void put(Patient p) {
  mutex.p();
  boolean full = (room_size == patient_queue.size());
  mutex.v();
  if (full) {
    System.out.println("The waiting room is full.");
    System.out.println("P" + p.id + "  is waiting outside for an available seat.");
  }
  empty_seats.p(); //--free seat_seatss
  mutex.p();
  patient_queue.add(p);
  System.out.println("P" + p.id + " entered the waiting room.");
  mutex.v();
  full_seats.v(); //++seated patients
}

public Patient take() { // till patient seat
  full_seats.p();  //--seated patients, cuz dr toke one
  mutex.p();
  Patient curr_p = patient_queue.poll();
  mutex.v();
  empty_seats.v();  //++free seats
  return curr_p;
}
}