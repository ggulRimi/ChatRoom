import java.io.Serializable;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Vector;


public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uid = null;
	private String upw = null;
	private String uname = null;
	private String uNickName = null;
	private String uPhone = null;
	private String uhobby = null;
	private String uIntro = null;
	private int slangCount = 0;
	private boolean isOnline = false;
	private int uRoomInNum = -1;
	private boolean isBan = false;
	private TreeMap<Long, Note> sendNote = null;
	private TreeMap<Long, Note> receiveNote = null;
	private Vector<String> blackList = null;
	private Vector<String> friendList = null;
	
	private Vector<User_Info> invite_Friend_List ;
	
	
	
	public void removeInvite_Friend_list(User_Info user) {
		if(invite_Friend_List.contains(user)){
			invite_Friend_List.remove(user);
		}
	}
	public void addInvite_Friend_List(User_Info user) {
		if(!(invite_Friend_List.contains(user))){
			invite_Friend_List.add(user);
		}
	}
	
	
	
	
	
	public Vector<User_Info> getInvite_Friend_List() {
		return invite_Friend_List;
	}
	public void setInvite_Friend_List(Vector<User_Info> invite_Friend_List) {
		this.invite_Friend_List = invite_Friend_List;
	}

	
	public boolean isBlack(String str) {
		if(blackList.contains(str)){
			return true;
		} else{
			return false;
		}
	}
	public boolean isFriend(String str) {
		if(friendList.contains(str)){
			return true;
		} else{
			return false;
		}
	}
	
	
	public void addFriend(String str) {
		if(!friendList.contains(str)){
			friendList.add(str);
		}
	}
	public void removeFriend(String str) {
		friendList.remove(str);
	}
	public void addBlakcList(String str) {
		if(!blackList.contains(str)){
			blackList.add(str);
		}
	}
	public void removeBlakcList(String str) {
		blackList.remove(str);
	}
	
	public TreeMap<Long, Note> getSendNote() {
		return sendNote;
	}

	public void setSendNote(TreeMap<Long, Note> sendNote) {
		this.sendNote = sendNote;
	}

	public TreeMap<Long, Note> getReceiveNote() {
		return receiveNote;
	}

	public void setReceiveNote(TreeMap<Long, Note> receiveNote) {
		this.receiveNote = receiveNote;
	}

	public String getUhobby() {
		return uhobby;
	}

	public void setUhobby(String uhobby) {
		this.uhobby = uhobby;
	}
	
	public boolean isBan() {
		return isBan;
	}

	public void setBan() {
		isBan = !isBan;
	}

	public int getSlangCount() {
		return slangCount;
	}

	public void setSlangCount(int slangCount) {
		this.slangCount = slangCount;
	}
	public void addSlangCount() {
		slangCount++;
	}
	public boolean isSlang() {
		if(slangCount != -1){
			return true;
		}
		return false;
	}
	
	public String toSlangString() {
		return "유저 id : "+uid+"(" +uNickName+") "+slangCount+"번";
	}
	
	
	public String toNowUserString() {

		if(isOnline){
			if(uRoomInNum != -1){
				return (uRoomInNum)+"번방";
			} else{
				return "대기실";
			}
		} else{
			return "오프라인";
		}
	}
	
	
	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline() {
		isOnline = !isOnline;
	}

	public int getuRoomInNum() {
		return uRoomInNum;
	}

	public void setuRoomInNum(int uRoomInNum) {
		this.uRoomInNum = uRoomInNum;
	}

	public String getuNickName() {
		return uNickName;
	}

	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
	}

	public String getuIntro() {
		return uIntro;
	}

	public void setuIntro(String uIntro) {
		this.uIntro = uIntro;
	}

	public User(String uid) {
		setUid(uid);
	}
	public User(String uid, String upw) {
		setUid(uid);
		setUpw(upw);
	}
	
	public User(String uid, String upw, String uname, String uNickName, String uPhone) {		
		setUid(uid);
		setUpw(upw);
		setUname(uname);
		setUNickName(uNickName);
		setuPhone(uPhone);
		isOnline = false;
		sendNote = new TreeMap<Long, Note>((Collections.reverseOrder()));
		receiveNote = new TreeMap<Long, Note>((Collections.reverseOrder()));
		blackList = new Vector<String>();
		friendList = new Vector<String>();
		invite_Friend_List = new Vector<User_Info>();
	}
	
	public Vector<String> getBlackList() {
		return blackList;
	}

	public void setBlackList(Vector<String> blackList) {
		this.blackList = blackList;
	}

	public Vector<String> getFriendList() {
		return friendList;
	}

	public void setFriendList(Vector<String> friendList) {
		this.friendList = friendList;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setBan(boolean isBan) {
		this.isBan = isBan;
	}

	public String getUNickName() {
		return uNickName;
	}
	public User_Info toUser_Info() {
		return new User_Info(uid,uname,uNickName,uPhone,uhobby,uIntro);
	}

	public void setUNickName(String unickName) {
		this.uNickName = unickName;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUpw() {
		return upw;
	}

	public void setUpw(String upw) {
		this.upw = upw;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

//
//	@Override
//	public String toString() {
//		String info = "<<" + uid + " 님의 회원정보 >>\n";
//		info += "- name : " + uname + "\n";
//		info += "- password : " + upw + "\n";
//		info += "- nickname : " + unick + "\n";
//		info += "- gedner : " + ugender + "\n";
//		
//		return info;
//		
//	}
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User)) {
			return false;
		}
		User user = (User)o;
		return uid.equals(user.getUid());
	}
}