import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Program {



    private HashMap<String,String> variables;

    public Program() {
        this.variables = new HashMap<>();
    }

    // assign instruction
    public void assign(String name, Object value) throws OSException {
            validateValueIsString(value);
            variables.put(name, (String) value);
    }
    // print instruction
    public void print(String name) throws OSException {
            validateExistingVariable(name);
            System.out.println(variables.get(name));
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
        FileWriter file = new FileWriter(fileName +".txt");
        file.write(data);
        file.close();
    }

    public String readFile(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName+".txt")));
        return data;
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

    // Getters and Setters

    public HashMap<String, String> getVariables() {
        return variables;
    }

}
