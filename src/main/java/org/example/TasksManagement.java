package org.example;

import java.io.Serializable;
import java.util.*;

public class TasksManagement implements Serializable {
    private Map<Employee, List<Task>> employees = new HashMap<>();

    public void addEmployee(Employee employee) {
        if(!employees.containsKey(employee)) {
            employees.put(employee, new ArrayList<>());
        }
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        for (Employee employee : employees.keySet()) {
            if(idEmployee == employee.getIdEmployee()){
                employees.get(employee).add(task);
            }
        }
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int sum = 0;
        for(Employee e: employees.keySet()) {
            if(idEmployee == e.getIdEmployee()){
                for(Task task: employees.get(e)) {
                    if(task.getStatusTask().equals("Completed")) {
                        sum += task.estimateDuration();
                    }
                }
            }
        }
        return sum;
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        for(Employee e: employees.keySet()) {
            if(idEmployee == e.getIdEmployee()){
                for(Task task: employees.get(e)) {
                    if(task.getIdTask() == idTask) {
                        if(task.getStatusTask().equals("Completed")) {
                            task.setStatusTask("Uncompleted");
                        }else {
                            task.setStatusTask("Completed");
                        }
                    }
                }
            }
        }
    }
}
