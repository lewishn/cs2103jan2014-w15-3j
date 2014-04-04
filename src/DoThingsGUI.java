import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.border.LineBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;


public class DoThingsGUI extends JFrame {

	private static final String MESSAGE_STARTUP = "Get ready to Do Things!\n";
	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoThingsGUI frame = new DoThingsGUI();
					frame.setVisible(true);
					frame.feedbackLabel.setText(MESSAGE_STARTUP);  
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
		setForeground(Color.BLACK);
		setFont(new Font("Consolas", Font.BOLD, 14));
		setTitle("Do-things");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setBounds(100, 100, 400, 600);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setToolTipText("");
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		headingLabel.setBackground(new Color(255, 255, 51));
		headingLabel.setFont(new Font("Pluto Sans Medium", Font.PLAIN, 22));
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(0, 0, 400, 63);
		contentPane.add(headingLabel);
		
		inputField = new JTextField();
		inputField.setOpaque(false);
		inputField.setBounds(10, 64, 380, 33);
		inputField.setBorder(null);
		inputField.setBackground(new Color(255, 255, 51));
		inputField.setForeground(Color.LIGHT_GRAY);
		inputField.setFont(new Font("Pluto Sans ExtraLight", Font.PLAIN, 23));
		contentPane.add(inputField);

		textPanel = new JPanel();
		textPanel.setBackground(Color.DARK_GRAY);
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
		taskPanelScroll.setBounds(0, 97, 400, 503);
		contentPane.add(taskPanelScroll);
		

		
		
		
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(SystemColor.windowBorder);
		backgroundPanel.setBounds(0, 0, 400, 600);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
			
		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				int key = arg0.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){
					
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
						messagePanel[i].setBackground(new Color(102, 153, 0));
						//messagePanel[i].setBackground(new Color(255, 255, 255));
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
						taskDescription[i].setFont(new Font("Pluto Sans Thin", Font.PLAIN, 18));
						taskDescription[i].setBounds(10,10, 380,60); //55 characters
						taskDescription[i].setLineWrap(true);
						taskDescription[i].setWrapStyleWord(true);
						taskDescription[i].setEditable(false);
						taskDescription[i].setOpaque(false);
						taskDescription[i].setForeground(Color.WHITE);
						messagePanel[i].add(taskDescription[i]);
						//----------//
						change += 70;
						taskDescription[i].append(input.get(i));
						taskPanel.setPreferredSize(new Dimension(320,change));
					}
					//String text = textField.getText();
					//taskDescription.append(text);
					/*Feedback feedback = DoThings.readCommand(text);
					
					
					if(feedback.getExitFlag()){
						System.exit(0);
					} */
					/*
					else if(doThingsFeedback.equalsIgnoreCase(COMMAND_HIDE)) {
						
					} else if(doThingsFeedback.contains("ERROR")) {
						textField.selectAll();
						textArea.append(doThingsFeedback);
					}
					*//*
					else{ 
						TaskDescription.append(feedback.toString());
						textField.setText("");  
					}
					*/
				}
			}
		});
		
	}
}
