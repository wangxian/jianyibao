package controllers.admin;

public class NewsController extends BaseController {
	public void index(){
		this.renderText( "URI:"+ this.getRequest().getRequestURI() );
	}
}
