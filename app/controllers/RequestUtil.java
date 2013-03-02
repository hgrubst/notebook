package controllers;

import play.api.mvc.Request;
import play.mvc.Http;



public class RequestUtil {

	
	public static boolean isAjaxRequest(Http.Request request){
		return "XMLHttpRequest".equals(request.headers().get("XMLHttpRequest"));
	}

	public static boolean isNotAjaxRequest(Http.Request request){
		return !isAjaxRequest(request);
	}
	
	
}
