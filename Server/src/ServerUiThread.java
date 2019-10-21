
public class ServerUiThread extends Thread{
	private ServerData sd = null;
	private ServerManager_Form gui = null;
	
	
	public ServerUiThread(ServerData sd){
		this.sd = sd;
		sd.setSut(this);
	}

	
	public void upDateGui_Room() {
		gui.updateRoomList();
	}
	public void upDateGui_User() {
		gui.updateUserList();
	}
	public void upDateGui_Slang() {
		gui.updateSlang();
	}
	public void upDateGui_Ban() {
		gui.updateBanUser();
	}
	
	@Override
	public void run() {
		gui = new ServerManager_Form(sd);
	}
}
