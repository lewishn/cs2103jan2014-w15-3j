import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

@SuppressWarnings("serial")
public class DoThingsGUI extends JFrame  {
	private static final String DEFAULT_EXIT = "exit";
	private static final String MESSAGE_STARTUP = "Get ready to Do Things!";
	private static final String STARTUP_COMMAND = "list";
	private static final String ERROR_CODE = "error";
	private static final String MARK_CODE = "marked";
	private static final String ALIAS = "@lias: ";
	private static final String OVERDUE = "overdue";
	private static final String DUE_TODAY = "today";
	private static final String FLOATING = "floating";
	private static final String DEFAULT_HELP = "help";
	private static final String HEADING_LABEL = "Do-Things";
	private static final String CONTENT_PANE_TITLE = "Do-Things";
	private static final String PLUTO_COND_LIGHT = "Pluto Sans Cond Light";
	private static final String PLUTO_EXLIGHT = "Pluto Sans ExtraLight";
	private static final String MESSAGE_ERROR_SYSTEM_TRAY = "Unable to set System Tray";
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;
	private static final int COMMAND_ENTER = KeyEvent.VK_ENTER;
	private static final int COMMAND_HIDE = NativeKeyEvent.VK_F8;
	private static final int COMMAND_SHIFT_WINDOW_LEFT = KeyEvent.VK_F11;
	private static final int COMMAND_SHIFT_WINDOW_RIGHT = KeyEvent.VK_F12;
	private static final int COMMAND_SHIFT_WINDOW_UP = KeyEvent.VK_F9;
	private static final int COMMAND_SHIFT_WINDOW_DOWN = KeyEvent.VK_F10;
	private static final int COMMAND_SCROLL_UP = KeyEvent.VK_UP;
	private static final int COMMAND_SCROLL_DOWN = KeyEvent.VK_DOWN;
	private static final int FRAME_MOVEMENT = 10;		
	private static final int FRAME_SCROLL_SPEED = 75;
	private static final int TASK_OBJECT_FRAME_HEIGHT = 53;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 700;
	private static final int TEXT_PANEL_HEIGHT = 35;
	private static final int TEXT_PANEL_WIDTH = FRAME_WIDTH;
	private static final int TEXT_PANEL_X_OFFSET = 0;
	private static final int TEXT_PANEL_Y_OFFSET = 62;
	private static final int HEADING_LABEL_BUFFER = 10;
	private static final int HEADING_LABEL_FONT_SIZE = 25;
	private static final int HEADING_LABEL_X_OFFSET = 0;
	private static final int HEADING_LABEL_Y_OFFSET = 0;
	private static final int HEADING_LABEL_WIDTH = FRAME_WIDTH;
	private static final int HEADING_LABEL_HEIGHT = 63;
	private static final int INPUT_FIELD_X_OFFSET = 10;
	private static final int INPUT_FIELD_Y_OFFSET = 57;
	private static final int INPUT_FIELD_WIDTH = FRAME_WIDTH - 20;
	private static final int INPUT_FIELD_HEIGHT = 33;
	private static final int INPUT_FIELD_FONT_SIZE = 23;
	private static final int FEEDBACK_FONT_SIZE = 14;
	private static final int FEEDBACK_X_OFFSET = 0;
	private static final int FEEDBACK_Y_OFFSET = 30;
	private static final int FEEDBACK_WIDTH = FRAME_WIDTH;
	private static final int FEEDBACK_HEIGHT = 33;
	private static final int ZERO = 0;
	private static final int CONTENT_PANE_X_OFFSET = 100;
	private static final int CONTENT_PANE_Y_OFFSET = 25;
	private static final int CONTENT_PANE_WIDTH = FRAME_WIDTH;
	private static final int CONTENT_PANE_HEIGHT = FRAME_HEIGHT;
	private static final int SHAPE_DIMENSION = 20;
	private static int heightChange =0;

