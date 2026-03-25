package businesslogic;

import datamodel.Employee;
import datamodel.Task;

import java.util.*;

public class Utility {
    public List<Employee> utilityFilter(TasksManagement info) {
        Map<Employee, List<Task>> data = info.getEmployees();
        List<Employee> filtered = new ArrayList<>();

        for(Employee emp: data.keySet()){
            if(info.calculateEmployeeWorkDuration(emp.getIdEmployee()) > 40) {
                filtered.add(emp);
            }
        }

        filtered.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                int d1 = info.calculateEmployeeWorkDuration(emp1.getIdEmployee());
                int d2 = info.calculateEmployeeWorkDuration(emp2.getIdEmployee());
                return Integer.compare(d1, d2);
            }
        });

        return filtered;
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
