package exts;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 菜单权限结构
 * @author wangxian
 */
public class Menu {
	/**
	 * 子菜单的英文表示
	 */
	private String key;
	
	/**
	 * 子菜单名称
	 */
	private String title;
	
	/**
	 * 子菜单数组
	 */
	private ArrayList<SubMenu> subMenus = new ArrayList<SubMenu>();
	
	/**
	 * 当前菜单，所有的操作actions
	 */
	private HashMap<String, String> actions = new HashMap<String, String>();

	public Menu(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(ArrayList<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}
	
	/**
	 * 添加子菜单
	 * @param subMenu SubMenu instance
	 * @return
	 */
	public Menu addSubMenu(SubMenu subMenu) {
		this.subMenus.add(subMenu);
		return this;
	}

	public HashMap<String, String> getActions() {
		return actions;
	}

	public void setActions(HashMap<String, String> actions) {
		this.actions = actions;
	}
	
	/**
	 * 添加菜单的所有操作action
	 * @param key 操作key, 英文
	 * @param value 操作名称
	 * @return Menu
	 */
	public Menu addAction(String key, String value) {
		this.actions.put(key, value);
		return this;
	}
	
}