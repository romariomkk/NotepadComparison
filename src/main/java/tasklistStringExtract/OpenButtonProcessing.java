package tasklistStringExtract;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.jna.platform.*;

public class OpenButtonProcessing implements Serializable, ActionListener {

    JPanel panel;
    boolean state;
    public OpenButtonProcessing(JPanel panel, boolean state){
        this.panel = panel;
        this.state = state;
    }


    @Override
    public void actionPerformed(ActionEvent ev) {
        Runtime runObj = Runtime.getRuntime();
        try {
            closeTasksOfIfPresent(runObj, "notepad.exe");
            openTwoTasksOf(runObj, "notepad.exe");
            openTaskList(runObj);
        } catch (IOException e) {
            Logger.getLogger(OpenButtonProcessing.class.getName()).log(Level.WARNING, "IO error occurred");
        }
        JOptionPane.showMessageDialog(panel, "Opened 2 Notepads", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    void closeTasksOfIfPresent(Runtime runObj, String taskName) throws IOException {
        if (Checker.checkAndPrintIfPresent(runObj.exec("tasklist"), taskName) > 0) {
            runObj.exec("taskkill /IM " + taskName);
        }
    }

    void openTwoTasksOf(Runtime runObj, String taskName) throws IOException {
        for (int i = 0; i++ < 2; ) {
            runObj.exec(taskName);
        }
    }

    void openTaskList(Runtime runtime) throws IOException {
        Process proc = runtime.exec("tasklist");
        printTaskListOf(proc);
    }

    private void printTaskListOf(Process proc) throws IOException {
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String taskLine;
            while ((taskLine = buf.readLine()) != null) {
                System.out.println(taskLine);
            }
        }
    }
}