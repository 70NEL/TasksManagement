package org.example;

import java.io.Serializable;
import java.util.*;

public non-sealed class ComplexTask extends Task implements Serializable {
    private List<Task> subTasks = new ArrayList<>();

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    @Override
    public int estimateDuration() {
        int sum = 0;
        for(Task t: subTasks) {
            sum =+ t.estimateDuration();
        }
        return sum;
    }
 }
