package interceptors;

import java.util.ArrayList;
import java.util.HashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

import configs.MainConfig;

public class SessionInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) { 
		Object sysuser = ai.getController().getSessionAttr("sysuser");
		if (sysuser == null) {
			if( MainConfig.DEVELOP_ENV.equals("DEV") && !ai.getController().getClass().getSimpleName().equals("IndexController") ) {
				// 测试环境不跳转
				ai.invoke();
			} else {
				ai.getController().redirect("/freedom/login");
			}
		} else {
			// System.out.println(ai.getMethodName());
			// System.out.println(ai.getControllerKey());
			// System.out.println(ai.getActionKey());

			String methodName = ai.getMethodName();
			String controllerName = ai.getControllerKey();
			if (controllerName != "/freedom")
				controllerName = controllerName.substring(9);
			else
				controllerName = "index";

			// System.out.println(controllerName + "===" + methodName);
			HashMap<String, ArrayList<String>> rights = ai.getController().getSessionAttr("rights");

			if (!rights.isEmpty() && (controllerName == "index" || rights.get(controllerName)
				.contains(methodName))) {
				
				// 添加controller和action到请求信息中
				ai.getController().setAttr("controllerName", controllerName)
				  .setAttr("methodName", methodName);
				ai.invoke();
			} else {
				ai.getController().renderText("Access denied!");
			}

		}
		
	}

}
