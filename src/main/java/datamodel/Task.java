package datamodel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask, ComplexTask {
    private int parentId = -1;
    private int idTask;
    private String statusTask;

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public abstract int estimateDuration();

    @Override
    public String toString() {
        return "Task ID: " + idTask  + (this instanceof ComplexTask ? " Complex" : " Simple") + " " + statusTask;
    }
}
