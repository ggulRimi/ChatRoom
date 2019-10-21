import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Wisper_Search_GUI extends JFrame {

	private JButton btn_Search;
	private JTextField tf;
	private JLabel lbl_Result;
	private JLabel lbl;
	private Vector<JPanel> UserList;
	
	private JScrollPane scroll;
	
	private TitledBorder tborder1;
	private TitledBorder tborder2;
	private TitledBorder tborder3;
	
	private int count = 1;
	
	
	public Wisper_Search_GUI() {
		init();
		setDisplay();
		showFrame();
	}

	private void init(){
		
		btn_Search = new JButton("검색");
		tf = new JTextField(8);
		lbl_Result = new JLabel("검색결과",JLabel.CENTER);
		
		UserList = new Vector<JPanel>();
		
		

		tborder1 = new TitledBorder(new LineBorder(Color.BLACK, 1),"");
		tborder2 = new TitledBorder(new LineBorder(Color.BLACK, 1),"");
		tborder3 = new TitledBorder(new LineBorder(Color.BLACK, 1),"");
		
	}

	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(tf);
		pnlNorth.add(btn_Search);
		pnlNorth.setBorder(tborder1);
		
		
		
		
		
		JPanel pnl1 = new JPanel(new GridLayout(0,1));
		pnl1.add(lbl_Result);
		lbl_Result.setBorder(tborder2);
		
		JPanel pnl2 = new JPanel(new GridLayout(0,1));
		scroll = new JScrollPane(pnl2,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		for(int i =0; i<5; i++){
			JPanel pnl_Wisper_User = new JPanel();
			pnl_Wisper_User.add(new JLabel("User" + count));
			pnl2.add(pnl_Wisper_User);
			UserList.add(pnl_Wisper_User);
			count++;
		}
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnl1,BorderLayout.NORTH);
		pnlCenter.add(scroll,BorderLayout.CENTER);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
	}

	private void showFrame() {
		setTitle("귓속말대상 검색");
		setSize(400, 300);
		setLocation(100, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Wisper_Search_GUI();
	}

}
