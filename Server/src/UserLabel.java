import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class UserLabel extends JLabel implements iProtocol {
	private ServerData sd;
	private User user;
	private JPopupMenu pM;
	private JMenuItem miBan;
	private JMenuItem miSendMsg;
	private JMenuItem miUnBan;
	private String id;
	
	
	public UserLabel(ServerData sd, User user){
		this.sd = sd;
		this.user = user;
		id = user.getUid();
		setText(user.toNowUserString());
		setPreferredSize(new Dimension(100,30));
		init();
		addListeners();
	}
	public void setSlang() {
		setText(user.toSlangString());
	}
	
	
	public void init() {
		pM = new JPopupMenu();
		miBan = new JMenuItem("계정정지");
		miSendMsg = new JMenuItem("메세지보내기");
		miUnBan = new JMenuItem("계정정지 풀기");
		pM.add(miSendMsg);
		pM.add(miBan);
		pM.add(miUnBan);
	}
	private void addListeners() {
		MouseAdapter mAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				if(me.isPopupTrigger()){
					System.out.println(123123);
					pM.show(UserLabel.this,me.getX(),me.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if(me.isPopupTrigger()){
					pM.show(UserLabel.this,me.getX(),me.getY());
				}
			}
		};
		ActionListener aListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				
				if(src == miBan) {
					if(!sd.getBan_User_List().contains(id)){
						sd.BanUser(id);
					}
				} else if(src == miUnBan) {
					if(sd.getBan_User_List().contains(id)){
						sd.unBanUser(id);
					}
				} else if(src == miSendMsg) {
					String msg = JOptionPane.showInputDialog("보낼 내용을 입력하시오");
					sd.getHm().get(id).sendTarget(new SendData(SEND_MASTER_MESSAGE,msg));
				}
				
				
			}
		};
		miBan.addActionListener(aListener);
		miUnBan.addActionListener(aListener);
		miSendMsg.addActionListener(aListener);
		addMouseListener(mAdapter);
		
	}

}
