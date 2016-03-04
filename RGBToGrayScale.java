import java.awt.image.BufferedImage;
import java.io.File;
import java.io.imageio.ImageIO;

public class RGBToGrayScale {

	private static BufferedImage buffer;

	public static void convertFromFile(String filePath) {
		try {
			buffer = new BufferedImage(new File(filePath));
			convertToGrayscale(buffer);
		} catch (IOException ioe) {
			System.out.println("Failed to open the file: " + filePath);
		}

	}

	private static void convertToGrayscale(BufferedImage rgbImage) {
		int red, green, blue;
		int grayShade;
		int height = rgbImage.getHeight();
		int width = rgbImage.getWidth();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				red = (rgbImage.getRGB(x, y) >> 16) & 0b1111_1111;
				green = (rgbImage.getRGB(x, y) >> 8) & 0b1111_1111;
				blue = rgbImage.getRGB(x, y) & 0b1111_1111;
				grayShade = mean(red, green, blue);
				grayShade = (grayShade << 16) + (grayShade << 8) + grayShade;
				grayImage.setRGB(x, y, grayShade);
			}
		}
		saveImage(grayImage, "filename", "png");
	}

	private static int mean(int r, int g, int b) {
		return (r + g + b) / 3;
	}

	private static void saveImage(BufferedImage image, String name, String format) {
		File file = new File(name + "." + format.toLowerCase());
		try {
			ImageIO.write(image, format.toUpperCase(), file);
		} catch (IOException ioe) {
			System.out.println("Failed to save the image");
		}
	}

}
