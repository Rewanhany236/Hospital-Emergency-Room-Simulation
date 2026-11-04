package Hospital;

public class Patient  implements Runnable {
     public final int id ;
     private  final WaitingRoom waitingRoom;
     public Patient(int id , WaitingRoom waitingroom){
         this.id = id;
         this.waitingRoom = waitingroom;
     }
     @Override
    public void run(){
         System.out.println("p" + id + " arrived.");
         waitingRoom.put(this);
    }
}
