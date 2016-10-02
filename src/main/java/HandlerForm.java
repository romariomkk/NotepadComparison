import tasklistStringExtract.CloseButtonProcessing;
import tasklistStringExtract.OpenButtonProcessing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

/**
 * Created by romariomkk on 25.09.2016.
 */
public class HandlerForm extends JFrame {
    private JPanel panel1;
    private JButton openProcessesButton;
    private JButton countAndCloseButton;
    private JCheckBox stateBox;

    private OpenButtonProcessing openButtonProcessing;
    private CloseButtonProcessing closeButtonProcessing;

    public HandlerForm() {

        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Notepad compare");

        initListeners();
    }

    private void initListeners() {
        openButtonProcessing = new OpenButtonProcessing(panel1, stateBox.isSelected());
        closeButtonProcessing = new CloseButtonProcessing(panel1, stateBox.isSelected());
        setListeners();
    }

    private void setListeners() {
        openProcessesButton.addActionListener(openButtonProcessing);
        countAndCloseButton.addActionListener(closeButtonProcessing);
    }

    public static void main(String[] args) throws IOException {
        HandlerForm form = new HandlerForm();
        form.setVisible(true);
    }
}