import java.util.*;
public class Unsigned {
    public static void main(String[] args) {
        //int [] unsigned = new int [260];
        ArrayList<Integer> unsigned = new ArrayList<Integer>();
        for (int x=-128;x<128;x++){
            unsigned.add(x);
        }
        for (int z = 0;z<unsigned.size();z++) {
            Integer out = unsigned.get(z) & 0xff;
            System.out.println(unsigned.get(z).toString() + "signed = " + out.toString());
        }
    }
}
