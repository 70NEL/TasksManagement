package org.example;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends Container {
    private JTabbedPane tabbedPane;
    JPanel panel1;
    private JPanel JPanelEmployee;
    private JTextField tfEmployeeName;
    private JTextField tfEmployeeID;
    private JLabel LabelEmployeeID;
    private JLabel LabelEmployeeName;
    private JButton btnAddEmployee;
    private JPanel JPanelAssignTasks;
    private JTextField tfEmployeeIDTask;
    private JLabel LabelEmployeeIDTasks;
    private JTextField tfTaskIDTask;
    private JButton btnAssign;
    private JPanel JPanelAddTask;
    private JTextField tfTaskID;
    private JLabel lbTaskID;
    private JButton btnAddTask;
    private JButton btnDisplayTasks;
    private JComboBox cbModifyStatus;
    private JButton btnModifyStatus;
    private JLabel lbModifyStatus;
    private JComboBox cbTaskType;
    private JLabel lbTaskType;
    private JTextField tfStartHr;
    private JTextField tfStopHr;
    private JLabel lbStartHr;
    private JLabel lbStopHr;
    private JButton btnCalculateWorkDur;
    private JCheckBox cbModStatus;

    public GUI() {
        lbModifyStatus.setVisible(false);
        cbModifyStatus.setVisible(false);
        btnModifyStatus.setVisible(false);

        btnAddEmployee.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                Employee emp = new Employee();
                emp.setName(tfEmployeeName.getText());
                emp.setIdEmployee(Integer.parseInt(tfEmployeeID.getText()));
                newManagement.addEmployee(emp);
                JOptionPane.showMessageDialog(null, "Employee Added Successfully!");
            }
        });

        btnDisplayTasks.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                Map<Employee, List<Task>> map = newManagement.getEmployees();
                for(Employee emp : newManagement.getEmployees().keySet()) {
                    List<Task> tasks = map.get(emp);
                    System.out.print("Name :" + emp.getName() + " with ID: " + emp.getIdEmployee());
                    int k = 1;
                    for(Task task : tasks) {
                        if(k==1) {
                            System.out.println(" ,was assigned the following tasks :");
                            k=0;
                        }
                        System.out.println("Task ID: " + task.getIdTask() + " | " + "Estimated Duration : " + task.estimateDuration());
                    }
                }
                JOptionPane.showMessageDialog(null, "Tasks Displayed Successfully!");
            }
        });

        btnAddTask.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                Task tsk = null;
                if(cbTaskType.getSelectedItem() == "Complex") {
                    tsk = new ComplexTask();
                }else {
                    SimpleTask tk = new SimpleTask();
                    tk.setStartHour(Integer.parseInt(tfStartHr.getText()));
                    tk.setEndHour(Integer.parseInt(tfStopHr.getText()));
                    tsk = tk;
                }

                if(tsk != null) {
                    tsk.setIdTask(Integer.parseInt(tfTaskID.getText()));
                    boolean isDone = newManagement.addTask(tsk);
                    if(isDone) {
                        JOptionPane.showMessageDialog(null, "Task Added Successfully!");
                    }else {
                        JOptionPane.showMessageDialog(null, "Task Added Failed!");
                    }
                }
            }
        });

        btnAssign.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                int taskID = Integer.parseInt(tfTaskIDTask.getText());
                int employeeID = Integer.parseInt(tfEmployeeIDTask.getText());
                Task tsk = newManagement.findTaskById(taskID);

                if(tsk != null) {
                    boolean isDone = newManagement.assignTaskToEmployee(employeeID, tsk);
                    if(!isDone) {
                        JOptionPane.showMessageDialog(null, "Task WAS NOT Assigned Successfully!");
                    }else {
                        JOptionPane.showMessageDialog(null, "Task Assigned Successfully!");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "The Task is not available in the company's list!");
                }

            }
        });

        btnModifyStatus.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                Map<Employee, List<Task>> employees = newManagement.getEmployees();
                int empID = Integer.parseInt(tfEmployeeIDTask.getText());
                int taskID = Integer.parseInt(tfTaskIDTask.getText());
                boolean isDone = newManagement.modifyTaskStatus(empID, taskID);
                if(isDone == false) {
                    JOptionPane.showMessageDialog(null, "Task Status WAS NOT Modified Successfully!");
                }else {
                    JOptionPane.showMessageDialog(null, "Task Status was modified Successfully!");
                }
            }
        });

        btnCalculateWorkDur.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                for(Employee emp : newManagement.getEmployees().keySet()) {
                    System.out.println(emp.getName() + " works : " + newManagement.calculateEmployeeWorkDuration(emp.getIdEmployee()) + " hours");
                }
                JOptionPane.showMessageDialog(null, "Works Duration Calculated Successfully!");
            }
        });

        cbTaskType.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbTaskType.getSelectedItem();
                boolean isSimple = "Simple".equals(selectedItem);

                tfStartHr.setVisible(isSimple);
                tfStopHr.setVisible(isSimple);
                lbStartHr.setVisible(isSimple);
                lbStopHr.setVisible(isSimple);
            }
        });

        cbModStatus.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean checked = cbModStatus.isSelected();

                lbModifyStatus.setVisible(checked);
                cbModifyStatus.setVisible(checked);
                btnModifyStatus.setVisible(checked);
            }
        });
    }
}
