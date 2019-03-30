# CSCI_232_Course_Work
Course work for CSCI 232 (Data Structures and Algorithms II)

Includes project files for assignments in Data Structures and Algorithms II


JOB SCHEDULER

Job Scheduler is a program that prioritizes the tasks for an operating system. Input is taken as a file with lines of the form
job_number priority arrival_time duration
All values in the input file are integers and each job number is unique. priority descrbes how important the job is (higher values means higher priority). arrival_time describes when the job should enter the operating system and duration describes how long the job takes to run. The program uses a priority queue to sort the tasks of the scheduler and then prints the second-by-second runtime information to the console.


BIN PACKER

Bin Packer is a program that implements different algorithms for packing bins with data. Input is taken as a file with one integer on each line. The algorithms are First Fit (inserting the values in the first bin that can fit the value), Best Fit Decreasing (inserting the value into the bin that has the least remaining space among bins that can store the value), and Worst Fit Decreasing (inserting the value into the value into the bin that has the most remaining space). For the first algorithm, a linked list is used and for the other two, a sorted symbol table is used (where the key is the remaining space and the value is a list of bins). Output is the number of bins used for each algorithm.
NOTE: Files BST.java Queue.java StdIn.java and StdOut.java are not my work. They are taken from a textbook


GPS GRAPH

GPS Graph is a program that implements an edge-weighted, directed graph and uses Dijkstra's Algorithm to find the shortest path from one vertex to another vertex (both are given by the user). This file input has several types of lines. If a line in a file begins with a 'c,' that line is a comment and is ignored by the program. A line that defines an edge and begins with an 'a.' Such lines have the form
a tail head weight
where tail, head, and weight are all positive integers and the values apply to the edge that will be created from this line. The final type of input line begins with a 'p.' There is only one of these lines in the file and it must come before any lines that define an edge. Such lines are of the form
p sp V E
where V and E are both positive integers. V defines the number of vertices in the graph, and E defines the number of edges in the graph. Additionally, all values of tail and head must be less than or equal to the value of V.
