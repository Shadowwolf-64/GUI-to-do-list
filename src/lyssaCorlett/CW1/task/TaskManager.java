package lyssaCorlett.CW1.task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;


public class TaskManager extends JSplitPane {

    public void taskManager(JButton saveTaskFile, JButton loadTaskFile, JButton clearButton,
                            JButton addTaskButton, DefaultTableModel tableModel,
                            ArrayList<String> rowData, JPanel inputPanel, JTable table,
                            JTextField taskInputField, JFormattedTextField dateInputField,
                            JButton deleteTaskButton, JButton completedTaskButton, JComboBox<String> priorityInputField) {
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
                    //rowData.add(reader.readLine());
                   // Vector<String> vector = new Vector<>(rowData);
                    //System.out.println(vector);
                    //loops over the list of data, stored in the rowData array, from the txt file
                    //and stores each set of data for a task into a new array, then uses this data to populate the table 
                    for(int i = 0; i < taskList.length; i += 4) {
                        Object[] data = new Object[4];
                        data[0] = taskList[i];
                        data[1] = taskList[i + 1];
                        data[2] = taskList[i + 2];
                        data[3] = taskList[i + 3];

                        tableModel.addRow(data);
                    }
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        String[] values = line.split(","); // Assuming CSV format
//                        Vector<Object> row = new Vector<>();
//                        for (String value : values) {
//                            row.add(value.trim());
//                        }
//                        data.add(row);
//                    }

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
            try {
                task.setTask(taskInputField);
                task.setDueDate(dateInputField);
                task.setStatus("false");
                tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem(), task.getStatus()});
                JOptionPane.showMessageDialog(inputPanel, "New task added to the list");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
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
            //try this for changing the background of the row to green when completed
            // @Override
            // public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
            //     Component c = super.prepareRenderer(renderer, row, col);
            //     String status = (String)getValueAt(table.getSelectedRow(), Completed);
            //     if ("active".equals(status)) {
            //         c.setBackground(Color.GREEN);
            //         c.setForeground(Color.WHITE);
            //     } else {
            //         c.setBackground(super.getBackground());
            //         c.setForeground(super.getForeground());
            //     }
            //     return c;
            //}
            JOptionPane.showMessageDialog(inputPanel, "Task marked as completed");
        });
    }
}
