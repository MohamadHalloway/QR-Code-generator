package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Helper {

    private URL savedUrl = getClass().getResource("saved.png");



    public static boolean exitAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit?", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    public static void createImage(boolean[][] boo, int scale, String path){
        BufferedImage image = new BufferedImage(boo.length * scale, boo[0].length * scale, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < boo.length; i++) {
            for (int j = 0; j < boo[i].length; j++) {
                for (int k = 0; k < scale; k++) {
                    for (int l = 0; l < scale; l++) {
                        if (boo[i][j]) {
                            image.setRGB((j * scale) + l, (i * scale) + k, Color.BLACK.getRGB());
                        } else {
                            image.setRGB((j * scale) + l, (i * scale) + k, Color.WHITE.getRGB());
                        }
                    }
                }
            }
        }

        try {
            // retrieve image
            File outputfile = new File(path);
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            System.err.println("Scheiße");
        }
    }
}
