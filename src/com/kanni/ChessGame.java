package com.kanni;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChessGame {

    JFrame f = new JFrame("Chess Game");
    ImageIcon iconImg = new ImageIcon("src/com/kanni/chessimages/chess.png");
    int width = 700, height = 600;
    JButton[][] BOARD = new JButton[8][8];
    JPanel mainPanel, customPanel, leftPanel, rightPanel;
    boolean flag = true;

    ImageIcon White_Rook = new ImageIcon("src/com/kanni/chessimages/White_Rook.png", "White_Rook");
    ImageIcon White_Bishop = new ImageIcon("src/com/kanni/chessimages/White_Bishop.png", "White_Bishop");
    ImageIcon White_King = new ImageIcon("src/com/kanni/chessimages/White_King.png", "White_King");
    ImageIcon White_Knight = new ImageIcon("src/com/kanni/chessimages/White_Knight.png", "White_Knight");
    ImageIcon White_Pawn = new ImageIcon("src/com/kanni/chessimages/White_Pawn.png", "White_Pawn");
    ImageIcon White_Queen = new ImageIcon("src/com/kanni/chessimages/White_Queen.png", "White_Queen");

    ImageIcon Black_Rook = new ImageIcon("src/com/kanni/chessimages/Black_Rook.png", "Black_Rook");
    ImageIcon Black_Bishop = new ImageIcon("src/com/kanni/chessimages/Black_Bishop.png", "Black_Bishop");
    ImageIcon Black_King = new ImageIcon("src/com/kanni/chessimages/Black_King.png", "Black_King");
    ImageIcon Black_Knight = new ImageIcon("src/com/kanni/chessimages/Black_Knight.png", "Black_Knight");
    ImageIcon Black_Pawn = new ImageIcon("src/com/kanni/chessimages/Black_Pawn.png", "Black_Pawn");
    ImageIcon Black_Queen = new ImageIcon("src/com/kanni/chessimages/Black_Queen.png", "Black_Queen");

    ImageIcon[][] whiteImageIcons = new ImageIcon[2][8];

    ImageIcon[][] blockImageIcons = new ImageIcon[2][8];

    JLabel right_White_Rook = new JLabel("", getScaledIcon(White_Rook.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel right_White_Bishop = new JLabel("", getScaledIcon(White_Bishop.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel right_White_King = new JLabel("", getScaledIcon(White_King.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel right_White_Knight = new JLabel("", getScaledIcon(White_Knight.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel right_White_Queen = new JLabel("", getScaledIcon(White_Queen.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel right_White_Pawn = new JLabel("", getScaledIcon(White_Pawn.getImage(), 0.75), SwingConstants.TRAILING);

    JLabel left_Black_Rook = new JLabel("", getScaledIcon(Black_Rook.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel left_Black_Bishop = new JLabel("", getScaledIcon(Black_Bishop.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel left_Black_King = new JLabel("", getScaledIcon(Black_King.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel left_Black_Knight = new JLabel("", getScaledIcon(Black_Knight.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel left_Black_Queen = new JLabel("", getScaledIcon(Black_Queen.getImage(), 0.75), SwingConstants.TRAILING);
    JLabel left_Black_Pawn = new JLabel("", getScaledIcon(Black_Pawn.getImage(), 0.75), SwingConstants.TRAILING);


    ImageIcon presentImage;
    String lastMoveColour;

    int count = 0, presentX = 0, presentY = 0;
    Font font = new Font("Serief", Font.BOLD, 25);

    AtomicBoolean moveFlag = new AtomicBoolean(false);

    public ChessGame() {
        f.setIconImage(iconImg.getImage());
        f.getContentPane().setLayout(new GridLayout(1, 1));

        customPanel = new JPanel(new GridLayout(8, 8, 1, 2));
        customPanel.setOpaque(true);
        customPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        leftPanel = new JPanel(new GridLayout(8, 1, 1, 2));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));


        rightPanel = new JPanel(new GridLayout(8, 1, 1, 2));
        rightPanel.setOpaque(true);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));


        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add("East", leftPanel);
        mainPanel.add("Center", customPanel);
        mainPanel.add("West", rightPanel);

        defaultChessDashboard();

        for (int i = 0; i < 8; i++) {
            if (i % 2 != 0) {
                flag = false;
            } else {
                flag = true;
            }

            for (int j = 0; j < 8; j++) {
                BOARD[i][j] = new JButton();
                ImageIcon icon = null;
                if (i < 2) {
                    icon = getScaledIcon(blockImageIcons[i][j].getImage(), 1.10);
                    icon.setDescription(blockImageIcons[i][j].getDescription());
                    BOARD[i][j].setIcon(icon);
                }
                if (i == 6) {
                    icon = getScaledIcon(whiteImageIcons[1][j].getImage(), 1.10);
                    icon.setDescription(whiteImageIcons[1][j].getDescription());
                    BOARD[i][j].setIcon(icon);
                }
                if (i == 7) {
                    icon = getScaledIcon(whiteImageIcons[0][j].getImage(), 1.10);
                    icon.setDescription(whiteImageIcons[0][j].getDescription());
                    BOARD[i][j].setIcon(icon);
                }

                if (flag) {
                    BOARD[i][j].setBackground(Color.DARK_GRAY);
                    flag = false;
                } else {
                    BOARD[i][j].setBackground(Color.white);
                    flag = true;
                }
                BOARD[i][j].addMouseListener(new MouseActionListener());
                customPanel.add(BOARD[i][j]);
            }
        }


        f.getContentPane().add(mainPanel);
        f.setBounds(300, 200, width, height);
        f.setResizable(false);
        f.addWindowListener(new WindowAction());
        f.setVisible(true);

    }

    public String getMoveCoin(String imgDesc) {
        if (null != imgDesc && imgDesc.length() > 0) {
            String[] split = imgDesc.split("_");
            if (null != split && split.length == 2) {
                return split[0];
            }
        }
        return null;
    }
    public String getImageDesc(String imgDesc) {
        if (null != imgDesc && imgDesc.length() > 0) {
            String[] split = imgDesc.split("_");
            if (null != split && split.length == 2) {
                return split[1];
            }
        }
        return null;
    }

    public String getColour(String colour){
        if(null!=colour && colour.length()>0){
            return colour.equalsIgnoreCase("White")? "Black":"White";
        }
        return null;
    }

    class MouseActionListener extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            JButton jButton = (JButton) me.getSource();

            if (null != jButton) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (me.getSource() == BOARD[i][j]) {
                            if (null != jButton.getIcon()) {
                                count++;
                                if (count == 1) {
                                    ImageIcon icon = (ImageIcon) jButton.getIcon();
                                    String move = getMoveCoin(icon.getDescription());
                                    if (!move.equalsIgnoreCase(lastMoveColour)) {
                                        presentX = i;
                                        presentY = j;
                                        presentImage = (ImageIcon) jButton.getIcon();
                                        BOARD[i][j].setIcon(null);
                                        BOARD[i][j].setBorder(new LineBorder(Color.GREEN));
                                    } else {
                                        count=0;
                                        JOptionPane.showMessageDialog(f, "Last move is  " + lastMoveColour + " . Please move  "+getColour(lastMoveColour));
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(f, "Paste the icon!!");
                                }
                            } else if (presentImage != null) {
                                if (isValidMove(i, j)) {
                                    BOARD[i][j].setIcon(presentImage);
                                    lastMoveColour = getMoveCoin(presentImage.getDescription());
                                    BOARD[presentX][presentY].setBorder(null);
                                    count = 0;
                                    presentImage = null;
                                    presentX = 0;
                                    presentY = 0;
                                } else {
                                    JOptionPane.showMessageDialog(f, "Invalid Move!!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(f, "select the coin!!");
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isValidMove(int i, int j) {
        boolean flag = false;
        if (presentImage != null) {
            String desc = getImageDesc(presentImage.getDescription());
            if (null != desc && desc.equalsIgnoreCase("Pawn")) {
                int tempX = presentX, tempY = presentY;
                int xx = tempY + 1;
                int xxx = tempY - 1;
                if (i == tempX && (j == xx || j == xxx)) {
                    flag = true;
                }
                int yy = tempX + 1;
                int yyy = tempX - 1;
                if (j == tempY && (i == yy || i == yyy)) {
                    flag = true;
                }
            }
        }
        return flag;
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

    private void defaultChessDashboard() {

        whiteImageIcons[0][0] = White_Rook;
        whiteImageIcons[0][1] = White_Knight;
        whiteImageIcons[0][2] = White_Bishop;
        whiteImageIcons[0][3] = White_Queen;
        whiteImageIcons[0][4] = White_King;
        whiteImageIcons[0][5] = White_Bishop;
        whiteImageIcons[0][6] = White_Knight;
        whiteImageIcons[0][7] = White_Rook;

        for (int i = 0; i < 8; i++) {
            whiteImageIcons[1][i] = White_Pawn;
        }

        blockImageIcons[0][0] = Black_Rook;
        blockImageIcons[0][1] = Black_Knight;
        blockImageIcons[0][2] = Black_Bishop;
        blockImageIcons[0][3] = Black_Queen;
        blockImageIcons[0][4] = Black_King;
        blockImageIcons[0][5] = Black_Bishop;
        blockImageIcons[0][6] = Black_Knight;
        blockImageIcons[0][7] = Black_Rook;

        for (int i = 0; i < 8; i++) {
            blockImageIcons[1][i] = Black_Pawn;
        }

        left_Black_Bishop.setFont(font);
        left_Black_Knight.setFont(font);
        left_Black_Rook.setFont(font);
        left_Black_Queen.setFont(font);
        left_Black_King.setFont(font);
        left_Black_Pawn.setFont(font);

        leftPanel.add(left_Black_Bishop);
        leftPanel.add(left_Black_Rook);
        leftPanel.add(left_Black_King);
        leftPanel.add(left_Black_Knight);
        leftPanel.add(left_Black_Queen);
        leftPanel.add(left_Black_Pawn);


        right_White_Bishop.setFont(font);
        right_White_King.setFont(font);
        right_White_Knight.setFont(font);
        right_White_Queen.setFont(font);
        right_White_Rook.setFont(font);
        right_White_Pawn.setFont(font);

        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(right_White_Bishop);
        rightPanel.add(right_White_King);
        rightPanel.add(right_White_Knight);
        rightPanel.add(right_White_Queen);
        rightPanel.add(right_White_Rook);
        rightPanel.add(right_White_Pawn);


    }

    class WindowAction extends WindowAdapter {
        public void windowClosing(WindowEvent we) {
            String[] select = {"Minesweeper","Puzzle","New","Exit"};
            int option = JOptionPane.showOptionDialog(f, "You Choose One Games?", "Choose the Games", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, select, "New");
            if (option == 0) {
                f.dispose();
                new Minesweeper();
            } else if (option == 1) {
                f.dispose();
                new PuzzleNumber();
            }else if (option == 2) {
                f.dispose();
                new ChessGame();
            }else{
                System.exit(0);
            }

        }
    }

    public static void main(String[] args) {
        new ChessGame();
    }
}
