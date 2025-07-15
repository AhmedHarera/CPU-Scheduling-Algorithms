/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schedulermain;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin {

    public void runScheduler() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.print("Enter the time quantum for Round Robin: ");
        int quantum = scanner.nextInt();

        int[] processes = new int[n];
        int[] arrivalTimes = new int[n];
        int[] burstTimes = new int[n];
        int[] remainingBurstTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        Queue<Integer> processQueue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            processes[i] = i + 1;
            arrivalTimes[i] = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            burstTimes[i] = scanner.nextInt();

            // Initialize remaining burst time to the total burst time
            remainingBurstTime[i] = burstTimes[i];
        }

        int currentTime = 0;
        int quantumTimer = 0;

        // Add processes to the queue in the order of their arrival
        for (int i = 0; i < n; i++) {
            if (arrivalTimes[i] == 0) {
                processQueue.add(i);
            }
        }

        while (!processQueue.isEmpty()) {
            int currentProcess = processQueue.poll();

            if (remainingBurstTime[currentProcess] <= quantum) {
                // Process completes within the time quantum
                currentTime += remainingBurstTime[currentProcess];
                turnaroundTime[currentProcess] = currentTime;
                remainingBurstTime[currentProcess] = 0;
            } else {
                // Process does not complete within the time quantum
                currentTime += quantum;
                remainingBurstTime[currentProcess] -= quantum;

                // Add the process back to the queue
                processQueue.add(currentProcess);
            }

            // Update waiting time for other processes in the queue
            for (int i = 0; i < n; i++) {
                if (arrivalTimes[i] <= currentTime && remainingBurstTime[i] > 0 && !processQueue.contains(i)) {
                    processQueue.add(i);
                }
            }
        }

        // Calculate waiting time for all processes
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] -= arrivalTimes[i];
            waitingTime[i] = turnaroundTime[i] - burstTimes[i];
        }

        System.out.println("Round Robin Scheduling:");
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

    public static void main(String[] args) {
        RoundRobin rrScheduler = new RoundRobin();
        rrScheduler.runScheduler();
    }
}

