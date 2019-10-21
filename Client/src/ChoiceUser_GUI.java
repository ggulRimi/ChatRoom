import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

public class ChoiceUser_GUI extends JFrame {
	private JButton btn_Ok;
	private JButton btn_Cancel;
	private JButton btn_Put;
	private JButton btn_Pop;

	private DefaultListModel<String> user;
	private DefaultListModel<String> selList;
	// private s
	private JList<String> selectedList;
	private JList<String> userList;

	private JLabel lblUserList;
	private JLabel lblSelList;
	private JLabel lblSearch;
	private JTextField tfSearch;
	private JButton btn_Search;
	private JButton btn_whisper;
	private ClientData cData;
	private HashMap<String, User_Info> waiting;
	private HashMap<String, User_Info> online;
	private int roomNum;
	// 해림수정0704
	private Color bgColor;
	private Color btnColor;
	private Color bgGColor;
	private Font font;
	private Point ptFirst;
	private JButton btn_Exit;
	private JLabel lblTitle;
	private JPanel pnlTotal;

	private String whisperid;// 명환

	public ChoiceUser_GUI(HashMap<String, User_Info> waiting, ClientData cData,
			int roomNum) {
		this.roomNum = roomNum;
		this.cData = cData;
		this.waiting = waiting;
		init();
		setDisplay_Invite();
		addListeners_Invite();
		showFrame();
		this.setAlwaysOnTop(true);
	}

	public ChoiceUser_GUI(HashMap<String, User_Info> online, ClientData cData) {// 명환26
		this.cData = cData;
		this.online = online;

		System.out.println("초이스칸 떳냐");
		init();
		setDisplay_Wisper();
		addListeners_Wisper();
		showFrame_Wisper();
		this.setAlwaysOnTop(true);
	}
	public ChoiceUser_GUI(ClientData cData,HashMap<String,User_Info> online) {// 명환26
		this.cData = cData;
		this.online = online;

		System.out.println("초이스칸 떳냐");
		init();
		btn_whisper.setText("선택");
		setDisplay_Wisper();
		addListeners_Note();
		showFrame_Wisper();
		this.setAlwaysOnTop(true);
	}

	private void init() {
		//해림수정 0704
		btnColor = new Color(0xE59BB9); //진한분홍
		bgGColor = new Color(0xFFEBFF); //엄청 연한분홍
		bgColor = new Color(0xFFFFFF); // 하얀색
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		pnlTotal = new JPanel(new BorderLayout());
		
		user = new DefaultListModel<String>();
		selList = new DefaultListModel<String>();
		// 온라인 유저 추가
		lblSearch = new JLabel("사용자 검색");
		lblSearch.setFont(font);
		tfSearch = new JTextField(10);
		btn_Search = new JButton("검색");
		btn_Search.setFont(font);
		btn_Search.setForeground(Color.WHITE); // 해림수정0702
		btn_Search.setBackground(btnColor); // 해림수정0702
		btn_Search.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정0702
		btn_Search.setPreferredSize(new Dimension(50, 30));// 해림수정0702

		btn_Ok = new JButton("확인");
		btn_Ok.setFont(font);
		btn_Ok.setForeground(Color.WHITE); // 해림수정0702
		btn_Ok.setBackground(btnColor); // 해림수정0702
		btn_Ok.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정0702
		btn_Ok.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		btn_Cancel = new JButton("취소");
		btn_Cancel.setFont(font);
		btn_Cancel.setForeground(Color.WHITE); // 해림수정0702
		btn_Cancel.setBackground(btnColor); // 해림수정0702
		btn_Cancel.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정0702
		btn_Cancel.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		btn_Put = new JButton("추가");
		btn_Put.setFont(font);
		btn_Put.setForeground(Color.WHITE); // 해림수정0702
		btn_Put.setBackground(btnColor); // 해림수정0702
		btn_Put.setBorder(new LineBorder(Color.WHITE, 1)); // 해림수정0702
		btn_Put.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		btn_Pop = new JButton("삭제");
		btn_Pop.setFont(font);
		btn_Pop.setForeground(Color.WHITE); // 해림수정0702
		btn_Pop.setBackground(btnColor); // 해림수정0702
		btn_Pop.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정
		btn_Pop.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		btn_whisper = new JButton("귓속말");
		btn_whisper.setFont(font);
		btn_whisper.setForeground(Color.WHITE); // 해림수정0702
		btn_whisper.setBackground(btnColor); // 해림수정0702
		btn_whisper.setBorder(new LineBorder(Color.WHITE, 1));
		btn_whisper.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		// 해림수정 0702
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 25));
		btn_Exit.setBorder(new LineBorder(Color.WHITE, 1));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
