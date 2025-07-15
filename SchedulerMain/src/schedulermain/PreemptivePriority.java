/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schedulermain;

import java.util.ArrayList;
import java.util.PriorityQueue;
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
        // Lower priority processes come first
        return Integer.compare(this.priority, other.priority);
    }
}

public class PreemptivePriority {

    public void runScheduler() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        ArrayList<Process> processList = new ArrayList<>();
        int[] remainingBurstTime = new int[n];
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
        processList.sort((p1, p2) -> {
            if (p1.arrivalTime != p2.arrivalTime) {
                return Integer.compare(p1.arrivalTime, p2.arrivalTime);
            } else {
                return Integer.compare(p1.priority, p2.priority);
            }
        });

        int currentTime = 0;
        PriorityQueue<Process> processQueue = new PriorityQueue<>();

        for (Process process : processList) {
            if (process.arrivalTime > currentTime) {
                // Process has not arrived yet, update the current time
                currentTime = process.arrivalTime;
            }

            processQueue.add(process);

            Process currentProcess = processQueue.poll();

            waitingTime[currentProcess.processId - 1] = currentTime - currentProcess.arrivalTime;
            currentTime += 1;
            remainingBurstTime[currentProcess.processId - 1] += 1;

            if (remainingBurstTime[currentProcess.processId - 1] == currentProcess.burstTime) {
                turnaroundTime[currentProcess.processId - 1] = currentTime - currentProcess.arrivalTime;

            } else {
                // Add the process back to the queue with updated priority
                processQueue.add(currentProcess);
            }
        }

        System.out.println("Preemptive Priority Scheduling:");
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
        PreemptivePriority ppsScheduler = new PreemptivePriority();
        ppsScheduler.runScheduler();
    }
}


