import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    boolean visible;
    private JLabel jlabel;

    public Display() {

        super("Image"); // Titre de la fenêtre
        setPreferredSize(new Dimension(400, 500));  // largeur, hauteur

        jlabel = new JLabel();

        visible = false;

        this.add(jlabel, BorderLayout.CENTER);
        this.pack();
    }

    public void setImage(Image blop) {

        if (!visible) {
            visible = true;
            this.setVisible(true);
        }

        jlabel.setIcon(new ImageIcon(blop));

    }

    /**
     * La fenêtre n'est plus visible
     */
    public void close() {

        this.dispose();
    }

}
