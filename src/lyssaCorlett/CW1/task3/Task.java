package lyssaCorlett.CW1.task3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private String task;
    private Date dueDate;
    private String priority;
    private boolean status;
    final List<Object> data = new ArrayList<>();

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
