import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class JoinForm extends JDialog implements ActionListener, iProtocol {
	public final int ID = 0;
	public final int PW = 1;
	public final int RE = 2;
	public final int NAME = 3;
	public final int NickName = 4;
	public final int Phone = 5;
	public String[] names = { "ID", "비밀번호", "재입력", "이름", "닉네임", "전화번호" };

	private Point ptFirst;
	private JButton btnJoin;
	private JButton btnCancel;
	private JTextComponent[] inputs;
	private LoginForm owner;
	private Color bgColor;
	private Color etcColor;
	private Color btnColor;
	private Font font;

	public JoinForm(LoginForm owner) {
		super(owner, "Join", false);
		this.owner = owner;
		setUndecorated(true);
		init();
		setDisplay();
		addListener();
		showDlg();
	}

	private void init() {
		// 해림수정 0703
		bgColor = new Color(0xFFFFFF);
		etcColor = new Color(0xFFD9EC); //연한분홍
		btnColor = new Color(0xE59BB9); // 진한분홍
		font = new Font("HY나무B", Font.PLAIN, 15);

		btnJoin = LoginUtils.getButton("Join");
		setButton(btnJoin);
		btnJoin.setFont(font);
		btnJoin.setForeground(Color.WHITE);
		btnCancel = LoginUtils.getButton("Cancel");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(font);
		setButton(btnCancel);

		inputs = new JTextComponent[] {
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.PASSWORD),
				LoginUtils.getTextComponent(LoginUtils.PASSWORD),
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.TEXT),
				LoginUtils.getTextComponent(LoginUtils.TEXT) };
	}

	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < inputs.length; i++) {
			JPanel pnl = new JPanel();
			pnl.add(LoginUtils.getLabel(names[i]));
			pnl.add(inputs[i]);
			pnlNorth.add(pnl);
			pnl.setBackground(bgColor);
		}

		JPanel pnlBtns = new JPanel();
		pnlBtns.add(btnJoin);
		pnlBtns.add(btnCancel);
		pnlBtns.setBackground(bgColor);

		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlBtns, BorderLayout.SOUTH);

		TitledBorder tBorder = new TitledBorder(new EmptyBorder(5, 10, 5, 10),
				"<<회원가입>>");
		tBorder.setTitleFont(new Font("HY나무B", Font.PLAIN, 15));
		pnlMain.setBorder(tBorder);

		pnlMain.setBackground(bgColor);
		JPanel pnlTotal = new JPanel();
		pnlTotal.add(pnlMain, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(etcColor, 6));

		pnlTotal.setBackground(bgColor);

		add(pnlTotal, BorderLayout.CENTER);
	}

	private void addListener() {
		btnJoin.addActionListener(this);
		btnCancel.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeDlg();
			}
		});
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	private void closeDlg() {
		dispose();
		owner.setVisible(true);
	}

	private void setButton(JButton btn) {
		btn.setOpaque(true);
		// btn.setBorderPainted(false);
		btn.setBackground(btnColor);
	}

	// 회원가입 기본기능
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnJoin) {
			boolean flag = true;
			String msg = "";
			// 입력 확인
			for (int idx = 0; flag && idx < inputs.length; idx++) {
				if (LoginUtils.isEmpty(inputs[idx])) {
					flag = false;
					msg = "missing input : " + names[idx];
					inputs[idx].requestFocus();
				}
			}
			// 아이디 체크
			if (flag) {
				User user = owner.findUser(inputs[ID].getText());
				if (user != null) {
					flag = false;
					msg = "invalid ID : already existed";
					inputs[ID].requestFocus();
				}
			}
			// 비번체크
			if (flag) {
				String pw1 = inputs[PW].getText();
				String pw2 = inputs[RE].getText();
				if (!pw1.equals(pw2)) {
					msg = "check your password";
					inputs[PW].requestFocus();
					flag = false;
				}
			}
			if (flag) {
				User user = owner.findUser(inputs[4].getText());
				if (user != null) {
					flag = false;
					msg = "invalid NickName : already existed";
					inputs[ID].requestFocus();
				}
			}
			if (flag) {
				User user = new User(inputs[ID].getText(),
						inputs[PW].getText(), inputs[NAME].getText(),
						inputs[NickName].getText(), inputs[Phone].getText());
				owner.send(new SendData(JOIN, user));
			} else {
				JOptionPane.showMessageDialog(this, msg, "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			closeDlg();
		}
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

	private void showDlg() {
		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

}