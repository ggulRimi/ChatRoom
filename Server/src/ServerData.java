import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;

public class ServerData implements Serializable, iProtocol {

	private static final long serialVersionUID = 1L;

	private transient Object User_Info_Key;
	private transient Object Chat_Room_Key;
	private transient Object send_User_Key;
	private transient HashMap<String, ServerThread> hm;
	private transient ServerUiThread sut = null;

	private HashMap<String, User> user_Map;
	private HashMap<String, User_Info> onLine_User_List;
	private HashMap<String, User_Info> offLine_User_List;
	private HashMap<String, User_Info> waitting_User_List;
	private TreeMap<Integer, Chat_Room> chat_Room_List;
	private HashMap<String, NoteBox> NoteBox_Map;
	private HashMap<String, Integer> banCount;
	private Vector<String> ban_User_List;

	private Vector<String> filter;
	private final int OVERUSER = 1; // 0705박상욱
	private final int ENTERBAN = 2;

	public HashMap<String, User_Info> returnFriendList(String id) {
		User some = user_Map.get(id);
		Vector<String> list = some.getFriendList();
		HashMap<String, User_Info> newlist = new HashMap<String, User_Info>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			String str = list.get(i);
			newlist.put(str, user_Map.get(str).toUser_Info());
		}
		return newlist;
	}

	public synchronized void addFriendUser(String id, String target) {
		user_Map.get(id).addFriend(target);
	}

	public synchronized void removeFriendUser(String id, String target) {
		user_Map.get(id).removeFriend(target);
	}

	public synchronized void addBlackUser(String id, String target) {
		user_Map.get(id).addBlakcList(target);
	}

	public synchronized void removeBlackUser(String id, String target) {
		user_Map.get(id).removeBlakcList(target);
	}

	public String filterStr(String msg, String str) {
		if (msg.contains(str)) {
			return msg.replace(str, "$$$");
		} else {
			return msg;
		}
	}

	// 0627민지영==============================================================================
	public synchronized void withdrawUser(String id, String pw) {
		User target = user_Map.get(id);
		if (target.getUpw().equals(pw)) {
			hm.get(id).sendTarget(new SendData(WITHDRAW_OK));
			removeHm(id);
			user_Map.remove(id);
		} else {
			hm.get(id).sendTarget(new SendData(WITHDRAW_FAIL));
		}
	}
	public void addSendNote(String post_id,String receive_id,long time, Note note){
		synchronized (user_Map) {
		user_Map.get(post_id).getSendNote().put(time, note);
		user_Map.get(receive_id).getReceiveNote().put(time, note);
		}

	}
	// 0627민지영==============================================================================
	public String checkFiltering(String msg, String id) {
		int size = filter.size();
		int idx = msg.indexOf(':');
		String name = msg.substring(0, idx);
		String str = msg.substring(idx);
		for (int i = 0; i < size; i++) {
			str = filterStr(str, filter.get(i));
		}
		String filterMsg = name + str;
		if (msg.equals(filterMsg)) {
			return msg;
		} else {
			if (user_Map.get(id).getSlangCount() != 4) {
				user_Map.get(id).addSlangCount();
				hm.get(id).sendTarget(
						new SendData(SEND_MASTER_MESSAGE, "욕설"
								+ (5 - user_Map.get(id).getSlangCount())
								+ "번 더하면 정지"));
				sut.upDateGui_Slang();
			} else {
				user_Map.get(id).setSlangCount(0);
				BanUser(id);
				sut.upDateGui_Slang();
			}
			return name + str;
		}
	}

	public synchronized void addFilter(String str) {
		filter.add(str);
	}

	public synchronized void removeFilter(String str) {
		filter.remove(str);
	}

	public boolean checkInFilter(String str) {
		return filter.contains(str);
	}

	public Vector<String> getFilter() {
		return filter;
	}

	public synchronized void setFilter(Vector<String> filter) {
		this.filter = filter;
	}

	public synchronized void add_Chat_Room(Chat_Room room) {

	}

	public int find_Chat_Room_Number_User(String id) {
		return user_Map.get(id).getuRoomInNum();
	}

	public Chat_Room user_In_Room(int roomNum) {
		return chat_Room_List.get(roomNum);
	}

	public  void BanUser(String id) {
		synchronized (Chat_Room_Key) {
			if (user_Map.get(id).getuRoomInNum() != -1) {
				int roomNum = user_Map.get(id).getuRoomInNum();
				remove_Room_User(roomNum, id);
				addWaiting(id);
			}
		}
		user_Map.get(id).setBan();
		ban_User_List.add(id);
		if (hm.containsKey(id)) {
			hm.get(id).sendTarget(new SendData(ID_BAN));
			removeHm(id);
		}
		sut.upDateGui_Ban();
		sut.upDateGui_User();
	}

	public synchronized void unBanUser(String id) {
		user_Map.get(id).setBan();
		ban_User_List.remove(id);
		sut.upDateGui_Ban();
	}

	//
	public ServerUiThread getSut() {
		return sut;
	}

	public  void setSut(ServerUiThread sut) {
		this.sut = sut;
	}

	//
	public void exit_Room(String id) {
		synchronized ((hm)) {
			int roomNum = user_Map.get(id).getuRoomInNum();
			SendData send = new SendData(EXIT_CHATROOM_OK, waitting_User_List,
					chat_Room_List, onLine_User_List);
			remove_Room_User(roomNum, id);
			sendTarget(send, (hm.get(id).getOos()));
			addWaiting(id);
		}
	}
