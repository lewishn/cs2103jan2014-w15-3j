import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
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

//@author: John, Jiajie
@SuppressWarnings("serial")
public class DoThingsGUI extends JFrame  {
	private static final String DEFAULT_EXIT = "exit";
	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	private static final String STARTUP_COMMAND = "list";
	private static final String ERROR_CODE = "error";
	private static final String MARK_CODE = "marked";
	private static final String ALIAS = "@lias: ";
	private static final String OVERDUE = "overdue";
	private static final String DUE_TODAY = "today";
	private static final String FLOATING = "floating";
	private static final String DEFAULT_HELP = "help";
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
	private static final int TASK_OBJECT_FRAME_HEIGHT = 72;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 700;
	private static int heightChange =0;
	
	private JPanel contentPane;
	private static JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private static JLabel feedbackLabel;
	private JScrollPane taskPanelScroll;
	private JScrollBar verticalScrollBar;
	private static JPanel taskPanel;
	private static ArrayList<JPanel> messagePanel;
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
	private TriggerOnKeyAction triggerOnKeyReleased;
	private TriggerOnMouseAction triggerOnMouseAction;
	private static Image image;

	private int xCoordOfFrame;
	private int yCoordOfFrame;

	// Launch application
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
		
		globalKeyPress = new GlobalKeyPress(true);
		triggerOnKeyReleased = new TriggerOnKeyAction();
		triggerOnMouseAction = new TriggerOnMouseAction();
		image = Toolkit.getDefaultToolkit().getImage("Task.png");
		
		createContentPane();		
		createInputField();
		createFeedbackLabel();
		createHeadingLabel();
		createTextPanel();	
		createTaskPanel();
		createTaskPanelScroll();
		populateToDoListStartup();
		setLocationRelativeTo(null);	// GUI appears in the middle of screen by default
		
