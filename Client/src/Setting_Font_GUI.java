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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Setting_Font_GUI extends JFrame{
	
	private JLabel lbl_Text;
	private JLabel lbl_MyColor;
	private JLabel lbl_OtherColor;
	private JLabel lbl_Size;
	private JLabel lbl_Style;
	private JButton btn_OK;
	private JButton btn_Reset;
	private JButton btn_Cencel;
	
	private String font_Text[] = {"HY나무B","굴림","궁서","serif","sans-serif","Arial"};
	private String font_style[] = {"PLAIN","BOLD","ITALIC"};
	private String my_Color_arr[] = {"검정색", "빨간색", "주황색", "초록색", "파랑색", "핑크색","흰색"};
	private String other_Color_arr[] = {"검정색", "빨간색", "주황색", "초록색", "파랑색", "핑크색","흰색"};
	
	private JComboBox<String> combo_Text;
	private JComboBox<String> combo_Style;
	private JComboBox<String> combo_MyColor;
	private JComboBox<String> combo_OtherColor;
	private JTextField tf;
	private JLabel lbl_MyshowFont;
	private JLabel lbl_OthershowFont;
	private JSlider slider;
	private Font font;
	private Color color;
	private Color myColor;
	private Color otherColor;
	private ClientData cData;
	//해림수정0705
	private Point ptFirst; 
	private Color bgColor= new Color(0xFFFFFF);
	private Color btnColor = new Color(0xE59BB9);
	private Color edgeColor = new Color(0xFFD9EC); 
	private JButton btn_Exit;
	private JLabel lblTopTitle;

	
	public Setting_Font_GUI(ClientData cData){
		this.cData =cData;
		init();
		setDisplay();
		addActionListeners();
		showFrame();
	}
	public Setting_Font_GUI(){
		init();
		setDisplay();
		addActionListeners();
		showFrame();
	}
	private void init(){
		//해림수정0705
		Font font = new Font("HY나무B", Font.PLAIN, 15);
		Font myfont = cData.getFont();
		
		lbl_Text = new JLabel("글꼴",JLabel.CENTER);
		lbl_Text.setFont(font);
		lbl_MyColor = new JLabel("나의 채팅 폰트 색상",JLabel.CENTER);
		lbl_MyColor.setFont(font);
		lbl_OtherColor = new JLabel("다른 채팅 폰트 색상",JLabel.CENTER);
		lbl_OtherColor.setFont(font);
		lbl_Size = new JLabel("크기",JLabel.CENTER);
		lbl_Size.setFont(font);
		lbl_Style = new JLabel("스타일",JLabel.CENTER);
		lbl_Style.setFont(font);

		btn_OK = new JButton("확인");
		btn_OK.setFont(font);
		btn_OK.setForeground(Color.WHITE); //해림수정0705
		btn_OK.setBorderPainted(false); //해림수정0705
		btn_OK.setBackground(btnColor); //해림수정0705
		btn_OK.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		btn_OK.setPreferredSize(new Dimension(70, 30));//해림수정0705
		
		btn_Reset = new JButton("초기화");
		btn_Reset.setFont(font);
		btn_Reset.setForeground(Color.WHITE); //해림수정0705
		btn_Reset.setBorderPainted(false); //해림수정0705
		btn_Reset.setBackground(btnColor); //해림수정0705
		btn_Reset.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		btn_Reset.setPreferredSize(new Dimension(70, 30));//해림수정0705
		
		btn_Cencel = new JButton("취소");
		btn_Cencel.setFont(font);
		btn_Cencel.setForeground(Color.WHITE); //해림수정0705
		btn_Cencel.setBorderPainted(false); //해림수정0705
		btn_Cencel.setBackground(btnColor); //해림수정0705
		btn_Cencel.setBorder(new LineBorder(bgColor, 1));//해림수정0705
		btn_Cencel.setPreferredSize(new Dimension(70, 30));//해림수정0705
		
		//해림수정 0702
		btn_Exit = new JButton("X");
		btn_Exit.setPreferredSize(new Dimension(50, 30));
		btn_Exit.setForeground(Color.WHITE);
		btn_Exit.setBackground(btnColor);
		btn_Exit.setBorder(new LineBorder(bgColor, 1));//해림수정0704
		
		lblTopTitle = new JLabel(" 폰트 변경", JLabel.LEFT);
		lblTopTitle.setFont(font);
		
		combo_Text = new JComboBox<String>(font_Text);
		for(int i =0;i<font_Text.length;i++){
			if (font_Text[i].equals(myfont.getFontName())){
				combo_Text.setSelectedIndex(i);
			}
		}
		combo_Text.setFont(font);
		combo_Style = new JComboBox<String>(font_style);
		for(int i =0;i<font_style.length;i++){
			if (selectFontStyle(font_style[i]) == myfont.getStyle()){
				combo_Style.setSelectedIndex(i);
			}
		}
		combo_Style.setFont(font);
		
		combo_MyColor = new JComboBox<String>(my_Color_arr);
		
		for(int i =0;i<my_Color_arr.length;i++){
			if (selectColor(my_Color_arr[i]) == cData.getMyColor()){
				combo_MyColor.setSelectedIndex(i);
			}
		}
		combo_MyColor.setFont(font);
		combo_OtherColor  = new JComboBox<String>(other_Color_arr);
		
		for(int i =0;i<other_Color_arr.length;i++){
			if (selectColor(other_Color_arr[i]) == cData.getOtherColor()){
				combo_OtherColor.setSelectedIndex(i);
			}
		}
		combo_OtherColor.setFont(font);
		
		tf = new JTextField(20);
		tf.setBorder(new LineBorder(btnColor, 2));//해림수정0705


		slider = new JSlider(JSlider.HORIZONTAL,0,50,10);
		slider.setValue(myfont.getSize());
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(bgColor);
		
		lbl_MyshowFont = new JLabel("My Font"+slider.getValue());
		lbl_MyshowFont.setPreferredSize(new Dimension(300,40));
		lbl_MyshowFont.setFont(myfont);
		lbl_MyshowFont.setForeground(cData.getMyColor());
		
		lbl_OthershowFont = new JLabel("Other Font"+slider.getValue());
		lbl_OthershowFont.setPreferredSize(new Dimension(300,40));
		lbl_OthershowFont.setFont(myfont);
		lbl_OthershowFont.setForeground(cData.getOtherColor());
		
	}
	private void setDisplay(){
		JPanel pnlNorth = new JPanel(new GridLayout(2,1));
		pnlNorth.add(lbl_MyshowFont);
		pnlNorth.add(lbl_OthershowFont);
		pnlNorth.setBorder(new EmptyBorder(5,15,5,0));
		pnlNorth.setBackground(bgColor);
		pnlNorth.setBorder(new LineBorder(btnColor,2));
		JPanel pnlNorthTopTitle = new JPanel(new BorderLayout());
		pnlNorthTopTitle.add(btn_Exit, BorderLayout.EAST); //해림수정0702
		pnlNorthTopTitle.setBackground(edgeColor);//해림수정0702
		pnlNorthTopTitle.add(lblTopTitle, BorderLayout.CENTER);
		pnlNorthTopTitle.add(pnlNorth, BorderLayout.SOUTH);
		JPanel pnlCenter_Top = new JPanel(new GridLayout(4,2));
		pnlCenter_Top.add(lbl_Text);
		pnlCenter_Top.add(combo_Text);
		pnlCenter_Top.add(lbl_Style);
		pnlCenter_Top.add(combo_Style);
		pnlCenter_Top.add(lbl_MyColor);
		pnlCenter_Top.add(combo_MyColor);
		pnlCenter_Top.add(lbl_OtherColor);
		pnlCenter_Top.add(combo_OtherColor);
		pnlCenter_Top.setBackground(bgColor);
		pnlCenter_Top.setBorder(new EmptyBorder(10,5,10,5));
		JPanel pnlCenter_Bottom = new JPanel(new BorderLayout());
		pnlCenter_Bottom.setBackground(bgColor);
		pnlCenter_Bottom.add(lbl_Size,BorderLayout.NORTH);
		pnlCenter_Bottom.add(slider,BorderLayout.CENTER);
		pnlCenter_Bottom.setBorder(new EmptyBorder(10,5,10,5));
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btn_OK);
		pnlSouth.add(btn_Reset);
		pnlSouth.add(btn_Cencel);
		pnlSouth.setBackground(bgColor);
		pnlSouth.setBorder(new EmptyBorder(5,5,5,5));
		JPanel pnlCenter =new JPanel(new BorderLayout());
		pnlCenter.setBackground(bgColor);
		pnlCenter.add(pnlNorthTopTitle,BorderLayout.NORTH);
		pnlCenter.add(pnlCenter_Top,BorderLayout.CENTER);
		pnlCenter.add(pnlCenter_Bottom,BorderLayout.SOUTH);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		pnlTotal.add(pnlSouth, BorderLayout.SOUTH);
		
		pnlTotal.setBorder(new LineBorder(edgeColor, 6));
		add(pnlTotal, BorderLayout.CENTER);
	}
	private void resetFont(){
		combo_Text.setSelectedIndex(0);
		combo_Style.setSelectedIndex(0);
		combo_MyColor.setSelectedIndex(0);
		combo_OtherColor.setSelectedIndex(0);
		slider.setValue(15);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		font = new Font((String) "HY나무B",font.PLAIN,15);
		lbl_MyshowFont.setFont(font);
		lbl_MyshowFont.setText("My Font "+slider.getValue());

		lbl_OthershowFont.setFont(font);
		lbl_OthershowFont.setText("Other Font "+slider.getValue());
		lbl_MyshowFont.setForeground(color.black);
		lbl_OthershowFont.setForeground(color.black);
	}
	private void changeFont(){
		String my_Stytle = (String)combo_Style.getSelectedItem();
		font = new Font((String) combo_Text.getSelectedItem(),selectFontStyle(my_Stytle),slider.getValue());
		lbl_MyshowFont.setText("My Font "+slider.getValue());
		lbl_MyshowFont.setFont(font);
		lbl_OthershowFont.setText("Other Font "+slider.getValue());
		lbl_OthershowFont.setFont(font);
		
		String my_Color = (String)combo_MyColor.getSelectedItem();
		String other_Color = (String)combo_OtherColor.getSelectedItem();
		
		lbl_MyshowFont.setForeground(selectColor(my_Color));
		lbl_OthershowFont.setForeground(selectColor(other_Color));
	}
	private int selectFontStyle(String sel_FontStyle){
		int myFontStyle = 0;
		if (sel_FontStyle.equals("PLAIN")){
			myFontStyle =Font.PLAIN;
		}else if(sel_FontStyle.equals("BOLD")){
			myFontStyle =Font.BOLD;
		}else if(sel_FontStyle.equals("ITALIC")){
			myFontStyle =Font.ITALIC;
		}
		return myFontStyle;
	}
	private Color selectColor(String sel_Color){
		Color myColor = null;
		if (sel_Color.equals("검정색")){
			myColor = color.BLACK;
		}else if(sel_Color.equals("빨간색")){
			myColor = color.RED;
		}else if(sel_Color.equals("주황색")){
			myColor = color.ORANGE;
		}else if(sel_Color.equals("노랑색")){
			myColor = color.YELLOW;
		}else if(sel_Color.equals("초록색")){
			myColor = color.GREEN;
		}else if(sel_Color.equals("파랑색")){
			myColor = color.BLUE;
		}else if(sel_Color.equals("보라색")){
			myColor =new Color(0x483D8B);
		}else if(sel_Color.equals("흰색")){
			myColor = color.WHITE;
		}else if(sel_Color.equals("핑크색")){
			myColor = color.PINK;
		}
		return myColor;
	}
	private void addActionListeners(){
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				changeFont();
				
			}
		});
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if ((src == combo_Text) || (src == combo_Style) || (src == combo_MyColor) ||(src == combo_OtherColor)){
					changeFont();
				}else if(src == btn_OK){
					String my_Color = (String)combo_MyColor.getSelectedItem();
					String other_Color = (String)combo_OtherColor.getSelectedItem();
					cData.setMyColor(selectColor(my_Color));
					cData.setOtherColor(selectColor(other_Color));
					String fontStyle = (String)combo_Style.getSelectedItem();
					font = new Font((String) combo_Text.getSelectedItem(),selectFontStyle(fontStyle),slider.getValue());
					cData.setFont(font);
					cData.change_Font();
					dispose();
				}else if(src ==btn_Reset){
					resetFont();
				}else if(src== btn_Cencel || src == btn_Exit){
					dispose();
				}
				
			}
		};
		combo_Text.addActionListener(listener);
		combo_Style.addActionListener(listener);
		combo_MyColor.addActionListener(listener);
		combo_OtherColor.addActionListener(listener);
		btn_OK.addActionListener(listener);
		btn_Reset.addActionListener(listener);
		btn_Cencel.addActionListener(listener);
		btn_Exit.addActionListener(listener);
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
	
	
	private void showFrame(){
		setSize(360, 430);
		setResizable(false);
		setUndecorated(true);
		setLocation(100,0);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}