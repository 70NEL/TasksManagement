import org.example.Employee;
import org.example.Task;
import org.example.TasksManagement;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JTabbedPane tabbedPane;
    private JPanel panel1;
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
    private JTextField textField1;
    private JTextField textField2;
    private JLabel lbStartHr;
    private JLabel lbStopHr;
    private JButton btnCalculateWorkDur;

    public GUI() {
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
            }
        });
    }
}
