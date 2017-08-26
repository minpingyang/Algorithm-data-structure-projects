package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Created by minpingyang on 26/08/17.
 */
public class Animation {



    private LeftCreationView pane1;
    private DegPanLeView pane2;
    private  MouseAdapter ma;
    private RightCreationView pane3;
    private DegPanRiView pane4;

    public LeftCreationView getPane1(){
        return pane1;
    }
    public DegPanLeView getPane2(){
        return pane2;
    }

    public Animation(View view) {
        this.pane1 =view.getLeftCreationView();
        this.pane2= view.getDegPanLeView();
        pane3 = view.getRightCreationView();
        pane4=view.getDegPanRiView();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }

                    pane1.setAlpha(1f);

                    pane2.setAlpha(0.3f);

                    pane3.setAlpha(1f);
                    pane4.setAlpha(0.3f);

            }
        });
    }

    public RightCreationView getPane3() {
        return pane3;
    }

    public DegPanRiView getPane4() {
        return pane4;
    }


    public static class InvalidStateException extends Exception {

        public InvalidStateException(String message) {
            super(message);
        }

        public InvalidStateException(String message, Throwable cause) {
            super(message, cause);
        }

    }


}
