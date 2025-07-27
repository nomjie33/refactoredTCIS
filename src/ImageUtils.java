import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {
    public static ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            // Load original image
            ImageIcon originalIcon = new ImageIcon(ImageUtils.class.getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Create scaled version
            Image scaledImage = originalImage.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH  // For high-quality scaling
            );

            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error resizing image: " + e.getMessage());
            return null;
        }
    }

    // Higher quality alternative using BufferedImage
    public static ImageIcon highQualityResize(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(ImageUtils.class.getResource(imagePath));
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = resizedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImage, 0, 0, width, height, null);
            g2.dispose();

            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}