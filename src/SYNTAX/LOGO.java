/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LOGO {

    public static void setIconResize(JLabel label, String fileName) {

        ImageIcon icon = new ImageIcon(
                LOGO.class.getResource("/ASSETS/" + fileName));

        Image image = icon.getImage();

        Image resize = image.getScaledInstance(
                label.getWidth(),
                label.getHeight(),
                Image.SCALE_SMOOTH);

        label.setIcon(new ImageIcon(resize));
    }
}