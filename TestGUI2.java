
/**
 * 
 * 
 * 
 * 
 * 
 * consulted code for instruction:
 * 
 * http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1755-how-to-create-menu-bar.html
 * http://www.cs101.org/courses/fall05/resources/swinglayout/
 * https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/using.html#set
 */





import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;

//import java.awt.event.*;

public class TestGUI2 extends JFrame implements ActionListener {
	
	private Model model;
	private JButton button1;
	private TmdbApi tmdbApi;
	private JTextField tField;
	private JTextArea textDisplay;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TmdbApi tmdbApi2 = new TmdbApi("dd2378082d2303539238c2a2ab898957");
		
		TmdbMovies movies = tmdbApi2.getMovies();
		
		
		//get information on  a movie remove print statement in model
		
		MovieDb movie = movies.getMovie(5353,  "en");
		System.out.println(movie.getTitle());
		System.out.println(movie.getReleaseDate());
		System.out.println(movie.getRuntime());


		new TestGUI2();
	
	}
	
	public TestGUI2() {
		
		// Instantiates model for calling methods.
		model = new Model();
		tmdbApi = model.getApi();
		
		this.setSize(500,400);
		
		Toolkit tk= Toolkit.getDefaultToolkit();
		
		Dimension dim = tk.getScreenSize();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/**
		 * 
		 */
		JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        // Create and add simple menu item to one of the drop down menu
        JMenuItem newAction = new JMenuItem("New");
        JMenuItem openAction = new JMenuItem("Open");
        JMenuItem exitAction = new JMenuItem("Exit");
        JMenuItem cutAction = new JMenuItem("Cut");
        JMenuItem copyAction = new JMenuItem("Copy");
        JMenuItem pasteAction = new JMenuItem("Paste");
        
        // Create and add CheckButton as a menu item to one of the drop down
        // menu
        //JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");
        // Create and add Radio Buttons as simple menu items to one of the drop
        // down menu
        //JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem("Radio Button1");
       // JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem("Radio Button2");
        // Create a ButtonGroup and add both radio Button to it. Only one radio
        // button in a ButtonGroup can be selected at a time.
       
        // ButtonGroup bg = new ButtonGroup();
        //bg.add(radioAction1);
        //bg.add(radioAction2);
        
        fileMenu.add(newAction);
        fileMenu.add(openAction);
        //fileMenu.add(checkAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.addSeparator();
        
        //editMenu.add(radioAction1);
        //editMenu.add(radioAction2);
		
		int xPos = (dim.width/2) - (this.getWidth()/2);
		int yPos = (dim.height/2) - (this.getHeight()/2);
				
		//Sets frame centered 
		this.setLocation(xPos, yPos);
		
		//this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Movie DB Test");
		
		tField = new JTextField(10);
		textDisplay = new JTextArea(10,20);
		JScrollPane scrollPane = new JScrollPane(textDisplay);
		textDisplay.setLineWrap(true);
		textDisplay.setWrapStyleWord(true);
		textDisplay.setEditable(false);
		
		JLabel label1 = new JLabel("Search");
		
		button1 = new JButton("Go");
		
		button1.addActionListener(this);
		
		
		//JTextArea tArea1 = new JTextArea(10, 30);
		//tArea1.setText("Feel free to type what you want");
		
		//Add poster
		
		MovieImages m = new MovieImages();
		m.getPosters();
		
		System.out.println("Here is the poster: " + m );

		
		ImageIcon pic = new ImageIcon("m");
		//panel.add(new JLabel(pic));
		
		
		//JLabel label1 = new JLabel("First Label");
	
		//label1.setText("First label");
		
		JPanel panel = new JPanel(new GridBagLayout());//;
		
		//panel.add(label1);
		//panel.add(tArea1);
		
		//Search area
		panel.add(label1);
		panel.add(tField);
		panel.add(button1);
		panel.add(scrollPane);
		//panel.add(new JLabel(pic));
		
		panel.add(menuBar);
		this.setJMenuBar(menuBar);
		
		this.add(panel);
		
		//this.pack();
		this.setVisible(true);
		
		tField.setFocusable(true);
		
	}
	
	/**
	 * This method is called when any button is clicked.
	 * 
	 * @param e the event that was fired.
	 */
	public void actionPerformed(ActionEvent e) {
		
		// Extract the button that was clicked
		JComponent buttonPressed = (JComponent) e.getSource();
		
		// React to button presses
		if (buttonPressed == this.button1) {
			if (tField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter some text.");
				return;
			}
			
			// Clear the text area
			this.textDisplay.setText("");
			
			// Access the model to retrieve the search results
			ArrayList<String> movieTitles = this.model.searchForTitles(this.tField.getText());
			for (String title : movieTitles) {
				this.textDisplay.append(title + "\n");
			}
			
			if (movieTitles.isEmpty()) {
				this.textDisplay.append("Nothing found...");
			}
		}
	}
	

}