//		btn_Exit.setBorderPainted(false); // 해림수정0702
		
		lblTitle = new JLabel("    초대하기", JLabel.CENTER);
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.WHITE); // 해림수정0702
	}


	private void setDisplay_Invite() {
		// ////////////////////////////////////////////////////////////////////////////////////north
		JPanel pnlNorth = new JPanel(new BorderLayout());

		JPanel pnlNorthTop = new JPanel(new GridLayout(1,0));
		lblUserList = new JLabel("현재 유저 목록", JLabel.CENTER);
		lblUserList.setFont(font);
		
		pnlNorthTop.add(lblUserList);
		lblSelList = new JLabel("선택된 유저",JLabel.CENTER);
		lblSelList.setFont(font);
		pnlNorthTop.add(lblSelList);
		
		JPanel pnlNorthTopTop = new JPanel(new BorderLayout());
		pnlNorthTopTop.add(lblTitle, BorderLayout.WEST);
		pnlNorthTopTop.add(btn_Exit, BorderLayout.EAST);
		pnlNorthTopTop.add(pnlNorthTop, BorderLayout.SOUTH);
		pnlNorthTopTop.setBackground(btnColor);
		
		JPanel pnlNorthBottom = new JPanel(new GridLayout(1,0));
		pnlNorthBottom.setBackground(bgColor);
	
		JPanel pnlNorthBottom_WEST = new JPanel();
		pnlNorthBottom_WEST.setBackground(bgColor);
		JPanel pnlNorthBottom_Empty = new JPanel();
		pnlNorthBottom_Empty.setBackground(bgColor);
		pnlNorthBottom_WEST.add(lblSearch);
		pnlNorthBottom_WEST.add(tfSearch);
		pnlNorthBottom_WEST.add(btn_Search);
		pnlNorthBottom.add(pnlNorthBottom_WEST);
		pnlNorthBottom.add(pnlNorthBottom_Empty);
		
		pnlNorth.add(pnlNorthTopTop, BorderLayout.NORTH);
		pnlNorth.add(pnlNorthBottom, BorderLayout.CENTER);
		// ////////////////////////////////////////////////////////////////////////////////////center
		JPanel pnlCenter = new JPanel();
		JPanel pnlUserList = new JPanel();// jlist
//		HashMap<String, User_Info> roomUser = cData.getChat_Room_List()
//				.get(roomNum).getRoom_User_List();
		HashMap<String, User_Info> roomUser = cData.getGui().getChat_Room_Gui().getRoom().getRoom_User_List();
		for (String id : waiting.keySet()) { // 유저 목록 최신화.
			if (!id.equals(cData.getMyID()) && !(roomUser.containsKey(id))) {
				user.addElement(id + "(" + waiting.get(id).getNickName() + ")");
			}
		}

		userList = new JList<String>(user);
		userList.setVisibleRowCount(10);
		userList.setCellRenderer(new MyListCellRenderer());
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		userList.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		JScrollPane scroll_UserList = setting_JScroll(userList);
		
		JPanel pnlPut = new JPanel();
		pnlPut.add(btn_Put);
		pnlPut.setBackground(bgColor);
		
		JPanel pnlPop = new JPanel();
		pnlPop.add(btn_Pop);
		pnlPop.setBackground(bgColor);
		
		JPanel pnlPutPop = new JPanel(new GridLayout(2, 0)); // 좌우 버튼 들어갈 거임
		pnlPutPop.add(pnlPut);
		pnlPutPop.add(pnlPop);
		JPanel pnlSelectedUser = new JPanel();// jlist

		selectedList = new JList<String>(selList);
		selectedList.setVisibleRowCount(10);
		selectedList.setCellRenderer(new MyListCellRenderer());
		selectedList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectedList.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		JScrollPane scroll_SelectedList = setting_JScroll(selectedList);
		pnlCenter.add(scroll_UserList);
		pnlCenter.add(pnlPutPop);
		pnlCenter.add(scroll_SelectedList);
		// //////////////////////////////////////////////////////////////////////////////////south
		JPanel pnlSouth = new JPanel();// 확인 버튼 취소 버튼
		pnlSouth.add(btn_Ok);
		pnlSouth.add(btn_Cancel);
		pnlNorth.setBackground(bgColor);
		pnlCenter.setBackground(bgGColor);
		pnlSouth.setBackground(bgGColor);
		
		
		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);
		pnlTotal.setBorder(new LineBorder(btnColor, 5));// 해림수정0702
		
		add(pnlTotal, BorderLayout.CENTER);

	}

	private void setDisplay_Wisper() {// /////////////해림 0703

		lblUserList = new JLabel("    현재 유저 목록",JLabel.CENTER);
		lblUserList.setForeground(Color.WHITE);
		lblUserList.setFont(font);
		JPanel pnlNorth = new JPanel(new BorderLayout());

		JPanel pnlNorthTop = new JPanel(new BorderLayout());
		pnlNorthTop.add(btn_Exit, BorderLayout.EAST);
		pnlNorthTop.add(lblUserList, BorderLayout.WEST);
		pnlNorthTop.setBorder(new EmptyBorder(0, 0, 5, 0));
		pnlNorthTop.setBackground(btnColor);
		JPanel pnlNorthBottom = new JPanel(new BorderLayout());
		JPanel pnlNorthBottom_WEST = new JPanel();
		pnlNorthBottom_WEST.add(lblSearch);
		pnlNorthBottom_WEST.add(tfSearch);
		pnlNorthBottom_WEST.add(btn_Search);
		pnlNorthBottom.add(pnlNorthBottom_WEST, BorderLayout.NORTH);

		pnlNorth.add(pnlNorthTop, BorderLayout.NORTH);
		pnlNorth.add(pnlNorthBottom, BorderLayout.CENTER);

		JPanel pnlCenter = new JPanel();
				
		for (String id : online.keySet()) { // 유저 목록 최신화.
			if (!id.equals(cData.getMyID()))
				user.addElement(id + "(" + online.get(id).getNickName() + ")");
		}

		userList = new JList<String>(user);
		userList.setVisibleRowCount(10);
		userList.setCellRenderer(new MyListCellRenderer());
		// userList.setSelectionMode( ListSelectionModel.);//
		userList.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXX");
		JScrollPane scroll_UserList = setting_JScroll(userList);

		pnlCenter.add(scroll_UserList);

		// //////////////////////////////////////////////////////////////////////////////////south
		JPanel pnlSouth = new JPanel();// 확인 버튼 취소 버튼
		pnlSouth.add(btn_whisper);
		pnlSouth.add(btn_Cancel);
		// 해림수정 0703
		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);
		pnlTotal.setBorder(new LineBorder(btnColor, 5));// 해림수정0702
		add(pnlTotal, BorderLayout.CENTER);

	}

	public JScrollPane setting_JScroll(JList list) {
		JScrollPane scrollPeople = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		// scrollPeople.setPreferredSize(new Dimension(x, y));//250 605
		scrollPeople
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPeople;
	}

	private void addListeners_Invite() {
		class ThreadRun extends Thread {
			@Override
			public void run() {
				Document doc = tfSearch.getDocument();
				doc.addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						documentChanged();
					}
				});
			}

			private void documentChanged() {
				user.removeAllElements();
				if (tfSearch.getText().length() == 0) {
					HashMap<String, User_Info> roomUser = cData.getGui().getChat_Room_Gui().getRoom().getRoom_User_List();
					for (String id : waiting.keySet()) { // 유저 목록 최신화.
						if (!id.equals(cData.getMyID())&& !(roomUser.containsKey(id))) {
							if (!selList.contains(id)) {
								user.addElement(id + "("+ waiting.get(id).getNickName() + ")");
							}
						}
					}
				}
			}
		}

		ThreadRun t = new ThreadRun();
		t.start();

		ListSelectionListener l_Listener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent le) {

			}
		};
		ActionListener a_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btn_Put) {
					boolean flag = true;
					int i = 0;
					if (user.getSize()>0){
					while (flag) {
						if (userList.isSelectedIndex(i)) {
							String item = user.get(userList.getSelectedIndex());
							user.remove(userList.getSelectedIndex());
							selList.addElement(item);
						} else {
							i++;
						}
						if (i == user.size()) {
							flag = false;
						}
					}
					}
				} else if (src == btn_Pop) {
					boolean flag = true;
					int i = 0;
					if(selList.getSize()>0){
					while (flag) {
						if (selectedList.isSelectedIndex(i)) {
							String item = selList.get(selectedList
									.getSelectedIndex());
							selList.remove(selectedList.getSelectedIndex());
							user.addElement(item);
						} else {
							i++;
						}
						if (i == selList.size()) {
							flag = false;
						}
					}
					}
				} else if (src == btn_Ok) {
					Vector<String> inviteList = new Vector<>();
					if (selList.getSize()>0){
					for (int i = 0; i < selList.getSize(); i++) {
						int idx = selList.get(i).indexOf("(");
						String id = selList.get(i).substring(0, idx);
						inviteList.add(id);
					}
					for (String id : cData.getGui().getChat_Room_Gui().getRoom().getRoom_User_List().keySet()){
						if (inviteList.contains(id) || !cData.getWaitting_User_List().containsKey(id)){
							inviteList.remove(id);
						}
					}
					SendData send = new SendData(cData.USER_INVITE, inviteList,roomNum, cData.getMyID());// 박초대인원과 방번호 ,초대하는 사람 이름
					showMsg("선택한 유저를 초대합니다.");
					cData.sendTarget(send);
					dispose();
					}else{
						showMsg("초대할 유저가 없습니다.");
					}
				} else if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				} else if (src == btn_Search) {
					if (!tfSearch.getText().equals("")) {
						user.removeAllElements();
						HashMap<String, User_Info> roomUser = cData.getGui().getChat_Room_Gui().getRoom().getRoom_User_List();
						for (String id : waiting.keySet()) {
							if ((id.contains(tfSearch.getText()) || waiting.get(id).getNickName()
									.contains(tfSearch.getText()))
									&& !(roomUser.containsKey(id))) {
								if (!selList.contains(id)) {
									user.addElement(id + "("+ waiting.get(id).getNickName()+ ")");
								}
							}
						}
					}
				}
			}
		};
		btn_Put.addActionListener(a_listener);
		btn_Pop.addActionListener(a_listener);
		btn_Ok.addActionListener(a_listener);
		btn_Cancel.addActionListener(a_listener);
		btn_Search.addActionListener(a_listener);
		btn_Exit.addActionListener(a_listener);

		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	
	
	
	
	
	
	
	
	
	private void addListeners_Wisper() { // // 명환26
		class ThreadRun extends Thread {
			@Override
			public void run() {
				Document doc = tfSearch.getDocument();
				doc.addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						documentChanged();
					}
				});
			}

			private void documentChanged() {
				user.removeAllElements();
				if (tfSearch.getText().length() == 0) {
					for (String id : online.keySet()) { // 유저 목록 최신화.
						if (!id.equals(cData.getMyID())) {
							if (!selList.contains(id)) {
								user.addElement(id + "("
										+ online.get(id).getNickName() + ")");
							}
						}
					}
				}
			}
		}
		ListSelectionListener l_Listener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent le) {
				String userId = userList.getSelectedValue();
				int idx = userId.indexOf("(");
				whisperid = userId.substring(0, idx);

			}
		};
		ActionListener a_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btn_whisper) {
					// String userId = userList.getSelectedValue();
					// int idx = userId.indexOf("(");
					// wisperid = user
					cData.getGui().getChat_Room_Gui().getTfWhisperId()
							.setText(whisperid);
					dispose();

				} else if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				} else if (src == btn_Search || src == tfSearch) {
					if (!tfSearch.getText().equals("")) {
						user.removeAllElements();
						for (String id : online.keySet()) {
							if (id.contains(tfSearch.getText())
									|| online.get(id).getNickName()
											.contains(tfSearch.getText())) {
								if (!selList.contains(id)) {
									user.addElement(id + "("+ online.get(id).getNickName()+ ")");
								}
							}
						}
					}
				}
			}
		};
	
		btn_Cancel.addActionListener(a_listener);
		btn_Search.addActionListener(a_listener);
		userList.addListSelectionListener(l_Listener);
		btn_whisper.addActionListener(a_listener);
		tfSearch.addActionListener(a_listener);
		btn_Exit.addActionListener(a_listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
		ThreadRun t = new ThreadRun();
		t.start();
	}
	private void addListeners_Note() { // // 명환26
		class ThreadRun extends Thread {
			@Override
			public void run() {
				Document doc = tfSearch.getDocument();
				doc.addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						documentChanged();
					}
				});
			}

			private void documentChanged() {
				user.removeAllElements();
				if (tfSearch.getText().length() == 0) {
					for (String id : online.keySet()) { // 유저 목록 최신화.
						if (!id.equals(cData.getMyID())) {
							if (!selList.contains(id)) {
								user.addElement(id + "("
										+ online.get(id).getNickName() + ")");
							}
						}
					}
				}
			}
		}
		ListSelectionListener l_Listener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent le) {
				String userId = userList.getSelectedValue();
				int idx = userId.indexOf("(");
				whisperid = userId.substring(0, idx);

			}
		};
		ActionListener a_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btn_whisper) {
					String userId = userList.getSelectedValue();
					int idx = userId.indexOf("(");
					String noteId = userId.substring(0,idx);
					new Send_Note_GUI(cData, noteId);
					dispose();
				} else if (src == btn_Cancel || src == btn_Exit) {
					dispose();
				} else if (src == btn_Search || src == tfSearch) {
					if (!tfSearch.getText().equals("")) {
						user.removeAllElements();
						for (String id : online.keySet()) {
							if (id.contains(tfSearch.getText())
									|| online.get(id).getNickName()
											.contains(tfSearch.getText())) {
								if (!selList.contains(id)) {
									user.addElement(id + "("
											+ online.get(id).getNickName()
											+ ")");
								}
							}
						}
					}
				}
			}
		};
	
		btn_Cancel.addActionListener(a_listener);
		btn_Search.addActionListener(a_listener);
		userList.addListSelectionListener(l_Listener);
		btn_whisper.addActionListener(a_listener);
		tfSearch.addActionListener(a_listener);
		btn_Exit.addActionListener(a_listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
		ThreadRun t = new ThreadRun();
		t.start();
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

	// --------------------------------------------------------------------------------

	private void showFrame() {
		setSize(680, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true); //해림 수정 0701 창 프레임 없앰
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	// 해림수정 0703===================================================
	private void showFrame_Wisper() {
		setSize(300, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true); //해림 수정 0701 창 프레임 없앰
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void updateInviteUser(HashMap<String, User_Info> waiting) { // 온라인 유저 업데이트!!!!
		user.removeAllElements();
		for (String id : waiting.keySet()) { // 유저 목록 최신화.
			if (!id.equals(cData.getMyID())) {
				if (!selList.contains(id)) {
					user.addElement(id + "(" + waiting.get(id).getNickName()
							+ ")");
				}
			}
		}

	}

	private class MyListCellRenderer extends DefaultListCellRenderer { // 다오버라이드하기
																		// 힘들기때문에
		// 상속받아서 사용
		@Override
		public Component getListCellRendererComponent( // 리스트의 갯수만큼 컴포넌트 호출
				// 보여지는 화면이 바뀔때마다 컴포넌트호출
				JList list, Object value, int idx, // 리스트,아이템들,
				// 각 인덱스,선택이되었는가,포커스받앗는가
				boolean isSelected, boolean cellHasfocus) {
			JLabel lblItem = new JLabel(value.toString());
			lblItem.setOpaque(true);
			if (isSelected) {
				lblItem.setBackground(Color.YELLOW);
				lblItem.setText(lblItem.getText() + "    (선택)");
			}
			// if (cellHasfocus) {
			// lblItem.setBackground(Color.GREEN);
			// }
			return lblItem;
			// return new JButton(value.toString()); //리스트가 버튼으로호출
			// 이렇게사용된 버튼리스트들은 보여지기만가능 -> 리스너,이벤트적용X
			// 리스너,이벤트 적용하려면 ListSelectionListener를 써서 사용해야함
		}
	}
	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}
}
