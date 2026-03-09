package org.example;

import java.io.Serializable;

public class Employee implements Serializable {
    private int idEmployee;
    private String name;

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setName(String name) {
        this.name = name;
    }
}
