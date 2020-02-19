import java.io.*;
import java.util.*;
public class FastReaderv2 {
    public static void main(String[] args) {
        ArrayList<Thread> t = new ArrayList<Thread>();
        try (
            InputStream in = new FileInputStream(args[0]);
        ) {
            byte[] fil;
            try {
                fil = in.readAllBytes();
            } catch (Exception e) {
                // imagine not having java 9
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = in.read(data, 0, data.length)) != -1) {
                  buffer.write(data, 0, nRead);
                }

                fil = buffer.toByteArray();
            }
            boolean header = false;
            // Main loop running through the file right here
            for (int x = 0; x<fil.length; x++) {
                if (fil[x] == -1) {
                    if (fil[x+1] == -40 && fil[x+2] == -1 ){
                        if (header == false) {
                            //make a thread here
                            String gname = "goblin" + t.size() + ".jpg";
                            Cutter tmp = new Cutter(fil, x, gname);
                            t.add(new Thread(tmp));
                            t.get(t.size()-1).start();
                            System.out.println("creating child process to create " + gname  + " from position:" + x);
                        }else {
                            header = false;
                        }
                    }
                    // checks for EXIF, and skips making this image if it finds one
                    //if (fil[x] == -1) {
                //        if (fil[x+1] == -31) {
                //            header = true;
                //        }
                //    }
                    //if (fil[x] == 69) {
                    //    if (fil[x+1] == 120 && fil[x+2]== 105 && fil[x+3]== 102) {
                    //        header = true;
                    //    }
                    //}
                }
            }
            System.out.println("Parent complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Cutter implements Runnable {
    public byte[] file;
    public int position;
    public OutputStream out;
    //public int ignore = 0;
    public Cutter(byte[] b, int p,String name) {
        file = b;
        position=p;
        try {
            out = new FileOutputStream(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            while (position < file.length) {
                out.write(file[position]);
                if (file[position] == -1) {
                    if (file[position+1] ==-39) {
                        out.write(file[position+1]);
                        out.close();
                        break;
                        }

                    }
                position++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
