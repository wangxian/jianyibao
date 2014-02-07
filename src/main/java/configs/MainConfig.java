package configs;
import models.SYSGroup;
import models.SYSRights;
import models.SYSUser;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;

import exts.HTML;
import freemarker.template.TemplateModelException;

public class MainConfig extends JFinalConfig {
	
	/**
	 * 开发环境为："DEV", 正式环境：""
	 */
	public static String DEVELOP_ENV;

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setError404View("/404.html");
		me.setError500View("/500.html");
		me.setViewType(ViewType.FREE_MARKER);
		
		// 
		String devENV = System.getenv("DEVELOP_ENV2");
		if(devENV == null) devENV = "";
		MainConfig.DEVELOP_ENV = devENV;
		
		// 开发环境
		if(MainConfig.DEVELOP_ENV.equals("DEV")) {
			me.setDevMode(true);
		}
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 前端路由
		me.add(new FrontRoutes());
		
		// 后台路由
		me.add(new AdminRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 支持ehCache
		// me.add(new EhCachePlugin());
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://127.0.0.1/sdtdance?useUnicode=true&characterEncoding=utf8", "root", "111111");
		me.add(c3p0Plugin);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);
		me.add(arp);
//		
//		// 配置ActiveRecord 表到模型的映射
		arp.addMapping("admin_sysuser", SYSUser.class);
		arp.addMapping("admin_sysgroup", SYSGroup.class);
		arp.addMapping("admin_sysrights", SYSRights.class);
	}
	
	/**
	 * 配置启动后的操作
	 */
	@Override
	public void afterJFinalStart() {
		try {
			FreeMarkerRender.getConfiguration().setSharedVariable("HTML", new HTML());
		} catch (TemplateModelException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
//		 me.add( new handlers.HtmlExtensionHandler() );
	}

	// /**
	// * 建议使用 JFinal 手册推荐的方式启动项目
	// * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	// */
	// public static void main(String[] args) {
	//     JFinal.start("WebRoot", 8080, "/", 5);
	// }
}
