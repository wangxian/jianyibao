package controllers;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index(){
//		StringKit.isBlank("xxx");
//		List<String> allActionKeys = JFinal.me().getAllActionKeys();
		
		this.renderText("hello front! -1");
//		this.render("index.html");
		
	}
}
