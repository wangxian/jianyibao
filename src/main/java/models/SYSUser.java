package models;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class SYSUser extends Model<SYSUser> {
	public static final SYSUser dao = new SYSUser();
	
	/**
	 * 使用用户名查询用户信息
	 * @param name
	 * @return
	 */
	public SYSUser getByName(String name) {
		return this.findFirst("select * from admin_sysuser where username=?", name);
	}
}
