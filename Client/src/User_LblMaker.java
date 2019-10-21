import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;

import org.w3c.dom.CDATASection;

public class User_LblMaker extends JLabel {
	private String NickName;
	private String KingId;
	private String id;

	private Dimension UserLblSize = new Dimension(110, 30);
	private Font lblFont = new Font("HY나무M", Font.BOLD, 15);
	private Color UserLblColor = new Color(0xE59BB9); // 진한분홍
	
	private JPopupMenu pMenu;
	private JMenuItem miWhisper;
	private JMenuItem miShowProfile;
	private JMenuItem miSendNote;
	private JMenuItem miGPS;
	private JMenuItem miFriend;
	private JMenuItem miUnFriend;
	private JMenuItem miBlack;
	private JMenuItem miUnBlack;
	private ClientData cData;
	private Font font = new Font("HY나무B", Font.PLAIN, 15);

	// 범용 박상욱 수정//////////////////////////////
	public User_LblMaker(String NickName, String id,ClientData cData) { 
		super(NickName+ id, JLabel.CENTER);
		this.cData = cData;
		this.id = id;
		init();
		setLblText(NickName, id);
		setPreferredSize(UserLblSize);
		setFont(lblFont);
		this.setForeground(Color.WHITE);
		setBorder(new LineBorder(Color.WHITE, 1));
		setOpaque(true);
		setBackground(UserLblColor);
		addActionListeners();

	}
	//박상욱 수정//////////////////////////////
	//채팅방 방장용
	public User_LblMaker(ImageIcon KingImg, String NickName,String id ) {
		super(NickName+id,KingImg,JLabel.CENTER);
		init();
		setLblText(NickName, id);
		setPreferredSize(UserLblSize);
		setFont(lblFont);
		setBorder(new LineBorder(Color.WHITE, 1));
		this.setForeground(Color.WHITE);
		setOpaque(true);
		setBackground(UserLblColor);
		addActionListeners();
	}
	// 지영수정 0704
	public User_LblMaker(String id,ClientData cData) {
		super(id,JLabel.CENTER);
		this.cData = cData;
		this.id = id;
		init();
		setText(id);
		setPreferredSize(UserLblSize);
		setFont(lblFont);
		setBorder(new LineBorder(Color.WHITE, 1));
		this.setForeground(Color.WHITE);
		setOpaque(true);
		setBackground(UserLblColor);
		addActionListeners();
		pMenu.removeAll();
		pMenu.add(miUnBlack);
		
	}
	private void showPopup(MouseEvent me){//김명환 수정 박상욱 수정 0624
		// 팝업신호가 뭔지, 우클릭이 맞는지 판정해줌
		if(me.isPopupTrigger()){
			pMenu.show(this, me.getX(),me.getY());
		}
	}
	public void addActionListeners(){ //박상욱 수정 0624
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me){
				showPopup(me);
			}
			@Override
			public void mouseReleased(MouseEvent me){
				showPopup(me);
			}
		
		});
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == miShowProfile){
					SendData send = new SendData(cData.USER_PROFILE, id);
					cData.sendTarget(send);
				}else if(src == miSendNote){//0624 박상욱 쪽지
					new Send_Note_GUI(cData,id);
				}else if(src == miWhisper){
					cData.getGui().getWaiting_Gui().getTf_Whisper_User().setText(id);
					//0627민지영==============================================================================
				}else if(src == miGPS) {
					cData.sendTarget(new SendData(cData.SEARCH_USER_LOCATION,id));
				}else if (src == miBlack) {
					cData.sendTarget(new SendData(cData.USER_BLAKC_ADD,id));
				} else if (src == miFriend) {
					if (cData.getFriend_User_List().containsKey(id)){
						showMsg( "이미 친구상태인 유저입니다.");
					}else{
						cData.sendTarget(new SendData(cData.USER_FRIEND_ADD,id));
					}
				} else if (src == miUnBlack) {
					cData.sendTarget(new SendData(cData.USER_BLAKC_REMOVE,id));
				} else if( src == miUnFriend) {
					cData.sendTarget(new SendData(cData.USER_FRIEND_REMOVE,id));
				}
				
			}
		};
		miGPS.addActionListener(listener);
		//0627민지영==============================================================================
		miShowProfile.addActionListener(listener);
		miWhisper.addActionListener(listener);
		miSendNote.addActionListener(listener);
		miBlack.addActionListener(listener);
		miFriend.addActionListener(listener);
		miUnBlack.addActionListener(listener);
		miUnFriend.addActionListener(listener);
	}
	public void setLblText(String NickName, String id) {
		this.NickName = NickName;
		this.id = id;
		setText(NickName + "(" + id + ")");
	}
	public void init(){//박상욱 수정
		pMenu = new JPopupMenu("MENU");
		pMenu.setFont(font);
		miWhisper = new JMenuItem("귓속말");
		miWhisper.setFont(font);
		miShowProfile = new JMenuItem("프로필보기");
		miShowProfile.setFont(font);
		miSendNote = new JMenuItem("쪽지전송");
		miSendNote.setFont(font);
		miGPS = new JMenuItem("위치찾기");
		miGPS.setFont(font);
		miFriend = new JMenuItem("친구추가");
		miFriend.setFont(font);
		miBlack = new JMenuItem("블랙리스트등록");
		miBlack.setFont(font);
		miUnBlack = new JMenuItem("블랙리스트해제");
		miUnBlack.setFont(font);
		miUnFriend = new JMenuItem("친구삭제");
		miUnFriend.setFont(font);
		
		
		pMenu.add(miWhisper);
		pMenu.add(miShowProfile);
		pMenu.add(miSendNote);
		pMenu.add(miGPS);
		pMenu.add(miBlack);
		pMenu.add(miUnBlack);
		pMenu.add(miFriend);
		pMenu.add(miUnFriend);

	}
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User_LblMaker)){
			return false;
		}
		User_LblMaker lbl = (User_LblMaker)o;
		return id.equals(lbl.getId());
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getKingId() {
		return KingId;
	}
	public void setKingId(String kingId) {
		KingId = kingId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}
}