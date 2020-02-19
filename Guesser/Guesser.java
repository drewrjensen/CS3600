//Password Guesser
//Frame for lab assignment
//1-30-2020
 
/*
Consider the following program that "guesses" to figure out a password based on it's CRC32 hash.
The password could be anything in the ASCII range from 0x30 - 0x7A
 
 
1. Add the capability to do this guessing with multiple threads. 15 pts
2. Use shared memory to let a child know it doesn't have to keep guessing. 10 pts
3. Experiment with the number of threads. Is there any speed up with 2, 3, 4? 5 pts
*/
 
 
import java.util.zip.CRC32;
import java.security.SecureRandom;
import java.util.Date;
 
public class Guesser implements Runnable {
    public Guesser(long password) {

    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + "started.");
        }
        
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public static void main(String [] args) {
        CRC32 cr = new CRC32();
        SecureRandom r = new SecureRandom();
        Date date = new Date();
        long guess = 0;
         
        byte[] b = new byte[3];
        long passwd = 2656977832L; //three char password. Very little time needed.
        //byte [] b = new byte[4];
        //long passwd = 3281894034L; //four char password. A little time needed. 
        //byte [] b = new byte[5];
        //long passwd = 2636021861L;    //five char password. More time needed. 
         
        //Start time
        System.out.println(date.toString());
         
        while (guess != passwd) {
            b[0] = (byte)(r.nextInt(75) + '0');
            b[1] = (byte)(r.nextInt(75) + '0');
            b[2] = (byte)(r.nextInt(75) + '0');
            //b[3] = (byte)(r.nextInt(75) + '0');
            //b[4] = (byte)(r.nextInt(75) + '0');
             
            cr.reset();
            cr.update(b);
             
            guess = cr.getValue();  
        }
         
        //Print the password
        System.out.print((char)b[0]);
        System.out.print((char)b[1]);
        System.out.print((char)b[2]);
        //System.out.print((char)b[3]);
        //System.out.print((char)b[4]);
        System.out.println();
     
        //end time
        System.out.println(date.toString());
    }
}