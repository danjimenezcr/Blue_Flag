import com.formdev.flatlaf.FlatLightLaf;

import view.LoginWindow;

public class Main {
    public static void main(String[] args) {
        
        FlatLightLaf.setup();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }
}