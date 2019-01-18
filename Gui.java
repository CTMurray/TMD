
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.rtf.RTFEditorKit;

import info.movito.themoviedbapi.model.MovieImages;

import java.util.ArrayList;

/*******************************************************************************
 * Gui contains the gui part of the TriviaGame project. From the Gui, the user 
 * can choose to search for movies or play a trivia game. Additionally, searches
 *  can be saved to a file.
 * 
 * @author Kimberlin Steffens, Raymundo Ramirez, Camaal Murray, Nicolas Heady
 * @version First Release
 ******************************************************************************/
public class Gui extends JFrame implements ActionListener {

  /** The model class for searching and posters. */
  private Model model;

  /** Go button to do searches. */
  private JButton goButton;

  /** Answer button to answer questions. */
  private JButton answerButton;

  /** Text field for user to type. */
  private JTextField tField;

  /** Text area for updated text (for search results, trivia, etc.). */
  private JTextArea textDisplay, detailDisplay;

  /** Holds position of line clicked in textDisplay. */
  private int prevHighlightStart, prevHighlightEnd;

  /** File menu for the gui. */
  private JMenu fileMenu;

  /** Edit menu for the gui. */
  private JMenu editMenu;

  /** The menu bar for the gui. */
  private JMenuBar menuBar;

  /** Option in fileMenu for "new" action. */
  private JMenuItem newAction;

  /** Option in fileMenu for saving searches. */
  private JMenuItem saveAction;

  /** Option in fileMenu for opening files. */
  private JMenuItem openAction;

  /** Option in fileMenu for exiting. */
  private JMenuItem exitAction;

  /** Option in editMenu for cutting. */
  private JMenuItem cutAction;

  /** Option in editMenu for copying. */
  private JMenuItem copyAction;

  /** Option in editMenu for pasting. */
  private JMenuItem pasteAction;

  /** Option in fileMenu for starting a TriviaGame. */
  private JMenuItem triviaGame;

  /** Option in fileMenu for searching for movies. */
  private JMenuItem searchMovies;

  /** The panel to place parts of the gui. */
  private JPanel infoPanel;

  /** The button panel to place buttons. */
  private JPanel buttonPanel;

  /** The searchLabel displays whether they are searching or doing Trivia. */
  private JLabel searchLabel;

  /** Boolean for whether playing trivia (true) or not (false). */
  private boolean playingTrivia;

  /** The TriviaGame itself. */
  private TriviaGame trivia;

  /** The scroll pane for the textDisplay. */
  private JScrollPane displayScrollPane;

  /** The scroll pane for the detailDisplay. */
  private JScrollPane detailScrollPane;

  /** The image panel. */
  private BackgroundPanel imgPanel;

  /** File dialog for use in doOpen() and doSave(). */
  private JFileChooser fileDialog;

  /** The serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The size of components. */
  private final int size = 500;
  
  /** The x-dimension of display components. */
  private final int xDim = 20;
  
  /** The y-dimension of display components. */
  private final int yDim = 30;
  
  /** The size of the text field. */
  private final int textBoxSize = 10;

