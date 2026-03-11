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

        if(!filtered.isEmpty()){
            for(Employee e: filtered){
                System.out.println(e.getName() + " works " + info.calculateEmployeeWorkDuration(e.getIdEmployee()));
            }
        }else {
            System.out.println("No employees found");
        }
    }

    public Map<String,Map<String, Integer>> nrOfTasks() {
        TasksManagement newManagement = TasksManagement.getInstance();
        Map<Employee, List<Task>> employees =  newManagement.getEmployees();
        Map<String,Map<String, Integer>> result = new HashMap<>();
        String employeeName;

        for(Employee e: employees.keySet()){
            Integer[] taskNumber = new Integer[2];
            taskNumber[0] = 0; taskNumber[1] = 0;
            List<Task> temp = employees.get(e);
            employeeName = e.getName();
            for(Task task: temp){
                if("Completed".equals(task.getStatusTask())) {
                    taskNumber[0] = taskNumber[0] + 1;
                }else {
                    taskNumber[1] = taskNumber[1] + 1;
                }
            }
            Map<String, Integer> mapTask = new HashMap<>();
            mapTask.put("Completed", taskNumber[0]);
            mapTask.put("Uncompleted", taskNumber[1]);
            result.put(employeeName,mapTask);
        }

        return result;
    }
}
