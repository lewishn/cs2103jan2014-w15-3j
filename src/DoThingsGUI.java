import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class DoThingsGUI extends JFrame  {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	private static final String MESSAGE_COMMAND = "Please enter a command: ";
	private static final String COMMAND_EXIT = "exit";
	private static final int COMMAND_HIDE = NativeKeyEvent.VK_F8;
	private static final int COMMAND_SHIFT_WINDOW_LEFT = KeyEvent.VK_F11;
	private static final int COMMAND_SHIFT_WINDOW_RIGHT = KeyEvent.VK_F12;
	private static final int COMMAND_SHIFT_WINDOW_UP = KeyEvent.VK_F9;
	private static final int COMMAND_SHIFT_WINDOW_DOWN = KeyEvent.VK_F10;
			
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private GlobalKeyPress globalKeyPress; // To toggle visibility of frame upon pressing hotkey
	private TriggerOnKeyReleased triggerOnKeyReleased;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.textArea.setText(MESSAGE_STARTUP);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DoThingsGUI () {
		
		globalKeyPress = new GlobalKeyPress(true);
		triggerOnKeyReleased = new TriggerOnKeyReleased();
		
		setUndecorated(true);
		setForeground(Color.BLACK);
		setFont(new Font("Consolas", Font.BOLD, 14));
		setTitle("Do-things");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(100, 100, 750, 500);
		
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBackground(new Color(255, 102, 51));
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		
		textArea = new JTextArea();
		textArea.setBorder(null);
		textArea.setLineWrap(true);
		textArea.setFocusTraversalKeysEnabled(false);
		textArea.setFocusable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 20));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setBackground(new Color(102, 102, 102));
		textArea.setEditable(false);
	
		JScrollPane textAreaJScrollPane = new JScrollPane(textArea);
		textAreaJScrollPane.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		textAreaJScrollPane.setBorder(null);
		textAreaJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		contentPane.add(textField, BorderLayout.SOUTH);
		contentPane.add(textAreaJScrollPane, BorderLayout.CENTER);
		
		addWindowListener(globalKeyPress);	
		textField.addKeyListener(triggerOnKeyReleased);
	}
	
	// Class recognizes KeyEvents even if focus is not on window
	private class GlobalKeyPress implements WindowListener, NativeKeyListener{

		Boolean isVisible = false;
		
		GlobalKeyPress(Boolean visible) {
			isVisible = visible;
		}
		
		@Override
		public void windowOpened(WindowEvent e) {
			//Initialise native hook.
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
							textField.requestFocus();
							DoThingsGUI.this.setVisible(false);
							isVisible = false;
						}
						else {
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
				setLocation(getX(),getY()-50);
				break;
			case COMMAND_SHIFT_WINDOW_DOWN:
				setLocation(getX(),getY()+50);
				break;
			case COMMAND_SHIFT_WINDOW_LEFT:
				setLocation(getX()-50,getY());
				break;
			case COMMAND_SHIFT_WINDOW_RIGHT:
				setLocation(getX()+50,getY());
				break;
			case KeyEvent.VK_ENTER:
				String text = textField.getText();
				Feedback feedback = DoThings.readCommand(text);
					
				if(feedback.getExitFlag()){
					System.exit(0);
				}else if(feedback.toString().contains("Invalid")) {
					textField.selectAll();
					textArea.append(feedback.toString());
				}else{ 
					textArea.append(feedback.toString());
					textField.setText("");  
				}
			default:
				break;	
			}	
		}

		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}


	}
}
