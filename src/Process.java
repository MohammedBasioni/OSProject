import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Process {


    private HashMap<String, String> variables;
    private String filePath;

    public Process(String filePath) {
        this.filePath = filePath;
        this.variables = new HashMap<>();
    }

    // assign instruction
    public void assign(String name, Object value) throws OSException {
        validateValueIsString(value);
        if (contains((String)value))
            value = fetch((String)value);
        insert(name, (String) value);
    }

    private boolean contains(String value) {
        return variables.containsKey(value);
    }

    private void insert(String name, String value) {
        variables.put(name, (String) value);
    }

    // print instruction
    public void print(String data) throws OSException {
        if (contains(data))
            data = fetch(data);
        System.out.println(data);
    }

    private String fetch(String data) {
        return variables.get(data);
    }

    // add instruction
    public void add(String variable1, String variable2) throws OSException {
        validateExistingVariable(variable1);
        validateExistingVariable(variable2);
        int var1 = validateValueIsInteger(fetch(variable1));
        int var2 = validateValueIsInteger(fetch(variable2));
        insert(variable1, (var1 + var2) + "");
    }

    public void writeFile(String fileName, String data) throws IOException {
        if (contains(data))
            data = fetch(data);
        if (contains(fileName))
            fileName = fetch(fileName);
        FileWriter file = new FileWriter(fileName);
        file.write(data);
        file.close();
    }

    public String readFile(String fileName) throws IOException {
        String data = "";
        if (contains(fileName))
            fileName =fetch(fileName);
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void run() throws IOException, OSException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while (br.ready()) {
            String[] line = br.readLine().split(" ");
            switch (line[0]) {
                case "assign" -> {
                    String data = "";
                    if (line[2].equals("input"))
                        data = sc.nextLine();
                    else if (line[2].equals("readFile"))
                        data = readFile(line[3]);
                    else
                        data = line[2];
                    assign(line[1], data);
                }
                case "print" -> print(line[1]);
                case "add" -> add(line[1], line[2]);
                case "writeFile" -> writeFile(line[1], line[2]);
                case "readFile" -> readFile(line[1]);
            }
        }
    }

    // Validations
    public void validateExistingVariable(String name) throws OSException {
        if (!contains(name))
            throw new OSException("We can NOT find a variable with name: " + name + ".");
    }

    public void validateValueIsString(Object value) throws OSException {
        if (!(value instanceof String))
            throw new OSException("The type of the value entered is NOT supported.");
    }

    public int validateValueIsInteger(String value) throws OSException {
        int result;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            throw new OSException("The value/s of the variable/s is/are not number/s.");
        }
        return result;
    }


}
