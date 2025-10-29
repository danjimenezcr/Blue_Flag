/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User;
import view.LoginWindow;
import view.MainWindow;

/**
 *
 * @author dannyjimenez
 */
public class MainController {
    
    private MainWindow main;
    
    public MainController(){
        
    }
    
    public MainController(MainWindow main){
        this.main = main;
    }
    
    public void handleLogout(User user){
        this.main.dispose();
        new LoginWindow().setVisible(true);
    }
    
    
}
