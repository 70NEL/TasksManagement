package org.example;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JPanel JPanelUtility;
    private JButton btnFilter;
    private JButton btnNrOfTasks;
    private JLabel lbFilter;
    private JLabel lbNrOfTasks;
    private JTable tableTasks;
    private JTextField tfParentTaskID;
    private JLabel lbParentTaskID;
    private JTable tableWorkDuration;

    public GUI() {
        lbModifyStatus.setVisible(false);
        cbModifyStatus.setVisible(false);
        btnModifyStatus.setVisible(false);

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
                String[] columnNames = {"ID Angajat", "Nume Angajat", "ID Task", "Durată Estimată"};

                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                TasksManagement newManagement = TasksManagement.getInstance();
                Map<Employee, List<Task>> map = newManagement.getEmployees();

                for (Employee emp : map.keySet()) {
                    List<Task> tasks = map.get(emp);

                    if (tasks.isEmpty()) {
                        model.addRow(new Object[]{
                                emp.getIdEmployee(),
                                emp.getName(),
                                "No tasks",
                                "-"
                        });
                    } else {
                        for (Task task : tasks) {
                            model.addRow(new Object[]{
                                    emp.getIdEmployee(),
                                    emp.getName(),
                                    task.getIdTask(),
                                    task.estimateDuration() + " hrs"
                            });
                        }
                    }
                }

                tableTasks.setModel(model);

                JOptionPane.showMessageDialog(null, "Tabelul a fost actualizat cu succes!");
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
                try{
                    TasksManagement newManagement = TasksManagement.getInstance();
                    Task tsk = null;
                    int parentID = 0;

                    if("Complex".equals(cbTaskType.getSelectedItem())) {
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

                        if(!tfParentTaskID.getText().isEmpty()) {
                            parentID = Integer.parseInt(tfParentTaskID.getText());
                            Task parentTaskPot = newManagement.findTaskById(parentID);

                            if(parentTaskPot instanceof  ComplexTask) {
                                ComplexTask parentTask = (ComplexTask) parentTaskPot;
                                parentTask.addTask(tsk);
                            }else {
                                JOptionPane.showMessageDialog(null, "The selected parent does not exist or is a simple task!");
                            }
                        }

                        if(isDone) {
                            JOptionPane.showMessageDialog(null, "Task Added Successfully!");
                        }else {
                            JOptionPane.showMessageDialog(null, "Task Added Failed, The Id already Exists!");
                        }
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Write a valid task id for the parent complex task!");
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
                String[] columnNames = {"Employee ID", "Employee Name", "Tasks ID's", "Total Estimated Duration"};

                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                TasksManagement newManagement = TasksManagement.getInstance();
                Map<Employee, List<Task>> employeesMap = newManagement.getEmployees();

                for (Employee emp : employeesMap.keySet()) {
                    List<Task> tasks = employeesMap.get(emp);

                    StringBuilder taskIds = new StringBuilder();
                    for (int i = 0; i < tasks.size(); i++) {
                        taskIds.append(tasks.get(i).getIdTask());
                        if (i < tasks.size() - 1) {
                            taskIds.append(", ");
                        }
                    }

                    String idsDisplay = !taskIds.isEmpty() ? taskIds.toString() : "No tasks";

                    int totalDuration = newManagement.calculateEmployeeWorkDuration(emp.getIdEmployee());

                    model.addRow(new Object[]{
                            emp.getIdEmployee(),
                            emp.getName(),
                            idsDisplay,
                            totalDuration + " hours"
                    });
                }

                tableWorkDuration.setModel(model);

                JOptionPane.showMessageDialog(null, "Works Duration Calculated and Displayed Successfully!");
            }
        });

        btnFilter.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TasksManagement newManagement = TasksManagement.getInstance();
                Utility util = new Utility();
                util.utilityFilter(newManagement);
            }
        });

        btnNrOfTasks.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility();
                Map<String,Map<String, Integer>> taskMap = util.nrOfTasks();

                for(Map.Entry<String, Map<String, Integer>> entry : taskMap.entrySet()) {
                    String name = entry.getKey();
                    int finished =  entry.getValue().get("Completed");
                    int unFinished =  entry.getValue().get("Uncompleted");

                    System.out.println("Employee name: " + name + " has a total of: " + finished + " ,finished tasks and a total of "  + unFinished + " unfinishedd tasks");
                }
            }
        });
    }
}
