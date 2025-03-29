package lyssaCorlett.CW1.task3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;


public class TaskManager extends JSplitPane {

    public void taskManager(JButton saveTaskFile, JButton loadTaskFile, JButton clearButton,
                            JButton addTaskButton, DefaultTableModel tableModel,
                            ArrayList<String> rowData, JPanel inputPanel, JTable table,
                            JTextField taskInputField, JFormattedTextField dateInputField,
                            JButton deleteTaskButton, JButton completedTaskButton, JComboBox<String> priorityInputField,
                            JLabel feedbackLabel) {

        //creating the task object
        Task task = new Task();
        Object[] options = {"Yes", "Cancel"}; //labels used for the names on JOptionPane buttons

        //add task to the table area
        addTaskButton.addActionListener(_ -> {
            //sets the time limit for displaying error messages when adding tasks
            int delay = 3500;
            //sets user input to the task object
            task.setTask(taskInputField);
            task.setDueDate(dateInputField);
            task.setStatus("NO");

            //displays an error message if any of the input fields are no filled in correctly
            if (taskInputField.getText().isEmpty() || dateInputField.getText().isEmpty()) {
                feedbackLabel.setText("Please fill in all of the boxes correctly");
                feedbackLabel.setVisible(true);
                ActionListener taskPerformed = _ -> feedbackLabel.setVisible(false);
                new Timer(delay, taskPerformed).start();
            } else if (!taskInputField.getText().isEmpty() && !dateInputField.getText().isEmpty()) { //adds task to list if all input fields are correct
                try {
                    tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem(), task.getStatus()});
                    feedbackLabel.setText("New task added to the list");
                    feedbackLabel.setVisible(true);
                    ActionListener taskPerformed = _ -> feedbackLabel.setVisible(false);
                    new Timer(delay, taskPerformed).start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding task, please try again");
                    throw new RuntimeException(ex);
                }
            }
        });

        //allows users to use the enter key instead of needing to click a button with the mouse when adding a task
        addTaskButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //sets the time limit for displaying error messages when adding tasks
                    int delay = 3500;
                    //sets user input to the task object
                    task.setTask(taskInputField);
                    task.setDueDate(dateInputField);
                    task.setStatus("NO");
                    //displays an error message if any of the input fields are no filled in correctly
                    if (taskInputField.getText().isEmpty() || dateInputField.getText().isEmpty()) {
                        feedbackLabel.setText("Please fill in all of the boxes correctly");
                        feedbackLabel.setVisible(true);
                        ActionListener taskPerformed = _ -> feedbackLabel.setVisible(false);
                        new Timer(delay, taskPerformed).start();
                    } else if (!taskInputField.getText().isEmpty() && !dateInputField.getText().isEmpty()) { //adds task to list if all input fields are correct{
                        try {
                            tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem(), task.getStatus()});
                            feedbackLabel.setText("New task added to the list");
                            feedbackLabel.setVisible(true);
                            ActionListener taskPerformed = _ -> feedbackLabel.setVisible(false);
                            new Timer(delay, taskPerformed).start();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error adding task, please try again");
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        //saving task list from table in the left panel to a file using the save button
        saveTaskFile.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showSaveDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    int row = 0;
                    int col = 0;
                    //collecting data from the task table and storing it in the txt file
                    while (row < tableModel.getRowCount()) {
                        rowData.add((String) tableModel.getValueAt(row, col));
                        rowData.add((String) tableModel.getValueAt(row, col + 1));
                        rowData.add((String) tableModel.getValueAt(row, col + 2));
                        rowData.add((String) tableModel.getValueAt(row, col + 3));
                        row += 1;
                    }
                    //removing the [] from around the file data
                    String taskData = rowData.toString().replace("[", "").replace("]", "");
                    writer.write(taskData);
                    JOptionPane.showMessageDialog(inputPanel, "Task list saved");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving file");
                }
            }
        });

        //loading a specific file to the text area using the load button
        loadTaskFile.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showOpenDialog(table);
            if(choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String text = reader.readLine();
                    String[] taskList = text.split(",");
                    /*loops over the list of data, stored in the taskList array, from the txt file
                    and stores each set of data for a task into a new array, then uses this data to populate the table*/
                    for(int i = 0; i < taskList.length; i += 4) {
                        Object[] data = new Object[4];
                        data[0] = taskList[i];
                        data[1] = taskList[i + 1];
                        data[2] = taskList[i + 2];
                        data[3] = taskList[i + 3];
                        tableModel.addRow(data);
                    }
                    JOptionPane.showMessageDialog(inputPanel, "Task list loaded");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error loading file");
                }
            }
        });

        //clearing all tasks from task table area
        clearButton.addActionListener(_ -> {
            //gives the JOptionPane to check if the user wants to delete the all tasks and only deletes if the "Yes" option is selected
            try {
                int option = JOptionPane.showOptionDialog(inputPanel,
                        "Are you sure you want to delete all tasks?",
                        "Delete all tasks",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, //uses default icon
                        options, //the names of buttons
                        options[0]); //default button title
                if(option == JOptionPane.YES_OPTION) {
                    while(tableModel.getRowCount() > 0) {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            rowData.remove((String) tableModel.getValueAt(i, 0));
                            rowData.remove((String) tableModel.getValueAt(i, 1));
                            rowData.remove((String) tableModel.getValueAt(i, 2));
                            rowData.remove((String) tableModel.getValueAt(i, 3));
                            tableModel.removeRow(i);
                        }
                    }
                    JOptionPane.showMessageDialog(inputPanel, "All tasks cleared from the list");
                } else if (option == JOptionPane.NO_OPTION) {
                    JOptionPane.getRootFrame().dispose(); //disposes of the JOptionPane and does not delete all the tasks if the cancel option is selected.
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error clearing tasks, please try again");
                throw new RuntimeException(ex);
            }

        });

        //delete selected task from the task table
        deleteTaskButton.addActionListener(_ -> {
            //gives the JOptionPane to check if the user wants to delete the task and only deletes if the "Yes" option is selected
            try {
                int option = JOptionPane.showOptionDialog(inputPanel,
                        "Are you sure you want to delete this task?",
                        "Delete task",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, //uses default icon
                        options, //the names of buttons
                        options[0]); //default button title
                if(option == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(table.getSelectedRow());
                    JOptionPane.showMessageDialog(inputPanel, "Task deleted from the list");
                } else if (option == JOptionPane.NO_OPTION) {
                    JOptionPane.getRootFrame().dispose(); //disposes of the JOptionPane and does not delete the selected task if the cancel option is selected.
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error deleting task, please try again");
                throw new RuntimeException(e);
            }
        });

        //changes the complete task column to true for the specified row
        completedTaskButton.addActionListener(_ -> {
            try {
                task.setStatus("YES");
                tableModel.setValueAt(task.getStatus(), table.getSelectedRow(), 3);

                JOptionPane.showMessageDialog(inputPanel, "Task marked as completed");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error completing task, please try again");
                throw new RuntimeException(e);
            }

        });
    }
}
