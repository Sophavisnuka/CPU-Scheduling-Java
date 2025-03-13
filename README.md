# CPU Scheduling Simulator in C++

This project simulates four CPU scheduling algorithms:
1. **First-Come, First-Served (FCFS)**
2. **Shortest-Job-First (SJF)**
3. **Shortest-Remaining-Time (SRT)**
4. **Round Robin (RR)**

The program provides a menu-driven interface for users to select a scheduling algorithm, input process details, and view the scheduling results, including Gantt charts, waiting times, turnaround times, and average waiting/turnaround times.

## Features
- **Menu-Driven Interface**: Users can select a scheduling algorithm from the menu.
- **Input Validation**: Handles invalid inputs (e.g., negative burst times, non-integer inputs).
- **Output Details**:
  - Gantt Chart: Visual representation of the scheduling order.
  - Waiting Times: Waiting time for each process.
  - Turnaround Times: Turnaround time for each process.
  - Average Waiting Time: Average waiting time for all processes.
  - Average Turnaround Time: Average turnaround time for all processes.

## How to Compile and Run the Program

### Prerequisites
- Clone this repo to get the following file
- Run the main Project (CPUScheduling)

### Steps
1. **Clone the Repository** (if not already done):
   ```bash
   git clone https://github.com/Sophavisnuka/CPU-Scheduling-Java.git
   cd CPU-Scheduling-Java

2. **Compile the Program:**
   ```bash
    javac CPUScheduling.java
    java CPUScheduling

3. **Run the Program:**
Execute the compiled program:

   ```bash
   ./CPUScheduling

4. **Follow the On-Screen Instructions:**
- Select a scheduling algorithm from the menu.
- Enter the number of processes and their details (Process ID, Arrival Time, Burst Time).
- For Round Robin, enter the time quantum.
- View the results (Gantt chart, waiting times, turnaround times, and averages).
