


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements iProtocol {

	public static void main(String[] args){
		Socket sk = null;
		ClientThread ct = null;
		ClientData cd = null;
		try {//192.168.200.74
			sk = new Socket("127.0.0.1",5555);
			cd = new ClientData();
			ct = new ClientThread(cd,sk);
			ct.start();
			ct.join();
			}
		 catch (Exception e) {
		} finally{
			try{
				sk.close();
			}catch(Exception e){
			}
		}
	}
}