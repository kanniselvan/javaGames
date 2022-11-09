package com.kanni;

import java.awt.event.*;
import javax.swing.*;

public class Progressbar extends JPanel implements ActionListener {
    static ProgressMonitor pm;
    static int counter = 0;

    public Progressbar() {
        pm = new ProgressMonitor(this, "Loading Games", "Initiating..", 0, 100);
        Timer t = new Timer(500, this);
        t.start();
    }

    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Update());
    }

    class Update implements Runnable {
        public void run() {
            if (pm.isCanceled()) {
                pm.close();
                System.exit(1);
            }
            pm.setProgress(counter);
            pm.setNote("GameLoading is" + counter + "% complete");
            counter += 2;
        }
    }
}
