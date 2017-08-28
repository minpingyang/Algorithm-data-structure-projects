package Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FadeTest {

    public static void main(String[] args) {
        new FadeTest();
    }

    public FadeTest() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }

                    BufferedImage img1 = ImageIO.read(new File("/Users/minpingyang/Desktop/Pig.png"));
                    BufferedImage img2 = ImageIO.read(new File("/Users/minpingyang/Desktop/Wheat.png"));

                    AlphaPane pane1 = new AlphaPane();
                    pane1.add(new JLabel(new ImageIcon(img1)));
                    pane1.setAlpha(1f);

                    AlphaPane pane2 = new AlphaPane();
                    pane2.add(new JLabel(new ImageIcon(img2)));
                    pane2.setAlpha(0f);

                    JFrame frame = new JFrame("Testing");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    gbc.weightx = 1;
                    gbc.weighty = 1;
                    gbc.fill = GridBagConstraints.BOTH;
                    frame.add(pane1, gbc);
                    frame.add(pane2, gbc);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    MouseAdapter ma = new MouseAdapter() {
                        private AnimationController controller;

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                if (controller != null) {
                                    controller.stop();
                                }
                                controller = new AnimationController(4000);

                                boolean fadeIn = pane1.getAlpha() < pane2.getAlpha();

                                controller.add(controller.new AlphaRange(pane1, fadeIn));
                                controller.add(controller.new AlphaRange(pane2, !fadeIn));

                                controller.start();
                            } catch (InvalidStateException ex) {
                                ex.printStackTrace();
                            }
                        }

                    };
                    pane1.addMouseListener(ma);
                    pane2.addMouseListener(ma);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public class AnimationController {

        private List<AlphaRange> animationRanges;
        private Timer timer;
        private Long startTime;
        private long runTime;

        public AnimationController(int runTime) {
            this.runTime = runTime;
            animationRanges = new ArrayList<>(25);
        }

        public void add(AlphaRange range) {
            animationRanges.add(range);
        }

        public void start() throws InvalidStateException {
            if (timer == null || !timer.isRunning()) {

                timer = new Timer(40, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (startTime == null) {
                            startTime = System.currentTimeMillis();
                        }
                        long duration = System.currentTimeMillis() - startTime;
                        float progress = (float) duration / (float) runTime;
                        if (progress > 1f) {
                            progress = 1f;
                            stop();
                        }

                        System.out.println(NumberFormat.getPercentInstance().format(progress));

                        for (AlphaRange range : animationRanges) {
                            range.update(progress);
                        }
                    }
                });
                timer.start();

            } else {
                throw new InvalidStateException("Animation is running");
            }
        }

        public void stop() {
            if (timer != null) {
                timer.stop();
            }
        }

        public class AlphaRange {

            private float from;
            private float to;

            private AlphaPane alphaPane;

            public AlphaRange(AlphaPane alphaPane, boolean fadeIn) {
                this.from = alphaPane.getAlpha();
                this.to = fadeIn ? 1f : 0f;
                this.alphaPane = alphaPane;
            }

            public float getFrom() {
                return from;
            }

            public float getTo() {
                return to;
            }

            public float getValueBasedOnProgress(float progress) {

                float value = 0;
                float distance = to - from;
                value = (distance * progress);
                value += from;

                return value;

            }

            public void update(float progress) {
                float alpha = getValueBasedOnProgress(progress);
                alphaPane.setAlpha(alpha);
            }

        }

    }

    public class InvalidStateException extends Exception {

        public InvalidStateException(String message) {
            super(message);
        }

        public InvalidStateException(String message, Throwable cause) {
            super(message, cause);
        }

    }

    public class AlphaPane extends JPanel {

        private float alpha;

        public AlphaPane() {
            setOpaque(false);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
            super.paint(g2d);
            g2d.dispose();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Fake the background
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        public void setAlpha(float value) {
            if (alpha != value) {
                this.alpha = Math.min(1f, Math.max(0, value));
                repaint();
            }
        }

        public float getAlpha() {
            return alpha;
        }

    }

}
