import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class User_Info_GUI extends JFrame{
	
	private JLabel lblID;
	private JLabel lblNickname;
	
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;
	private JTextField tf_phone;
	private JTextField tf2_hobby;
	private JTextField tf3_intro;
	
	private JButton btn_OK;
	private JButton btn_Exit;
	private JLabel lblTitle;
	
	private TitledBorder tborder1;
	private TitledBorder tborder2;
	private TitledBorder tborder3;
	private User_Info info;
	// 해림수정 0705
	private Point ptFirst; 
	private Color bgColor;
	private Color btnColor;
	private Color bgGColor;
	private Color edgeColor;
	private Font font;
	
	public User_Info_GUI(User_Info info){
		this.info =info;
		init();
		setDisplay();
		showFrame();
		addListeners();
	}
	
	private void init(){
		
		// 해림수정 0705
		bgColor = new Color(0xFFFFFF); // 하얀색
		btnColor = new Color(0xE59BB9); //진한분홍
		bgGColor = new Color(0xFFEBFF); //엄청연한분홍
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		lblID = new JLabel("ID : "+info.getId() + "(" + "NAME : "+info.getName() + ")");
		lblID.setFont(font);
		lblID.setPreferredSize(new Dimension(120, 30));//해림수정0705
		
		lblNickname = new JLabel("Nickname : "+info.getNickName());
		lblNickname.setFont(font);
		lblNickname.setPreferredSize(new Dimension(120, 30));//해림수정0705
		
		lbl1 = new JLabel("전화번호   :",JLabel.CENTER);
		lbl1.setFont(font);
		lbl2 = new JLabel("취미 :",JLabel.CENTER);
		lbl2.setFont(font);
		lbl3 = new JLabel("특이사항 :",JLabel.CENTER);
		lbl3.setFont(font);
		
		tf_phone = new JTextField(13);
		tf2_hobby = new JTextField(13);
		tf3_intro = new JTextField(13);
		tf_phone.setText(info.getPhonenum());
		tf2_hobby.setText(info.getHobby());
		tf3_intro.setText(info.getIntro());
		tf_phone.setEnabled(false);
		tf2_hobby.setEnabled(false);
		tf3_intro.setEnabled(false);
		
		btn_OK = new JButton("확인");
		btn_OK.setFont(font);
		btn_OK.setForeground(bgColor);
		btn_OK.setBackground(btnColor);
		btn_OK.setPreferredSize(new Dimension(50, 30));
		btn_OK.setBorder(new LineBorder(bgColor, 1));
		
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 30));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
		btn_Exit.setBorder(new LineBorder(edgeColor, 1));//해림수정0704
		
		lblTitle = new JLabel("프로필");
		lblTitle.setFont(font);
		
		tborder1 = new TitledBorder(new LineBorder(btnColor, 2),"");
		tborder2 = new TitledBorder(new LineBorder(btnColor, 2),"");
		tborder3 = new TitledBorder(new LineBorder(btnColor, 2),"");
	}
	private void setDisplay(){
		JPanel pnlNorth = new JPanel(new BorderLayout());
		JPanel pnlNorthTop = new JPanel(new BorderLayout());
		JPanel pnlNorth1 = new JPanel();
		JPanel pnlNorth2 = new JPanel();
		pnlNorth.add(pnlNorthTop);
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);
		pnlNorthTop.add(btn_Exit, BorderLayout.EAST);
		pnlNorthTop.add(lblTitle, BorderLayout.WEST);
		pnlNorthTop.setBackground(edgeColor);
		pnlNorth1.add(lblID);
		pnlNorth1.setBorder(tborder1);
		pnlNorth2.add(lblNickname);
		pnlNorth2.setBorder(tborder2);
		
		pnlNorth.add(pnlNorthTop, BorderLayout.NORTH);
		pnlNorth.add(pnlNorth1,BorderLayout.CENTER);
		pnlNorth.add(pnlNorth2,BorderLayout.SOUTH);
		
		JPanel pnlCenter = new JPanel(new GridLayout(3,2));
		pnlCenter.add(lbl1);
		pnlCenter.add(tf_phone);
		pnlCenter.setFont(font);
		pnlCenter.add(lbl2);
		pnlCenter.add(tf2_hobby);
		pnlCenter.add(lbl3);
		pnlCenter.add(tf3_intro);
		pnlCenter.setBorder(tborder3);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn_OK);
		
		pnlNorth1.setBackground(bgColor);
		pnlNorth2.setBackground(bgColor);
		pnlNorth.setBackground(bgColor);
		pnlCenter.setBackground(bgColor);
		pnlSouth.setBackground(bgColor);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);
	
		add(pnlTotal, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(edgeColor, 6));
		
	}
	
	private void addListeners(){
		ActionListener aListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btn_OK || src == btn_Exit){
					dispose();
				}
			}
		};
		btn_OK.addActionListener(aListener);
		btn_Exit.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	
	// 해림수정 0701------------------------------------------------------------
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
	//-
		
	private void showFrame(){
		setTitle(info.getId() + "의 프로필");
		setSize(350, 300);
 		setLocation(100,0);
 		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new User_Info_GUI();
//	}

}
