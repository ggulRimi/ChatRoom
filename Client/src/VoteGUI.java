import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VoteGUI extends JFrame implements iProtocol {
	private JTextField tfMany;
	private JButton btnMany;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnResult;
	private JButton btnExit;

	private JButton btnDelete;
	private JPanel pnlSouth_result;

	private JList<String> list;
	private DefaultListModel<String> model;
	private Vector<String> trans_List;
	private ClientData cData;
	private int roomNum;
	private Vector<String> voteList;
	private JLabel lblPut;
	private JLabel lblTitle;
	private JLabel lblTitleName;
	private JTextField tfTitle;
	private String voteTitle;
	private JLabel lblTitle2;

	private JLabel lblVoteClose;
	private Color bgColor;
	private Color OutColor;
	private Color bgGColor;
	private Font font;
	private ChatRoom_GUI owner;
	private Point ptFirst;

	private JPanel pnlTotal;

	public VoteGUI(ClientData cData, int roomNum) {
		this.cData = cData;
		this.roomNum = roomNum;
		init();
		setDisplay();
		addActionlisteners();
		showDlg();
	}

	public VoteGUI(ClientData cData, Vector<String> voteList, int roomNumt,
			String voteTitle) {
		this.cData = cData;
		this.roomNum = roomNum;
		this.voteList = voteList;
		this.voteTitle = voteTitle;
		init2();
		setDisplay2();
		addActionlisteners2();
		showDlg2();

	}

	private void init() {
		bgColor = new Color(0xFFFFFF);
		bgGColor = new Color(0xE59BB9);
		OutColor = new Color(0xFFD9EC);

		font = new Font("HY나무B", Font.PLAIN, 15);
		// FFEBFF// 엄청연한분홍
		// FFD9EC // 연한분홍
		// E59BB9 // 진한분홍
		lblTitleName = new JLabel("투표 종료");
		lblTitleName.setFont(font);
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setPrototypeCellValue("abcdefghijklmnopqrstuskdlfjskdlfjskldfj");
		list.setVisibleRowCount(10);
		list.setCellRenderer(new MyListCellRenderer());
		trans_List = new Vector<String>();
		tfMany = new JTextField(15);
		btnMany = new JButton("추가");
		setButton(btnMany);

		btnOK = new JButton("확인");
		setButton(btnOK);
		btnCancel = new JButton("취소");
		setButton(btnCancel);
		btnResult = new JButton("결과");
		setButton(btnResult);
		btnDelete = new JButton("삭제");
		setButton(btnDelete);
		btnExit = new JButton(" X ");
		setButton(btnExit);
		lblVoteClose = new JLabel("투표를 마감 하시겠습니까?", JLabel.CENTER);
		lblVoteClose.setFont(font);
		lblVoteClose.setPreferredSize(new Dimension(350, 70));

		lblPut = new JLabel("항목 추가");
		lblPut.setFont(font);

		lblTitle = new JLabel("제목  :    ", JLabel.CENTER);
		lblTitle.setFont(font);
		tfTitle = new JTextField(15);

		pnlTotal = new JPanel(new BorderLayout());

	}

	private void setButton(JButton btn) {
		btn.setFont(font);
		btn.setPreferredSize(new Dimension(50, 25));
		btn.setForeground(Color.WHITE);
		btn.setBackground(bgGColor);
		btn.setBorder(new LineBorder(bgGColor, 1));
	}

	private void init2() {

		bgColor = new Color(0xFFFFFF);
		bgGColor = new Color(0xE59BB9);
		OutColor = new Color(0xFFD9EC);
		font = new Font("HY나무B", Font.PLAIN, 15);

		model = new DefaultListModel<String>();
		for (int i = 0; i < voteList.size(); i++) {
			model.addElement(voteList.get(i));
		}

		list = new JList<String>(model);
		list.setPrototypeCellValue("abcdefghijklmnopqrstuskdlfjskdlfjskldfj");
		list.setVisibleRowCount(10);
		list.setCellRenderer(new MyListCellRenderer2());
		list.setBorder(new LineBorder(bgGColor, 2));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btnOK = new JButton("확인");
		setButton(btnOK);
		btnCancel = new JButton("취소");
		setButton(btnCancel);
		lblTitle = new JLabel(voteTitle);
		btnExit = new JButton(" X ");
		setButton(btnExit);
		lblVoteClose = new JLabel("투표를 마감 하시겠습니까?", JLabel.CENTER);
		lblVoteClose.setFont(font);
		lblVoteClose.setPreferredSize(new Dimension(350, 70));

	}

	private void setDisplay() {

		lblTitleName.setText("투표");
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(lblTitleName, BorderLayout.WEST);
		pnlNorth.add(btnExit, BorderLayout.EAST);
		pnlNorth.setBackground(OutColor);
		pnlNorth.setBorder(new EmptyBorder(3, 3, 3, 3));

		JPanel pnlCenter_Top_Up = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlCenter_Top_Up.add(lblTitle);
		pnlCenter_Top_Up.add(tfTitle);
		pnlCenter_Top_Up.setBackground(bgColor);

		JPanel pnlCenter_Top_Down = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlCenter_Top_Down.add(lblPut);
		pnlCenter_Top_Down.add(tfMany);
		pnlCenter_Top_Down.add(btnMany);
		pnlCenter_Top_Down.setBackground(bgColor);
		JPanel pnlCenter_Top = new JPanel(new BorderLayout());
		pnlCenter_Top.add(pnlCenter_Top_Up, BorderLayout.NORTH);
		pnlCenter_Top.add(pnlCenter_Top_Down, BorderLayout.CENTER);
		pnlCenter_Top.setBackground(bgColor);

		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnlCenter_Top, BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(list);
		pnlCenter.add(scroll, BorderLayout.CENTER);
		// System.out.println("중간판 확인");
		pnlCenter.setBackground(bgColor);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnOK);
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnCancel);
		pnlSouth.setBackground(bgColor);

		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);

		pnlTotal.setBorder(new LineBorder(OutColor, 5));
		add(pnlTotal, BorderLayout.CENTER);
	}

	private void setDisplay_Result() {
		lblTitleName.setText("투표 종료");
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(lblTitleName, BorderLayout.WEST);
		pnlNorth.add(btnExit, BorderLayout.EAST);
		pnlNorth.setBackground(OutColor);
		pnlNorth.setBorder(new EmptyBorder(3, 3, 3, 3));

		JPanel pnlResultCover = new JPanel();
		pnlResultCover.add(btnResult);
		pnlResultCover.setBackground(bgColor);
		pnlResultCover.setBorder(new EmptyBorder(0, 0, 10, 0));

		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(lblVoteClose, BorderLayout.CENTER);
		pnlCenter.add(pnlResultCover, BorderLayout.SOUTH);
		pnlCenter.setBackground(bgColor);

		// System.out.println("버튼확인 ");

		pnlTotal.setBackground(bgColor);
		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.setBorder(new LineBorder(OutColor, 5));

		add(pnlTotal, BorderLayout.CENTER);
		pack();
	}

	private void setDisplay2() {
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(lblTitle, BorderLayout.WEST);
		pnlNorth.add(btnExit, BorderLayout.EAST);

		pnlNorth.setBackground(OutColor);
		pnlNorth.setBorder(new EmptyBorder(2, 5, 5, 5));

		JPanel pnlCenter = new JPanel();
		JScrollPane scroll = new JScrollPane(list);
		pnlCenter.add(scroll);
		pnlCenter.setBackground(bgColor);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnOK);
		pnlSouth.add(btnCancel);
		pnlSouth.setBackground(bgColor);

		JPanel pnlTotal2 = new JPanel(new BorderLayout());
		pnlTotal2.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal2.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal2.add(pnlSouth, BorderLayout.SOUTH);
		pnlTotal2.setBackground(bgColor);

		pnlTotal2.setBorder(new LineBorder(OutColor, 6));
		pnlTotal2.setPreferredSize(new Dimension(300, 300));

		add(pnlTotal2, BorderLayout.CENTER);
	}

	private void addActionlisteners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();

				if (src == btnCancel) {
					dispose();
				} else if (src == btnOK) {
					if (model.getSize() != 0) {
						if (tfTitle.getText().length() != 0) {
							System.out.println("오케이");
							for (int i = 0; i < model.getSize(); i++) {
								trans_List.add(model.get(i));
							}
							System.out.println("trans_List" + trans_List
									+ "roomNum" + roomNum + tfTitle.getText());
							SendData send = new SendData(CHATROOM_VOTE,
									trans_List, roomNum, tfTitle.getText());
							try {
								cData.sendTarget(send);
							} catch (Exception e2) {
							}

							pnlTotal.removeAll();
							setDisplay_Result();
							pnlTotal.invalidate();
							pnlTotal.repaint();
							pnlTotal.updateUI();

						}
					} else if (tfTitle.getText().length() == 0) {
						showMsg("투표의 제목을 정해주세요");
						tfTitle.requestFocus();
					} else if (model.getSize() == 0) {
						showMsg( "투표 내용이 없습니다.");
					}

				} else if (src == btnMany) {
					String manyname = tfMany.getText();
					if (!model.contains(manyname)) {

						if (manyname.trim().length() != 0) {

							model.addElement(manyname);
							tfMany.setText("");
							tfMany.grabFocus();
						} else {
							showMsg( "투표 내용을 기입해주세요");
						}
					} else {
						showMsg( "투표내용이 중복 됩니다.");
					}
				} else if (src == btnResult || src == btnExit) {
					trans_List.removeAllElements();
					SendData send = new SendData(VOTE_END, roomNum);
					cData.sendTarget(send);
					dispose();

				} else if (src == btnDelete) {
					int idx = list.getSelectedIndex();
					if (idx >= 0) {
						model.remove(idx);
					}
				}
			}
		};
		btnCancel.addActionListener(listener);
		btnMany.addActionListener(listener);
		btnOK.addActionListener(listener);
		btnResult.addActionListener(listener);
		btnDelete.addActionListener(listener);
		btnExit.addActionListener(listener);
		addMouseMotionListener(new MyMouseMotionListener());
		addMouseListener(new MyMouseListener());
	}

	private void addActionlisteners2() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();

				if (src == btnCancel || src == btnExit) {
					String vote_choice = "무효표";
					SendData send = new SendData(CHATROOM_VOTE_RESULTBACK,
							vote_choice, roomNum + 1);
					cData.sendTarget(send);
					dispose();
				} else if (src == btnOK) {
					if (!list.isSelectionEmpty()) {
						String vote_choice = (int) (list.getSelectedIndex() + 1)
								+ "번  " + (String) list.getSelectedValue();
						SendData send = new SendData(CHATROOM_VOTE_RESULTBACK,
								vote_choice, roomNum + 1);
						System.out.println("표보내졌냐");
						try {
							cData.sendTarget(send);
						} catch (Exception e2) {
						}
						dispose();

					} else {
						showMsg( "투표를 고르지 않았습니다.");
					}
				}
			}
		};
		btnCancel.addActionListener(listener);
		btnOK.addActionListener(listener);
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

	private void showDlg() {
		setUndecorated(true);
		setLocationRelativeTo(owner);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void showDlg2() {
		setUndecorated(true);
		setLocation(700, 300);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private class MyListCellRenderer extends DefaultListCellRenderer { // 다오버라이드하기
		// 힘들기때문에
		// 상속받아서 사용
		@Override
		public Component getListCellRendererComponent( // 리스트의 갯수만큼 컴포넌트 호출
				// 보여지는 화면이 바뀔때마다 컴포넌트호출
				JList list, Object value, int idx, // 리스트,아이템들,
				// 각 인덱스,선택이되었는가,포커스받앗는가
				boolean isSelected, boolean cellHasfocus) {
			JLabel lblItem = new JLabel(value.toString());
			lblItem.setOpaque(true);
			if (isSelected) {
				lblItem.setBackground(Color.YELLOW);
				lblItem.setText(lblItem.getText() + "  (선택)");
			}
			return lblItem;

		}
	}

	private class MyListCellRenderer2 extends DefaultListCellRenderer { // 다오버라이드하기
		// 힘들기때문에
		// 상속받아서 사용
		@Override
		public Component getListCellRendererComponent( // 리스트의 갯수만큼 컴포넌트 호출
				// 보여지는 화면이 바뀔때마다 컴포넌트호출
				JList list, Object value, int idx, // 리스트,아이템들,
				// 각 인덱스,선택이되었는가,포커스받앗는가
				boolean isSelected, boolean cellHasfocus) {
			JLabel lblItem = new JLabel(value.toString());
			lblItem.setOpaque(true);
			if (isSelected) {
				lblItem.setBackground(Color.YELLOW);
				lblItem.setText(lblItem.getText() + "  (투표선택)");
			}
			return lblItem;

		}
	}
	public void showMsg(String str) {
		new MsgDiag(cData.getGui().checkOnGUI(), str);
	}

}