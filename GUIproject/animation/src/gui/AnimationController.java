package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by minpingyang on 26/08/17.
 */
public class AnimationController {

    private java.util.List<AlphaRange> animationRanges;
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

    public void start() throws Animation.InvalidStateException {
        if (timer == null || !timer.isRunning()) {

            timer = new Timer(30, new ActionListener() {
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

//                    System.out.println(NumberFormat.getPercentInstance().format(progress));

                    for (AlphaRange range : animationRanges) {
                        range.update(progress);
                    }
                }
            });
            timer.start();

        } else {
            throw new Animation.InvalidStateException("Animation is running");
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

        private LeftCreationView alphaPaneCreation;
        private DegPanLeView alphadegView;

        private RightCreationView alphaRightCrea;
        private DegPanRiView alphaDegreRig;
        public AlphaRange(LeftCreationView leftCreationView, DegPanLeView degPanLeView,boolean fadeIn) {
            if(leftCreationView!=null){
                this.from = leftCreationView.getAlpha();
                this.to = fadeIn ? 1f : 0f;
                this.alphaPaneCreation = leftCreationView;
            }else if (degPanLeView!=null){
                this.from = degPanLeView.getAlpha();
                this.to = fadeIn ? 1f : 0f;
                this.alphadegView = degPanLeView;
            }

        }
        public AlphaRange(RightCreationView rightCreationView, DegPanRiView degPanRiView,boolean fadeIn) {
            if(rightCreationView!=null){
                this.from = rightCreationView.getAlpha();
                this.to = fadeIn ? 1f : 0f;
                this.alphaRightCrea = rightCreationView;
            }else if (degPanRiView!=null){
                this.from = degPanRiView.getAlpha();
                this.to = fadeIn ? 1f : 0f;
                this.alphaDegreRig = degPanRiView;
            }

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
            value = 1.4f*(distance * progress);
            value += from;

            return value;

        }

        public void update(float progress) {
            float alpha = getValueBasedOnProgress(progress);
            if(alphaRightCrea!=null&&alphaDegreRig==null){
                alphaRightCrea.setAlpha(alpha);
            }else if(alphaRightCrea==null&&alphaDegreRig!=null){
                alphaDegreRig.setAlpha(alpha);
            }


            if(alphaPaneCreation!=null&&alphadegView==null){
                alphaPaneCreation.setAlpha(alpha);
            }else if(alphaPaneCreation==null&&alphadegView!=null){
                alphadegView.setAlpha(alpha);
            }

        }

    }

}