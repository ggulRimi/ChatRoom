import java.io.Serializable;


public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String msg;
	private long time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
