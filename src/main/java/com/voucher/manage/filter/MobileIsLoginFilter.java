package com.voucher.manage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MobileIsLoginFilter implements Filter{

	 public FilterConfig config3=null;
	    @Override  
	    public void destroy() {   
	  
	    }   
	  
	    @Override  
	    public void doFilter(ServletRequest request, ServletResponse response,   
	            FilterChain chain) throws IOException, ServletException {   
	    	  HttpServletRequest hrequest = (HttpServletRequest)request;
		        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
		        
		        String loginStrings = config3.getInitParameter("loginStrings");        
		        String includeStrings = config3.getInitParameter("includeStrings");    
		        String redirectPath = hrequest.getContextPath() + config3.getInitParameter("redirectPath");
		        String disabletestfilter = config3.getInitParameter("disabletestfilter");
		        

		        String[] loginList = loginStrings.split(";");
		        String[] includeList = includeStrings.split(";");
		        
		        if (!IsLoginFilter.isContains(hrequest.getRequestURI(), includeList)) {
		            chain.doFilter(request, response);
		            return;
		        }
		        
		        if (IsLoginFilter.isContains(hrequest.getRequestURI(), loginList)) {//
		            chain.doFilter(request, response);
		            return;
		        }
		        
		        Integer type=(Integer) hrequest.getSession().getAttribute("type");
		        
		        HttpServletRequest httpRequest = (HttpServletRequest) request;
				WebApplicationContext wac = WebApplicationContextUtils    //controller以外的包初始化类
						.getRequiredWebApplicationContext(httpRequest.getSession()
								.getServletContext());
		        
			//	usersMapper=wac.getBean(UsersMapper.class);
				
		        if (type!=0) {
		        //	HttpSession session = hrequest.getSession();
		        //	  session.invalidate();       
		        	System.out.println("redirectPath="+redirectPath);
		        	wrapper.sendRedirect(redirectPath);
		            return;
		        }else {
		        	
		        	System.out.println("mobileIsLoginFilter type="+type);
		        	
		        	chain.doFilter(request, response);   //通过验证
		   
		            return;
		        }   
	    }   
	  
	    @Override  
	    public void init(FilterConfig filterConfig) throws ServletException {   
	    	config3 = filterConfig;
	    }
}
