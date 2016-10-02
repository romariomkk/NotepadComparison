package tasklistStringExtract;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by romariomkk on 02.10.2016.
 */
public class CloseButtonProcessing implements ActionListener {

    JPanel panel;
    boolean state;

    public CloseButtonProcessing(JPanel panel, boolean state) {
        this.panel = panel;
        this.state = state;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Runtime runObj = Runtime.getRuntime();

        try {
            closeHighMemorySucker(runObj, "notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeHighMemorySucker(Runtime runObj, String taskName) throws IOException {
        Process proc = runObj.exec("tasklist");
        if (Checker.checkAndPrintIfPresent(proc, taskName) == 2) {
            compareProcesses(taskName);
        } else {
            JOptionPane.showMessageDialog(panel, "Not enough tasks opened", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void compareProcesses(String taskName) throws IOException {
        ArrayList<ArrayList<String>> lineInfo = new ArrayList<>();
        Collections.addAll(lineInfo, new ArrayList<>(), new ArrayList<>());

        Process proc = Runtime.getRuntime().exec("tasklist");
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String taskLine;
            int counter = 0;
            while ((taskLine = buf.readLine()) != null) {
                if (taskLine.startsWith(taskName)) {
                    StringTokenizer strToken = new StringTokenizer(taskLine, " ");
                    while (strToken.hasMoreTokens()) {
                        lineInfo.get(counter).add(strToken.nextToken());
                    }
                    counter++;
                }
            }
        }
        System.out.println(lineInfo);

        closeHighConsumingProcess(lineInfo);
    }

    private void closeHighConsumingProcess(ArrayList<ArrayList<String>> lineInfo) throws IOException {
        String memory1 = lineInfo.get(0).get(4);
        String memory2 = lineInfo.get(1).get(4);

        Integer memOfPad1 = Integer.parseInt(getMemoryValueFromInfo(memory1));
        Integer process1ID = Integer.parseInt(lineInfo.get(0).get(1));

        Integer memOfPad2 = Integer.parseInt(getMemoryValueFromInfo(memory2));
        Integer process2ID = Integer.parseInt(lineInfo.get(1).get(1));

        if (memOfPad1 > memOfPad2)
            closeTask(process1ID);
        else if (memOfPad1 < memOfPad2)
            closeTask(process2ID);
        else
            JOptionPane.showMessageDialog(panel, "Both tasks consume equal memory.\nNone closed",
                    "Task closing failed", JOptionPane.WARNING_MESSAGE);
    }

    private String getMemoryValueFromInfo(String mem1) {
        return (mem1.length() > 4) ? longMemoryValue(mem1) : mem1;
    }

    private String longMemoryValue(String mem1) {
        String[] newStr = mem1.split(String.valueOf(mem1.charAt(mem1.length() - 4)));
        StringBuilder strBuilder = new StringBuilder();

        for (String s : newStr) {
            strBuilder = strBuilder.append(s);
        }
        return strBuilder.toString();
    }

    private void closeTask(Integer pid) throws IOException {
        Runtime.getRuntime().exec("taskkill /PID " + pid);
    }

}
