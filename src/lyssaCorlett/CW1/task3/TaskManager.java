package lyssaCorlett.CW1.task3;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TaskManager extends JFrame{
    //create a frame (GUI window) and gives it a title
    public void taskManager() {
        Task task = new Task();
        ArrayList<Object> rowData = new ArrayList<>();
        //configuration of frame and creation of splitPane, JTable and default table model
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes window and program when clicking the exit button
        setLayout(new GridLayout()); //set layout for proper positioning of components
        JSplitPane splitPane = new JSplitPane();
//        JCheckBox completion = new JCheckBox();
//        completion.setBounds(100, 150, 500, 50);
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        //populate table columns
        tableModel.addColumn("Task");
        tableModel.addColumn("Due Date");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Completed");
        //sorts column cells in either ascending or descending order
        table.setAutoCreateRowSorter(true);

        //centers all the text inside the JTable
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);


        //panels
        JPanel leftPanel = new JPanel(); //container panel for left half of window
        JPanel rightPanel = new JPanel(); //container for right half of window
        JPanel inputPanel = new JPanel();
        JPanel taskPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        //setting layout of each panel
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));

        //creating scroll pane for task list and setting size and alignment
        JScrollPane tasksScrollPane = new JScrollPane(table);
        tasksScrollPane.setPreferredSize(new Dimension(520, 450));
        tasksScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        table.setFillsViewportHeight(true);

        //creating labels
        JLabel currentTasksLabel = new JLabel("Current tasks");
        JLabel addPanelLabel = new JLabel("Add a new task");
        JLabel newTaskLabel = new JLabel("Task: ");
        JLabel newDateLabel = new JLabel("Due date: ");
        JLabel newPriorityLabel = new JLabel("Priority level: ");

        //button creation
        JButton addTaskButton = new JButton("ADD TASK");
        JButton saveTaskFile = new JButton("SAVE TASKS TO FILE");
        JButton loadTaskFile = new JButton("LOAD TASKS FILE");
        JButton clearButton = new JButton("CLEAR");
        JButton deleteTaskButton = new JButton("DELETE TASK");
        JButton completedTaskButton = new JButton("Completed");

        String[] priority_choices = {"1  Urgent", "2  High", "3  Moderate", "4  Low"};

        //setting up the date format for the due date of the task
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //input fields
        final JTextField taskInputField = new JTextField(10);
        final JFormattedTextField dateInputField = new JFormattedTextField(dateFormat);
        dateInputField.setColumns(10);
        final JComboBox<String> priorityInputField = new JComboBox<>(priority_choices);

        //adding and configuration of splitPane
        add(splitPane);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(550);
        splitPane.setTopComponent(leftPanel);
        splitPane.setBottomComponent(rightPanel);

        //adding labels to panels and nesting panels
        leftPanel.add(currentTasksLabel);
        leftPanel.add(taskPanel);
        rightPanel.add(addPanelLabel);
        rightPanel.add(inputPanel);
        taskPanel.add(tasksScrollPane);
        taskPanel.add(bottomPanel);

        //adding input fields and matching labels to right panel
        inputPanel.add(newTaskLabel);
        inputPanel.add(taskInputField);
        inputPanel.add(newDateLabel);
        inputPanel.add(dateInputField);
        inputPanel.add(newPriorityLabel);
        inputPanel.add(priorityInputField);

        //adding buttons to correct panels
        inputPanel.add(addTaskButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(deleteTaskButton);
        bottomPanel.add(loadTaskFile);
        bottomPanel.add(saveTaskFile);
        bottomPanel.add(completedTaskButton);

        setVisible(true);

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
