package datamodel;

import java.util.*;

public non-sealed class ComplexTask extends Task{
    private List<Task> subTasks = new ArrayList<>();

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    @Override
    public int estimateDuration() {
        int sum = 0;
        for(Task tsk: subTasks) {
            sum += tsk.estimateDuration();
        }
        return sum;
    }

    public boolean isAncestorOf(Task tsk) {
        if(this == tsk) {
            return true;
        }

        for(Task son: this.subTasks) {
            if(son == tsk) {
                return true;
            }
            if(son instanceof ComplexTask) {
                if(((ComplexTask) son).isAncestorOf(tsk)) {
                    return true;
                }

            }
        }

        return false;
    }
 }
