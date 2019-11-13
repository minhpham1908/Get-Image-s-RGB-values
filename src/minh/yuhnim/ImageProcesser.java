package minh.yuhnim;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcesser {
    private BufferedImage image;
    public int imageHeight;
    public int imageWidth;

    public ImageProcesser() {

    }

    public void LoadImage(BufferedImage image) {
        this.image = image;
        this.imageHeight = image.getHeight();
        this.imageWidth = image.getWidth();
        System.out.println("Loaded!");
    }

    public int[][] getRGBArray(BufferedImage image) {
        int[][] rgbArray = new int[imageHeight][imageWidth];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                rgbArray[j][i] = image.getRGB(i, j);
            }
        }
        return rgbArray;
    }

    public int[][] GetRedArray(int[][] rgbArray) {
        int[][] redArray = new int[imageHeight][imageHeight];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                redArray[i][j] = (rgbArray[i][j] >> 16) & 0xff;
            }
        }

        return redArray;
    }

    public int[][] GetGreenArray(int[][] rgbArray) {
        int[][] greenArray = new int[imageHeight][imageHeight];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                greenArray[i][j] = (rgbArray[i][j] >> 8) & 0xff;
            }
        }

        return greenArray;
    }

    public int[][] GetBlueArray(int[][] rgbArray) {
        int[][] blueArray = new int[imageHeight][imageHeight];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                blueArray[i][j] = (rgbArray[i][j]) & 0xff;
            }
        }

        return blueArray;
    }

    //https://en.wikipedia.org/wiki/YCbCr#JPEG_conversion
    private double kr = 0.299;
    private double kg = 0.587;
    private double kb = 0.114;

    public int[][] ConvertRGBToY(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int[][] Y = new int[imageHeight][imageWidth];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                double y = kr * redArray[i][j] + kg * greenArray[i][j] + kb * blueArray[i][j];
                Y[i][j] = (int) (y);
            }
        }

        return Y;
    }

    public int[][] ConvertRGBToCb(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int[][] Cb = new int[imageHeight][imageHeight];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                double y = kr * redArray[i][j] + kg * greenArray[i][j] + kb * blueArray[i][j];
                Cb[i][j] = (int) ((blueArray[i][j] - y) / (2 * (1 - kb)));
            }
        }

        return Cb;
    }

    public int[][] ConvertRGBToCr(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int[][] Cr = new int[imageHeight][imageHeight];
         for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                double y = kr * redArray[i][j] + kg * greenArray[i][j] + kb * blueArray[i][j];
                Cr[i][j] = (int) ((redArray[i][j] - y) / (2 * (1 - kr)));
            }
        }

        return Cr;
    }

    public int[][] ConvertYCbCrToR(int[][] Y, int[][] Cb, int[][] Cr) {
        int[][] redArray = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                redArray[i][j] = (int) (Y[i][j] + 1.402*(Cr[i][j] -128));
            }
        }
        return redArray;
    }
    public int[][] ConvertYCbCrToG(int[][] Y, int[][] Cb, int[][] Cr) {
        int[][] greenArray = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                greenArray[i][j] = (int) (Y[i][j] - 0.34414*(Cb[i][j] -128)- 0.71414*(Cr[i][j] -128));
            }
        }
        return greenArray;
    }
    public int[][] ConvertYCbCrToB(int[][] Y, int[][] Cb, int[][] Cr) {
        int[][] blueArray = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                blueArray[i][j] = (int) (Y[i][j] + 1.772*(Cb[i][j] -128));
            }
        }
        return blueArray;
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