//
	public void enter_Room(int roomNum, String id) {
		synchronized (hm) {
			SendData send = null;
			Chat_Room room = chat_Room_List.get(roomNum);
			if (!user_Map.get(room.getKing()).getBlackList().contains(id)) {
				if ((room.getMax_User()) > (room.getUser_Count())) {
					HashMap<String, User_Info> map = room.getRoom_User_List();//
					add_Room_User(roomNum, id);
					send = new SendData(OPEN_CHAT_ROOM_OK,chat_Room_List.get(roomNum));
				} else {
					send = new SendData(OPEN_CHAT_FAIL, OVERUSER);
				}
			} else {
				send = new SendData(OPEN_CHAT_FAIL, ENTERBAN);
			}
			sendTarget(send,hm.get(id).getOos());
			updateChatRoom(room, CHAT_ROOM_CHANGE);
		}
	}

	public void fast_Enter_Room(String id) {
		synchronized ((hm)) {
			TreeMap<Integer, Chat_Room> chat_roomList = chat_Room_List;
			HashMap<Integer, Chat_Room> new_ChatRoom_list = new HashMap<Integer, Chat_Room>();
			SendData send = null;
			int random = 0;
			if (chat_roomList.size() != 0) {
				for (Integer idx : chat_roomList.keySet()) {
					if (!chat_roomList.get(idx).isBlock()) {
						if (!user_Map.get(chat_roomList.get(idx).getKing())
								.getBlackList().contains(id)) {
							if ((chat_roomList.get(idx).getMax_User()) > (chat_roomList
									.get(idx).getUser_Count())) {

								new_ChatRoom_list.put(idx,
										chat_roomList.get(idx));
							}
						}
					}
				}
				if (new_ChatRoom_list.size() != 0) {
					Vector<Integer> roomNumArr = new Vector<Integer>();
					for (Integer idx : new_ChatRoom_list.keySet()) {
						roomNumArr.add(idx);
					}
					random = (int) (Math.random() * roomNumArr.size());
					Chat_Room room = new_ChatRoom_list.get(roomNumArr
							.get(random));
					if ((room.getMax_User()) > (room.getUser_Count())) {
						HashMap<String, User_Info> map = room
								.getRoom_User_List();//
						add_Room_User(roomNumArr.get(random), id);
						send = new SendData(OPEN_CHAT_ROOM_OK,
								chat_Room_List.get(roomNumArr.get(random)));
					} else {
						send = new SendData(OPEN_CHAT_FAIL, OVERUSER);

					}
					sendTarget(send, (hm.get(id).getOos()));
					updateChatRoom(room, CHAT_ROOM_CHANGE);
				} else {
					send = new SendData(CHATROOM_FAST_ENTER_FAIL);
					sendTarget(send, (hm.get(id).getOos()));
				}
			} else {
				send = new SendData(CHATROOM_FAST_ENTER_FAIL);
				sendTarget(send, (hm.get(id).getOos()));
			}
		}
	}

	public synchronized void sendTarget(SendData send, ObjectOutputStream oos) {
		try {
			oos.writeObject(send);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
		}
	}

	public  void sendUsers(SendData senddata, HashMap<String, User_Info> map) {

		if (map.size() > 0) {
			Iterator<String> itr = map.keySet().iterator();
			while (itr.hasNext()) {
				ServerThread st = hm.get(itr.next());
				st.sendTarget(senddata);
			}
		}
	}

	// 0705박상욱 추가
	public synchronized void sendUsers(SendData senddata, HashMap<String, User_Info> map,
			String id) {
		if (map.size() > 0) {
			Iterator<String> itr = map.keySet().iterator();
			while (itr.hasNext()) {
				String targetId = itr.next();
				if (!user_Map.get(targetId).getBlackList().contains(id)) {
					if (user_Map.get(id).getBlackList().size() > 0) {
						for (String blackId : user_Map.get(id).getBlackList()) {
							if (!targetId.equals(blackId)) {
								ServerThread st = hm.get(targetId);
								st.sendTarget(senddata);
							}
						}
					} else {
						ServerThread st = hm.get(targetId);
						st.sendTarget(senddata);
					}
				}
			}
		}
	}

	public void sendTarget2(SendData senddata, String id) {
		ObjectOutputStream oos = hm.get(id).getOos();
		try {
			oos.writeObject(senddata);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendUsersVector(SendData senddata, Vector<String> vec) { // 박상욱
		if (vec.size() > 0) {
			Iterator<String> itr = vec.iterator();
			while (itr.hasNext()) {
				ServerThread st = hm.get(itr.next());
				st.sendTarget(senddata);
			}
		}
	}

	public int Login_Check(User user) {
		String str = user.getUid();
		if (user_Map.containsKey(str)) {
			User some = user_Map.get(str);
			if (!ban_User_List.contains(str)) {
				if (!onLine_User_List.containsKey(str)) {
					String pw = some.getUpw();
					if (user.getUpw().equals(pw)) {
						return LOGIN_OK;
					} else {
						return LOGIN_PWMISMATCH;
					}
				} else {
					return LOGIN_ALREADY_LOGIN;
				}
			} else {
				return LOGIN_IDBAN;
			}
		} else {
			return LOGIN_IDMISMATCH;
		}
	}

	public synchronized void updateWaitingUser(String id, int i) {
		sendUsers(new SendData(WATTING_USER_UPDATE, user_Map.get(id)
				.toUser_Info(), i), onLine_User_List);
	}

	public synchronized void updateChatRoom(Chat_Room room, int i) {
		synchronized (Chat_Room_Key) {

			sendUsers(new SendData(CHAT_ROOM_LIST_UPDATE, room, i),
					waitting_User_List);
		}
	}

	public synchronized void remove_Chat_Room(int roomNum) {
		synchronized (Chat_Room_Key) {

			updateChatRoom(chat_Room_List.get(roomNum), CHAT_ROOM_ADD_REMOVE);
			chat_Room_List.remove(roomNum);
			sut.upDateGui_Room();// 민지영수정 0625 리무브 쳇룸..
		}
	}

	public synchronized void removeWaiting(String id) {
		waitting_User_List.remove(id);
		updateWaitingUser(id, WAITING);
		sut.upDateGui_User();
	}

	public synchronized void addWaiting(String id) {
		user_Map.get(id).setuRoomInNum(-1);
		waitting_User_List.put(id, user_Map.get(id).toUser_Info());
		updateWaitingUser(id, WAITING);
		sut.upDateGui_User();
	}

	public synchronized void add_Room_User(int roomNum, String id) {
		synchronized (Chat_Room_Key) {

			removeWaiting(id);
			user_Map.get(id).setuRoomInNum(roomNum);
			Chat_Room cr = chat_Room_List.get(roomNum);
			sendUsers(new SendData(CHAT_ROOM_USER_UPDATE, user_Map.get(id)
					.toUser_Info()), cr.getRoom_User_List());
//			cr.addUser(id, user_Map.get(id).toUser_Info());//꿍
			cr.add_or_removeUser(id, user_Map.get(id).toUser_Info(), 1);
			sut.upDateGui_Room();// 주석해제함.
			sut.upDateGui_User();
		}
	}

	public synchronized void remove_Room_User(int roomNum, String id) {
		Chat_Room cr = chat_Room_List.get(roomNum);
		synchronized (Chat_Room_Key) {
			cr.add_or_removeUser(id, user_Map.get(id).toUser_Info(), 0);
		}
		SendData send = null;
		if (cr.getUser_Count() == 0) {
			remove_Chat_Room(roomNum);
		} else if (cr.getKing().equals(id)) {
			cr.setNewKing();
			send = new SendData(CAPTAIN_TOSS_OK, cr.getKing());
			sendUsers(send, cr.getRoom_User_List());
		} else {
			send = new SendData(BROADCAST_MSG_OK, id + "님이 나갔습니다.");
			sendUsers(send, cr.getRoom_User_List());
		}
		sendUsers(new SendData(CHAT_ROOM_USER_UPDATE, user_Map.get(id)
				.toUser_Info()), cr.getRoom_User_List());
		updateChatRoom(cr, CHAT_ROOM_CHANGE);
		user_Map.get(id).setuRoomInNum(-1);
		sut.upDateGui_User();
		sut.upDateGui_Room();
	}

	// 리무브 룸유저 오버로딩 정재훈
	public synchronized void remove_Room_User(int roomNum, String id, String msg) {
		Chat_Room cr = chat_Room_List.get(roomNum);
		synchronized (Chat_Room_Key) {
			cr.add_or_removeUser(id, user_Map.get(id).toUser_Info(), 0);
		}
		SendData send = null;
		if (cr.getUser_Count() == 0) {
			remove_Chat_Room(roomNum);
		} else if (cr.getKing().equals(id)) {
			cr.setNewKing();
			send = new SendData(BROADCAST_MSG_OK, id + "(방장)님이 나갔습니다 새로운 방장 : "
					+ cr.getKing());
			sendUsers(send, cr.getRoom_User_List());
			// 0626 정재훈 수정
			// ======================================================================
			// send = new SendData(CAPTAIN_TOSS_OK,id,cr.getKing()); //기존 방장이
			// 그냥나갔을때 보낼 SendData
			// 나갓을때 위임되는 방장 id와 king의 정보 //////
			// =======================================================================================
		} else {
			send = new SendData(BROADCAST_MSG_OK, id + msg);
			sendUsers(send, cr.getRoom_User_List());
		}
		sendUsers(new SendData(CHAT_ROOM_USER_UPDATE, user_Map.get(id)
				.toUser_Info()), cr.getRoom_User_List());
		user_Map.get(id).setuRoomInNum(-1);
		updateChatRoom(cr, CHAT_ROOM_CHANGE);

		sut.upDateGui_User();
		sut.upDateGui_Room();
	}

	public boolean Join(User user) {
		String id = user.getUid();
		synchronized (User_Info_Key) {
			if (!user_Map.containsKey(id)) {
				user_Map.put(id, user);
				offLine_User_List.put(id, user_Map.get(id).toUser_Info());
				return true;
			}
			return false;
		}
	}

	public synchronized void withDrawUser(String id) {
		removeWaiting(id);
		user_Map.remove(id);
		hm.get(id).sendTarget(new SendData(WITHDRAW_OK));
		removeHm(id);
		sut.upDateGui_User();
	}

	public HashMap<String, ServerThread> getHm() {
		return hm;
	}

	public synchronized void putHm(String id, ServerThread st) {
		hm.put(id, st);
		offLine_User_List.remove(id);
		user_Map.get(id).setuRoomInNum(-1);
		user_Map.get(id).setOnline();
		onLine_User_List.put(id, user_Map.get(id).toUser_Info());
		waitting_User_List.put(id, user_Map.get(id).toUser_Info());
		sut.upDateGui_User();

	}

	public synchronized void setNotice(SendData data) {
		SendData some = data;
		String notice_Msg = (String) some.getObj1();
		String notice_Title = (String) some.getObj2();
		int roomNum = (int) some.getObj3();
		Chat_Room room = chat_Room_List.get(roomNum);

		room.setNotice_Msg(notice_Msg);
		room.setNotice_Title(notice_Title);
		SendData send = new SendData(SET_NOTICE_OK, room.getNotice_Msg(),
				room.getNotice_Title());
		synchronized (room) {

			sendUsers(send, chat_Room_List.get(roomNum).getRoom_User_List());
		}
	}

	public synchronized void LogoutHm(String id) {
		hm.remove(id);
		User_Info uInfo = user_Map.get(id).toUser_Info();
		if (onLine_User_List.containsKey(id)) {
			onLine_User_List.remove(id);
		}
		if (waitting_User_List.containsKey(id)) {
			waitting_User_List.remove(id);
		}

		offLine_User_List.put(id, uInfo);
	}

	public  void removeHm(String id) {
		hm.remove(id);
		user_Map.get(id).setOnline();
		User_Info uInfo = user_Map.get(id).toUser_Info();
		onLine_User_List.remove(id);
		if (waitting_User_List.containsKey(id)) {
			removeWaiting(id);
		} else {
			int idx = find_Chat_Room_Number_User(id);
			Chat_Room target = chat_Room_List.get(idx);
			remove_Room_User(idx, id);
		}
		updateWaitingUser(id, ONLINE);
		offLine_User_List.put(id, uInfo);
		sut.upDateGui_User();
	}

	public Object getUser_Info_Key() {
		return User_Info_Key;
	}

	public  void setUser_Info_Key(Object user_Info_Key) {
		User_Info_Key = user_Info_Key;
	}

	public Object getChat_Room_Key() {
		return Chat_Room_Key;
	}

	public  void setChat_Room_Key(Object chat_Room_Key) {
		Chat_Room_Key = chat_Room_Key;
	}

	public ServerData() {
		onLine_User_List = new HashMap<String, User_Info>();
		offLine_User_List = new HashMap<String, User_Info>();
		waitting_User_List = new HashMap<String, User_Info>();
		hm = new HashMap<String, ServerThread>();
		ban_User_List = new Vector<String>();
		banCount = new HashMap<String, Integer>();
		Chat_Room_Key = new Object();
		chat_Room_List = new TreeMap<Integer, Chat_Room>();
		NoteBox_Map = new HashMap<String, NoteBox>();
		User_Info_Key = new Object();
		user_Map = new HashMap<String, User>();
		filter = new Vector<String>();
		send_User_Key = new Object();

		LoadServerData();

	}

	public  void LoadServerData() {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("a.data");
			ois = new ObjectInputStream(fis);

			ServerData loadData = (ServerData) ois.readObject();
			user_Map = loadData.getUser_Map();
			ban_User_List = loadData.getBan_User_List();
			NoteBox_Map = loadData.getNoteBox_Map();
			banCount = loadData.getBanCount();
			chat_Room_List = loadData.getChat_Room_List();
			offLine_User_List = loadData.getOffLine_User_List();
			filter = loadData.getFilter();

		} catch (Exception e) {
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}

	}

	public Vector<String> getBan_User_List() {
		return ban_User_List;
	}

	public synchronized void setBan_User_List(Vector<String> ban_User_List) {
		this.ban_User_List = ban_User_List;
	}

	public HashMap<String, User> getUser_Map() {
		return user_Map;
	}

	public synchronized void setUser_Map(HashMap<String, User> user_Map) {
		this.user_Map = user_Map;
	}

	public synchronized void SaveServerData() {

		Iterator<String> itr = hm.keySet().iterator();
		while (itr.hasNext()) {
			removeHm(itr.next());
		}
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("a.data");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.reset();

		} catch (Exception e) {

		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}

	}

	public HashMap<String, User_Info> getOnLine_User_List() {
		return onLine_User_List;
	}

	public  void setOnLine_User_List(HashMap<String, User_Info> onLine_User_List) {
		this.onLine_User_List = onLine_User_List;
	}

	public HashMap<String, User_Info> getOffLine_User_List() {
		return offLine_User_List;
	}

	public  void setOffLine_User_List(
			HashMap<String, User_Info> offLine_User_List) {
		this.offLine_User_List = offLine_User_List;
	}

	public HashMap<String, User_Info> getWaitting_User_List() {
		return waitting_User_List;
	}

	public  void setWaitting_User_List(
			HashMap<String, User_Info> waitting_User_List) {
		this.waitting_User_List = waitting_User_List;
	}

	public TreeMap<Integer, Chat_Room> getChat_Room_List() {
		return chat_Room_List;
	}

	public  void setChat_Room_List(TreeMap<Integer, Chat_Room> chat_Room_List) {
		this.chat_Room_List = chat_Room_List;
	}

	public HashMap<String, NoteBox> getNoteBox_Map() {
		return NoteBox_Map;
	}

	public  void setNoteBox_Map(HashMap<String, NoteBox> noteBox_Map) {
		NoteBox_Map = noteBox_Map;
	}

	public HashMap<String, Integer> getBanCount() {
		return banCount;
	}

	public  void setBanCount(HashMap<String, Integer> banCount) {
		this.banCount = banCount;
	}

	public  void setHm(HashMap<String, ServerThread> hm) {
		this.hm = hm;
	}

	public synchronized SendData okNoteOpen(String id) {
		User user = user_Map.get(id);
		return new SendData(OPEN_NOTELIST_OK, user.getSendNote(),user.getReceiveNote());
	}

	public synchronized void putChat_Room_List(Chat_Room room) {
		Iterator<Integer> itr = chat_Room_List.keySet().iterator();
		int i = 1;
		if (!itr.hasNext()) {
		} else {
			while (itr.hasNext()) {
				int num = (int) itr.next();
				if (!(num == i)) {
				} else {
					i++;
				}
			}
		}
		String id = room.getKing();
		room.setRoomNumber(i);
		room.setTime(System.currentTimeMillis());
		user_Map.get(room.getKing()).setuRoomInNum(i);
		room.addUser(id, user_Map.get(id).toUser_Info());
		chat_Room_List.put(i, room);
		hm.get(room.getKing()).sendTarget(
				new SendData(SETUP_CHAT_ROOM__OK, room));
		sendUsers(new SendData(CHAT_ROOM_LIST_UPDATE, room,
				CHAT_ROOM_ADD_REMOVE), waitting_User_List);
		sut.upDateGui_User();
		sut.upDateGui_Room();
	}
}
