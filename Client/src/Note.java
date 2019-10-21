import java.io.Serializable;


public class Note implements Serializable{
	private static final long serialVersionUID = 5L;
	private String receive_id;
	private String post_Id;
	private String msg;
	private String title;
	//보낸 사람 받는 사람 구분 필요함.
	
	// 동훈 수정0625
	public Note(String post_Id,String receive_id ,String title, String msg){
		this.post_Id =post_Id;
		this.receive_id =receive_id;
		this.title =title;
		this.msg =msg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(String receive_id) {
		this.receive_id = receive_id;
	}
	public String getPost_Id() {
		return post_Id;
	}
	public void setPost_Id(String post_Id) {
		this.post_Id = post_Id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}

