import java.util.*;

public class CPUScheculing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------------");
            System.out.println("\nCPU Scheduling Simulator");
            System.out.println("1. First-Come, First-Served (FCFS)");
            System.out.println("2. Shortest-Job-First (SJF)");
            System.out.println("3. Shortest-Remaining-Time (SRT)");
            System.out.println("4. Round Robin (RR)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            List<cpuProcess> processes = new ArrayList<>();
            if (choice >= 1 && choice <= 4) {
                System.out.print("Enter number of processes: ");
                int n = scanner.nextInt();
                for (int i = 1; i <= n; i++) {
                    System.out.print("Enter arrival time for P" + i + ": ");
                    int arrival = scanner.nextInt();
                    System.out.print("Enter burst time for P" + i + ": ");
                    int burst = scanner.nextInt();
                    processes.add(new cpuProcess(i, arrival, burst));
                }
            }
            
            switch (choice) {
                case 1:
                    cpuProcess.fcfs(processes);
                    break;
                case 2:
                    cpuProcess.sjf(processes);
                    break;
                case 3:
                    cpuProcess.srt(processes);
                    break;
                case 4:
                    System.out.print("Enter time quantum: ");
                    int quantum = scanner.nextInt();
                    cpuProcess.roundRobin(processes, quantum);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

