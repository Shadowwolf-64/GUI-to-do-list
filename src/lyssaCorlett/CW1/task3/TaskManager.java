package lyssaCorlett.CW1.task3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


import static java.awt.Component.LEFT_ALIGNMENT;


public class TaskManager extends JFrame{
    //create a frame (GUI window) and gives it a title
    public void taskManager() {
        Task task = new Task();
        ArrayList<String> rowData = new ArrayList<String>();
        //configuration of frame and creation of splitPane, JTable and default table model
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes window and program when clicking the exit button
        setLayout(new GridLayout()); //set layout for proper positioning of components
        JSplitPane splitPane = new JSplitPane();
        JCheckBox checkBox;
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        //populate table columns
        tableModel.addColumn("Task");
        tableModel.addColumn("Due Date");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Completed");

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

        String[] priority_choices = {"Urgent", "High", "Moderate", "Low"};

        //input fields
        final JTextField taskInputField = new JTextField(10);
        final JFormattedTextField dateInputField = new JFormattedTextField(10);
        final JComboBox<String> priorityInputField = new JComboBox<String>(priority_choices);

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

        setVisible(true);

        //saving task list from text area to a file using the save button
        saveTaskFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showSaveDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    rowData.add(tableModel.getDataVector().toString());
                    writer.write(String.valueOf(rowData));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving file");
                }
            }
        });

//        //loading a specific file to the text area using the load button
//        loadTaskFile.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int choice = fileChooser.showOpenDialog(table);
//            if(choice == JFileChooser.APPROVE_OPTION) {
//                File file = fileChooser.getSelectedFile();
//                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                    String line = reader.readLine();
//                    Object[] rowFields = line.split(" ");
//                    tableModel.addRow(rowFields[0], rowFields[1], rowFields[2]);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error loading file");
//                }
//            }
//        });

        //clearing all tasks from text area
        clearButton.addActionListener(e -> {
            tableModel.setRowCount(0);
        });

        //add task to text area
        addTaskButton.addActionListener(e -> {
            try {
                task.setTask(taskInputField);
                task.setDueDate(dateInputField);
                tableModel.addRow(new Object[]{task.getTask().getText(), task.getDueDate().getText(), priorityInputField.getSelectedItem()});
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //delete selected task
        deleteTaskButton.addActionListener(e -> {
            tableModel.removeRow(table.getSelectedRow());
        });
    }
}
