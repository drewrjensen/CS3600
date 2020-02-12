import java.io.*;
import java.util.*;
public class FastReader {
    public static void main(String[] args) {
        ArrayList<Thread> t = new ArrayList<Thread>();
        try (
            InputStream in = new FileInputStream("Goblins.dd");
        ) {
            byte[] fil = in.readAllBytes();
            boolean header = false;
            // Main loop running through the file right here
            for (int x = 0; x<fil.length; x++) {
                //fil[x] = fil[x] &
                System.out.println(fil[x]);
                if (fil[x] == -1) {//255
                    //TODO fix this trailing number ?
                    //if (fil[x+1] == 216 && fil[x+2] == 255 && fil[x+3] == 224){
                    if (fil[x+1] == -40 && fil[x+2] == -1 && fil[x+3] == -32){
                        if (header == false) {
                            //make a thread here
                            String gname = "goblin" + t.size() + ".jpg";
                            Cutter tmp = new Cutter(fil, x, gname);
                            t.add(new Thread(tmp));
                            t.get(t.size()-1).start();
                            System.out.println("creating child, did it work ? ");
                        }else {
                            header = false;
                        }
                    }
                    // checks for EXIF, and skips making this image if it finds one
                    if (fil[x] == 69) {
                        if (fil[x+1] == 120 && fil[x+2]== 105 && fil[x+3]== 102) {
                            header = true;
                        }
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
                if (current == -1) {//255
                    position++;
                    if (current ==-39) {//217
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
