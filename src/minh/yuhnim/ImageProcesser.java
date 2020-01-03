package minh.yuhnim;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcesser {
    private BufferedImage image;
    public int imageHeight;
    public int imageWidth;

    private int[][] lumi_quantizier = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}};

    private int[][] chroma_quantizier = {
            {17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}
    };

    public ImageProcesser() {

    }

    public void LoadImage(BufferedImage image) {
        this.image = image;
        this.imageHeight = image.getHeight();
        this.imageWidth = image.getWidth();
        System.out.println("Loaded!");
    }

    public int[][] getRGBArray() {
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
                redArray[i][j] = (int) (Y[i][j] + 1.402 * (Cr[i][j] - 128));
            }
        }
        return redArray;
    }

    public int[][] ConvertYCbCrToG(int[][] Y, int[][] Cb, int[][] Cr) {
        int[][] greenArray = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                greenArray[i][j] = (int) (Y[i][j] - 0.34414 * (Cb[i][j] - 128) - 0.71414 * (Cr[i][j] - 128));
            }
        }
        return greenArray;
    }

    public int[][] ConvertYCbCrToB(int[][] Y, int[][] Cb, int[][] Cr) {
        int[][] blueArray = new int[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                blueArray[i][j] = (int) (Y[i][j] + 1.772 * (Cb[i][j] - 128));
            }
        }
        return blueArray;
    }

    public void PrintMatrix(int[][] matrix) {
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                System.out.print(" | " + matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void showRGB(int[][] grbMatrix, int x, int y) {
        System.out.println();
        int red = (grbMatrix[x][y] & 0xff0000) >> 16;
        System.out.println(red);
        int green = (grbMatrix[x][y] & 0xff00) >> 8;
        System.out.println(green);
        int blue = (grbMatrix[x][y] & 0xff);
        System.out.println(blue);
    }

    public static void showRGB(int[][] grbMatrix, int row, int column, int f) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                int red = (grbMatrix[i][j] & 0xff0000) >> 16;
                System.out.print(red + " ");
                int green = (grbMatrix[i][j] & 0xff00) >> 8;
                System.out.print(green + " ");
                int blue = (grbMatrix[i][j] & 0xff);
                System.out.print(blue + " \t");
            }
            System.out.println();
        }
    }

    public int[][] shift(int[][] a) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                a[i][j] -= 128;
            }
        }
        return a;
    }

    public double[][] DCT(int[][] g) {

        double[][] G = new double[8][8];
        double Cu;
        double Cv;
        for (int u = 0; u < 8; u++) {
            for (int v = 0; v < 8; v++) {
                int temp = 0;
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        temp += g[x][y] * Math.cos(((2.0 * x + 1.0) * u * Math.PI) / 16.0) * Math.cos(((2.0 * y + 1.0) * v * Math.PI) / 16.0);
                    }
                }
                if (u == 0) Cu = 1 / Math.sqrt(2);
                else Cu = 1;
                if (v == 0) Cv = 1 / Math.sqrt(2);
                else Cv = 1;
                G[u][v] = Cu * Cv * temp / 4;
            }
        }
        return G;
    }

    public int[][] quant(int[][] g) {
        double[][] G = DCT(g);
        int[][] Q = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Q[i][j] = (int) Math.round(G[i][j]/lumi_quantizier[i][j]);
            }
        }
        return Q;
    }

    private void PrintMatrix(double[][] g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(g[i][j] + " | ");
            }
            System.out.println();
        }
    }
}
