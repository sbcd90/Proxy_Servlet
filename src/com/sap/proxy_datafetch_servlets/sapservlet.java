package com.sap.proxy_datafetch_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sap.proxy_datafetch_pac.datafetch;
import org.apache.http.conn.ClientConnectionManager;

/**
 * Servlet implementation class sapservlet
 */
@WebServlet("/sapservlet")
public class sapservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private datafetch fetching;
    private String data;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sapservlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = "url";
		String value = request.getParameter(name);
		try{
    		fetching = new datafetch();
    		data = fetching.getApiData(value);
    	}
    	catch(Exception e3){
    		e3.printStackTrace();
    	}
		PrintWriter out = response.getWriter();
		out.println(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
