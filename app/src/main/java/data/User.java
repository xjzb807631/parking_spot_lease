package data;

public class User extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public User(Account other)
	{
		super(other);
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String cellphone, String nickname,String realname,String identity_number,String wechat,String password)
	{
		this.cellphone=cellphone;
		this.password=password;
		this.nickname=nickname;
		this.identity_number=identity_number;
		this.realname=realname;
		this.wechat=wechat;
	}
	public String nickname;
	public String identity_number;
	public String realname;
	public String wechat;
}
