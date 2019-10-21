//동훈 쪽지 수정  전체적으로 다함
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Open_Note_GUI extends JFrame{
	private JLabel lblTitle;
	private JTextArea taNoteText;
	private JButton btnExit;
	private ClientData cData;
	private String id;
	private String myId;
	
	private String noteType;
	private JLabel lblDlgName;
	private Color color;
	private Color edgeColor;
	private Color bgColor;
	private Color pinkColor;
	private Font font;
	private Point ptFirst;
	private Note note;
//	JLabel
	
	public Open_Note_GUI(Note note, ClientData cData){
		this.note = note;
		this.cData =cData;
		init();
		setDisplay();
		addListeners();
		showDiag();
	}
	public Open_Note_GUI() {
		init();
		setDisplay();
		addListeners();
		showDiag();
	}
	//동훈 쪽지 수정
	private void init(){
		// 0704 해림수정
//		FFFFFF //하얀색
//		FFEBFF// 엄청연한분홍
//		FFD9EC // 연한분홍
//		E59BB9 // 진한분홍
		color = new Color(0xFFFFFF);
		edgeColor = new Color(0xE59BB9);
		bgColor = new Color(0xFFD9EC);
		pinkColor = new Color(0xFFEBFF);
		font = new Font("HY나무B", Font.PLAIN, 15);
		
		noteType = "   받은 쪽지";
		if(note.getPost_Id().equals(cData.getMyID())) {
			noteType = "   보낸 쪽지";
		}
		lblDlgName = new JLabel(noteType);
		lblDlgName.setFont(font);
		
		lblTitle = new JLabel("제목 : " + note.getTitle(),JLabel.CENTER);
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setPreferredSize(new Dimension(100,15));
		taNoteText = new JTextArea(10,25);
		taNoteText.append(note.getMsg());
		taNoteText.setLineWrap(true);
		
		btnExit = new JButton(" X ");
		btnExit.setBackground(edgeColor);
		btnExit.setBorderPainted(false);
		btnExit.setForeground(Color.WHITE);
		//동훈 쪽지 수정
	}
	
	private void setDisplay(){
		
		JPanel pnlLblCover = new JPanel(new BorderLayout());
		pnlLblCover.add(lblDlgName, BorderLayout.CENTER);
		pnlLblCover.setBackground(bgColor);
//		pnlLblCover.setBorder(new EmptyBorder(12,10,10,0));
		JPanel pnlBtnCover = new JPanel();
		pnlBtnCover.add(btnExit);
		pnlBtnCover.setBackground(bgColor);
//		pnlBtnCover.setBorder(new EmptyBorder(5,0,0,5));
		
		
		JPanel pnlNorthTop = new JPanel(new BorderLayout());
		pnlNorthTop.add(pnlLblCover, BorderLayout.WEST);
		pnlNorthTop.add(pnlBtnCover, BorderLayout.EAST);
		pnlNorthTop.setBackground(bgColor);
		JPanel pnlNorthBottom = new JPanel(new BorderLayout());
		pnlNorthBottom.add(lblTitle, BorderLayout.CENTER);
//		pnlNorthBottom.setBorder(new EmptyBorder(15,5,5,0));
		pnlNorthBottom.setBackground(edgeColor);
		
		
		JPanel pnlNorth = new JPanel(new GridLayout(0,1));
		pnlNorth.setPreferredSize(new Dimension(100,80));
		pnlNorth.add(pnlNorthTop);
		pnlNorth.add(pnlNorthBottom);
		pnlNorth.setBackground(edgeColor);

		
	
		
		JPanel pnlCenter = new JPanel();
		JScrollPane scrollPane = new JScrollPane(taNoteText);
		pnlCenter.add(scrollPane);
		pnlCenter.setBackground(color);
		scrollPane.setBorder(new LineBorder(edgeColor, 3));
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlNorth,BorderLayout.NORTH);
		pnlTotal.add(pnlCenter,BorderLayout.CENTER);
		
		add(pnlTotal, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(bgColor,6));
		
		
	}
	
	private void addListeners(){
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == btnExit){
					dispose();
				}
				
			}
		};
		
		btnExit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
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
		setSize(300,320);
		setResizable(true);
		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Open_Note_GUI();
	}
	
}

