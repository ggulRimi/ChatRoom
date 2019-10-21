import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.JOptionPane;

import org.w3c.dom.CDATASection;

public class ServerThread extends Thread implements iProtocol {
	private ServerData sd;
	private ObjectOutputStream oos = null;
	private SendData sendData;
	private ObjectInputStream ois = null;
	private Object user_Info_Key;
	private Object Chat_Room_Key;
	private String id = null;
	private String myID;
	private Object send_User_Key;

	private final int OVERUSER =1; //0705박상욱
	private final int ENTERBAN =2;
	// public static void main(String[] args) {
	//
	// }

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public ServerThread(ServerData sd, Socket socket) {
		this.sd = sd;
		user_Info_Key = sd.getUser_Info_Key();
		Chat_Room_Key = sd.getChat_Room_Key();
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			
		}
	}

	public void sendTarget(SendData send) {
		try {
			oos.writeObject(send);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
		}
	}

	// ///////////////////////////////////////////명환 25
	public void whispering(String whisperMsg, String wc) {
		synchronized (sd.getHm()) {

			if (wc.equals("/친구")) {
				sd.sendUsers(new SendData(WHISPER_OK, whisperMsg, wc),
						sd.returnFriendList(id));
				SendData whisperSend2 = new SendData(WHISPER_OK_NOTICE,
						whisperMsg);
				sendTarget(whisperSend2);
			} else {
				if (sd.getOnLine_User_List().containsKey(wc)) {
					SendData whisperSend = new SendData(WHISPER_OK, whisperMsg,
							wc);
					try {
						ServerThread st = sd.getHm().get(wc);
						ObjectOutputStream st_Oos = st.getOos();
						st_Oos.writeObject(whisperSend);
						st_Oos.flush();
						st_Oos.reset();

					} catch (IOException e) {
						e.printStackTrace();
					}
					SendData whisperSend2 = new SendData(WHISPER_OK_NOTICE,
							whisperMsg);
					sendTarget(whisperSend2);
				} else {
					SendData whisperFailSend = new SendData(WHISPER_FAIL);
					sendTarget(whisperFailSend);
				}
			}
		}
	}

	// 민지영 수정
	// ################################바꾼것#######################################
	public void broadCastMsg(String msg) { // 민지영 수정
		synchronized (sd.getHm()) {
			String newMsg = null;
			if (msg.contains("(님)")) {
				newMsg = sd.checkFiltering(msg, id);
			} else {
				newMsg = msg;
			}
			SendData send = new SendData(BROADCAST_MSG_OK, newMsg);
			
			if (sd.getWaitting_User_List().containsKey(id)) {
				//
				sd.sendUsers(send, sd.getWaitting_User_List(), id);
			} else {
				int roomNum = sd.getUser_Map().get(id).getuRoomInNum();
				sd.sendUsers(send, sd.user_In_Room(roomNum).getRoom_User_List());
			}
			
		}
	}
	// ################################바꾼것#######################################

	public void TotalSend() {
		HashMap<String, User_Info> OnLine = sd.getOnLine_User_List();
		HashMap<String, User_Info> OffLine = sd.getOffLine_User_List();

		Collection<ServerThread> collection = sd.getHm().values();
		Iterator<ServerThread> iter = collection.iterator();
		while (iter.hasNext()) {
			SendData send = new SendData(TOTAL_USER_OK, OnLine, OffLine);
			try {
				ServerThread st = iter.next();
				ObjectOutputStream st_Oos = st.getOos();
				st_Oos.writeObject(send);
				st_Oos.flush();
				st_Oos.reset();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendNote(String receive_Id, String post_Id) { // 박상욱 수정 쪽지 0624
		synchronized (sd.getUser_Map()) {
		if (sd.getOnLine_User_List().containsKey(receive_Id)) {
			HashMap<String, User> user = sd.getUser_Map();
			SendData sendData = new SendData(SEND_NOTE_OK, receive_Id, post_Id,
					user);
			ServerThread targetSt = sd.getHm().get(receive_Id);
			try {
				targetSt.sendTarget(sendData);
			} catch (Exception e) {
			}
			sendTarget(sendData);
		}
		}
	}
	public void delete_Note(String deleteId,int deleteNote,long noteTime){
		synchronized (sd.getUser_Map()) {
		if (deleteNote == 0) {
			sd.getUser_Map().get(deleteId).getSendNote().remove(noteTime);
			sd.getUser_Map().get(deleteId).getReceiveNote().remove(noteTime);

		} else if (deleteNote == 1) {
			sd.getUser_Map().get(deleteId).getSendNote()
					.remove(noteTime);
		} else if (deleteNote == 2) {
			sd.getUser_Map().get(deleteId).getReceiveNote()
					.remove(noteTime);
		}
		HashMap<String, User> NoteUser = sd.getUser_Map();
		SendData send = new SendData(DELETE_NOTE_OK, NoteUser);
		sendTarget(send);
		}
	}
	public void VoteMessage(String str){
		synchronized(sd.getHm()){
			SendData send = new SendData(VOTE_RESULT_MESSAGE_OK,str);
			int roomNum = sd.getUser_Map().get(id).getuRoomInNum();
			sd.sendUsers(send, sd.user_In_Room(roomNum).getRoom_User_List());
		}
	}
	public void captain_Toss(int roomNum, String kingid){
		synchronized (sd.getChat_Room_List().get(roomNum)) {
		String preKing = sd.getChat_Room_List().get(roomNum).getKing();
		sd.getChat_Room_List().get(roomNum).setKing(kingid);

		SendData send = new SendData(CAPTAIN_TOSS_OK, kingid);
		sd.sendUsers(send, sd.getChat_Room_List().get(roomNum).getRoom_User_List());
		// /toss ok
		send = new SendData(BROADCAST_MSG_OK, preKing+ "(방장)이 방장권한을 넘겼습니다.... 새로운 방장 : " + kingid);
		sd.sendUsers(send, sd.getChat_Room_List().get(roomNum)
				.getRoom_User_List());
		}
	}
	public void delete_Notice(int roomNum){
		synchronized (sd.getChat_Room_List().get(roomNum)) {
			sd.getChat_Room_List().get(roomNum).setNotice_Msg("");//0705박상욱
			sd.getChat_Room_List().get(roomNum).setNotice_Title("");
		}
	}

	private void checkProNum(int protocol) {

		try {
			SendData send = null;
			Chat_Room room = null;
			long time;
			int roomNum;
			HashMap<String, User_Info> map = null;
			User user = null;
			switch (protocol) {
			case LOGIN:
				user = (User) sendData.getObj1();
				int checkuser = sd.Login_Check(user);
				if (checkuser == LOGIN_OK) {
					id = user.getUid();
					send = new SendData(LOGIN_OK, sd.getWaitting_User_List(),
							sd.getChat_Room_List(), sd.getUser_Map().get(id),
							sd.getOnLine_User_List(), sd.returnFriendList(id));
				} else if (checkuser == LOGIN_ALREADY_LOGIN) {
					send = new SendData(LOGIN_ALREADY_LOGIN);
				} else if (checkuser == LOGIN_IDBAN) {
					send = new SendData(LOGIN_IDBAN);
				} else if (checkuser == LOGIN_IDMISMATCH) {
					send = new SendData(LOGIN_IDMISMATCH);
				} else if (checkuser == LOGIN_PWMISMATCH) {
					send = new SendData(LOGIN_PWMISMATCH);
				}
				sendTarget(send);
				sd.putHm(id, this);
				sd.updateWaitingUser(id, LOG);

				break;
			case JOIN:
				user = (User) sendData.getObj1();
				if (sd.Join(user)) {
					send = new SendData(JOIN_OK);
					sendTarget(send);
					sd.getSut().upDateGui_User();
				} else {
					send = new SendData(JOIN_IDEXIST);
					sendTarget(send);
				}
				break;
			case LOGOUT:
				//07-05 동훈 수정
				sd.removeHm(id);
				send = new SendData(LOGOUT_OK);
				sd.sendTarget(send, oos);
				break;
			// 0627민지영==============================================================================
			case WITHDRAW:
				sd.withdrawUser(id, (String) sendData.getObj1());
				break;
			// 0627민지영==============================================================================
			case OPEN_CHAT_ROOM:// 민지영 //박상욱 0705
				roomNum = (int) sendData.getObj1();
				sd.enter_Room(roomNum, id);
				break;
			case OPEN_LOCK_CHAT_ROOM:
				break;
			case SETUP_CHAT_ROOM:// 방개설 //수정 박상욱
				room = (Chat_Room) sendData.getObj1();
				sd.putChat_Room_List(room);
				sd.removeWaiting(id);
				break;
			case CHATROOM_FAST_ENTER:// 0703박상욱 0705박상욱
				sd.fast_Enter_Room(id);
				break;
			case SEARCH_CHATROOM:
				break;
			//
			case BROADCAST_MSG:
				// 받았다 메세지
				String msg = (String) sendData.getObj1();
				broadCastMsg(msg);
				break;
			case ONLINE_USER_LIST:
				break;
			case WHISPER: // 김명환 수정25
				String whisperMsg = (String) sendData.getObj1();
				String wc = (String) sendData.getObj2();
				whispering(whisperMsg, wc);

				break;
			case WHISPER_IN_ROOM:
				send = new SendData(WHISPER_IN_ROOM_OK,
						sd.getOnLine_User_List());
				sendTarget(send);

				break;
			case CAPTAIN_TOSS:
				String kingid = (String) sendData.getObj1();
				roomNum = (int) sendData.getObj2();
				if (sd.getWaitting_User_List().containsKey(kingid)) {
					send = new SendData(CAPTAIN_TOSS_FAIL);
					sendTarget(send);
				} else {
					captain_Toss(roomNum, kingid);
				}
				break;
			case USER_KICK:
				String kickid = (String) sendData.getObj1();
				int kickIdx = (int) sendData.getObj2();
				roomNum = (int) sendData.getObj3();
				// roomNum = sd.getUser_Map().get(id).getuRoomInNum();
				if (sd.getWaitting_User_List().containsKey(kickid)) {
					send = new SendData(USER_KICK_FAIL);
					sendTarget(send);
				} else {
					sd.addWaiting(kickid);// 나가졌으니 대기열추가
					sd.remove_Room_User(roomNum, kickid, "강퇴당하였습니다."); // 제거
																		// 성공~~~
					// 나갈애한태 해주고
					// 남아있는 유저
					send = new SendData(USER_KICK_OK,
							sd.getWaitting_User_List(), sd.getChat_Room_List(),
							id, sd.getOnLine_User_List());
					// 혹시모르니깐
					ObjectOutputStream oos = sd.getHm().get(kickid).getOos();
					oos.writeObject(send);
					oos.flush();
					oos.reset();
				}
				break;
			case EXIT_CHATROOM:// 수정 박상욱
				sd.exit_Room(id);
				break;
			case SET_NOTICE:
				sd.setNotice(sendData);
				break;
			case DEL_NOTICE:
				roomNum = (int) sendData.getObj1();
				delete_Notice(roomNum);
//				sd.getChat_Room_List().get(roomNum).setNotice_Msg("");//0705박상욱
//				sd.getChat_Room_List().get(roomNum).setNotice_Title("");
				send = new SendData(DEL_NOTICE_OK, roomNum);
				sd.sendUsers(send, sd.getChat_Room_List().get(roomNum).getRoom_User_List());
				break;
			case USER_INVITE: // 0625 박상욱 초대
				synchronized (sd.getHm()) {
					Vector<String> inviteList = (Vector<String>) sendData.getObj1();
					roomNum = (int) sendData.getObj2();
					time =sd.getChat_Room_List().get(roomNum).getTime();
					String inviteId = (String) sendData.getObj3();
					send = new SendData(RESPOND_USER_INVITE, roomNum, inviteId,time);
					sd.sendUsersVector(send, inviteList);
				}
				break;
			case USER_INVITE_RESPOND_OK:// 0625 박상욱 초대
				send = new SendData(USER_INVITE_ENTER, id);
				sd.sendTarget2(send, (String) sendData.getObj1()); // 초대한 사람한태
				
				id = (String) sendData.getObj2(); // 대기실 제거 채팅방 유저 추가 --> 채팅방 유저
				roomNum = (int) sendData.getObj3();
				time = (long)sendData.getObj4();
				room = sd.getChat_Room_List().get(roomNum);
				if (sd.getChat_Room_List().containsKey(roomNum)){
					if(sd.getChat_Room_List().get(roomNum).getTime() ==time){
						if ((room.getMax_User()) > (room.getUser_Count())){
							map = room.getRoom_User_List();//
							sd.add_Room_User(roomNum, id);
							sd.removeWaiting(id);
							send = new SendData(OPEN_CHAT_ROOM_OK, sd
									.getChat_Room_List().get(roomNum));
						}else{
							send = new SendData(OPEN_CHAT_FAIL,ENTERBAN);
						}
					}else{
						send = new SendData(OPEN_CHAT_FAIL,ENTERBAN);
					}
				}else {
					send = new SendData(OPEN_CHAT_FAIL,ENTERBAN);
				}
				sendTarget(send);
				sd.updateChatRoom(room, CHAT_ROOM_CHANGE);
				break;
			case USER_INVITE_RESPOND_NO:// 0625 박상욱 초대
				send = new SendData(USER_INVITE_FAIL,
						(String) sendData.getObj2());
				sd.sendTarget2(send, (String) sendData.getObj1());
				break;
			case USER_INVITE_LIST:
				roomNum = (int) sendData.getObj1();
				send = new SendData(USER_INVITE_LIST_OK,
						sd.getWaitting_User_List(), roomNum);
				sendTarget(send);
				break;
			// 0627민지영==============================================================================
			case SEARCH_USER_LOCATION:
				int roomnum = sd.find_Chat_Room_Number_User((String) sendData
						.getObj1());
				sendTarget(new SendData(SEARCH_USER_LOCATION_OK, roomnum));
				break;
			// 0627민지영==============================================================================
			case WAITING_ROOM_USER:
				break;
			case TOTAL_USER:
				TotalSend();
				break;
			// 0625정재훈수정
			// ======================================================================================
			case USER_PROFILE:// 0624 박상욱수정
				if ((String) sendData.getObj1() != null) {
					id = (String) sendData.getObj1();
					User_Info info = sd.getOnLine_User_List().get(id);
					send = new SendData(USER_PROFILE_OK, info);
					sendTarget(send);
				} else if ((String) sendData.getObj1() == null
						|| (String) sendData.getObj2() != null) {
					myID = (String) sendData.getObj2();
					User_Info info = sd.getOnLine_User_List().get(myID);
					send = new SendData(MY_PROFILE_OK, info);
					sendTarget(send);
				}
				break;
			case MODIFY_MY_PROFILE:
				User_Info myInfo = (User_Info) sendData.getObj1();
				sd.getOnLine_User_List().put(myID, myInfo);
				sd.getWaitting_User_List().put(myID, myInfo);
				sd.getOffLine_User_List().put(myID, myInfo);
				send = new SendData(MODIFY_MY_PROFILE_OK);
				sendTarget(send);
				break;
			// 정재훈수정
			// =================================================================================

			case SEND_NOTE:// 박상욱 수정 0624 쪽지 //동훈 수정 0709박상욱
				Note note = (Note) sendData.getObj1();
				time = (Long) sendData.getObj2();
				String receive_id = note.getReceive_id();
				String post_id = note.getPost_Id();
				sd.addSendNote(post_id, receive_id, time, note);
//				sd.getUser_Map().get(post_id).getSendNote().put(time, note);
//				sd.getUser_Map().get(receive_id).getReceiveNote().put(time, note);

				sendNote(receive_id, post_id);

				break;
			case OPEN_NOTEBOX:// 박상욱 수정
				sendTarget(sd.okNoteOpen(id));
				break;
			case OPEN_NOTE:
				break;
			// 07_01 동훈 쪽지 수정
			case CHATROOM_SEND_NOTE:
				send = new SendData(CHATROOM_SEND_NOTE_OK,
						sd.getOnLine_User_List());
				sendTarget(send);
				break;
			case DELETE_NOTE:
				Long noteTime = (Long) sendData.getObj1();
				String deleteId = (String) sendData.getObj2();
				int deleteNote = (int) sendData.getObj3();
				delete_Note(deleteId, deleteNote, noteTime);
				break;
			// 07_01 동훈 쪽지 수정
			case CHANGE_SORT: //내가 수정 
				String cate_item = (String) sendData.getObj1();
				String block_item = (String) sendData.getObj2();
				TreeMap<Integer, Chat_Room> temp = new TreeMap<Integer, Chat_Room>();
				id = (String) sendData.getObj3();
				if (cate_item.equals("번호")) {
					if (block_item.equals("전체")) {
						temp = sd.getChat_Room_List(); // 그대로 보내면됨.
					} else if (block_item.equals("공개")) {
						for (int i = 0; i < sd.getChat_Room_List().size(); i++) {
							if (sd.getChat_Room_List().get(i).isBlock() == false) {
								temp.put(i, sd.getChat_Room_List().get(i));
							}
						}
					} else if (block_item.equals("비공개")) {
						for (int i = 0; i < sd.getChat_Room_List().size(); i++) {
							if (sd.getChat_Room_List().get(i).isBlock() == true) {
								temp.put(i, sd.getChat_Room_List().get(i));
							}
						}
					}
				} else {
					if (block_item.equals("공개")) {
						for (int i = 0; i < sd.getChat_Room_List().size(); i++) {
							if (sd.getChat_Room_List().get(i)
									.getCategory_Name().equals(cate_item)
									&& sd.getChat_Room_List().get(i).isBlock() == false) {
								temp.put(i, sd.getChat_Room_List().get(i));
							}
						}
					} else if (block_item.equals("비공개")) {
						for (int i = 0; i < sd.getChat_Room_List().size(); i++) {
							if (sd.getChat_Room_List().get(i)
									.getCategory_Name().equals(cate_item)
									&& sd.getChat_Room_List().get(i).isBlock() == true) {
								temp.put(i, sd.getChat_Room_List().get(i));
							}
						}
					} else if (block_item.equals("전체")) {
						for (int i = 0; i < sd.getChat_Room_List().size(); i++) {
							if (sd.getChat_Room_List().get(i)
									.getCategory_Name().equals(cate_item)) {
								temp.put(i, sd.getChat_Room_List().get(i));
							}
						}
					}
				}
				send = new SendData(CHANGE_SORT_OK, temp);
				sendTarget(send);
				break;
			case CHATROOM_SET_CHANGE:// 대기유저들한태는 -> 룸리스트 갱신 방유저들한태는 내부 변화.
	
				Chat_Room setRoom = (Chat_Room) sendData.getObj1();
				roomNum = (int) sendData.getObj2();
				boolean isBlock = setRoom.isBlock();
				room = sd.getChat_Room_List().get(roomNum);
				if ((room.getRoom_User_List().keySet().size() <= setRoom
						.getMax_User())) {
					room.setMax_User(setRoom.getMax_User());
				} else {
					send = new SendData(CHATROOM_SET_CHANGE_FAIL);
					sendTarget(send);
					break;
				}
				room.setTitle(setRoom.getTitle());
				room.setCategory_Name(setRoom.getCategory_Name());
				if (isBlock) {
					room.setBlock(isBlock);
					room.setPassword(setRoom.getPassword());
				} else {
					room.setBlock(false);
				}
		
				send = new SendData(CHATROOM_SET_CHANGE_OK, room);
				sd.sendUsers(send, sd.getChat_Room_List().get(roomNum)
						.getRoom_User_List());
				send = new SendData(CHATROOM_SET_CHANGE_OK, room);
				sd.sendUsers(send, sd.getWaitting_User_List());

		
				break;
			case USER_FRIEND_ADD:
				sd.sendTarget2(new SendData(USER_FRIEND_ADD, sd.getUser_Map()
						.get(id).toUser_Info()), (String) sendData.getObj1());
				sd.getUser_Map().get((String) sendData.getObj1()).addInvite_Friend_List(sd.getUser_Map().get(id).toUser_Info());
				break;
			case USER_FRIEND_ADD_OK:
				String targetid = (String) sendData.getObj1();
				sd.addFriendUser(id, targetid);
				sd.addFriendUser(targetid, id);
				if (sd.getWaitting_User_List().containsKey(id)) {
					sendTarget(new SendData(USER_FRIEND_ADD_OK, sd
							.getUser_Map().get(targetid).toUser_Info()));
				}
				if (sd.getWaitting_User_List().containsKey(targetid)) {
					sd.sendTarget2(new SendData(USER_FRIEND_ADD_OK, sd
							.getUser_Map().get(id).toUser_Info()), targetid);
				}
				sd.getUser_Map()
						.get(id)
						.removeInvite_Friend_list(
								sd.getUser_Map()
										.get((String) sendData.getObj1())
										.toUser_Info());
				break;
			case USER_FRIEND_ADD_NO:
				sd.sendTarget2(new SendData(USER_FRIEND_ADD_NO, id),
						(String) sendData.getObj1());
				sd.getUser_Map()
						.get(id)
						.removeInvite_Friend_list(
								sd.getUser_Map()
										.get((String) sendData.getObj1())
										.toUser_Info());
				break;
			case USER_BLAKC_ADD:
				if (!(id.equals((String) sendData.getObj1()))) {
					sd.addBlackUser(id, (String) sendData.getObj1());
					sendTarget(new SendData(USER_BLAKC_ADD_OK,
							(String) sendData.getObj1()));
				} else {
					sendTarget(new SendData(USER_BLAKC_ADD_NO));
				}
				break;
			case USER_FRIEND_REMOVE:
				sd.removeFriendUser(id, (String) sendData.getObj1());
				sd.removeFriendUser((String) sendData.getObj1(), id);
				sd.sendTarget2(new SendData(USER_FRIEND_REMOVE_OK, id),
						(String) sendData.getObj1());
				sendTarget(new SendData(USER_FRIEND_REMOVE_OK,
						(String) sendData.getObj1()));
				break;
			case USER_BLAKC_REMOVE:
				sd.removeBlackUser(id, (String) sendData.getObj1());
				sendTarget(new SendData(USER_BLAKC_REMOVE_OK,
						(String) sendData.getObj1()));
				break;

			case CHATROOM_VOTE:
				Vector<String> voteList = (Vector<String>) sendData.getObj1();
				roomNum = (int) sendData.getObj2();
				String voteTitle = (String) sendData.getObj3();
				send = new SendData(CHATROOM_VOTE_SPREAD, voteList, roomNum,
						voteTitle);
				sd.sendUsers(send, sd.getChat_Room_List().get(roomNum)
						.getRoom_User_List());

				break;

			case CHATROOM_VOTE_RESULTBACK:
				roomNum = (int) sendData.getObj2();
				String vote_choice = (String) sendData.getObj1();
				send = new SendData(CHATROOM_VOTE_RESULTBACK_SPREAD,
						vote_choice, roomNum);
				sd.sendTarget2(send, sd.getChat_Room_List().get(roomNum)
						.getKing());

				break;
			case VOTE_END:
				roomNum = (int) sendData.getObj1();
				send = new SendData(VOTE_END_OK, roomNum);
				sd.sendUsers(send, sd.getChat_Room_List().get(roomNum)
						.getRoom_User_List());
				break;

			case VOTE_RESULT_MESSAGE:
				String str = (String)sendData.getObj1();
				VoteMessage(str);
				
				break;
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		try {
			sendTarget(new SendData(CONNECT_OK));
			Object src = null;
			while ((src = ois.readObject()) != null) {
				sendData = (SendData) src;
				int protocol = sendData.getProtocol();
				checkProNum(protocol);
			}
		} catch (Exception e) {
			if (id != null) {
			}

		} finally {
			sd.removeHm(id);
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
			}
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (Exception e) {
			}
		}
	}
}