  /*****************************************************************************
   * Constructor for the GUI.
   ****************************************************************************/
  public Gui() {

    playingTrivia = false;

    // Instantiates model for calling methods.
    model = new Model();

    menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    menuBar.add(fileMenu);
    menuBar.add(editMenu);

    // Create and add simple menu item to one of the drop down menu
    newAction = new JMenuItem("New");
    openAction = new JMenuItem("Open");
    saveAction = new JMenuItem("Save");
    exitAction = new JMenuItem("Exit");
    cutAction = new JMenuItem("Cut");
    copyAction = new JMenuItem("Copy");
    pasteAction = new JMenuItem("Paste");
    triviaGame = new JMenuItem("Trivia Game");
    searchMovies = new JMenuItem("Search Movies");

    imgPanel = new BackgroundPanel();
    imgPanel.setSize(size, size);

    fileMenu.add(newAction);
    fileMenu.add(openAction);
    fileMenu.add(saveAction);

    fileMenu.addSeparator();
    fileMenu.add(exitAction);
    editMenu.add(cutAction);
    editMenu.add(copyAction);
    editMenu.add(pasteAction);
    fileMenu.add(triviaGame);
    fileMenu.add(searchMovies);
    editMenu.addSeparator();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setTitle("Trivia Game");

    tField = new JTextField(textBoxSize);
    textDisplay = new JTextArea(xDim, yDim);
    detailDisplay = new JTextArea(xDim, yDim);
    displayScrollPane = new JScrollPane(textDisplay);
    detailScrollPane = new JScrollPane(detailDisplay);
    textDisplay.setLineWrap(true);
    textDisplay.setWrapStyleWord(true);
    textDisplay.setEditable(false);
    detailDisplay.setLineWrap(true);
    detailDisplay.setWrapStyleWord(true);
    detailDisplay.setEditable(false);
    searchLabel = new JLabel("Search");

    goButton = new JButton("Go");
    answerButton = new JButton("Answer");
    answerButton.setEnabled(false);
    answerButton.setEnabled(false);

    goButton.addActionListener(this);
    answerButton.addActionListener(this);
    openAction.addActionListener(this);
    saveAction.addActionListener(this);
    exitAction.addActionListener(this);
    triviaGame.addActionListener(this);
    searchMovies.addActionListener(this);

    MovieImages m = new MovieImages();
    m.getPosters();

    infoPanel = new JPanel(new GridLayout(1, 3));
    buttonPanel = new JPanel(new GridLayout(1, 3));

    this.setJMenuBar(menuBar);

    this.add(infoPanel, BorderLayout.SOUTH);
    this.add(buttonPanel, BorderLayout.NORTH);

    tField.setFocusable(true);

    // Search area
    buttonPanel.add(searchLabel);
    buttonPanel.add(tField);
    buttonPanel.add(goButton);
    buttonPanel.add(answerButton);

    infoPanel.add(displayScrollPane);
    infoPanel.add(imgPanel);
    infoPanel.add(detailScrollPane);

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    /** Check for line selected in textDisplay and highlights it. */
    textDisplay.addMouseListener(new MouseAdapter() {
      public void mouseClicked(final MouseEvent me) {

        int line;
        try {
          line = textDisplay.getLineOfOffset(textDisplay.getCaretPosition());
          int start = textDisplay.getLineStartOffset(line);
          int end = textDisplay.getLineEndOffset(line);
          String textOnLine = textDisplay.getDocument()
              .getText(start, end - start);

          // For troubleshooting:
          System.out.println("Start: " + start + " End: " + end);
          System.out.println("textOnLine: " + textOnLine);

          if (prevHighlightEnd != 0) {
            textDisplay.getHighlighter().removeAllHighlights();
          }

          Highlighter.HighlightPainter painter 
          = new DefaultHighlighter.DefaultHighlightPainter(
              Color.LIGHT_GRAY);
          textDisplay.getHighlighter().addHighlight(start, end, painter);

          setPrevHighlightStart(start);
          prevHighlightEnd = end;

          BufferedImage poster = model.getNewPoster(textOnLine);
          poster = model.getScaledImage(poster, size, size);
          updatePoster(poster);
          updateDetails(textOnLine);

        } catch (BadLocationException e) {
          
          // TODO Auto-generated catch block
          System.out.println(e.getMessage());
          e.printStackTrace();
        }

      }
    });

  }

