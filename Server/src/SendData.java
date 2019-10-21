import java.io.Serializable;


public class SendData implements Serializable {
	private static final long serialVersionUID = 1L;
	private int protocol;
	private Object obj1;
	private Object obj2;
	private Object obj3;
	private Object obj4;
	private Object obj5;
	
	public Object getObj5() {
		return obj5;
	}
	public void setObj5(Object obj5) {
		this.obj5 = obj5;
	}
	public SendData(int protocol) {
		this.protocol = protocol;
	}
	public Object getObj4() {
		return obj4;
	}
	public void setObj4(Object obj4) {
		this.obj4 = obj4;
	}
	public SendData(int protocol, Object obj1) {
		this.protocol = protocol;
		this.obj1 = obj1;
	}
	public SendData(int protocol, Object obj1, Object obj2) {
		this.protocol = protocol;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	public SendData(int protocol, Object obj1, Object obj2,Object obj3) {
		this.protocol = protocol;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.obj3 = obj3;
	}
	public SendData(int protocol, Object obj1, Object obj2,Object obj3,Object obj4) {
		this.protocol = protocol;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.obj3 = obj3;
		this.obj4 = obj4;
	}
	public SendData(int protocol, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5) {
		this.protocol = protocol;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.obj3 = obj3;
		this.obj4 = obj4;
		this.obj5 = obj5;
	}
	public Object getObj3() {
		return obj3;
	}
	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public Object getObj1() {
		return obj1;
	}
	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}
	public Object getObj2() {
		return obj2;
	}
	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}
	
}