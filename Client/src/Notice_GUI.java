
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Notice_GUI extends JDialog{
	private JLabel lblNoticeTitle;
	private JTextField tfTitle;
	private JTextArea taNoticeText;
	private JButton btnOK;
	private JButton btnDel;
	private JButton btnCancel;
	private ChatRoom_GUI owner;
	private ClientData cData;
	private int roomNum;
	private Chat_Room room;
	
	private JPanel pnlTop;
	private JLabel lblTitle;
	private JButton btnExit;
	private Color btnColor;
	private Color edgeColor;
	private Color bgGColor;
	private Font font;
	
	private Point ptFirst;
	
	public Notice_GUI(ClientData cData,int roomNum,ChatRoom_GUI owner){
		this.cData =cData;
		this.roomNum =roomNum;
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showDiag();
		this.setAlwaysOnTop(true);
	}
	public Notice_GUI(Chat_Room room){
		this.room =room;
		init();
		setDisplay_User();
		addListeners_User();
		showDiag();
		this.setAlwaysOnTop(true);
	}


	private void init(){
		//해림수정 0704
		btnColor = new Color(0xE59BB9); //진한분홍
		bgGColor = new Color(0xFFD9EC); // 연한분홍
		edgeColor = new Color(0xFFFFFF); // 하얀색
		font = new Font("HY나무B", Font.PLAIN, 15);

		lblTitle = new JLabel("공지 설정");
		lblTitle.setFont(font);
		lblNoticeTitle = new JLabel("  공지 제목  ");
		lblNoticeTitle.setFont(font);
		tfTitle = new JTextField(10);
		tfTitle.setBorder(new LineBorder(null));
		taNoticeText = new JTextArea(10,20);
		taNoticeText.setBorder(new LineBorder(btnColor, 3));
		taNoticeText.setLineWrap(true);
		
		
		btnOK = new JButton("등록");
		btnOK.setFont(font);
		btnOK.setPreferredSize(new Dimension(70, 30));
		btnOK.setForeground(Color.WHITE);
		btnOK.setBackground(btnColor);
		btnOK.setBorder(new LineBorder(Color.WHITE, 1));
		
		btnDel = new JButton("삭제");
		btnDel.setFont(font);
		btnDel.setPreferredSize(new Dimension(70, 30));
		btnDel.setForeground(Color.WHITE);
		btnDel.setBackground(btnColor);
		btnDel.setBorder(new LineBorder(Color.WHITE, 1));
		
		
		btnExit = new JButton(" X ");
		btnExit.setPreferredSize(new Dimension(50, 25));
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(btnColor);
		btnExit.setBorder(new LineBorder(Color.WHITE, 1));

		btnCancel = new JButton("취소");
		btnCancel.setFont(font);
		btnCancel.setPreferredSize(new Dimension(70, 30));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(btnColor);
		btnCancel.setBorder(new LineBorder(Color.WHITE, 1));
		
	}
		
	
	private void setDisplay(){

		
		pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(lblTitle, BorderLayout.WEST);
		pnlTop.add(btnExit, BorderLayout.EAST);
		pnlTop.setBackground(bgGColor);
		pnlTop.setBorder(new EmptyBorder(2,3,5,3));
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblNoticeTitle);
		pnlNorth.add(tfTitle);
		pnlNorth.setBackground(edgeColor);
	
		JPanel pnlCenter = new JPanel();

		JScrollPane scrollPane = new JScrollPane(taNoticeText);
		pnlCenter.add(scrollPane);
		pnlCenter.setBackground(edgeColor);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnOK);
		pnlSouth.add(btnDel);
		pnlSouth.add(btnCancel);
		pnlSouth.setBackground(edgeColor);
		
		JPanel pnlMid = new JPanel(new BorderLayout());
		pnlMid.add(pnlNorth,BorderLayout.NORTH);
		pnlMid.add(pnlCenter, BorderLayout.CENTER);
		pnlMid.setBackground(edgeColor);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		
		pnlTotal.add(pnlTop,BorderLayout.NORTH);
		pnlTotal.add(pnlMid,BorderLayout.CENTER);
		pnlTotal.add(pnlSouth,BorderLayout.SOUTH);
		pnlTotal.setBorder(new LineBorder(bgGColor, 5));
		
		add(pnlTotal, BorderLayout.CENTER);
	}
	private void setDisplay_User(){
		btnCancel = new JButton("닫기");
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblNoticeTitle);
		if ((room.getNotice_Title()!=null)){
			tfTitle.setText(room.getNotice_Title());
		}
		pnlNorth.add(tfTitle);
		tfTitle.setEnabled(false);
		
		JPanel pnlCenter = new JPanel();
		if ((room.getNotice_Msg()!=null)){
			taNoticeText.setText(room.getNotice_Msg());
		}
		taNoticeText.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(taNoticeText);
		pnlCenter.add(scrollPane);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnCancel);
		
		add(pnlNorth,BorderLayout.NORTH);
		add(pnlCenter,BorderLayout.CENTER);
		add(pnlSouth,BorderLayout.SOUTH);
	}
	private void addListeners(){//0623 박상욱
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btnCancel || src == btnExit){
					if(owner.getCbTop().isSelected()) {
						owner.setAlwaysOnTop(true);
						}
					dispose();
					
				}
				if(src == btnOK){
					if (taNoticeText.getText()!=null && tfTitle.getText()!=null){
							SendData send = new SendData(cData.SET_NOTICE,taNoticeText.getText(),tfTitle.getText(),roomNum);
							cData.sendTarget(send);
							dispose();
					}else{
						JOptionPane.showMessageDialog(null, "제목과 내용 기입을 확인해주세요");
					}
					//뭔가 센드데이터 3개이상부턴 수정해야할 듯!!!
				}else if(src ==btnDel){
					SendData send = new SendData(cData.DEL_NOTICE,roomNum);
					cData.sendTarget(send);
					dispose();
				}
			}
		};
		btnCancel.addActionListener(listener);
		btnOK.addActionListener(listener);
		btnDel.addActionListener(listener);
		btnExit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	private void addListeners_User(){//0623 박상욱
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btnCancel){
					dispose();
				}
			}
		};
		btnCancel.addActionListener(listener);	
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	
	public JTextField getTfTitle() {
		return tfTitle;
	}

	public void setTfTitle(JTextField tfTitle) {
		this.tfTitle = tfTitle;
	}

	public JTextArea getTaNoitceText() {
		return taNoticeText;
	}

	public void setTaNoitceText(JTextArea taNoitceText) {
		this.taNoticeText = taNoitceText;
	}
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

	private void showDiag(){
		
		setSize(350,310);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(owner.getSize().width/2, owner.getSize().height/2);
//		setLocation((int)owner.getLocation().getX()+ 1045, (int)owner.getLocation().getY() + 200);
	
		setVisible(true);
	}
}

