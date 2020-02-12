//Multiple forking demonstration - explain the output
//Created for CS3600
//1-30-20
//-Dr. G
 
#include <iostream>
#include <unistd.h>
#include <sys/wait.h>
 
using namespace std;
 
int main()
{ 
    //Make an array of processes 
    pid_t processArray[10];
    int i;
    int total = 0;
    int status; //used to hold terminating child state
 
    //Create an array of 100 ints
    int array[100];
     
    //fill in that array with int values 0-99
    //Sum would equal 4950
    for (i=0; i<100; i++)
        array[i]=i;
 
    //Iterate through the array of processes
    for (i = 0; i < 10; i++)
    {
        processArray[i] = fork();
         
      if ((processArray[i]) < 0)
        cout << "failed to start child " << i << endl;
      else
            if (processArray[i] == 0)
            {
                for (int x = (i * 10); x < (i*10) + 10; x++)
                    total += array[x];
                 
                cout << "Child process " << getpid() << " finished"<< endl;
                exit(0);
             }
            else //parent
                {
                    
                }
    }//end processArray for loop
    
    cout << "Waiting" << endl;
    //wait for each child to die
    for(int i=0; i<10; i++)
		waitpid(processArray[i], &status, 0);
    
	cout << total << endl;
}