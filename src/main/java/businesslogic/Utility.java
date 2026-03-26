package businesslogic;

import datamodel.ComplexTask;
import datamodel.Employee;
import datamodel.Task;

import java.util.*;

public class Utility {
    public List<Employee> utilityFilter(TasksManagement info) {
        Map<Employee, List<Task>> data = info.getEmployees();
        Map<Employee, Integer> timeMap = new HashMap<>();

        for(Employee emp: data.keySet()){
            timeMap.put(emp, info.calculateEmployeeWorkDuration(emp.getIdEmployee()));
        }

        ArrayList<Employee> filtered = new ArrayList<>();
        for(Employee emp: data.keySet()){
            if(timeMap.get(emp) > 40){
                filtered.add(emp);
            }
        }

        filtered.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                return Integer.compare(timeMap.get(emp1), timeMap.get(emp2));
            }
        });

        return filtered;
    }

    public Map<String,Map<String, Integer>> nrOfTasks() {
        TasksManagement newManagement = TasksManagement.getInstance();
        Map<Employee, List<Task>> employees =  newManagement.getEmployees();
        Map<String,Map<String, Integer>> result = new HashMap<>();
        String employeeName;

        for(Employee emp: employees.keySet()){
            Integer[] taskNumber = new Integer[2];
            taskNumber[0] = 0; taskNumber[1] = 0;
            List<Task> temp = employees.get(emp);
            employeeName = emp.getName();
            for(Task task: temp){
                if(task instanceof ComplexTask) {
                    for(Task tsk : ((ComplexTask) task).getSubTasks()) {
                        if("Completed".equals(task.getStatusTask())) {
                            taskNumber[0] = taskNumber[0] + 1;
                        }else {
                            taskNumber[1] = taskNumber[1] + 1;
                        }
                    }
                }
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
