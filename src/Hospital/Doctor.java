package Hospital;

import java.util.Random;

public class Doctor implements Runnable {
    private final int doctorId;
    private final WaitingRoom waitingRoom;
    private final Semaphore treatmentRooms;
    private final Random random = new Random();

    public Doctor(int doctorId, WaitingRoom waitingRoom, Semaphore treatmentRooms) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                Patient patient = waitingRoom.take();

                System.out.println("Doctor " + doctorId + " is assigned to P" + patient.id + ".");
                System.out.println("Doctor " + doctorId + " is trying to acquire a treatment room.");


                boolean acquired = treatmentRooms.tryAcquire(3000);

                if (acquired) {

                    System.out.println("Doctor " + doctorId + " acquired a treatment room.");
                    System.out.println("Doctor " + doctorId + " begins treating P" + patient.id + ".");


                    Thread.sleep(1000 + random.nextInt(2000));

                    System.out.println("Doctor " + doctorId + " finished treating P" + patient.id + ".");

                    treatmentRooms.v();
                    System.out.println("Doctor " + doctorId + " released the treatment room.");
                    System.out.println("Doctor " + doctorId + " is now available.");


                    System.out.println("P" + patient.id + " left.");

                } else {

                    System.out.println("Doctor " + doctorId + " could not acquire a treatment room.");
                    System.out.println("Doctor " + doctorId + " is sending P" + patient.id + " back to the waiting room.");


                    waitingRoom.put(patient);

                    System.out.println("Doctor " + doctorId + " is now available.");
                }
            }
        } catch (InterruptedException e) {
           
            Thread.currentThread().interrupt();
        }
    }
}