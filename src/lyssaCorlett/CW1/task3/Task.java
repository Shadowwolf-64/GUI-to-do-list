package lyssaCorlett.CW1.task3;

import javax.swing.*;

public class Task {
    private JTextField task;
    private JFormattedTextField dueDate;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
