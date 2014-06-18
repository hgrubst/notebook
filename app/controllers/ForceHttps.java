package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Play;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;

public class ForceHttps extends Action.Simple{

	static Logger log = LoggerFactory.getLogger(ForceHttps.class);
	
//	private static String HTTP_HEADER_SCHEME = "X-Scheme";
	//the x-forwarded proto is used by heroku see here : https://devcenter.heroku.com/articles/http-routing#heroku-headers
	private static String HTTP_HEADER_SCHEME = "X-Forwarded-Proto";
	private static String HTTP_HEADER_HOST = "Host";
	
	@Override
	public Result call(Context ctx) throws Throwable {

		if(!isHttpsRequest(ctx.request())){
			String redirect = "https://" + ctx.request().getHeader(HTTP_HEADER_HOST) + ctx.request().uri();
			
			log.info("Application not using https, redirecting to : " + redirect);
			
			return redirect(redirect);
		}
		
		return delegate.call(ctx);
	}
	
	
	private boolean isHttpsRequest(Request request){
		if(Play.isDev()){
			return true;
		}
		
		if("https".equalsIgnoreCase(request.getHeader(HTTP_HEADER_SCHEME))){
			return true;
		}
		
		return false;
	}
}
