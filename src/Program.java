import java.util.HashMap;

public class Program {



    private HashMap<String,Integer> variables;

    public Program() {
        this.variables = new HashMap<>();
    }

    // assign instruction
    public void assign(String name, Object value) throws OSException {
            validateValue(value);
            variables.put(name, (Integer) value);
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
        variables.put(variable1,variables.get(variable1)+variables.get(variable2));
    }




    // Validations
    public void validateExistingVariable(String name) throws OSException {
        if (!variables.containsKey(name))
            throw new OSException("We can NOT find a variable with name: " + name+ ".");
    }

    public void validateValue(Object value) throws OSException {
        if (!(value instanceof Integer))
            throw new OSException("The value entered is NOT a number! This instruction only supports numbers.");
    }

    // Getters and Setters

    public void setVariables(HashMap<String, Integer> variables) {
        this.variables = variables;
    }

    public HashMap<String, Integer> getVariables() {
        return variables;
    }

}
