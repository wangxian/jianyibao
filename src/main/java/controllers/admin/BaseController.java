package controllers.admin;

import com.jfinal.core.Controller;

public class BaseController extends Controller {
	public void showSuccess(String message) {
		String referer = this.getRequest().getHeader("Referer");
		if(referer == null) referer = "";
		this.showSuccess(message, referer);
	}
	
	public void showSuccess(String message, String url) {
		setAttr("message", message);
		setAttr("url", url);
		setAttr("wait", 6);
		this.render("public/success.html");
	}
	
	public void showError(String message) {
		String referer = this.getRequest().getHeader("Referer");
		if(referer == null) referer = "";
		this.showError(message, referer);
	}
	
	public void showError(String message, String url) {
		setAttr("message", message);
		setAttr("url", url);
		setAttr("wait", 6);
		this.render("public/error.html");
	}
}
