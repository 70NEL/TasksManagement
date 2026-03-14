package org.example;

import java.io.Serializable;
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
        for(Employee e: employees.keySet()) {
            if(idEmployee == e.getIdEmployee()){
                for(Task task: employees.get(e)) {
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

    public boolean modifyTaskStatus(int idEmployee, int idTask) {
        for(Employee e: employees.keySet()) {
            if(idEmployee == e.getIdEmployee()){
                for(Task task: employees.get(e)) {
                    if(task.getIdTask() == idTask) {
                        if("Completed".equals(task.getStatusTask())) {
                            task.setStatusTask("Uncompleted");
                        }else {
                            task.setStatusTask("Completed");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
