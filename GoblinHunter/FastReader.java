import java.io.*;
import java.util.*;
public class FastReader {
    public static void main(String[] args) {
        ArrayList<Thread> t = new ArrayList<Thread>();
        try (
            InputStream in = new FileInputStream("GoblinsV2.dd");
        ) {
            byte[] fil = in.readAllBytes();

            // Main loop running through the file right here
            for (int x = 0; x<fil.length; x++) {
                fil[x] = fil[x] &
                System.out.println(fil[x]);
                if (fil[x] == 255) {
                    //TODO fix this trailing number ?
                    if (fil[x+1] == 216 && fil[x+2] == 255 && fil[x+3] == 224){
                        //make a thread here
                        String gname = "goblin" + t.size() + ".jpg";
                        Cutter tmp = new Cutter(fil, x, gname);
                        t.add(new Thread(tmp));
                        t.get(t.size()).start();
                        System.out.println("creating child, did it work ? ");
                    }
                    //TODO make a flag for when exif's are found to skip making a thread for the next image found
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
                byte current = file[position];
                System.out.println(current);
                out.write(current);
                if (current == 255) {
                    position++;
                    if (current ==217) {
                        out.write(current);
                        out.close();
                        break;
                    }
                    else {
                        position--;
                    }
                }
                position++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
