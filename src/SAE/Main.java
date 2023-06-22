package SAE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    static int[] tab;
    public static void main(String[] args) throws IOException {
        doRefacter();



    }

    public static void doRefacter() throws IOException {
        // Setting colors tab


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
                newImage.setRGB(j,i, lessDistance(image.getRGB(j,i)) );
            }
        }

        // Saving new image
        ImageIO.write(newImage, "png", new File("Q2.png"));
    }

    public static int[] getRgb(int RGB) {
        int[] res = {(RGB & 0xff),(RGB & 0xff00), (RGB & 0xff0000) };
        return res;
    }

    public static int lessDistance(int rgb) {
        int min = (int) Double.MAX_VALUE;
        for (int item : tab){
            if (claculerDistance(getRgb(item), getRgb(rgb)) < min){
               min = item;
            }
        }
        return min;
    }

    public static long claculerDistance(int[] RGB1, int[] RGB2){
        return (long)(Math.pow((RGB1[0]- RGB2[0]),2) + Math.pow((RGB1[0]-RGB2[0]),2) + Math.pow((RGB1[0]- RGB2[0]),2));
    }
}
