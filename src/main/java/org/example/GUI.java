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
                    System.out.println(emp.getName() + " " + emp.getIdEmployee());
                    for(Task task : tasks) {
                        System.out.println(task.getIdTask() + task.estimateDuration());
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
                    tsk = new SimpleTask();
                }

                if(tsk != null) {
                    newManagement.addTask(tsk);
                }

                JOptionPane.showMessageDialog(null, "Task Added Successfully!");
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
                Map<Employee, List<Task>> employees = newManagement.getEmployees();
                String employeeID = tfEmployeeID.getText();
                String taskID = tfTaskID.getText();
                Task tsk = null;
                boolean isTaskIdValid = false;
                Integer tmp = Integer.parseInt(employeeID);

                for(Employee emp : employees.keySet()) {
                    for(Task task : employees.get(emp)) {
                        if(taskID.equals(task.getIdTask())) {
                            isTaskIdValid = true;
                            tsk = task;
                            break;
                        }
                    }
                }
                if(isTaskIdValid) {
                    for(Employee emp : employees.keySet()) {
                        if(tmp.equals(emp.getIdEmployee())) {
                            List<Task> lst = employees.get(emp);
                            lst.add(tsk);
                            JOptionPane.showMessageDialog(null, "Task Assigned Successfully!");
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "The Task WAS NOT Assigned Successfully!");
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
                for(Employee emp : employees.keySet()) {
                    if(tfEmployeeIDTask.getText().equals(emp.getIdEmployee())) {
                        for(Task task : employees.get(emp)) {
                            if(tfTaskIDTask.getText().equals(task.getIdTask())) {
                                if(cbModifyStatus.getSelectedItem() == "Completed") {
                                    task.setStatusTask("Completed");
                                }else {
                                    task.setStatusTask("Uncompleted");
                                }
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Task Status was modified Successfully!");
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
                    System.out.println(emp.getName() + " works :" + newManagement.calculateEmployeeWorkDuration(emp.getIdEmployee()));
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
