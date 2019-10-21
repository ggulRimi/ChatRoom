import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

public class LoginForm extends JFrame implements ActionListener, iProtocol {

	private JTextComponent tfID;
	private JTextComponent pfPW;
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel lblIcon;
	private JButton btnLogin;
	private JButton btnJoin;
	private JButton btnExit;
	private Color btnColor;
	private Color bgColor;
	private Color btnXColor;
	private Color bgGcolor;
	private Vector<User> list;
	private Point ptFirst;
	private JPanel pnlPw;
	private JLabel lblFrameIcon;
	private ClientData cData;

	public ClientData getCd() {
		return cd;
	}

	public void setCd(ClientData cd) {
		this.cd = cd;
	}

	public JoinForm getJoinGui() {
		return joinGui;
	}

	public void setJoinGui(JoinForm joinGui) {
		this.joinGui = joinGui;
	}

	private ClientData cd;
	private JoinForm joinGui;

	public LoginForm(ClientData cd) {
		this.cd = cd;
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src == btnLogin) {
			SendData send = new SendData(LOGIN, new User(tfID.getText(),
					pfPW.getText()));
			send(send);
		} else if (src == btnJoin) {
			joinGui = new JoinForm(this);
		} else {
			System.exit(0);
		}

	}

	public void showIdOk() {
		joinGui.dispose();
		showMsg("회원가입이 완료되었습니다.");
	}

	public void showDiag(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	public void send(SendData send) {
		try {
			cd.sendTarget(send);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		// FFFFFF //하얀색
		// FFEBFF// 엄청연한분홍
		// FFD9EC // 연한분홍
		// E59BB9 // 진한분홍
		list = new Vector<User>();
		btnColor = new Color(0xE59BB9); // 진한분홍
		bgColor = new Color(0xFFFFFF); // 하얀색
		bgGcolor = new Color(0xFFD9EC); // 연한분홍
		btnXColor = new Color(0xBDBDBD); // 회색
		Font font = new Font("HY나무B", Font.PLAIN, 15);

		tfID = new JTextField(18);
		tfID.setFont(font);
		pfPW = new JPasswordField(21);
		btnLogin = new JButton("로그인");
		btnLogin.setFont(font);
		btnLogin.setOpaque(true);

		btnLogin.setEnabled(false);
		btnLogin.setBackground(btnXColor);
		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.WHITE);

		btnLogin.setPreferredSize(new Dimension(90, 45));

		btnJoin = new JButton("회원가입");
		btnJoin.setFont(font);

		btnJoin.setOpaque(false);
		btnJoin.setBackground(bgColor);
		btnJoin.setBorderPainted(false);

		btnExit = new JButton("X");
		btnExit.setPreferredSize(new Dimension(50, 25));
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(btnColor);
		btnExit.setBorder(new LineBorder(Color.WHITE, 1));
		btnExit.setFont(font);

		lblId = new JLabel("ID  : ", JLabel.CENTER);
		lblId.setFont(font);

		lblPw = new JLabel("PW : ", JLabel.CENTER);
		lblPw.setFont(font);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Siba.gif");
		img = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblIcon = new JLabel(new ImageIcon(img));

		Image img2 = kit.getImage("Icon.png");
		img2 = img2.getScaledInstance(270, 90, Image.SCALE_SMOOTH);
		lblFrameIcon = new JLabel(new ImageIcon(img2));
	}

	public void setDisplay() {

		JPanel pnlTopBar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		pnlTopBar.add(btnExit);

		pnlTopBar.setBackground(bgColor);

		JPanel pnlNorth_Down = new JPanel(new BorderLayout());

		pnlNorth_Down.setPreferredSize(new Dimension(300, 370));
		pnlNorth_Down.add(lblFrameIcon, BorderLayout.NORTH);
		pnlNorth_Down.add(lblIcon, BorderLayout.CENTER);
		pnlNorth_Down.setBackground(bgColor);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlTopBar, BorderLayout.NORTH);
		pnlNorth.add(pnlNorth_Down, BorderLayout.CENTER);

		JPanel pnlId = new JPanel();
		pnlId.add(lblId);
		pnlId.add(tfID);
		pnlId.setBackground(bgColor);

		pnlPw = new JPanel();
		pnlPw.add(lblPw);
		pnlPw.add(pfPW);

		pnlPw.setBackground(bgColor);

		JPanel pnlBtnLogin = new JPanel();
		pnlBtnLogin.add(btnLogin);
		pnlBtnLogin.setBackground(bgColor);

		JPanel pnlTfCenter = new JPanel(new BorderLayout());
		pnlTfCenter.add(pnlId, BorderLayout.NORTH);
		pnlTfCenter.add(pnlPw, BorderLayout.CENTER);
		pnlTfCenter.add(pnlBtnLogin, BorderLayout.SOUTH);

		pnlTfCenter.setBackground(bgColor);

		JPanel pnlCenter = new JPanel();
		pnlCenter.add(pnlTfCenter);

		pnlCenter.setPreferredSize(new Dimension(300, 70));

		JPanel pnlBtn = new JPanel(new BorderLayout());
		pnlBtn.add(btnJoin, BorderLayout.SOUTH);
		pnlBtn.setBorder(new EmptyBorder(20, 0, 0, 0));
		pnlBtn.setBackground(bgColor);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(pnlBtn, BorderLayout.SOUTH);

		pnlSouth.setPreferredSize(new Dimension(300, 70));

		pnlNorth.setBackground(bgColor);
		pnlCenter.setBackground(bgColor);
		pnlSouth.setBackground(bgColor);

		JPanel pnlTotal = new JPanel(new BorderLayout());

		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlTotal, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(bgGcolor, 6));

	}

	private void addListener() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(LoginForm.this,
						"exit?", "question", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		KeyAdapter keyListener  = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						SendData send = new SendData(LOGIN, new User(tfID.getText(),
								pfPW.getText()));
						send(send);
					}
				}

		};
		
		tfID.addKeyListener(new MyKeyListener());
		tfID.addKeyListener(keyListener);
		pfPW.addKeyListener(new MyKeyListener());
		pfPW.addKeyListener(keyListener);
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		btnExit.addActionListener(this);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	private void clear() {
		tfID.setText("");
		pfPW.setText("");
	}

	public User findUser(String userId) {
		int idx = list.indexOf(new User(userId));
		if (idx >= 0) {
			return list.get(idx);
		} else {
			return null;
		}
	}

	public void addUser(User user) {
		if (findUser(user.getUid()) == null) {
			list.add(user);
		}
	}

	public void removeUser(User user) {
		list.remove(user);
	}

	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (!tfID.getText().equals("") && !pfPW.getText().equals("")) {
				btnLogin.setBackground(btnColor);
				btnLogin.setEnabled(true);
			} else {
				btnLogin.setBackground(btnXColor);
				btnLogin.setEnabled(false);
			}
		};
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

	public void showMsg(String str) {
		new MsgDiag(this, str);
	}

	public void showFrame() {
		setLocation(700, 300);
		setSize(350, 600);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}