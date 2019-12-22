package servlet;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class SignupServletTest {
	
	@Ignore
	@Test
	//correct user test
	public void testServlet1() throws Exception {
		ServletConfig sg = mock(ServletConfig.class);

		HttpServletRequest request = mock(HttpServletRequest.class);       
        when(request.getParameter("username")).thenReturn("augustus");
        when(request.getParameter("pw")).thenReturn("principate");
        when(request.getParameter("pw2")).thenReturn("principate");
        Mockito.doNothing().when(request).setAttribute(any(), any());

        HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).setContentType(any());
        
        RequestDispatcher dispatch = mock(RequestDispatcher.class);
        Mockito.doNothing().when(dispatch).forward(any(), any());
        
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRequestDispatcher(any())).thenReturn(dispatch);
        SignupServlet servlet = new SignupServlet() {
        	public ServletContext getServletContext() {
        	      return servletContext; // return the mock
        	    }
        };
        servlet.init(sg);
        servlet.service(request, response);
        Mockito.verify(request, Mockito.times(0)).setAttribute(any(),any());
	}
	
	@Test
	//empty user test
	public void testServlet2() throws Exception {
		ServletConfig sg = mock(ServletConfig.class);

		HttpServletRequest request = mock(HttpServletRequest.class);       
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("pw")).thenReturn("principate");
        when(request.getParameter("pw2")).thenReturn("principate");
        Mockito.doNothing().when(request).setAttribute(any(), any());

        HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).setContentType(any());
        
        RequestDispatcher dispatch = mock(RequestDispatcher.class);
        Mockito.doNothing().when(dispatch).forward(any(), any());
        
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRequestDispatcher(any())).thenReturn(dispatch);
        SignupServlet servlet = new SignupServlet() {
        	public ServletContext getServletContext() {
        	      return servletContext; // return the mock
        	    }
        };
        servlet.init(sg);
        servlet.service(request, response);
        Mockito.verify(request, Mockito.times(1)).setAttribute("uerror", "No Username Entered");
	}
	
	@Test
	//empty password test
	public void testServlet3() throws Exception {
		ServletConfig sg = mock(ServletConfig.class);

		HttpServletRequest request = mock(HttpServletRequest.class);       
        when(request.getParameter("username")).thenReturn("augustus");
        when(request.getParameter("pw")).thenReturn("");
        when(request.getParameter("pw2")).thenReturn("");
        Mockito.doNothing().when(request).setAttribute(any(), any());

        HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).setContentType(any());
        
        RequestDispatcher dispatch = mock(RequestDispatcher.class);
        Mockito.doNothing().when(dispatch).forward(any(), any());
        
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRequestDispatcher(any())).thenReturn(dispatch);
        SignupServlet servlet = new SignupServlet() {
        	public ServletContext getServletContext() {
        	      return servletContext; // return the mock
        	    }
        };
        servlet.init(sg);
        servlet.service(request, response);
        Mockito.verify(request, Mockito.times(2)).setAttribute("perror", "No Password Entered");
	}
	
	@Test
	//passwords matching test
	public void testServlet4() throws Exception {
		ServletConfig sg = mock(ServletConfig.class);

		HttpServletRequest request = mock(HttpServletRequest.class);       
        when(request.getParameter("username")).thenReturn("augustus");
        when(request.getParameter("pw")).thenReturn("julius");
        when(request.getParameter("pw2")).thenReturn("nero");
        Mockito.doNothing().when(request).setAttribute(any(), any());

        HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).setContentType(any());
        
        RequestDispatcher dispatch = mock(RequestDispatcher.class);
        Mockito.doNothing().when(dispatch).forward(any(), any());
        
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRequestDispatcher(any())).thenReturn(dispatch);
        SignupServlet servlet = new SignupServlet() {
        	public ServletContext getServletContext() {
        	      return servletContext; // return the mock
        	    }
        };
        servlet.init(sg);
        servlet.service(request, response);
        Mockito.verify(request, Mockito.times(1)).setAttribute("ierror", "Password Didn't Match");
	}
	
	@Test
	//user existed test
	public void testServlet5() throws Exception {
		ServletConfig sg = mock(ServletConfig.class);

		HttpServletRequest request = mock(HttpServletRequest.class);       
        when(request.getParameter("username")).thenReturn("augustus");
        when(request.getParameter("pw")).thenReturn("principate");
        when(request.getParameter("pw2")).thenReturn("principate");
        Mockito.doNothing().when(request).setAttribute(any(), any());

        HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.doNothing().when(response).setContentType(any());
        
        RequestDispatcher dispatch = mock(RequestDispatcher.class);
        Mockito.doNothing().when(dispatch).forward(any(), any());
        
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRequestDispatcher(any())).thenReturn(dispatch);
        SignupServlet servlet = new SignupServlet() {
        	public ServletContext getServletContext() {
        	      return servletContext; // return the mock
        	    }
        };
        servlet.init(sg);
        servlet.service(request, response);
        Mockito.verify(request, Mockito.times(1)).setAttribute("uerror", "User Already Exists");
	}
}
