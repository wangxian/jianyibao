package models;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class SYSRights extends Model<SYSRights> {
	public static final SYSRights dao = new SYSRights();
	
	/**
	 * 根据group_id获取权限
	 * @param id
	 * @return
	 */
	public List<SYSRights> getByGroupId(int id) {
		return this.find("select * from admin_sysrights where group_id=?", id);
	}
}
