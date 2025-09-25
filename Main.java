package edu.ccrm;

import edu.ccrm.cli.Menu;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            menu.start();
        } catch (Throwable t) {
            Logger.getLogger(Main.class.getName()).severe("Fatal error: " + t.getMessage());
            System.out.println("A fatal error occurred. Exiting.");
        }
    }
}
