package minh.yuhnim;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageProcesser imageProcesser = new ImageProcesser();
        BufferedImage image = null;
        image = ImageIO.read(new File("./res/huff_simple0.jpg"));


        imageProcesser.LoadImage(image);
        int rgb[][] = imageProcesser.getRGBArray();
        int red[][] = imageProcesser.GetRedArray(rgb);
        int green[][] = imageProcesser.GetGreenArray(rgb);
        int blue[][] = imageProcesser.GetBlueArray(rgb);
        int Y[][] = imageProcesser.ConvertRGBToY(red,green,blue);
        int Cb[][] = imageProcesser.ConvertRGBToCb(red,green,blue);
        int Cr[][] = imageProcesser.ConvertRGBToCr(red,green,blue);
        int test[][]= imageProcesser.getBlock(Y,0);
        PrintMatrix(red, image.getHeight(), image.getWidth());
        PrintMatrix(green, image.getHeight(), image.getWidth());
        PrintMatrix(blue, image.getHeight(), image.getWidth());
        PrintMatrix(test, 8,8);
        int quanM[][] = imageProcesser.quant(test);
        PrintMatrix(quanM,8, 8);

    }


    public static void PrintMatrix(int[][] matrix, int imageHeight, int imageWidth) {
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                System.out.print(" | " + matrix[i][j]);
            }
            System.out.println();
        }
    }

}
