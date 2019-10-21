import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UserPanel extends JPanel implements iProtocol {
	private ServerData sd;
	private User user;
	private JPopupMenu pM;
	private JMenuItem miBan;
	private JMenuItem miSendMsg;
	private JMenuItem miUnBan;
	private String id;

	private String userid;
	private String usernick;
	private String ucondition;
	
	private TitledBorder tborder;
	

	public UserPanel(ServerData sd, User user) {
		setLayout(new FlowLayout());
		this.sd = sd;
		this.user = user;
		id = user.getUid();
		init();
		pop();
		addListeners();
	}
	
	public void setSlang() {
		JLabel slang = new JLabel(user.toSlangString());
	}
	
	private void pop(){
		pM = new JPopupMenu();
		miBan = new JMenuItem("계정정지");
		miSendMsg = new JMenuItem("메세지보내기");
		miUnBan = new JMenuItem("계정정지 풀기");
		pM.add(miSendMsg);
		pM.add(miBan);
		pM.add(miUnBan);
	}

	public void init() {
		setPreferredSize(new Dimension(420,50));
		userid = user.getUid();
		usernick = user.getUNickName();
		ucondition = user.toNowUserString();
		
		JLabel name = new JLabel(userid);
		JLabel nick = new JLabel(usernick);
		JLabel condition = new JLabel(ucondition);
		
		name.setPreferredSize(new Dimension(100,50));
		nick.setPreferredSize(new Dimension(100,50));
		condition.setPreferredSize(new Dimension(150,50));
		
		add(name);
		add(nick);
		add(condition);

		tborder = new TitledBorder(new LineBorder(Color.BLACK, 1),"");
		setBorder(tborder);
	}

	private void addListeners() {
		MouseAdapter mAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger()) {
					System.out.println(123123);
					pM.show(UserPanel.this, me.getX(), me.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger()) {
					pM.show(UserPanel.this, me.getX(), me.getY());
				}
			}
		};
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();

				if (src == miBan) {
					if (!sd.getBan_User_List().contains(id)) {
						sd.BanUser(id);
					}
				} else if (src == miUnBan) {
					if (sd.getBan_User_List().contains(id)) {
						sd.unBanUser(id);
					}
				} else if (src == miSendMsg) {
					String msg = JOptionPane.showInputDialog("보낼 내용을 입력하시오");
					sd.getHm().get(id)
							.sendTarget(new SendData(SEND_MASTER_MESSAGE, msg));
				}

			}
		};
		miBan.addActionListener(aListener);
		miUnBan.addActionListener(aListener);
		miSendMsg.addActionListener(aListener);
		addMouseListener(mAdapter);

	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsernick() {
		return usernick;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

	public String getCondition() {
		return ucondition;
	}

	public void setCondition(String condition) {
		this.ucondition = condition;
	}
}
