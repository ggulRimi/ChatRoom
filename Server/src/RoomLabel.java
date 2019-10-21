import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;


public class RoomLabel extends JLabel {
	private Chat_Room room;
	
	public RoomLabel(Chat_Room room) {
		this.room = room;
		String str = "("+(1+room.getRoomNumber())+"번방)"+room.getTitle()+"\n";
		str += room.getUser_Count()+"/"+room.getMax_User();
		setText(str);
		setBorder(new LineBorder(Color.GRAY));
		setPreferredSize(new Dimension(200,30));
		addListeners();
	}
	private void addListeners() {
		MouseAdapter mAdapter = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String str = null;
				Iterator<String> itr = room.getRoom_User_List().keySet().iterator();
				while(itr.hasNext()){
					str += "\n"+itr.next();
				}
				JOptionPane.showMessageDialog(null, str, (room.getRoomNumber()+1)+"번방 유저정보", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		addMouseListener(mAdapter);
		
	}
	

}
