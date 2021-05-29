import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Program1 {

    public static String readFileAsString(String fileName)throws Exception
    {

    }
    public static void main(String[] args) throws Exception {
        String data = readFileAsString("pp.txt");
        System.out.println(data);
    }
}
