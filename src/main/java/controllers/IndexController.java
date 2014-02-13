package controllers;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import configs.MainConfig;

public class IndexController extends Controller {
	public void index(){
//		StringKit.isBlank("xxx");
//		List<String> allActionKeys = JFinal.me().getAllActionKeys();

//		this.renderText("hello front! -4 ="+ MainConfig.DEVELOP_ENV);
//		this.render("index.html");
        this.renderText(PathKit.getWebRootPath());
	}
}
