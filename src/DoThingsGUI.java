import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

//@author: John
public class DoThingsGUI extends JFrame  {
	private static final String DEFAULT_EXIT = "exit";
	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	private static final String STARTUP_COMMAND = "list";
	private static final String ERROR_CODE = "error";
	private static final String MARK_CODE = "marked";
	private static final String UNMARK_CODE = "unmarked";
	private static final String ALIAS = "@lias: ";
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int COMMAND_ENTER = KeyEvent.VK_ENTER;
	private static final int COMMAND_HIDE = NativeKeyEvent.VK_F8;
	private static final int COMMAND_SHIFT_WINDOW_LEFT = KeyEvent.VK_F11;
	private static final int COMMAND_SHIFT_WINDOW_RIGHT = KeyEvent.VK_F12;
	private static final int COMMAND_SHIFT_WINDOW_UP = KeyEvent.VK_F9;
	private static final int COMMAND_SHIFT_WINDOW_DOWN = KeyEvent.VK_F10;
	private static final int TASK_OBJECT_FRAME_HEIGHT = 72;
	private static final int FRAME_MOVEMENT = 10;		
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 700;
	private static int heightChange =0;
	
	private JPanel contentPane;
	private static JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private static JLabel feedbackLabel;
	private JScrollPane taskPanelScroll;
	private static JPanel taskPanel;
	private static ArrayList<JPanel> messagePanel;
	private static ArrayList<JTextArea> dateTime;
	private static ArrayList<JTextArea> alias;
	private static ArrayList<JTextArea> taskDescription;
	private static ArrayList<String> taskDesc;
	private static ArrayList<String> taskAlias;
	private static ArrayList<String> taskStatus;
	private GlobalKeyPress globalKeyPress; 
	private TriggerOnKeyReleased triggerOnKeyReleased;
	private TriggerOnMouseAction triggerOnMouseAction;
	
	
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
		triggerOnKeyReleased = new TriggerOnKeyReleased();
		triggerOnMouseAction = new TriggerOnMouseAction();
		
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
		
		inputField = new JTextField();
		inputField.setBounds(10, 57, FRAME_WIDTH-20, 33);
		inputField.setBorder(null);
		inputField.setBackground(new Color(153,204,255));
		inputField.setForeground(Color.BLACK);
		inputField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(inputField);
		
