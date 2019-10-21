import java.awt.Color;
import java.awt.Component;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class ClientThread extends Thread implements iProtocol {
	private ClientData cData = null;
	private SendData sendData = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private Vector<Vector<User_Info>> Total_User_List;
	private Vector<User_Info> onLine_User_List;
	private Vector<User_Info> offLine_User_List;
	private Vector<User_Info> waitting_User_List;
	private Vector<Chat_Room> chat_Room_List;
	private String myID;
	private Socket sk;
	private int totalCount = 0;
	private int noCount = 0;
	private final int OVERUSER = 1; // 0705박상욱
	private final int ENTERBAN = 2;

	public ClientData getcData() {
		return cData;
	}

	public void sendTarget(SendData sd) {
		try {
			oos.writeObject(sd);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
		}
	}

	public void setcData(ClientData cData) {
		this.cData = cData;
	}

	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}
	public void showMsg_invite(String str,SendData send) {
		new MsgDiag(str,cData,cData.getGui().checkOnGUI(),send);
	}

	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(sk.getInputStream());
			oos = new ObjectOutputStream(sk.getOutputStream());
			sendTarget(new SendData(CONNECT));
			Object src = null;
			while ((src = ois.readObject()) != null) {
				sendData = (SendData) src;
				int pronum = sendData.getProtocol();
				System.out.println(pronum + "을 실행합니다");
				checkProNum(pronum);
			}
		} catch (Exception e) {
			showMsg( "서버가 닫혀있습니다");
		} finally {
			try {
				showMsg("클라이언트 팅김");
				if (oos != null) {
					oos.close();
				}
			} catch (Exception e) {
			}
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
			}
