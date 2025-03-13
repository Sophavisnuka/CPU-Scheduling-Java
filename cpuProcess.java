import java.util.*;

public class cpuProcess {
    int pid, arrivalTime, burstTime, waitingTime, turnaroundTime, remainingTime;

    // Constructor for a CPU process
    public cpuProcess(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;  // Initialize remaining time to burst time
    }

    // First-Come, First-Served Scheduling Algorithm (FCFS)
    public static void fcfs(List<cpuProcess> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort processes by arrival time
        int time = 0;  // Tracks current time
        double totalWait = 0, totalTurnaround = 0;  // For calculating averages

        System.out.println("Gantt Chart:");
        for (cpuProcess p : processes) {
            if (time < p.arrivalTime) time = p.arrivalTime;  // If current time is before process arrival, jump forward
            p.waitingTime = time - p.arrivalTime;  // Calculate waiting time
            p.turnaroundTime = p.waitingTime + p.burstTime;  // Calculate turnaround time
            time += p.burstTime;  // Update current time after process execution
            totalWait += p.waitingTime;
            totalTurnaround += p.turnaroundTime;
            System.out.print("[P" + p.pid + "|" + time + "] ");  // Print Gantt chart output
        }
        displayResults(processes, totalWait, totalTurnaround);  // Display final results
    }

    // Shortest Job First Scheduling Algorithm (SJF)
    public static void sjf(List<cpuProcess> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort processes by arrival time
        PriorityQueue<cpuProcess> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));  // Min-heap based on burst time
        int time = 0, index = 0;
        double totalWait = 0, totalTurnaround = 0;

        System.out.println("Gantt Chart:");
        while (index < processes.size() || !pq.isEmpty()) {
            // Add all processes that have arrived by the current time to the priority queue
            while (index < processes.size() && processes.get(index).arrivalTime <= time) {
                pq.add(processes.get(index++));
            }

            if (!pq.isEmpty()) {
                cpuProcess p = pq.poll();  // Get the process with the shortest burst time
                p.waitingTime = time - p.arrivalTime;  // Calculate waiting time
                p.turnaroundTime = p.waitingTime + p.burstTime;  // Calculate turnaround time
                time += p.burstTime;  // Update time
                totalWait += p.waitingTime;
                totalTurnaround += p.turnaroundTime;
                System.out.print("[P" + p.pid + "|" + time + "] ");
            } else {
                time++;  // If no process is ready, just increment time
            }
        }
        displayResults(processes, totalWait, totalTurnaround);  // Display final results
    }

    // Round-Robin Scheduling Algorithm
    public static void roundRobin(List<cpuProcess> processes, int quantum) {
        Queue<cpuProcess> queue = new LinkedList<>(processes);  // Queue to hold processes
        int time = 0;
        double totalWait = 0, totalTurnaround = 0;

        System.out.println("Gantt Chart:");
        while (!queue.isEmpty()) {
            cpuProcess p = queue.poll();  // Dequeue the next process
            if (time < p.arrivalTime) time = p.arrivalTime;  // Jump to arrival time if needed
            int executeTime = Math.min(quantum, p.remainingTime);  // Execute process for quantum or remaining time
            time += executeTime;
            p.remainingTime -= executeTime;  // Reduce remaining time
            System.out.print("[P" + p.pid + "|" + time + "] ");  // Print Gantt chart output

            if (p.remainingTime > 0) {
                queue.add(p);  // Re-add process to the queue if not completed
            } else {
                p.turnaroundTime = time - p.arrivalTime;  // Calculate turnaround time
                p.waitingTime = p.turnaroundTime - p.burstTime;  // Calculate waiting time
                totalWait += p.waitingTime;
                totalTurnaround += p.turnaroundTime;
            }
        }
        displayResults(processes, totalWait, totalTurnaround);  // Display final results
    }

    // Shortest Remaining Time First (SRT) Scheduling Algorithm
    public static void srt(List<cpuProcess> processes) {
        int time = 0, completed = 0, n = processes.size();
        double totalWait = 0, totalTurnaround = 0;

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort by arrival time
        PriorityQueue<cpuProcess> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));  // Min-heap based on remaining time

        System.out.println("Gantt Chart:");

        int index = 0;  // Index to track process arrivals
        cpuProcess currentProcess = null;  // Currently running process

        while (completed < n) {
            // Add processes that have arrived by current time to the queue
            while (index < n && processes.get(index).arrivalTime <= time) {
                pq.add(processes.get(index));
                index++;
            }

            if (!pq.isEmpty()) {
                cpuProcess nextProcess = pq.poll();  // Get process with the shortest remaining time

                // Preempt the current process if necessary
                if (currentProcess != null && currentProcess != nextProcess && currentProcess.remainingTime > 0) {
                    pq.add(currentProcess);  // Re-add preempted process to the queue
                }

                currentProcess = nextProcess;  // Set current process
                currentProcess.remainingTime--;  // Execute process for 1 unit of time
                time++;
                System.out.print("[P" + currentProcess.pid + "|" + time + "] ");

                // If process completes, calculate waiting and turnaround times
                if (currentProcess.remainingTime == 0) {
                    completed++;
                    currentProcess.turnaroundTime = time - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWait += currentProcess.waitingTime;
                    totalTurnaround += currentProcess.turnaroundTime;
                    currentProcess = null;  // Reset current process
                }
            } else {
                // If no process in the queue, jump to the next arrival time
                if (index < n) {
                    time = processes.get(index).arrivalTime;
                } else {
                    break;  // No more processes to schedule
                }
            }
        }

        displayResults(processes, totalWait, totalTurnaround);  // Display final results
    }

    // Display results for all scheduling algorithms
    private static void displayResults(List<cpuProcess> processes, double totalWait, double totalTurnaround) {
        System.out.println("\nProcess\tWaiting Time\tTurnaround Time");
        for (cpuProcess p : processes) {
            System.out.println("P" + p.pid + "\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
        }
        // Calculate and display average waiting and turnaround times
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWait / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaround / processes.size());
    }
}
