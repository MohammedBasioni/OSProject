import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Program {



    private HashMap<String,String> variables;
    private String filePath;

    public Program(String filePath) {
        this.filePath = filePath;
        this.variables = new HashMap<>();
    }

    // assign instruction
    public void assign(String name, Object value) throws OSException {
            validateValueIsString(value);
        if (variables.containsKey(value))
            value = variables.get(value);
        variables.put(name, (String) value);
    }

    // print instruction
    public void print(String data) throws OSException {
        if (variables.containsKey(data))
            data = variables.get(data);
        System.out.println(data);
    }

    // add instruction
    public void add(String variable1, String variable2) throws OSException {
        validateExistingVariable(variable1);
        validateExistingVariable(variable2);
        int var1 = validateValueIsInteger(variables.get(variable1));
        int var2 = validateValueIsInteger(variables.get(variable2));
        variables.put(variable1,(var1+var2)+"");
    }

    public void writeFile(String fileName, String data) throws IOException {
        if (variables.containsKey(data))
            data = variables.get(data);
        if (variables.containsKey(fileName))
            fileName = variables.get(fileName);
        FileWriter file = new FileWriter(fileName);
        file.write(data);
        file.close();
    }

    public String readFile(String fileName) throws IOException {
        String data = "";
        if (variables.containsKey(fileName))
            fileName = variables.get(fileName);
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void run() throws IOException, OSException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while (br.ready()){
            String[] line = br.readLine().split(" ");
            if (line[0].equals("assign")){
                String data = "";
                if (line[2].equals("input"))
                    data = sc.nextLine();
                else if (line[2].equals("readFile"))
                    data = readFile(line[3]);
                else
                    data = line[2];
                assign(line[1],data);
            }
            else if (line[0].equals("print"))
                print(line[1]);
            else if (line[0].equals("add"))
                add(line[1],line[2]);
            else if (line[0].equals("writeFile"))
                writeFile(line[1],line[2]);
            else if (line[0].equals("readFile"))
                readFile(line[1]);
        }
    }

    // Validations
    public void validateExistingVariable(String name) throws OSException {
        if (!variables.containsKey(name))
            throw new OSException("We can NOT find a variable with name: " + name+ ".");
    }

    public void validateValueIsString(Object value) throws OSException {
        if (!(value instanceof String))
            throw new OSException("The type of the value entered is NOT supported.");
    }

    public int validateValueIsInteger(String value) throws OSException {
        int result;
        try {
            result = Integer.parseInt(value);
        }
        catch (Exception e) {
            throw new OSException("The value/s of the variable/s is/are not number/s.");
        }
        return result;
    }

    public static void main(String[] args) throws IOException, OSException {
        Program program1 = new Program("Program 1.txt");
        program1.run();
        Program program2 = new Program("Program 2.txt");
        program2.run();
        Program program3 = new Program("Program 3.txt");
        program3.run();

    }


}
