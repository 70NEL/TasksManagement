package businesslogic;

import datamodel.ComplexTask;
import datamodel.Employee;
import datamodel.SimpleTask;
import datamodel.Task;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.*;

public class TasksManagement implements Serializable {
    private static TasksManagement instance;
    private Map<Employee, List<Task>> employees;
    private List<Task> tasks = new ArrayList<>();

    private TasksManagement() {
        employees = new HashMap<>();
    }

    public static TasksManagement getInstance() {
        if (instance == null) {
            instance = new TasksManagement();
        }
        return instance;
    }

    public Map<Employee, List<Task>>  getEmployees() {
        return this.employees;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addEmployee(Employee employee) {
        if(!employees.containsKey(employee)) {
            employees.put(employee, new ArrayList<>());
        }
    }

    public boolean addTask(Task task) {
        if(!tasks.contains(task)) {
            for(Task t: tasks) {
                if(t.getIdTask() == task.getIdTask()) {
                    return false;
                }
            }
            tasks.add(task);
            return true;
        }
        return false;
    }

    public boolean assignTaskToEmployee(int idEmployee, Task task) {
        if(tasks.contains(task)) {
            for (Employee employee : employees.keySet()) {
                if(idEmployee == employee.getIdEmployee()){
                    employees.get(employee).add(task);
                    return true;
                }
            }
        }
        return false;
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int sum = 0;
        for(Employee emp: employees.keySet()) {
            if(idEmployee == emp.getIdEmployee()){
                for(Task task: employees.get(emp)) {
                    sum += task.estimateDuration();
                }
            }
        }
        return sum;
    }

    public Task findTaskById(int idTask) {
        for(Task tsk: tasks) {
            if(tsk.getIdTask() == idTask) {
                return tsk;
            }
        }
        return null;
    }

    public Employee findEmployeeById(int idEmployee) {
        for(Employee emp: employees.keySet()) {
            if(idEmployee == emp.getIdEmployee()) {
                return emp;
            }
        }
        return null;
    }

    public boolean checkParentStatus(ComplexTask taskParent) {
        int nrOfTasks = 0;
        for (Task task : taskParent.getSubTasks()) {
            System.out.println(taskParent.getIdTask() + taskParent.getStatusTask() + " " + task.getIdTask() + task.getStatusTask());
            if ("Completed".equals(task.getStatusTask())) {
                nrOfTasks++;
            } else {
                break;
            }
        }

        System.out.println(nrOfTasks + " " + taskParent.getSubTasks().size());
        if (nrOfTasks == taskParent.getSubTasks().size()) {
            taskParent.setStatusTask("Completed");
            System.out.println("Am schimbat statusul task-ului " + taskParent.getSubTasks() + " cu succes");
            return true;
        }

        return false;
    }

    public boolean modifyTaskStatus(int idEmployee, int idTask, String status) {
        Task tsk = findTaskById(idTask);
        int parentId = tsk.getParentId();
        boolean result = false;
        if(tsk instanceof SimpleTask) {
            for(Employee emp: employees.keySet()) {
                if(idEmployee == emp.getIdEmployee()){
                    for(Task task: employees.get(emp)) {
                        if(task.getIdTask() == idTask) {
                            task.setStatusTask(status);
                            result = true;
                        }
                    }
                }
            }
        }else if(tsk instanceof ComplexTask) {
            for(Employee emp: employees.keySet()) {
                for(Task task: employees.get(emp)) {
                    task.setStatusTask(status);
                }
                return result;
            }
        }


        TasksManagement newManagement = getInstance();
        Task parentTask =  findTaskById(parentId);
        if(parentTask instanceof ComplexTask) {
            System.out.println("intra aici " + parentTask.getIdTask() + " " + idTask);
            if(newManagement.checkParentStatus((ComplexTask) parentTask)) {
                System.out.println("Parent task's status was modified");
            }
        }

        return result;
    }
}
