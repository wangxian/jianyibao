package controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.SYSGroup;
import models.SYSRights;
import models.SYSUser;
import interceptors.SessionInterceptor;

import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.sun.media.jai.codec.PNGEncodeParam.RGB;

import configs.MenusConfig;
import exts.Link;
import exts.MD5;
import exts.Menu;

@Before(SessionInterceptor.class)
public class RightsController extends BaseController {
	public void index(){
		this.renderText( "DEVELOP_ENV:"+ System.getenv("DEVELOP_ENV") );
	}
	
	/**
	 * 分配权限
	 */
	// @ActionKey("/admin/rights/user/list")
	@SuppressWarnings("unchecked")
	public void mgr(){
		// this.renderText("user_list==="+ getAttr("controllerName") +"/" + getAttr("methodName") );
		int gid = getParaToInt("gid", -100);
		Gson gson = new Gson();
		
		// 更新权限
		if(getPara("submit") != null) {
			Map<String, String[]> rt = getParaMap();
			boolean f = SYSRights.dao.deleteByGid(gid);
			for(Entry<String, String[]> x: rt.entrySet()) {
				String key = x.getKey();
				if( !"gid".equals(key) && !"submit".equals(key) ) {
					String actions = gson.toJson(x.getValue());
					
					System.out.println(key +"==="+ actions );
					
					new SYSRights().set("group_id", gid)
						.set("controller", key)
						.set("action", actions)
						.save();
					
					this.redirect("/freedom/rights/mgr");
					
				}
			}
		}
		
		// 当前组权限
		List<SYSRights> rightsList = SYSRights.dao.find("select * from admin_sysrights where group_id=?", gid);
		HashMap<String, ArrayList<String>> rights = new HashMap<String, ArrayList<String>>();
		
		for(SYSRights x: rightsList) {
			ArrayList<String> actions = gson.fromJson(x.getStr("action"), ArrayList.class);
			rights.put(x.getStr("controller"), actions);
		}
		
		this.setAttr("rights", rights);
		
		List<SYSGroup> groups = SYSGroup.dao.find("select * from admin_sysgroup order by id asc");
		this.setAttr("groups", groups);
		this.setAttr("gid", gid);
		
		ArrayList<Menu> menus = MenusConfig.init();
		this.setAttr("menus", menus);
		
		this.render("rights/mgr.html");
	}
	
	/**
	 * 管理员管理
	 */
	public void sysuserMgr() {
		Page<SYSUser> userPage = SYSUser.dao.paginate(1, 10, 
				"select a.id,a.username,b.name,FROM_UNIXTIME(a.lastlogintime,'%Y-%m-%d %H:%i:%s') lastlogintime,a.logincount", 
				"from admin_sysuser a left join admin_sysgroup b on a.group_id=b.id order by a.id desc");
		Link linkpage = new Link(getParaToInt("page", 1), userPage.getTotalRow(), "?", 20, 9);
		
		setAttr("data", userPage.getList());
		setAttr("linkpage", linkpage.show(3));
		this.render("rights/sysuserMgr.html");
	}

	/**
	 * 添加管理员
	 */
	public void sysuserAdd() {
				
		if(getPara("SYSUser.username") != null) {
			SYSUser sysuser = getModel(SYSUser.class, "SYSUser");
			sysuser.set("password", MD5.getMD5( this.getPara("password") ));
			if(sysuser.save()) {
				this.showSuccess("保存成功！");
			} else {
				this.showError("保存失败！");
			}
		} else {
			List<SYSGroup> groups = SYSGroup.dao.find("select * from admin_sysgroup order by id desc");
			setAttr("groups", groups);
			
			this.render("rights/sysuserAdd.html");
		}
	}
	
	/**
	 * 修改管理员
	 */
	public void sysuserModi(){
		SYSUser userinfo = SYSUser.dao.findById(getParaToInt(0, 0));
		if(userinfo == null) {
			this.showError("用户不存在!");
		} else {
			if(getPara("SYSUser.username") == null) {
				List<SYSGroup> groups = SYSGroup.dao.find("select * from admin_sysgroup order by id desc");
				setAttr("groups", groups);
				setAttr("data", userinfo);
				
				render("rights/sysuserModi.html");
			} else {
				SYSUser sysuser = getModel(SYSUser.class, "SYSUser");
				
				if(getPara("password") != "")
					sysuser.set("password", MD5.getMD5( this.getPara("password") ));
				
				if(sysuser.update()) {
					this.showSuccess("修改成功！");
				} else {
					this.showError("修改失败！");
				}
			}
		}
	}
	
	
	/**
	 * 删除管理员
	 */
	public void sysuserDel(){
		if( SYSUser.dao.deleteById(getParaToInt(0, 0)) ) {
			this.showSuccess("删除成功！");
		} else {
			this.showSuccess("删除失败！");
		}
	}
	
	/**
	 * 管理组
	 */
	public void groupMgr(){
		Page<SYSGroup> data = SYSGroup.dao.paginate(1, 10, "select *",  "from admin_sysgroup order by id desc");
		Link linkpage = new Link(getParaToInt("page", 1), data.getTotalRow(), "?", 20, 9);
		
		setAttr("data", data.getList());
		setAttr("linkpage", linkpage.show(3));
		this.render("rights/groupMgr.html");
	}
	
	/**
	 * 删除组
	 */
	public void groupDel(){
		if( SYSGroup.dao.deleteById(getParaToInt(0, 0)) ) {
			this.showSuccess("删除成功！");
		} else {
			this.showSuccess("删除失败！");
		}
	}
	
	/**
	 * 添加组
	 */
	public void groupAdd(){
		if(getPara("SYSGroup.name") != null) {
			SYSGroup sysgroup = getModel(SYSGroup.class, "SYSGroup");
			if(sysgroup.save()) {
				this.showSuccess("保存成功！");
			} else {
				this.showError("保存失败！");
			}
		} else {
			this.render("rights/groupAdd.html");
		}
	}
	
	/**
	 * 组名称修改
	 */
	public void groupModi(){
		if(getPara("name") != null) {
			if( SYSGroup.dao.set("name", getPara("name").trim()).update() ) {
				this.showSuccess("修改成功！");
			} else {
				this.showError("修改失败！");
			}
		}
	}

}
