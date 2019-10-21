import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChoiceImoticon_GUI extends JDialog {
	private JLabel lblTitle = new JLabel("이모티콘을 선택하세용",JLabel.CENTER);
	private final String[] LOVEIMOARR = { "( ღ'ᴗ'ღ )", "◟( ˘ ³˘)◞ ♡", "(•ө•)♡",
			"(ฅ•ω•ฅ)♡", "( ்́ꇴ ்̀)♡", "ღ'ᴗ'ღ", "๑❤‿❤๑", "(๑˃̵ᴗ˂̵)و ♡",
			"꒰◍ˊ◡ˋ꒱੭ु⁾⁾♡", "(*ฅ́˘ฅ̀*)♡", "( ˇ͈ᵕˇ͈ ) ¨̮♡⃛", "٩(๑• ₃ -๑)۶♥",
			"(๑˃̵ᴗ˂̵)و ♡", "ʕ•ﻌ•ʔ ♡", "ღ˘‿˘ற꒱", "ෆ╹ .̮ ╹ෆ", "ლ|'ー'ლ|",
			"ლ( ╹ ◡ ╹ ლ)", "(✿◖◡​◗)❤" };
	private final String[] SMILEIMOARR = {"( ⁼̴̤̆◡̶͂⁼̴̤̆ )","(❁´▽`❁)","(❀╹◡╹)","(๑･̑◡･̑๑)","'◡'✿","✦‿✦","๑･̑◡･̑๑ ","๑❛◡ુ❛๑","๑❛◡ુ❛๑","◕‿◕✿","◕‿◕✿","๑◕‿‿◕๑"
			,"✪‿✪","✿˘◡˘✿","ღ˘‿˘ற꒱","(▰˘◡˘▰)","˘◡˘","✿˘◡˘✿","(๑′ᴗ‵๑)","(∗❛⌄❛∗)","(๑´◡ુ`๑)","p(´∇｀)q","๑•‿•๑","(*´ ワ `*)“","ξ(✿ ❛‿❛)ξ","(๑・‿・๑)"};
	private final String[] HAPPYIMOARR = {"♪(´ε｀*)","♪(*´θ｀)ノ","٩(ˊᗜˋ*)و","҉ ٩(๑>ω<๑)۶҉","o(*'▽'*)/☆ﾟ’ ","٩(๑>∀<๑)۶","( ๑˃̶ ꇴ ˂̶)♪⁺","✧*.◟(ˊᗨˋ)◞.*✧"
			,"⁽⁽◝( ˙ ꒳ ˙ )◜⁾⁾","( ˃⍨˂̥̥ )","(๑˃̵ᴗ˂̵)و ","ᕕ( ᐛ )ᕗ","(๑╹∀╹๑)"};

	private JList<String> imoList_love;
	private JList<String> imoList_Smile;
	private JList<String> imoList_Happy;
	private JButton btn_Ok;
	private JButton btn_Close;
	private JButton btn_Exit ;
	
	private JTabbedPane tbp;
	private ChatRoom_GUI owner;
	private Color bgColor = new Color(0xFFFFFF); // 하얀색
	private	Color btnColor = new Color(0xE59BB9); //진한분홍
	private Color bgGColor = new Color(0xFFEBFF); //엄청연한분홍
	private Color edgeColor = new Color(0xFFD9EC); // 연한분홍
	private Font font = new Font("HY나무B", Font.PLAIN, 15);
/*
 * 
 * 연한분홍색이 테두리
진한분홍색이 버튼
배경은 하얀색
구분하고싶은 배경은 엄청연한분홍색으로 작업!
*/
	public ChoiceImoticon_GUI(ChatRoom_GUI owner) {
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showDlg();
	}
	public ChoiceImoticon_GUI() {
		init();
		setDisplay();
		addListeners();
		showDlg();
	}

	public void init() {
		tbp = new JTabbedPane();
		tbp.setBackground(bgGColor);
		tbp.setFont(font);
		tbp.setPreferredSize(new Dimension(255,200));
		imoList_love = new JList<String>(LOVEIMOARR);
		imoList_love.setVisibleRowCount(10);
		imoList_love.setCellRenderer(new HeartListCellRenderer());
		imoList_love
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		imoList_love.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXxxxxXxx");
		
		imoList_Smile = new JList<String>(SMILEIMOARR);
		imoList_Smile.setVisibleRowCount(10);
		imoList_Smile.setCellRenderer(new SmileListCellRenderer());
		imoList_Smile
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		imoList_Smile.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXxxxxXxx");
		
		imoList_Happy = new JList<String>(HAPPYIMOARR);
		imoList_Happy.setVisibleRowCount(10);
		imoList_Happy.setCellRenderer(new HappyListCellRenderer());
		imoList_Happy.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		imoList_Happy.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXXXxxxxXxx");
		
		btn_Ok = new JButton("확인");
		btn_Ok.setFont(font);
		btn_Ok.setForeground(Color.WHITE); // 해림수정0702
		btn_Ok.setBackground(btnColor); // 해림수정0702
		btn_Ok.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정0702
		btn_Ok.setPreferredSize(new Dimension(70, 30));// 해림수정0702

		btn_Close = new JButton("닫기");
		btn_Close.setFont(font);
		btn_Close.setForeground(Color.WHITE); // 해림수정0702
		btn_Close.setBackground(btnColor); // 해림수정0702
		btn_Close.setBorder(new LineBorder(Color.WHITE, 1));// 해림수정0702
		btn_Close.setPreferredSize(new Dimension(70, 30));// 해림수정0702
		
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 25));
		btn_Exit.setBorder(new LineBorder(Color.WHITE, 1));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);

	}

	public void setDisplay() {
		JScrollPane scroll_Love = setting_JScroll(imoList_love);
		JScrollPane scroll_Smile = setting_JScroll(imoList_Smile);
		JScrollPane scroll_Happy = setting_JScroll(imoList_Happy);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setBackground(bgColor);
		lblTitle.setBackground(bgColor);
		lblTitle.setFont(font);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("1.png");
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon newimg = new ImageIcon(img);
		JLabel lblTitle_l = new JLabel();
		lblTitle_l.setIcon(newimg);
		JLabel lblTitle_r = new JLabel();
		lblTitle_r.setIcon(newimg);
		JPanel pnlSouth_Bottom = new JPanel();
		pnlSouth_Bottom.setBackground(bgColor);
		pnlSouth_Bottom.add(lblTitle_l);
		pnlSouth_Bottom.add(lblTitle);
		pnlSouth_Bottom.add(lblTitle_r);
		pnlNorth.add(pnlSouth_Bottom,BorderLayout.SOUTH);
		pnlNorth.add(btn_Exit,BorderLayout.EAST);
		
		// ///////////////////////////////////////////////////////////
		JPanel pnlCenter = new JPanel();
		JPanel pnlCenter_Love = new JPanel();
		pnlCenter_Love.add(scroll_Love);
		pnlCenter_Love.setBackground(bgColor);
		JPanel pnlCenter_Smile = new JPanel();
		pnlCenter_Smile.add(scroll_Smile);
		pnlCenter_Smile.setBackground(bgColor);
		JPanel pnlCenter_Happy = new JPanel();
		pnlCenter_Happy.add(scroll_Happy);
		pnlCenter_Smile.setBackground(bgColor);
		
		tbp.add("러브티콘", pnlCenter_Love);
		tbp.add("스마일티콘", pnlCenter_Smile);
		tbp.add("해피티콘", pnlCenter_Happy);

		pnlCenter.add(tbp);
		pnlCenter.setBackground(bgColor);
		// ///////////////////////////////////////////////////////////
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn_Ok);
		pnlSouth.add(btn_Close);
		pnlSouth.setBackground(bgColor);
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(new LineBorder(btnColor,2));
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlMain);
	}

	public void addListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if (src == btn_Ok) {
					boolean flag = true;
					int i = 0;
					int j =0;
					int k=0;
					if (tbp.getSelectedIndex() == 0) {
						while (flag) {
							if (imoList_love.isSelectedIndex(i)) {
								String item = LOVEIMOARR[i];
								owner.getTfChat().setText(owner.getTfChat().getText()+item);
								i++;
							} else {
								i++;
							}
							if (i == LOVEIMOARR.length) {
								flag = false;
							}
						}
					}else if(tbp.getSelectedIndex() == 1){
						while (flag) {
							if (imoList_Smile.isSelectedIndex(j)) {
								String item = SMILEIMOARR[j];
								owner.getTfChat().setText(owner.getTfChat().getText()+item);
								j ++;
							} else {
								j ++;
							}
							if (j == SMILEIMOARR.length) {
								flag = false;
							}
						}
					}else if(tbp.getSelectedIndex() == 2){
						while (flag) {
							if (imoList_Happy.isSelectedIndex(k)) {
								String item = HAPPYIMOARR[k];
								owner.getTfChat().setText(owner.getTfChat().getText()+item);
								k ++;
							} else {
								k ++;
							}
							if (k == HAPPYIMOARR.length) {
								flag = false;
							}
						}
					}
					dispose();
				} else if (src == btn_Close || src == btn_Exit) {
					dispose();
				}
			}
		};
		btn_Ok.addActionListener(listener);
		btn_Close.addActionListener(listener);
		btn_Exit.addActionListener(listener);
		
	}

	public void showDlg() {
		setSize(300, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true); //해림 수정 0701 창 프레임 없앰
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public JScrollPane setting_JScroll(JList list) {
		JScrollPane scrollPeople = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPeople.getVerticalScrollBar().setUnitIncrement(4);
		scrollPeople.setBorder(new EmptyBorder(0, 0, 0, 0));
		// scrollPeople.setPreferredSize(new Dimension(x, y));//250 605
		scrollPeople
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPeople;
	}

	private class HeartListCellRenderer extends DefaultListCellRenderer { // 다오버라이드하기
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
			lblItem.setBackground(bgColor);
			if (isSelected) {
				lblItem.setBackground(Color.PINK);
				lblItem.setText(lblItem.getText() + "    (선택)");
			}
			return lblItem;
		}
	}

	private class SmileListCellRenderer extends DefaultListCellRenderer { // 다오버라이드하기
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
			lblItem.setBackground(bgColor);
			
			if (isSelected) {
				lblItem.setBackground(Color.YELLOW);
				lblItem.setText(lblItem.getText() + "    (선택)");
			}
			return lblItem;
		}
	}
	private class HappyListCellRenderer extends DefaultListCellRenderer { // 다오버라이드하기
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
			lblItem.setBackground(bgColor);
			if (isSelected) {
				lblItem.setBackground(Color.BLUE);
				lblItem.setText(lblItem.getText() + "    (선택)");
			}
			return lblItem;
		}
	}
	public static void main(String[] args) {
		new ChoiceImoticon_GUI();
	}
}
