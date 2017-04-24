package code.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A simple GUI, similar to the one in assignments 1 and 2, that you can base
 * your renderer off. It is abstract, and there are three methods you need to
 * implement: onLoad, onKeyPress, and render. There is a method to get the
 * ambient light level set by the sliders. You are free to use this class as-is,
 * modify it, or ignore it completely.
 * 
 * @author tony
 */
public abstract class GUI {

	/**
	 * Is called when the user has successfully selected a model file to load,
	 * and is passed a File representing that file.
	 */
	protected abstract void onLoad(File file);

	/**
	 * Is called every time the user presses a key. This can be used for moving
	 * the camera around. It is passed a KeyEvent object, whose methods of
	 * interest are getKeyChar() and getKeyCode().
	 */
	protected abstract void onKeyPress(KeyEvent ev);
	/*
	 * Is called when the mouse wheel is scrolled in or out
	 * @param e
	 * */
	protected abstract void onScroll(MouseWheelEvent e);
	/*
	 * Is called when the mouse is pressed. This can be used for memorising the location
	 * where the mouse is pressed, and cooperate with onPressed() method to move or rotate the camera
	 * @param e
	 * **/
	protected abstract void onPressed(MouseEvent e);
	/*
	 * Is called when the mouse is released. This can be used for moving or rotating the camera around
	 * @param e
	 * **/
	protected abstract void onReleased(MouseEvent e);
	/*
	 * Is called when the mode switch button is clicked. It is intended to switch between dragging
	 * **/
	protected abstract void switchMoveRotation();
	/**
	 * Is called the default button is pressed. This button is intended to set the viewing as default scale
	 * and direction.  
	 * */
	protected abstract void onDefalut();
	
	public Dimension getDrawingDimentsion() {
		return DRAWING_SIZE;
	}
	
	/**
	 * Is called every time the drawing canvas is drawn. This should return a
	 * BufferedImage that is your render of the scene.
	 */
	protected abstract BufferedImage render();

	/**
	 * Forces a redraw of the drawing canvas. This is called for you, so you
	 * don't need to call this unless you modify this GUI.
	 */
	public void redraw() {
		frame.repaint();
	}

	/**
	 * Returns the Color values of the three sliders used for setting the ambient
	 * light of the scene. The returned array in the form [R, G, B] where each
	 * value is between 0 and 255.
	 */
	public Color getAmbientLight() {
		return new Color(ambientRed.getValue(),ambientGreen.getValue(),ambientBlue.getValue());
	}
	/**
	 * Returns the Color values of the three sliders used for setting the direct
	 * light of the scene. The returned array in the form [R, G, B] where each
	 * value is between 0 and 255.
	 */
	public Color getDirectLight() {
		return new Color(directRed.getValue(),directGreen.getValue(),directBlue.getValue());
	}

	public static final int CANVAS_WIDTH = 600;
	public static final int CANVAS_HEIGHT = 600;

	// --------------------------------------------------------------------
	// Everything below here is Swing-related and, while it's worth
	// understanding, you don't need to look any further to finish the
	// assignment up to and including completion.
	// --------------------------------------------------------------------

	private JFrame frame;
	private final JSlider ambientRed = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
	private final JSlider ambientGreen = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
	private final JSlider ambientBlue = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
	
