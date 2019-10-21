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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class User_Info_Modified_GUI extends JFrame {

	private JLabel lblID;
	private JLabel lblNickname;

	private JLabel lbl_phone;
	private JLabel lbl_hobby;
	private JLabel lbl_intro;
	private JTextField tf_phone;
	private JTextField tf_hobby;
	private JTextField tf_intro;

	private JButton btn_Modified;
	private JButton btn_Cancel;

	private TitledBorder tborder1;
	private TitledBorder tborder2;
	private TitledBorder tborder3;
	private User_Info info;

	private ClientData cData;
	// 해림수정 0705
	private JButton btn_Exit;
	private JLabel lblTitle;
	private Point ptFirst; 
	private Color bgColor;
	private Color btnColor;
	private Color bgGColor;
	private Color edgeColor;
	private Font font;

	public User_Info_Modified_GUI(User_Info info, ClientData cData) {
		this.cData = cData;
		this.info = info;
		init();
		setDisplay();
		showFrame();
		addListeners();
	}

	private void init() {
		// 해림수정 0705
		bgColor = new Color(0xFFFFFF); // 하얀색
		btnColor = new Color(0xE59BB9); //진한분홍
		bgGColor = new Color(0xFFEBFF); //엄청연한분홍
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		lblID = new JLabel("ID : " + info.getId() + "(" + "NAME : "
				+ info.getName() + ")");
		lblID.setFont(font);
		lblNickname = new JLabel("Nickname : " + info.getNickName());
		lblNickname.setFont(font);
		
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 25));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
		btn_Exit.setBorder(new LineBorder(edgeColor, 1));//해림수정0704

		lblTitle = new JLabel("프로필수정");
		lblTitle.setFont(font);
		
		lbl_phone = new JLabel("전화번호 :", JLabel.CENTER);
		lbl_phone.setFont(font);
		lbl_hobby = new JLabel("취미 :", JLabel.CENTER);
		lbl_hobby.setFont(font);
		lbl_intro = new JLabel("자기소개 :", JLabel.CENTER);
		lbl_intro.setFont(font);
		
		tf_phone = new JTextField(13);
		tf_hobby = new JTextField(13);
		tf_intro = new JTextField(13);
		tf_phone.setText(info.getPhonenum());
		tf_hobby.setText(info.getHobby());
		tf_intro.setText(info.getIntro());

		btn_Modified = new JButton("수정");
		btn_Modified.setFont(font);
		btn_Modified.setForeground(bgColor);
		btn_Modified.setPreferredSize(new Dimension(50, 30));
		btn_Modified.setBorder(new LineBorder(bgColor, 1));
		
		btn_Cancel = new JButton("취소");
		btn_Cancel.setFont(font);
		btn_Cancel.setForeground(bgColor);
		btn_Cancel.setPreferredSize(new Dimension(50, 30));
		btn_Cancel.setBorder(new LineBorder(bgColor, 1));
		
		btn_Modified.setBackground(btnColor);
		btn_Cancel.setBackground(btnColor);

		tborder1 = new TitledBorder(new LineBorder(btnColor, 2), "");
		tborder2 = new TitledBorder(new LineBorder(btnColor, 2), "");
		tborder3 = new TitledBorder(new LineBorder(btnColor, 2), "");
	}

	private void setDisplay() {
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

		JPanel pnlCenter = new JPanel(new GridLayout(3, 2));
		pnlCenter.add(lbl_phone);
		pnlCenter.add(tf_phone);
		pnlCenter.add(lbl_hobby);
		pnlCenter.add(tf_hobby);
		pnlCenter.add(lbl_intro);
		pnlCenter.add(tf_intro);
		pnlCenter.setBorder(tborder3);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn_Modified);
		pnlSouth.add(btn_Cancel);
		
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

	// 
	// ================================================================================================
	private void addListeners() {
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == btn_Modified) {
					String id = info.getId();
					String name = info.getName();
					String nickname = info.getNickName();
					String phonenum = tf_phone.getText();
					String hobby = tf_hobby.getText();
					String intro = tf_intro.getText();

					User_Info info = new User_Info(id, name, nickname,
							phonenum, hobby, intro);

					SendData send = new SendData(cData.MODIFY_MY_PROFILE, info);
					cData.sendTarget(send);
					dispose();

				}
				if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				}
			}
		};
		btn_Modified.addActionListener(aListener);
		btn_Cancel.addActionListener(aListener);
		btn_Exit.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	// ================================================================================================

	public JTextField getTf1() {
		return tf_phone;
	}

	public void setTf1(JTextField tf1) {
		this.tf_phone = tf1;
	}

	public JTextField getTf2() {
		return tf_hobby;
	}

	public void setTf2(JTextField tf2) {
		this.tf_hobby = tf2;
	}

	public JTextField getTf3() {
		return tf_intro;
	}

	public void setTf3(JTextField tf3) {
		this.tf_intro = tf3;
	}

	// 해림수정 0705------------------------------------------------------------
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
	
	private void showFrame() {
		setTitle("프로필 수정");
		setSize(350, 300);
		setLocation(100, 0);
		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}