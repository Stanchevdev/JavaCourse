import javafx.geometry.HorizontalDirection;

import javax.management.loading.MLet;
import javax.swing.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class FrameClass extends Reader {

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws FileNotFoundException {
        new FrameClass();
    }

    /**
     * Create the frame.
     */
    public FrameClass() throws FileNotFoundException {
        super("Welcome");
        getCategories();
        createTree();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 810, 345);
        getContentPane().setLayout(null);
        tree.setBounds(1, 1, 156, 344);
        getContentPane().add(tree);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,2));
        mainPanel.setBounds(157,1, 630,140);
        JLabel Jlabel = new JLabel("Welcome");
        Jlabel.setHorizontalAlignment(SwingConstants.CENTER);
        Jlabel.setBounds(302, 2, 75, 39);
        getContentPane().add(Jlabel);
        setVisible(true);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
                tree.getSelectionModel().setSelectionMode
                        (TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

                /* if nothing is selected */
                if (node == null) return;
                /* retrieve the node that was selected */
                Object nodeInfo = node.getUserObject();
                getProducts(nodeInfo);
                /* React to the node selection. */
                DefaultListModel<String> listModel = new DefaultListModel<>();
                listModel.removeAllElements();
                mainPanel.removeAll();
                for (int j = 0; j < products.size(); j++) {
                    JPanel panel = new JPanel();
                    StringBuilder nameOfProduct = new StringBuilder();
                    StringBuilder price = new StringBuilder();
                    StringBuilder img = new StringBuilder();

                    String data = products.get(j);
                    int step = 0;
                    int letter = 0;
                    for (int c = 0; c <= products.size(); c++) {
                        if (step == 0) {
                            while (data.charAt(letter) != ',') {
                                nameOfProduct.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        } else if (step == 1) {
                            letter++;
                            while (data.charAt(letter) != ',') {
                                price.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        } else if (step == 2) {
                            letter++;
                            while (data.charAt(letter) != '.') {
                                img.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        }
                    }

                    JLabel prLabel = new JLabel(String.valueOf(nameOfProduct));
                    JLabel priceLabel = new JLabel(String.valueOf(price));
                    ImageIcon imageIcon = new ImageIcon("src/" + img + ".jpg"); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);
                    prLabel.setIcon(imageIcon);

                    panel.setBounds(10,0, 100, 100);
                    panel.setLayout(new GridLayout(2,1));
                    panel.setBorder(BorderFactory.createEmptyBorder());
                    panel.add(prLabel);
                    panel.add(priceLabel);
                    mainPanel.add(panel);
                    System.out.println(img);

                }
                getContentPane().remove(Jlabel);
                mainPanel.repaint();
                getContentPane().add(mainPanel);
                getContentPane().repaint();
            }
        });
    }
}