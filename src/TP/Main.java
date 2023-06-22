package TP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {




        // Getting image
        BufferedImage image = ImageIO.read(new File("images_etudiants/originale.jpg"));

        // Writing image to copy it
        ImageIO.write(image, "png", new File("copi.png"));
    }
}
