import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
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


public class DoThingsGUI extends JFrame  {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	private static final int COMMAND_ENTER = KeyEvent.VK_ENTER;
	private static final int COMMAND_HIDE = NativeKeyEvent.VK_F8;
	private static final int COMMAND_SHIFT_WINDOW_LEFT = KeyEvent.VK_F11;
	private static final int COMMAND_SHIFT_WINDOW_RIGHT = KeyEvent.VK_F12;
	private static final int COMMAND_SHIFT_WINDOW_UP = KeyEvent.VK_F9;
	private static final int COMMAND_SHIFT_WINDOW_DOWN = KeyEvent.VK_F10;
	private static final int FRAME_MOVEMENT = 10;
	private static final int TASK_OBJECT_FRAME_HEIGHT = 72;
			
	private JPanel contentPane;
	private JTextField inputField;
	private JLabel headingLabel;
	private JPanel textPanel;
	private JLabel feedbackLabel;
	private ArrayList<String>input = new ArrayList<String>();
	private JScrollPane taskPanelScroll;
	private JPanel taskPanel;
	private int TEXTAREAHEIGHT = 120;
	private int CHARPERLINE = 20;
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
		setBounds(100, 25, 400, 700);
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
		inputField.setBounds(10, 57, 380, 33);
		inputField.setBorder(null);
		inputField.setBackground(new Color(153,204,255));
		inputField.setForeground(Color.BLACK);
		inputField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(inputField);
		
		feedbackLabel = new JLabel("");
		feedbackLabel.setForeground(Color.GRAY);
		feedbackLabel.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 14));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(0, 30, 400, 33);
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
		textPanel.setBackground(new Color(255, 255, 255));
		textPanel.setBounds(0, 62, 400, 35);
		contentPane.add(textPanel);
			
		taskPanel = new JPanel();
		taskPanel.setBounds(0, 0, 171, 485);
		taskPanel.setOpaque(false);
		taskPanel.setBackground(new Color(255, 204, 51));
		contentPane.add(taskPanel);
		
		taskPanelScroll = new JScrollPane(taskPanel);
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
		taskPanelScroll.setBounds(0, 97, 400, 603);
		contentPane.add(taskPanelScroll);
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(SystemColor.windowBorder);
		backgroundPanel.setBounds(0, 0, 400, 600);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		
		setLocationRelativeTo(null);
		
		addWindowListener(globalKeyPress);	
		inputField.addKeyListener(triggerOnKeyReleased);
		addMouseListener(triggerOnMouseAction);
		headingLabel.addMouseMotionListener(triggerOnMouseAction);
	}
	
	// Class recognizes KeyEvents even if focus is not on window
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
		
		@Override
		public void keyReleased(KeyEvent e) {
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
			case COMMAND_ENTER:
				String userInput = inputField.getText();
				input.add(userInput);
				inputField.setText("");  
				JPanel messagePanel[] = new JPanel[input.size()];
				JTextArea startDate[] = new JTextArea[input.size()];
				JTextArea startTime[] = new JTextArea[input.size()];
				JTextArea endDate[] = new JTextArea[input.size()];
				JTextArea endTime[] = new JTextArea[input.size()];
				JTextArea taskDescription[] = new JTextArea[input.size()];
				int change=0;
				for(int i=0; i<input.size(); i++) {	
					//----- one task ----//
					messagePanel[i] = new JPanel();
					messagePanel[i].setBounds(0, 0+change, 400, 70);
					messagePanel[i].setLayout(null);
					taskPanel.add(messagePanel[i]);
					taskPanel.revalidate();
					taskPanel.repaint();

					startTime[i] = new JTextArea("04:20");
					startTime[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
					startTime[i].setBounds( 85, 50, 50, 22);
					startTime[i].setOpaque(false);
					messagePanel[i].add(startTime[i]);
					
					startDate[i] = new JTextArea("22/04/2014");
					startDate[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
					startDate[i].setBounds(10, 50, 88, 27);
					startDate[i].setOpaque(false);
					messagePanel[i].add(startDate[i]);

					endTime[i] = new JTextArea("18:20");
					endTime[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
					endTime[i].setBounds(225, 50, 88, 22);
					endTime[i].setOpaque(false);
					messagePanel[i].add(endTime[i]);
					
					endDate[i] = new JTextArea("22/04/2014");
					endDate[i].setFont(new Font("Pluto Sans Cond ExLight", Font.PLAIN, 12));
					endDate[i].setBounds(150, 50, 88, 27);
					endDate[i].setOpaque(false);
					messagePanel[i].add(endDate[i]);
					taskDescription[i] = new JTextArea();
					taskDescription[i].setFont(new Font("Pluto Sans Medium", Font.PLAIN, 18));
					taskDescription[i].setBounds(10,10, 375,60); //55 characters
					taskDescription[i].setLineWrap(true);
					taskDescription[i].setWrapStyleWord(true);
					taskDescription[i].setEditable(false);
					taskDescription[i].setOpaque(false);
					messagePanel[i].add(taskDescription[i]);
					
					if(i%4==0) {
						//green
						messagePanel[i].setBackground(new Color(153, 204, 102));
						taskDescription[i].setForeground(Color.WHITE);
						startDate[i].setForeground(Color.WHITE);
						startTime[i].setForeground(Color.WHITE);
						endDate[i].setForeground(Color.WHITE);
						endTime[i].setForeground(Color.WHITE);
					} else if(i%4==1) {
						//yellow
						messagePanel[i].setBackground(new Color(255,255,51));
						taskDescription[i].setForeground(new Color(102,102,102));
						startDate[i].setForeground(new Color(102,102,102));
						startTime[i].setForeground(new Color(102,102,102));
						endDate[i].setForeground(new Color(102,102,102));
						endTime[i].setForeground(new Color(102,102,102));
					} else if(i%4==2) {
						// red
						messagePanel[i].setBackground(new Color(255, 153, 153));
						taskDescription[i].setForeground(Color.WHITE);
						startDate[i].setForeground(Color.WHITE);
						startTime[i].setForeground(Color.WHITE);
						endDate[i].setForeground(Color.WHITE);
						endTime[i].setForeground(Color.WHITE);
					} else {
						//light grey
						messagePanel[i].setBackground(new Color(204, 204, 204));
						taskDescription[i].setForeground(new Color(153,153,153));
						startDate[i].setForeground(new Color(153,153,153));
						startTime[i].setForeground(new Color(153,153,153));
						endDate[i].setForeground(new Color(153,153,153));
						endTime[i].setForeground(new Color(153,153,153));
					}
					
					change += TASK_OBJECT_FRAME_HEIGHT;
					taskDescription[i].append(input.get(i));
					taskPanel.setPreferredSize(new Dimension(400,change));
				}
				//String text = textField.getText();
				//taskDescription.append(text);
				break;
			default:
				break;	
			}	
		}

		@Override
		public void keyPressed(KeyEvent e) {}
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
}
