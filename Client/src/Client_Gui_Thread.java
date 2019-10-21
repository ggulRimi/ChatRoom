import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFrame;


public class Client_Gui_Thread extends Thread {
	private LoginForm gui_Login = null;
	private Waitting_Room_GUI waiting_Gui = null;
	private ChatRoom_GUI chat_Room_Gui = null;
	private User_Info_Modified_GUI info_modified_Gui = null;
	private User_Info_GUI info_Gui = null;
	private ChoiceUser_GUI invite_Gui = null; //박상욱 0626 수정
	private NoteBox_GUI note_Box_Gui = null;
	private ChoiceUser_GUI whisper_Gui = null;//명환 수정
	private ChoiceUser_GUI noteSend_Gui=null;
	private Setting_Font_GUI font_Gui =null; //박상욱 0626 수정
	private VoteGUI voteGUI= null;
	private VoteGUI voteGUI2 = null;
	private ClientData cd = null;
	
	
	public   void setNoticeOk(String a1 , String a2){
		getChat_Room_Gui().getTaNotice().setText("");
		getChat_Room_Gui().getTaNotice().invalidate();
		getChat_Room_Gui().getTaNotice().repaint();
		getChat_Room_Gui().getTaNotice().updateUI();
		getChat_Room_Gui().getTaNotice().append("공지 제목 : " + a1 + "\n\n"+"공지 내용 : \n" + a2 + "\n");// 내창에
		getChat_Room_Gui().getTaNotice().invalidate();
		getChat_Room_Gui().getTaNotice().repaint();
		getChat_Room_Gui().getTaNotice().updateUI();
	}
	
	
	
	public Client_Gui_Thread(ClientData cd){
		this.cd = cd;
	}
	
	
	
	public   void showVoteGUI(Vector<String>voteList,int roomNum,String voteTitle){
		voteGUI =new VoteGUI(cd,voteList,roomNum,voteTitle);
	}
	
	public   void showVoteGUI2(int roomNum){
		voteGUI2 =new VoteGUI(cd,roomNum);
	}
	
	
	
	public   void showWhisper(HashMap<String, User_Info> online){//명환 수정
		whisper_Gui = new ChoiceUser_GUI(online, cd);
	}
	public   void showInvite(HashMap<String, User_Info> online,int roomNum){//박상욱 수정 0625 초대
		invite_Gui = new ChoiceUser_GUI(online,cd,roomNum);
	}
	public   void showLogin() {
		gui_Login = new LoginForm(cd);
	}
	public   void showWating() {
		waiting_Gui = new Waitting_Room_GUI(cd);
	}
	public   void show_Chat_Gui(Chat_Room room){
		chat_Room_Gui = new ChatRoom_GUI(room,cd);
		chat_Room_Gui.settingFont(cd.getMyColor(), cd.getOtherColor(),cd.getFont()); //박상욱수정 폰트 
	}
	public   void showUserInfo_Modified_Gui(User_Info info){
		info_modified_Gui = new User_Info_Modified_GUI(info,cd);
	}
	public   void showUserInfo_Gui(User_Info info) { //박상욱 수정 0624
		info_Gui = new User_Info_GUI(info);
	}
	public   void showSettingFont_Gui(){//박상욱 수정 0625 폰트
		font_Gui = new Setting_Font_GUI(cd);
	}
	public   void show_NoteBox_Gui(TreeMap<Long, Note> post,
			TreeMap<Long, Note> receive) {
		note_Box_Gui = new NoteBox_GUI(post, receive, cd);
	}
	public   void showSendNote(HashMap<String, User_Info> online){//명환 수정
		noteSend_Gui = new ChoiceUser_GUI(cd,online);
	}
	
	
	
	public JFrame checkOnGUI() {
		if(gui_Login.isShowing()){
			return gui_Login;
		} else if(waiting_Gui.isShowing()){
			return waiting_Gui;
		} else if(chat_Room_Gui.isShowing()) {
			return waiting_Gui;
		}
		return null;
	}
	
	public   void allGuiClose() {
		if(gui_Login.isShowing()){
			gui_Login.dispose();
		} else if(waiting_Gui.isShowing()){
			waiting_Gui.dispose();
		} else if(chat_Room_Gui.isShowing()) {
			chat_Room_Gui.dispose();
		}
	}
	
	
	
	public LoginForm getGui_Login() {
		return gui_Login;
	}



	public   void setGui_Login(LoginForm gui_Login) {
		this.gui_Login = gui_Login;
	}



	public Waitting_Room_GUI getWaiting_Gui() {
		return waiting_Gui;
	}



	public   void setWaiting_Gui(Waitting_Room_GUI waiting_Gui) {
		this.waiting_Gui = waiting_Gui;
	}



	public ChatRoom_GUI getChat_Room_Gui() {
		return chat_Room_Gui;
	}



	public   void setChat_Room_Gui(ChatRoom_GUI chat_Room_Gui) {
		this.chat_Room_Gui = chat_Room_Gui;
	}



	public User_Info_Modified_GUI getInfo_modified_Gui() {
		return info_modified_Gui;
	}



	public   void setInfo_modified_Gui(User_Info_Modified_GUI info_modified_Gui) {
		this.info_modified_Gui = info_modified_Gui;
	}



	public User_Info_GUI getInfo_Gui() {
		return info_Gui;
	}



	public   void setInfo_Gui(User_Info_GUI info_Gui) {
		this.info_Gui = info_Gui;
	}



	public ChoiceUser_GUI getInvite_Gui() {
		return invite_Gui;
	}



	public   void setInvite_Gui(ChoiceUser_GUI invite_Gui) {
		this.invite_Gui = invite_Gui;
	}



	public NoteBox_GUI getNote_Box_Gui() {
		return note_Box_Gui;
	}



	public   void setNote_Box_Gui(NoteBox_GUI note_Box_Gui) {
		this.note_Box_Gui = note_Box_Gui;
	}



	public ChoiceUser_GUI getWhisper_Gui() {
		return whisper_Gui;
	}



	public   void setWhisper_Gui(ChoiceUser_GUI whisper_Gui) {
		this.whisper_Gui = whisper_Gui;
	}



	public ChoiceUser_GUI getNoteSend_Gui() {
		return noteSend_Gui;
	}



	public   void setNoteSend_Gui(ChoiceUser_GUI noteSend_Gui) {
		this.noteSend_Gui = noteSend_Gui;
	}



	public Setting_Font_GUI getFont_Gui() {
		return font_Gui;
	}



	public   void setFont_Gui(Setting_Font_GUI font_Gui) {
		this.font_Gui = font_Gui;
	}



	public VoteGUI getVoteGUI() {
		return voteGUI;
	}

	

	public   void setVoteGUI(VoteGUI voteGUI) {
		this.voteGUI = voteGUI;
	}

	public   void setVoteGUI2(VoteGUI voteGUI2) {
		this.voteGUI2 = voteGUI2;
	}



	public VoteGUI getVoteGUI2() {
		return voteGUI2;
	}

	public ClientData getCd() {
		return cd;
	}



	public   void setCd(ClientData cd) {
		this.cd = cd;
	}



	@Override
	public   void run(){
		
	}
	

}
