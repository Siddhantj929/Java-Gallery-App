package MemoryTreasure;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class controller_2 {

    @FXML
    TextField textField_directory = new TextField();

    public String getDirectory(){
        return textField_directory.getText();
    }
}
