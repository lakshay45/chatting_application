package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.border.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

class Client implements ActionListener
{
	static JFrame f1=new JFrame();
	JPanel jp;
	JTextField jf;
	JButton jb,jb2,jb3;
	static JPanel ja;
	static ServerSocket ss;
	static Socket sc;
	static DataInputStream din;
	static DataOutputStream dout;
	Boolean typing;				//by default Boolean is taken as false
	static Box vertical=Box.createVerticalBox();
	
	public Client()
	{
		jp=new JPanel();
		jp.setLayout(null);
		jp.setBounds(0,0,400,70);
		jp.setBackground(new Color(7,90,84));
		f1.add(jp);

		ImageIcon i1=new ImageIcon("chatting/application/images/back.jpg");
		Image i2=i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);	//length,width
		ImageIcon i3=new ImageIcon(i2);
		
		JLabel j1=new JLabel(i3);
		j1.setBounds(5,17,30,30);		//x,y,length,width
		jp.add(j1);

		j1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me)
			{	System.exit(0);	}
		});

		ImageIcon i4=new ImageIcon("chatting/application/images/profile.png");
		Image i5=i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);	//length,width
		ImageIcon i6=new ImageIcon(i5);
		
		JLabel j2=new JLabel(i6);
		j2.setBounds(40,5,60,60);		//at x=5 arrow was there +30 its length ,so now x>35
		jp.add(j2);

		ImageIcon i7=new ImageIcon("chatting/application/images/phone.png");
		Image i8=i7.getImage().getScaledInstance(25,23,Image.SCALE_DEFAULT);	//length,width
		ImageIcon i9=new ImageIcon(i8);
		
		JLabel j3=new JLabel(i9);
		j3.setBounds(315,25,25,23);	
		jp.add(j3);

		ImageIcon i10=new ImageIcon("chatting/application/images/video.png");
		Image i11=i10.getImage().getScaledInstance(27,25,Image.SCALE_DEFAULT);	//length,width
		ImageIcon i12=new ImageIcon(i11);
		
		JLabel j4=new JLabel(i12);
		j4.setBounds(250,5,60,60);	
		jp.add(j4);

		ImageIcon i13=new ImageIcon("chatting/application/images/menu.png");
		Image i14=i13.getImage().getScaledInstance(6,30,Image.SCALE_DEFAULT);	//length,width
		ImageIcon i15=new ImageIcon(i14);
		
		JLabel j5=new JLabel(i15);
		j5.setBounds(365,20,6,30);	
		jp.add(j5);

		JLabel j6=new JLabel("Active now");					//Active now
		j6.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		j6.setForeground(Color.WHITE);
		j6.setBounds(110,38,100,20);
		jp.add(j6);

		JLabel j7=new JLabel("Lakshay");
		j7.setFont(new Font("SAN_SERIF",Font.BOLD,20));
		j7.setForeground(Color.WHITE);
		j7.setBounds(110,16,100,20);
		jp.add(j7);

//-------------------------------------------------------------------------------------------------------------------------------------

		jf=new JTextField();
		jf.setBounds(5,563,290,30);
		jf.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		f1.add(jf);

		Timer t=new Timer(1,new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				if(!typing)
				{
					j6.setText("Active now");
				}
			}
		});
		
		t.setInitialDelay(1000);

		jf.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke)
			{
				j6.setText("typing...");
				t.stop();
				typing=true;
			}
			public void keyReleased(KeyEvent ke)
			{
				typing=false;
				if(!t.isRunning())
				{
					t.start();
				}
			}
		});

		ImageIcon i16=new ImageIcon("chatting/application/images/send.png");
		Image i17=i16.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
		ImageIcon i18=new ImageIcon(i17);
		jb=new JButton(i18);
		jb.setBounds(365,563,35,30);
		f1.add(jb);
		jb.addActionListener(this);

		ImageIcon i19=new ImageIcon("chatting/application/images/attach.png");
		Image i20=i19.getImage().getScaledInstance(30,22,Image.SCALE_DEFAULT);
		ImageIcon i21=new ImageIcon(i20);
		jb2=new JButton(i21);
		jb2.setBounds(297,566,30,22);
		f1.add(jb2);

		ImageIcon i22=new ImageIcon("chatting/application/images/camera.png");
		Image i23=i22.getImage().getScaledInstance(30,22,Image.SCALE_DEFAULT);
		ImageIcon i24=new ImageIcon(i23);
		jb3=new JButton(i24);
		jb3.setBounds(330,566,30,22);
		f1.add(jb3);

		ja=new JPanel();
		ja.setBounds(5,72,395,484);
		ja.setBackground(new Color(245,245,245));
		ja.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		/*ja.setEditable(false);
		ja.setLineWrap(true);
		ja.setWrapStyleWord(true);*/
		f1.add(ja);

//-------------------------------------------------------------------------------------------------------------------------------------
	
		f1.setLayout(null);
		f1.setSize(400,600);
		f1.setLocation(1000,200);
		f1.getContentPane().setBackground(new Color(245,245,245));		//will set full background to grey
		f1.setUndecorated(true);		//will remove the top header bar
		f1.setVisible(true);
		
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		jf.addKeyListener(new KeyAdapter() {
       		 
       		 public void keyPressed(KeyEvent e) {
       		     if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try{
					String s=jf.getText();
					//ja.setText(ja.getText()+"\n\t\t\t"+s);
			
					JPanel p2=formatLabel(s);
					
					ja.setLayout(new BorderLayout());
	
					JPanel right=new JPanel(new BorderLayout());
					right.add(p2,BorderLayout.LINE_END);	
					vertical.add(right);
	
					ja.add(vertical,BorderLayout.PAGE_START);
					jf.setText("");
			
					dout.writeUTF(s);
				}catch(Exception ec){}
       		     }
       		 }
    		});
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try{
			String s=jf.getText();
			//ja.setText(ja.getText()+"\n\t\t\t"+s);
			
			JPanel p2=formatLabel(s);
			
			//ja.setLayout(new BorderLayout());

			JPanel right=new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));    //this is used to create spacing b/w lines,15 is ht. space 

			ja.add(vertical,BorderLayout.PAGE_START);
			dout.writeUTF(s);
			jf.setText("");
		}catch(Exception e){
				System.out.println(e);
		}
	}
	public static JPanel formatLabel(String out)
	{
		JPanel jp=new JPanel();
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));

		JLabel l1=new JLabel("<html><p style=\"width : 150px\">"+out+"</p></html>");
		l1.setBackground(new Color(0,204,153));
		l1.setOpaque(true);
		l1.setBorder(new EmptyBorder(4,4,4,15));

		Calendar c=Calendar.getInstance();
		SimpleDateFormat sf=new SimpleDateFormat("HH:mm");

		JLabel l2=new JLabel();
		l2.setText(sf.format(c.getTime()));		

		jp.add(l1);
		jp.add(l2);
		return jp;
	}
	public static void main(String[] args)
	{
		new Client().f1.setVisible(true);
		String message="";
			try{
				sc=new Socket("127.0.0.1",6001);
				
					din=new DataInputStream(sc.getInputStream());
					dout=new DataOutputStream(sc.getOutputStream());
					while(true)
					{
						ja.setLayout(new BorderLayout());
						message=din.readUTF();
						JPanel jp=formatLabel(message);
						
						JPanel left=new JPanel(new BorderLayout());
						left.add(jp,BorderLayout.LINE_START);

						vertical.add(left);

						ja.add(vertical,BorderLayout.PAGE_START);

						f1.validate();
					}
				
			}catch(Exception e){}
	}
}