	private JPanel contentPane;
	private static JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private static JLabel feedbackLabel;
	private JScrollPane taskPanelScroll;
	private JScrollBar verticalScrollBar;
	private static JTextArea help;
	private static JPanel taskPanel;
	private static ArrayList<JPanel> taskObjectPanel;
	private static ArrayList<JTextArea> dateTime;
	private static ArrayList<JTextArea> alias;
	private static ArrayList<JTextArea> taskDescription;
	private static ArrayList<String> taskDesc;
	private static ArrayList<String> taskAlias;
	private static ArrayList<String> taskStatus;
	private static ArrayList<String> taskDate;
	private static ArrayList<String> taskTime;
	private static SystemTray tray;
	private static TrayIcon trayIcon;
	private GlobalKeyPress globalKeyPress; 
	private TriggerOnKeyAction triggerOnKeyAction;
	private TriggerOnMouseAction triggerOnMouseAction;
	private static Image iconImage;

	private static final Color HIGHLIGHT_FONT_DARK_BLUE = new Color(0,0,51); 
	private static final Color HIGHLIGHT_Yellow = new Color(255,255,51);
	private static final Color INPUT_FIELD_BACKGROUND_LIGHT_BLUE= new Color(153, 204, 255);

	private int xCoordOfFrame;
	private int yCoordOfFrame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DoThingsGUI () {

		getIconImageForGUI();
		createContentPane();		
		createInputField();
		createFeedbackLabel();
		createTitleLabel();
		createTextPanel();	
		createTaskPanel();
		createTaskPanelScroll();
		populateToDoListOnStartup();
		setGUIAppearMiddleOfScreen();
		setListeners();
		setVerticalScrollBarSettings();

	}
	
	// @author A0101924R
	private class GlobalKeyPress implements WindowListener, NativeKeyListener{

		private static final String MESSAGE_REGISTER_NATIVE_HOOK_ERROR = "There was a problem registering the native hook.";

		Boolean isVisible = false;

