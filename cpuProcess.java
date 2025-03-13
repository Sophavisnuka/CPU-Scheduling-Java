import java.util.*;

public class cpuProcess {
    int pid, arrivalTime, burstTime, waitingTime, turnaroundTime, remainingTime;
    public cpuProcess(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public static void fcfs(List<cpuProcess> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int time = 0;
        double totalWait = 0, totalTurnaround = 0;

        System.out.println("Gantt Chart:");
        for (cpuProcess p : processes) {
            if (time < p.arrivalTime) time = p.arrivalTime;
            p.waitingTime = time - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            time += p.burstTime;
            totalWait += p.waitingTime;
            totalTurnaround += p.turnaroundTime;
            System.out.print("[P" + p.pid + "|" + time + "] ");
        }
        displayResults(processes, totalWait, totalTurnaround);
    }

    public static void sjf(List<cpuProcess> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        PriorityQueue<cpuProcess> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        int time = 0, index = 0;
        double totalWait = 0, totalTurnaround = 0;

        System.out.println("Gantt Chart:");
        while (index < processes.size() || !pq.isEmpty()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= time) {
                pq.add(processes.get(index++));
            }

            if (!pq.isEmpty()) {
                cpuProcess p = pq.poll();
                p.waitingTime = time - p.arrivalTime;
                p.turnaroundTime = p.waitingTime + p.burstTime;
                time += p.burstTime;
                totalWait += p.waitingTime;
                totalTurnaround += p.turnaroundTime;
                System.out.print("[P" + p.pid + "|" + time + "] ");
            } else {
                time++;
            }
        }
        displayResults(processes, totalWait, totalTurnaround);
    }

    public static void roundRobin(List<cpuProcess> processes, int quantum) {
        Queue<cpuProcess> queue = new LinkedList<>(processes);
        int time = 0;
        double totalWait = 0, totalTurnaround = 0;

        System.out.println("Gantt Chart:");
        while (!queue.isEmpty()) {
            cpuProcess p = queue.poll();
            if (time < p.arrivalTime) time = p.arrivalTime;
            int executeTime = Math.min(quantum, p.remainingTime);
            time += executeTime;
            p.remainingTime -= executeTime;
            System.out.print("[P" + p.pid + "|" + time + "] ");

            if (p.remainingTime > 0) {
                queue.add(p);
            } else {
                p.turnaroundTime = time - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                totalWait += p.waitingTime;
                totalTurnaround += p.turnaroundTime;
            }
        }
        displayResults(processes, totalWait, totalTurnaround);
    }
    public static void srt(List<cpuProcess> processes) {
        int time = 0, completed = 0, n = processes.size();
        double totalWait = 0, totalTurnaround = 0;
    
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
    
        // Priority queue to prioritize processes with the shortest remaining time
        PriorityQueue<cpuProcess> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
    
        System.out.println("Gantt Chart:");
    
        int index = 0; // Index to track the next process to arrive
        cpuProcess currentProcess = null; // Currently running process
    
        while (completed < n) {
            // Add all processes that have arrived by the current time to the priority queue
            while (index < n && processes.get(index).arrivalTime <= time) {
                pq.add(processes.get(index));
                index++;
            }
    
            if (!pq.isEmpty()) {
                // Get the process with the shortest remaining time
                cpuProcess nextProcess = pq.poll();
    
                // If the current process is different from the next process, preempt the current process
                if (currentProcess != null && currentProcess != nextProcess && currentProcess.remainingTime > 0) {
                    pq.add(currentProcess); // Re-add the preempted process to the queue
                }
    
                // Update the current process
                currentProcess = nextProcess;
    
                // Execute the current process for 1 unit of time
                currentProcess.remainingTime--;
                time++;
    
                System.out.print("[P" + currentProcess.pid + "|" + time + "] ");
    
                // Check if the current process has completed
                if (currentProcess.remainingTime == 0) {
                    completed++;
                    currentProcess.turnaroundTime = time - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWait += currentProcess.waitingTime;
                    totalTurnaround += currentProcess.turnaroundTime;
                    currentProcess = null; // Reset the current process
                }
            } else {
                // If no processes are in the queue, jump to the next process's arrival time
                if (index < n) {
                    time = processes.get(index).arrivalTime;
                } else {
                    break; // No more processes to schedule
                }
            }
        }
    
        // Display the results
        displayResults(processes, totalWait, totalTurnaround);
    }
    private static void displayResults(List<cpuProcess> processes, double totalWait, double totalTurnaround) {
        System.out.println("\nProcess\tWaiting Time\tTurnaround Time");
        for (cpuProcess p : processes) {
            System.out.println("P" + p.pid + "\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
        }
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWait / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaround / processes.size());
    }
}
