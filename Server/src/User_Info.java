import java.io.Serializable;


public class User_Info implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String nickName;
	private String phonenum;
	private String hobby;
	private String intro;
	
	public User_Info(String id, String name, String nickName,String phonenum ,String hobby,String intro) {
		this.id = id;
		this.name = name;
		this.nickName = nickName;
		this.phonenum = phonenum;
		this.hobby = hobby;
		this.intro = intro;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User_Info)) {
			return false;
		}
		User_Info user = (User_Info)o;
		return id.equals(user.getId());
	}
	
}
