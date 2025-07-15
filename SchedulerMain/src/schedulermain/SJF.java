/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schedulermain;

import java.util.Arrays;
import java.util.Scanner;

public class SJF {

    public void runScheduler() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] processes = new int[n];
        int[] arrivalTimes = new int[n];
        int[] burstTimes = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            processes[i] = i + 1;
            arrivalTimes[i] = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            burstTimes[i] = scanner.nextInt();
        }

        // Sort processes based on arrival time
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrivalTimes[i] > arrivalTimes[j]) {
                    int temp = arrivalTimes[i];
                    arrivalTimes[i] = arrivalTimes[j];
                    arrivalTimes[j] = temp;

                    temp = processes[i];
                    processes[i] = processes[j];
                    processes[j] = temp;

                    temp = burstTimes[i];
                    burstTimes[i] = burstTimes[j];
                    burstTimes[j] = temp;
                }
            }
        }

        waitingTime[0] = 0;

        for (int i = 1; i < n; i++) {
            waitingTime[i] = waitingTime[i - 1] + burstTimes[i - 1];
        }

        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = waitingTime[i] + burstTimes[i];
        }

        System.out.println("SJF Scheduling:");
        System.out.println("Process\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");

        for (int i = 0; i < n; i++) {
            System.out.println(processes[i] + "\t\t" + arrivalTimes[i] + "\t\t" + burstTimes[i] + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }

        double averageWaitingTime = calculateAverage(waitingTime);
        double averageTurnaroundTime = calculateAverage(turnaroundTime);

        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }

    private double calculateAverage(int[] array) {
        double sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum / array.length;
    }
}

