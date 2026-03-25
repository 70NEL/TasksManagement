package dataaccess;

import java.io.*;

import businesslogic.TasksManagement;

public class SerializationOperations {
    public static void save() throws IOException {
        TasksManagement newManagement = TasksManagement.getInstance();

        FileOutputStream fileOutputStream = new
                FileOutputStream("projectData.ser");
        ObjectOutputStream objectOutputStream = new
                ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(newManagement);
        objectOutputStream.flush();
        objectOutputStream.close();
    }



    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream
                ("projectData.ser");
        ObjectInputStream objectInputStream = new
                ObjectInputStream(fileInputStream);

        TasksManagement restoredUser = (TasksManagement) objectInputStream.
                readObject(); objectInputStream.close();

        TasksManagement.getInstance().getEmployees().putAll(restoredUser.getEmployees());
        TasksManagement.getInstance().getTasks().addAll(restoredUser.getTasks());

        System.out.println("TaskManagement restored successfully");
    }
}
