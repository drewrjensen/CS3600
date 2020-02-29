/*
* File carver fast reader (v2)
* Written by:  Ethan Smith, Kaylee Hall, Drew Jensen, Jaeger Christensen
* Date : Feb 25, 2020
* This program takes a binary file, and uses multithreading to extract JPEG images from the binary
*
*/


import java.io.*;
import java.util.*;
public class FastReaderv3 {
    public static void main(String[] args) {
		
		 long nano_startTime = System.nanoTime();
		
        // running requires two command line arguments
        // args[0] ( the first) is the file to read from, allowing to select either goblins.dd or goblinsv2.dd ( defaults to goblinsv2)
        // args[1] (second) allows for setting the output folder... not setting this argument will default to no folder
        String infile;
        try {
            infile = args[0];
        }
        catch (Exception e) {
            infile = "GoblinsV2.dd";
        }
        // if (args[0] != null) {
        //     infile = args[0];
        // }
        // else {
        //
        // }
        ArrayList<Thread> t = new ArrayList<Thread>();
        try (
            InputStream in = new FileInputStream(infile);
        ) {
            // reads in the file, as a byte array, which is apparently faster to read, but also easier to move through
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
            }
            byte[] fil = buffer.toByteArray();
            boolean header = false;
            // Main loop running through the file right here
            for (int x = 0; x<fil.length; x++) {
                if (fil[x] == -1) { // FF
                    if (fil[x+1] == -40 && fil[x+2] == -1 ){ // D8 FF
                        if (!header) {
                            //make a thread whenever a goblin(JPEG header) is found
                            String gname;
                            // checks to see if the CLI arg is set, otherwise sets to default
                            try {
                                //makes the directory if it doesn't exist
                                File dir = new File(args[1]);
                                if (!dir.exists()) dir.mkdir();
                                gname = args[1] + "/goblin" + t.size() + ".jpeg";
                            } catch (Exception e) {
                                gname = "goblin" + t.size() + ".jpg";
                            }
                            Cutter tmp = new Cutter(fil, x, gname);
                            t.add(new Thread(tmp));
                            t.get(t.size()-1).start();
						    try{
						        t.get(t.size()-1).join();
						    } catch (InterruptedException f){
							    System.out.println(f);
                            }
                            System.out.println("creating child process to create " + gname  + " from position:" + x);
                            header = true;
                        }
                    }
                    else if (fil[x+1] == -39) header = false;
                }
            }

            System.out.println("Parent complete");
			long nano_endTime= System.nanoTime();
			System.out.println("Time take in nano seconds: " + (nano_endTime - nano_startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Cutter implements Runnable {
    // cutter is the child thread, and actually makes the images when they are found
    public byte[] file;
    public int position;
    public OutputStream out;
    private String name;
    public Cutter(byte[] b, int p,String name) {
        this.name = name;
        file = b;
        position=p;
        try {
            out = new FileOutputStream(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // this method writes to a new jpeg file until it finds the jpeg termination bytes
    public void run() {
        try {
			//long nano_startTime2 = System.nanoTime();
            while (position < file.length) {
                out.write(file[position]);
                if (file[position] == -1) {
                    if (file[position+1] == -39) {
                        out.write(file[position+1]);
                        out.close();
                        break;
                    }
                    else if (file[position + 1] == -40 && file[position + 2] == -1) {
                        out.close();
                        out = new FileOutputStream(name);
                        out.write(file[position]);
                    }
                }
                position++;
            }
			System.out.println("Finished");
			//long nano_endTime2= System.nanoTime();
			//System.out.println("Total Time for thread to run in nano seconds: " + (nano_endTime2 - nano_startTime2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}