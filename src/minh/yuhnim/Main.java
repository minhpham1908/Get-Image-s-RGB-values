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
        image = ImageIO.read(new File("./res/huff_simple0.png"));


        imageProcesser.LoadImage(image);
        int rgb[][] = imageProcesser.getRGBArray();
        int red[][] = imageProcesser.GetRedArray(rgb);
        int green[][] = imageProcesser.GetGreenArray(rgb);
        int blue[][] = imageProcesser.GetBlueArray(rgb);
        int Y[][] = imageProcesser.ConvertRGBToY(red,green,blue);
        int Cb[][] = imageProcesser.ConvertRGBToCb(red,green,blue);
        int Cr[][] = imageProcesser.ConvertRGBToCr(red,green,blue);
        Y = imageProcesser.shift(Y);

        int quan[][] = imageProcesser.quant(Y);
        PrintMatrix(quan);

    }


    public static void PrintMatrix(int[][] matrix) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" | " + matrix[i][j]);
            }
            System.out.println();
        }
    }

}
