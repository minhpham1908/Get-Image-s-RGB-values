package minh.yuhnim;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        ImageProcesser imageProcesser = new ImageProcesser();
        System.out.println("Hello world!");
        BufferedImage img = null;
//        int[] rgbArray = new int[1000];
        try {
            File imgFile = new File("res/pic1.png");
           System.out.println("Load anh thanh cong");
           img =ImageIO.read(imgFile);
//            int[] color= img.getRGB(0,0,img.getWidth(),img.getHeight(),null,0,160);
//            int[][] grbMatrix = getRGB(img);
//            int row = img.getHeight();
//            int column = img.getWidth();
//            showRGB(grbMatrix,0,1);
//            showRGB(grbMatrix,1,1);
//            showRGB(grbMatrix,2,1);
//            showRGB(grbMatrix,row,column,1);

        } catch (IOException e) {
            System.out.println(e);
        }

        imageProcesser.LoadImage(img);
        int [][] rgbArray = imageProcesser.getRGBArray(img);
        int [][] redArray = imageProcesser.GetRedArray(rgbArray);
        int [][] blueArray = imageProcesser.GetBlueArray(rgbArray);
        int [][] greenArray = imageProcesser.GetGreenArray(rgbArray);
        int [][] yArray = imageProcesser.ConvertRGBToY(redArray,blueArray,greenArray);

        imageProcesser.PrintMatrix(rgbArray);
        imageProcesser.PrintMatrix(redArray);
        imageProcesser.PrintMatrix(blueArray);
        imageProcesser.PrintMatrix(greenArray);

        BufferedImage temp = new BufferedImage(8,8, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < temp.getHeight(); i++) {
            for (int j = 0; j < temp.getWidth(); j++) {

                temp.setRGB(i,j, rgbArray[j][i]);
            }
        }
        ImageIO.write(temp,"png", new File("res/ycolor.png"));
    }

    public static int[][] getRGB(BufferedImage img) {
        int[][] data = new int[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                data[j][i] = img.getRGB(i,j);
            }
        }
        return data;
    }

    public static void showRGB(int[][] grbMatrix, int x, int y) {
        System.out.println();
        int red = (grbMatrix[x][y] & 0xff0000) >>16;
        System.out.println(red);

        int green = (grbMatrix[x][y] & 0xff00) >> 8;
        System.out.println(green);
        int blue = (grbMatrix[x][y] & 0xff);
        System.out.println(blue);
    }
    public static void showRGB(int[][] grbMatrix, int row, int column,int f) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                int red = (grbMatrix[i][j] & 0xff0000) >> 16;
                System.out.print(red + " ");
                int green = (grbMatrix[i][j] & 0xff00) >> 8;
                System.out.print(green + " ");
                int blue = (grbMatrix[i][j] & 0xff);
                System.out.print(blue+ " \t");
            }
            System.out.println();
        }

    }
}
