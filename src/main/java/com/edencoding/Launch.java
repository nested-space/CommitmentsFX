package com.edencoding;

import com.edencoding.dao.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class Launch extends Application {

    /**
     * The entry point for this application
     *
     * @param args commandline arguments, not currently handled
     */
    public static void main(String[] args) {
        if (Database.isOK()) {  //check for driver errors
            launch();
        } else {
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("SETUP:\tLaunching Main Window");
    }

}
