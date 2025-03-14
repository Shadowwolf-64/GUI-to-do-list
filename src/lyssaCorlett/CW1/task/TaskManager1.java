package lyssaCorlett.CW1.task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class TaskManager1 extends JSplitPane {
    public void taskManager1(JButton saveTaskFile, JButton loadTaskFile, JButton clearButton,
                             JButton addTaskButton, DefaultTableModel tableModel,
                             ArrayList<Object> rowData, JPanel inputPanel, JTable table,
                             JTextField taskInputField, JFormattedTextField dateInputField,
                             JButton deleteTaskButton, JButton completedTaskButton) {
        Task task = new Task();
        //saving task list from text area to a file using the save button
        saveTaskFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showSaveDialog(null);
                if(choice == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        int row = 0;
                        int col = 0;
                        while (row < tableModel.getRowCount()) {
                            rowData.add(tableModel.getValueAt(row, col).toString());
                            rowData.add(tableModel.getValueAt(row,col + 1).toString());
                            rowData.add(tableModel.getValueAt(row, col + 2).toString());
                            rowData.add(tableModel.getValueAt(row, col + 3).toString());
                            row += 1;
                        }
                        String text = rowData.toString();
                        writer.write(text);
                        JOptionPane.showMessageDialog(inputPanel, "Task list saved");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving file");
                    }
                }
            }
        });

        //loading a specific file to the text area using the load button
        loadTaskFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showOpenDialog(table);
                if(choice == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        rowData.add(reader.readLine());
                        //need to increase the increment to 4 (from 3), and the same for the array size for tableRowData,
                        // once the checkbox is added to the stored table data
                        for(int i = 0; i < rowData.size(); i += 4) {
                            Object[] tablingRowData = new Object[4];
                            tablingRowData[0] = rowData.get(i).toString();
                            tablingRowData[1] = rowData.get(i + 1).toString();
                            tablingRowData[2] = rowData.get(i + 2).toString();
                            tablingRowData[3] = rowData.get(i + 3).toString();
                            tableModel.addRow(tablingRowData);
                        }
                        JOptionPane.showMessageDialog(inputPanel, "Task list loaded");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error loading file");
                    }
                }
            }
        });

        //clearing all tasks from text area
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
            }
        });

        //add task to text area
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    task.setTask(taskInputField);
                    task.setDueDate(dateInputField);
                    task.setStatus("false");
                    tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem(), task.getStatus()});
                    JOptionPane.showMessageDialog(inputPanel, "New task added to the list");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //delete selected task
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.removeRow(table.getSelectedRow());
            }
        });

        //complete task
        completedTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task.setStatus("true");

                tableModel.setValueAt(task.getStatus(), table.getSelectedRow(), 3);
            }
        });
    }
}
