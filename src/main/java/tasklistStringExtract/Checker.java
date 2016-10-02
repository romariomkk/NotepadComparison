package tasklistStringExtract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by romariomkk on 02.10.2016.
 */
public class Checker {
    public static int checkAndPrintIfPresent(Process proc, String taskName) throws IOException {
        int numOfTasks = 0;
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String taskLine;
            while ((taskLine = buf.readLine()) != null) {
                if (taskLine.startsWith(taskName)) {
                    System.out.println(taskLine);
                    numOfTasks++;
                }
            }
        }
        return numOfTasks;
    }

}
