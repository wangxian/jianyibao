package exts;

import java.util.ArrayList;

public class SubMenu {
	private String title;
	private ArrayList<ArrayList<String>> subMenu = new ArrayList<ArrayList<String>>();
	
	public SubMenu(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<ArrayList<String>> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(ArrayList<ArrayList<String>> subMenu) {
		this.subMenu = subMenu;
	}
	
	/**
	 * 添加子菜单
	 * @param key 菜单key，英文
	 * @param value 菜单名称，中文
	 * @return SubMenu
	 */
	public SubMenu addSubMenu(String key, String value) {
		ArrayList<String> al = new ArrayList<String>();
		al.add(key);
		al.add(value);
		this.subMenu.add(al);
		return this;
	}
}