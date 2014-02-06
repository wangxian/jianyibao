package configs;

import com.jfinal.config.Routes;

import controllers.IndexController;

public class FrontRoutes extends Routes {

	@Override
	public void config() {
		this.add("/", IndexController.class, "/");
	}

}
