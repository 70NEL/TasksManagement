package org.example;

import java.io.Serializable;
import java.util.*;

public class TasksManagement implements Serializable {
    private         Map<Employee, List<Task>> employees = new HashMap<>();

    public void assignTaskToEmployee(int idEmployee, Task task) {
        for (Employee employee : employees.keySet()) {
            if(idEmployee == employee.getIdEmployee()){
                employees.get(employee).add(task);
            }
        }
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        for(Employee e: employees.keySet()) {

        }
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {

    }
}
