import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Waitting_Room_GUI extends JFrame implements iProtocol {

	private HashMap<Integer, Room_PnlMaker> pnlRoomList;
	private List<User_LblMaker> userList;

	private ClientThread ct;

	public JTextField getTf_Whisper_User() {
		return tf_Whisper_User;
	}

	public void setTf_Whisper_User(JTextField tf_Whisper_User) {
		this.tf_Whisper_User = tf_Whisper_User;
	}

	private JTextField tf_Search_Room;
	private JTextField tf_Search_User;
	private JTextField tf_Whisper_User;
	private JTextField tf_Send_Chat;

	// private JTextArea ta_Waitting_Room_Chat;

	private JLabel lblWaitting;
	private JLabel lbl_Search_Room;
	// private JLabel lbl_Menu;
	private JButton lbl_Menu;
	private JLabel lblTitle;
	private JButton lbl_Search_User;
	private JScrollPane spnlTa;

	private JButton btn_Open_Note_List;
	private JButton btn_Make_Room;
	private JButton btn_Set_Category;
	private JButton btn_Send_Msg;
	private JButton btn_Search_Room;
	private JLabel btn_Search_User;
	private JButton btn_Change_User_List;
	private JButton btn_Show_Chat_List;
	private JButton btn_Friend;
	private JButton btn_Fast_Enter_Room; // 0703박상욱
	private JButton btn_Exit;

	private JPanel pnl_Center_Center;

	private JPanel pnl_Center;
	private JPanel pnl_East;
	private JPanel pnl_East_CENTER;
	private JPanel pnl_East_Cen_Wait;
	private JPanel pnl_East_Cen_Online;
	private JPanel pnl_East_Cen_Friend;
	private JPanel pnl_East_Cen_Black; // 0704박상욱
	private JPanel pnl_User_List_Waitting;
	private JPanel pnl_User_List_All;
	private JPanel pnl_Friend_List;
	private JPanel pnl_Black_List;
	private JPanel pnlTotal;

	private JPanel pnl_User_List;
	private CardLayout card;
	private ClientData cData;
	private JScrollPane spCenter;
	private JPanel pnl_User_List_On;
	private JPanel pnl_User_List_Off;
	private JPanel pnlCard;

	private JTextPane tp_Waitting_Room_Chat;
	private StyledDocument doc;
	private SimpleAttributeSet left;
	private SimpleAttributeSet right;

	// 김명환 수정 멤버변수 추가
	private JPopupMenu pMenu;
	private JMenuItem miWhisper;
	private JMenuItem miShowProfile;
	private JMenuItem miSendNote;
	private JMenuItem miGPS;

	private JPopupMenu mMenu;
	private JMenuItem miProfileSet;
	private JMenuItem miFontSet;
	private JMenuItem miLogout;
	private JMenuItem miWithdrawal;

	private JPanel pnlUserCover_w;
	private JPanel pnlUserCover_a;
	private JPanel pnlUserCover_f;
	private JPanel pnlUserCover_b;

	private JPanel pnlRoomCover;
	private JPanel pnlRoom;
	private Friend_Invite_List friend_Invite_List_GUI;
	private Create_Chat_Room_GUI create_Chat_Room_GUI;
	private JComboBox<String> cb_Set_Blocked;
	private String blocked[] = { "전체", "공개", "비공개" };
	private String[] Category = { "번호", "요리", "스포츠", "게임", "음악" };
	private JComboBox<String> cb_Set_Category;
	private JTabbedPane tbp;
	private int count;

	private Point ptFirst;

	// 해림수정 0703
	private Color bgColor;
	private Color btnColor;
	private Color bgGColor;
	private Color edgeColor;
	private Font font;
	private TitledBorder titleBorder;

	// 해림수정0707
	private JScrollPane scrollPeople_w;
	private JScrollPane scrollPeople_f;
	private JScrollPane scrollPeople_b;

	// public Waitting_Room_GUI(ClientThread ct) {
	// this.ct = ct;
	// init();
	// setDisplay();
	// addListeners();
	// showFrame();
	// }
	public Waitting_Room_GUI(ClientData cData) {
		System.out.println("체크1");
		this.cData = cData;
		init();
		setDisplay();
		// updateRoomPnlList(cData.getChat_Room_List());
		// updateUserLbl_Waititing_List(cData.getWaitting_User_List());
		addListeners();
		showFrame();
	}

	private void init() {
		// 해림수정 0703
		bgColor = new Color(0xFFFFFF); // 하얀색
		btnColor = new Color(0xE59BB9); // 진한분홍
		bgGColor = new Color(0xFFEBFF); // 엄청연한분홍
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		font = new Font("HY나무B", Font.PLAIN, 15);
		UIManager.put("TabbedPane.selected", btnColor);

		tbp = new JTabbedPane();
		tbp.setBackground(bgGColor);
		tbp.setFont(font);
		btn_Set_Category = new JButton("카테고리");
		btn_Set_Category.setFont(font);
		pnl_User_List_On = new JPanel(new GridLayout(0, 1));
		pnl_User_List_On.setBackground(bgColor);
		pnl_User_List_On.setFont(font);

		pnl_East_CENTER = new JPanel();
		pnlRoomCover = new JPanel(new BorderLayout());
		pnlUserCover_w = new JPanel(new BorderLayout());
		pnlUserCover_a = new JPanel(new BorderLayout()); // 박상욱 수정
		pnlUserCover_f = new JPanel(new BorderLayout());
		pnlUserCover_b = new JPanel(new BorderLayout());// 0704박상욱

		tp_Waitting_Room_Chat = new JTextPane();
		tp_Waitting_Room_Chat.setEditable(false);
		doc = tp_Waitting_Room_Chat.getStyledDocument();
		left = new SimpleAttributeSet();// 박상욱 0626 폰트 설정

		right = new SimpleAttributeSet();// 박상욱 0626 폰트 설정
		settingFont(cData.getMyColor(), cData.getOtherColor(), cData.getFont());

		pnl_Center = new JPanel(new BorderLayout());
		pnl_Center.setBorder(new BevelBorder(BevelBorder.RAISED));

		pnl_User_List_All = new JPanel(new BorderLayout());
		pnl_User_List_Waitting = new JPanel(new GridLayout(0, 1));
		scrollPeople_w = setting_JScroll(pnlUserCover_w, 250, 605); // 스크롤
		scrollPeople_w.setBorder(titleBorder = new TitledBorder(new LineBorder(
				btnColor, 2), "대기실"));
		scrollPeople_w.setBackground(bgColor);
		titleBorder.setTitleFont(font);
		pnl_User_List_Waitting.setBackground(bgColor);
		pnl_Friend_List = new JPanel(new GridLayout(0, 1));
		scrollPeople_f = setting_JScroll(pnlUserCover_f, 250, 605);
		scrollPeople_f.setBorder(titleBorder = new TitledBorder(new LineBorder(
				btnColor, 2), "친구목록"));
		scrollPeople_f.setBackground(bgColor);
		titleBorder.setTitleFont(font);
		pnl_Friend_List.setBackground(bgColor);
		pnl_Black_List = new JPanel(new GridLayout(0, 1));
		scrollPeople_b = setting_JScroll(pnlUserCover_b, 250, 605);
		scrollPeople_b.setBorder(titleBorder = new TitledBorder(new LineBorder(
				btnColor, 2), "블랙리스트"));
		scrollPeople_b.setBackground(bgColor);
		titleBorder.setTitleFont(font);
		pnl_Black_List.setBackground(bgColor);
		pnlTotal = new JPanel(new BorderLayout());

		tf_Search_Room = new JTextField(10);
		tf_Search_User = new JTextField(10);
		tf_Send_Chat = new JTextField(20);
		tf_Send_Chat.setDocument(new JTextFieldLimit(50));
		tf_Whisper_User = new JTextField(5);
		lbl_Menu = new JButton("MENU");
		lbl_Menu.setBorder(new LineBorder(null));
		lbl_Menu.setFont(font);
		lbl_Menu.setForeground(bgColor);
		lbl_Menu.setBackground(btnColor); // 해림수정0703
		lbl_Menu.setPreferredSize(new Dimension(70, 30));
		lbl_Search_Room = new JLabel("방이름");
		lbl_Search_Room.setForeground(Color.WHITE);
		lbl_Search_Room.setFont(font);
		lbl_Search_User = new JButton("사용자검색");
		lbl_Search_User.setForeground(Color.WHITE);
		lbl_Search_User.setFont(font);
		lbl_Search_User.setBackground(btnColor); // 해림수정0703
		lbl_Search_User.setPreferredSize(new Dimension(100, 30));// 해림수정0703
		lbl_Search_User.setBorder(new LineBorder(null));
		lblWaitting = new JLabel(" 대기실");
		lblWaitting.setFont(font);

		lblTitle = new JLabel("채팅방" + "      (" + cData.getMyID() + ")님 환영합니다");
		lblTitle.setFont(font);

		btn_Change_User_List = new JButton("전체유저");
		btn_Change_User_List.setFont(font);
		btn_Change_User_List.setBackground(btnColor); // 해림수정0703
		btn_Change_User_List.setPreferredSize(new Dimension(70, 30));// 해림수정0703

		btn_Make_Room = new JButton("방생성");
		btn_Make_Room.setForeground(Color.WHITE);
		btn_Make_Room.setFont(font);
		btn_Make_Room.setBackground(btnColor); // 해림수정0703
		btn_Make_Room.setPreferredSize(new Dimension(70, 30));// 해림수정0703
		btn_Make_Room.setBorder(new LineBorder(null));

		btn_Fast_Enter_Room = new JButton("빠른입장"); // 0703박상욱
		btn_Fast_Enter_Room.setForeground(Color.WHITE);
		btn_Fast_Enter_Room.setFont(font);
		btn_Fast_Enter_Room.setBackground(btnColor); // 해림수정0703
		btn_Fast_Enter_Room.setBorder(new LineBorder(Color.GRAY, 1));// 해림수정0703
		btn_Fast_Enter_Room.setPreferredSize(new Dimension(90, 30));// 해림수정0703
		btn_Fast_Enter_Room.setBorder(new LineBorder(null));
		btn_Fast_Enter_Room.setMnemonic('Q');

		btn_Open_Note_List = new JButton("쪽지함");
		btn_Open_Note_List.setForeground(Color.WHITE);
		btn_Open_Note_List.setFont(font);
		btn_Open_Note_List.setPreferredSize(new Dimension(50, 40)); // 박상욱 수정
		btn_Open_Note_List.setBackground(btnColor); // 해림수정0703
		btn_Open_Note_List.setPreferredSize(new Dimension(70, 30));// 해림수정0703
		btn_Open_Note_List.setBorder(new LineBorder(null));

		btn_Search_Room = new JButton("방검색");
		btn_Search_Room.setForeground(Color.WHITE);
		btn_Search_Room.setFont(font);
		btn_Search_Room.setBackground(btnColor); // 해림수정0703
		btn_Search_Room.setPreferredSize(new Dimension(90, 30));// 해림수정0703
		btn_Search_Room.setBorder(new LineBorder(null));

		btn_Send_Msg = new JButton("전송");
		btn_Send_Msg.setForeground(Color.WHITE);
		btn_Send_Msg.setFont(font);
		btn_Send_Msg.setPreferredSize(new Dimension(50, 40)); // 박상욱 수정
		btn_Send_Msg.setBackground(btnColor); // 해림수정0703
		btn_Send_Msg.setPreferredSize(new Dimension(70, 30));// 해림수정0703
		btn_Send_Msg.setBorder(new LineBorder(null));

		btn_Show_Chat_List = new JButton("방보기");
		btn_Show_Chat_List.setForeground(Color.WHITE);
		btn_Show_Chat_List.setFont(font);
		btn_Show_Chat_List.setPreferredSize(new Dimension(80, 40)); // 박상욱 수정
		btn_Show_Chat_List.setBackground(btnColor); // 해림수정0703
		btn_Show_Chat_List.setPreferredSize(new Dimension(90, 30));// 해림수정0703
		btn_Show_Chat_List.setBorder(new LineBorder(null));

		btn_Friend = new JButton("친구요청");
		btn_Friend.setForeground(Color.WHITE);
		btn_Friend.setFont(font);
		btn_Friend.setPreferredSize(new Dimension(90, 40)); // 박상욱 수정
		btn_Friend.setBackground(btnColor); // 해림수정0703
		btn_Friend.setPreferredSize(new Dimension(70, 30));// 해림수정0703
		btn_Friend.setBorder(new LineBorder(null));

		pnlRoomList = new HashMap<Integer, Room_PnlMaker>();
		lblWaitting.setPreferredSize(new Dimension(50, 28));
		// spnlTa = new JScrollPane(ta_Waitting_Room_Chat);

		btn_Exit = new JButton(" X ");
		btn_Exit.setPreferredSize(new Dimension(50, 25));
		btn_Exit.setBorder(new LineBorder(Color.WHITE, 1));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);

		// 김명환 수정(팝업)
		pMenu = new JPopupMenu("MENU");
		pMenu.setFont(font);
		pMenu.setForeground(Color.WHITE);
		pMenu.setBorder(new LineBorder(null));
		miWhisper = new JMenuItem("귓속말");
		miWhisper.setFont(font);
		miShowProfile = new JMenuItem("프로필보기");
		miShowProfile.setFont(font);
		miSendNote = new JMenuItem("쪽지전송");
		miSendNote.setFont(font);
		miGPS = new JMenuItem("위치찾기");
		miGPS.setFont(font);

		pMenu.add(miWhisper);
		pMenu.add(miShowProfile);
		pMenu.add(miSendNote);
		pMenu.add(miGPS);

		mMenu = new JPopupMenu("MENU");
		mMenu.setBorder(new LineBorder(null));
		mMenu.setForeground(Color.WHITE);
		mMenu.setFont(font);
		miProfileSet = new JMenuItem("프로필설정");
		miProfileSet.setFont(font);
		miFontSet = new JMenuItem("폰트설정");
		miFontSet.setFont(font);
		miLogout = new JMenuItem("로그아웃");
		miLogout.setFont(font);
		miWithdrawal = new JMenuItem("회원탈퇴");
		miWithdrawal.setFont(font);

		mMenu.add(miProfileSet);
		mMenu.add(miFontSet);
		mMenu.add(miLogout);
		mMenu.add(miWithdrawal);

		// //////////////////////////////// 21명환
		cb_Set_Category = new JComboBox<String>(Category);
		cb_Set_Category.setFont(font);
		cb_Set_Category.setBackground(bgColor);
		cb_Set_Blocked = new JComboBox<String>(blocked);
		cb_Set_Blocked.setFont(font);
		cb_Set_Blocked.setBackground(bgColor);
		// /////////////////////////////////21명환 여까지

	}

	public void updatePnl(JPanel pnl) {
		pnl.invalidate();
		pnl.repaint();
		pnl.updateUI();
	}

	private void setDisplay() {
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btn_Exit, BorderLayout.EAST);
		pnlTop.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlTop.setBackground(edgeColor);

		JPanel pnl_Center_North = new JPanel(new GridLayout(0, 1));
		JPanel pnl_Center_North_1 = new JPanel(new BorderLayout());
		JPanel pnl_Center_North_2 = new JPanel(new BorderLayout());
		pnl_Center_North.setBorder(new LineBorder(btnColor, 2));
		pnl_Center_North_1.setBorder(new LineBorder(btnColor, 1));
		pnl_Center_North_1.add(lblWaitting, BorderLayout.WEST);
		pnl_Center_North_1.setBackground(bgColor);

		JPanel pnl_room_Button = new JPanel();
		pnl_room_Button.add(btn_Make_Room); // 0703 박상욱
		pnl_room_Button.add(btn_Fast_Enter_Room);// 0703 박상욱
		pnl_room_Button.setBackground(bgColor);
		pnl_Center_North_1.add(pnl_room_Button, BorderLayout.EAST);// 0703 박상욱
		pnl_Center_North_2.add(cb_Set_Category, BorderLayout.WEST);// /////////21명환
		pnl_Center_North_2.add(cb_Set_Blocked, BorderLayout.CENTER);// ////////////21명환
		JPanel pnl_Center_North_2_1 = new JPanel();
		pnl_Center_North_2_1.add(lbl_Search_Room);
		pnl_Center_North_2_1.add(tf_Search_Room);
		pnl_Center_North_2_1.add(btn_Search_Room);
		pnl_Center_North_2_1.setBackground(bgColor);
		pnl_Center_North_2.add(pnl_Center_North_2_1, BorderLayout.EAST);

		pnl_Center_North.add(pnl_Center_North_1);
		pnl_Center_North.add(pnl_Center_North_2);

		JPanel pnl_Center_South = new JPanel(new BorderLayout());
		JPanel pnl_Center_South_1 = new JPanel();
		pnl_Center_South_1.setBackground(bgColor);
		JPanel pnl_Center_South_2 = new JPanel();
		pnl_Center_South_2.setBackground(bgColor);
		JScrollPane sp = new JScrollPane(tp_Waitting_Room_Chat,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(600, 250));

		pnl_Center_South_1.add(sp);
		pnl_Center_South_1.setBackground(bgGColor); // 대기채팅방컬러

		// pnl_Center_South_1.add(spnlTa);
		pnl_Center_South_2.add(tf_Whisper_User);
		pnl_Center_South_2.add(tf_Send_Chat);
		pnl_Center_South_2.add(btn_Send_Msg);
		pnl_Center_South_2.setBackground(bgGColor); // 귓속말컬러

		pnl_Center_South.add(pnl_Center_South_1, BorderLayout.CENTER);
		pnl_Center_South.add(pnl_Center_South_2, BorderLayout.SOUTH);
		// ///////////////////////////////////////////////////////////////////////
		// 채팅방 목록 유저///////////////////////////////
		pnl_Center_Center = new JPanel(new BorderLayout());
		pnlRoom = new JPanel(new GridLayout(0, 1));
		Iterator<Integer> itr = cData.getChat_Room_List().keySet().iterator();
		JScrollPane scrollRoom = setting_JScroll(pnlRoomCover, 100, 330);
		while (itr.hasNext()) {
			Chat_Room some = cData.getChat_Room_List().get(itr.next());
			Room_PnlMaker pnlsome = new Room_PnlMaker(some, cData);// 상욱 수정
			pnlRoom.add(pnlsome);
		}
		pnlRoomCover.add(pnlRoom, BorderLayout.NORTH);
		pnlRoomCover.setBackground(bgColor); // 대기창배경
		pnl_Center_Center.add(scrollRoom, BorderLayout.NORTH);
		pnl_Center_Center.setPreferredSize(new Dimension(300, 300));
		pnl_Center.add(pnl_Center_Center, BorderLayout.CENTER);
		pnl_Center_Center.setBackground(bgGColor); // 대기창 배경 밑
		// //////////////////////////////////////////
		pnl_Center.add(pnl_Center_South, BorderLayout.SOUTH);
		pnl_Center.add(pnl_Center_Center, BorderLayout.CENTER);
		pnl_Center.add(pnl_Center_North, BorderLayout.NORTH);
		// //////////////////////////////////////////////////////////
		pnl_East = new JPanel(new BorderLayout());
		pnl_East.setPreferredSize(new Dimension(370, 723));// 0704박상욱
		JPanel pnl_East_North = new JPanel(new GridLayout(0, 1));
		// JPanel pnl_East_North_1 = new JPanel(new BorderLayout());
		JPanel pnl_East_North_1 = new JPanel();
		pnl_East_North_1.add(btn_Show_Chat_List);
		pnl_East_North_1.add(lbl_Menu);
		pnl_East_North_1.add(btn_Open_Note_List);
		pnl_East_North_1.add(btn_Friend);
		pnl_East_North_1.setBackground(bgColor);

		JPanel pnl_East_North_2 = new JPanel();
		// pnl_East_North_1.add(btn_Show_Chat_List, BorderLayout.WEST);
		// pnl_East_North_1.add(lbl_Menu, BorderLayout.CENTER);
		// pnl_East_North_1.add(btn_Open_Note_List, BorderLayout.EAST);
		pnl_East_North_2.add(lbl_Search_User, BorderLayout.WEST);
		pnl_East_North_2.add(tf_Search_User, BorderLayout.CENTER);
		pnl_East_North_2.setBackground(bgColor);
		// pnl_East_North_2.add(btn_Search_User, BorderLayout.EAST); //박상욱 0701

		pnl_East_North.add(pnl_East_North_1);
		pnl_East_North.add(pnl_East_North_2);
		// //////////////////////////////////////////////////////////// 박상욱 수정
		// 대기실 유저///////////////////////////////
		// card = new CardLayout(); //욱상박
		// pnlCard = new JPanel(card);//욱상박

		for (String id : cData.getWaitting_User_List().keySet()) {
			if (!id.equals(cData.getMyID())) {
				User_Info userInfo = cData.getWaitting_User_List().get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl_User_List_Waitting.add(lbl);

			}
		}
		pnlUserCover_w.add(pnl_User_List_Waitting, BorderLayout.NORTH);

		pnlUserCover_w.setBackground(bgColor); // 대기자배경
		// btn_Change_User_List.setText("대기유저");욱삭방
		pnl_East_Cen_Wait = new JPanel(new BorderLayout());
		pnl_East_Cen_Wait.add(scrollPeople_w, BorderLayout.NORTH);

		pnl_East_Cen_Wait.setPreferredSize(new Dimension(300, 300));
		tbp.add("대기유저", pnl_East_Cen_Wait);
		pnl_East_Cen_Wait.setBackground(bgColor); // 대기자배경

		// //////////////////////////////////////////////////////////////전체
		// 유저/////////////////////////////////////////////

		JScrollPane scrollPeople_a = setting_JScroll(pnlUserCover_a, 250, 605);// 스크롤
		scrollPeople_a.setBorder(titleBorder = new TitledBorder(new LineBorder(
				btnColor, 2), "온라인"));// 세팅
		titleBorder.setTitleFont(font);
		scrollPeople_a.setBackground(bgColor);

		pnl_User_List_All.add(pnl_User_List_On, BorderLayout.NORTH);
		pnl_User_List_All.setBackground(bgColor);
		pnlUserCover_a.add(pnl_User_List_All, BorderLayout.NORTH);
		pnlUserCover_a.setBackground(bgColor);
		pnl_East_Cen_Online = new JPanel(new BorderLayout());
		pnl_East_Cen_Online.add(scrollPeople_a, BorderLayout.NORTH);
		pnl_East_Cen_Online.setPreferredSize(new Dimension(300, 300));
		pnl_East_Cen_Online.setBackground(bgColor);
		tbp.add("온라인유저", pnl_East_Cen_Online);

		// //////////////////////////////////////////////////////////////친구

		for (String id : cData.getFriend_User_List().keySet()) {
			if (!id.equals(cData.getMyID())) {
				User_Info userInfo = cData.getFriend_User_List().get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl_Friend_List.add(lbl);
			}
		}
		pnlUserCover_f.add(pnl_Friend_List, BorderLayout.NORTH);
		pnlUserCover_f.setBackground(bgColor);
		pnl_East_Cen_Friend = new JPanel(new BorderLayout());
		pnl_East_Cen_Friend.add(scrollPeople_f, BorderLayout.NORTH);
		pnl_East_Cen_Friend.setPreferredSize(new Dimension(300, 300));
		pnl_East_Cen_Friend.setBackground(bgColor);
		tbp.add("친구유저", pnl_East_Cen_Friend);

		// ////////////////////////////////////////////////////////////////블랙리스트
		for (String id : cData.getBlack_User_List()) {// 0707박상욱 수정
			User_Info userInfo = cData.getOnLine_User_List().get(id);
			String nickName = userInfo.getNickName();
			String userId = userInfo.getId();
			User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
			// User_LblMaker lbl = new User_LblMaker(id, cData);
			pnl_Black_List.add(lbl);
		}
		pnlUserCover_b.add(pnl_Black_List, BorderLayout.NORTH);
		pnlUserCover_b.setBackground(bgColor);
		pnl_East_Cen_Black = new JPanel(new BorderLayout());
		pnl_East_Cen_Black.add(scrollPeople_b, BorderLayout.NORTH);
		pnl_East_Cen_Black.setPreferredSize(new Dimension(300, 300));
		pnl_East_Cen_Black.setBackground(bgColor);
		tbp.add("블랙리스트", pnl_East_Cen_Black);
		// ///////////////////////////////////////////////////////////////////////////////////
		// pnlCard.add(pnl_East_CENTER2,"전체유저");
		pnl_East.add(tbp, BorderLayout.CENTER);
		pnl_East.setBackground(bgColor);
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		pnl_East.add(pnl_East_North, BorderLayout.NORTH);
		// pnl_East.add(btn_Change_User_List, BorderLayout.SOUTH);
		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnl_East, BorderLayout.EAST);
		pnlTotal.setBorder(new LineBorder(edgeColor, 5));

		add(pnlTotal, BorderLayout.CENTER);
		// ////////////////////////////////////
		// tbp.add(arg0)
	}

	private void addListeners() {
		System.out.println("check1");
		class ThreadRun extends Thread {
			@Override
			public void run() {
				Document doc = tf_Search_User.getDocument();
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
				Document doc2 = tf_Search_Room.getDocument();
				doc2.addDocumentListener(new DocumentListener() {
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
				if (tf_Search_User.getText().length() == 0) {
					int tbp_idx = tbp.getSelectedIndex();
					if (tbp_idx == 0) {
						updateUserLbl_Waititing_List(cData
								.getWaitting_User_List());
					} else if (tbp_idx == 1) {
						updateUserLbl_Total_List(cData.getOnLine_User_List());
					} else if (tbp_idx == 2) {
						updateUserLbl_Friend_List(cData.getFriend_User_List()); // 박상욱
					} else if (tbp_idx == 3) {
						updateUserLbl_Black_List(cData.getBlack_User_List());
					}
				}
				if (tf_Search_Room.getText().length() == 0) {
					updateRoomPnlList(cData.getChat_Room_List());
				}
			}
		}
		ThreadRun t_Run = new ThreadRun();
		t_Run.start();

		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object src = e.getSource();

				if (src == btn_Show_Chat_List) {
					Dimension some = getSize();

					int x = getX();
					int y = getY();
					int pre_x = getX();
					int pre_y = getY();
					if (pnl_Center.isShowing()) {
						pnlTotal.remove(pnl_Center);
						pack();
						tp_Waitting_Room_Chat.add(getSize().toString(),
								new JLabel());
						pre_x = pre_x + 514;
					} else {
						pnlTotal.add(pnl_Center, BorderLayout.CENTER);
						pack();
						tp_Waitting_Room_Chat.add(getSize().toString(),
								new JLabel());
						pre_x = pre_x - 514;

					}
					setLocation(pre_x, pre_y);

				} else if (src == btn_Send_Msg || src == tf_Send_Chat) {// 박상욱
																		// 김명환
																		// (채팅)
																		// (귓속말)수정
					String wc = tf_Whisper_User.getText();
					String cg = tf_Send_Chat.getText();
					if (cg.trim().length() != 0) {
						if (wc.length() == 0) {
							if (cg.equals("/주사위") || cg.equals("/dice")
									|| cg.equals("/Dice") || cg.equals("/다이스")) {
								System.out.println("다이스 굴러갑니다요");
								Random random = new Random();
								String msg = cData.getMyID() + "(님)의 주사위 : ("
										+ (random.nextInt(6) + 1) + ")" + ", "
										+ "(" + (random.nextInt(6) + 1) + ")";
								SendData send = new SendData(
										cData.BROADCAST_MSG, msg);
								cData.sendTarget(send);
							} else {
								String str = tf_Send_Chat.getText();
								String newstr = "";
								int size = str.length();
								int rum = size / 10;
								if (rum != 0) {
									for (int i = 0; i < rum; i++) {
										newstr += str.substring(0, 10) + "\n";
										str = str.substring(10);
									}
									newstr += str;
								} else {
									newstr = str;
								}
								newstr += "\n";
								String msg = cData.getMyID() + "(님)의 메세지 : "
										+ newstr;
								SendData send = new SendData(BROADCAST_MSG, msg);
								cData.sendTarget(send);// 상욱 수정
								System.out.println("보냈다 메세지");
							}

						} else if (!wc.equals("ID") && wc.length() != 0
								&& !cData.getMyID().equals(wc)) {
							if (tf_Send_Chat.getText().contains("/흔들기")
									|| tf_Send_Chat.getText().contains("/벚꽃")) {
								showMsg("(귓속말 / 친구채팅)에서 \n사용할수없는 기능입니다.");
							} else {

								String str = tf_Send_Chat.getText();
								String newstr = "";
								int size = str.length();
								int rum = size / 10;
								if (rum != 0) {
									for (int i = 0; i < rum; i++) {
										newstr += str.substring(0, 10) + "\n";
										str = str.substring(10);
									}
									newstr += str;
								} else {
									newstr = str;
								}
								newstr += "\n";
						
								// /////////////////////////
								String whisperMsg = cData.getMyID() + "(님)이"
										+ wc + "에게 :" +newstr;
								String sendMan = cData.getMyID();
								SendData whisperSend = new SendData(
										cData.WHISPER, whisperMsg, wc);
								System.out.println(whisperMsg);

								cData.sendTarget(whisperSend);
							}


						} else if (wc.equals(cData.getMyID())) {

							showMsg("자기자신에게 귓속말을 보낼 수 없습니다.");
						} else {
							showMsg("자기자신에게 귓속말을 보낼 수 없습니다.");
						}
						// ////여기까지
						tf_Send_Chat.setText("");
						tf_Send_Chat.requestFocus();
					}
				} else if (src == btn_Make_Room) {
					new Create_Chat_Room_GUI(cData, Waitting_Room_GUI.this);
				} else if (src == btn_Fast_Enter_Room) {// 0703박상욱
					SendData senddData = new SendData(CHATROOM_FAST_ENTER);
					cData.sendTarget(senddData);
				} else if (src == lbl_Menu) { // 김명환 수정
					System.out.println("" + e.getSource());
					mMenu.show((Component) e.getSource(), 0, 20);
				} else if (src == btn_Open_Note_List) {// 김명환 수정
					SendData sendData = new SendData(OPEN_NOTEBOX);
					cData.sendTarget(sendData);
					// 06_27민지영=====================================================================
				} else if (src == btn_Search_User) {
					String str = tf_Search_User.getText();
					if (str != null) {
						cData.sendTarget(new SendData(SEARCH_USER_LOCATION, str));
					}
				} else if (src == btn_Search_Room) {
					String str = tf_Search_Room.getText();
					TreeMap<Integer, Chat_Room> sorttarget = cData
							.getChat_Room_List();
					if (tf_Search_Room.getText().trim().length() != 0) {
						Iterator<Integer> itr = sorttarget.keySet().iterator();
						TreeMap<Integer, Chat_Room> list = new TreeMap<Integer, Chat_Room>();
						while (itr.hasNext()) {
							Chat_Room some = sorttarget.get(itr.next());
							if (some.getTitle().contains(str)) {
								list.put(some.getRoomNumber(), some);
							}
						}
						updateRoomPnlList(list);
					} else {
						updateRoomPnlList(sorttarget);
					}
				} else if (src == lbl_Search_User) {
					String str = tf_Search_User.getText();
					if (str != null || !str.trim().equals("")) {
						HashMap<String, User_Info> map = cData
								.getOnLine_User_List();
						count = 0;
						for (String id : map.keySet()) {
							if (id.contains(str)
									|| map.get(id).getNickName().contains(str)) {
								if ((tbp.getSelectedIndex() == 0)) {
									setting_Search_User(id, str,
											cData.getWaitting_User_List(), map,
											pnl_User_List_Waitting,
											pnl_East_Cen_Wait);
								} else if ((tbp.getSelectedIndex() == 1)) {
									setting_Search_User(id, str,
											cData.getOnLine_User_List(), map,
											pnl_User_List_On,
											pnl_East_Cen_Online);
								} else if ((tbp.getSelectedIndex() == 2)) {
									setting_Search_User(id, str,
											cData.getFriend_User_List(), map,
											pnl_Friend_List,
											pnl_East_Cen_Friend);

								} else if ((tbp.getSelectedIndex() == 3)) {
									setting_Search_User(id, str,
											cData.getBlack_User_List(), map,
											pnl_Black_List, pnl_East_Cen_Black);// 박상욱
																				// 수정
																				// 0702
																				// 친구추가
								}
								count++;
							}
						}
						if (count == 0) {
							showMsg("검색과 일치하는 대상이 없습니다.");
						}

					}
				} else if (src == btn_Friend) {
					friend_Invite_List_GUI = new Friend_Invite_List(cData);
				} else if (src == btn_Exit) {
					yesNoMsg("종료 하시겠습니까?");
				}

				// 06_27민지영=====================================================================

			}
		};
		System.out.println("check2");
		ItemListener iListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				cData.updateWaitRoomChatList();
			}
		};
		cb_Set_Blocked.addItemListener(iListener);
		cb_Set_Category.addItemListener(iListener);

		btn_Friend.addActionListener(aListener);
		System.out.println("check3");
		btn_Change_User_List.addActionListener(aListener);
		btn_Search_Room.addActionListener(aListener);
		btn_Open_Note_List.addActionListener(aListener);
		btn_Send_Msg.addActionListener(aListener);
		btn_Set_Category.addActionListener(aListener);
		System.out.println("check4");
		btn_Make_Room.addActionListener(aListener);
		btn_Fast_Enter_Room.addActionListener(aListener);// 0703박상욱
		lbl_Search_User.addActionListener(aListener);
		btn_Exit.addActionListener(aListener);
		// btn_Search_User.addActionListener(aListener);
		// lbl_Menu.addActionListener(aListener);
		btn_Show_Chat_List.addActionListener(aListener);
		tf_Send_Chat.addActionListener(aListener);

		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
		System.out.println("check5");
		// 김명환 수정
		tf_Whisper_User.addActionListener(aListener);
		lbl_Menu.addActionListener(aListener);
		pnl_User_List_Waitting.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}

		});
		//
		// 정재훈수정
		// ===============================================================================
		ActionListener bListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == miProfileSet) {
					SendData send = new SendData(USER_PROFILE, null,
							cData.getMyID());
					cData.sendTarget(send);
				} else if (src == miFontSet) {
					cData.getGui().showSettingFont_Gui();
				} else if (src == miLogout) {
					yesNoMsg("로그아웃 하시겠습니까?");
				} else if (src == miWithdrawal) {
					String str = JOptionPane.showInputDialog("비밀번호를 입력하시오");
					cData.sendTarget(new SendData(WITHDRAW, str));
				}
			}
		};
		miProfileSet.addActionListener(bListener);
		miLogout.addActionListener(bListener);
		miWithdrawal.addActionListener(bListener);
		miFontSet.addActionListener(bListener);
		// ===============================================================================
		// 0707 박상욱 수정
		tbp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tbp.getSelectedIndex() == 0) {
					tf_Search_User.setText("");
					updateUserLbl_Total_List(cData.getOnLine_User_List());
					updateUserLbl_Friend_List(cData.getFriend_User_List());
					updateUserLbl_Black_List(cData.getBlack_User_List());
				} else if (tbp.getSelectedIndex() == 1) {
					tf_Search_User.setText("");
					updateUserLbl_Waititing_List(cData.getWaitting_User_List());
					updateUserLbl_Friend_List(cData.getFriend_User_List());
					updateUserLbl_Black_List(cData.getBlack_User_List());
				} else if (tbp.getSelectedIndex() == 2) {
					tf_Search_User.setText("");
					updateUserLbl_Waititing_List(cData.getWaitting_User_List());
					updateUserLbl_Total_List(cData.getOnLine_User_List());
					updateUserLbl_Black_List(cData.getBlack_User_List());
				} else if (tbp.getSelectedIndex() == 3) {
					tf_Search_User.setText("");
					updateUserLbl_Waititing_List(cData.getWaitting_User_List());
					updateUserLbl_Total_List(cData.getOnLine_User_List());
					updateUserLbl_Friend_List(cData.getFriend_User_List());
				}
			}
		});
	}

	// 아이디 검색 온라인유저에서 for문돌리면서 아이디 그다음 검색 단어 대기실 유저
	public void setting_Search_User(String id, String str,
			HashMap<String, User_Info> userList,
			HashMap<String, User_Info> map, JPanel pnl, JPanel pnl_Update) {
		if (!str.equals(cData.getMyID()) && userList.size() > 0) {
			if ((id.contains(str) || userList.get(id).getNickName()
					.contains(str))) {
				if (userList.containsKey(id)) {
					if (!id.equals(cData.getMyID())) {
						if (count == 0) {
							pnl.removeAll();
						}
						User_Info userInfo = map.get(id);
						String nickName = userInfo.getNickName();
						String userId = userInfo.getId();
						User_LblMaker lbl = new User_LblMaker(nickName, userId,
								cData);
						pnl.add(lbl);
						updatePnl(pnl);
						pnl_Update.updateUI();
						count += 1;
					}
				}
			}

		} else if (str.equals(cData.getMyID())) {
			showMsg("나의 ID는 검색할 수 없습니다.");
		}
	}

	public void setting_Search_User(String id, String str,
			HashMap<String, User_Info> userList, JPanel pnl, JPanel pnl_Update) { // 수정
																					// 하는중0707
		if (!str.equals(cData.getMyID()) && userList.size() > 0) {
			if ((id.contains(str) || userList.get(id).getNickName()
					.contains(str))) {
				if (userList.containsKey(id)) {
					if (!id.equals(cData.getMyID())) {
						if (count == 0) {
							pnl.removeAll();
						}
						User_Info userInfo = userList.get(id);
						String nickName = userInfo.getNickName();
						String userId = userInfo.getId();
						User_LblMaker lbl = new User_LblMaker(nickName, userId,
								cData);
						pnl.add(lbl);
						updatePnl(pnl);
						pnl_Update.updateUI();
						count += 1;
					}
				}
			}

		} else if (str.equals(cData.getMyID())) {
			showMsg("나의 ID는 검색할 수 없습니다.");
		}
	}

	// setting_Search_user 오버로딩
	public void setting_Search_User(String id, String str,
			Vector<String> userList, HashMap<String, User_Info> map,
			JPanel pnl, JPanel pnl_Update) {
		if (userList.contains(id) && (!str.equals(cData.getMyID()))) {
			if ((id.contains(str))) {
				// if (!id.equals(cData.getMyID())) {
				if (count == 0) {
					pnl.removeAll();
				}
				User_Info userInfo = map.get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl.add(lbl);
				updatePnl(pnl);
				pnl_Update.updateUI();
				count += 1;
				// }
			}
		} else if (!userList.contains(id)) {
			showMsg("검색대상이 존재하지 않습니다.");
		}
	}

	public JButton getBtn_Change_User_List() {
		return btn_Change_User_List;
	}

	public void setBtn_Change_User_List(JButton btn_Change_User_List) {
		this.btn_Change_User_List = btn_Change_User_List;
	}

	public void settingFont(Color myColor, Color otherColor, Font font) {
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, otherColor);

		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, myColor);
		if (font != null) {
			tp_Waitting_Room_Chat.setFont(font);
		}
	}

	private void showFrame() {
		setUndecorated(true);
		setLocation(700, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);

	}

	private void showPopup(MouseEvent me) {// 김명환 수정
		// 팝업신호가 뭔지, 우클릭이 맞는지 판정해줌
		if (me.isPopupTrigger()) {
			pMenu.show(pnl_User_List_Waitting, me.getX(), me.getY());
		}
	}

	public boolean checkIsSearchUser() {
		if (tf_Search_User.getText().trim().length() > 0) {
			return false;
		} else
			return true;
	}

	public boolean checkIsSearchRoom() {
		if (tf_Search_Room.getText().trim().length() > 0) {
			return false;
		} else
			return true;
	}

	public void updateUserLbl_Waititing_List(HashMap<String, User_Info> userList) {
		updatePnl(setting_waittingPnl(userList));
		pnl_East_Cen_Wait.updateUI();
	}

	public JPanel setting_waittingPnl(HashMap<String, User_Info> userList) {
		pnl_User_List_Waitting.removeAll();
		for (String id : userList.keySet()) {
			if (!id.equals(cData.getMyID())) {
				User_Info userInfo = userList.get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl_User_List_Waitting.add(lbl);
			}
		}
		return pnl_User_List_Waitting;
	}

	// /지영아 친구추가 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void updateUserLbl_Friend_List(HashMap<String, User_Info> userList) {
		updatePnl(setting_FriendPnl(userList));
		pnl_East_Cen_Friend.updateUI();
	}

	public JPanel setting_FriendPnl(HashMap<String, User_Info> userList) {
		pnl_Friend_List.removeAll();
		for (String id : userList.keySet()) {
			if (!id.equals(cData.getMyID())) {
				User_Info userInfo = userList.get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl_Friend_List.add(lbl);
			}
		}
		return pnl_Friend_List;
	}

	public void updateUserLbl_Black_List(Vector<String> userList) {
		updatePnl(setting_BlackPnl(userList));
		pnl_East_Cen_Black.updateUI();
	}

	public JPanel setting_BlackPnl(Vector<String> userList) {
		pnl_Black_List.removeAll();
		for (String id : userList) {
			User_LblMaker lbl = new User_LblMaker(id, cData);
			pnl_Black_List.add(lbl);
		}
		return pnl_Black_List;
	}

	// 스크롤 세팅 // 박상욱 머리도 세팅 박상욱머머리 // 박상욱 코드 엉망 // 윤종두강사님 : 이거 왜이렇게 했어요???
	public JScrollPane setting_JScroll(JPanel pnl, int x, int y) {
		JScrollPane scrollPeople = new JScrollPane(pnl,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPeople.setPreferredSize(new Dimension(x, y));// 250 605
		scrollPeople
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPeople;
	}

	public void updateUserLbl_Total_List(HashMap<String, User_Info> onLineList) {
		updatePnl(setting_OnlinePnl(onLineList));
		// updatePnl(setting_OffLinePnl(offLineList));
		pnl_East_Cen_Online.updateUI();
	}

	public JPanel setting_OnlinePnl(HashMap<String, User_Info> onLineList) {
		pnl_User_List_On.removeAll();
		for (String id : onLineList.keySet()) {
			if (!id.equals(cData.getMyID())) {
				User_Info userInfo = onLineList.get(id);
				String nickName = userInfo.getNickName();
				String userId = userInfo.getId();
				User_LblMaker lbl = new User_LblMaker(nickName, userId, cData);
				pnl_User_List_On.add(lbl);//
				pnl_User_List_On.setFont(font);
			}
		}
		return pnl_User_List_On;
	}

	// public JPanel setting_OffLinePnl(HashMap<String,User_Info> offLineList){
	// pnl_User_List_Off.removeAll();
	// for (String id : offLineList.keySet()) {
	// if (!id.equals(cData.getMyID())) {
	// User_Info userInfo = offLineList.get(id);
	// String nickName = userInfo.getNickName();
	// String userId = userInfo.getId();
	// User_LblMaker lbl = new User_LblMaker(nickName, userId,cData);
	// pnl_User_List_Off.add(lbl);
	// }
	// }
	// return pnl_User_List_Off;
	// }
	public TreeMap<Integer, Chat_Room> sortList(TreeMap<Integer, Chat_Room> list) {
		Iterator<Integer> itr = list.keySet().iterator();
		String blockStr = cb_Set_Blocked.getSelectedItem().toString();
		String categoryStr = cb_Set_Category.getSelectedItem().toString();
		TreeMap<Integer, Chat_Room> some = new TreeMap<Integer, Chat_Room>();
		while (itr.hasNext()) {
			Chat_Room cr = list.get(itr.next());
			if (blockStr.equals("전체")
					&& cr.getCategory_Name().equals(categoryStr)) {
				some.put(cr.getRoomNumber(), cr);
			} else if (blockStr.equals("공개")
					&& cr.getCategory_Name().equals(categoryStr)
					&& (cr.isBlock() == false)) {
				some.put(cr.getRoomNumber(), cr);
			} else if (blockStr.equals("비공개")
					&& cr.getCategory_Name().equals(categoryStr)
					&& (cr.isBlock() == true)) {
				some.put(cr.getRoomNumber(), cr);
			} else if (categoryStr.equals("번호") && blockStr.equals("전체")) {
				some.put(cr.getRoomNumber(), cr);
			} else if (categoryStr.equals("번호") && blockStr.equals("공개")
					&& (cr.isBlock() == false)) {
				some.put(cr.getRoomNumber(), cr);
			} else if (categoryStr.equals("번호") && blockStr.equals("비공개")
					&& (cr.isBlock() == true)) {
				some.put(cr.getRoomNumber(), cr);
			}
		}
		return some;
	}

	public JPanel setting_chatRoomList(TreeMap<Integer, Chat_Room> list) {
		pnlRoom.removeAll();
		Iterator<Integer> itr = list.keySet().iterator();
		while (itr.hasNext()) {
			Chat_Room some = list.get(itr.next());
			Room_PnlMaker pnlsome = new Room_PnlMaker(some, cData);// 상욱 수정
			pnlRoom.add(pnlsome);
		}
		return pnlRoom;
	}

	public void updateRoomPnlList(TreeMap<Integer, Chat_Room> list) {

		updatePnl(setting_chatRoomList(sortList(list)));
		pnl_Center_Center.updateUI();
	}

	public JTextPane getTp_Waitting_Room_Chat() {
		return tp_Waitting_Room_Chat;
	}

	public void setTp_Waitting_WRoom_Chat(JTextPane tp_Waitting_Room_Chat) {
		this.tp_Waitting_Room_Chat = tp_Waitting_Room_Chat;
	}

	public StyledDocument getDoc() {
		return doc;
	}

	public void setDoc(StyledDocument doc) {
		this.doc = doc;
	}

	public SimpleAttributeSet getLeft() {
		return left;
	}

	public void setLeft(SimpleAttributeSet left) {
		this.left = left;
	}

	public SimpleAttributeSet getRight() {
		return right;
	}

	public void setRight(SimpleAttributeSet right) {
		this.right = right;
	}

	public static void main(String[] args) {
		new Waitting_Room_GUI(null);
	}

	public JTabbedPane getTbp() {
		return tbp;
	}

	public void setTbp(JTabbedPane tbp) {
		this.tbp = tbp;
	}

	public Friend_Invite_List getFriend_Invite_List_GUI() {
		return friend_Invite_List_GUI;
	}

	public void setFriend_Invite_List_GUI(
			Friend_Invite_List friend_Invite_List_GUI) {
		this.friend_Invite_List_GUI = friend_Invite_List_GUI;
	}

	public void shakeit() {
		int x = (int) this.getLocation().getX();
		int y = (int) this.getLocation().getY();
		this.getLocation().getY();
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int ran1 = (int) (Math.random() * 25) + 1;
			int ran2 = (int) (Math.random() * 25) + 1;
			setLocation(x + ran1, y + ran2);
			this.toFront();
			this.setExtendedState(JFrame.NORMAL);
			this.setAlwaysOnTop(true);
			this.setAlwaysOnTop(false);

		}
		setLocation(x, y);
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

	public void yesNoMsg(String str) {
		new MsgDiag(str, cData, cData.getGui().checkOnGUI());
	}

	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}
}