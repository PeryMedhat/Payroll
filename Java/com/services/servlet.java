package com.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// setup connection variables
				String user = "springstudent";
				String pass = "springstudent";
				String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_schema?useSSL=false";
				//String driver = "com.mysql.jdbc.Driver";
				
				//get connection to database
				try {
					PrintWriter out = response.getWriter();
					out.println("Connecting to DB .."+jdbcUrl);
					Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
					out.println("SUCCESS!!");
					myConn.close();
					
				}
				catch(Exception exc) {
					exc.printStackTrace();
					
				}
				}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
