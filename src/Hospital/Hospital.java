package Hospital;
import java.util.Scanner;

public class Hospital {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Waiting room capacity: ");
        int capacity = scanner.nextInt();
        System.out.print("Number of doctors: ");
        int numDoctors = scanner.nextInt();
        System.out.print("Number of patients: ");
        int numPatients = scanner.nextInt();
        System.out.println("\n--- Emergency Room Simulation Started ---\n");
        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        Semaphore treatmentRooms = new Semaphore(2);
        for (int i = 1; i <= numDoctors; i++) {
            Doctor doctor = new Doctor(i, waitingRoom, treatmentRooms);
            Thread doctorThread = new Thread(doctor, "Doctor-" + i);
            doctorThread.setDaemon(true); 
            doctorThread.start();
        }
        Thread[] patientThreads = new Thread[numPatients];
        for (int i = 1; i <= numPatients; i++) {
            Patient patient = new Patient(i, waitingRoom);
            patientThreads[i - 1] = new Thread(patient, "Patient-" + i);
            patientThreads[i - 1].start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (int i = 0; i < numPatients; i++) {
            try {
                patientThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }System.out.println("\nAll patients have been handled. Simulation Finished.");
        scanner.close();
    }
}
