import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class ServerManager_Form extends JFrame {
	private JLabel lbl_Room_Status;
	private Vector<JLabel> lbl_Room_Number;
	private Vector<JLabel> lbl_Room_Title;
	private JLabel lbl_Slang_Status;
	private Vector<JLabel> lbl_Slang_Number;
	private Vector<JLabel> lbl_Slang_Title;
	private JLabel lbl_Total_User;
	private JLabel lbl_Ban_User;
	private Vector<JLabel> lbl_Total_User_List;
	private Vector<JLabel> lbl_Ban_User_List;
	private JLabel lbl_Total_User_Search;
	private JLabel lbl_Ban_User_Search;
	private JTextField tf_Total_User_Search;
	private JTextField tf_Ban_User_Search;
	private JButton btn_Total_User_Search;
	private JButton btn_Ban_User_Search;
	private JButton btn_Server_Close;
	private JButton btn_Slang_Count_Setting;

	private JPanel pnlRoom;
	private JPanel pnlSlang;
	private Vector<JPanel> pnl_Total_User_Vector;
	private Vector<JPanel> pnl_Ban_User_Vector;
	private ServerData sd;

	private JPanel pnl_Total_User_List;
	private JPanel pnlRoom_List;
	private JPanel pnlSlang_List;
	private JPanel pnl_Ban_User_List;

	private JList<String> list;
	private DefaultListModel<String> model;

	public ServerManager_Form(ServerData sd) {
		this.sd = sd;
		init();
		setDisplay();
		addListeners();
		setFrame();
	}

	public void init() {
		lbl_Room_Status = new JLabel("개설된 방 현황");
		lbl_Room_Number = new Vector<JLabel>();
		lbl_Room_Title = new Vector<JLabel>();

		lbl_Slang_Status = new JLabel("욕설 count");
		btn_Slang_Count_Setting = new JButton("욕설 추가/삭제");
		btn_Slang_Count_Setting.setPreferredSize(new Dimension(120, 20));
		lbl_Slang_Number = new Vector<JLabel>();
		lbl_Slang_Title = new Vector<JLabel>();

		btn_Server_Close = new JButton("서버 종료");
		lbl_Total_User = new JLabel("전체 유저 리스트");
		lbl_Ban_User = new JLabel("벤 유저 리스트");

		lbl_Total_User_Search = new JLabel("검색");
		lbl_Ban_User_Search = new JLabel("검색");

		btn_Total_User_Search = new JButton("검색하기");
		btn_Ban_User_Search = new JButton("검색하기");
		tf_Total_User_Search = new JTextField(10);
		tf_Ban_User_Search = new JTextField(10);
		pnlRoom = new JPanel(new BorderLayout());
		pnlSlang = new JPanel(new BorderLayout());
		pnl_Total_User_Vector = new Vector<JPanel>();
		pnl_Ban_User_Vector = new Vector<JPanel>();

	}

	public void updateSlang() {
		pnlSlang_List.removeAll();
		int size = sd.getUser_Map().size();
		java.util.Iterator<String> itr = sd.getUser_Map().keySet().iterator();
		while (itr.hasNext()) {
			User user = sd.getUser_Map().get(itr.next());
			if (user.getSlangCount() > 0) {
				UserLabel slangLbl = new UserLabel(sd, user);
				slangLbl.setSlang();
				pnlSlang_List.add(slangLbl);
			}
		}
		pnlSlang_List.invalidate();
		pnlSlang_List.repaint();
		pnlSlang_List.updateUI();
		pnlSlang.updateUI();
	}

	public void updateRoomList() {
		pnlRoom_List.removeAll();
		Iterator<Integer> itr = sd.getChat_Room_List().keySet().iterator();
		while (itr.hasNext()) {
			Chat_Room room = sd.getChat_Room_List().get(itr.next());
			String roomName = room.getTitle();
			pnlRoom_List.add(new RoomLabel(room));
		}
		pnlRoom_List.invalidate();
		pnlRoom_List.repaint();
		pnlRoom_List.updateUI();
		pnlRoom.updateUI();
	}

	public void updateBanUser() {
		pnl_Ban_User_List.removeAll();
		pnl_Ban_User_Vector.clear();
		int size = sd.getUser_Map().size();
		java.util.Iterator<String> itr = sd.getUser_Map().keySet().iterator();
		while (itr.hasNext()) {
			User user = sd.getUser_Map().get(itr.next());
			if (user.isBan()) {
				JPanel pnl_Ban_User = new JPanel();
				pnl_Ban_User.add(new UserPanel(sd, user));
				pnl_Ban_User_List.add(pnl_Ban_User);
				pnl_Ban_User_Vector.add(pnl_Ban_User);
			}
		}
		pnl_Ban_User_List.invalidate();
		pnl_Ban_User_List.repaint();
		pnl_Ban_User_List.updateUI();
	}

	public void updateUserList() {
		pnl_Total_User_Vector.clear();
		pnl_Total_User_List.removeAll();

		int size = sd.getUser_Map().size();
		java.util.Iterator<String> itr = sd.getUser_Map().keySet().iterator();
		while (itr.hasNext()) {
			User user = sd.getUser_Map().get(itr.next());
			JPanel pnl_Total_User = new JPanel();
			pnl_Total_User.add(new UserPanel(sd, user));
			pnl_Total_User_List.add(pnl_Total_User);
		}
		pnl_Total_User_List.invalidate();
		pnl_Total_User_List.repaint();
		pnl_Total_User_List.updateUI();
	}

	public void setDisplay() {
		// /////////////////////////////////////////////////룸룸룸
		JPanel pnlWest = new JPanel(new BorderLayout());
		JPanel pnlWest_Main = new JPanel(new BorderLayout());
		pnlRoom_List = new JPanel(new GridLayout(0, 1));
		JScrollPane scroll_Room = new JScrollPane(pnlRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_Room.getVerticalScrollBar().setUnitIncrement(4);
		scroll_Room.setBorder(new EmptyBorder(0, 0, 0, 0));
		scroll_Room.setPreferredSize(new Dimension(200, 200));// 250 605
		scroll_Room.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlRoom_List.add(lbl_Room_Status);
		Iterator<Integer> itr1 = sd.getChat_Room_List().keySet().iterator();
		while (itr1.hasNext()) {
			Chat_Room room = sd.getChat_Room_List().get(itr1.next());
			String roomName = room.getTitle();
			pnlRoom_List.add(new RoomLabel(room));
		}
		pnlRoom.add(pnlRoom_List, BorderLayout.NORTH);
		// ////////////////////////////////////////////////////////////슬레그슬레그

		pnlSlang_List = new JPanel(new GridLayout(0, 1));
		JScrollPane scroll_Slang = new JScrollPane(pnlSlang, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_Slang.getVerticalScrollBar().setUnitIncrement(4);
		scroll_Slang.setBorder(new EmptyBorder(0, 0, 0, 0));
		scroll_Slang.setPreferredSize(new Dimension(200, 200));// 250 605
		scroll_Slang.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		int size = sd.getUser_Map().size();
		java.util.Iterator<String> itr = sd.getUser_Map().keySet().iterator();
		while (itr.hasNext()) {
			User user = sd.getUser_Map().get(itr.next());
			if (user.getSlangCount() > 0) {
				JPanel pnlSlang_Index = new JPanel();
				UserLabel slangLbl = new UserLabel(sd, user);
				slangLbl.setSlang();
				pnlSlang_Index.add(slangLbl);
				pnlSlang_Index.setPreferredSize(new Dimension(100, 10));
				pnlSlang_List.add(pnlSlang_Index);
			}
		}
		pnlSlang.add(pnlSlang_List, BorderLayout.NORTH);
		// //////////////////////////////////채팅방 , 욕설 유저 추가
		// /////////////////////////
		JPanel pnl_Room_Top = new JPanel(new BorderLayout());
		pnl_Room_Top.add(lbl_Room_Status, BorderLayout.NORTH);
		pnl_Room_Top.add(scroll_Room, BorderLayout.CENTER);

		JPanel pnl_Slang_Top = new JPanel(new BorderLayout());
		JPanel pnl_Slang_Top_Count = new JPanel();
		pnl_Slang_Top_Count.add(lbl_Slang_Status);
		pnl_Slang_Top_Count.add(btn_Slang_Count_Setting);
		pnl_Slang_Top.add(pnl_Slang_Top_Count, BorderLayout.NORTH);
		pnl_Slang_Top.add(scroll_Slang, BorderLayout.CENTER);

		pnlWest_Main.add(pnl_Room_Top, BorderLayout.NORTH);
		pnlWest_Main.add(pnl_Slang_Top, BorderLayout.SOUTH);

		pnlWest.add(pnlWest_Main, BorderLayout.CENTER);
		pnlWest.add(btn_Server_Close, BorderLayout.SOUTH);

		add(pnlWest, BorderLayout.WEST);

		JPanel pnlEast = new JPanel(new BorderLayout());
		// pnlEast.setPreferredSize(new Dimension(800,1000));
		JPanel pnlEast_Main = new JPanel(new GridLayout(1, 2));//

		// //////////////////////////////////전체유저 추가/////////////////////////
		pnl_Total_User_List = new JPanel();
		updateUserList();// ////////////////
		pnl_Total_User_List.setPreferredSize(new Dimension(200, 500));

		JPanel pnl_total_t = new JPanel(new BorderLayout());
		pnl_total_t.add(pnl_Total_User_List, BorderLayout.NORTH);
		JScrollPane scroll_Total = new JScrollPane(pnl_total_t, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel pnl_Total_North = new JPanel(new BorderLayout());
		pnl_Total_North.add(lbl_Total_User, BorderLayout.NORTH);
		JPanel pnl_Total_North_Center = new JPanel();

		pnl_Total_North_Center.add(lbl_Total_User_Search);
		pnl_Total_North_Center.add(tf_Total_User_Search);
		pnl_Total_North_Center.add(btn_Total_User_Search);

		pnl_Total_North.add(pnl_Total_North_Center, BorderLayout.CENTER);

		JPanel pnl_Total_Top = new JPanel(new BorderLayout());
		pnl_Total_Top.add(pnl_Total_North, BorderLayout.NORTH);

		JPanel pnl_Total_Search = new JPanel();
		pnl_Total_Search.add(new JLabel("이름              "));
		pnl_Total_Search.add(new JLabel("닉네임     "));
		pnl_Total_Search.add(new JLabel("         상태"));
		//

		pnl_Total_Top.add(pnl_Total_Search, BorderLayout.CENTER);
		JPanel pnl_Total = new JPanel(new BorderLayout());
		pnl_Total.add(pnl_Total_Top, BorderLayout.NORTH);
		pnl_Total.add(scroll_Total, BorderLayout.CENTER);
		// //////////////////////////////////벤유저 추가/////////////////////////

		pnl_Ban_User_List = new JPanel();
		updateBanUser();
		pnl_Ban_User_List.setPreferredSize(new Dimension(200, 500));

		JPanel pnl_Ban_b = new JPanel(new BorderLayout());
		pnl_Ban_b.add(pnl_Ban_User_List, BorderLayout.NORTH);

		// 어제 박상욱 추가
		JScrollPane scroll_Ban = new JScrollPane(pnl_Ban_b, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel pnl_Ban_North = new JPanel(new BorderLayout());
		pnl_Ban_North.add(lbl_Ban_User, BorderLayout.NORTH);
		JPanel pnl_Ban_North_Center = new JPanel();

		pnl_Ban_North_Center.add(lbl_Ban_User_Search);
		pnl_Ban_North_Center.add(tf_Ban_User_Search);
		pnl_Ban_North_Center.add(btn_Ban_User_Search);
		//
		pnl_Ban_North.add(pnl_Ban_North_Center, BorderLayout.CENTER);
		JPanel pnl_Ban_Top = new JPanel(new BorderLayout());
		pnl_Ban_Top.add(pnl_Ban_North, BorderLayout.NORTH);

		JPanel pnl_Ban_Search = new JPanel();

		pnl_Ban_Search.add(new JLabel("이름              "));
		pnl_Ban_Search.add(new JLabel("닉네임     "));
		pnl_Ban_Search.add(new JLabel("         상태"));
		pnl_Ban_Top.add(pnl_Ban_Search, BorderLayout.CENTER);
		JPanel pnl_Ban = new JPanel(new BorderLayout());
		pnl_Ban.add(pnl_Ban_Top, BorderLayout.NORTH);
		pnl_Ban.add(scroll_Ban, BorderLayout.CENTER);

		pnlEast_Main.add(pnl_Total);
		pnlEast_Main.add(pnl_Ban);
		pnlEast.add(pnlEast_Main);

		add(pnlEast, BorderLayout.CENTER);
	}

	public void addListeners() {
		WindowAdapter wAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				sd.SaveServerData();
			}

		};
		addWindowListener(wAdapter);
		ActionListener aListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btn_Server_Close) {
					sd.SaveServerData();
					System.exit(0);
				} else if (src == btn_Slang_Count_Setting) {
					new SlangListGUI(sd, ServerManager_Form.this);
				}
			}
		};
		btn_Server_Close.addActionListener(aListener);
		btn_Slang_Count_Setting.addActionListener(aListener);
	}

	public void setFrame() {
		setTitle("Server");
		setSize(1100, 500);
		setResizable(false);
		// pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ServerManager_Form(null);
	}

}
