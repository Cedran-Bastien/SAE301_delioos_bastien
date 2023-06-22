package TP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainQ2 {
    public static void main(String[] args) throws IOException {
        // Getting image
        BufferedImage image = ImageIO.read(new File("images_etudiants/originale.jpg"));

        // Getting length
        int width = image.getWidth();
        int heigth = image.getHeight();

        // Creating new image of the right length
        BufferedImage newImage = new BufferedImage(width,heigth, BufferedImage.TYPE_3BYTE_BGR);

        // pour chaque ligne
        for(int i = 0; i<heigth; i++){
            // Pour chaque colone
             for (int j = 0; j<width; j++){
                 // Set RGB ou each pixel
                 newImage.setRGB(j,i, image.getRGB(j,i));
             }
        }

        // Saving new image
        ImageIO.write(newImage, "png", new File("Q2.png"));

    }

    public int[] getRgb(int RGB) {
        int[] res = {(RGB & 0xff),(RGB & 0xff00), (RGB & 0xff0000) };
        return res;
    }
}