	private final JSlider directRed = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
	private final JSlider directGreen = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
	private final JSlider directBlue = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);

	private static final Dimension DRAWING_SIZE = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
	private static final Dimension CONTROLS_SIZE = new Dimension(150, 600);

	private static final Font FONT = new Font("Courier", Font.BOLD, 36);

	public GUI() {
		initialise();
	}

	@SuppressWarnings("serial")
	private void initialise() {
		// make the frame
		frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
		frame.setSize(new Dimension(DRAWING_SIZE.width + CONTROLS_SIZE.width, DRAWING_SIZE.height));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set up the drawing canvas, hook it into the render() method, and give
		// it a nice default if render() returns null.
		JComponent drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				BufferedImage image = render();
				if (image == null) {
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, DRAWING_SIZE.width, DRAWING_SIZE.height);
					g.setColor(Color.BLACK);
					g.setFont(FONT);
					g.drawString("IMAGE IS NULL", 50, DRAWING_SIZE.height - 50);
				} else {
					g.drawImage(image, 0, 0, null);
				}
			}
		};
		//After initialise
		/***********Add mouse control feature below    @author minpingyang ****************/
		//dragging to move
		//TODO 1
		drawing.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				onPressed(e);
			}
			public void mouseReleased(MouseEvent e){
				onReleased(e);
				redraw();
			}
			
		});
		drawing.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				onScroll(e);
				redraw();
			}
		});
		/******************done******/
		// fix its size
		drawing.setPreferredSize(DRAWING_SIZE);
		drawing.setMinimumSize(DRAWING_SIZE);
		drawing.setMaximumSize(DRAWING_SIZE);
		drawing.setVisible(true);

		// set up the load button
		final JFileChooser fileChooser = new JFileChooser();
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// set up the file chooser
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setDialogTitle("Select input file");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				// run the file chooser and check the user didn't hit cancel
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					onLoad(file);
					redraw();
				}
			}
		});
		
		/***********Implement to switch Move and Rotate    @author minpingyang ****************/
		JButton moveRoateButton = new JButton("Move");
		moveRoateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(moveRoateButton.getText().equals("Move")){
					moveRoateButton.setText("Rotate");
				}else {
					moveRoateButton.setText("Move");
				}
				switchMoveRotation();
			}
		});
		/*******Implement default button--- set back to default position and default scale******/
		JButton defaultButton = new JButton("Default");
		defaultButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//set value back to initial value
				ambientRed.setValue(128);
				ambientGreen.setValue(128);
				ambientBlue.setValue(128);
				directRed.setValue(128);
				directGreen.setValue(128);
				directBlue.setValue(128);
				onDefalut();
				redraw();
			}
		});
		
		
		// we have to put the button in its own panel to ensure it fills the
		// full width of the control bar.
		JPanel loadpanel = new JPanel(new BorderLayout());
		loadpanel.setMaximumSize(new Dimension(1000, 25));
		loadpanel.setPreferredSize(new Dimension(1000, 25));
		loadpanel.add(load, BorderLayout.CENTER);
		loadpanel.add(moveRoateButton, BorderLayout.CENTER);
		loadpanel.add(defaultButton, BorderLayout.CENTER);
		
		// set up the sliders for ambient light. they were instantiated in
		// the field definition, as for some reason they need to be final to
		// pull the set background trick.
		ambientRed.setBackground(new Color(230, 50, 50));
		ambientGreen.setBackground(new Color(50, 230, 50));
		ambientBlue.setBackground(new Color(50, 50, 230));

		JPanel sliderpartyAmbient = new JPanel();
		sliderpartyAmbient.setLayout(new BoxLayout(sliderpartyAmbient, BoxLayout.PAGE_AXIS));
		sliderpartyAmbient.setBorder(BorderFactory.createTitledBorder("Ambient Light"));
		
		
		/***add a another slider for direct light*/
		directRed.setBackground(new Color(230, 50, 50));
		directGreen.setBackground(new Color(50, 230, 50));
		directBlue.setBackground(new Color(50, 50, 230));
		
		JPanel sliderpartyDirect = new JPanel();
		sliderpartyDirect.setLayout(new BoxLayout(sliderpartyDirect, BoxLayout.PAGE_AXIS));
		sliderpartyDirect.setBorder(BorderFactory.createTitledBorder("Direct Light"));
		
		
		/****Add a feature for showing instant ambient color by dragging slider *********/
		JComponent ambientReview = new JComponent() {
			@Override
			protected void paintComponent(Graphics graphics){
				graphics.setColor(new Color(ambientRed.getValue(), ambientGreen.getValue(), ambientBlue.getValue()));
				graphics.fillRect(0, 0, CONTROLS_SIZE.width, 40);
			
			}
		};
		
		Dimension reviewDimension = new Dimension(160, 20);
		ambientReview.setPreferredSize(reviewDimension);
		ambientReview.setMinimumSize(reviewDimension);
		ambientReview.setMaximumSize(reviewDimension);
		ambientReview.setVisible(true);
		ChangeListener ambientListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				ambientReview.repaint();
				redraw();
			}
		};
		ambientRed.addChangeListener(ambientListener);
		ambientGreen.addChangeListener(ambientListener);
		ambientBlue.addChangeListener(ambientListener);
		
	
		sliderpartyAmbient.add(ambientRed);
		sliderpartyAmbient.add(ambientGreen);
		sliderpartyAmbient.add(ambientBlue);
		sliderpartyAmbient.add(ambientReview);
		/**************done***************************************/
		//Similarly, add a instant review for direct light by dragging sliders
		JComponent directReview = new JComponent() {
			@Override
			protected void paintComponent(Graphics graphics){
				graphics.setColor(new Color(directRed.getValue(), directGreen.getValue(), directBlue.getValue()));
				graphics.fillRect(0, 0, CONTROLS_SIZE.width, 40);
			
			}
		};
		
		directReview.setPreferredSize(reviewDimension);
		directReview.setMinimumSize(reviewDimension);
		directReview.setMaximumSize(reviewDimension);
		directReview.setVisible(true);
		ChangeListener directListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				directReview.repaint();
				redraw();
			}
		};
		directRed.addChangeListener(directListener);
		directGreen.addChangeListener(directListener);
		directBlue.addChangeListener(directListener);
		
	
		sliderpartyDirect.add(directRed);
		sliderpartyDirect.add(directGreen);
		sliderpartyDirect.add(directBlue);
		sliderpartyDirect.add(directReview);
		/************done ********/
		
		
		
		
		
		
		

		// this is not a best-practices way of doing key listening; instead you
		// should use either a KeyListener or an InputMap/ActionMap combo. but
		// this method neatly avoids any focus issues (KeyListener) and requires
		// less effort on your part (ActionMap).
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent ev) {
				if (ev.getID() == KeyEvent.KEY_PRESSED) {
					onKeyPress(ev);
					redraw();
				}
				return true;
			}
		});

		// make the panel on the right, fix its size, give it a border!
		JPanel controls = new JPanel();
		controls.setPreferredSize(CONTROLS_SIZE);
		controls.setMinimumSize(CONTROLS_SIZE);
		controls.setMaximumSize(CONTROLS_SIZE);
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controls.setBorder(edge);

		controls.add(loadpanel);
		controls.add(Box.createRigidArea(new Dimension(0, 15)));
		controls.add(sliderpartyAmbient);
		controls.add(Box.createRigidArea(new Dimension(0, 15)));
		controls.add(sliderpartyDirect);
		// if i were going to add more GUI components, i'd do it here.
		controls.add(Box.createVerticalGlue());

		// put it all together.
		frame.add(drawing);
		frame.add(controls);

		frame.pack();
		frame.setVisible(true);
	}
}

// code for comp261 assignments
