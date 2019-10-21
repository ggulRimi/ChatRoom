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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChatRoom_Set_Change_GUI extends JFrame implements iProtocol {

	private JLabel lblRoomName;
	private JLabel lblPeople;
	private JLabel lblCategory;
	private JLabel lblTitle;
	private JCheckBox cbPW;

	private JTextField tf1;
	private JTextField tf2;

	private String People[] = { "1명", "2명", "3명", "4명", "5명" ,"10명", "15명", "20명" };
	private String Category[] = { "요리", "스포츠", "게임", "음악" };
	private JComboBox<String> combo1;
	private JComboBox<String> combo2;

	private JButton btn_OK;
	private JButton btn_Cancel;
	private JButton btn_Exit;
	// private String king;
	private boolean isBlock;
	private ClientData cData;
	private int roomNum;
	private JPanel pnlCenter;

	private Color btnColor;
	private Color bgColor;
	private Font font;
	
	private JPanel pnlPW;
	private Point ptFirst;

	public ChatRoom_Set_Change_GUI(ClientData cData, int roomNum) {
		this.cData = cData;
		this.roomNum = roomNum;
		init();
		setDisplay();
		showFrame();
		addActionListener();
	}

	private void init() {
		//해림수정 0704
		btnColor = new Color(0xE59BB9); //진한분홍
		bgColor = new Color(0xFFFFFF); // 하얀색
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		isBlock = true;
		lblRoomName = new JLabel("방이름", JLabel.CENTER);
		lblRoomName.setFont(font);
		lblPeople = new JLabel("채팅방 인원", JLabel.CENTER);
		lblPeople.setFont(font);
		lblCategory = new JLabel("카테고리", JLabel.CENTER);
		lblCategory.setFont(font);
		lblTitle = new JLabel("방 설정", JLabel.CENTER);
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.WHITE);
		cbPW = new JCheckBox("비밀번호", cData.getChat_Room_List().get(roomNum).isBlock());
		cbPW.setFont(font);
		cbPW.setBackground(bgColor);

		pnlPW = new JPanel(new FlowLayout());
		pnlPW.setBackground(bgColor);




		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		tf1 = new JTextField(12);
		tf1.setText(cData.getChat_Room_List().get(roomNum).getTitle());
		tf1.setFont(font);
		tf2 = new JTextField(12);
		tf2.setText(cData.getChat_Room_List().get(roomNum).getPassword());
		tf2.setFont(font);
		if (!cbPW.isSelected()) {
			tf2.setEnabled(false);
		} else {
			tf2.setEnabled(true);
		}
		combo1 = new JComboBox<String>(People);
		combo1.setBackground(Color.WHITE);
		combo1.setSelectedItem(cData.getChat_Room_List().get(roomNum).getMax_User() + "명");
		combo1.setRenderer(listRenderer);
		combo1.setFont(font);

		combo2 = new JComboBox<String>(Category);
		combo2.setBackground(Color.WHITE);
		combo2.setSelectedItem(cData.getChat_Room_List().get(roomNum).getCategory_Name());
		combo2.setRenderer(listRenderer);
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

	private void setDisplay() {
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
					String sub = combo1.getSelectedItem().toString().substring(0, idx);
					Integer max_User = Integer.valueOf(sub);
					String category = combo2.getSelectedItem().toString();
					String king = cData.getMyID();

					if (cbPW.isSelected() == false) {
						isBlock = false;
					} else {
					}
					if (isBlock == true) {
						String password = tf2.getText();
						Chat_Room setRoom = new Chat_Room(title, king, category, max_User, password, isBlock);
						SendData sd = new SendData(CHATROOM_SET_CHANGE, setRoom, roomNum);
						System.out.println("블락체크");
						try {
							cData.sendTarget(sd);
						} catch (Exception e1) {
						}
					} else {

						Chat_Room setRoom = new Chat_Room(title, king, category, max_User, isBlock);
						SendData sd = new SendData(CHATROOM_SET_CHANGE, setRoom, roomNum);
						try {
							cData.sendTarget(sd);
						} catch (Exception e1) {
						}
					}
					dispose();
				} else if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				}
			}
		};
		cbPW.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					tf2.setEnabled(true);
					isBlock = true;
				} else {
					tf2.setEnabled(false);
					isBlock = false;
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

	private void showFrame() {
		setSize(350, 245);
		setUndecorated(true);
		setLocation(100, 200);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
