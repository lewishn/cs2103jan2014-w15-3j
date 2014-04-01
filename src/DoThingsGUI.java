import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class DoThingsGUI extends JFrame {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	private static final String MESSAGE_COMMAND = "Please enter a command: ";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_HIDE = "hide";
	
	private JPanel contentPane;
	private TextField textField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
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
	public DoThingsGUI() {
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
		
		textField = new TextField();
		textField.setBackground(new Color(255, 102, 51));
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 16));
		contentPane.add(textField, BorderLayout.SOUTH);
		
		textArea = new JTextArea();
		textArea.setFocusTraversalKeysEnabled(false);
		textArea.setFocusable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 16));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setBackground(new Color(102, 102, 102));
		textArea.setEditable(false);
		//textArea.setWrapStyleWord(true);
		JScrollPane textAreaJScrollPane = new JScrollPane(textArea);
		textAreaJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		contentPane.add(textAreaJScrollPane, BorderLayout.CENTER);
		
			
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				int key = arg0.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){
					String text = textField.getText();
					String doThingsFeedback = DoThings.readCommand(text);
					
					if(doThingsFeedback.equalsIgnoreCase(COMMAND_EXIT)){
						System.exit(0);
					} else if(doThingsFeedback.equalsIgnoreCase(COMMAND_HIDE)) {
						
					} else if(doThingsFeedback.contains("ERROR")) {
						textField.selectAll();
						textArea.append(doThingsFeedback);
					}else{ 
						textArea.append(doThingsFeedback);
						textField.setText("");  
					}
				}
			}
		});
		
	}
}
