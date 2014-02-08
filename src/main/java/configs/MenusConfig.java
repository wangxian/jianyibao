package configs;

import java.util.ArrayList;

import exts.Menu;
import exts.SubMenu;

/**
 * Admin menu configure
 * @author wangxian
 */
public class MenusConfig {
	private ArrayList<Menu> menus = new ArrayList<Menu>();
	
	private static MenusConfig me = null;
	
	/**
	 * 初始化菜单
	 */
	public MenusConfig() {
		System.out.println("menusconfig init.....");
		
		// 添加菜单
		Menu news = new Menu("news", "新闻管理");
		news.addSubMenu(
			new SubMenu("新闻管理")
			.addSubMenu("list", "新闻-列表")
			.addSubMenu("add", "新闻-添加")
		).addAction("list", "新闻-列表")
		.addAction("add", "新闻-添加")
		.addAction("edit", "新闻-编辑")
		.addAction("del", "新闻-删除");
		menus.add(news);
			
		
		// 添加菜单
		Menu menu = new Menu("rights", "权限管理");
		menu.addSubMenu(
			new SubMenu("帐号管理")
			.addSubMenu("sysuserMgr", "管理员-列表")
			.addSubMenu("sysuserAdd", "管理员-添加")
		).addSubMenu(
			new SubMenu("管理员组")
			.addSubMenu("groupMgr", "管理员组-列表")
			.addSubMenu("groupAdd", "管理员组-添加")
		).addSubMenu(
			new SubMenu("权限管理")
			.addSubMenu("mgr", "权限管理")
		)
		.addAction("mgr", "权限管理")
		.addAction("sysuserAdd", "管理员-添加")
		.addAction("sysuserMgr", "管理员-列表")
		.addAction("sysuserDel", "管理员-删除")
		.addAction("sysuserModi", "管理员-修改")
		
		.addAction("groupAdd", "管理组-添加")
		.addAction("groupMgr", "管理组-列表")
		.addAction("groupModi", "管理组-修改")
		.addAction("groupDel", "管理组-删除");
		
		// 保存
		menus.add(menu);
	}
	
	/**
	 * 获取菜单
	 * @return ArrayList<Menu>
	 */
	public ArrayList<Menu> getMenus() {
		return menus;
	}
	
	/**
	 * 单例获取菜单
	 * @return ArrayList<Menu>
	 */
	public static ArrayList<Menu> init() {
		if(me == null) {
			me = new MenusConfig();
		}
		return me.getMenus();
	}
}
