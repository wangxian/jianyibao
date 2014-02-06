package controllers.admin;

import models.SYSUser;
import interceptors.SessionInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

@Before(SessionInterceptor.class)
public class RightsController extends Controller {
	public void index(){
		this.renderText("index");
	}
	
//	@ActionKey("/admin/rights/user/list")
	public void mgr(){
		this.renderText("user_list==="+ getAttr("controllerName") +"/" + getAttr("methodName") );
	}
	
	public void sysuserMgr() {
		// 分页查询年龄大于18的user,当前页号为1,每页10个user
		Page<SYSUser> userPage = SYSUser.dao.paginate(1, 10, "select *", "from admin_sysuser", 18);
	}

	public void sysuserAdd() {
		renderText(getRequest().getHeader("User-Agent"));
	}
	
	public void sysuserModi(){
		
	}
	
	
	public void sysuserDel(){
		renderText("user_del");
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
