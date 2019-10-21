
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class Friend_Invite_List extends JFrame{
	
	private JButton btn_Close;
	private JLabel lbl_Title;
	private JLabel lbl_Id;
	private JLabel lbl_NickName;
	private JLabel lbl_Ok;
	private JLabel lbl_Cancel;
	
	private JPanel pnl_Center;
	private JPanel pnlFriend_Cover;
	private JPanel pnlFriend_List;
	//해림수정0705
	private Point ptFirst; 
	private JButton btn_Exit;
	private JLabel lblTopTitle;
	private TitledBorder titleBorder;
	private Color btnColor = new Color(0xE59BB9); //진한분홍;
	private Color bgColor = new Color(0xFFFFFF); //하얀색;
	private Color edgeColor = new Color(0xFFD9EC); // 연한분홍;
	private Font font = new Font("HY나무B", Font.PLAIN, 15);
	
	private ClientData cData;
	private final Dimension ID_NICK_SIZE = new Dimension(60,50);
	private final Dimension OK_CANCEL_SIZE = new Dimension(40,50);
	public Friend_Invite_List(ClientData cData){
		this.cData = cData;
		init();
		setDisplay();
		addListeners();
		showDlg();
		update_Friend_invite(cData.getInvite_Friend_List());
	}
	public Friend_Invite_List(){
		init();
		setDisplay();
		addListeners();
		showDlg();
	}
	
	private void init(){


		
		//해림수정 0705
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 30));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
		btn_Exit.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		
		lblTopTitle = new JLabel("친구요청목록", JLabel.LEFT);
		lblTopTitle.setFont(font);
		
		btn_Close =  new JButton("닫기");
		btn_Close.setPreferredSize(new Dimension(50, 30));
		btn_Close.setForeground(Color.WHITE);
		btn_Close.setBackground(btnColor);
		btn_Close.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		
		pnlFriend_Cover = new JPanel(new BorderLayout());
		pnlFriend_Cover.setFont(font);
		
		pnlFriend_List = new JPanel(new GridLayout(0,1));
	}
	private void setDisplay(){
		//north
		JPanel pnl_North = new JPanel(new BorderLayout());
		//north TopTitle
		JPanel pnl_NorthTopTitle = new JPanel(new BorderLayout());
		pnl_NorthTopTitle.add(btn_Exit, BorderLayout.EAST); //해림수정0702
		pnl_NorthTopTitle.setBackground(edgeColor);//해림수정0702
		pnl_NorthTopTitle.add(lblTopTitle, BorderLayout.WEST);

		
		pnl_North.add(pnl_NorthTopTitle, BorderLayout.NORTH);
		//center
		pnl_Center = new JPanel(); //패널 추가될 자리

		JScrollPane scroll_Friend = setting_JScroll(pnlFriend_Cover,300,500); //스크롤 세팅
		pnlFriend_Cover.add(pnlFriend_List, BorderLayout.NORTH);
		pnlFriend_Cover.setBackground(bgColor);
		
		scroll_Friend.setBorder(titleBorder = 
				new TitledBorder(new LineBorder(btnColor,2),"  친구요청목록"));
		titleBorder.setTitleFont(font);
		scroll_Friend.setBackground(bgColor);
		
		pnl_Center.add(scroll_Friend);
		pnl_Center.setBackground(bgColor);
		//south
		JPanel pnl_South = new JPanel();//닫기 버튼 추가
		pnl_South.add(btn_Close);
		pnl_South.setBackground(bgColor);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		
		pnlTotal.add(pnl_North,BorderLayout.NORTH);
		pnlTotal.add(pnl_Center,BorderLayout.CENTER);
		pnlTotal.add(pnl_South,BorderLayout.SOUTH);
		
		pnlTotal.setBorder(new LineBorder(edgeColor,6));
		add(pnlTotal, BorderLayout.CENTER);
		
	}
	private void addListeners(){
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if(btn_Close == src || src == btn_Exit ){
					dispose();
				}
			}
		};
		btn_Close.addActionListener(listener);	
		btn_Exit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
		
	}
	
	//업데이트
	public void update_Friend_invite(Vector<User_Info> userList){
		updatePnl(setting_Friend_InvitePnl(userList));
		pnl_Center.updateUI();
	}

	public JPanel setting_Friend_InvitePnl(Vector<User_Info> list){
		pnlFriend_List.removeAll();
		int size = cData.getInvite_Friend_List().size();
		for (int i = 0; i<size ; i++) { //고쳐야함
			User_Info userInfo = cData.getInvite_Friend_List().get(i);
			String nickName = userInfo.getNickName();
			String userId = userInfo.getId();
			Pnl_Friend_List pnl_Friend_Invite = new Pnl_Friend_List(userInfo,cData);
			pnlFriend_List.add(pnl_Friend_Invite);

		}
		return pnlFriend_List;
	}
	public void updatePnl(JPanel pnl) {
		pnl.invalidate();
		pnl.repaint();
		pnl.updateUI();
	}

	public JScrollPane setting_JScroll(JPanel pnl,int x,int y){
		JScrollPane scrollPeople = new JScrollPane(pnl,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPeople.setPreferredSize(new Dimension(x, y));//250 605
		scrollPeople.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPeople;
	}
	
	class Pnl_Friend_List extends JPanel{
		private JLabel lbl_Id;
		private JLabel lbl_NickName;
		private JButton btn_Ok;
		private JButton btn_Cancel;
		private String id;
		private User_Info user;
		private String nickName;
		private ClientData cData;
		private final Dimension ID_NICK_SIZE = new Dimension(55,40);
		private final Dimension OK_CANCEL_SIZE = new Dimension(45,40);
		public Pnl_Friend_List(User_Info user,ClientData cData){
			this.user = user;
			this.cData = cData;
			id = user.getId();
			nickName = user.getNickName();
			init();
			setDisplay();
			addListeners();
		}
		private void init(){
			lbl_Id = new JLabel(id + "(" + nickName + ")", JLabel.CENTER);
			lbl_Id.setPreferredSize(ID_NICK_SIZE);
			lbl_Id.setFont(font);
			lbl_Id.setPreferredSize(new Dimension(100,30));
			btn_Ok = new JButton("YES");
			btn_Ok.setPreferredSize(OK_CANCEL_SIZE);
			btn_Ok.setPreferredSize(new Dimension(50, 30));
			btn_Ok.setForeground(Color.WHITE);
			btn_Ok.setBackground(btnColor);
			btn_Ok.setBorder(new LineBorder(bgColor, 1));//해림수정0705
			
			btn_Cancel = new JButton("NO");
			btn_Cancel.setPreferredSize(OK_CANCEL_SIZE);
			btn_Cancel.setPreferredSize(new Dimension(50, 30));
			btn_Cancel.setForeground(Color.WHITE);
			btn_Cancel.setBackground(btnColor);
			btn_Cancel.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		}
		private void setDisplay(){
			add(lbl_Id);
			add(btn_Ok);
			add(btn_Cancel);
			this.setBackground(edgeColor);
			this.setBorder(new LineBorder(bgColor,1));
		}
		private void addListeners(){
			ActionListener listener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent ae) {
					Object src = ae.getSource();
					if(src == btn_Ok){
						cData.sendTarget(new SendData(cData.USER_FRIEND_ADD_OK,id));
						
					}else if(src==btn_Cancel){
						cData.sendTarget(new SendData(cData.USER_FRIEND_ADD_NO,id));
					}
					cData.removeInvite_Friend_List(user);
				}
			};
			btn_Ok.addActionListener(listener);
			btn_Cancel.addActionListener(listener);
			addMouseMotionListener(new MyMouseMotionListener());
			addMouseListener(new MyMouseListener());
		}
		
	}
	
	// 해림수정 0705------------------------------------------------------------
	private class MyMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			Point loc = e.getLocationOnScreen();
			loc.x -= ptFirst.x;
			loc.y -= ptFirst.y;

			setLocation(loc);// 프레임창 움직이기
		}
	}

	private class MyMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			ptFirst = e.getPoint();
		}
	}
	//--------------------------------------------------------------------------------
	
	private void showDlg(){
		setLocationRelativeTo(null);
		setSize(330, 590);
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
