package controllers.admin;

import interceptors.SessionInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.SYSGroup;
import models.SYSRights;
import models.SYSUser;

import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

import configs.MenusConfig;
import exts.MD5;
import exts.Menu;
import exts.SubMenu;

@Before(SessionInterceptor.class)
public class IndexController extends Controller {
	
	public void index() {
//		StringBuffer sb = new StringBuffer();
//		List<String> keysArrayList = JFinal.me().getAllActionKeys();
//		sb.append("keysArrayList.size():" + keysArrayList.size() + "\n");
//		sb.append("getRequestURL:" + this.getRequest().getRequestURL() + "\n");
//		sb.append("getRequestURI:" + this.getRequest().getRequestURI() + "\n");
//
//		sb.append("session:" + this.getSessionAttr("name") + "\n");
//
//		for (String x : keysArrayList) {
//			sb.append("action:" + x + "\n");
//		}
//
//		System.out.println(MenusConfig.init().get(0).getActions().get("rights/sysuserAdd"));
//		System.out.println(MenusConfig.init().get(0).getActions().get("rights/sysuserMgr"));
//		System.out.println(MenusConfig.init().get(0).getTitle());
//
//		this.renderText("hello admin!" + sb);
		
//		// 解析JSON
//		String x = "{\"id\":12}";
//		Gson gson = new Gson();
//		@SuppressWarnings("unchecked")
//		HashMap<String, Integer> d = gson.fromJson(x, HashMap.class);
//		System.out.println(d.get("id").TYPE);

//		 Logger log = Logger.getLogger(IndexController.class);
//		 log.info("xxx");
//		 log.debug("debug.....");
//		 this.redirect("/admin/rights/user_list");
//		 log.info("redirect/xxxx, after");
		
		HashMap<String, String> sessioninfoHashMap = getSessionAttr("sysuser");
		HashMap<String, ArrayList<String>> sessionRightsHashMap = getSessionAttr("rights");
		ArrayList<Menu> menus = MenusConfig.init();
		
		// 一级菜单
		ArrayList<String> menu1 = new ArrayList<String>();
		
		// 二级菜单
		ArrayList<ArrayList<String>> menu2 = new ArrayList<ArrayList<String>>();
		
		// 三级菜单
		HashMap<String, ArrayList<ArrayList<String>>> menu3 = new HashMap<String, ArrayList<ArrayList<String>>>();
		
		for(Menu menu: menus) {
			if (sessionRightsHashMap.containsKey(menu.getKey())) {
				
				// 存放子菜单
				ArrayList<String> subMenus = new ArrayList<String>();
				for(SubMenu submenuObj:menu.getSubMenus()) {
					ArrayList<ArrayList<String>> subMenu = new ArrayList<ArrayList<String>>();
					
					ArrayList<ArrayList<String>> submenu = submenuObj.getSubMenu();
					for(ArrayList<String> st: submenu) {
						
						// 判断对菜单是否有权限
						if( sessionRightsHashMap.get(menu.getKey()).contains(st.get(0)) ) {
							ArrayList<String> si = new ArrayList<String>();
							si.add("/freedom/" + menu.getKey() +"/"+ st.get(0));
							si.add(st.get(1));
							subMenu.add(si);
						}
					}
					menu3.put(submenuObj.getTitle(), subMenu);
					
					// 存放二级菜单
					subMenus.add(submenuObj.getTitle());
				}
				menu1.add(menu.getTitle());
				menu2.add(subMenus);				
			}
		}		
		
		// 传到VIEW
		this.setAttr("user", sessioninfoHashMap);
		this.setAttr("menu1", menu1);
		this.setAttr("menu2", menu2);
		this.setAttr("menu3", menu3);
		
		this.render("index/index.html");
	}

	/**
	 * 登录
	 */
	@ClearInterceptor(ClearLayer.ALL)
	public void login() {

		// 传统方式
		// SYSUser user = SYSUser.dao.findById(78);
		// System.out.println(user.toJson());
		//
		// List<SYSUser> users =
		// SYSUser.dao.find("select * from admin_sysuser");
		// System.out.println( com.jfinal.kit.JsonKit.toJson(users) );

		// JFinal 独创 Db + Record 模式
		// Record user = Db.findById("admin_sysuser", 78, "username, password");
		// System.out.println(user.getStr("username") +
		// user.getStr("password"));

		// test logger
		// Logger log = Logger.getLogger(IndexController.class);
		// log.info("xxx");
		// log.debug("debug.....");
		// log.warn("warn....");
		// setSessionAttr("name", MenusConfig.init());
		
		if( getPara("username") != null && getPara("password") != null) {
			String text = "{\"state\":10020, \"message\":\"\"}";
			SYSUser user = SYSUser.dao.getByName( getPara("username").trim() );
			if(user == null) {
				text = "{\"state\":10020, \"message\":\"用户不存在\"}";
			} else {
				if(! user.getStr("password").equals( MD5.getMD5(getPara("password").trim()) )) {
					text = "{\"state\":10020, \"message\":\"密码错误\"}";
				} else {
					text = "{\"state\":200, \"message\":\"\"}";
					
					// 查询 group 信息
					SYSGroup group = SYSGroup.dao.findById(user.getInt("group_id"));
										
					HashMap<String, String> sessioninfoHashMap = new HashMap<String, String>();
					sessioninfoHashMap.put("user_id", user.getInt("id").toString());
					sessioninfoHashMap.put("username", user.getStr("username"));
					sessioninfoHashMap.put("group_id", user.getInt("group_id").toString());
					sessioninfoHashMap.put("realname", user.getStr("realname"));
					sessioninfoHashMap.put("group_name", group.getStr("group_name"));
					
					// 存储session
					this.setSessionAttr("sysuser", sessioninfoHashMap);
					
					// 查询用户权限
					List<SYSRights> rights = SYSRights.dao.getByGroupId(user.getInt("group_id"));
					
					// sessionRights
					HashMap<String, ArrayList<String>> sessionRightsHashMap = new HashMap<String, ArrayList<String>>();
					Gson gson = new Gson();
					if(rights != null) {
						for(SYSRights r: rights) {
//							System.out.println(r.getStr("action"));
							
							@SuppressWarnings("unchecked")
							ArrayList<String> actions = gson.fromJson(r.getStr("action"), ArrayList.class);
							sessionRightsHashMap.put(r.getStr("controller"), actions);
						}
						
						System.out.println(gson.toJson(sessionRightsHashMap));
						
						// 存储权限
						setSessionAttr("rights", sessionRightsHashMap);
					}
				}
			}
			
			
			this.renderText(text, "application/json");
		} else {
			this.render("index/login.html");
		}
		
		
	}

	/**
	 * 退出
	 */
	@ClearInterceptor
	public void logout() {
		this.removeSessionAttr("sysuser");
		this.redirect("/freedom/login");
	}

	/**
	 * 我的帐号信息
	 */
	public void myaccount() {

	}
}
