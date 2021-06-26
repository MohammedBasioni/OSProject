import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class OS {
    public static class Pair {
        String key, value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    static Object[] memory;
    private Scheduler scheduler;

    public OS() {
        memory = new Object[(int) 1e5];
    }

    public void setMemory(int index, Object o) {
        System.out.println("Writting " + o + " in memory at index :" + index);
        memory[index] = o;
    }

    public Object getMemory(int index) {
        System.out.println("Reading " + memory[index] + " From memory at index :" + index);
        return memory[index];
    }

    // assign instruction
    public void assign(String name, Object value, int start, int end) throws OSException {
        validateValueIsString(value);
        if (contains(name, start, end))
            update(name, (String) value, start, end);
        else
            insert(name, (String) value, start, end);
    }

    private void update(String name, String value, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (memory[i] == null)
                continue;
            Object temp = memory[i];
            if (temp instanceof Pair) {
                Pair pair = (Pair) temp;
                if (pair.key.equals(name)) {
                    pair.value = value;
                    setMemory(i, pair);
                    return;
                }
            }
        }
    }

    private boolean contains(String value, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (memory[i] == null)
                continue;
            Object temp = getMemory(i);
            if (temp instanceof Pair) {
                Pair pair = (Pair) temp;
                if (pair.key.equals(value))
                    return true;
            }
        }
        return false;
    }

    private void insert(String name, String value, int start, int end) {
        for (int i = start; i <= end; i++)
            if (memory[i] == null) {
                Pair temp = new Pair(name, value);
                System.out.println("Inserting " + temp + " in memory at index:" + i);
                memory[i] = new Pair(name, value);
                return;
            }
    }

    // print instruction
    public void print(String data, int start, int end) throws OSException {

        if (contains(data, start, end))
            data = fetch(data, start, end);
        System.out.println("----------------------------------------------------------------");
        System.out.println(data);
        System.out.println("----------------------------------------------------------------");

    }

    private String fetch(String data, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (memory[i] == null)
                continue;
            Object temp = getMemory(i);
            if (temp instanceof Pair) {
                Pair pair = (Pair) temp;
                if (pair.key.equals(data))
                    return ((Pair) memory[i]).value;
            }
        }
        return null;
    }

    // add instruction
    public void add(String variable1, String variable2, int start, int end) throws OSException {
        validateExistingVariable(variable1, start, end);
        validateExistingVariable(variable2, start, end);
        int var1 = validateValueIsInteger(fetch(variable1, start, end));
        int var2 = validateValueIsInteger(fetch(variable2, start, end));
        assign(variable1, (var1 + var2) + "", start, end);
    }

    public void writeFile(String fileName, String data, int start, int end) throws IOException {
        if (contains(data, start, end))
            data = fetch(data, start, end);
        if (contains(fileName, start, end))
            fileName = fetch(fileName, start, end);
        FileWriter file = new FileWriter(fileName);
        file.write(data);
        file.close();
    }

    public String readFile(String fileName, int start, int end) throws IOException {
        String data = "";
        if (contains(fileName, start, end))
            fileName = fetch(fileName, start, end);
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void run(String[] line, int start, int end) throws IOException, OSException {
        Scanner sc = new Scanner(System.in);
        switch (line[0]) {
            case "assign" -> {
                String data = "";
                if (line[2].equals("input"))
                    data = sc.nextLine();
                else if (line[2].equals("readFile"))
                    data = readFile(line[3], start, end);
                else
                    data = line[2];
                assign(line[1], data, start, end);
            }
            case "print" -> print(line[1], start, end);
            case "add" -> add(line[1], line[2], start, end);
            case "writeFile" -> writeFile(line[1], line[2], start, end);
            case "readFile" -> readFile(line[1], start, end);
        }
    }

    // Validations
    public void validateExistingVariable(String name, int start, int end) throws OSException {
        if (!contains(name, start, end))
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