		GlobalKeyPress(Boolean visible) {
			isVisible = visible;
		}		
		/**
		 * Listens for opened window and registers native hook to the window. Adds NativeKeyListener to the window
		 */
		@Override
		public void windowOpened(WindowEvent e) {
			//Initialize native hook.
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				printErrorMessage(ex);
				System.exit(1);
			}
			GlobalScreen.getInstance().addNativeKeyListener(this);
		}		
		/**
		 * Listens for closed window and unregisters native hook from the window.
		 */
		@Override
		public void windowClosed(WindowEvent e) {
			//Clean up the native hook.
			GlobalScreen.unregisterNativeHook();
			System.runFinalization();
			System.exit(0);
		}	
		/**
		 * Prints error message when window fails to register native hook
		 * @param ex
		 */
		private void printErrorMessage(NativeHookException ex) {
			System.err.println(MESSAGE_REGISTER_NATIVE_HOOK_ERROR);
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}		
		/**
		 * Listens for global key press. If window is visible, sets window to invisible and hide it to system tray. 
		 * If window is not visible, sets window to visible and remove system tray.  
		 */
		@Override
		public void nativeKeyReleased(NativeKeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == COMMAND_HIDE) {
				if (isVisible) {
					inputField.requestFocus();
					DoThingsGUI.this.setVisible(false);
					isVisible = false;
					hideToSytemTray();

				} else {
					inputField.requestFocus();
					DoThingsGUI.this.setVisible(true);
					isVisible = true;
					removeFromSystemTray();
				}
			}
		}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void nativeKeyPressed(NativeKeyEvent e) {}
		
		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0) {}
	}

	private class TriggerOnKeyAction implements KeyListener{
		
		/**
		 * Listens for key released. If user press and releases ENTER key, execute user commands
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			switch(key){
			case COMMAND_ENTER:
				String userInput = inputField.getText();
				ResponsiveContent.drawTaskObjectField(userInput);
				break;
			default:
				break;	
			}	
		}	
		/**
		 * Listens and executes key pressed.
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			InputMap verticalMap = verticalScrollBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW );

			switch(key){
			case COMMAND_SHIFT_WINDOW_UP:
				setLocation(getX(),getY()-FRAME_MOVEMENT);
				break;
			case COMMAND_SHIFT_WINDOW_DOWN:
				setLocation(getX(),getY()+FRAME_MOVEMENT);
				break;
			case COMMAND_SHIFT_WINDOW_LEFT:
				setLocation(getX()-FRAME_MOVEMENT,getY());
				break;
			case COMMAND_SHIFT_WINDOW_RIGHT:
				setLocation(getX()+FRAME_MOVEMENT,getY());
				break;
			case COMMAND_SCROLL_UP:					
				verticalMap.put(KeyStroke.getKeyStroke( "UP" ),"negativeUnitIncrement" );
				break;
			case COMMAND_SCROLL_DOWN:
				verticalMap.put(KeyStroke.getKeyStroke( "DOWN" ),"positiveUnitIncrement" );
				break;
			default:
				break;
			}
		}
		
		@Override
		public void keyTyped(KeyEvent e) {}	
	}

	private class TriggerOnMouseAction implements MouseListener, MouseMotionListener{
		
		/**
		 * Listens for mouse press and records the the x,y coordinates relative to the source component.
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {	 
			// Get x,y and store them
			xCoordOfFrame=arg0.getX();
			yCoordOfFrame=arg0.getY();
		}		
		/**
		 * Listens for mouse drag and shifts the frame to the end position.
		 */
		@Override
		public void mouseDragged(MouseEvent arg0) {
			setLocation(getX()+arg0.getX()-xCoordOfFrame,getY()+arg0.getY()-yCoordOfFrame);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mouseMoved(MouseEvent arg0) {}
	}	
	/**
	 * Hides program to system tray and creates tray icon in system tray
	 */
	private void hideToSytemTray(){
		try{
			PopupMenu popup = new PopupMenu();
			tray = SystemTray.getSystemTray();   
			trayIcon = new TrayIcon(iconImage, "Do-Things", popup);
			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);		
		}catch(Exception e){
			System.out.println(MESSAGE_ERROR_SYSTEM_TRAY);
		}
	}	
	/**
	 * Removes tray icon from system tray.
	 */
	private void removeFromSystemTray(){
		tray.remove(trayIcon);
	}	
	/**
	 * Sets GUI to appear in the middle of the screen by default.
	 */
	private void setGUIAppearMiddleOfScreen() {
		setLocationRelativeTo(null);
	}	
	/**
	 * Gets the icon image from home directory.
	 */
	private void getIconImageForGUI() {
		iconImage = Toolkit.getDefaultToolkit().getImage("Task.png");
	}	
	/**
	 * Sets vertical scroll bar scrolling speed.
	 */
	private void setVerticalScrollBarSettings() {
		verticalScrollBar = taskPanelScroll.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(FRAME_SCROLL_SPEED);
	}	
	/**
	 * Sets various listeners to some GUI components.
	 */
	private void setListeners() {	
		setWindowHideDisplayListener();
		setInputFieldListener();
		setHeadingLabelListener();
	}	
	/**
	 * Sets windowListener to GUI to listen for global key press.
	 */
	private void setWindowHideDisplayListener() {
		globalKeyPress = new GlobalKeyPress(true);
		addWindowListener(globalKeyPress);
	}	
	/**
	 * Sets keyListener to input field.
	 */
	private void setInputFieldListener() {
		triggerOnKeyAction = new TriggerOnKeyAction();
		inputField.addKeyListener(triggerOnKeyAction);
	}	
	/**
	 * Sets mouseListener and mouseActionListener to Heading Label.
	 */
	private void setHeadingLabelListener() {
		triggerOnMouseAction = new TriggerOnMouseAction();
		headingLabel.addMouseListener(triggerOnMouseAction);
		headingLabel.addMouseMotionListener(triggerOnMouseAction);
	}
	
	// @author A0097082Y
	/**
	 * populates to do list on startup
	 */
	private void populateToDoListOnStartup() {
		ResponsiveContent.drawTaskObjectField(STARTUP_COMMAND);
		feedbackLabel.setText(MESSAGE_STARTUP);
	}
	/**
	 * creates task panel scroll which sits below the input field (i.e. white box). Gives scroll property to panel within it.
	 */
	private void createTaskPanelScroll() {
		taskPanelScroll = new JScrollPane(taskPanel);
		taskPanelScroll.setFocusable(false);
		GroupLayout gl_taskPanel = new GroupLayout(taskPanel);
		gl_taskPanel.setHorizontalGroup(
				gl_taskPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 320, Short.MAX_VALUE)
				);
		gl_taskPanel.setVerticalGroup(
				gl_taskPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 485, Short.MAX_VALUE)
				);
		taskPanel.setLayout(gl_taskPanel);
		taskPanelScroll.setOpaque(false);
		taskPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		taskPanelScroll.setBorder(null);
		taskPanelScroll.setBounds(0, 97, FRAME_WIDTH, 603);
		contentPane.add(taskPanelScroll);
	}
	/**
	 * Creates task panel. It is inserted to taskPanelScroll.
	 */
	private void createTaskPanel() {
		taskPanel = new JPanel();
		taskPanel.setFocusable(false);
		taskPanel.setBounds(0, 0, 171, 485);
		taskPanel.setOpaque(false);
		taskPanel.setBackground(new Color(255, 204, 51));
		contentPane.add(taskPanel);
	}
	/**
	 * creates text panel to sit behind the input field. 
	 */
	private void createTextPanel() {
		textPanel = new JPanel();
		textPanel.setFocusable(false);
		textPanel.setBackground(Color.white);
		textPanel.setBounds(TEXT_PANEL_X_OFFSET, TEXT_PANEL_Y_OFFSET, TEXT_PANEL_WIDTH, TEXT_PANEL_HEIGHT);
		contentPane.add(textPanel);
	}
	/**
	 * creates title label
	 */
	private void createTitleLabel() {
		headingLabel = new JLabel(HEADING_LABEL);
		headingLabel.setForeground(Color.GRAY);
		headingLabel.setVerticalAlignment(SwingConstants.TOP);
		headingLabel.setVerticalTextPosition(SwingConstants.TOP);
		headingLabel.setBorder(new EmptyBorder(HEADING_LABEL_BUFFER, HEADING_LABEL_BUFFER, HEADING_LABEL_BUFFER, HEADING_LABEL_BUFFER));
		headingLabel.setOpaque(true);
		headingLabel.setBackground(Color.white);
		headingLabel.setFont(new Font(PLUTO_COND_LIGHT, Font.PLAIN, HEADING_LABEL_FONT_SIZE));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(HEADING_LABEL_X_OFFSET, HEADING_LABEL_Y_OFFSET, HEADING_LABEL_WIDTH, HEADING_LABEL_HEIGHT);
		contentPane.add(headingLabel);
	}
	/**
	 * creates feedback label which sits below the title. Feedback for users are printed here.
	 */
	private void createFeedbackLabel() {
		feedbackLabel = new JLabel();
		feedbackLabel.setFocusable(false);
		feedbackLabel.setForeground(Color.GRAY);
		feedbackLabel.setFont(new Font(PLUTO_EXLIGHT, Font.PLAIN, FEEDBACK_FONT_SIZE));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(FEEDBACK_X_OFFSET, FEEDBACK_Y_OFFSET, FEEDBACK_WIDTH, FEEDBACK_HEIGHT);
		contentPane.add(feedbackLabel);
	}
	/**
	 * creates text input field where user types in commands
	 */
	private void createInputField() {
		inputField = new JTextField();
		inputField.setSelectedTextColor(HIGHLIGHT_FONT_DARK_BLUE);
		inputField.setSelectionColor(HIGHLIGHT_Yellow);
		inputField.setBounds(INPUT_FIELD_X_OFFSET, INPUT_FIELD_Y_OFFSET, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
		inputField.setBorder(null);
		inputField.setBackground(INPUT_FIELD_BACKGROUND_LIGHT_BLUE);
		inputField.setForeground(Color.BLACK);
		inputField.setFont(new Font(PLUTO_EXLIGHT, Font.PLAIN, INPUT_FIELD_FONT_SIZE));
		contentPane.add(inputField);
	}
	/**
	 *  creates Content pane which holds everything
	 */
	private void createContentPane() {
		setForeground(Color.BLACK);
		setTitle(CONTENT_PANE_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(CONTENT_PANE_X_OFFSET, CONTENT_PANE_Y_OFFSET, CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setShape(new RoundRectangle2D.Double(ZERO,ZERO,FRAME_WIDTH,FRAME_HEIGHT, SHAPE_DIMENSION, SHAPE_DIMENSION));
		setIconImage(iconImage);
	}

	private static class ResponsiveContent {

		private static final Color MESSAGE_ELSE_TIME_BACKGROUND_GREEN = new Color(153,204,102);
		private static final Color FONT_ELSE_TIME_WHITE = new Color(255,255,255);
		private static final Color MESSAGE_OVERDUE_BACKGROUND_RED = new Color(204,51,61);
		private static final Color FONT_OVERDUE_WHITE = new Color(255,255,255);
		private static final Color MESSAGE_TODAY_BACKGROUND_YELLOW = new Color(255,255,51);
		private static final Color FONT_TODAY_DARK_GREY = new Color(102,102,102);
		private static final Color FONT_FLOAT_WHITE = new Color(255,255,255); 
		private static final Color MESSAGE_FLOAT_BACKGROUND_TURQUOISE = new Color(153, 204, 153); 
		private static final Color MESSAGE_MARKED_BACKGROUND_LIGHT_GREY = new Color(204, 204, 204); 
		private static final Color FONT_MARKED_GREY = new Color(153,153,153); 
		private static final int TASK_DESCRIPTION_FONT_SIZE = 18;
		private static final int ALIAS_FONT_SIZE = 11;
		private static final int DATE_TIME_FONT_SIZE = ALIAS_FONT_SIZE;
		private static final int TASK_DESCRIPTION_X_OFFSET = 10;
		private static final int TASK_DESCRIPTION_Y_OFFSET = 10;
		private static final int TASK_DESCRIPTION_WIDTH = 25;
		private static final int TASK_DESCRIPTION_HEIGHT = 23;
		private static final int ALIAS_X_OFFSET = 265;
		private static final int ALIAS_Y_OFFSET = 31;
		private static final int ALIAS_WIDTH = 120;
		private static final int ALIAS_HEIGHT = 22;
		private static final int DATE_TIME_X_OFFSET = 10;
		private static final int DATE_TIME_Y_OFFSET = 31;
		private static final int DATE_TIME_WIDTH = 250;
		private static final int DATE_TIME_HEIGHT = 22;
		private static final int MESSAGE_PANEL_HEIGHT = 51;
		private static final int ZERO = 0;
		private static final int NUM_CHAR_FIRST_LINE = 31;
		private static final int NUM_CHAR_LINE = 34;
		private static final int HEIGHT_OF_ONE_LINE = 25;
		private static final int NUM_CHAR_ALIAS_FIRST_LINE = 14;
		private static final int NUM_CHAR_ALIAS_LINE = 19;
		private static final int HELP_X_OFFSET = 10;
		private static final int HELP_Y_OFFSET = 10;
		private static final int HELP_WIDTH = FRAME_WIDTH;
		private static final int HELP_HEIGHT = 500;
		private static final int HELP_FONT_SIZE = 13;
		private static final String PLUTO_COND_EXLIGHT_FONT = "Pluto Sans Cond ExLight";
		private static final String PLUTO_LIGHT_FONT = "Pluto Sans Light";
		/**
		 * function to create task object panel which holds task description, alias and date & time text areas
		 * @param aliasExtension
		 * @param descriptionExtension
		 * @param change
		 * @param i
		 */
		private static void createTaskObjects(int aliasExtension, int descriptionExtension, int change, int i) {
			taskObjectPanel(aliasExtension, descriptionExtension, change, i);
			createDateTimeField(descriptionExtension, i);
			createAliasField(aliasExtension, descriptionExtension, i);
			createTaskDescription(descriptionExtension, i);
		}
		/**
		 * creates task description text area within the task object panel
		 * @param descriptionExtension
		 * @param i
		 */
		private static void createTaskDescription(int descriptionExtension, int i) {
			taskDescription.add(new JTextArea());	
			taskDescription.get(i).setFont(new Font(PLUTO_LIGHT_FONT, Font.PLAIN, TASK_DESCRIPTION_FONT_SIZE));	
			taskDescription.get(i).setBounds(TASK_DESCRIPTION_X_OFFSET,TASK_DESCRIPTION_Y_OFFSET, FRAME_WIDTH - TASK_DESCRIPTION_WIDTH,TASK_DESCRIPTION_HEIGHT + descriptionExtension); //55 characters
			taskDescription.get(i).setLineWrap(true);
			taskDescription.get(i).setWrapStyleWord(true);
			taskDescription.get(i).setEditable(false);
			taskDescription.get(i).setOpaque(false);
			taskObjectPanel.get(i).add(taskDescription.get(i));
		}
		/**
		 * creates alias text area within task object panel
		 * @param aliasExtension
		 * @param descriptionExtension
		 * @param i
		 */
		private static void createAliasField(int aliasExtension, int descriptionExtension,int i) {
			alias.add(new JTextArea());
			alias.get(i).setFont(new Font(PLUTO_COND_EXLIGHT_FONT, Font.PLAIN, ALIAS_FONT_SIZE));
			alias.get(i).setBounds(ALIAS_X_OFFSET, ALIAS_Y_OFFSET + descriptionExtension, ALIAS_WIDTH, ALIAS_HEIGHT + aliasExtension);
			alias.get(i).setLineWrap(true);
			alias.get(i).setWrapStyleWord(true);
			alias.get(i).setOpaque(false);
			alias.get(i).setEditable(false);
			taskObjectPanel.get(i).add(alias.get(i));
		}
		/**
		 * Creates date & time text area within task object panel
		 * @param descriptionExtension
		 * @param i
		 */
		private static void createDateTimeField(int descriptionExtension ,int i) {
			dateTime.add(new JTextArea());
			dateTime.get(i).setFont(new Font(PLUTO_COND_EXLIGHT_FONT, Font.PLAIN, DATE_TIME_FONT_SIZE));
			dateTime.get(i).setBounds(DATE_TIME_X_OFFSET, DATE_TIME_Y_OFFSET + descriptionExtension, DATE_TIME_WIDTH, DATE_TIME_HEIGHT);
			dateTime.get(i).setOpaque(false);
			dateTime.get(i).setEditable(false);
			taskObjectPanel.get(i).add(dateTime.get(i));
		}
		/**
		 * Creates task object which is one panel holding all the description, alias and date & time text fields 
		 * @param aliasExtension
		 * @param descriptionExtension
		 * @param change
		 * @param i
		 */
		private static void taskObjectPanel(int aliasExtension, int descriptionExtension, int change, int i) {
			taskObjectPanel.add(new JPanel());
			taskObjectPanel.get(i).setBounds(ZERO, ZERO + change, FRAME_WIDTH, MESSAGE_PANEL_HEIGHT + descriptionExtension + aliasExtension);
			taskObjectPanel.get(i).setLayout(null);
			taskPanel.add(taskObjectPanel.get(i));
			taskPanel.revalidate();
			taskPanel.repaint();
		}
		/**
		 * updates panel height according to how many tasks are listed
		 * @param userInput 
		 */
		private static void drawTaskObjectField(String userInput) {
			ArrayList<ArrayList<String>> result = MainLogic.runLogic(userInput);
			String feedbackType = result.get(FEEDBACK_TYPE).get(ZERO);
			if(feedbackType.equals(DEFAULT_EXIT)) {
				System.exit(ZERO);
			} else if (feedbackType.equals(ERROR_CODE)) {
				String feedbackDesc = result.get(FEEDBACK_DESC).get(ZERO);
				errorProcessing(feedbackDesc);
			} else {
				String feedbackDesc = result.get(FEEDBACK_DESC).get(ZERO);
				refreshTaskPanel();

				if (feedbackType.equals(DEFAULT_HELP)) {
					String helpDesc = result.get(TASK_DESC).get(ZERO);
					feedbackLabel.setText(feedbackDesc);
					printHelp(helpDesc);
				} else {
					if (feedbackType.equals(ERROR_CODE)) {
						errorProcessing(feedbackDesc);
					} else {
						taskDesc = result.get(TASK_DESC);
						taskAlias = result.get(TASK_ALIAS);
						taskStatus = result.get(TASK_STATUS);
						taskDate = result.get(TASK_DATE);
						taskTime = result.get(TASK_TIME);
						int numOfTask = initialiseFeedbackVariables();
						for(int i=ZERO; i<numOfTask; i++) {	

							int descriptionOverflowExtension = heightForDescriptionTextOverflow(i);
							int aliasOverflowExtension = heightForAliasTextOverflow(i);
							createTaskObjects(aliasOverflowExtension, descriptionOverflowExtension, heightChange, i);
							heightChange += descriptionOverflowExtension;
							heightChange += aliasOverflowExtension;

							if (taskStatus.get(i).equals(MARK_CODE)) {
								setTaskObjectColorScheme(i, MESSAGE_MARKED_BACKGROUND_LIGHT_GREY, FONT_MARKED_GREY);
							} else if (taskTime.get(i).equals(FLOATING)) {
								setTaskObjectColorScheme(i, MESSAGE_FLOAT_BACKGROUND_TURQUOISE, FONT_FLOAT_WHITE);
							} else if (taskTime.get(i).equals(DUE_TODAY)) {
								setTaskObjectColorScheme(i, MESSAGE_TODAY_BACKGROUND_YELLOW, FONT_TODAY_DARK_GREY);
							} else if (taskTime.get(i).equals(OVERDUE)) {
								setTaskObjectColorScheme(i, MESSAGE_OVERDUE_BACKGROUND_RED, FONT_OVERDUE_WHITE);
							} else {
								setTaskObjectColorScheme(i, MESSAGE_ELSE_TIME_BACKGROUND_GREEN, FONT_ELSE_TIME_WHITE);
							}
							setFeedbackIntoRespectiveFields(i);
							setTaskPanelHeight();
						}
						feedbackLabel.setText(feedbackDesc);
					}			
				}
			}
		}
		/**
		 * determines additional height that needs to be added to the task object to fit text overflow in alias field
		 * @param i
		 * @return additionalHeight
		 */
		private static int heightForAliasTextOverflow(int i) {
			int additionalHeight = ZERO;
			if(taskAlias.get(i) != null) {
				int charLength = taskAlias.get(i).length();

				if(charLength > NUM_CHAR_ALIAS_FIRST_LINE) {
					additionalHeight += HEIGHT_OF_ONE_LINE;
					charLength -= NUM_CHAR_FIRST_LINE;
					while (charLength>NUM_CHAR_ALIAS_LINE) {
						additionalHeight += HEIGHT_OF_ONE_LINE;
						charLength-=NUM_CHAR_LINE;
					}
				}
			}
			return additionalHeight;
		}
		/**
		 * determines additional height that needs to be added to the task object panel to fit text overflow in description field
		 * @param i
		 * @return additionalHeight
		 */
		private static int heightForDescriptionTextOverflow(int i) {
			int charLength = taskDesc.get(i).length();
			int additionalHeight = ZERO; 
			if (charLength > NUM_CHAR_FIRST_LINE) {
				additionalHeight += HEIGHT_OF_ONE_LINE;
				charLength -= NUM_CHAR_FIRST_LINE;
				while (charLength>NUM_CHAR_LINE) {
					additionalHeight += HEIGHT_OF_ONE_LINE;
					charLength-=NUM_CHAR_LINE;
				}
			}
			return additionalHeight;
		}
		/**
		 * updates panel height according to how many tasks are listed
		 */
		private static void setTaskPanelHeight() {
			heightChange += TASK_OBJECT_FRAME_HEIGHT;
			taskPanel.setPreferredSize(new Dimension(FRAME_WIDTH,heightChange));
		}
		/**
		 * sets date time 
		 * @param i
		 */
		private static void setFeedbackIntoRespectiveFields(int i) {
			dateTime.get(i).setText(taskDate.get(i));
			if (taskAlias.get(i) == null) {		
			} else {
				alias.get(i).setText(ALIAS + taskAlias.get(i));
			}	
			taskDescription.get(i).append(taskDesc.get(i));
		}
		/**
		 * sets color schemes for message panel, task description font, alias font and date time font
		 * @param i
		 * @param messageBackground
		 * @param fontColor
		 */
		private static void setTaskObjectColorScheme(int i, Color messageBackground, Color fontColor) {
			taskObjectPanel.get(i).setBackground(messageBackground);
			taskDescription.get(i).setForeground(fontColor);
			alias.get(i).setForeground(fontColor);
			dateTime.get(i).setForeground(fontColor);
		}
		/**
		 * set Jpanels to new Jpanels, re initialising variables
		 * @return numOfTask
		 */
		private static int initialiseFeedbackVariables() {
			int numOfTask = taskDesc.size();
			inputField.setText("");  
			taskObjectPanel = new ArrayList<JPanel>();
			dateTime = new ArrayList<JTextArea>();
			alias = new ArrayList<JTextArea>();
			taskDescription = new ArrayList<JTextArea>();
			heightChange=ZERO;
			return numOfTask;
		}
		/**
		 * Sets error message into feedback field and selects all text in input field
		 * @param feedbackDesc
		 */
		private static void errorProcessing(String feedbackDesc) {
			feedbackLabel.setText(feedbackDesc);
			inputField.selectAll();
		}
		/**
		 * removes all panels added to taskPanel
		 */
		private static void refreshTaskPanel() {
			taskPanel.removeAll();
			taskPanel.updateUI();
		}
		/**
		 * Function called when Help command is input by user
		 * @param desc
		 */
		private static void printHelp(String desc) {
			refreshTaskPanel(); 
			createHelpTextarea();
			help.append(desc);
			inputField.setText("");
		}
		/**
		 * Creates Text field when Help command is called
		 */
		private static void createHelpTextarea() {
			help = new JTextArea();
			help.setFont(new Font(PLUTO_COND_EXLIGHT_FONT, Font.PLAIN, HELP_FONT_SIZE));
			help.setBounds(HELP_X_OFFSET, HELP_Y_OFFSET, HELP_WIDTH, HELP_HEIGHT);
			help.setLineWrap(true);
			help.setWrapStyleWord(true);
			help.setOpaque(false);
			help.setEditable(false);
			taskPanel.add(help);
			taskPanel.revalidate();
			taskPanel.repaint();
		}
	}
}
