package SAE;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static int[] tab;

    public static void main(String[] args) throws IOException {
        doRefacter(Integer.parseInt(args[0]));
    }

    public static void doRefacter(int nbColor) throws IOException {


        // Getting image
        BufferedImage image = ImageIO.read(new File("images_etudiants/originale.jpg"));

        // Getting length
        int width = image.getWidth();
        int heigth = image.getHeight();

        // Setting colors tab
        // Set data
        long ecart = 15000;
        Map<String, Integer> map = new HashMap<>();

        // Browse image
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                // Get RGB
                int[] RGB = getRgb(image.getRGB(j, i));

                // Check if color exist
                boolean isExist = false;
                for (String item : map.keySet()) {
                    System.out.print("x: " + j + " y: " + i + " ");
                    System.out.println(claculerDistance(getRgb(map.get(item)), RGB));
                    if (claculerDistance(getRgb(map.get(item)), RGB) < ecart) {
                        map.put(item, map.get(item) + 1);
                        isExist = true;
                        break;
                    }
                }

                // Add color if not exist in map
                if (!isExist) {

                    map.put(Integer.toString(image.getRGB(j, i)) , 1);
                }




            }


        }

        System.out.println(map);

        // Set up tab
        tab = new int[nbColor];
        for (int k = 0; k < nbColor; k++){
            tab[k] = map.get(map.keySet().toArray()[k]);
        }

        // Sort map by value in a list
        for (String item : map.keySet()) {
            for (int k = 0; k < nbColor; k++) {
                System.out.printf(item+  "");
                if (map.get(item) > map.get(tab[k])) {
                    tab[k] = Integer.parseInt(item);
                    break;
                }
            }
        }

        // Creating new image of the right length
        BufferedImage newImage = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

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
        int[] res = {(RGB & 0xff), (RGB & 0xff00), (RGB & 0xff0000)};
        return res;
    }

    public static int lessDistance(int rgb) {
        int min = (int) Double.MAX_VALUE;
        for (int item : tab) {
            if (claculerDistance(getRgb(item), getRgb(rgb)) < min) {
                min = item;
            }
        }
        return min;
    }

    public static long claculerDistance(int[] RGB1, int[] RGB2) {
        return (long) (Math.pow((RGB1[0] - RGB2[0]), 2) + Math.pow((RGB1[0] - RGB2[0]), 2) + Math.pow((RGB1[0] - RGB2[0]), 2));
    }
}
