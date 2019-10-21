import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NoteBox_PnlMaker extends JPanel implements iProtocol {

	private String id; // 이름
	private String title; // 쪽지 제목
	private Long time; // 송수신 시간

	private JLabel lblId;
	private JLabel lblTitle;
	private JLabel lblTime;

	private int deleteNote;

	private JPanel pnlWest;
	private JPanel pnlCenter;
	private JPanel pnlCenter_South;
	private JPanel pnlMain;
	private Note note;
	private ClientData cData;
	// 07_01 동훈 쪽지 수정
	private JPopupMenu pNoteMenu;
	private JMenuItem miOpen;
	private JMenuItem miDelete;
	private JMenuItem miSendNote;
	// 07_01 동훈 쪽지 수정
	private Color color;
	private Color edgeColor;

	private SimpleDateFormat dayTime;

	public NoteBox_PnlMaker(Note note, Long SendTime, ClientData cData) {
		this.note = note;
		this.cData = cData;
		this.time = SendTime;
		init();
		setDisplay();
		addListeners();
	}

	private void init() {
		deleteNote = 0;
		// 동훈 수정 쪽지
		if (note.getPost_Id().equals(cData.getMyID())) {
			id = note.getReceive_id();
		} else {
			id = note.getPost_Id();
		}

		title = note.getTitle();

		lblId = new JLabel(id, JLabel.CENTER);
		lblId.setVerticalAlignment(SwingConstants.CENTER);
		
		// 07_01 동훈 쪽지 수정
		pNoteMenu = new JPopupMenu();
		miOpen = new JMenuItem("쪽지 읽기");
		miDelete = new JMenuItem("쪽지 삭제");
		miSendNote = new JMenuItem("답장");

		pNoteMenu.add(miOpen);
		pNoteMenu.add(miDelete);
		pNoteMenu.add(miSendNote);
		// 0704 해림수정
//		FFFFFF //하얀색
//		FFEBFF// 엄청연한분홍
//		FFD9EC // 연한분홍
//		E59BB9 // 진한분홍
		color = new Color(0xE59BB9);
		edgeColor = new Color(0xFFD9EC);
		
		lblTitle = new JLabel(title, JLabel.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		// 심플포맷 해야됨
		dayTime = new SimpleDateFormat("YYYY-MM-dd  a hh:mm ", new Locale(
				"en", "US"));
		String StrTime = dayTime.format(time);

		lblTime = new JLabel(StrTime, JLabel.CENTER);
		lblTime.setVerticalAlignment(SwingConstants.CENTER);
	}

	// 동훈 수정 쪽지
	private void setDisplay() {
		pnlWest = new JPanel(new BorderLayout());
		setPnl(pnlWest, lblId);
		pnlWest.setBorder(new LineBorder(Color.WHITE, 1));
		
		pnlCenter_South = new JPanel(new BorderLayout());
		setPnl(pnlCenter_South, lblTime);
	
		pnlCenter = new JPanel(new BorderLayout());
		setPnl(pnlCenter, lblTitle);
		pnlCenter.add(pnlCenter_South, BorderLayout.SOUTH);

		pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.setBorder(new LineBorder(Color.WHITE, 1));
		pnlMain.setPreferredSize(new Dimension(250,80));
		
		add(pnlMain, BorderLayout.CENTER);
		

	}

	/*
	 * 
	 * 내가 수신했을 노트 를 받음 post : 이게 상대방아이디 rece :이게 내아이디
	 * 
	 * if(postId != cData.myId) { 받은거 }
	 */

	private void setPnl(JPanel pnl, JLabel lbl) {
		pnl.add(lbl);
		lbl.setBorder(new EmptyBorder(10, 20, 10, 20));
	
		pnl.setBackground(color); //확인
		lbl.setForeground(Color.WHITE);
	}

	// 07_01 동훈 수정 쪽지
	private void addListeners() {
		MouseAdapter mAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					new Open_Note_GUI(note, cData);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}

		};

		this.addMouseListener(mAdapter);

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == miOpen) {
					new Open_Note_GUI(note, cData);
				} else if (src == miDelete) {
					if (note.getPost_Id().equals(cData.getMyID())
							&& note.getReceive_id().equals(cData.getMyID())) {
						deleteNote = 0;
					}

					else if (note.getPost_Id().equals(cData.getMyID())) {
						deleteNote = 1;
					} else if(note.getReceive_id().equals(cData.getMyID())) {
						deleteNote = 2;
					}
					SendData send = new SendData(cData.DELETE_NOTE, time,
							cData.getMyID(), deleteNote);
					cData.sendTarget(send);
				} else if (src == miSendNote) {// 0624 박상욱 쪽지
					new Send_Note_GUI(cData, id);
				}
			}
		};

		miOpen.addActionListener(listener);
		miDelete.addActionListener(listener);
		miSendNote.addActionListener(listener);

	}

	private void showPopup(MouseEvent me) {
		if (me.isPopupTrigger()) {
			pNoteMenu.show(this, me.getX(), me.getY());
		}
	}
	// 07_01 동훈 수정 쪽지
}
