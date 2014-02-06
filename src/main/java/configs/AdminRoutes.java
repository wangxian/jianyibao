package configs;

import com.jfinal.config.Routes;

import controllers.admin.IndexController;
import controllers.admin.RightsController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		this.add("/freedom", IndexController.class, "/admin");
//		this.add("/admin/login", IndexController.class, "/admin");
//		this.add("/admin/logout", IndexController.class, "/admin");
		
		this.add("/freedom/rights", RightsController.class, "/admin");
	}

}
