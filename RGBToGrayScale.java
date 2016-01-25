import java.awt.image.BufferedImage;
import java.io.File;
import java.io.imageio.ImageIO;

public class RGBToGrayScale {
	
	private static BufferedImage buffer;
	
	public static void convertFromFile(String filePath) {
		try {
			buffer = new BufferedImage(new File(filePath));
			createGrayScaleImage(buffer, buffer.getHeight(), buffer.getWidth());
		} catch (IOException ioe) {
			System.out.println("Failed to open the file: " + filePath);
		}
	
	}
	
	private static void createGrayScaleImage(BufferedImage rgbImage, int height, int width) {
		int red, green, blue;
		int grayShade;
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				red = (rgbImage.getRGB(x, y) >> 16) & 0b1111_1111;
				green = (rgbImage.getRGB(x, y) >> 8) & 0b1111_1111;
				blue = rgbImage.getRGB(x, y) & 0b1111_1111;
				grayShade = meanOf(red, green, blue);
				grayShade = (grayShade << 16) + (grayShade << 8) + grayShade;
				grayImage.setRGB(x, y, grayShade);
			}
		}
		saveIamge(grayImage, "filename", "png");
	}
	
	private static int meanOf(int r, int g, int b) {
		return (r + g + b) / 3;
	}
	
	private static void saveIamge(BufferedImage image, String name, String format) {
		File file = new File(name + "." + format.toLoweCase);
		try {
			ImageIO.write(image, format.toUpperCase, file);
		} catch (IOException ioe) {
			System.out.println("Failed to save the image");
		}
	}
	
}
