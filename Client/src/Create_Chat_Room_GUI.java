import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Create_Chat_Room_GUI extends JFrame implements iProtocol{
	
	private JLabel lblRoomName;
	private JLabel lblPeople;
	private JLabel lblCategory;
	private JLabel lblTitle;
	private JCheckBox cbPW;
	
	private JTextField tf1;
	private JTextField tf2;
	
	private String People[] = { "1명", "2명", "3명", "4명", "5명" ,"10명", "15명", "20명" };
	private String Category[] = {"요리","스포츠","게임","음악"};
	
	private JComboBox<String> combo1;
	private JComboBox<String> combo2;
	
	private JButton btn_OK;
	private JButton btn_Cancel;
	private JButton btn_Exit;
	private String king;
	private boolean isBlock;
	private ClientData cData;
	private JPanel pnlCenter;
	private Color btnColor;
	private Color bgColor;
	private Font font;
	private JPanel pnlPW;
	private Point ptFirst;
	private Waitting_Room_GUI owner;

	public Create_Chat_Room_GUI(ClientData cData, Waitting_Room_GUI owner ){
		this.cData =cData;
		this.owner = owner;
		init();
		setDisplay();
		showFrame();
		addActionListener();
	}
	
	private void init(){
		isBlock =true;
		btnColor = new Color(0xE59BB9); //진한분홍
		bgColor = new Color(0xFFFFFF); // 하얀색
		font = new Font("HY나무B", Font.PLAIN, 15);
		lblRoomName = new JLabel("방이름",JLabel.CENTER);
		lblRoomName.setFont(font);
		lblPeople = new JLabel("채팅방 인원",JLabel.CENTER);
		lblPeople.setFont(font);
		lblCategory = new JLabel("카테고리",JLabel.CENTER);
		lblCategory.setFont(font);
		cbPW = new JCheckBox("비밀번호",true);
		cbPW.setFont(font);
		cbPW.setBackground(bgColor);
		lblTitle = new JLabel("방 설정", JLabel.CENTER);
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.WHITE);
		
		pnlPW = new JPanel(new FlowLayout());
		pnlPW.setBackground(bgColor);

		
		tf1 = new JTextField(12);
		tf1.setFont(font);
		tf2 = new JTextField(12);
		tf2.setFont(font);
		combo1 = new JComboBox<String>(People);
		combo1.setBackground(Color.WHITE);
		combo1.setFont(font);

		combo2 = new JComboBox<String>(Category);
		combo2.setBackground(Color.WHITE);
		combo2.setFont(font);
		btn_OK = new JButton("확인");
		btn_OK.setPreferredSize(new Dimension(70, 30));
		btn_OK.setFont(font);
		btn_OK.setForeground(Color.WHITE);
		btn_OK.setBackground(btnColor);
		btn_OK.setBorder(new LineBorder(Color.WHITE, 1));

		btn_Cancel = new JButton("취소");
		btn_Cancel.setPreferredSize(new Dimension(70, 30));
		btn_Cancel.setFont(font);
		btn_Cancel.setForeground(Color.WHITE);
		btn_Cancel.setBackground(btnColor);
		btn_Cancel.setBorder(new LineBorder(Color.WHITE, 1));

		btn_Exit = new JButton(" X ");
		btn_Exit.setPreferredSize(new Dimension(50, 25));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
		btn_Exit.setBorder(new LineBorder(Color.WHITE, 1));
		
	}
	private void setDisplay(){
		JPanel pnlTotal = new JPanel(new BorderLayout());

		pnlPW.add(cbPW);
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btn_Exit, BorderLayout.EAST);
		pnlTop.setBorder(new EmptyBorder(3, 10, 5, 10));
		pnlTop.setBackground(btnColor);

		JPanel pnlCenter_Cover = new JPanel();
		pnlCenter_Cover.setPreferredSize(new Dimension(100, 30));

		pnlCenter = new JPanel(new GridLayout(4, 2));
		pnlCenter.add(lblRoomName);
		pnlCenter.add(tf1);
		pnlCenter.add(pnlPW);
		pnlCenter.add(tf2);
		pnlCenter.add(lblPeople);
		pnlCenter.add(combo1);
		pnlCenter.add(lblCategory);
		pnlCenter.add(combo2);

		pnlCenter.setBackground(bgColor);
		pnlCenter_Cover.add(pnlCenter);
		pnlCenter_Cover.setBackground(bgColor);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn_OK);
		pnlSouth.add(btn_Cancel);
		pnlSouth.setBackground(bgColor);
		pnlSouth.setBorder(new EmptyBorder(0, 0, 5, 0));

		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter_Cover, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);

		pnlTotal.setBorder(new LineBorder(btnColor, 5));

		add(pnlTotal, BorderLayout.CENTER);
		
	}
	
	private void addActionListener() {
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == btn_OK) {
					String title = tf1.getText();
					int idx = combo1.getSelectedItem().toString().indexOf("명");
					String sub = combo1.getSelectedItem().toString()
							.substring(0, idx);
					Integer max_User = Integer.valueOf(sub);
					String category = combo2.getSelectedItem().toString();
					String king = cData.getMyID();
					String password = tf2.getText();

					if (title.trim().length() == 0) {
						showMsg("생성할 방제목이 비어있습니다.");
					} else if (cbPW.isSelected()
							&& password.trim().length() == 0) {
						showMsg("비밀번호란이 비어있습니다.");
					} else  {
			
						if (isBlock == true) {

							Chat_Room room = new Chat_Room(title, king,
									category, max_User, password, isBlock);
							SendData sd = new SendData(SETUP_CHAT_ROOM, room);
							try {
								cData.sendTarget(sd);
							} catch (Exception e1) {
							}
						} else {
							Chat_Room room = new Chat_Room(title, king,
									category, max_User, isBlock);
							SendData sd = new SendData(SETUP_CHAT_ROOM, room);
							try {
								cData.sendTarget(sd);
							} catch (Exception e1) {
							}
						}
						dispose();
					}
				} else if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				}
			}
		};
		cbPW.addItemListener(new ItemListener(){
	        @Override
	        public void itemStateChanged(ItemEvent e) {
	            if(e.getStateChange()==ItemEvent.SELECTED){
	                tf2.setEnabled(true);
	            	isBlock =true;
	            }else{
	            	tf2.setEnabled(false);
	            	isBlock =false;
	            }
	        }
	    });
		btn_OK.addActionListener(aListener);
		btn_Cancel.addActionListener(aListener);
		btn_Exit.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	private class MyMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			Point loc = e.getLocationOnScreen();
			loc.x -= ptFirst.x;
			loc.y -= ptFirst.y;

			setLocation(loc);// 프레임창 움직이기
		}
	}

	private class MyMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			ptFirst = e.getPoint();
		}
	}

	private void showFrame(){
		setSize(350, 245);
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}

//	public static void main(String[] args) {
//		new Create_Chat_Room_GUI();
//	}

}