//			System.exit(0);
		}
	}
	public void whisMsg(Component comp) { // 민지영 수정 채팅 // 명환26
		Color myColor = cData.getMyColor();
		Color otherColor = cData.getMyColor();
		String msg = (String) sendData.getObj1();
		String newMsg = "";
		int idx = msg.indexOf("(");
		newMsg = msg.substring(0, idx);//////보낸이 닉네임
		
		cData.setMyColor(Color.MAGENTA);
		cData.setOtherColor(Color.MAGENTA);
		JTextPane tp = null;
		StyledDocument doc = null;
		SimpleAttributeSet left = null;
		SimpleAttributeSet right = null;
	
		if (comp == cData.getGui().getWaiting_Gui()) {

			tp = cData.getGui().getWaiting_Gui().getTp_Waitting_Room_Chat();
			doc = cData.getGui().getWaiting_Gui().getDoc();
			left = cData.getGui().getWaiting_Gui().getLeft();
			right = cData.getGui().getWaiting_Gui().getRight();
		} else if (comp == cData.getGui().getChat_Room_Gui()) {
			tp = cData.getGui().getChat_Room_Gui().getTp_Chat_Room();
			doc = cData.getGui().getChat_Room_Gui().getDoc();
			left = cData.getGui().getChat_Room_Gui().getLeft();
			right = cData.getGui().getChat_Room_Gui().getRight();
		}

		try {

			if (newMsg.equals(cData.getMyID())) {
				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
				doc.insertString(doc.getLength(), msg + "\n", right);
				tp.setCaretPosition(tp.getDocument().getLength());
			} else {
				doc.setParagraphAttributes(doc.getLength(), 1, left, false);
				doc.insertString(doc.getLength(), msg + "\n", left);
				tp.setCaretPosition(tp.getDocument().getLength());
			}

			cData.setMyColor(myColor);
			cData.setOtherColor(otherColor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void inputMsg(Component comp) { // 민지영 수정 채팅 // 명환26
//		Color myColor = cData.getMyColor();
//		Color otherColor = cData.getMyColor();
//		String msg = (String) sendData.getObj1();
//		String newMsg = "";
//		String whisperMan = "";
//		if (msg.contains("(님)")) {
//			int idx = msg.indexOf("(");
//			newMsg = msg.substring(0, idx);
//			whisperMan = msg.substring(0, idx);
//		} else {
//			newMsg = msg;
//			cData.setMyColor(Color.MAGENTA);
//			cData.setOtherColor(Color.MAGENTA);
//		}
//		JTextPane tp = null;
//		StyledDocument doc = null;
//		SimpleAttributeSet left = null;
//		SimpleAttributeSet right = null;
//		System.out.println("메시지 인풋");
//		if (comp == cData.getGui().getWaiting_Gui()) {
//
//			tp = cData.getGui().getWaiting_Gui().getTp_Waitting_Room_Chat();
//			doc = cData.getGui().getWaiting_Gui().getDoc();
//			left = cData.getGui().getWaiting_Gui().getLeft();
//			right = cData.getGui().getWaiting_Gui().getRight();
//		} else if (comp == cData.getGui().getChat_Room_Gui()) {
//			tp = cData.getGui().getChat_Room_Gui().getTp_Chat_Room();
//			doc = cData.getGui().getChat_Room_Gui().getDoc();
//			left = cData.getGui().getChat_Room_Gui().getLeft();
//			right = cData.getGui().getChat_Room_Gui().getRight();
//		}
//
//		class thImg extends Thread {
//			@Override
//			public void run() {
//				cData.getGui().getChat_Room_Gui().getTp_Chat_Room()
//						.setOpaque(false);
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				cData.getGui().getChat_Room_Gui().getTp_Chat_Room()
//						.setOpaque(true);
//			}
//		};
//		class thshakeit_w extends Thread {
//			@Override
//			public void run() {
//				cData.getGui().getWaiting_Gui().shakeit();
//			}
//		};
//		class thshakeit_c extends Thread {
//			@Override
//			public void run() {
//				cData.getGui().getChat_Room_Gui().shakeit();
//			}
//		};
//		try {
//			if (msg.contains("/흔들기")) {
//				int idx1 = msg.indexOf(")");
//				String subId = msg.substring(0, idx1 + 1);
//				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//				doc.insertString(doc.getLength(), subId + "이 방을 흔드셨습니다. \n",
//						right);
//				tp.setCaretPosition(tp.getDocument().getLength());
//				if (comp == cData.getGui().getWaiting_Gui()) {
//					thshakeit_w th = new thshakeit_w();
//					th.start();
//				} else if (comp == cData.getGui().getChat_Room_Gui()) {
//					thshakeit_c th = new thshakeit_c();
//					th.start();
//				}
//
//			} else if (msg.contains("/벚꽃")) {
//				int idx1 = msg.indexOf(")");
//				String subId = msg.substring(0, idx1 + 1);
//				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//				doc.insertString(doc.getLength(), subId
//						+ "이 벚꽃을 뿌렷네용~~~~~~. \n", right);
//				tp.setCaretPosition(tp.getDocument().getLength());
//				thImg th = new thImg();
//				th.start();
//			} else {
//				if (newMsg.equals(cData.getMyID())) {
//					doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//					doc.insertString(doc.getLength(), msg + "\n", right);
//					tp.setCaretPosition(tp.getDocument().getLength());
//				} else if (whisperMan.equals(cData.getMyID())) {
//					cData.setMyColor(Color.RED);
//					cData.setOtherColor(Color.RED);
//					doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//					doc.insertString(doc.getLength(), msg + "\n", right);
//					tp.setCaretPosition(tp.getDocument().getLength());
//				} else {
//					doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//					doc.insertString(doc.getLength(), msg + "\n", left);
//					tp.setCaretPosition(tp.getDocument().getLength());
//				}
//			}
//			cData.setMyColor(myColor);
//			cData.setOtherColor(otherColor);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void broadCastMsg(Component comp,SendData sendData) { // 민지영 수정 채팅 // 명환26
//		Color myColor = cData.getMyColor();
//		Color otherColor = cData.getMyColor();
		String msg = (String) sendData.getObj1();
		String newMsg = "";
		if (msg.contains("(님)")) {
			int idx = msg.indexOf("(");
			newMsg = msg.substring(0, idx);
		}
		Waitting_Room_GUI waitting_gui = cData.getGui().getWaiting_Gui();
		ChatRoom_GUI chat_gui = cData.getGui().getChat_Room_Gui();
		JTextPane tp = null;
		StyledDocument doc = null;
		SimpleAttributeSet left = null;
		SimpleAttributeSet right = null;
		System.out.println("메시지 인풋");
		if (comp ==waitting_gui) {
			tp = waitting_gui.getTp_Waitting_Room_Chat();
			doc = waitting_gui.getDoc();
			left = waitting_gui.getLeft();
			right = waitting_gui.getRight();
		} else if (comp == chat_gui) {
			tp = chat_gui.getTp_Chat_Room();
			doc = chat_gui.getDoc();
			left = chat_gui.getLeft();
			right = chat_gui.getRight();
		}
		class thImg extends Thread {
			@Override
			public void run() {
				cData.getGui().getChat_Room_Gui().getTp_Chat_Room().setOpaque(false);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cData.getGui().getChat_Room_Gui().getTp_Chat_Room()
						.setOpaque(true);
			}
		};
		class thshakeit_w extends Thread {
			@Override
			public void run() {
				cData.getGui().getWaiting_Gui().shakeit();
			}
		};
		class thshakeit_c extends Thread {
			@Override
			public void run() {
				cData.getGui().getChat_Room_Gui().shakeit();
			}
		};
		try {
			if (msg.contains("/흔들기")) {
				int idx1 = msg.indexOf(")");
				String subId = msg.substring(0, idx1 + 1);
				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
				doc.insertString(doc.getLength(), subId + "이 방을 흔드셨습니다. \n",right);
				tp.setCaretPosition(tp.getDocument().getLength());
				if (comp ==waitting_gui) {
					thshakeit_w th = new thshakeit_w();
					th.start();
				} else if (comp == chat_gui) {
					thshakeit_c th = new thshakeit_c();
					th.start();
				}
			} else if (msg.contains("/벚꽃")) {
				int idx1 = msg.indexOf(")");
				String subId = msg.substring(0, idx1 + 1);
				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
				doc.insertString(doc.getLength(), subId
						+ "이 벚꽃을 뿌렷네용~~~~~~. \n", right);
				tp.setCaretPosition(tp.getDocument().getLength());
				thImg th = new thImg();
				th.start();
			} else {
				if (newMsg.equals(cData.getMyID())) {
					doc.setParagraphAttributes(doc.getLength(), 1, right, false);
					doc.insertString(doc.getLength(), msg + "\n", right);
					tp.setCaretPosition(tp.getDocument().getLength());
				} else {
					doc.setParagraphAttributes(doc.getLength(), 1, left, false);
					doc.insertString(doc.getLength(), msg + "\n", left);
					tp.setCaretPosition(tp.getDocument().getLength());
				}
			}
//			cData.setMyColor(myColor);
//			cData.setOtherColor(otherColor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chatRoom_Set_Change(Component comp) {// 김명환0627
		Chat_Room newRoom = (Chat_Room) sendData.getObj1();
		if (comp == cData.getGui().getChat_Room_Gui()) {
			cData.getGui()
					.getChat_Room_Gui()
					.updateRoom_Setting(newRoom.getTitle(),
							newRoom.getCategory_Name());
			Chat_Room room = cData.getChat_Room_List().get(
					newRoom.getRoomNumber());
			room.setTitle(newRoom.getTitle());
			room.setCategory_Name(newRoom.getCategory_Name());
			room.setMax_User(newRoom.getMax_User());
			boolean isBlock = newRoom.isBlock();
			if (isBlock) {
				room.setBlock(isBlock);
				room.setPassword(newRoom.getPassword());
			} else {
				room.setBlock(false);
			}
		} else if (comp == cData.getGui().getWaiting_Gui()) {
			Chat_Room room = cData.getChat_Room_List().get(
					newRoom.getRoomNumber());
			room.setTitle(newRoom.getTitle());
			room.setCategory_Name(newRoom.getCategory_Name());
			room.setMax_User(newRoom.getMax_User());
			boolean isBlock = newRoom.isBlock();
			if (isBlock) {
				room.setBlock(isBlock);
				room.setPassword(newRoom.getPassword());
			} else {
				room.setBlock(false);
			}
			cData.getGui().getWaiting_Gui()
					.updateRoomPnlList(cData.getChat_Room_List());
		}
	}
	public void inputVote(Vector<String> voteList, int roomNum, String voteTitle) {
		if (!cData.getMyID().equals(
				cData.getGui().getChat_Room_Gui().getRoom().getKing())) {
			cData.getGui().showVoteGUI(voteList, roomNum, voteTitle);
		}
	}

	public void totalVote(String vote_choice, int roomNum) {

		if (vote_choice.equals("무효표")) {
			noCount++;
		} else {

			if (!cData.getChoice_map().containsKey(vote_choice)) {
				cData.getChoice_map().put(vote_choice, 1);
				totalCount++;

			} else if (cData.getChoice_map().containsKey(vote_choice)) {
				int voteCount = cData.getChoice_map().get(vote_choice);
				cData.getChoice_map().put(vote_choice, voteCount + 1);
				totalCount++;
			}
		}
		if (totalCount + noCount == (cData.getGui().getChat_Room_Gui()
				.getTempVec().size() - 1)) {
			SendData send = new SendData(VOTE_END, roomNum);
			cData.sendTarget(send);

		} else {

		}

	}

	public void VoteMessage(TreeMap<String, Integer> choice_map, int roomNum,
			int totalCount) {
		Iterator<String> itr = choice_map.keySet().iterator();
		String str = "";
		String sendMsg2 = "";
		for (String num : choice_map.keySet()) {

			String sendMsg = num + "이(가)  무효표 제외 총 투표  " + totalCount + "회에서  "
					+ choice_map.get(num) + "표 받았고   "
					+ (((double) choice_map.get(num) / totalCount) * 100)
					+ "% 득표 했습니다\n";
			str += sendMsg;

		}
		String sendMsg1 = "총 투표가능인원 "
				+ ((cData.getGui().getChat_Room_Gui().getTempVec().size()) - 1)
				+ "명에서 \n";
		if (totalCount + noCount == (cData.getGui().getChat_Room_Gui()
				.getTempVec().size() - 1)) {
			sendMsg2 = "무효표 :  " + noCount + "표 있습니다.";
		} else {
			sendMsg2 = "무효표 :  "
					+ (cData.getGui().getChat_Room_Gui().getTempVec().size() - 1 - totalCount)
					+ "표 있습니다.";
		}
		str = sendMsg1 + str + sendMsg2;
		SendData send = new SendData(VOTE_RESULT_MESSAGE, str);
		sendTarget(send);

		cData.getChoice_map().clear();
		this.totalCount = 0;
		this.noCount = 0;
	}
	public void voteMsg(String str) { // 민지영 수정 채팅 // 명환26
		Color myColor = cData.getMyColor();
		Color otherColor = cData.getMyColor();

		cData.setMyColor(Color.MAGENTA);
		cData.setOtherColor(Color.MAGENTA);
		ChatRoom_GUI chatroom_gui = cData.getGui().getChat_Room_Gui();

		JTextPane tp = chatroom_gui.getTp_Chat_Room();
		StyledDocument doc =chatroom_gui.getDoc();
		SimpleAttributeSet left = chatroom_gui.getLeft();

		try {
			doc.setParagraphAttributes(doc.getLength(), 1, left, false);
			doc.insertString(doc.getLength(), str + "\n", left);
			tp.setCaretPosition(tp.getDocument().getLength());

			cData.setMyColor(myColor);
			cData.setOtherColor(otherColor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientThread(ClientData cData, Socket sk) {
		this.cData = cData;
		this.sk = sk;
		cData.setCt(this);
	}

	public String getMyID() {
		return myID;
	}

	public void setMyID(String myID) {
		this.myID = myID;
	}

	public void checkProNum(int protocol) {
		try {
			SendData send = null;
			String id = null;
			int roomNum;
			switch (protocol) {
			case CONNECT_OK:
				System.out.println("연결성공");
				cData.getGui().showLogin();
				break;
			case LOGIN_OK:
				cData.setWaitting_User_List((HashMap<String, User_Info>) sendData
						.getObj1());
				cData.setChat_Room_List((TreeMap<Integer, Chat_Room>) sendData
						.getObj2());
				cData.setOnLine_User_List((HashMap<String, User_Info>) sendData
						.getObj4());
				User myUser = (User) sendData.getObj3();
				cData.setMyID(myUser.getUid());
				cData.setInvite_Friend_List(myUser.getInvite_Friend_List());
				cData.setBlack_User_List(myUser.getBlackList());
				cData.setFriend_User_List((HashMap<String, User_Info>) sendData
						.getObj5());
				cData.getGui().allGuiClose();
				cData.getGui().showWating();
				break;
			case WATTING_USER_UPDATE:
				if ((int) sendData.getObj2() == WAITING) {
					cData.add_remove_WaitingUser((User_Info) sendData.getObj1());
					System.out.println("웨이팅");
				} else if ((int) sendData.getObj2() == ONLINE) {
					cData.add_remove_OnlineUser((User_Info) sendData.getObj1());
					System.out.println("온라인");
				} else {
					System.out.println("아닌경우");
					cData.add_remove_WaitingUser((User_Info) sendData.getObj1());
					cData.add_remove_OnlineUser((User_Info) sendData.getObj1());
					System.out.println("전체");
				}
				System.out.println("업데이트시도");
				cData.checkWatingBtn();
				System.out.println("업데이트성공");
				break;
			case LOGIN_IDMISMATCH:

				showMsg("존재하지않는 아이디입니다.");
				break;
			case LOGIN_PWMISMATCH:
				showMsg("비밀번호가 틀렸습니다.");
				break;
			case LOGIN_ALREADY_LOGIN:
				showMsg("현재 접속중인 계정입니다.");
				break;
			case LOGIN_IDBAN:
				showMsg("정지당한 계정입니다.");
				break;
			case JOIN_IDEXIST:
				showMsg("이미 존재하는 ID입니다.");
				break;
			case JOIN_OK:
				cData.getGui().getGui_Login().showIdOk();

				break;
			case BROADCAST_MSG_OK: // 민지영 수정
				Component comp = null;
				Waitting_Room_GUI watting_gui =cData.getGui().getWaiting_Gui();
				ChatRoom_GUI chatroom_gui = cData.getGui().getChat_Room_Gui();
				if ((comp = watting_gui).isShowing()&& watting_gui!=null)
					broadCastMsg(comp,sendData);
				else if((comp = chatroom_gui).isShowing() && chatroom_gui!=null) {
					broadCastMsg(comp,sendData);
				}
				break;
			case SETUP_CHAT_ROOM__OK: // 박상욱 민지영 수정
				System.out.println("채팅방개설성공");
				Chat_Room room = (Chat_Room) sendData.getObj1();
				cData.getGui().allGuiClose();
				cData.getGui().show_Chat_Gui(room);
				break;
			case CHATROOM_SET_CHANGE_OK:// /김명환0627
				System.out.println("체크왔습니까");
				Component comp3 = null;
				if ((comp3 = cData.getGui().getWaiting_Gui()).isShowing()) {
					chatRoom_Set_Change(comp3);

				} else {
					comp3 = cData.getGui().getChat_Room_Gui();
					chatRoom_Set_Change(comp3);
					showMsg("방설정이 변경되었습니다.");
				}

				break;
			case EXIT_CHATROOM_OK:// 박상욱 수정
				cData.setWaitting_User_List((HashMap<String, User_Info>) sendData
						.getObj1());
				cData.setChat_Room_List((TreeMap<Integer, Chat_Room>) sendData
						.getObj2());
				cData.setOnLine_User_List((HashMap<String, User_Info>) sendData
						.getObj3());
				cData.getGui().allGuiClose();
				cData.getGui().showWating();

				break;
			case EXIT_CHATROOM_NOTICE:// 박상욱 수정?????????????????????????????
				room = (Chat_Room) sendData.getObj1();
				cData.getGui().getChat_Room_Gui().getRoom()
						.setUser_Count(room.getUser_Count());
				cData.getGui().getChat_Room_Gui().setRoom(room);// ?
				// cData.getChat_Room_Gui().updateUser(room.getRoom_User_List());
				// //박상욱 수정

				// cData.getChat_room_Gui().set
				// 아직 룸유저안만들어져있음.
				break;
			case CHAT_ROOM_LIST_UPDATE: // 민지영 수정
				if (CHAT_ROOM_ADD_REMOVE == (int) sendData.getObj2()) {
					cData.add_remove_Chat_Room_List((Chat_Room) sendData
							.getObj1());
				} else {
					cData.add_remove_Chat_Room_List((Chat_Room) sendData
							.getObj1());
					cData.add_remove_Chat_Room_List((Chat_Room) sendData
							.getObj1());
				}
				break;
			case OPEN_CHAT_ROOM_OK:// 민지영 수정
				cData.getGui().allGuiClose();
				cData.getGui().show_Chat_Gui((Chat_Room) sendData.getObj1());
				break;
			case OPEN_CHAT_FAIL:// 박상욱 수정 0621 0705박상욱
				int proto = (int) sendData.getObj1();
				if (proto == OVERUSER) {
					showMsg("방인원 초과!.");
				} else if (proto == ENTERBAN) {
					showMsg("해당방에 들어갈 수 없습니다.!.");
				}
				break;
			case CHATROOM_FAST_ENTER_FAIL:
				showMsg("빠른입장 가능한방 없음!");
				break;

			case CHAT_ROOM_USER_UPDATE:// 박상욱 수정 06_23
				cData.updateChatRoomUser((User_Info) sendData.getObj1());
				break;
			case OPEN_NOTELIST_OK:// 박상욱 0624 쪽지
				// 동훈 06_25 수정 쪽지
					cData.getGui().show_NoteBox_Gui(
						(TreeMap<Long, Note>) sendData.getObj1(),
						(TreeMap<Long, Note>) sendData.getObj2());
				break;

			case WHISPER_OK:// 김명환25 수정
				System.out.println("귓속말임다");
				Component comp1 = null;
				if ((comp1 = cData.getGui().getWaiting_Gui()).isShowing()) {
					whisMsg(comp1);
				} else {
					comp1 = cData.getGui().getChat_Room_Gui();
					whisMsg(comp1);
				}
				break;
			case WHISPER_OK_NOTICE: // 명환25
				Component comp2 = null;
				if ((comp2 = cData.getGui().getWaiting_Gui()).isShowing()) {
					whisMsg(comp2);
				} else {
					comp2 = cData.getGui().getChat_Room_Gui();
					whisMsg(comp2);
				}
				break;
			case WHISPER_FAIL:// 명환25
				showMsg("해당유저는 오프라인입니다..");
				break;
			case WHISPER_IN_ROOM_OK:// /명환26
				cData.getGui().showWhisper(
						(HashMap<String, User_Info>) sendData.getObj1());
				break;

			case SET_NOTICE_OK:// 박상욱 0626 추가 수정 0628 수정
				cData.getGui().setNoticeOk((String) sendData.getObj2(),
						(String) sendData.getObj1());
				showMsg("새로운 공지가 등록되었습니다.");
				break;

			case DEL_NOTICE_OK:
				roomNum = (int) sendData.getObj1();
				cData.getGui().getChat_Room_Gui().getTaNotice().setText("");
				cData.getChat_Room_List().get(roomNum).setNotice_Msg(null);
				cData.getChat_Room_List().get(roomNum).setNotice_Title(null);
				break;
			case ID_BAN:
				showMsg( "당신은 정지당했다. 불량이용자뇨속" );
				cData.getGui().allGuiClose();
				break;
			case CHANGE_SORT_OK:// 김명환 수정0624
				cData.setChat_Room_List((TreeMap<Integer, Chat_Room>) sendData
						.getObj1());
				cData.updateWaitRoomChatList();
				break;
			case SEND_MASTER_MESSAGE:
				showMsg("운영자 : \n" + (String) sendData.getObj1());
				break;
			case TOTAL_USER_OK:
				break;
			case USER_PROFILE_OK:// 박상욱 수정 0624
				cData.getGui().showUserInfo_Gui((User_Info) sendData.getObj1());
				break;
			// 정재훈수정
			// ===============================================================================
			case MY_PROFILE_OK:
				cData.getGui().showUserInfo_Modified_Gui(
						(User_Info) sendData.getObj1());
				break;
			case MODIFY_MY_PROFILE_OK:
				showMsg("수정이 완료되었습니다.");
				break;
			// ===============================================================================
			case USER_KICK_OK:
				cData.setWaitting_User_List((HashMap<String, User_Info>) sendData
						.getObj1());
				cData.setChat_Room_List((TreeMap<Integer, Chat_Room>) sendData
						.getObj2());
				cData.setOnLine_User_List((HashMap<String, User_Info>) sendData
						.getObj4());
				showMsg("강퇴당함 ㅋㅋ");
				cData.getGui().allGuiClose();
				cData.getGui().showWating();
				break;
			case USER_KICK_NOTICE:
				break;
			case USER_KICK_FAIL:
				showMsg("유저가 방을 나갔습니다");
				break;
			case CAPTAIN_TOSS_OK:
				id = (String) sendData.getObj1();
				cData.getGui().getChat_Room_Gui().getRoom().setKing(id);
				cData.getGui().getChat_Room_Gui().updateUser();
				showMsg(id + " 님이 새로운 방장이 되었습니다");
				break;
			case CAPTAIN_TOSS_FAIL:
				showMsg("상대가 방을 나갔습니다.");
				break;
			case USER_INVITE_LIST_OK:// 박상욱 수정 초대
				cData.getGui().showInvite(
						(HashMap<String, User_Info>) sendData.getObj1(),
						(int) sendData.getObj2()); // 유저
				// 초대창
				break;
			case RESPOND_USER_INVITE:
				id = (String) sendData.getObj2();
				showMsg_invite(id+ "(님)이 채팅방에 초대를 하셨습니다.\n초대에 응하시겠습니까?",sendData);
				break;
			case USER_INVITE_ENTER:
				if (cData.getGui().getChat_Room_Gui().isShowing()){
				showMsg((String) sendData.getObj1() + "님 초대 수락.");
				}
				break;
			case USER_INVITE_FAIL:
				if (cData.getGui().getChat_Room_Gui().isShowing()){
				showMsg((String) sendData.getObj1() + "님 초대 거절.");
				}
				break;
			// 0627민지영==============================================================================
			case WITHDRAW_OK:
				showMsg("회원탈퇴 성공!");
				cData.getGui().allGuiClose();
				cData.getGui().showLogin();
				break;
			case WITHDRAW_FAIL:
				showMsg("비밀번호가 틀렸습니다.");
				break;
			case SEARCH_USER_LOCATION_OK:
				int rnum = (int) sendData.getObj1();
				if (rnum != -1) {
					showMsg(rnum + " 번방에 있습니다");
				} else {
					showMsg("대기실에 있습니다.");
				}
				break;
			case SEND_NOTE_OK:

				String receive_Id = (String) sendData.getObj1();
				String post_Id = (String) sendData.getObj2();
				HashMap<String, User> user = (HashMap<String, User>) sendData
						.getObj3();

				cData.setUser(user);

				if (post_Id.equals(cData.getMyID())) {
					cData.getGui()
							.getNote_Box_Gui()
							.updateNote_Box(
									user.get(post_Id).getReceiveNote(),
									cData.getGui().getNote_Box_Gui()
											.getPnlSendNote());
					cData.getGui()
							.getNote_Box_Gui()
							.updateNote_Box(
									user.get(post_Id).getSendNote(),
									cData.getGui().getNote_Box_Gui()
											.getPnlSendNote());
				}
				if (receive_Id.equals(cData.getMyID())) {

					cData.getGui()
							.getNote_Box_Gui()
							.updateNote_Box(
									user.get(receive_Id).getReceiveNote(),
									cData.getGui().getNote_Box_Gui()
											.getPnlReceivedNote());
					showMsg(post_Id + " 님이 보낸 쪽지가 도착했습니다.");
				} else {
					cData.getGui()
							.getNote_Box_Gui()
							.updateNote_Box(
									user.get(post_Id).getSendNote(),
									cData.getGui().getNote_Box_Gui()
											.getPnlSendNote());
					showMsg(receive_Id + "님이 쪽지를 받으셨습니다.");
				}

				break;
			// 0627민지영==============================================================================
			// 07_01 동훈 수정 쪽지
			case DELETE_NOTE_OK:
				HashMap<String, User> noteUser = (HashMap<String, User>) sendData
						.getObj1();
				cData.getGui()
						.getNote_Box_Gui()
						.updateNote_Box(
								noteUser.get(cData.getMyID()).getReceiveNote(),
								cData.getGui().getNote_Box_Gui()
										.getPnlReceivedNote());
				cData.getGui()
						.getNote_Box_Gui()
						.updateNote_Box(
								noteUser.get(cData.getMyID()).getSendNote(),
								cData.getGui().getNote_Box_Gui()
										.getPnlSendNote());
				showMsg("쪽지가 삭제되었습니다");
				break;
			// 07_01 동훈 수정 쪽지
			case CHATROOM_SEND_NOTE_OK:
				cData.getGui().showSendNote(
						(HashMap<String, User_Info>) sendData.getObj1());
				break;

			case CHATROOM_SET_CHANGE_FAIL:
				showMsg("현재 방 인원수보다 적게 설정할수없습니다.");
				break;
			case USER_FRIEND_ADD:
				showMsg("친구요청이 도착했습니다.");
				// 0702
				cData.addInvite_Friend_List((User_Info) sendData.getObj1());
				// 친구추가요청 레이블 만들어서 갱신해주기//
				break;
			case USER_FRIEND_ADD_OK:
				User_Info usome = (User_Info) sendData.getObj1();
				showMsg(usome.getId() + "님이 친구요청을 수락했습니다.");
				cData.addFriend_User_List(usome);
				cData.removeInvite_Friend_List(usome);

				// //////////친구패널 업데이트해주기///////////

				break;
			case USER_FRIEND_ADD_NO:
				showMsg((String) sendData.getObj1() + "님이 친구요청 거절했다.");
				break;
			case USER_FRIEND_REMOVE_OK:
				cData.removeFriend_User_List((String) sendData.getObj1());

				// ////////////친구패널 업데이트해주기//////////////
				break;
			case USER_BLAKC_ADD_OK:
				showMsg((String) sendData.getObj1() + "님이 블랙리스트에 등록됨");
				cData.addBlack_User_List((String) sendData.getObj1());
				break;
			case USER_BLAKC_ADD_NO:
				showMsg("자기자신은 블랙리스트에 추가할수 없습니다.");
				break;
			case USER_BLAKC_REMOVE_OK:
				cData.removeBlack_User_List((String) sendData.getObj1());
				showMsg((String) sendData.getObj1() + "님이 블랙리스트에서 제거됨");
				break;
			case CHATROOM_VOTE_SPREAD:
				System.out.println("리져트스프레드");
				Vector<String> voteList = (Vector<String>) sendData.getObj1();
				roomNum = (int) sendData.getObj2();
				String voteTitle = (String) sendData.getObj3();
				inputVote(voteList, roomNum, voteTitle);

				break;

			case CHATROOM_VOTE_RESULTBACK_SPREAD:
				System.out.println("리져트백스프레드");
				String vote_choice = (String) sendData.getObj1();
				roomNum = (int) sendData.getObj2();

				totalVote(vote_choice, roomNum);

				break;

			case VOTE_END_OK:
				roomNum = (int) sendData.getObj1();
				if (cData.getGui().getChat_Room_Gui().getRoom().getKing()
						.equals(cData.getMyID())) {
					VoteMessage(cData.getChoice_map(), roomNum, totalCount);
				
					if (cData.getGui().getVoteGUI2().isShowing()) {
						cData.getGui().getVoteGUI2().dispose();
						System.out.println("보트가 꺼진다.");
						showMsg("모두가 투표를 끝냈습니다.");
					}
				}
				if (cData.getGui().getVoteGUI().isShowing()) {
					cData.getGui().getVoteGUI().dispose();
					showMsg("투표마감 되었습니다");

				}
				break;
			case LOGOUT_OK :
				cData.getGui().allGuiClose();
				cData.getGui().showLogin();
				break;
			case VOTE_RESULT_MESSAGE_OK:
				String str = (String) sendData.getObj1();
				voteMsg(str);
				break;
			}
		} catch (Exception e) {
		}
	}

}