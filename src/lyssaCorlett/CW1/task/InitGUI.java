package lyssaCorlett.CW1.task;



import lyssaCorlett.CW1.task3.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InitGUI extends JFrame {

    public void initGUI() {
        TaskManager1 taskManager1 = new TaskManager1();

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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

        taskManager1.taskManager1(saveTaskFile, loadTaskFile, clearButton, addTaskButton, tableModel,
                rowData, inputPanel, table, taskInputField, dateInputField, deleteTaskButton, completedTaskButton);

        setVisible(true);
    }

}
