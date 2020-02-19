// Password Guesser
// Drew Jensen
// 18Feb20
 
// 1. Add the capability to do this guessing with multiple threads. 15 pts (done)
//      The class implements Runnable. Threading and guessing is done in test() at line 104.

// 2. Use shared memory to let a child know it doesn't have to keep guessing. 10 pts (done)
//      This is done with the success variables declared on lines 32-34.

// 3. Experiment with the number of threads. Is there any speed up with 2, 3, 4? 5 pts (done)
//      As each thread guesses randomly, and previous guesses aren't stored,
//      each thread can potentially guess the correct password instantly or never.
//      From a limited number of tests, there doesn't seem to be any thread count from 1-5 that's consistently fastest.
 
import java.util.zip.CRC32;
import java.security.SecureRandom;
 
public class Guesser implements Runnable {
    enum Password {
        THREE(2656977832L, 3),
        FOUR(3281894034L, 4),
        FIVE(2636021861L, 5);

        public long hash;
        public int length;
        private Password(long hash, int length) {
            this.hash = hash;
            this.length = length;
        }
    }

    static boolean success3 = false;
    static boolean success4 = false;
    static boolean success5 = false;
    static String result3 = "";
    static String result4 = "";
    static String result5 = "";

    private CRC32 cr;
    private SecureRandom r;
    private Password pwd;
    private byte[] bytes;
    private long guess;
    private String result;

    public Guesser(Password pwd) {
        cr = new CRC32();
        r = new SecureRandom();
        this.pwd = pwd;
        bytes = new byte[pwd.length];
        guess = 0;
        result = "";
    }

    @Override
    public void run() {
        try {
            switch (pwd) {
                case THREE:
                    while (!success3) {
                        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte)(r.nextInt(75) + '0');
                        cr.reset();
                        cr.update(bytes);
                        guess = cr.getValue();
                        if (guess == pwd.hash) {
                            success3 = true;
                            for (byte b : bytes) result3 += (char)b;
                        }
                    }
                break;

                case FOUR:
                    while (!success4) {
                        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte)(r.nextInt(75) + '0');
                        cr.reset();
                        cr.update(bytes);
                        guess = cr.getValue();
                        if (guess == pwd.hash) {
                            success4 = true;
                            for (byte b : bytes) result4 += (char)b;
                        }
                    }
                break;

                case FIVE:
                    while (!success5) {
                        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte)(r.nextInt(75) + '0');
                        cr.reset();
                        cr.update(bytes);
                        guess = cr.getValue();
                        if (guess == pwd.hash) {
                            success5 = true;
                            for (byte b : bytes) result5 += (char)b;
                        }
                    }
                break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Long test(Password pwd, int threadCount) {
        long startTime = System.currentTimeMillis();
        Thread threads[] = new Thread[threadCount];

        String result = "";
        switch (pwd) {
            case THREE:
                success3 = false;
                result3 = "";

                for (int i = 0; i < threads.length; i++) {
                    threads[i] = new Thread(new Guesser(pwd));
                    threads[i].start();
                }

                try {
                    for (Thread t : threads) t.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = result3;
            break;

            case FOUR:
                success3 = false;
                result3 = "";

                for (int i = 0; i < threads.length; i++) {
                    threads[i] = new Thread(new Guesser(pwd));
                    threads[i].start();
                }

                try {
                    for (Thread t : threads) t.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = result4;
            break;

            case FIVE:
                success3 = false;
                result3 = "";

                for (int i = 0; i < threads.length; i++) {
                    threads[i] = new Thread(new Guesser(pwd));
                    threads[i].start();
                }

                try {
                    for (Thread t : threads) t.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = result5;
            break;
        }

        return (System.currentTimeMillis() - startTime);
    }
    
    public static void main(String [] args) {
        for (int i = 1; i <= 5; i++) System.out.println("Chars: 3, ThreadCount: " + i + ", Time: " + test(Password.THREE, i) + "ms" + ", Password: " + result3);
        // for (int i = 1; i <= 5; i++) System.out.println("Chars: 4, ThreadCount: " + i + ", Time: " + test(Password.FOUR, i) + "ms" + ", Password: " + result4); // slow
        // for (int i = 1; i <= 5; i++) System.out.println("Chars: 5, ThreadCount: " + i + ", Time: " + test(Password.FIVE, i) + "ms" + ", Password: " + result5); // infinite loop simulator
    }
}