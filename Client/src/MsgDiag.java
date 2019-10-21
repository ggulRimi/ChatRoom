import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MsgDiag extends JDialog {
	private JButton btn;
	private JLabel lbl;
	private Point ptFirst;
	private JButton btnExit;
	private Color bgColor;
	private Color OutColor;
	private Color bgGColor;
	private Font font;
	private JLabel lblTitle;
	private JTextField tfInput;
	private ClientData cData;
	private JButton btnCancel;
	private String msg;
	
	
	private String id;
	private long time;
	private int roomNum;

	public MsgDiag(JFrame frame, String msg) {
		super(frame, msg, false);
		init();
		setLbl(msg);
		setDisplay();
		addListeners();
		showDiag();
		setAlwaysOnTop(true);
	}

	public MsgDiag(String msg, JFrame frame, ClientData cData) {
		super(frame, msg, false);
		this.cData = cData;
		init();
		setLbl(msg);
		setDisplay_Input();
		addListeners_Input();
		showDiag();
		setAlwaysOnTop(true);
	}
	public MsgDiag(String msg,ClientData cData ,JFrame frame) {
		super(frame, msg, false);
		this.cData = cData;
		init();
		this.msg = msg;
		setLbl(msg);
		setDisplay_YesOrNo();
		addListeners_YesOrNo();
		showDiag();
		setAlwaysOnTop(true);
	}
	public MsgDiag(String msg,ClientData cData ,JFrame frame,SendData send) {
		super(frame, msg, false);
		this.cData = cData;
		init();
		this.msg = msg;
		this.roomNum =(int)send.getObj1();
		this.id =(String)send.getObj2();
		this.time = (long)send.getObj3();
		setLbl(msg);
		setDisplay_YesOrNo();
		btnCancel.setText("아니오");
		btn.setText("예");
		
		addListeners_YesOrNo_Invite();
		showDiag();
		setAlwaysOnTop(true);
	}

	private void init() {
		bgColor = new Color(0xFFFFFF);
		bgGColor = new Color(0xE59BB9);
		OutColor = new Color(0xFFD9EC);

		font = new Font("HY나무B", Font.PLAIN, 15);

		btn = new JButton("확인");
		setButton(btn);
		btn.setFont(font);
		btnExit = new JButton(" X ");
		setButton(btnExit);
		btnExit.setFont(font);
		
		btnCancel = new JButton("취소");
		setButton(btnCancel);
		btnCancel.setFont(font);
		
		lblTitle = new JLabel("확인", JLabel.CENTER);
		lblTitle.setFont(font);
		tfInput = new JTextField(12);
		tfInput.setFont(font);
		tfInput.setBorder(new LineBorder(bgGColor,1));
		
	}

	private void setDisplay() {
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btnExit, BorderLayout.EAST);
		pnlTop.setBackground(OutColor);
		pnlTop.setBorder(new EmptyBorder(2,3,5,3));

		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(lbl, BorderLayout.CENTER);
		pnl.add(tfInput, BorderLayout.SOUTH);
		pnl.setBackground(bgColor);

		JPanel pnl_South = new JPanel();
		pnl_South.add(btn);
		pnl_South.setBackground(bgColor);

		pnl.add(pnl_South, BorderLayout.SOUTH);

		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnl, BorderLayout.CENTER);

		pnlTotal.setBorder(new LineBorder(OutColor, 6));
		pnlTotal.setBackground(bgColor);

		add(pnlTotal, BorderLayout.CENTER);

	}

	private void setDisplay_Input() {
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btnExit, BorderLayout.EAST);
		pnlTop.setBackground(OutColor);
		pnlTop.setBorder(new EmptyBorder(2,3,5,3));
		
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(lbl, BorderLayout.NORTH);
		
		JPanel pnlInput = new JPanel();
		pnlInput.add(tfInput);
		pnlInput.setBackground(bgColor);
		
		pnl.add(pnlInput,BorderLayout.CENTER);
		pnl.setBackground(bgColor);

		JPanel pnl_South = new JPanel();
		pnl_South.add(btn);
		pnl_South.setBackground(bgColor);
		
		pnl.add(pnl_South,BorderLayout.SOUTH);

		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnl, BorderLayout.CENTER);

		pnlTotal.setBorder(new LineBorder(OutColor, 6));
		pnlTotal.setBackground(bgColor);
		pnlTotal.setPreferredSize(new Dimension(350,140));

		add(pnlTotal, BorderLayout.CENTER);

	}
	private void setDisplay_YesOrNo() {
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btnExit, BorderLayout.EAST);
		pnlTop.setBackground(OutColor);

		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(lbl, BorderLayout.CENTER);
		pnl.setBackground(bgColor);

		JPanel pnl_South = new JPanel(new FlowLayout());
		pnl_South.add(btn);
		pnl_South.add(btnCancel);
		pnl_South.setBackground(bgColor);

		pnl.add(pnl_South, BorderLayout.SOUTH);

		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnl, BorderLayout.CENTER);

		pnlTotal.setBorder(new LineBorder(OutColor, 6));
		pnlTotal.setBackground(bgColor);

		add(pnlTotal, BorderLayout.CENTER);

	}

	private void setLbl(String msg) {
		lbl = new JLabel(msg, JLabel.CENTER);
		lbl.setFont(font);
		lbl.setPreferredSize(new Dimension(350, 30));
	}

	private void addListeners() {
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				MsgDiag.this.dispose();
			}
		};
		KeyAdapter keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					MsgDiag.this.dispose();
				}
			}

		};
		btnExit.addKeyListener(keyListener);
		
		btnExit.addActionListener(aListener);
		btn.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	private void addListeners_Input() {
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btnExit) {
					MsgDiag.this.dispose();
				} else {
					if(cData.getOnLine_User_List().containsKey(tfInput.getText()) || cData.getOffLine_User_List().containsKey(tfInput.getText())){
						new Send_Note_GUI(cData,tfInput.getText());
						MsgDiag.this.dispose();
					} else {
						cData.getGui().getNote_Box_Gui().showMsg("존재하지 않는 ID입니다!");
					}
				}
			}
		};
		KeyAdapter keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(cData.getOnLine_User_List().containsKey(tfInput.getText()) || cData.getOffLine_User_List().containsKey(tfInput.getText())){
						new Send_Note_GUI(cData,tfInput.getText());
						MsgDiag.this.dispose();
					} else {
						cData.getGui().getNote_Box_Gui().showMsg("존재하지 않는 ID입니다!");
					}
				}
			}

		};
		tfInput.addKeyListener(keyListener);
		btnExit.addActionListener(aListener);
		btn.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	private void addListeners_YesOrNo() {
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if(src == btnExit || src == btnCancel){
				MsgDiag.this.dispose();
				}
				else if(!msg.contains("로그아웃")){
					System.exit(0);
				} else {
//					cData.getGui().allGuiClose();
//					cData.getGui().showLogin();
					cData.sendTarget(new SendData(cData.LOGOUT));
				}
			}
		};
		btnCancel.addActionListener(aListener);
		btnExit.addActionListener(aListener);
		btn.addActionListener(aListener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	private void addListeners_YesOrNo_Invite() {
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if(src == btnExit){
				MsgDiag.this.dispose();
				}else if(src == btnCancel){
					cData.sendTarget(new SendData(cData.USER_INVITE_RESPOND_NO, id,
							cData.getMyID()));
					MsgDiag.this.dispose();
				}else if(src == btn){
					cData.sendTarget(new SendData(cData.USER_INVITE_RESPOND_OK, id,
							cData.getMyID(), roomNum,time));
					MsgDiag.this.dispose();
				}
			}
		};
		btnCancel.addActionListener(aListener);
		btnExit.addActionListener(aListener);
		btn.addActionListener(aListener);
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

	private void setButton(JButton btn) {
		btn.setFont(font);
		btn.setPreferredSize(new Dimension(50, 25));
		btn.setForeground(Color.WHITE);
		btn.setBackground(bgGColor);
		btn.setBorder(new LineBorder(bgGColor, 1));
	}

	public JTextField getTfInput() {
		return tfInput;
	}

	public void setTfInput(JTextField tfInput) {
		this.tfInput = tfInput;
	}

	private void showDiag() {
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}


}
