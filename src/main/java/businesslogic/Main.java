package businesslogic;

import gui.GUI;
import dataaccess.SerializationOperations;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        try{
            SerializationOperations.load();
        }catch(Exception e){
            System.out.println("Load Serialization failed");
        }

        JFrame frame = new JFrame();
        GUI gui = new GUI();
        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    SerializationOperations.save();
                }catch(Exception ex) {
                    System.out.println("Serialization Save failed");
                }
                System.exit(0);
            }
        });

        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}