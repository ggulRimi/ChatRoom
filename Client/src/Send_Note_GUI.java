
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.border.LineBorder;


public class Send_Note_GUI extends JDialog{
	private JLabel lblTitle;
	private JTextField tfTitle;
	private JTextArea taNoteText;
	private JButton btnSend;
	private JButton btnCancel;
	private ClientData cData;
	private String id;
	private String myId;
	//해림수정0702
	private Point ptFirst; 
	private Color bgColor;
	private Color btnXColor;
	private JButton btnExit;
	private JLabel lblTopTitle;
	private Color edgeColor;
	
	public Send_Note_GUI(ClientData cData,String id){
		this.cData =cData;
		this.id =id;
		init();
		setDisplay();
		addListeners();
		showDiag();
	}
	//동훈 쪽지 수정
	private void init(){

		//해림수정0702
		bgColor = new Color(0xFFFFFF); //하얀색
		btnXColor = new Color(0xE59BB9); //진한분홍
		edgeColor = new Color(0xFFD9EC); // 연한분홍
		Font font = new Font("HY나무B", Font.PLAIN, 15);
		
		myId = cData.getMyID();
	
//		tfUser.setBorder(new EmptyBorder(0,0,0,0));

		lblTitle = new JLabel("  제목  ",JLabel.CENTER);
		lblTitle.setFont(font);

		tfTitle = new JTextField(15); 
		tfTitle.setDocument((new JTextFieldLimit(10))); //글자수제한 해림수정0702
		
		lblTopTitle = new JLabel("   쪽지보내기", JLabel.LEFT);
		lblTopTitle.setFont(font);
		lblTopTitle.setForeground(Color.WHITE); //해림수정0702

		lblTitle.setPreferredSize(new Dimension(100,20));
		taNoteText = new JTextArea(9,25);
		taNoteText.setLineWrap(true);
		
		btnSend = new JButton("보내기");
		btnSend.setFont(font);
		btnSend.setForeground(Color.WHITE); //해림수정0702
		btnSend.setBorderPainted(false); //해림수정0702
		btnSend.setBackground(btnXColor); //해림수정0702
		btnSend.setBorder(new LineBorder(btnXColor, 1));//해림수정0702
		btnSend.setPreferredSize(new Dimension(70, 30));//해림수정0702
		
		btnCancel = new JButton("취소");
		btnCancel.setFont(font);
		btnCancel.setForeground(Color.WHITE); //해림수정0702
		btnCancel.setBorderPainted(false); //해림수정0702
		btnCancel.setBackground(btnXColor); //해림수정0702
		btnCancel.setBorder(new LineBorder(Color.WHITE, 1));//해림수정0702
		btnCancel.setPreferredSize(new Dimension(70, 30));//해림수정0702
		
		//동훈 쪽지 수정
		//해림수정 0702
		btnExit = new JButton("X");
		btnExit.setPreferredSize(new Dimension(50, 25));
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(btnXColor);
		btnExit.setBorder(new LineBorder(bgColor, 1));//해림수정0704

	}
	
	private void setDisplay(){
		JPanel pnlNorth = new JPanel(new GridLayout(0,1));
		JPanel pnlNorthTop = new JPanel(new BorderLayout());
		JPanel pnlNorthBottom = new JPanel();
		pnlNorthBottom.add(lblTitle);
		pnlNorthBottom.add(tfTitle);
		pnlNorthBottom.setBackground(bgColor);//해림수정0702
		pnlNorthTop.add(btnExit, BorderLayout.EAST); //해림수정0702
		pnlNorthTop.setBackground(btnXColor);//해림수정0702
		pnlNorthTop.add(lblTopTitle, BorderLayout.CENTER);
		
		pnlNorth.add(pnlNorthTop);
		pnlNorth.add(pnlNorthBottom);
		
		JPanel pnlCenter = new JPanel();
		JScrollPane scrollPane = new JScrollPane(taNoteText);
		pnlCenter.add(scrollPane);
		pnlCenter.setBackground(bgColor);
		scrollPane.setBorder(new LineBorder(btnXColor, 3));
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnSend);
		pnlSouth.add(btnCancel);
		pnlSouth.setBackground(bgColor);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		
		pnlTotal.add(pnlNorth,BorderLayout.NORTH);
		pnlTotal.add(pnlCenter,BorderLayout.CENTER);
		pnlTotal.add(pnlSouth,BorderLayout.SOUTH);
		
		pnlTotal.setBorder(new LineBorder(edgeColor,5));
		
		add(pnlTotal, BorderLayout.CENTER);
	}
	
	private void addListeners(){
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btnCancel || src == btnExit){ //해림수정 0702
					dispose();
				}
				
				if(src == btnSend){
					// 동훈수정 0702
					if(tfTitle.getText().trim().length() != 0 && taNoteText.getText().trim().length() != 0) {
						Note note = new Note(myId,id,tfTitle.getText(),taNoteText.getText());//보낸사람 id,받는 사람 id
						//동훈 수정 06_25 쪽지
						Long time = System.currentTimeMillis();
						//동훈 수정 06_25 쪽지				
						SendData send = new SendData(cData.SEND_NOTE,note, time);
						cData.sendTarget(send);
						dispose();
					} else if(tfTitle.getText().trim().length() == 0 &&  taNoteText.getText().trim().length() == 0){
						JOptionPane.showMessageDialog(null, 
								"제목과 내용을 입력하세요!", "Message", 
								JOptionPane.ERROR_MESSAGE); 					
					} else if(taNoteText.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(null, 
								"내용을 입력하세요!.", "Message", 
								JOptionPane.ERROR_MESSAGE); 
					} else if(tfTitle.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(null, 
								"제목을 입력하세요!.", "Message", 
								JOptionPane.ERROR_MESSAGE); 
					}
				}
				
			}
		};
		btnCancel.addActionListener(listener);
		btnSend.addActionListener(listener);
		btnExit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}
	
	
	
	// 해림수정 0701------------------------------------------------------------
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
	private void showDiag(){
		setSize(350, 300);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true); //해림 수정 0701 창 프레임 없앰
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
}