  /*****************************************************************************
   * Loads the file chosen.
   * 
   * @param file
   *          the file to be loaded
   ****************************************************************************/
  private void openFile(final File file) {
    textDisplay.setText("");
    RTFEditorKit rtfReader = new RTFEditorKit();
    try {
      FileInputStream fis = new FileInputStream(file);
      
      BufferedReader br = new BufferedReader(new InputStreamReader(fis));
      String strLine;
      String searchText = br.readLine();
      tField.setText(searchText);
      while ((strLine = br.readLine()) != null)   {
    	  textDisplay.append(strLine + "\n");
    	}
      br.close();
      
      model.searchForTitles(tField.getText());
      
      //rtfReader.read(fis, textDisplay.getDocument(), 0);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null,
          "Sorry, but an error " + "occurred while trying to open the file:\n" 
      + ex);
      return;
    }

  }

  /*****************************************************************************
   * Method to save the search results.
   * 
   * @param file
   *          the file to be saved
   ****************************************************************************/
  private void saveFile(final File file) {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(file));
      out.println(tField.getText());
      out.println(textDisplay.getText());
      out.close();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null,
          "Sorry, but an error " + "occurred while trying to save the file:\n" 
      + ex);
      return;
    }
  }

  /*****************************************************************************
   * This method is called when any button is clicked.
   * 
   * @param e
   *          the event that was fired.
   ****************************************************************************/
  public final void actionPerformed(final ActionEvent e) {

    // Extract the button that was clicked
    JComponent buttonPressed = (JComponent) e.getSource();
    if (buttonPressed == saveAction) {
      fileDialog = new JFileChooser() {
        private static final long serialVersionUID = 1L;

        public void approveSelection() {
          if (getSelectedFile().exists() && getDialogType() == SAVE_DIALOG) {
            int result = JOptionPane.showConfirmDialog(this, "The file exists,"
                + " overwrite?",
                "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            switch (result) {
            case JOptionPane.YES_OPTION:
              super.approveSelection();
              saveFile(getSelectedFile());
              return;
            case JOptionPane.NO_OPTION:
              return;
            case JOptionPane.CLOSED_OPTION:
              return;
            case JOptionPane.CANCEL_OPTION:
              cancelSelection();
              return;
            default:
              break;
            }
          } else if (!getSelectedFile().exists() 
              && getDialogType() == SAVE_DIALOG) {
            super.approveSelection();
            saveFile(getSelectedFile());
          }
        }
      };

      File selectedFile = new File("searchlist.rtf");
      fileDialog.setSelectedFile(selectedFile);
      fileDialog.showSaveDialog(null);
    }

    if (buttonPressed == openAction) {
      fileDialog = new JFileChooser();
      int option = fileDialog.showOpenDialog(null);

      if (option == JFileChooser.APPROVE_OPTION) {
        openFile(fileDialog.getSelectedFile());
      }
    }

    if (buttonPressed == goButton) {
      if (tField.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter some text.");
        return;
      }

      // Clear the text area
      textDisplay.setText("");

      // Access the model to retrieve the search results
      ArrayList<String> movieTitles = model.searchForTitles(tField.getText());

      // Updates the details display area
      this.updateDetails(movieTitles.get(0));

      for (String title : movieTitles) {
        textDisplay.append(title + "\n");
      }

      if (movieTitles.isEmpty()) {
        textDisplay.append("Nothing found...");
      }

      // Poster Retrieve
      BufferedImage image = model.getFirstMoviePoster(tField.getText());

      image = model.getScaledImage(image, size, size);
      this.updatePoster(image);
    }

    // exit button functionality
    if (buttonPressed == exitAction) {
      System.exit(0);
    }
    if (buttonPressed == triviaGame) {
      beginTrivia();
    }
    if (buttonPressed == searchMovies) {
      if (playingTrivia) {
        textDisplay.setText("Triva Game Results \n Correct: " 
      + trivia.getNumCorrect() + "\n Incorrect: " + trivia.getNumIncorrect() 
      + "\n You may now enter a search query.");

      } else {
        textDisplay.setText("");
      }
      playingTrivia = false;
      trivia = null;
      answerButton.setEnabled(false);
      goButton.setEnabled(true);
      searchLabel.setText("Search");
      imgPanel.changeBackground("Please search for a movie");
    }
    // if answering trivia
    if (buttonPressed == answerButton) {

        // answer the question
        String posterURL = trivia.getPoster();
        BufferedImage img = model.getMoviePoster(posterURL);
        img = model.getScaledImage(img, size, size);
        this.updatePoster(img);
        trivia.answer(tField.getText());
        textDisplay.setText(trivia.getLeftText());
        detailDisplay.setText(trivia.getRightText());
      
      if (trivia.isGameOver()) {
        String optionText;
        if (trivia.getNumIncorrect() == 3) {
          optionText = "Game over. You lost. " 
        + "Would you like to play again?";
          
          imgPanel.changeBackground(":'(");

        } else {
          optionText = "Game over. You won! Would you like to play again?";
          imgPanel.changeBackground(":D");
        }
        int result = JOptionPane.showConfirmDialog(null, optionText, null,
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
          beginTrivia();

        } else if (result == JOptionPane.NO_OPTION) {
          textDisplay
              .setText("Triva Game Results \n Correct: " 
          + trivia.getNumCorrect() + "\n Incorrect: " + trivia.getNumIncorrect()
          + "\n You may now enter a search query.");
          
          detailDisplay.setText("");
          playingTrivia = false;
          trivia = null;
          answerButton.setEnabled(false);
          goButton.setEnabled(true);
          searchLabel.setText("Search");
        }

      }
    }
    }
  

  /**
   * Updates the poster image on the GUI.
   * 
   * @param image
   *          the poster image to be displayed
   */
  private void updatePoster(final BufferedImage image) {
    this.imgPanel.changeBackground(image);
    this.imgPanel.repaint();
  }

  /*****************************************************************************
   * Updates the details displayed for the selected movie.
   * 
   * @param title
   *          the movie title
   ****************************************************************************/
  private void updateDetails(final String title) {
    this.detailDisplay.setText(this.model.generateDetails(title));
  }

  /*****************************************************************************
   * Gets the prevHighlightStart.
   * 
   * @return the prevHighlightStart.
   ****************************************************************************/
  public final int getPrevHighlightStart() {
    return prevHighlightStart;
  }

  /*****************************************************************************
   * Sets the prevHighlightStart.
   * 
   * @param pHS 
   *        the prevHighlightStart.
   ****************************************************************************/
  public final void setPrevHighlightStart(final int pHS) {
    this.prevHighlightStart = pHS;
  }
  
  /*****************************************************************************
   * Starts up the TriviaGame.
   ****************************************************************************/
  public final void beginTrivia() {
    searchLabel.setText("Trivia");
    playingTrivia = true;
    trivia = new TriviaGame();
    answerButton.setEnabled(true);
    goButton.setEnabled(false);
    textDisplay.setText(trivia.getLeftText());
    detailDisplay.setText(trivia.getRightText());
    imgPanel.changeBackground("Can you get it right?");
  }
}
