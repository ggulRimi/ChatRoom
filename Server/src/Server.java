import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	public static void main(String[] args){
		ServerSocket ss = null;
		Socket sk = null;
		ServerData sd = new ServerData();
		ServerUiThread sut = new ServerUiThread(sd);
		sut.start();
		try {
			ss = new ServerSocket(5555);
			while((sk = ss.accept()) != null) {
					ServerThread st = new ServerThread(sd,sk);
					st.start();
			}
		} catch (IOException e) {
			sd.SaveServerData();
		} finally{
			sd.SaveServerData();
			try{
				ss.close();
			}catch(Exception e){
			sd.SaveServerData();
			}
		}
	}

}