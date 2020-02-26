import java.io.File;
import java.nio.file.Files;

public class Scraper {
    private String inPath;
    private String outPath;
    private byte[] byteArray;

    public Scraper(String inPath, String outPath) {
        if (!inPath.isEmpty()) this.inPath = inPath;
        else this.inPath = "GoblinsV2.dd";

        if (!outPath.isEmpty()) this.outPath = outPath;

        try {
            byteArray = Files.readAllBytes(inPath);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        
    }
}