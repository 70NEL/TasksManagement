package datamodel;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Employee other = (Employee) obj;
        return this.idEmployee == other.idEmployee && Objects.equals(this.name, other.name);
    }
}
