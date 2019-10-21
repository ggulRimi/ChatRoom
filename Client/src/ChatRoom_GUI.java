import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Random;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatRoom_GUI extends JFrame {
	private JButton btnOut;
	private JButton btnInvite;
	private JButton btnWhisper;
	private JButton btnNotice;
	private JButton btnSend;
	private JButton btnRoomSet;
	private JButton btnVote;
	private JButton btnSendNote;
	private JButton btnNoteBox;
	private JButton btnExit;

	private JLabel lblRoomUser;
	private JLabel lblRoomName;
	private JLabel lblTitle;

	// 팝업창에 뜰것들
	private JMenuItem micaptain_toss;
	private JMenuItem miKickUser;
	private JMenuItem miGiveKing;
	private JMenuItem miWhisper;
	private String id;
	private JScrollPane scrollChat;
	// private JTextArea taChat;
	private JTextArea taNotice;
	private JTextField tfWhisperId;
	private JTextField tfChat;
	private JTextPane tp_Chat_Room;
	private StyledDocument doc;
	private SimpleAttributeSet left;
	private SimpleAttributeSet right;

	private Color btnColor;
	private Color bgColor;
	private Color edgeColor;
	private Font font;

	private Chat_Room room;
	private ClientData cData;

	private JPanel pnlEast; // 0623 박상욱 수정
	private JPanel pnlEast_East;
	private JPanel pnlTop;

	private Collection<String> tempList;
	// private Vector<String> tempVec;
	private DefaultListModel<String> tempVec;
	private JList<String> chatRoomUserList;// 이해림 수정
	private Notice_GUI notice_Gui;// 0623박상욱 수정 세터개터 추가
	private JPopupMenu pmk;// 0625 정재훈
	// 수정++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private JPopupMenu pm;
	// 0703 동훈 수정
	private Point ptFirst;
	private JCheckBox cbTop;
	private Image img;
	 private TitledBorder titleBorder; 
	 private JScrollPane scrollNotice;
	 

	public JCheckBox getCbTop() {
		return cbTop;
	}

	public void setCbTop(JCheckBox cbTop) {
		this.cbTop = cbTop;
	}

	public void checkBoxstate() {
		if (cbTop.isSelected()) {
			this.setAlwaysOnTop(true);
		}
	}

	public ChatRoom_GUI(Chat_Room room, ClientData cData) {
		this.room = room;
		this.cData = cData;
		init();
		setDisplay();
		addListener();
		showDlg();
		taNotice.append("공지 제목 : " + room.getNotice_Title() + "\n\n");// 내창에
		// 추가
		taNotice.append("공지 내용 : \n" + room.getNotice_Msg() + "\n");
	}

	public void init() {

		bgColor = new Color(0xFFFFFF); // 흰색
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		btnColor = new Color(0xE59BB9); // 진한분홍
		font = new Font("HY나무B", Font.PLAIN, 15);

		cbTop = new JCheckBox("항상 맨위로");
		cbTop.setFont(font);
		cbTop.setBackground(edgeColor);

		btnNotice = new JButton("공지하기");
		btnNotice.setFont(font);
		setButton(btnNotice);
		btnInvite = new JButton("초대하기");
		btnInvite.setFont(font);
		setButton(btnInvite);
		btnWhisper = new JButton("귓속말");
		btnWhisper.setFont(font);
		setButton(btnWhisper);
		btnOut = new JButton("나가기");
		btnOut.setFont(font);
		setButton(btnOut);
		btnOut.setMnemonic(KeyEvent.VK_W);
		btnSendNote = new JButton("쪽지보내기");// 0703박상욱
		btnSendNote.setFont(font);
		setButton(btnSendNote);// 0703박상욱
		btnNoteBox = new JButton("내쪽지함");// 0703박상욱
		btnNoteBox.setFont(font);
		setButton(btnNoteBox);// 0703박상욱


		btnRoomSet = new JButton("방설정");
		btnRoomSet.setFont(font);
		setButton(btnRoomSet);
		btnRoomSet.setPreferredSize(new Dimension(100, 30));

		btnSend = new JButton("보내기");
		btnSend.setFont(font);
		setButton(btnSend);
		btnSend.setPreferredSize(new Dimension(90, 30));
		// 0625 정재훈 수정
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		pmk = new JPopupMenu();
		miKickUser = new JMenuItem("강퇴");
		miKickUser.setFont(font);
		micaptain_toss = new JMenuItem("방장위임");
		micaptain_toss.setFont(font);
		miWhisper = new JMenuItem("귓속말");
		miWhisper.setFont(font);

		pmk.add(miKickUser);
		pmk.add(micaptain_toss);
		pmk.add(miWhisper);

		pm = new JPopupMenu();
		pm.add(miWhisper);
		// 0625 정재훈 수정
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// 팝업메뉴

		lblRoomUser = new JLabel("접속인원", JLabel.CENTER);
		lblRoomUser.setFont(font);
		lblRoomName = new JLabel("방제목 : <" + room.getTitle() + ">" + "카테고리 "
				+ room.getCategory_Name(), JLabel.CENTER); // 나중에Get
		// Text로해서
		// 넣을예정
		lblRoomName.setFont(font);

		Toolkit kit = Toolkit.getDefaultToolkit();
		img = kit.getImage("BlowFlower.gif");
		img = img.getScaledInstance(1000, 600, Image.SCALE_SMOOTH);

		tp_Chat_Room = new JTextPane();
		tp_Chat_Room.setOpaque(true);
		tp_Chat_Room.setEditable(false);
		tp_Chat_Room.setBackground(Color.WHITE);
		doc = tp_Chat_Room.getStyledDocument();

		left = new SimpleAttributeSet();
		// StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		// StyleConstants.setForeground(left, Color.RED);

		right = new SimpleAttributeSet();
		// StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		// StyleConstants.setForeground(right, Color.BLUE);
		settingFont(cData.getMyColor(), cData.getOtherColor(), cData.getFont());
		taNotice = new JTextArea(4, 60);
		taNotice.setOpaque(true);
		// taNotice.setForeground(Color.BLUE); 폰트 색상
		taNotice.setFocusable(false);
		taNotice.setFont(font);

		tfChat = new JTextField(60);
		tfChat.setDocument(new JTextFieldLimit(90));
		tfChat.setFont(font);
		scrollNotice = new JScrollPane(taNotice);
		

		tfWhisperId = new JTextField(8);
		tfWhisperId.setText("ID");
		tfWhisperId.setFont(font);

		// 0703 동훈 수정
		pnlTop = new JPanel(new BorderLayout());
		btnExit = new JButton(" X ");
		btnExit.setPreferredSize(new Dimension(50, 25));
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(btnColor);
		btnExit.setBorder(new LineBorder(Color.WHITE, 1));
		lblTitle = new JLabel("   방제목 :  " + room.getTitle());
		lblTitle.setFont(font);

		btnVote = new JButton("투표하기");
		btnVote.setFont(font);
		setButton(btnVote);

	}

	public void setDisplay() {

		JPanel pnlTop_West = new JPanel(new BorderLayout());
		pnlTop_West.add(cbTop, BorderLayout.WEST);
		pnlTop_West.add(lblTitle, BorderLayout.CENTER);
		pnlTop_West.setBackground(edgeColor);

		pnlTop.add(btnExit, BorderLayout.EAST);
		pnlTop.add(pnlTop_West, BorderLayout.WEST);
		pnlTop.setBackground(edgeColor);
		pnlTop.setBorder(new EmptyBorder(0, 0, 5, 0));

		// ------------------------ 제일큰 패널 (왼쪽)---------------------------------
		JPanel pnlWest = new JPanel(new BorderLayout());
		// 왼쪽패널 상단 ---------------------------------------------
		JPanel pnlRoomInfo = new JPanel(new BorderLayout());
		pnlRoomInfo.add(btnRoomSet, BorderLayout.WEST);
		pnlRoomInfo.add(lblRoomName, BorderLayout.CENTER);

		pnlRoomInfo.setBackground(bgColor);
		pnlRoomInfo.setBorder(new EmptyBorder(10, 0, 10, 0));

		JPanel pnlWest_North = new JPanel(new BorderLayout());
		pnlWest_North.add(pnlRoomInfo, BorderLayout.NORTH);
		
		scrollNotice.setBorder(titleBorder =new TitledBorder(new LineBorder(btnColor, 2),
				"< 공지사항 >"));
		titleBorder.setTitleFont(font);
		scrollNotice.setPreferredSize(new Dimension(700, 140));
		scrollNotice.setBackground(bgColor);
		
		pnlWest_North.add(scrollNotice, BorderLayout.CENTER);// ///////여기있음
		pnlWest_North.setBackground(bgColor);
		pnlWest_North.setBorder(new EmptyBorder(5, 10, 0, 5));

		// 왼쪽패널 중단 ---------------------------------------------
		// 채팅 에어리어
		JPanel pnlCover_Cover = new JPanel(new BorderLayout());
		JPanel pnlScrollCahtCover = new JPanel(new BorderLayout());
		scrollChat = new JScrollPane(tp_Chat_Room,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {
			{
				setOpaque(false);
			}

			public void paintComponent(Graphics g) {
				super.setBackground(bgColor);
				g.drawImage(img, 0, 0, this); // 이미지 그리기
				super.paintComponent(g);
			}
		};
		scrollChat.getViewport().setOpaque(false);
		scrollChat.setBackground(bgColor);
		scrollChat.setPreferredSize(new Dimension(1000, 527));
		scrollChat.setBorder(new EmptyBorder(0, 0, 0, 0));

		pnlScrollCahtCover.add(scrollChat, BorderLayout.CENTER);
		pnlScrollCahtCover.setBorder(new LineBorder(btnColor, 2));
		pnlScrollCahtCover.setPreferredSize(new Dimension(700, 530));
		pnlScrollCahtCover.setBackground(bgColor);

		pnlCover_Cover.add(pnlScrollCahtCover);
		pnlCover_Cover.setBorder(new EmptyBorder(5, 8, 5, 5));
		pnlCover_Cover.setBackground(bgColor);
		// 왼쪽패널 하단 ---------------------------------------------
		// 제일하단 귓속말창, 텍스트 입력창, 보내기버튼

		JPanel pnlWest_South = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlWest_South.setBorder(new EmptyBorder(0, 10, 10, 0));
		pnlWest_South.setBackground(bgColor);
		pnlWest_South.add(tfWhisperId);
		pnlWest_South.add(tfChat);
		pnlWest_South.add(btnSend);

		pnlWest.setBackground(bgColor);
		pnlWest.add(pnlWest_North, BorderLayout.NORTH);
		pnlWest.add(pnlCover_Cover, BorderLayout.CENTER);
		pnlWest.add(pnlWest_South, BorderLayout.SOUTH);

		// ------------------------ 제일큰 패널
		// (오른쪽)---------------------------------
		pnlEast = new JPanel(new BorderLayout());

		// 오른쪽패널 최상단 ---------------------------------------------
		JPanel pnlEast_North = new JPanel();
		pnlEast_North.setBackground(bgColor);
		pnlEast_North.setBorder(new EmptyBorder(0, 0, 3, 0));
		pnlEast_North.add(lblRoomUser);

		pnlEast.add(pnlEast_North, BorderLayout.NORTH);

		// 오른쪽패널 중단 ---------------------------------------------
		JPanel pnlChatPeopleList = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlChatPeopleList.setBorder(new EmptyBorder(0, 0, 0, 10));
		pnlChatPeopleList.setBackground(bgColor);
		// ////////////////////////////////////////////////// 해림수정 06.23
		// 대기 리스트 띄우기
		tempList = room.getRoom_User_List().keySet();
		tempVec = new DefaultListModel<String>();
		for (String id : tempList) {
			if (id.equals(room.getKing())) {
				String nick = room.getRoom_User_List().get(id).getNickName();
				tempVec.addElement("★" + id + "(" + nick + ")★");
			}
		}
		for (String id : tempList) {
			if (!id.equals(room.getKing())) {
				String nick = room.getRoom_User_List().get(id).getNickName();
				tempVec.addElement(id + "(" + nick + ")");
			}
		}
		// ///////////////////////////////////////////////////////////////////////

		chatRoomUserList = new JList<String>(tempVec);
		chatRoomUserList.setCellRenderer(new MyListCellRenderer()); // 상욱추가
		JScrollPane scrollPeople = new JScrollPane(chatRoomUserList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPeople.setPreferredSize(new Dimension(100, 500));

		pnlEast_East = new JPanel(new BorderLayout());
		pnlEast_East.add(scrollPeople, BorderLayout.CENTER);
		pnlEast_East.setBorder(new LineBorder(btnColor, 2));
		pnlEast.add(pnlEast_East, BorderLayout.CENTER);
		// /////////////////////////////////////////////////////////////////
		// 방장
		// 오른쪽패널 하단 ---------------------------------------------
		JPanel pnlEast_South = new JPanel(new GridLayout(0, 1, 3, 3));
		pnlEast_South.setPreferredSize(new Dimension(120, 220));
		pnlEast_South.setBorder(new EmptyBorder(3, 3, 3, 3));
		pnlEast_South.setBackground(bgColor);
		pnlEast_South.add(btnNotice);
		pnlEast_South.add(btnVote);
		pnlEast_South.add(btnInvite);
		pnlEast_South.add(btnWhisper);
		pnlEast_South.add(btnSendNote);// 0703박상욱
		pnlEast_South.add(btnNoteBox);// 0703박상욱
		pnlEast_South.add(btnOut);

		pnlEast.add(pnlEast_South, BorderLayout.SOUTH);

		pnlEast.setBorder(new EmptyBorder(0, 10, 0, 10));
		pnlEast.setBackground(bgColor);
		pnlEast.setPreferredSize(new Dimension(200, 800));

		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlTop, BorderLayout.NORTH);
		pnlTotal.add(pnlWest, BorderLayout.WEST);
		pnlTotal.add(pnlEast, BorderLayout.CENTER);
		pnlTotal.setBackground(edgeColor);
		pnlTotal.setBorder(new LineBorder(edgeColor, 6));
		add(pnlTotal, BorderLayout.CENTER);
	}

	public void addListener() {
		WindowAdapter wAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(ChatRoom_GUI.this,
						"시스템을 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {

				}
			}
		};
		addWindowListener(wAdapter);
		ItemListener iListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbTop.isSelected()) {
					ChatRoom_GUI.this.setAlwaysOnTop(true);
				} else {
					ChatRoom_GUI.this.setAlwaysOnTop(false);
				}
			}
		};
		cbTop.addItemListener(iListener);
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == btnOut || src == btnExit) {
					SendData sendData = new SendData(cData.EXIT_CHATROOM,
							cData.getMyID(), cData.getChat_Room_List(),
							room.getRoomNumber());// 박상욱 // 수정
					cData.sendTarget(sendData);
				} else if (src == btnSend || src == tfChat) { // 김명환 귓속말
					String wc = tfWhisperId.getText();
					String cg = tfChat.getText();
					if(cg.trim().length()!=0){
					if (wc.length() == 0 || wc.equals("ID")) {
						if (cg.equals("/주사위") || cg.equals("/dice")
								|| cg.equals("/Dice") || cg.equals("/다이스")) {
							System.out.println("다이스 굴러갑니다요");
							Random random = new Random();
							String msg = cData.getMyID() + "(님)의 주사위 : ("
									+ (random.nextInt(6) + 1) + ")" + ", "
									+ "(" + (random.nextInt(6) + 1) + ")";
							SendData send = new SendData(cData.BROADCAST_MSG,
									msg);
							cData.sendTarget(send);

						} else {
							String str = tfChat.getText();
							String newstr = "";
							int size = str.length();
							int rum = size/10;
							if(rum!=0){
								for(int i = 0 ; i<rum; i++){
									newstr += str.substring(0, 10)+"\n";
									str = str.substring(10);
								}
								newstr+=str;
							} else{
								newstr = str;
							}
							newstr+="\n";
							String msg = cData.getMyID() + "(님)의 메세지 : "
									+ newstr;
							SendData send = new SendData(cData.BROADCAST_MSG,
									msg);
							cData.sendTarget(send);
						}
					} else if (!wc.equals("ID") && wc.length() != 0
							&& !cData.getMyID().equals(wc)) {
						if (tfChat.getText().contains("/흔들기")|| tfChat.getText().contains("/벚꽃") ) {
							showMsg("(귓속말 / 친구채팅)에서 \n사용할수없는 기능입니다.");
						} else {

							String str = tfChat.getText();
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

						showMsg(
								"자기자신에게 귓속말을 보낼 수 없습니다.");
					}
					tfChat.setText("");
					tfChat.requestFocus();
					}
				} else if (src == btnNotice) {
					// 공지 방장만 띄울거면 if문 하나 더 넣어야함
					if (room.getKing().equals(cData.getMyID())) {
						notice_Gui = new Notice_GUI(cData,
								room.getRoomNumber(), ChatRoom_GUI.this);
					} else {
						showMsg( "공지는 방장만 설정가능합니다.");
					}
				} else if (src == btnInvite) { // 유저 초대할 온라인 목록 받아오기 //0625박상욱
												// 초대
					SendData sendData = new SendData(cData.USER_INVITE_LIST,
							room.getRoomNumber());// 방유저
													// 요청하면
													// 서
													// 방번호
													// 던져줌
					cData.sendTarget(sendData);

				} else if (src == btnWhisper) {
					SendData sendData = new SendData(cData.WHISPER_IN_ROOM);// 프로토콜변경필요
					// cData.showWhisper((HashMap<String,User_Info>)cData.getOnLine_User_List());
					cData.sendTarget(sendData);
					System.out.println("위스퍼갔읍니다");
				} else if (src == btnRoomSet) {
					if (cData.getMyID().equals(room.getKing())) {
						new ChatRoom_Set_Change_GUI(cData, room.getRoomNumber());
					} else {
						showMsg( "방장만 가능합니다");
					}
				} else if (src == btnVote) {
					if (cData.getMyID().equals(room.getKing())) {
						if(cData.getGui().getChat_Room_Gui().getTempVec().getSize()-1 ==0){
							showMsg("방장을 제외하고 최소 1명의 투표자필요");
						}else{
						cData.getGui().showVoteGUI2(room.getRoomNumber());
						}
					} else {
						showMsg( "방장만 가능합니다");
					}
				} else if (src == btnSendNote) {// 0703박상욱
					SendData sendData = new SendData(cData.CHATROOM_SEND_NOTE);// 프로토콜변경필요
					cData.sendTarget(sendData);
				} else if (src == btnNoteBox) {// 0703박상욱
					SendData sendData = new SendData(cData.OPEN_NOTEBOX);
					cData.sendTarget(sendData);
				}
			}
		};
		btnOut.addActionListener(listener);
		tfChat.addActionListener(listener);
		btnVote.addActionListener(listener);
		btnSend.addActionListener(listener);
		btnNotice.addActionListener(listener);
		btnInvite.addActionListener(listener); // 0625박상욱 초대
		btnWhisper.addActionListener(listener);
		tfWhisperId.addActionListener(listener);
		btnSendNote.addActionListener(listener);
		btnNoteBox.addActionListener(listener);
		btnRoomSet.addActionListener(listener);
		btnExit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());

		chatRoomUserList.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}

			private void showPopup(MouseEvent me) {
				if (me.isPopupTrigger()) {
					int x = me.getX();
					int y = me.getY();
					int idx = chatRoomUserList.locationToIndex(new Point(x, y));
					if (idx >= 0 && chatRoomUserList.getSelectedIndex() >= 0) {
						if (cData.getMyID().equals(room.getKing())) {
							pmk.show(chatRoomUserList, x, y);
						} else if (!cData.getMyID().equals(room.getKing())) {
							pm.show(chatRoomUserList, x, y);
						}
					}
				}
			}
		});
		taNotice.addMouseListener(new MouseAdapter() {// 0628 박상욱 수정

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					if (cData.getMyID().equals(room.getKing())) {
						new Notice_GUI(cData, room.getRoomNumber(),
								ChatRoom_GUI.this);
					} else {
						new Notice_GUI(room);
					}
				}
			}

		});
		// 0627 정재훈
		// +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		miKickUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				for (String id : tempList) {
					int i = chatRoomUserList.getSelectedIndex();
					String tempid = null;
					if (i == 0) {
						String tempid_nick = tempVec.get(i);
						int idx1 = tempid_nick.indexOf("★"); // 0
						int idx2 = tempid_nick.indexOf("(");
						tempid = tempid_nick.substring(idx1 + 1, idx2); // 왕일때
																		// 이름자르기
					} else {
						String tempid_nick = tempVec.get(i); // id(nick)
						int idx = tempid_nick.indexOf("(");// (어디서 부터 시작하는//
															// 가?/????
						tempid = tempid_nick.substring(0, idx);// 일반 유저 이름자르기
					}
					// id
					if (id.equals(tempid) && !id.equals(room.getKing())) { // 강퇴시킬 아이디가 -> jlist에서 선택된 아이디면?
						SendData send = new SendData(cData.USER_KICK, id,
								chatRoomUserList.getSelectedIndex(), room
										.getRoomNumber());// 아이디가 들어가는 이유 ->
						// 서버데이터 룸유저리스트에서
						// 제거하기위해 //인덱스를 보내는
						// 이유 룸유저들이 받았을때 자기
						// 클라이언트쪽에서 제거해주기 위해
						cData.sendTarget(send);
					}// 서버쪽에 전달
				}
			}
		});
		micaptain_toss.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempid = null;
				for (String id : tempList) {
					int i = chatRoomUserList.getSelectedIndex();
					if (i == 0) {
						String tempid_nick = tempVec.get(i);
						int idx1 = tempid_nick.indexOf("★"); // 0
						int idx2 = tempid_nick.indexOf("(");
						tempid = tempid_nick.substring(idx1 + 1, idx2); // 왕일때
																		// 이름자르기
					} else {
						String tempid_nick = tempVec.get(i); // id(nick)
						int idx = tempid_nick.indexOf("(");// (어디서 부터 시작하는//
															// 가?/????
						tempid = tempid_nick.substring(0, idx);// 일반 유저 이름자르기
					}
					if (id.equals(tempid) && !id.equals(room.getKing())) { 
						SendData send = new SendData(cData.CAPTAIN_TOSS, id,
								room.getRoomNumber());
						cData.sendTarget(send);
					}
				}
			}
		});
		// 0627 정재훈
		// +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
		miWhisper.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tempid = null;
				for (String id : tempList) {
					int i = chatRoomUserList.getSelectedIndex();
					if (i == 0) {
						String tempid_nick = tempVec.get(i);
						int idx1 = tempid_nick.indexOf("★"); // 0
						int idx2 = tempid_nick.indexOf("(");
						tempid = tempid_nick.substring(idx1 + 1, idx2); // 왕일때
																		// 이름자르기
					} else {
						String tempid_nick = tempVec.get(i); // id(nick)
						int idx = tempid_nick.indexOf("(");// (어디서 부터 시작하는//
															// 가?/????
						tempid = tempid_nick.substring(0, idx);// 일반 유저 이름자르기
					}
					if (id.equals(tempid)) {
						tfWhisperId.setText(tempid);
					}
				}
			}
		});
	}

	public void setCheckBox() {
		cbTop.setSelected(!cbTop.isSelected());
		cbTop.setSelected(!cbTop.isSelected());
	}

	public void setCbState() {
		if (cbTop.isSelected()) {
			this.setAlwaysOnTop(true);
		} else {
			this.setAlwaysOnTop(false);
		}
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

	public void updateUser() {// 박상욱 수정 ///0626박상욱// 수정
		tempVec.removeAllElements();
		tempList = room.getRoom_User_List().keySet();
		for (String id : tempList) {
			if (id.equals(room.getKing())) {
				String nick = room.getRoom_User_List().get(id).getNickName();
				tempVec.addElement("★" + id + "(" + nick + ")★");
			}
		}
		for (String id : tempList) {
			if (!id.equals(room.getKing())) {
				String nick = room.getRoom_User_List().get(id).getNickName();
				tempVec.addElement(id + "(" + nick + ")");
			}
		}
		chatRoomUserList.invalidate();
		chatRoomUserList.repaint();
		chatRoomUserList.updateUI();
		pnlEast_East.invalidate();
		pnlEast_East.repaint();
		pnlEast_East.updateUI();
		pnlEast.invalidate();
		pnlEast.repaint();
		pnlEast.updateUI();

	}

	private void setButton(JButton btn) {
		btn.setPreferredSize(new Dimension(50, 22));
		btn.setOpaque(true);
		btn.setBorderPainted(false);
		btn.setBackground(btnColor);
		btn.setForeground(Color.WHITE);
	}

	public void settingFont(Color myColor1, Color otherColor1, Font font) {
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, otherColor1);
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, myColor1);
		if (font != null) {
			tp_Chat_Room.setFont(font);
		}
	}

	public void updateRoom_Setting(String title, String category) {
		lblRoomName.setText("방제목 : <" + title + ">" + "카테고리 " + category);
		lblTitle.setText("   방제목 :  " + title);
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

	public void showDlg() {
		setUndecorated(true);
		pack();
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public JButton getBtnOut() {
		return btnOut;
	}

	public void setBtnOut(JButton btnOut) {
		this.btnOut = btnOut;
	}

	public JButton getBtnInvite() {
		return btnInvite;
	}

	public void setBtnInvite(JButton btnInvite) {
		this.btnInvite = btnInvite;
	}

	public JButton getBtnWhisper() {
		return btnWhisper;
	}

	public void setBtnWhisper(JButton btnWhisper) {
		this.btnWhisper = btnWhisper;
	}

	public JButton getBtnNotice() {
		return btnNotice;
	}

	public void setBtnNotice(JButton btnNotice) {
		this.btnNotice = btnNotice;
	}

	public JButton getBtnSend() {
		return btnSend;
	}

	public void setBtnSend(JButton btnSend) {
		this.btnSend = btnSend;
	}

	public JButton getBtnRoomSet() {
		return btnRoomSet;
	}

	public void setBtnRoomSet(JButton btnRoomSet) {
		this.btnRoomSet = btnRoomSet;
	}

	public Color getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(Color btnColor) {
		this.btnColor = btnColor;
	}

	public JLabel getLblRoomUser() {
		return lblRoomUser;
	}

	public void setLblRoomUser(JLabel lblRoomUser) {
		this.lblRoomUser = lblRoomUser;
	}

	public JLabel getLblRoomName() {
		return lblRoomName;
	}

	public void setLblRoomName(JLabel lblRoomName) {
		this.lblRoomName = lblRoomName;
	}

	public JMenuItem getMiKickUser() {
		return miKickUser;
	}

	public void setMiKickUser(JMenuItem miKickUser) {
		this.miKickUser = miKickUser;
	}

	public JMenuItem getMiGiveKing() {
		return miGiveKing;
	}

	public void setMiGiveKing(JMenuItem miGiveKing) {
		this.miGiveKing = miGiveKing;
	}

	public JMenuItem getMiWhisper() {
		return miWhisper;
	}

	public void setMiWhisper(JMenuItem miWhisper) {
		this.miWhisper = miWhisper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JScrollPane getScrollChat() {
		return scrollChat;
	}

	public void setScrollChat(JScrollPane scrollChat) {
		this.scrollChat = scrollChat;
	}

	public JTextArea getTaNotice() {
		return taNotice;
	}

	public void setTaNotice(JTextArea taNotice) {
		this.taNotice = taNotice;
	}

	public JTextField getTfWhisperId() {
		return tfWhisperId;
	}

	public void setTfWhisperId(JTextField tfWhisperId) {
		this.tfWhisperId = tfWhisperId;
	}

	public JTextField getTfChat() {
		return tfChat;
	}

	public void setTfChat(JTextField tfChat) {
		this.tfChat = tfChat;
	}

	public StyledDocument getDoc() {
		return doc;
	}

	public void setDoc(StyledDocument doc) {
		this.doc = doc;
	}

	public Color getMyColor() {
		return bgColor;
	}

	public void setMyColor(Color myColor) {
		this.bgColor = myColor;
	}

	public Chat_Room getRoom() {
		return room;
	}

	public void setRoom(Chat_Room room) {
		this.room = room;
	}

	public ClientData getcData() {
		return cData;
	}

	public void setcData(ClientData cData) {
		this.cData = cData;
	}

	public JTextPane getTp_Chat_Room() {
		return tp_Chat_Room;
	}

	public void setTp_Chat_Room(JTextPane tp_Chat_Room) {
		this.tp_Chat_Room = tp_Chat_Room;
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

	public Notice_GUI getNotice_Gui() {
		return notice_Gui;
	}

	public void setNotice_Gui(Notice_GUI notice_Gui) {
		this.notice_Gui = notice_Gui;
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
				lblItem.setBackground(Color.PINK);
			}
			return lblItem;

		}
	}

	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}

	public DefaultListModel<String> getTempVec() {
		return tempVec;
	}

	public void setTempVec(DefaultListModel<String> tempVec) {
		this.tempVec = tempVec;
	}
}
