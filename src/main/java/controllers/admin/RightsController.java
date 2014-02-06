package controllers.admin;

import java.util.List;

import org.eclipse.jetty.server.Authentication.User;

import models.SYSGroup;
import models.SYSUser;
import interceptors.SessionInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import exts.Link;
import exts.MD5;

//@Before(SessionInterceptor.class)
public class RightsController extends BaseController {
	public void index(){
		this.renderText("index");
	}
	
//	@ActionKey("/admin/rights/user/list")
	public void mgr(){
		this.renderText("user_list==="+ getAttr("controllerName") +"/" + getAttr("methodName") );
	}
	
	public void sysuserMgr() {
		Page<SYSUser> userPage = SYSUser.dao.paginate(1, 10, 
				"select a.id,a.username,b.name,FROM_UNIXTIME(a.lastlogintime,'%Y-%m-%d %H:%i:%s') lastlogintime,a.logincount", 
				"from admin_sysuser a left join admin_sysgroup b on a.group_id=b.id order by a.id desc");
		Link linkpage = new Link(getParaToInt("page", 1), userPage.getTotalRow(), "?", 20, 9);
		
		setAttr("data", userPage.getList());
		setAttr("linkpage", linkpage.show(3));
		this.render("rights/sysuserMgr.html");
	}

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
	
	
	public void sysuserDel(){
		if( SYSUser.dao.deleteById(getParaToInt(0, 0)) ) {
			this.showSuccess("删除成功！");
		} else {
			this.showSuccess("删除失败！");
		}
	}
	
	public void groupMgr(){
		
	}
	
	public void groupDel(){
		
	}
	
	public void groupAdd(){
		
	}
	
	public void groupModi(){
		
	}

}
