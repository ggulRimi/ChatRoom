import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;


public class Chat_Room implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private String password;
	private String king;
	private String[] category;
	private String category_Name;
	private String notice;
	private Integer user_Count;
	private Integer max_User;
	private boolean isBlock;
	private HashMap<String,User_Info> room_User_List;
	private int roomNumber;
	private transient Object key = new Object();
	
	private String notice_Title;
	private String notice_Msg;
	private long time;
	
	//메서드 equals
	//userCount();
	//checkRoom(): boolean
	//isKing(String): boolean
	//kickMember(String) :String
	//user_Invite(String) : void

	public void setNewKing() {
		Iterator<String> itr =  room_User_List.keySet().iterator();
		setKing(itr.next());
	}
	
	public String getTitle() {
		return title;
	}
	public Chat_Room(
			String title,
			String king,
			String category_Name,
			Integer max_User,
			String password,
			boolean isBlock) {
		this.title = title;
		this.password = password;
		this.king = king;
		this.category_Name = category_Name;
		this.max_User = max_User;
		this.isBlock = isBlock;
		this.user_Count =0;
		room_User_List = new HashMap<String,User_Info>();
		this.notice_Title = "";
		this.notice_Msg ="";
		time = System.currentTimeMillis();
	}
	public Chat_Room(
			String title,
			String king,
			String category_Name,
			Integer max_User,
			boolean isBlock){
		this.title = title;
		this.king = king;
		this.category_Name = category_Name;
		this.max_User = max_User;
		this.isBlock = isBlock;
		this.user_Count =0;
		room_User_List = new HashMap<String,User_Info>();
		this.notice_Title = "";
		this.notice_Msg ="";
		time = System.currentTimeMillis();
	}	
	
	public synchronized void add_or_removeUser(String id,User_Info info,int flag){
		if(flag ==1){
			addUser(id, info);
		}else{
			removeUser(id);
		}
	}
	
	public synchronized void addUser(String id, User_Info info) {
		if(!(room_User_List.containsKey(id))){
			room_User_List.put(id, info);
			user_Count++;
		}
	}
	public synchronized void removeUser(String id) {
		if(room_User_List.containsKey(id)){
			room_User_List.remove(id);
			user_Count--;
		}
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKing() {
		return king;
	}
	public void setKing(String king) {
		this.king = king;
	}
	public String[] getCategory() {
		return category;
	}
	public void setCategory(String[] category) {
		this.category = category;
	}
	public String getCategory_Name() {
		return category_Name;
	}
	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Integer getUser_Count() {
		return user_Count;
	}
	public void setUser_Count(Integer user_Count) {
		this.user_Count = user_Count;
	}
	public Integer getMax_User() {
		return max_User;
	}
	public void setMax_User(Integer max_User) {
		this.max_User = max_User;
	}
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isBlock() {
		return isBlock;
	}
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	public HashMap<String,User_Info> getRoom_User_List() {
		return room_User_List;
	}
	public void setRoom_User_List(HashMap<String,User_Info> room_User_List) {
		this.room_User_List = room_User_List;
	}

	public synchronized String getNotice_Title() {
		return notice_Title;
	}

	public synchronized void setNotice_Title(String notice_Title) {
		this.notice_Title = notice_Title;
	}

	public synchronized String getNotice_Msg() {
		return notice_Msg;
	}

	public synchronized void setNotice_Msg(String notice_Msg) {
		this.notice_Msg = notice_Msg;
	}
	
}
