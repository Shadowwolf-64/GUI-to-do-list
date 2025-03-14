package lyssaCorlett.CW1.task3;

import javax.swing.*;

public class Task {
    private JTextField task;
    private JFormattedTextField dueDate;
    private boolean status;

    public JTextField getTask() {
        return task;
    }

    public void setTask(JTextField task) {
        this.task = task;
    }

    public JFormattedTextField getDueDate() {
        return dueDate;
    }

    public void setDueDate(JFormattedTextField dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
