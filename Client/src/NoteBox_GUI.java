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
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NoteBox_GUI extends JFrame {
	private JButton btnSendNote;
	private JLabel lblNoteBox;
	private JLabel lblReceiveBox;
	private JLabel lblSendBox;
	private JLabel lblNoReceiveContent;
	private JLabel lblNoSendContent;
	private JButton btnExit;
	private Point ptFirst;
	private Color bgColor;
	private Color btnColor;
	private Color bgGColor;
	private TreeMap<Long, Note> post_Note;
	private TreeMap<Long, Note> receive_Note;

	private String sendId;


	// 박상욱 수정
	// 동훈 수정 쪽지 -----------------------------------------------------
	private JPanel pnlSendNote;

	private JPanel pnlReceivedNote;

	private JScrollPane scrollSend;
	private JScrollPane scrollReceive;

	public JPanel getPnlSendNote() {
		return pnlSendNote;
	}

	public void setPnlSendNote(JPanel pnlSendNote) {
		this.pnlSendNote = pnlSendNote;
	}

	public JPanel getPnlReceivedNote() {
		return pnlReceivedNote;
	}

	public void setPnlReceivedNote(JPanel pnlReceivedNote) {
		this.pnlReceivedNote = pnlReceivedNote;
	}

	private JPanel pnlSouth;

	private JPanel pnlMsgReceive;
	private JPanel pnlMsgSend;

	private JPanel pnlReceiveCover;
	private JPanel pnlSendCover;
	// 동훈 수정 쪽지 -----------------------------------------------------
	private ClientData cData;

	public NoteBox_GUI(TreeMap<Long, Note> sendNote,
			TreeMap<Long, Note> receiveNote, ClientData cData) {
		this.post_Note = sendNote;
		this.receive_Note = receiveNote;
		this.cData = cData;
		init();
		setDisplay();
		// setSendNote(post_Note);
		// setReceiveNote(receive_Note);
		addListeners();
		showDiag();
	}

	private void init() {

		bgColor = new Color(0xFFEBFF);
		bgGColor = new Color(0xE59BB9);
		btnColor = new Color(0xFFD9EC);

		// FFEBFF// 엄청연한분홍
		// FFD9EC // 연한분홍
		// E59BB9 // 진한분홍

		Font font = new Font("HY나무B", Font.PLAIN, 15);

		lblNoReceiveContent = new JLabel("받은쪽지가  없습니다.", JLabel.CENTER);
		lblNoReceiveContent.setFont(font);
		lblNoReceiveContent.setPreferredSize(new Dimension(200, 300));
		lblNoReceiveContent.setBackground(Color.WHITE); // 해림수정0701
		lblNoSendContent = new JLabel("보낸쪽지가  없습니다.", JLabel.CENTER);
		lblNoSendContent.setFont(font);
		lblNoSendContent.setPreferredSize(new Dimension(200, 300));
		lblNoSendContent.setBackground(Color.WHITE); // 해림수정0701

		lblNoteBox = new JLabel("   쪽지함");
		lblNoteBox.setFont(font);

		btnSendNote = new JButton("새로운 쪽지 보내기");
		btnSendNote.setFont(font);
		btnSendNote.setForeground(Color.WHITE); // 해림수정0701
		btnSendNote.setBackground(bgGColor); // 해림수정0701
		btnSendNote.setBorderPainted(false); // 해림수정0701
		btnSendNote.setBorder(new LineBorder(Color.WHITE, 1));

		btnExit = new JButton("X");
		btnExit.setPreferredSize(new Dimension(50, 25));
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(bgGColor);
		btnExit.setBorder(new LineBorder(bgGColor, 1));

		lblReceiveBox = new JLabel("받은 쪽지함", JLabel.CENTER);
		lblReceiveBox.setFont(font);
		lblSendBox = new JLabel("보낸 쪽지함", JLabel.CENTER);
		lblSendBox.setFont(font);

		pnlReceiveCover = new JPanel(new BorderLayout());
		pnlReceiveCover.setBackground(Color.WHITE); // 해림수정0701
		pnlSendCover = new JPanel(new BorderLayout());
		pnlSendCover.setBackground(Color.WHITE); // 해림수정0701

		pnlReceivedNote = new JPanel(new GridLayout(0, 1));
		pnlReceivedNote.setBackground(Color.WHITE); // 해림수정0701
		pnlSendNote = new JPanel(new GridLayout(0, 1));
		pnlSendNote.setBackground(Color.WHITE); // 해림수정0701

	}

	private void setDisplay() {
		// 동훈 수정 0625
		// 동훈 수정 쪽지 -----------------------------------------------------
		JPanel pnlNorth_North = new JPanel(new BorderLayout());
		pnlNorth_North.add(lblNoteBox, BorderLayout.WEST);
		pnlNorth_North.add(btnExit, BorderLayout.EAST);

		pnlNorth_North.setBackground(bgColor);
		pnlNorth_North.setBorder(new EmptyBorder(0,0,5,0));

		pnlMsgReceive = new JPanel(new GridLayout(0, 1));
		pnlMsgReceive.setBackground(btnColor);
		pnlMsgReceive.add(lblReceiveBox, BorderLayout.NORTH);
		pnlMsgSend = new JPanel(new GridLayout(0, 1));
		pnlMsgSend.setBackground(btnColor);
		pnlMsgSend.add(lblSendBox, BorderLayout.NORTH);

		JPanel pnlNorth_South = new JPanel(new GridLayout(1, 2));
		pnlNorth_South.setBorder(new EmptyBorder(10, 0, 0, 0));
		pnlNorth_South.setBackground(bgColor);
		pnlNorth_South.add(pnlMsgSend);
		pnlNorth_South.add(pnlMsgReceive);
		pnlNorth_South.setBorder(new LineBorder(Color.WHITE, 1));

		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		pnlNorth.add(pnlNorth_North);
		pnlNorth.add(btnSendNote);
		pnlNorth.add(pnlNorth_South);

		pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBackground(Color.WHITE);
		// 동훈 수정 쪽지
		scrollReceive = setting_JScroll(pnlReceiveCover, 280, 500);
		if (receive_Note.size() != 0) {
			setting_Note_Box(receive_Note, pnlReceivedNote);
			pnlReceiveCover.add(pnlReceivedNote, BorderLayout.NORTH);
			pnlSouth.add(scrollReceive, BorderLayout.CENTER);
		} else if (receive_Note.size() == 0) {
			pnlReceivedNote.add(lblNoReceiveContent);
			pnlReceiveCover.add(pnlReceivedNote, BorderLayout.NORTH);
			pnlSouth.add(scrollReceive, BorderLayout.CENTER);
		}
		scrollSend = setting_JScroll(pnlSendCover, 280, 500);
		if (post_Note.size() != 0) {
			setting_Note_Box(post_Note, pnlSendNote);
			pnlSendCover.add(pnlSendNote, BorderLayout.NORTH);
			pnlSouth.add(scrollSend, BorderLayout.WEST);

		} else if (post_Note.size() == 0) {
			pnlSendNote.add(lblNoSendContent);
			pnlSendCover.add(pnlSendNote, BorderLayout.NORTH);
			pnlSouth.add(scrollSend, BorderLayout.WEST);
		}
		pnlSouth.setPreferredSize(new Dimension(400, 300));
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlSouth, BorderLayout.CENTER);
		// 동훈 수정 쪽지

		// 해림수정 0703
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlSouth, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(bgColor, 6));// 해림수정0702
		add(pnlTotal, BorderLayout.CENTER);
	}

	// 동훈 수정 쪽지 -----------------------------------------------------

	// 보내는 쪽지함 ,받는쪽지함이 같아서 메서드 통일 2번째파라미터로 어디패널에 넣을지 정하면됨.
	public void updateNote_Box(TreeMap<Long, Note> note_Box, JPanel pnl) {
		updatePnl(setting_Note_Box(note_Box, pnl));
		pnlSouth.updateUI();
	}

	// 동훈 수정 쪽지
	public JPanel setting_Note_Box(TreeMap<Long, Note> note_Box, JPanel pnl) {
		pnl.removeAll();

		for (Long time : note_Box.keySet()) {
			Note note = note_Box.get(time);
			Long sendTime = time;
			NoteBox_PnlMaker pnlNoteBox = new NoteBox_PnlMaker(note, sendTime,
					cData);
			pnlNoteBox.setBackground(bgGColor); // 해림수정0701
			pnl.add(pnlNoteBox);

		}
		updatePnl(pnl);
		return pnl;
	}

	// 동훈 수정 쪽지
	public void updateSp(JScrollPane sp) {
		sp.invalidate();
		sp.repaint();
		sp.updateUI();
	}

	public void updatePnl(JPanel pnl) {
		pnl.invalidate();
		pnl.repaint();
		pnl.updateUI();
	}

	public JScrollPane setting_JScroll(JPanel pnl, int x, int y) {
		JScrollPane scrollPeople = new JScrollPane(pnl,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPeople.setPreferredSize(new Dimension(x, y));// 250 605
		scrollPeople
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPeople;
	}

	private void addListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == btnSendNote) {
					NoteBox_GUI.this.setAlwaysOnTop(false);
					inputMsg("쪽지를 보낼 ID를 입력하세요");
					} else if (src == btnExit) {
					cData.getGui().checkOnGUI().setAlwaysOnTop(true);
					cData.getGui().checkOnGUI().setAlwaysOnTop(false);
					if(cData.getGui().checkOnGUI()==cData.getGui().getChat_Room_Gui()){
						cData.getGui().getChat_Room_Gui().setCheckBox();	
					}
					if(cData.getGui().checkOnGUI() == cData.getGui().getChat_Room_Gui()) {
					cData.getGui().getChat_Room_Gui().setCbState();
					}
					dispose();
				}
			}
		};
		
		MouseAdapter mListener = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JPanel selectPnl  = (JPanel)e.getSource();
				
				new Open_Note_GUI();
			}
		};
		btnSendNote.addActionListener(listener);
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
	}	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	private void showDiag() {
		setSize(570, 700);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}
	public void inputMsg(String str) {
		new MsgDiag(str, cData.getGui().checkOnGUI(), cData);
	}
}
