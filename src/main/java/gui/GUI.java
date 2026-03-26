package gui;

import businesslogic.TasksManagement;
import businesslogic.Utility;
import datamodel.ComplexTask;
import datamodel.Employee;
import datamodel.SimpleTask;
import datamodel.Task;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

public class GUI extends Container {
    private JTabbedPane tabbedPane;
    public JPanel panel1;
    private JPanel JPanelEmployee;
    private JTextField tfEmployeeName;
    private JTextField tfEmployeeID;
    private JLabel LabelEmployeeID;
    private JLabel LabelEmployeeName;
    private JButton btnAddEmployee;
    private JPanel JPanelAssignTasks;
    private JTextField tfEmployeeIDTask;
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
    private JTextField tfParentTaskID;
    private JLabel lbParentTaskID;
    private JTable tableWorkDuration;
    private JTable tableFilter;
    private JTable tableNrTasks;
    private JTree trTasks;
    private JTable tbEmployees;

    private void displayEmployeeTable() {
        String[] columnNames = {"ID Employee", "Employee Name"};
        TasksManagement newManagement = TasksManagement.getInstance();

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for(Employee emp : newManagement.getEmployees().keySet()) {
            model.addRow(new Object[]{
                    emp.getIdEmployee(),
                    emp.getName()
            });
        }

        tbEmployees.setModel(model);
    }

    private DefaultMutableTreeNode createTreeNode(Task tsk) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(tsk);

