import java.util.HashMap;

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
