package SAE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class KMean {

    public static int[] getRgb(int RGB) {
        int[] res = {(RGB & 0xff), (RGB & 0xff00) >> 8, (RGB & 0xff0000) >> 16};
        return res;
    }


    public static int calculerDistance(int[] RGB1, int[] RGB2) {
        return (int) (Math.pow((RGB1[0] - RGB2[0]), 2) + Math.pow((RGB1[1] - RGB2[1]), 2) + Math.pow((RGB1[2] - RGB2[2]), 2));
    }

    public static void main(String[] args) throws IOException {
        // Vérification des arguments en ligne de commande

        // Initialisation des variables
        /*
        if (args.length < 2) {
            System.out.println("Usage: java KMeans <nombre de groupes> <chemin de l'image>");
            return;
        }

        int Ng = Integer.parseInt(args[0]);
        BufferedImage image = ImageIO.read(new File(args[1]));
         **/
        int Ng = 3;
        BufferedImage image = ImageIO.read(new File("images_diverses_small/animaux/ours.png"));


        int width = image.getWidth();
        int height = image.getHeight();
        int[] tab = new int[Ng]; // Utiliser un tableau pour stocker les centroides

        // Initialisation des centroides
        for (int i = 0; i < Ng; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int rgb = image.getRGB(x, y);
            tab[i] = rgb;
        }

        boolean carryOn = true;
        HashMap<Integer, ArrayList<Pixel>> groupes = new HashMap<>();

        while (carryOn) {
            // Initialisation des groupes
            for (int i = 0; i < Ng; i++) {
                groupes.put(i, new ArrayList<>());
            }

            // Construction des groupes
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int rgb = image.getRGB(j, i);
                    int min = Integer.MAX_VALUE;
                    int indice = 0;
                    for (int k = 0; k < Ng; k++) {
                        int distance = calculerDistance(getRgb(tab[k]), getRgb(rgb));
                        if (distance < min) {
                            min = distance;
                            indice = k;
                        }
                    }
                    groupes.get(indice).add(new Pixel(j, i, rgb));
                }
            }

            // Mise à jour des centroides
            boolean convergence = true;

            for (int i = 0; i < Ng; i++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                ArrayList<Pixel> pixels = groupes.get(i);

                for (Pixel pixel : pixels) {
                    int[] rgb = getRgb(pixel.getRgb());
                    sumRed += rgb[0];
                    sumGreen += rgb[1];
                    sumBlue += rgb[2];
                }

                int newRed = sumRed / pixels.size();
                int newGreen = sumGreen / pixels.size();
                int newBlue = sumBlue / pixels.size();
                int newCentroid = new Color(newRed, newGreen, newBlue).getRGB();

                if (newCentroid != tab[i]) {
                    tab[i] = newCentroid;
                    convergence = false;
                }
            }

            if (convergence) {
                carryOn = false;
            }
        }

        // Création de la nouvelle image avec les couleurs réduites
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = image.getRGB(j, i);
                int min = Integer.MAX_VALUE;
                int indice = 0;
                for (int k = 0; k < Ng; k++) {
                    int distance = calculerDistance(getRgb(tab[k]), getRgb(rgb));
                    if (distance < min) {
                        min = distance;
                        indice = k;
                    }
                }
                newImage.setRGB(j, i, tab[indice]);
            }
        }

        // Enregistrement de l'image résultante
        File outputFile = new File("resultat.png");
        ImageIO.write(newImage, "png", outputFile);

        System.out.println("Traitement terminé. Le résultat a été enregistré dans le fichier resultat.png.");
    }
}
