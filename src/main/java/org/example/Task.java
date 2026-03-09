package org.example;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask, ComplexTask{
    private int idTask;
    private String statusTask;

    public abstract int estimateDuration();
}
