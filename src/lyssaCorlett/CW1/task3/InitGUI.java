package lyssaCorlett.CW1.task3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InitGUI extends JFrame {

    public void initGUI() {
        TaskManager taskManager1 = new TaskManager();

        ArrayList<String> rowData = new ArrayList<>();
        //configuration of frame and creation of splitPane, JTable and default table model
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes window and program when clicking the exit button
        setLayout(new GridLayout()); //set layout for proper positioning of components
        JSplitPane splitPane = new JSplitPane();
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        //populate table columns
        tableModel.addColumn("Task");
        tableModel.addColumn("Due Date");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Completed");

        //sorts column cells in either ascending or descending order
        table.setAutoCreateRowSorter(true);

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
        tasksScrollPane.setPreferredSize(new Dimension(550, 300));
        tasksScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        table.setFillsViewportHeight(true);

        //creating labels
        JLabel currentTasksLabel = new JLabel("Current tasks");
        JLabel addPanelLabel = new JLabel("Add a new task");
        JLabel newTaskLabel = new JLabel("Task: ");
        JLabel newDateLabel = new JLabel("Due date: ");
        JLabel newPriorityLabel = new JLabel("Priority level: ");
        JLabel confirmation = new JLabel("New task added to the list");
        JLabel errorLabel = new JLabel("Please fill in all of the boxes");
        JLabel dateErrorLabel = new JLabel("Please enter a date in the format of dd/MM/yyyy");
        //setting condition dependent label visibility to false
        errorLabel.setVisible(false);
        confirmation.setVisible(false);
        dateErrorLabel.setVisible(false);

        //button creation
        JButton addTaskButton = new JButton("ADD TASK");
        JButton saveTaskFile = new JButton("SAVE TASKS TO FILE");
        JButton loadTaskFile = new JButton("LOAD TASKS FILE");
        JButton clearButton = new JButton("CLEAR ALL");
        JButton deleteTaskButton = new JButton("DELETE TASK");
        JButton completedTaskButton = new JButton("COMPLETED");

        //setting tool tips for buttons
        deleteTaskButton.setToolTipText("Highlight the task to be deleted to use this function");
        clearButton.setToolTipText("Deletes all tasks in the table");
        completedTaskButton.setToolTipText("Highlight the task to be marked as completed to use this function");


        String[] priorityChoices = {"1 Urgent", "2 High", "3 Moderate", "4 Low"};

        //setting up the date format for the due date of the task
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //setting the lenient to false applies strict date parsing
        dateFormat.setLenient(false);

        //input fields
        final JTextField taskInputField = new JTextField(10);
        final JFormattedTextField dateInputField = new JFormattedTextField(dateFormat);
        dateInputField.setColumns(10);
        final JComboBox<String> priorityInputField = new JComboBox<>(priorityChoices);

        //setting tooltip for the dateInputField
        dateInputField.setToolTipText("Please use the dd/MM/yyyy date format");

        //adding and configuration of splitPane
        add(splitPane);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(650);
        splitPane.setTopComponent(leftPanel);
        splitPane.setBottomComponent(rightPanel);

        //adding labels to panels and nesting panels
        leftPanel.add(currentTasksLabel);
        leftPanel.add(taskPanel);
        rightPanel.add(addPanelLabel);
        rightPanel.add(inputPanel);
        taskPanel.add(tasksScrollPane);
        taskPanel.add(bottomPanel);

        //organising the layout of the components in the input panel
        GridBagLayout layout = new GridBagLayout();
        inputPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        //positions task, due date and priority labels, corresponding input boxes, and error messages using a grid layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add((newTaskLabel), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add((taskInputField), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add((newDateLabel), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add((dateInputField), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add((newPriorityLabel), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add((priorityInputField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        inputPanel.add((addTaskButton), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        inputPanel.add((confirmation), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        inputPanel.add((errorLabel), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        inputPanel.add((dateErrorLabel), gbc);

        //adding buttons to correct panels
        bottomPanel.add(clearButton);
        bottomPanel.add(deleteTaskButton);
        bottomPanel.add(loadTaskFile);
        bottomPanel.add(saveTaskFile);
        bottomPanel.add(completedTaskButton);

        taskManager1.taskManager(saveTaskFile, loadTaskFile, clearButton, addTaskButton, tableModel,
                rowData, inputPanel, table, taskInputField, dateInputField, dateFormat, deleteTaskButton,
                completedTaskButton, priorityInputField, confirmation, errorLabel, dateErrorLabel);

        setVisible(true);
    }

}
