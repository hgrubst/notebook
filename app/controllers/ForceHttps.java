package controllers;

import org.apache.commons.lang.StringUtils;

import play.Play;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;

public class ForceHttps extends Action.Simple{

	private static String SSL_HEADER_CLOUD_FOUNDRY = "SSLSESSIONID";
	
	@Override
	public Result call(Context ctx) throws Throwable {

		if(!isHttpsRequest(ctx.request())){
			String redirect = "https://" + ctx.request().host() + ctx.request().uri();
			
			System.out.println("Application not using https, redirecting to : " + redirect);
			
			return redirect(redirect);
		}
		
		return delegate.call(ctx);
	}
	
	
	private boolean isHttpsRequest(Request request){
 
		if(Play.isDev()){
			return true;
		}
		
		if(StringUtils.isNotEmpty(request.getHeader(SSL_HEADER_CLOUD_FOUNDRY))){
			return true;
		}
		
		return false;
	}
}