		setListeners();
		setVrticalScrollBarSettings();
	
	}

	private void setVrticalScrollBarSettings() {
		verticalScrollBar = taskPanelScroll.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(FRAME_SCROLL_SPEED);
	}
	private void setListeners() {
		addWindowListener(globalKeyPress);	
		inputField.addKeyListener(triggerOnKeyReleased);
		addMouseListener(triggerOnMouseAction);
		headingLabel.addMouseMotionListener(triggerOnMouseAction);
	}
	private void populateToDoListStartup() {
		ResponsiveContent.getInfoOfTasks(STARTUP_COMMAND);
		feedbackLabel.setText(MESSAGE_STARTUP);
	}
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
	private void createTaskPanel() {
		taskPanel = new JPanel();
		taskPanel.setFocusable(false);
		taskPanel.setBounds(0, 0, 171, 485);
		taskPanel.setOpaque(false);
		taskPanel.setBackground(new Color(255, 204, 51));
		contentPane.add(taskPanel);
	}
	private void createTextPanel() {
		textPanel = new JPanel();
		textPanel.setFocusable(false);
		textPanel.setBackground(new Color(255, 255, 255));
		textPanel.setBounds(0, 62, FRAME_WIDTH, 35);
		contentPane.add(textPanel);
	}
	private void createHeadingLabel() {
		headingLabel = new JLabel("Do-Things");
		headingLabel.setForeground(Color.GRAY);
		headingLabel.setVerticalAlignment(SwingConstants.TOP);
		headingLabel.setVerticalTextPosition(SwingConstants.TOP);
		headingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headingLabel.setOpaque(true);
		headingLabel.setBackground(new Color(255, 255, 255));
		headingLabel.setFont(new Font("Pluto Sans Cond Light", Font.PLAIN, 22));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(0, 0, 400, 63);
		contentPane.add(headingLabel);
	}
	private void createFeedbackLabel() {
		feedbackLabel = new JLabel();
		feedbackLabel.setFocusable(false);
		feedbackLabel.setForeground(Color.GRAY);
		feedbackLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 14));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(0, 30, FRAME_WIDTH, 33);
		contentPane.add(feedbackLabel);
	}
	private void createInputField() {
		inputField = new JTextField();
		inputField.setBounds(10, 57, FRAME_WIDTH-20, 33);
		inputField.setBorder(null);
		inputField.setBackground(new Color(153,204,255));
		inputField.setForeground(Color.BLACK);
		inputField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(inputField);
	}
	private void createContentPane() {
		setForeground(Color.BLACK);
		setFont(new Font("Consolas", Font.BOLD, 14));
		setTitle("Do-things");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(100, 25, FRAME_WIDTH, FRAME_HEIGHT);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setShape(new RoundRectangle2D.Double(0,0,FRAME_WIDTH,FRAME_HEIGHT, 20, 20));
		setIconImage(image);
	}
	
	// Class recognizes KeyEvents even if focus is not on window 
	// @author: Jiajie 
	private class GlobalKeyPress implements WindowListener, NativeKeyListener{

		Boolean isVisible = false;
		
		GlobalKeyPress(Boolean visible) {
			isVisible = visible;
		}
		
		@Override
		public void windowOpened(WindowEvent e) {
			//Initialize native hook.
            try {
                    GlobalScreen.registerNativeHook();
            }
            catch (NativeHookException ex) {
                    System.err.println("There was a problem registering the native hook.");
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();

                    System.exit(1);
            }

            GlobalScreen.getInstance().addNativeKeyListener(this);
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
            //Clean up the native hook.
            GlobalScreen.unregisterNativeHook();
            System.runFinalization();
            System.exit(0);
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
		public void nativeKeyReleased(NativeKeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == COMMAND_HIDE) {
						if (isVisible == true) {
							inputField.requestFocus();
							DoThingsGUI.this.setVisible(false);
							isVisible = false;
							hideToSytemTray();
							
						}
						else {
							inputField.requestFocus();
							DoThingsGUI.this.setVisible(true);
							isVisible = true;
							removeFromSystemTray();
						}
					}
		}
		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0) {}
	}
	
	// @Author: Jiajie 
	private class TriggerOnKeyAction implements KeyListener{
		
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			switch(key){
			case COMMAND_ENTER:
				String userInput = inputField.getText();
				ResponsiveContent.getInfoOfTasks(userInput);
				break;
			default:
				break;	
			}	
		}

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
		
		@Override
		public void mousePressed(MouseEvent arg0) {	 
	         // Get x,y and store them
	         xCoordOfFrame=arg0.getXOnScreen();
	         yCoordOfFrame=arg0.getYOnScreen();
		}
		
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
	// @author: A0097082Y
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
		private static final int ALIAS_FONT_SIZE = 12;
		private static final int DATE_TIME_FONT_SIZE = ALIAS_FONT_SIZE;
		private static final int TASK_DESCRIPTION_X_OFFSET = 10;
		private static final int TASK_DESCRIPTION_Y_OFFSET = 10;
		private static final int TASK_DESCRIPTION_WIDTH = 25;
		private static final int TASK_DESCRIPTION_HEIGHT = 38;
		private static final int ALIAS_X_OFFSET = 260;
		private static final int ALIAS_Y_OFFSET = 50;
		private static final int ALIAS_WIDTH = 88;
		private static final int ALIAS_LENGTH = 22;
		private static final int DATE_TIME_X_OFFSET = 10;
		private static final int DATE_TIME_Y_OFFSET = 50;
		private static final int DATE_TIME_WIDTH = 250;
		private static final int DATE_TIME_LENGTH = 22;
		private static final int MESSAGE_PANEL_LENGTH = 70;
		private static final int ZERO = 0;
		private static final String PLUTO_LIGHT_FONT = "Pluto Sans Light";
		private static final String PLUTO_COND_EXLIGHT_FONT = "Pluto Sans Cond ExLight";
		
		private static void createTaskObjects(int extension, int change, int i) {
			createMessagePanel(extension, change, i);
			createDateTimeField(extension, i);
			createAliasField(extension, i);
			createTaskDescription(extension, i);
		}
		private static void createTaskDescription(int extension, int i) {
			taskDescription.add(new JTextArea());	
			
			taskDescription.get(i).setFont(new Font(PLUTO_LIGHT_FONT, Font.PLAIN, TASK_DESCRIPTION_FONT_SIZE));	
			taskDescription.get(i).setBounds(TASK_DESCRIPTION_X_OFFSET,TASK_DESCRIPTION_Y_OFFSET, FRAME_WIDTH - TASK_DESCRIPTION_WIDTH,TASK_DESCRIPTION_HEIGHT + extension); //55 characters
			taskDescription.get(i).setLineWrap(true);
			taskDescription.get(i).setWrapStyleWord(true);
			taskDescription.get(i).setEditable(false);
			taskDescription.get(i).setOpaque(false);
			messagePanel.get(i).add(taskDescription.get(i));
		}
		private static void createAliasField(int extension,int i) {
			alias.add(new JTextArea());
			alias.get(i).setFont(new Font(PLUTO_COND_EXLIGHT_FONT, Font.PLAIN, ALIAS_FONT_SIZE));
			alias.get(i).setBounds(ALIAS_X_OFFSET, ALIAS_Y_OFFSET + extension, ALIAS_WIDTH, ALIAS_LENGTH);
			alias.get(i).setOpaque(false);
			alias.get(i).setEditable(false);
			messagePanel.get(i).add(alias.get(i));
		}
		private static void createDateTimeField(int extension ,int i) {
			dateTime.add(new JTextArea());
			dateTime.get(i).setFont(new Font(PLUTO_COND_EXLIGHT_FONT, Font.PLAIN, DATE_TIME_FONT_SIZE));
			dateTime.get(i).setBounds(DATE_TIME_X_OFFSET, DATE_TIME_Y_OFFSET + extension, DATE_TIME_WIDTH, DATE_TIME_LENGTH);
			dateTime.get(i).setOpaque(false);
			dateTime.get(i).setEditable(false);
			messagePanel.get(i).add(dateTime.get(i));
		}
		private static void createMessagePanel(int extension, int change, int i) {
			messagePanel.add(new JPanel());
			messagePanel.get(i).setBounds(ZERO, ZERO + change, FRAME_WIDTH, MESSAGE_PANEL_LENGTH + extension);
			messagePanel.get(i).setLayout(null);
			taskPanel.add(messagePanel.get(i));
			taskPanel.revalidate();
			taskPanel.repaint();
		}
		private static void getInfoOfTasks(String userInput) {
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
					printHelp();
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
							// Checks for overflow of description text and extends the message panel to fit.
							int overflowExtension = lengthForTextOverflow(i);
							createTaskObjects(overflowExtension, heightChange, i);
							heightChange += overflowExtension;
							
							// Select color scheme of taskObject
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
							// Update text fields and extend taskPanel to fit i number of message panels
							setFeedbackIntoRespectiveFields(i);
							setTaskPanelHeight();
						}
						// Finally feedback to user what has been done
						feedbackLabel.setText(feedbackDesc);
					}			
				}
			}
		}
		private static int lengthForTextOverflow(int i) {
			int length = taskDesc.get(i).length();
			int additionalLength = ZERO;
			if (length > 55) {
				 while (length>34) {
					 additionalLength += 19;
					 length-=55;
				 }
			}
			return additionalLength;
		}
		private static void setTaskPanelHeight() {
			heightChange += TASK_OBJECT_FRAME_HEIGHT;
			taskPanel.setPreferredSize(new Dimension(FRAME_WIDTH,heightChange));
		}
		private static void setFeedbackIntoRespectiveFields(int i) {
			dateTime.get(i).setText(taskDate.get(i));
			if (taskAlias.get(i) == null) {		
			} else {
				alias.get(i).setText(ALIAS + taskAlias.get(i));
			}	
			taskDescription.get(i).append(taskDesc.get(i));
		}
		private static void setTaskObjectColorScheme(int i, Color messageBackground, Color fontColor) {
			messagePanel.get(i).setBackground(messageBackground);
			taskDescription.get(i).setForeground(fontColor);
			alias.get(i).setForeground(fontColor);
			dateTime.get(i).setForeground(fontColor);
		}
		private static int initialiseFeedbackVariables() {
			int numOfTask = taskDesc.size();
			inputField.setText("");  
			messagePanel = new ArrayList<JPanel>();
			dateTime = new ArrayList<JTextArea>();
			alias = new ArrayList<JTextArea>();
			taskDescription = new ArrayList<JTextArea>();
			heightChange=ZERO;
			return numOfTask;
		}
		private static void errorProcessing(String feedbackDesc) {
			feedbackLabel.setText(feedbackDesc);
			inputField.selectAll();
		}
		private static void refreshTaskPanel() {
			taskPanel.removeAll();
			taskPanel.updateUI();
		}
		
		private static void printHelp() {
		}
	}
	private void hideToSytemTray(){
        try{
        	PopupMenu popup = new PopupMenu();
        	tray=SystemTray.getSystemTray();   
            trayIcon=new TrayIcon(image, "Do-Things", popup);
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
        }catch(Exception e){
            System.out.println("Unable to set System Tray");
        }
	}
	
	private void removeFromSystemTray(){
		tray.remove(trayIcon);
	}
}
