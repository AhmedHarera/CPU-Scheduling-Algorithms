/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schedulermain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Process implements Comparable<Process> {
    int processId;
    int arrivalTime;
    int burstTime;
    int priority;

    public Process(int processId, int arrivalTime, int burstTime, int priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    @Override
    public int compareTo(Process other) {
        // Higher priority processes come first
        return Integer.compare(other.priority, this.priority);
    }
}

public class NonPreemptivePriority {

    public void runScheduler() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        ArrayList<Process> processList = new ArrayList<>();
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int priority = scanner.nextInt();

            Process process = new Process(i + 1, arrivalTime, burstTime, priority);
            processList.add(process);
        }

        // Sort processes based on arrival time and priority
        Collections.sort(processList, (p1, p2) -> {
            if (p1.arrivalTime != p2.arrivalTime) {
                return Integer.compare(p1.arrivalTime, p2.arrivalTime);
            } else {
                return Integer.compare(p2.priority, p1.priority);
            }
        });

        int currentTime = 0;

        for (Process process : processList) {
            if (process.arrivalTime > currentTime) {
                // Process has not arrived yet, update the current time
                currentTime = process.arrivalTime;
            }

            waitingTime[process.processId - 1] = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            turnaroundTime[process.processId - 1] = currentTime - process.arrivalTime;
        }

        System.out.println("Non-Preemptive Priority Scheduling:");
        System.out.println("Process\tArrival Time\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");

        for (Process process : processList) {
            System.out.println(process.processId + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime +
                    "\t\t" + process.priority + "\t\t" + waitingTime[process.processId - 1] +
                    "\t\t" + turnaroundTime[process.processId - 1]);
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
        NonPreemptivePriority nppsScheduler = new NonPreemptivePriority();
        nppsScheduler.runScheduler();
    }
}
