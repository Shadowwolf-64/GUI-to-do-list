package lyssaCorlett.CW1.task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;


public class TaskManager extends JSplitPane {

    public void taskManager(JButton saveTaskFile, JButton loadTaskFile, JButton clearButton,
                            JButton addTaskButton, DefaultTableModel tableModel,
                            ArrayList<String> rowData, JPanel inputPanel, JTable table,
                            JTextField taskInputField, JFormattedTextField dateInputField,
                            JButton deleteTaskButton, JButton completedTaskButton, JComboBox<String> priorityInputField,
                            JLabel confirmation, JLabel errorLabel) {
        Task task = new Task();
        //saving task list from text area to a file using the save button
        saveTaskFile.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showSaveDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                if(!filePath.endsWith(".txt")) {
                    file = new File(filePath + ".txt");
                }
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    int row = 0;
                    int col = 0;
                    while (row < tableModel.getRowCount()) {
                        rowData.add(tableModel.getValueAt(row, col).toString());
                        rowData.add(tableModel.getValueAt(row, col + 1).toString());
                        rowData.add(tableModel.getValueAt(row, col + 2).toString());
                        rowData.add(tableModel.getValueAt(row, col + 3).toString());
                        row += 1;
                    }
                    writer.write(rowData.toString());
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
                    System.out.println(text);
                    String[] taskList = text.split(",");
                    //loops over the list of data, stored in the taskList array, from the txt file
                    //and stores each set of data for a task into a new array, then uses this data to populate the table 
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

        //clearing all tasks from text area
        clearButton.addActionListener(_ -> {
            //tableModel.setRowCount(0);
            while(tableModel.getRowCount() > 0) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.removeRow(i);
                }
            }
            JOptionPane.showMessageDialog(inputPanel, "All tasks cleared from the list");
        });

        //add task to text area
        addTaskButton.addActionListener(_ -> {
            int delay = 3000;
            task.setTask(taskInputField);
            task.setDueDate(dateInputField);
            task.setStatus("false");
            if(taskInputField.getText().isEmpty() || dateInputField.getText().isEmpty()) {
                errorLabel.setVisible(true);
                ActionListener taskPerformed = _ -> errorLabel.setVisible(false);
                new Timer(delay, taskPerformed).start();
            } else {
                try {
                    tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem(), task.getStatus()});
                    confirmation.setVisible(true);
                    ActionListener taskPerformed = _ -> confirmation.setVisible(false);
                    new Timer(delay, taskPerformed).start();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //delete selected task
        deleteTaskButton.addActionListener(_ -> {
            tableModel.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(inputPanel, "Task deleted from the list");
        });

        //complete task
        completedTaskButton.addActionListener(_ -> {
            task.setStatus("true");
            tableModel.setValueAt(task.getStatus(), table.getSelectedRow(), 3);
            JOptionPane.showMessageDialog(inputPanel, "Task marked as completed");
        });
    }
}
