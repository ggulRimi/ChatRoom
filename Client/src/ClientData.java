import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ClientData implements iProtocol{
	private Vector<HashMap<String,User_Info>> Total_User_List;
	private HashMap<String,User_Info> onLine_User_List;
	private HashMap<String,User_Info> offLine_User_List;
	private HashMap<String,User_Info> waitting_User_List;
	private TreeMap<Integer,Chat_Room> chat_Room_List;
	private String myID;
	private Color myColor =null;  //박상욱 0626 수정
	private Color otherColor=null; //박상욱 0626 수정
	private Font font = new Font("HY나무B", Font.PLAIN, 15);// //박상욱 0626 수정
	private HashMap<String,User_Info> friend_User_List;
	private Vector<String> black_User_List;
	private Vector<User_Info> invite_Friend_List;
	private TreeMap<String,Integer>choice_map=null;
	

	private transient Client_Gui_Thread gui = null;
	
	private VoteGUI voteGUI= null;
	private VoteGUI voteGUI2 = null;

	
	public void addInvite_Friend_List(User_Info user){
		if(!(invite_Friend_List.contains(user))){
			invite_Friend_List.add(user);
			gui.getWaiting_Gui().getFriend_Invite_List_GUI().update_Friend_invite(invite_Friend_List);
			
		}
	}
	public void removeInvite_Friend_List(User_Info user) {
		invite_Friend_List.remove(user);
		gui.getWaiting_Gui().getFriend_Invite_List_GUI().update_Friend_invite(invite_Friend_List);
	}
	
	
	public void addBlack_User_List(String str) {
		black_User_List.add(str);
		gui.getWaiting_Gui().updateUserLbl_Black_List(black_User_List);
	}
	public void removeBlack_User_List(String str) {
		black_User_List.remove(str);
		gui.getWaiting_Gui().updateUserLbl_Black_List(black_User_List);
	}
	
	public void addFriend_User_List(User_Info user) {
		friend_User_List.put(user.getId(),user);
		checkWatingBtn();  //박상욱 0702 friend 갱신 해결 
	}
	public HashMap<String, User_Info> getFriend_User_List() {
		return friend_User_List;
	}
	public void setFriend_User_List(HashMap<String, User_Info> friend_User_List) {
		this.friend_User_List = friend_User_List;
	}
	public Vector<String> getBlack_User_List() {
		return black_User_List;
	}
	public void setBlack_User_List(Vector<String> black_User_List) {
		this.black_User_List = black_User_List;
	}
	public void removeFriend_User_List(String str) {
		friend_User_List.remove(str);
		checkWatingBtn();  //박상욱 0702 friend 갱신 해결 
	}
	
	private HashMap<String, User> user;
	
	public HashMap<String, User> getUser() {
		return user;
	}

	public void setUser(HashMap<String, User> user) {
		this.user = user;
	}
	
	//################################바꾼것#######################################
	private ClientThread ct = null;
	//################################바꾼것#######################################
	public ClientData() {
		super();
		Total_User_List = new Vector<HashMap<String,User_Info>>();
		onLine_User_List = new HashMap<String, User_Info>();
		offLine_User_List= new HashMap<String, User_Info>();
		waitting_User_List = new HashMap<String, User_Info>();
		chat_Room_List = new TreeMap<Integer, Chat_Room>();
		myColor = Color.BLACK; //박상욱 0626 수정
		otherColor = Color.BLACK; //박상욱 0626 수정
		black_User_List = new Vector<String>();
		friend_User_List = new HashMap<String, User_Info>();
		invite_Friend_List = new Vector<User_Info>();
		choice_map = new TreeMap<String, Integer>();
		gui = new Client_Gui_Thread(this);
		gui.start();
	}

	
	public Vector<User_Info> getInvite_Friend_List() {
		return invite_Friend_List;
	}
	public void setInvite_Friend_List(Vector<User_Info> invite_Friend_List) {
		this.invite_Friend_List = invite_Friend_List;
	}
	public void updateAllUserLbls(){
		gui.getWaiting_Gui().updateUserLbl_Total_List(onLine_User_List);
	}
	public void updateChatRoomUser(User_Info user) { //박상욱 0626 수정
		Chat_Room room = gui.getChat_Room_Gui().getRoom();
		HashMap<String,User_Info> map = room.getRoom_User_List();
		if(map.containsKey(user.getId())){
			map.remove(user.getId());
			add_remove_WaitingUser(user);
			
		} else{
			map.put(user.getId(), user);
		}
		gui.getChat_Room_Gui().updateUser();
	}
	public void updateWaitingUserLbl() {
		System.out.println("테스트중");
		gui.getWaiting_Gui().updateUserLbl_Waititing_List(waitting_User_List);
		System.out.println("테스트중1");
	}
	public void updateFriendUserLbl() {
		gui.getWaiting_Gui().updateUserLbl_Friend_List(friend_User_List);
	}
	public void changeKing(String user){//0626박상욱  수정
		Chat_Room room = gui.getChat_Room_Gui().getRoom();
		room.setKing(user);
		gui.getChat_Room_Gui().updateUser();
	}
	public void sendTarget(SendData send) {
		try{
			ct.sendTarget(send);
		}catch(Exception e){}
	}
	
	public void add_remove_Chat_Room_List(Chat_Room room) {
		int idx = room.getRoomNumber();
		if(chat_Room_List.containsKey(idx)){
			chat_Room_List.remove(idx);
		} else{
			chat_Room_List.put(idx,room);
		}
		updateWaitRoomChatList();
	}
	
	
	public void add_remove_WaitingUser(User_Info user){
		if(waitting_User_List.containsKey(user.getId())) {
			System.out.println(user.getId()+"를리스트에제거합니다");
			waitting_User_List.remove(user.getId());
		} else{
			System.out.println(user.getId()+"를리스트에추가합니다");
			waitting_User_List.put(user.getId(), user);
		}
	}
	public void add_remove_OnlineUser(User_Info user) {
		if(onLine_User_List.containsKey(user.getId())) {
			System.out.println(user.getId()+"를리스트에제거합니다");
			onLine_User_List.remove(user.getId());
		} else{
			System.out.println(user.getId()+"를리스트에추가합니다");
			onLine_User_List.put(user.getId(), user);
		}
	}
	public void checkWatingBtn() {
		if(gui.getWaiting_Gui().checkIsSearchUser()){
//			JOptionPane.showMessageDialog(null, waiting_Gui.getTbp().getSelectedIndex()==1);
//			if(waiting_Gui.getTbp().getSelectedIndex()==0){
//			if(waiting_Gui.getBtn_Change_User_List().getText().equals("대기유저")){
			updateFriendUserLbl();
				updateWaitingUserLbl();
//			} else{
				updateAllUserLbls();
//			}
		}
	}
	
	
	
	//################################바꾼것#######################################
	public void setCt(ClientThread ct) {
		this.ct = ct;
	}
	public ClientThread getCt(){
		return ct;
	}
	//################################바꾼것#######################################
	
	public void updateWaitRoomChatList() {
		if(gui.getWaiting_Gui().checkIsSearchRoom()){
			gui.getWaiting_Gui().updateRoomPnlList(chat_Room_List);
		}
	}
	
	
	
	public void change_Font(){//박상욱 수정 0625 채팅방  폰트 바꾸는 메서드
		gui.getWaiting_Gui().settingFont(myColor, otherColor,font);
	}
	

	public Vector<HashMap<String, User_Info>> getTotal_User_List() {
		return Total_User_List;
	}

	public void setTotal_User_List(
			Vector<HashMap<String, User_Info>> total_User_List) {
		Total_User_List = total_User_List;
	}

	public HashMap<String, User_Info> getOnLine_User_List() {
		return onLine_User_List;
	}

	public void setOnLine_User_List(HashMap<String, User_Info> onLine_User_List) {
		this.onLine_User_List = onLine_User_List;
	}

	public HashMap<String, User_Info> getOffLine_User_List() {
		return offLine_User_List;
	}

	public void setOffLine_User_List(HashMap<String, User_Info> offLine_User_List) {
		this.offLine_User_List = offLine_User_List;
	}

	public HashMap<String, User_Info> getWaitting_User_List() {
		return waitting_User_List;
	}

	public void setWaitting_User_List(HashMap<String, User_Info> waitting_User_List) {
		this.waitting_User_List = waitting_User_List;
	}

	public String getMyID() {
		return myID;
	}

	public void setMyID(String myID) {
		this.myID = myID;
	}

	public Color getMyColor() {
		return myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

	public Color getOtherColor() {
		return otherColor;
	}

	public Client_Gui_Thread getGui() {
		return gui;
	}
	public void setGui(Client_Gui_Thread gui) {
		this.gui = gui;
	}
	public TreeMap<Integer, Chat_Room> getChat_Room_List() {
		return chat_Room_List;
	}
	public void setChat_Room_List(TreeMap<Integer, Chat_Room> chat_Room_List) {
		this.chat_Room_List = chat_Room_List;
	}
	public void setOtherColor(Color otherColor) {
		this.otherColor = otherColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	public TreeMap<String, Integer> getChoice_map() {
		return choice_map;
	}

	public void setChoice_map(TreeMap<String, Integer> choice_map) {
		this.choice_map = choice_map;
	}
	
	public void showVoteGUI(Vector<String>voteList,int roomNum,String voteTitle){
		voteGUI =new VoteGUI(this,voteList,roomNum,voteTitle);
	}
	public void showVoteGUI2(int roomNum){
		voteGUI2 =new VoteGUI(this,roomNum);
	}
	public VoteGUI getVoteGUI() {
		return voteGUI;
	}
	public void setVoteGUI(VoteGUI voteGUI) {
		this.voteGUI = voteGUI;
	}
	public VoteGUI getVoteGUI2() {
		return voteGUI2;
	}
	public void setVoteGUI2(VoteGUI voteGUI2) {
		this.voteGUI2 = voteGUI2;
	}
	
	
}