		feedbackLabel = new JLabel();
		feedbackLabel.setFocusable(false);
		feedbackLabel.setForeground(Color.GRAY);
		feedbackLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 14));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(0, 30, FRAME_WIDTH, 33);
		contentPane.add(feedbackLabel);
		
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

		textPanel = new JPanel();
		textPanel.setFocusable(false);
		textPanel.setBackground(new Color(255, 255, 255));
		textPanel.setBounds(0, 62, FRAME_WIDTH, 35);
		contentPane.add(textPanel);
			
		taskPanel = new JPanel();
		taskPanel.setFocusable(false);
		taskPanel.setBounds(0, 0, 171, 485);
		taskPanel.setOpaque(false);
		taskPanel.setBackground(new Color(255, 204, 51));
		contentPane.add(taskPanel);
		
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
		
		Content.getInfoOfTasks(STARTUP_COMMAND);
		feedbackLabel.setText(MESSAGE_STARTUP);
		
		setLocationRelativeTo(null);
		
		addWindowListener(globalKeyPress);	
		inputField.addKeyListener(triggerOnKeyReleased);
		addMouseListener(triggerOnMouseAction);
		headingLabel.addMouseMotionListener(triggerOnMouseAction);
	}
	
	// Class recognizes KeyEvents even if focus is not on window @author: Jiajie 
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
			//	SwingUtilities.invokeLater(new Runnable() {
				//	public void run() {
						//JOptionPane.showMessageDialog(null, "This will run on Swing's Event Dispatch Thread.");
						if (isVisible == true) {
							inputField.requestFocus();
							DoThingsGUI.this.setVisible(false);
							isVisible = false;
						}
						else {
							inputField.requestFocus();
							DoThingsGUI.this.setVisible(true);
							isVisible = true;
						}
					}
				//});
			//}
		}
		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0) {}
	}
	
	private class TriggerOnKeyReleased implements KeyListener{
		//@author: john
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			switch(key){
			case COMMAND_ENTER:
				String userInput = inputField.getText();
				Content.getInfoOfTasks(userInput);
				break;
			default:
				break;	
			}	
		}

		//@author: Jiajie
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
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
	// @author: John
	private static class Content {
		private static void createTaskObject(int change, int i) {
			messagePanel.add(new JPanel());
			messagePanel.get(i).setBounds(0, 0+change, FRAME_WIDTH, 70);
			messagePanel.get(i).setLayout(null);
			taskPanel.add(messagePanel.get(i));
			taskPanel.revalidate();
			taskPanel.repaint();

			dateTime.add(new JTextArea("12:40"));
			dateTime.get(i).setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
			dateTime.get(i).setBounds(10, 50, 200, 22);
			dateTime.get(i).setOpaque(false);
			dateTime.get(i).setEditable(false);
			messagePanel.get(i).add(dateTime.get(i));
			
			alias.add(new JTextArea());
			alias.get(i).setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
			alias.get(i).setBounds(250, 50, 88, 22);
			alias.get(i).setOpaque(false);
			alias.get(i).setEditable(false);
			messagePanel.get(i).add(alias.get(i));
			
			taskDescription.add(new JTextArea());
			taskDescription.get(i).setFont(new Font("Pluto Sans Light", Font.PLAIN, 18));
			taskDescription.get(i).setBounds(10,10, FRAME_WIDTH-25,60); //55 characters
			taskDescription.get(i).setLineWrap(true);
			taskDescription.get(i).setWrapStyleWord(true);
			taskDescription.get(i).setEditable(false);
			taskDescription.get(i).setOpaque(false);
			messagePanel.get(i).add(taskDescription.get(i));
		}
		private static void getInfoOfTasks(String userInput) {
			ArrayList<ArrayList<String>> result = MainLogic.runLogic(userInput);
			String feedbackType = result.get(FEEDBACK_TYPE).get(0);
			if(feedbackType.equals(DEFAULT_EXIT)) {
				System.exit(0);
			}
			String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
			taskPanel.removeAll();
			taskPanel.updateUI();

			if (feedbackType.equals(ERROR_CODE)) {
				feedbackLabel.setText(feedbackDesc);
				inputField.selectAll();
				//taskPanel.setPreferredSize(new Dimension(FRAME_WIDTH,0));
			} else {
				taskDesc = result.get(TASK_DESC);
				taskAlias = result.get(TASK_ALIAS);
				taskStatus = result.get(TASK_STATUS);
				int numOfTask = taskDesc.size();
				inputField.setText("");  
				
				messagePanel = new ArrayList<JPanel>();
				dateTime = new ArrayList<JTextArea>();
				alias = new ArrayList<JTextArea>();
				taskDescription = new ArrayList<JTextArea>();
				heightChange=0;
				for(int i=0; i<numOfTask; i++) {	
					//----- one task ----//
					Content.createTaskObject(heightChange, i);
			
					if (taskStatus.get(i).equals(MARK_CODE)) {
						//light grey
						messagePanel.get(i).setBackground(new Color(204, 204, 204));
						taskDescription.get(i).setForeground(new Color(153,153,153));
						alias.get(i).setForeground(new Color(153,153,153));
						dateTime.get(i).setForeground(new Color(153,153,153));
					} else if (taskStatus.get(i).equals(UNMARK_CODE)) {
						
						// if task due today
						//yellow
						messagePanel.get(i).setBackground(new Color(255,255,51));
						taskDescription.get(i).setForeground(new Color(102,102,102));
						dateTime.get(i).setForeground(new Color(102,102,102));
						alias.get(i).setForeground(new Color(102,102,102));
						
						// if task overdue
						// red
						/*
						messagePanel.get(i).setBackground(new Color(255, 153, 153));
						taskDescription.get(i).setForeground(Color.WHITE);
						alias.get(i).setForeground(Color.WHITE);
						dateTime.get(i).setForeground(Color.WHITE);
						// else
						//green
						messagePanel.get(i).setBackground(new Color(153, 204, 102));
						taskDescription.get(i).setForeground(Color.WHITE);
						alias.get(i).setForeground(Color.WHITE);
						dateTime.get(i).setForeground(Color.WHITE);
						 */
					}
					heightChange += TASK_OBJECT_FRAME_HEIGHT;
					dateTime.get(i).setText("12:09");
					if (taskAlias.get(i) == null) {		
					} else {
						alias.get(i).setText(ALIAS + taskAlias.get(i));
					}	
					taskDescription.get(i).append(taskDesc.get(i));
					taskPanel.setPreferredSize(new Dimension(FRAME_WIDTH,heightChange));
				}
				feedbackLabel.setText(feedbackDesc);
			}			
		}
	}
}
