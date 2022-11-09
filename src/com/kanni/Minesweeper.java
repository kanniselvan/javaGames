package com.kanni;


import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Minesweeper {

    JFrame f = new JFrame("Minesweeper");
    ImageIcon iconImg = new ImageIcon("src/com/kanni/images/icon.gif");
    ImageIcon successImg = new ImageIcon("src/com/kanni/images/success.gif");
    ImageIcon bugIcon = new ImageIcon("src/com/kanni/images/bug.jpg");
    ImageIcon puzzledIcon = new ImageIcon("src/com/kanni/images/puzzled.png");
    ImageIcon correctIcon = new ImageIcon("src/com/kanni/images/correct.png");
    ImageIcon icon = null;

    int height, width, mine, mm;
    static String[] name = {"KanniSelvan.k", "KanniSelvan.K"};

    static String[] sec = {"999", "999"};
    int i, x, y, rno1;
    static int flag = 1;
    static int cal;
    static int ncol = 9, nrow = 9, nmin = 10;
    JPanel p1, p3, best;
    JPanel p2;
    int count = 0;
    String playerName = " ", bse = "999", hms = " ";
    JLabel bl1, bl2, bl3, bl4, bl5, bl6, bl7, bl8, bl9;
    JButton bb1, bb2;
    JDialog d1, d2, help1, help2;
    static int fwidth = 350, fheight = 450;
    JButton[] b = new JButton[ncol * nrow];
    JPanel custom;
    JLabel cl1, cl2, cl3;
    JTextField ct1, ct2, ct3;
    JButton cb1, cb2;
    JLabel l1;
    public JLabel l2;

    AtomicBoolean state = new AtomicBoolean(true);
    AtomicBoolean startTimer = new AtomicBoolean(true);
    JLabel[] no = new JLabel[ncol * nrow];
    int rno;
    JLabel sl1, sl2;
    int bx, by, bw, bh;
    JLabel il1, il2, il3, il4, il5, il6, il7, il8, il9;
    JLabel al1, al2, al3, al4;
    JMenuBar mb;
    JMenu game, help;
    JMenuItem g1, g2, g3, g4, g5, pm;
    JCheckBoxMenuItem c2, c4, c5;
    Font font = new Font("Serief", Font.BOLD, 25);
    Font fff = new Font("Serief", Font.BOLD, 15);

    double imageSize = 0.40;

    JMenuItem h1, h2;
    JPopupMenu pp;

    public Minesweeper() {

        f.setIconImage(iconImg.getImage());
        f.getContentPane().setLayout(new GridLayout(1, 1));
        mb = new JMenuBar();
        f.setJMenuBar(mb);
        game = new JMenu("Menu");
        g1 = new JMenuItem("New ");
        c2 = new JCheckBoxMenuItem("puzzle.. ");
        g2 = new JMenuItem("Custom");
        g3 = new JMenuItem("Color");
        g4 = new JMenuItem("Best Time");
        g5 = new JMenuItem("Exit");
        game.add(g1);
        game.addSeparator();
        game.add(g2);
        game.add(c2);
        game.addSeparator();
        game.add(g3);
        game.addSeparator();
        game.add(g4);
        game.addSeparator();
        game.add(g5);
        mb.add(game);
        help = new JMenu("Help");
        h1 = new JMenuItem("Instruction..");
        h2 = new JMenuItem("About..");
        help.add(h1);
        help.add(h2);
        mb.add(help);
        cal = nmin;
        pp = new JPopupMenu();
        pm = new JMenuItem("Mark(?)");
        pp.add(pm);
        pm.addActionListener(new ButtonRightClickAction());
        p1 = new JPanel(new GridLayout(1, 5));
        p1.setOpaque(true);
        p1.setBorder(BorderFactory.createLineBorder(Color.white));
        l1 = new JLabel("" + cal, SwingConstants.CENTER);
        l1.setBorder(BorderFactory.createEtchedBorder());
        l1.setFont(font);
        l1.setForeground(Color.red);

        l2 = new JLabel("000", SwingConstants.CENTER);
        l2.setBorder(BorderFactory.createLineBorder(Color.black));
        l2.setFont(font);
        l2.setForeground(Color.red);
        p1.add(new JLabel(getScaledIcon(new ImageIcon("src/com/kanni/images/insect.png").getImage(), imageSize)));
        p1.add(l1);
        p1.add(new JLabel(getScaledIcon(new ImageIcon("src/com/kanni/images/findBug.png").getImage(), imageSize)));
        p1.add(new JLabel(getScaledIcon(new ImageIcon("src/com/kanni/images/clock.png").getImage(), imageSize)));
        p1.add(l2);

        startTimer.set(true);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(nrow, ncol));
        p2.setOpaque(true);
        p2.setBorder(BorderFactory.createLineBorder(Color.blue));

        for (int i = 0; i < (ncol * nrow); i++) {
            b[i] = new JButton();
            no[i] = new JLabel("0", SwingConstants.CENTER);
            no[i].setFont(font);
            p2.add(b[i]);
            b[i].addActionListener(new ButtonPlayAction());
            b[i].addMouseListener(new MouseActions());
        }
        y = 1;
        while (y <= nmin) {
            rno1 = (int) (Math.random() * (ncol * nrow));
            no[rno1].setText("*");
            no[rno1].setIcon(getScaledIcon(bugIcon.getImage(), 0.25));
            y++;
        }

        for (int j = 0; j < (ncol * nrow); j++) {
            if ((no[j].getText()).equals("0")) {
                rno = (int) (Math.random() * 39);
                String imagePath = "src/com/kanni/images/img-" + rno + ".png";
                icon = new ImageIcon(imagePath);
                no[j].setText("");
                no[j].setIcon(getScaledIcon(icon.getImage(), imageSize));
            }
        }

        p3 = new JPanel(new BorderLayout());
        p3.setOpaque(true);
        p3.setBorder(BorderFactory.createLineBorder(Color.black));
        p3.add("South", p1);
        p3.add("Center", p2);
        f.getContentPane().add(p3);


        custom = new JPanel(new GridLayout(4, 2, 5, 5));
        custom.setOpaque(true);
        custom.setBorder(BorderFactory.createLineBorder(Color.blue));
        cl1 = new JLabel("Height");
        cl1.setFont(fff);
        cl2 = new JLabel("Weight");
        cl2.setFont(fff);
        cl3 = new JLabel("Mines");
        cl3.setFont(fff);
        ct1 = new JTextField("" + nrow, SwingConstants.CENTER);
        ct2 = new JTextField("" + ncol, SwingConstants.CENTER);
        ct3 = new JTextField("" + nmin, SwingConstants.CENTER);
        cb1 = new JButton("Ok");
        cb2 = new JButton("Cancel");
        cb1.addActionListener(new CustomizeAction());
        cb2.addActionListener(new CustomizeAction());

        custom.add(cl1);
        custom.add(ct1);
        custom.add(cl2);
        custom.add(ct2);
        custom.add(cl3);
        custom.add(ct3);
        custom.add(cb1);
        custom.add(cb2);

        d2 = new JDialog(f, "Best Times", true);
        best = new JPanel(new GridLayout(4, 3, 5, 5));
        best.setOpaque(true);
        best.setBorder(BorderFactory.createLineBorder(Color.blue));
        bl1 = new JLabel("Serial NO:",SwingConstants.CENTER);
        bl2 = new JLabel("Name ",SwingConstants.CENTER);
        bl3 = new JLabel(" Seconds",SwingConstants.CENTER);

        Font font1 = bl1.getFont();
        Map attributes1 = font1.getAttributes();
        attributes1.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        bl1.setFont(font1.deriveFont(attributes1));

        Font font2 = bl2.getFont();
        Map attributes2 = font2.getAttributes();
        attributes2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        bl2.setFont(font2.deriveFont(attributes2));

        Font font3 = bl3.getFont();
        Map attributes3 = font3.getAttributes();
        attributes3.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        bl3.setFont(font3.deriveFont(attributes3));


        sl1 = new JLabel("1", SwingConstants.CENTER);
        sl2 = new JLabel("2", SwingConstants.CENTER);
        bl4 = new JLabel(name[0]);

        bl5 = new JLabel(name[1]);
        bl7 = new JLabel(sec[0] + " sec");
        bl8 = new JLabel(sec[1] + " sec");
        bb1 = new JButton("Reset");
        bb2 = new JButton("Ok");
        bb1.addActionListener(new CustomizeAction());
        bb2.addActionListener(new CustomizeAction());
        best.add(bl1);
        best.add(bl2);
        best.add(bl3);
        best.add(sl1);
        best.add(bl4);
        best.add(bl7);
        best.add(sl2);
        best.add(bl5);
        best.add(bl8);
        best.add(bb1);
        best.add(bb2);
        d2.getContentPane().add(best);
        d2.setBounds(300, 300, 300, 150);
        f.setBounds(300, 300, fwidth, fheight);//175,255
        f.setResizable(false);
        f.addWindowListener(new WindowAction());
        f.setVisible(true);

        g1.addActionListener(new ButtonAction());
        g2.addActionListener(new ButtonAction());
        g3.addActionListener(new ButtonAction());
        g4.addActionListener(new ButtonAction());
        g5.addActionListener(new ButtonAction());
        c2.addActionListener(new ButtonAction());
        h1.addActionListener(new ButtonAction());
        h2.addActionListener(new ButtonAction());

    }

    class WindowAction extends WindowAdapter {
        public void windowClosing(WindowEvent we) {
            int gg2 = JOptionPane.showConfirmDialog(f, "You want exit the game(Yes/No) ?", "Exit the Game", JOptionPane.YES_NO_OPTION);
            if (gg2 == JOptionPane.YES_OPTION) System.exit(0);
            else {
                f.dispose();
                new Minesweeper();
            }
        }
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == g1) {
                f.dispose();
                new Minesweeper();
            }

            if (ae.getSource() == c2) {
                f.dispose();
                new PuzzleNumber();
            }

            if (ae.getSource() == g3) {
                Color col = JColorChooser.showDialog(f, "Choose color", Color.red);
                for (int i = 0; i < (nrow * ncol); i++)
                    b[i].setBackground(col);

            }
            if (ae.getSource() == g2) {
                d1 = new JDialog(f, "Customize", true);
                d1.getContentPane().setLayout(new FlowLayout());
                d1.getContentPane().add(custom);
                d1.setBounds(650, 350, 190, 225);
                d1.setVisible(true);
            }
            if (ae.getSource() == h1) {
                help1 = new JDialog(f, "Instructions", true);
                help1.getContentPane().setLayout(new GridLayout(9, 1, 5, 5));
                help1.getContentPane().setBackground(Color.white);
                il1 = new JLabel("<html><body><h1><u>To Play Minesweeper</u></h1></body></html>");
                il2 = new JLabel("<html><body><h2><b>1.On the Menu ,Click New option</b></h2></body></html>");
                il3 = new JLabel("<html><body><h2><b>2.To play games</b></h2></body></html>");
                il4 = new JLabel("<html><body><h1><u>Notes:</u></h2></body></html>");
                il5 = new JLabel("<html><body><h3>1.You can uncover a square by clicking it.<br>If you uncover a mine, you lose the game.</body></html>");
                il6 = new JLabel("<html><body><h3>2.If a number appears on a square,<br>down side Label contains how many mines present in games.</body></html>");
                il7 = new JLabel("<html><body><h3>3.In Custom box used for Own create<br>row,columns and Mines.ex.min(9,9,10) & Max(20,20,79)</body></html>");
                il8 = new JLabel("<html><body><h3>4.In BestTime box contains Names is winning userName and Times</body></html>");
                il9 = new JLabel("<html><body><h3>5.In Color box used set Background color in button</body></html>");
                help1.getContentPane().add(il1);
                help1.getContentPane().add(il2);
                help1.getContentPane().add(il3);
                help1.getContentPane().add(il4);
                help1.getContentPane().add(il5);
                help1.getContentPane().add(il6);
                help1.getContentPane().add(il7);
                help1.getContentPane().add(il8);
                help1.getContentPane().add(il9);
                help1.setBounds(650, 350, 450, 450);
                help1.setVisible(true);
            }
            if (ae.getSource() == h2) {
                help2 = new JDialog(f, "About the Game..", true);
                help2.getContentPane().setLayout(new FlowLayout());
                help2.getContentPane().setBackground(Color.green);
                al1 = new JLabel("open source");
                al2 = new JLabel("Enjoy the games ");
                al3 = new JLabel(iconImg);
                help2.getContentPane().add(al3);
                help2.getContentPane().add(al1);
                help2.getContentPane().add(al2);
                help2.setBounds(650, 350, 200, 250);
                help2.setVisible(true);
            }

            if (ae.getSource() == g5) {
                int option = JOptionPane.showConfirmDialog(f, "You want Exit on the Game ?", "close the Game", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
            if (ae.getSource() == g4) {
                d2.setVisible(true);
            }
        }
    }

    public class MouseActions extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            for (int j = 0; j < (ncol * nrow); j++) {
                if (me.getSource() == b[j]) {
                    if (me.isPopupTrigger()) {
                        pp.show(me.getComponent(), me.getX(), me.getY());
                    }
                }
            }
        }


        public void mouseReleased(MouseEvent me) {
            for (int j = 0; j < (ncol * nrow); j++) {
                if (me.getSource() == b[j]) {
                    if (me.isPopupTrigger()) {
                        JButton item = (JButton) me.getSource();
                        if (null != item && item.getIcon() != null) {
                            ImageIcon buttonImg = (ImageIcon) item.getIcon();

                            String imgDesc="Image-" + j;
                            if (buttonImg.getDescription().equalsIgnoreCase(imgDesc)) {
                                pm.setText("UnMark(?)");
                            }
                        }else{
                            pm.setText("Mark(?)");
                        }
                        pp.show(me.getComponent(), me.getX(), me.getY());
                        flag = j;
                    }
                }
            }
        }
    }

    class CustomizeAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == bb2) {
                d2.dispose();
            }
            if (ae.getSource() == bb1) {
                name[0] = "KanniSelvan.K";
                name[1] = "KanniSelvan.K";
                sec[0] = "999";
                sec[1] = "999";
                bl4.setText(name[0]);
                bl7.setText(sec[0] + " sec");
                bl5.setText(name[1]);
                bl8.setText(sec[1] + " sec");
            }
            if (ae.getSource() == cb1) {
                height = Integer.parseInt(ct1.getText().trim());
                width = Integer.parseInt(ct2.getText().trim());
                mine = Integer.parseInt(ct3.getText().trim());

                if (height < 9) {
                    ncol = 9;
                } else if (height > 20) {
                    ncol = 20;
                } else {
                    ncol = height;
                }

                if (width < 9) {
                    nrow = 9;
                } else if (width > 20) {
                    nrow = 20;
                } else {
                    nrow = width;
                }

                if (mine < 10) {
                    nmin = 10;
                } else if (mine > 79) {
                    nmin = 79;
                } else {
                    nmin = mine;
                }

                fwidth = 350;
                fheight = 450;

                fwidth += ((nrow - 9) * 30);
                fheight += ((ncol - 9) * 30);
                d1.dispose();
                f.dispose();
                new Minesweeper();
            }
            if (ae.getSource() == cb2) {
                d1.dispose();
            }

        }
    }

    public class ButtonPlayAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JButton jButton = (JButton) ae.getSource();
            boolean skip = false;

            if (startTimer.get()) {
                Timer tt = new Timer();
                new Thread(tt).start();
                startTimer.set(false);
            }

            if (null != jButton && null != jButton.getIcon()) {
                ImageIcon buttonImg = (ImageIcon) jButton.getIcon();
                if (buttonImg.getImage() == puzzledIcon.getImage()) {
                    skip = true;
                }
            }

            if (!skip) {
                for (int j = 0; j < (ncol * nrow); j++) {

                    if (ae.getSource() == b[j]) {
                        count++;
                        //   System.out.println("\7");
                        bx = b[j].getX();
                        by = b[j].getY();
                        bw = b[j].getWidth();
                        bh = b[j].getHeight();
                        b[j].removeNotify();
                        no[j].setBounds(bx, by, bw, bh);
                        p2.remove(b[j]);
                        p2.revalidate();
                        p2.repaint();
                        p2.add(no[j], j);
                        p2.revalidate();
                        p2.repaint();

                        if (count == ((nrow * ncol) - nmin)) {
                            state.set(false);
                          /*  for (i = 1; i < 15; i++)
                                System.out.println("\7");*/
                            JOptionPane.showMessageDialog(f, "", "Congratulation Winner!!!", 1, successImg);
                            playerName = JOptionPane.showInputDialog("Enter your Name:");
                            bse = l2.getText();

                            if (Integer.parseInt(bse) < Integer.parseInt(sec[0])) {
                                String kap = name[0];
                                String kop = sec[0];
                                name[0] = playerName;
                                sec[0] = bse;
                                String kap1 = name[1];
                                String kop1 = sec[1];
                                name[1] = kap;
                                sec[1] = kop;
                            } else if (Integer.parseInt(bse) < Integer.parseInt(sec[1])) {
                                String kap2 = name[1];
                                String kop2 = sec[1];
                                name[1] = playerName;
                                sec[1] = bse;
                            }

                            bl4.setText(name[0]);
                            bl7.setText(sec[0] + " sec");
                            bl5.setText(name[1]);
                            bl8.setText(sec[1] + " sec");

                            d2.setVisible(true);

                            int gg1 = JOptionPane.showConfirmDialog(f, "You want play the game again?", "choose the game", JOptionPane.YES_NO_OPTION);
                            if (gg1 == JOptionPane.YES_OPTION) {
                                f.dispose();
                                new Minesweeper();
                            } else System.exit(0);
                        }

                        if (no[j].getText().equals("*")) {
                            state.set(false);
                            System.out.println("\7");
                            try {
                                for (int k = 0; k < (ncol * nrow); k++) {
                                    if ((no[k].getText()).equals("*")) {

                                        if ((b[k].getText()).equals("?")) {
                                            b[k].setIcon(getScaledIcon(correctIcon.getImage(), imageSize));
                                        } else {
                                            b[k].setIcon(getScaledIcon(bugIcon.getImage(), 0.25));
                                        }
                                        no[k].setText("");
                                        b[k].setText("");
                                    }
                                }
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                                System.out.println("Error:" + ie.getMessage());
                            }

                            JOptionPane.showMessageDialog(null, "Game Over!!");

                            int gg = JOptionPane.showConfirmDialog(null, "You want play the game again?", "choose the game", JOptionPane.YES_NO_OPTION);

                            if (gg == JOptionPane.YES_OPTION) {
                                f.dispose();
                                new Minesweeper();
                            } else System.exit(0);
                        }
                    }
                }
            }
        }
    }


    public class ButtonRightClickAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == pm && cal > 0) {
                JMenuItem item = (JMenuItem) ae.getSource();
                if (item.getText().equalsIgnoreCase("Mark(?)")) {
                    ImageIcon icon = getScaledIcon(puzzledIcon.getImage(), imageSize);
                    icon.setDescription("Image-" + flag);
                    b[flag].setIcon(icon);
                    cal--;
                    l1.setText(" " + cal);
                } else {
                    b[flag].setIcon(null);
                    cal++;
                    l1.setText(" " + cal);
                }
            }
        }
    }


    public BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            File FileToRead = new File(path);
            image = ImageIO.read(FileToRead);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return image;
    }

    private Image getScaledImage(BufferedImage image, double scale) {
        int w = (int) (image.getWidth() * scale);
        int h = (int) (image.getHeight() * scale);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        g2.drawRenderedImage(image, at);
        g2.dispose();
        return bi;
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


    public class Timer implements Runnable {
        int hh = 0, mm1 = 0, ss = 0;

        public void run() {
            while (state.get()) {
                String hms = new String(String.valueOf(hh) + "" + String.valueOf(mm1) + "" + String.valueOf(ss));

                l2.setText(hms);

                ss++;
                if (ss == 9) {
                    mm1++;
                    ss = 0;
                }
                if (mm1 == 9) {
                    hh++;
                    mm1 = 0;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
        }
    }
}
