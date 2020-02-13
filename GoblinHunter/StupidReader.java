import java.io.*;
import java.util.*;
public class StupidReader {
    public static void main(String[] args) {
        ArrayList<Thread> t = new ArrayList<Thread>();
        try (
            InputStream in = new FileInputStream(args[0]);
        ) {
            byte[] fil = in.readAllBytes();
            boolean header = false;
            for (int x = 0; x<fil.length; x++) {
                if (fil[x] == -1) {
                    if (fil[x+1] == -40 && fil[x+2] == -1 ){
                        String gname = "goblin" + t.size() + ".jpg";
                        Cut tmp = new Cut(fil, x, gname);
                        t.add(new Thread(tmp));
                        t.get(t.size()-1).start();
                        System.out.println("creating child process to create " + gname);

                    }
                }
                else {

                }
            }
            System.out.println("Parent complete");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Cut implements Runnable {
    public byte[] file;
    public int position;
    public OutputStream out;
    public int ignore = 0;
    public Cut(byte[] b, int p,String name) {
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
