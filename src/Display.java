import javax.swing.*;
import java.awt.*;

class Display extends JFrame {

    private boolean visible;
    private JLabel jlabel;

    Display() {

        super("Image"); // Titre de la fenêtre
        setPreferredSize(new Dimension(400, 500));  // largeur, hauteur

        jlabel = new JLabel();

        visible = false;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(jlabel, BorderLayout.CENTER);
        this.pack();
    }

    void setImage(Image blop) {

        if (!visible) {
            visible = true;
            this.setVisible(true);
        }

        jlabel.setIcon(new ImageIcon(blop));

    }

    /**
     * La fenêtre n'est plus visible
     */
    void close() {

        this.dispose();
    }

}
