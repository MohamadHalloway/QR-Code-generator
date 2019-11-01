package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.Localtests;
import jpp.qrcode.QRCode;
import jpp.qrcode.encode.Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;


public class View extends Application {
    private StackPane mainPain = new StackPane();

    private HBox hbox = new HBox();


    @Override
    public void start(Stage primaryStage) throws Exception {



        Image image = new Image(new FileInputStream("C:\\Users\\s353177\\IdeaProjects\\QR-Code\\src\\Junit_Tests\\saved.png"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(177);
        imageView.setFitWidth(177);


        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);

        hbox.getChildren().add(vBox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);


        Label label = new Label("Enter your text:");
        TextField textField = new TextField();


        //The enter button
        Button enter = new Button("enter");
        enter.setOnAction(e -> {
            String text = textField.getText();
            if (!text.isEmpty()) {
                QRCode qrCode = Encoder.createFromString(text, ErrorCorrection.LOW);
                Localtests.createImage(qrCode.data(), 20);
                Image updated = null;
                try {
                    updated = new Image(new FileInputStream("C:\\Users\\s353177\\IdeaProjects\\QR-Code\\src\\Junit_Tests\\saved.png"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (!hbox.getChildren().contains(imageView)) {
                    imageView.setImage(updated);
                    hbox.getChildren().add(imageView);
                } else {
                    hbox.getChildren().remove(imageView);
                    imageView.setImage(updated);
                    hbox.getChildren().add(imageView);
                }
            }
        });
//        vBox.getChildren().addAll(label, textField, enter);



        primaryStage.setOnCloseRequest(e -> {
            if (Helper.exitAlert()) {
                primaryStage.close();
            }else  {
                e.consume();
            }
        });

        mainPain.getChildren().add(hbox);
        mainPain.setId("pane");
        Scene scene = new Scene(mainPain, 700, 700);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


        // *********MENU*********
        Menu file = new Menu("File", new ImageView("file:FileIcon.png"));
        MenuItem newQrCode = new MenuItem("New");
        newQrCode.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Text Input Dialog");
            dialog.setHeaderText("Enter your text here");
            dialog.setContentText("");


            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                createQrCode(result.get());
                try {
                    showQrCode(imageView,vBox);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });



        file.getItems().add(newQrCode);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(file);
        mainPain.getChildren().add(menuBar);
        mainPain.setAlignment(Pos.BASELINE_CENTER);



        primaryStage.setTitle("QR-Code generator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showQrCode(ImageView imageView,VBox vbox) throws FileNotFoundException {
        Image updated = new Image(new FileInputStream("C:\\Users\\s353177\\IdeaProjects\\QR-Code\\src\\Junit_Tests\\saved.png"));
        imageView.setImage(updated);
        vbox.getChildren().clear();
        vbox.getChildren().add(new Label("                        "));
        vbox.getChildren().add(imageView);
    }

    private void createQrCode(String text) {
        QRCode qrCode = Encoder.createFromString(text, ErrorCorrection.LOW);
        Localtests.createImage(qrCode.data(), 20);
    }

}
