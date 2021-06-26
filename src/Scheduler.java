import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Scheduler {
    //0->Process ID,1->state , 2->PC ,3->Boundaries(Start),4->Boundaries(End)
    //5->a , 6->b
    //7,8,9->instructions
    //10->Process ID ,  11->state,12->pc,13->Boundaries(start),14->Boundaries(End)
    //15->a  , 16->b
    //17,18,19,20,21 ->instructions
    // 22-> process ID ,23 ->state , 24->pc,25,26 ->Boundaries
    // 27-> a,28->b
    //29,30,31,32,33,34 ->instructions
    Queue<Integer> queue;
    Queue<Integer> runningTime;
    OS os;

    public Scheduler() {
        queue = new LinkedList<>();
        runningTime=new LinkedList<>();
        os = new OS();
        queue.add(0);
        runningTime.add(0);
        queue.add(1);
        runningTime.add(0);
        queue.add(2);
        runningTime.add(0);
    }

    public void run() throws IOException, OSException {
        while (!queue.isEmpty()) {
            int curProcess = queue.poll();
            int time=runningTime.poll();
            time++;
            System.out.println("Running process "+curProcess);
            for(int i=0;i<2;i++) {
                activateProcess(curProcess);
                int pc = getPc(curProcess);
                int nextInstruction = (int) os.getMemory(pc);
                String[] line = ((String) os.getMemory(nextInstruction)).split(" ");
                int start = getStart(curProcess), end = getEnd(curProcess);
                int s = (int) os.getMemory(start), e = (int) os.getMemory(end);
                os.run(line, s, e);
                nextInstruction++;
                if(nextInstruction>e) {
                    System.out.println("Process "+ curProcess+" ran for "+ time+" times");
                    break;
                }else {
                    os.setMemory(pc,nextInstruction);
                }
                if(i==1) {
                    queue.add(curProcess);
                    runningTime.add(time);
                }
            }
            deactivateProcess(curProcess);
        }
    }

    private void deactivateProcess(int curProcess) {
        int stateIndex=getStateIndex(curProcess);
        os.setMemory(stateIndex,false);
    }

    private int getEnd(int curProcess) {
        if (curProcess == 0)
            return 4;
        if (curProcess == 1)
            return 14;
        return 26;
    }

    private int getStart(int curProcess) {
        if (curProcess == 0)
            return 3;
        if (curProcess == 1)
            return 13;
        return 25;
    }

    private void activateProcess(int curProcess) {
        int stateIndex = getStateIndex(curProcess);
        os.setMemory(stateIndex, true);
    }

    private int getStateIndex(int curProcess) {
        if (curProcess == 0)
            return 1;
        if (curProcess == 1)
            return 11;
        return 23;
    }

    private int getPc(int curProcess) {
        if (curProcess == 0)
            return 2;
        if (curProcess == 1)
            return 12;
        return 24;
    }

    public static void main(String[] args) throws IOException, OSException {
        Scheduler scheduler = new Scheduler();
        OS os = scheduler.os;
        os.setMemory(0, 1);
        os.setMemory(1, false);
        os.setMemory(2, 7);
        os.setMemory(3, 0);
        os.setMemory(4, 9);
        BufferedReader br = new BufferedReader(new FileReader("program 1.txt"));
        os.setMemory(7, br.readLine());
        os.setMemory(8, br.readLine());
        os.setMemory(9, br.readLine());
        os.setMemory(10, 2);
        os.setMemory(11, false);
        os.setMemory(12, 17);
        os.setMemory(13, 10);
        os.setMemory(14, 21);
        br = new BufferedReader(new FileReader("program 2.txt"));
        os.setMemory(17, br.readLine());
        os.setMemory(18, br.readLine());
        os.setMemory(19, br.readLine());
        os.setMemory(20, br.readLine());
        os.setMemory(21, br.readLine());
        os.setMemory(22, 1);
        os.setMemory(23, false);
        os.setMemory(24, 29);
        os.setMemory(25, 22);
        os.setMemory(26, 34);
        br = new BufferedReader(new FileReader("program 3.txt"));
        os.setMemory(29, br.readLine());
        os.setMemory(30, br.readLine());
        os.setMemory(31, br.readLine());
        os.setMemory(32, br.readLine());
        os.setMemory(33, br.readLine());
        os.setMemory(34, br.readLine());
        scheduler.run();
    }
}
