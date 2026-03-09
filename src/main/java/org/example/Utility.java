package org.example;

import java.util.*;

public class Utility {
    public void utilityFilter(TasksManagement info) {
        Map<Employee, List<Task>> data = info.getEmployees();
        List<Employee> filtered = new ArrayList<>();

        for(Employee e: data.keySet()){
            if(info.calculateEmployeeWorkDuration(e.getIdEmployee()) > 40) {
                filtered.add(e);
            }
        }

        filtered.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                int d1 = info.calculateEmployeeWorkDuration(e1.getIdEmployee());
                int d2 = info.calculateEmployeeWorkDuration(e2.getIdEmployee());
                return Integer.compare(d1, d2);
            }
        });

        for(Employee e: filtered){
            System.out.println(e.getName() + " works " + info.calculateEmployeeWorkDuration(e.getIdEmployee()));
        }
    }
}
