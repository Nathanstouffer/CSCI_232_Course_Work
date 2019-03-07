# CSCI_232_Course_Work
Course work for CSCI 232 (Data Structures and Algorithms II)

Includes project files for assignments in Data Structures and Algorithms II


JOB SCHEDULER

Job Scheduler is a program that prioritizes the tasks for an operating system based on priority. Input is taken as a file with lines of the form:
job_number priority arrival_time duration
All values in the input file are integers and each job number is unique. priority descrbes how important the job is (higher values means higher priority). arrival_time describes when the job should enter the operating system and duration describes how long the job takes to run. The program uses a priority queue to sort the tasks of the scheduler and then prints the second-by-second runtime information to the console.


BIN PACKER

Bin Packer is a program that implements different algorithms for packing bins with data. Input is taken as a file with one integer on each line. The algorithms are First Fit (inserting the values in the first bin that can fit the value), Best Fit Decreasing (inserting the value into the bin that has the least remaining space among bins that can store the value), and Worst Fit Decreasing (inserting the value into the value into the bin that has the most remaining space). For the first algorithm, a linked list is used and for the other two, a sorted symbol table is used (key: remaining space value: list of bins). Output is the number of bins used for each algorithm.
NOTE: Files BST.java Queue.java StdIn.java and StdOut.java are not my work. They are taken from a textbook
