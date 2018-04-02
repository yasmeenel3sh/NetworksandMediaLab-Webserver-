
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.Scanner;

	import javax.swing.*;
	import javax.swing.text.*;

	public class Test
	{
	    private static String ENTER = "Enter";
	    static JButton enterButton;
	    static JButton one;
	    static JButton two;
	    static JButton three ;
	    static JButton four;
	    static JButton five;
	    static JButton six;
	    static JButton seven;
	    static JButton eight;
	    static JButton nine;
	    static JButton ten;
	   static String[] con = { "keep-alive","close" };
	   static JComboBox connection = new JComboBox(con);
static boolean closed=false;
	    static JButton close;
	    public static JTextArea output;
	    public static JTextField input;
	    public static String message="";
	    public static boolean sent;
	    static JFrame frame;
	    static JPanel panel;
	    public static String testString = "test";

	    public static void main(String... args)
	    {
	        try
	        {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	        createFrame("name");
	    }
	    public static void hold(){
	    	frame.dispose();
	    	
	    		
	    }
	    public static void createFrame(String name)
	    {
	        frame = new JFrame(name);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	        panel.setOpaque(true);
	        ButtonListener buttonListener = new ButtonListener();
	        output = new JTextArea(15, 50);
	        output.setWrapStyleWord(true);
	        output.setEditable(false);
	        JScrollPane scroller = new JScrollPane(output);
	        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	        JPanel inputpanel = new JPanel();
	        inputpanel.setLayout(new FlowLayout());
	       
	        
	        
	        one = new JButton("TXT1.txt");
	        one.setActionCommand("one");
	        one.addActionListener(buttonListener);
	        close = new JButton("Close");
	        close.setActionCommand("close");
	        close.addActionListener(buttonListener);
	        
	        two = new JButton("TXT2.txt");
	        two.setActionCommand("two");
	        two.addActionListener(buttonListener);
	        
	        three = new JButton("TXT3.txt");
	        three.setActionCommand("three");
	        three.addActionListener(buttonListener);
	        
	        four= new JButton("TXT4.txt");
	        four.setActionCommand("four");
	        four.addActionListener(buttonListener);
	        
	        five= new JButton("JPG1.jpg");
	        five.setActionCommand("five");
	        five.addActionListener(buttonListener);
	        
	        six= new JButton("JPG2.jpg");
	        six.setActionCommand("six");
	        six.addActionListener(buttonListener);
	        
	        seven = new JButton("PNG1.png");
	        seven.setActionCommand("seven");
	        seven.addActionListener(buttonListener);

	        eight = new JButton("PNG2.png");
	        eight.setActionCommand("eight");
	        eight.addActionListener(buttonListener);
	        
	        nine = new JButton("MP41.mp4");
	        nine.setActionCommand("nine");
	        nine.addActionListener(buttonListener);
	        
	        ten = new JButton("MP42.mp4");
	        ten.setActionCommand("ten");
	        ten.addActionListener(buttonListener);
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        // enterButton.setEnabled(false);
	        //input.setActionCommand(ENTER);
	        //input.addActionListener(buttonListener);
	        DefaultCaret caret = (DefaultCaret) output.getCaret();
	        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	        panel.add(scroller);
	       // inputpanel.add(input);
	       // inputpanel.add(enterButton);
	        
	        inputpanel.add(one);
	        inputpanel.add(two);
	        inputpanel.add(three);
	        inputpanel.add(four);
	        inputpanel.add(five);
	        inputpanel.add(six);
	        inputpanel.add(seven);
	        inputpanel.add(eight);
	        inputpanel.add(nine);
	        inputpanel.add(ten);
	        inputpanel.add(connection);
	        connection.setSelectedItem(0);
	        panel.add(inputpanel);
	        frame.getContentPane().add(BorderLayout.CENTER, panel);
	        frame.pack();
	        frame.setLocationByPlatform(true);
	        // Center of screen
	        // frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        frame.setResizable(false);
	       // input.requestFocus();
	        
	    }
	   

	    public static class ButtonListener implements ActionListener
	    {
         
	        public void actionPerformed(final ActionEvent ev)
	        {
	           
	                String cmd = ev.getActionCommand();
	                
	                if(cmd.equals("one")){
	                	message="TXT1.txt";
	                	sent=true;
	                	//output.append(message);		
	                }else  if(cmd.equals("two")){
	                	message="TXT2.txt";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("three")){
	                	message="TXT3.txt";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("four")){
	                	message="TXT4.txt";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("five")){
	                	message="JPG1.jpg";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("six")){
	                	message="JPG2.jpg";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("seven")){
	                	message="PNG1.png";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("eight")){
	                	message="PNG2.png";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("nine")){
	                	message="MP41.mp4";
	                	sent=true;
	                	//output.append(message);
	                }else  if(cmd.equals("ten")){
	                	message="MP42.mp4";
	                	sent=true;
	                	//output.append(message);
	                }
	                if(connection.getSelectedIndex()==1){
	                	sent=true;
	                	closed=true;
	                	one.setEnabled(false);;
	                	two.setEnabled(false);
	                	three.setEnabled(false);
	                	four.setEnabled(false);
	                	five.setEnabled(false);
	                	six.setEnabled(false);
	                	seven.setEnabled(false);
	                	eight.setEnabled(false);
	                	nine.setEnabled(false);
	                	ten.setEnabled(false);
	                	close.setEnabled(false);
	                }
	                
	        }
	    }
	}

