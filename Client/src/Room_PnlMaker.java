
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Room_PnlMaker extends JPanel implements iProtocol {
	private String roomTitle;
	private String category;
	private Toolkit kit;
	private Image lockImg;
	private int user_Count;
	private int max_User;
	private boolean isBlock;
	private Chat_Room room;
	private ClientThread ct = null;
	
	private JLabel lblIsBlock;
	private JLabel lblTitle;
	private JLabel lblCategory;
	private ClientData cData;
	//해림 수정 0627
	private JLabel lblRoomNum;
	private Color bgColor;
	private Color btnColor;
	private Font font;
	private Color edgeColor;
	
	public Room_PnlMaker(Chat_Room room, ClientData cData) {
		this.cData = cData;
		this.room = room;
		setRoomTitle(room.getTitle());
		setCategory(room.getCategory_Name());
		setBlock(room.isBlock());
		setUser_Count(room.getUser_Count());
		setMax_User(room.getMax_User());
		
		init();
		setDisplay();
		addListeners();
	}
	private void init() {
		//해림수정 0704
		btnColor = new Color(0xE59BB9); //진한분홍
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		bgColor = new Color(0xFFFFFF); // 하얀색
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		String msgIsBlock = null;
		if(isBlock) {
			msgIsBlock = "비공개방";
		} else if (!isBlock) {
			msgIsBlock = "공개방";
		}
		lblIsBlock = new JLabel(msgIsBlock, JLabel.CENTER);
		lblIsBlock.setFont(font);
		lblIsBlock.setOpaque(true);
		lblIsBlock.setBackground(edgeColor);
		
		String msgLblTitle = roomTitle + "   (  " + user_Count + " / " +max_User+"  )   ";
		// 해림 수정---------------------------------------------0627
				String roomNum = String.valueOf(room.getRoomNumber());
				lblTitle = new JLabel(msgLblTitle,JLabel.CENTER);
				lblTitle.setFont(font);
				lblRoomNum = new JLabel(roomNum,JLabel.CENTER); 
				lblRoomNum.setFont(font);
				lblRoomNum.setForeground(bgColor);
//				---------------------------------------------------------------
		
		
		lblCategory = new JLabel(category,JLabel.CENTER);
		lblCategory.setFont(font);
	}
	private void setDisplay() {
		setLayout(new GridLayout(0,1));
//		해림수정 ---------------------------------------------
		JPanel pnlWest_East = new JPanel();
		pnlWest_East.add(lblIsBlock);
		pnlWest_East.setPreferredSize(new Dimension(70,30));
		pnlWest_East.setBorder(new LineBorder(edgeColor, 2));
		pnlWest_East.setBackground(edgeColor);
		
		JPanel pnlWest_West = new JPanel();
		pnlWest_West.add(lblRoomNum);
		pnlWest_West.setPreferredSize(new Dimension(30,30));
		pnlWest_West.setBorder(new LineBorder(btnColor, 1));
		pnlWest_West.setBackground(btnColor);
		
		JPanel pnlWest = new JPanel(new BorderLayout());
		pnlWest.add(pnlWest_East, BorderLayout.EAST);
		pnlWest.add(pnlWest_West,BorderLayout.WEST);
//		pnlWest.setBorder(new LineBorder(btnColor, 3));
		pnlWest.setBackground(bgColor);
//		-------------------------------------------------------
		
		
		JPanel pnlCenter_Left = new JPanel(new BorderLayout());
		pnlCenter_Left.add(lblTitle, BorderLayout.NORTH);
		pnlCenter_Left.add(lblCategory, BorderLayout.CENTER);
		pnlCenter_Left.setBackground(bgColor);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnlCenter_Left, BorderLayout.NORTH);
//		pnlCenter.setBorder(new LineBorder(btnColor, 3));
		pnlCenter.setBackground(bgColor);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlWest,BorderLayout.WEST);
		pnlTotal.add(pnlCenter,BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(btnColor, 2));
		
		if(isBlock){
			Toolkit kit = Toolkit.getDefaultToolkit();
			Image img = kit.getImage("lock.png");
			img = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			ImageIcon imgIcon = new ImageIcon(img);
			JLabel lblSecret = new JLabel(imgIcon) ;
			lblSecret.setBorder(new EmptyBorder(0,0,0,10));
			pnlCenter.add(lblSecret, BorderLayout.EAST);
		}
		add(pnlTotal,BorderLayout.NORTH);
		
		
	}
	
	
	public String getRoomTitle() {
		return roomTitle;
	}
	
	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	
	
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getUser_Count() {
		return user_Count;
	}
	
	public void setUser_Count(int user_Count) {
		this.user_Count = user_Count;
	}
	
	public int getMax_User() {
		return max_User;
	}
	
	public void setMax_User(int max_User) {
		this.max_User = max_User;
	}
	
	public boolean isBlock(boolean isBlock) {
		return isBlock;
	}
	
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	public void addListeners() {
		MouseAdapter mAdapter = new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				if(me.getButton()==MouseEvent.BUTTON1){
					if(me.getClickCount()==2){
						if(isBlock){
							String pas = JOptionPane.showInputDialog("비밀번호를 입력하시오");
							if(room.getPassword().equals(pas)){
								SendData data = new SendData(OPEN_CHAT_ROOM,room.getRoomNumber());
								cData.sendTarget(data);
							} else{
								JOptionPane.showMessageDialog(Room_PnlMaker.this,"비밀번호가 다릅니다");
							}
						} else{
							SendData data = new SendData(OPEN_CHAT_ROOM,room.getRoomNumber());
							cData.sendTarget(data);
						}
					}
				}
			}
		};
		addMouseListener(mAdapter);
	}

}