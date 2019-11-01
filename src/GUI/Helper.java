package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Helper {

    public static boolean exitAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit?", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }



}
