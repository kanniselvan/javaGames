package com.kanni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleNumber {
    JFrame f1;
    JPanel p1, p2, p3;
    int x = 0;
    JButton[] b = new JButton[65];
    ImageIcon ii = new ImageIcon("src/com/kanni/images/puzzle.png");
    Image im = ii.getImage();
    int h[] = new int[63];
    JLabel l1;
    JButton b1, b2;
    int m, r = 0, s = 0;
    Font font = new Font("Serief", Font.BOLD, 20);
    String buttonValue;
    int ch1, ch2;
    boolean check = false;
    boolean state = false;

    AtomicInteger move=new AtomicInteger(1);

    PuzzleNumber() {
        f1 = new JFrame("Puzzle Game");
        f1.setIconImage(im);

        f1.getContentPane().setLayout(new GridLayout(1, 1));
        p1 = new JPanel(new GridLayout(8, 8));
        p2 = new JPanel(new GridLayout(1, 4, 3, 5));
        p3 = new JPanel(new BorderLayout());
        for (int k = 0; k < 63; k++) {
            h[k] = 0;
        }

        while (true) {
            m = (int) (Math.random() * 63);
            if (h[m] == 0) {
                h[m] = x;
                x++;
            }
            if (x == 63)
                break;
        }

        for (int y = 0; y <= 62; y++) {
            if (h[y] == 0) {
                h[y] = 63;
            }
        }

        for (int i = 1; i <= 64; i++) {
            if (i == 64)
                b[i] = new JButton(" ");
            else
                b[i] = new JButton("" + h[i - 1]);
            p1.add(b[i]);
            b[i].setForeground(Color.blue);
            b[i].setEnabled(false);
            b[i].setFont(font);
            b[i].addMouseListener(new MouseActionListener());
        }

        l1 = new JLabel("0", SwingConstants.CENTER);
        l1.setFont(font);
        l1.setForeground(Color.black);
        b1 = new JButton(getScaledIcon(new ImageIcon("src/com/kanni/images/play.png").getImage(),0.30));
        b2 = new JButton(getScaledIcon(new ImageIcon("src/com/kanni/images/verification.png").getImage(),0.60));
        b2.setEnabled(false);
        p2.add(b1);
        p2.add(new JLabel(getScaledIcon(new ImageIcon("src/com/kanni/images/move.png").getImage(),0.50)));
        p2.add(l1);
        p2.add(b2);
        b1.addActionListener(new ButtonAction());
        b2.addActionListener(new ButtonAction());
        p3.add("Center", p1);
        p3.add("North", p2);


        f1.getContentPane().add(p3);
        f1.addWindowListener(new WindowAction());
        f1.setBounds(200, 200, 500, 500);
        f1.setResizable(false);
        f1.setVisible(true);
    }

    private ImageIcon getScaledIcon(final Image image, final double scale) {
        ImageIcon scaledIcon = new ImageIcon(image) {
            public int getIconWidth() {
                return (int) (image.getWidth(null) * scale);
            }

            public int getIconHeight() {
                return (int) (image.getHeight(null) * scale);
            }

            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.drawImage(image, x, y, getIconWidth(), getIconHeight(), c);
            }
        };
        return scaledIcon;
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == b1) {
                for (int n = 1; n <= 64; n++)
                    b[n].setEnabled(true);
                state = true;

                b2.setEnabled(true);
                b1.setIcon(getScaledIcon(new ImageIcon("src/com/kanni/images/play.png").getImage(),0.30));
            }
            if (ae.getSource() == b2) {
              b1.setIcon(getScaledIcon(new ImageIcon("src/com/kanni/images/stop.png").getImage(),0.30));
                for (int n = 1; n <= 64; n++){
                    b[n].setEnabled(false);
                }

                state = false;
                for (int i = 1; i <= 63; i++) {
                    ch2 = Integer.parseInt(b[i].getText());
                    if (ch2 == i)
                        check = true;
                    else {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    ch1 = Integer.parseInt(l1.getText());
                    if (ch1 < 100)
                        JOptionPane.showMessageDialog(f1, "First Class Winning");
                    else if ((ch1 >= 100) && (ch1 < 200))
                        JOptionPane.showMessageDialog(f1, "Second Class Winning");
                    else
                        JOptionPane.showMessageDialog(f1, "Improve Your Speed....");
                } else {
                    JOptionPane.showMessageDialog(f1, "Invalid Order puzzle....");
                }
            }
        }
    }

    class MouseActionListener extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            JButton jButton = (JButton) me.getSource();
            if (null != jButton && jButton.isEnabled()) {
                for (int r = 0; r <= 64; r++) {
                    if (me.getSource() == b[r]) {
                        if (b[r].getText() != " ") {
                            s++;
                            if (s == 1) {
                                buttonValue = b[r].getText();
                                b[r].setText(" ");
                            } else {
                                System.out.println("\7");
//  f1.getContentPane().DebuggSound.beep();
                                JOptionPane.showMessageDialog(f1, "Paste the Number!!");
                            }
                        } else {
                            b[r].setText(buttonValue);
                            l1.setText(""+ move.getAndIncrement());
                            s = 0;
                            buttonValue = " ";
                        }
                    }
                }
            }
        }
    }


    class WindowAction extends WindowAdapter {
        public void windowClosing(WindowEvent we) {
            String[] select = {"Minesweeper","New", "Exit"};
            int option = JOptionPane.showOptionDialog(f1, "You Choose One Games?", "Choose the Games", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, select, "New");
            if (option == 0) {
                f1.dispose();
                new Minesweeper();
            } else if (option == 1) {
                f1.dispose();
                new PuzzleNumber();
            } else
                System.exit(0);
        }
    }

    public static void main(String x[]) {
        PuzzleNumber p = new PuzzleNumber();
    }
}
