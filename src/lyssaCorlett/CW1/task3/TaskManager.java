package lyssaCorlett.CW1.task3;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Component.LEFT_ALIGNMENT;


public class TaskManager {
    //create a frame (GUI window) and gives it a title
    public void taskManager() {
        Task task = new Task();
        JFrame frame = new JFrame("To-Do-List");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes window and program when clicking the exit button
        frame.setLayout(new GridLayout()); //set layout for proper positioning of components
        String[][] data = {};
        String[] columnNames = {"Task", "Due Date", "Priority"};
        //splits the window giving a top and bottom section to the window
        JSplitPane splitPane = new JSplitPane();
        JTable table = new JTable(data, columnNames);

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
        //JTextArea textArea = new JTextArea(50, 40);
        JScrollPane tasksScrollPane = new JScrollPane(table);
        tasksScrollPane.setPreferredSize(new Dimension(520, 450));
        tasksScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        table.setFillsViewportHeight(true);

        //creating labels
        JLabel toDoLabel = new JLabel("Current To-Do's: ");
        JLabel addTaskLabel = new JLabel("Add new task");
        JLabel confirmationLabel = new JLabel("Task4 added");
        JLabel errorLabel = new JLabel("Error, task could not be added");
        JLabel newTaskLabel = new JLabel("Task: ");
        JLabel newDateLabel = new JLabel("Due date: ");
        JLabel newPriorityLabel = new JLabel("Priority level: ");

        //button creation
        JButton addTaskButton = new JButton("ADD TASK");
        JButton saveTaskFile = new JButton("SAVE TASKS TO FILE");
        JButton loadTaskFile = new JButton("LOAD TASKS FILE");
        JButton clearButton = new JButton("CLEAR");
        JButton deleteTaskButton = new JButton("DELETE TASK");

        //input fields
        final JTextField taskInputField = new JTextField(10);
        final JFormattedTextField dateInputField = new JFormattedTextField(10);
        final JTextField priorityInputField = new JTextField(10);

        //adding components to frame window
        frame.add(splitPane);

        //configuring split pane and adding containers to split pane
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(550);
        splitPane.setTopComponent(leftPanel);
        splitPane.setBottomComponent(rightPanel);


        //adding labels to panels and nesting panels
        leftPanel.add(toDoLabel);
        leftPanel.add(taskPanel);
        rightPanel.add(addTaskLabel);
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

        //making frame visible
        frame.setVisible(true);

//        //saving task list from text area to a file using the save button
//        saveTaskFile.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int choice = fileChooser.showSaveDialog(null);
//            if(choice == JFileChooser.APPROVE_OPTION) {
//                File file = fileChooser.getSelectedFile();
//                try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                    writer.write(textArea.getText());
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error saving file");
//                }
//            }
//        });
//
//        //loading a specific file to the text area using the load button
//        loadTaskFile.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int choice = fileChooser.showOpenDialog(frame);
//            if(choice == JFileChooser.APPROVE_OPTION) {
//                File file = fileChooser.getSelectedFile();
//                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                    textArea.read(reader, null);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(frame, "Error loading file");
//                }
//            }
//        });
//
//        //clearing all tasks from text area
//        clearButton.addActionListener(e -> {
//            textArea.setText("");
//        });
//
//        //add task to text area
//        addTaskButton.addActionListener(e -> {
//            try {
//                Task task = new Task(taskInputField.getText(), priorityInputField.getText());
//                textArea.append(task.getTask()+ " " + task.getPriority());
//                textArea.append("\n");
//                inputPanel.add(confirmationLabel);
//            } catch (Exception ex) {
//                inputPanel.add(errorLabel);
//            }
//
//        });

    }
}
