
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

public class SlangListGUI extends JFrame implements iProtocol {
	private JTextField tfMany;
	private JButton btnMany;
	private JButton btnCancel;
	private JButton btnExit;

	private JButton btnDelete;
	private JPanel pnlSouth_result;

	private JList<String> list;
	private DefaultListModel<String> model;
	private Vector<String> trans_List;
	private ServerData sData;
	private Vector<String> slangList;
	private JLabel lblPut;

	private JLabel lblVoteClose;
	private Color bgColor;
	private Color OutColor;
	private Color bgGColor;
	private Font font;
	private Point ptFirst;

	private JPanel pnlTotal;
	private ServerManager_Form owner;

	public SlangListGUI(ServerData sData,ServerManager_Form owner) {
		this.sData = sData;
		this.owner =owner;
		init();
		setDisplay();
		addActionlisteners();
		showDlg();
	}
//	public SlangListGUI() {
//		init();
//		setDisplay();
//		addActionlisteners();
//		showDlg();
//	}

	private void init() {
		bgColor = new Color(0xFFFFFF);
		bgGColor = new Color(0xE59BB9);
		OutColor = new Color(0xFFD9EC);

		font = new Font("HY나무B", Font.PLAIN, 15);
		// FFEBFF// 엄청연한분홍
		// FFD9EC // 연한분홍
		// E59BB9 // 진한분홍
		model = new DefaultListModel<String>();
		for(String slang : sData.getFilter()){
			model.addElement(slang);
		}
		list = new JList<String>(model);
		list.setPrototypeCellValue("abcdefghijklmnopqrstuskdlfjskdlfjskldfj");
		list.setVisibleRowCount(10);
		list.setCellRenderer(new MyListCellRenderer());
		trans_List = new Vector<String>();
		tfMany = new JTextField(15);
		btnMany = new JButton("추가");
		setButton(btnMany);

		btnCancel = new JButton("닫기");
		setButton(btnCancel);
		btnDelete = new JButton("삭제");
		setButton(btnDelete);
		btnExit = new JButton(" X ");
		setButton(btnExit);

		lblPut = new JLabel("욕설 항목 추가");
		lblPut.setFont(font);


		pnlTotal = new JPanel(new BorderLayout());

	}

	private void setButton(JButton btn) {
		btn.setFont(font);
		btn.setPreferredSize(new Dimension(50, 25));
		btn.setForeground(Color.WHITE);
		btn.setBackground(bgGColor);
		btn.setBorder(new LineBorder(bgGColor, 1));
	}


	private void setDisplay() {

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(btnExit, BorderLayout.EAST);
		pnlNorth.setBackground(OutColor);
		pnlNorth.setBorder(new EmptyBorder(3, 3, 3, 3));

		JPanel pnlCenter_Top_Up = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnCancel);
		pnlSouth.setBackground(bgColor);

		pnlTotal.add(pnlNorth, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);

		pnlTotal.setBorder(new LineBorder(OutColor, 5));
		add(pnlTotal, BorderLayout.CENTER);
	}



	private void addActionlisteners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();

				if (src == btnCancel) {
					dispose();
				} else if (src == btnMany) {
					String slang_str = tfMany.getText();
					if(slang_str!=null && !slang_str.trim().equals("")){
						model.addElement(slang_str);
						sData.addFilter(slang_str);
						tfMany.setText("");
					}
				} else if (src == btnExit) {
					dispose();
				} else if (src == btnDelete) {
					int idx = list.getSelectedIndex();
					if (idx >= 0) {
						sData.removeFilter(model.get(idx));
						model.remove(idx);
					}
				}
			}
		};
		btnCancel.addActionListener(listener);
		btnMany.addActionListener(listener);
		btnDelete.addActionListener(listener);
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

}