        if(tsk instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) tsk;
            for(Task subTask: complexTask.getSubTasks()) {
                node.add(createTreeNode(subTask));
            }
        }

        return node;
    }

    private void displayTaskTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tasks");

        TasksManagement newManagement = TasksManagement.getInstance();

        for(Task task : newManagement.getTasks()) {
            if(task.getParentId() == -1) {
                root.add(createTreeNode(task));
            }
        }

        DefaultTreeModel model = new DefaultTreeModel(root);
        trTasks.setModel(model);
    }

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

                if(newManagement.findEmployeeById(Integer.parseInt(tfEmployeeID.getText())) != null &&
                        newManagement.findEmployeeById(Integer.parseInt(tfEmployeeID.getText())).getIdEmployee() == emp.getIdEmployee()) {
                    JOptionPane.showMessageDialog(null, "Employee with this ID already exists!");
                }else {
                    newManagement.addEmployee(emp);
                    JOptionPane.showMessageDialog(null, "Employee Added Successfully!");
                }

                displayEmployeeTable();
                tbEmployees.updateUI();
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
                    boolean tskExisted = false;
                    String parentIDText = tfParentTaskID.getText();
                    tsk = newManagement.findTaskById(Integer.parseInt(tfTaskID.getText()));

                    if(tsk == null) {
                        if("Complex".equals(cbTaskType.getSelectedItem())) {
                            tsk = new ComplexTask();
                        }else {
                            SimpleTask tk = new SimpleTask();
                            tk.setStartHour(Integer.parseInt(tfStartHr.getText()));
                            tk.setEndHour(Integer.parseInt(tfStopHr.getText()));
                            tsk = tk;
                        }
                        tsk.setIdTask(Integer.parseInt(tfTaskID.getText()));
                    }else {
                        tskExisted = true;
                    }

                    if(!parentIDText.isEmpty()) {
                        int parentID = Integer.parseInt(parentIDText);
                        Task parentTask = newManagement.findTaskById(parentID);

                        if(parentTask != null) {
                            if(parentTask instanceof ComplexTask) {
                                if(tsk instanceof ComplexTask &&
                                        (((ComplexTask) tsk).isAncestorOf(parentTask) || ((ComplexTask) parentTask).getSubTasks().contains(tsk))) {
                                    JOptionPane.showMessageDialog(null, "A task cant appear twice in a single branch !!");
                                }else if(tsk instanceof SimpleTask && ((ComplexTask) parentTask).getSubTasks().contains(tsk)) {
                                    JOptionPane.showMessageDialog(null, "A task cant appear twice in a single branch !!");
                                }else {
                                    ((ComplexTask) parentTask).addTask(tsk);
                                    if(!tskExisted) {
                                        tsk.setParentId(parentID);
                                        newManagement.addTask(tsk);
                                    }
                                    JOptionPane.showMessageDialog(null, "Task Added Successfully!");
                                }
                            }else {
                                JOptionPane.showMessageDialog(null, "Tasks can't have a simple task as parent!");
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "The Task ID for the Parent is invalid!");
                        }
                    }else {
                        boolean isAdded = newManagement.addTask(tsk);

                        if(isAdded) {
                            JOptionPane.showMessageDialog(null, "Task Added Successfully!");
                        }else {
                            JOptionPane.showMessageDialog(null, "Task Already Exists!");
                        }
                    }

                    displayTaskTree();
                    trTasks.updateUI();
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
                int selectedRow = tbEmployees.getSelectedRow();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trTasks.getLastSelectedPathComponent();

                if(selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an employee!");
                    return;
                }

                if(selectedNode == null || selectedNode.isRoot()) {
                    JOptionPane.showMessageDialog(null, "Please select a task to assign!");
                    return;
                }

                int employeeID = (int) tbEmployees.getValueAt(selectedRow, 0);
                Task selectedTask = (Task) selectedNode.getUserObject();

                TasksManagement newManagement = TasksManagement.getInstance();
                boolean isDone = newManagement.assignTaskToEmployee(employeeID, selectedTask);

                if(!isDone) {
                    JOptionPane.showMessageDialog(null, "Task WAS NOT Assigned Successfully!");
                }else {
                    JOptionPane.showMessageDialog(null, "Task Assigned Successfully!");
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
                int selectedRow = tbEmployees.getSelectedRow();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trTasks.getLastSelectedPathComponent();
                if(selectedRow == -1 || selectedNode == null ||  selectedNode.isRoot()) {
                    JOptionPane.showMessageDialog(null, "Please select an employee and/or a task!");
                }

                int empId = (int) tbEmployees.getValueAt(selectedRow, 0);
                Task selectedTask = (Task) selectedNode.getUserObject();
                int taskId = (int) selectedTask.getIdTask();
                TasksManagement newManagement = TasksManagement.getInstance();
                String newStatus = (String) cbModifyStatus.getSelectedItem();
                boolean isDone = newManagement.modifyTaskStatus(empId, taskId, newStatus);

                if(!isDone) {
                    JOptionPane.showMessageDialog(null, "Task WAS NOT Modified Successfully!");
                }else {
                    JOptionPane.showMessageDialog(null, "Task Modified Successfully!");
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
                List<Employee> filtered = util.utilityFilter(newManagement);

                String[] columnNames = {"Employee Name", "Work Duration"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                if(!filtered.isEmpty()){
                    for(Employee emp: filtered){
                        model.addRow(new Object[]{
                                emp.getName(),
                                newManagement.calculateEmployeeWorkDuration(emp.getIdEmployee())
                        });
                    }
                }else {
                    model.addRow(new Object[]{
                            "No employees that respect the filter were found",
                            "-----------------------------------"
                    });
                }

                tableFilter.setModel(model);
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

                String[] columnNames = {"Employee Name", "Completed Task", "Uncompleted Task"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                if(!taskMap.isEmpty()){
                    for(Map.Entry<String, Map<String, Integer>> entry : taskMap.entrySet()) {
                        String name = entry.getKey();
                        int finished =  entry.getValue().get("Completed");
                        int unFinished =  entry.getValue().get("Uncompleted");

                        model.addRow(new Object[]{
                                name,
                                finished,
                                unFinished
                        });

                    }
                }else {
                    model.addRow(new Object[]{
                            "No employees that respect the filter were found",
                            "----------------------------------------",
                            "----------------------------------------"
                    });
                }

                tableNrTasks.setModel(model);
            }
        });

        displayEmployeeTable();
        displayTaskTree();
    }
}
