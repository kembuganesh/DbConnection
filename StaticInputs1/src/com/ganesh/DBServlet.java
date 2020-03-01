package com.ganesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public class DBServlet extends HttpServlet {
	
		Connection con;
		PreparedStatement ps;
		
		public void init() {
			try {
				//get access to ServletConfig 
				ServletConfig cg= getServletConfig();
				//read init param values from web.xml
				
				String s1=cg.getInitParameter("driver");
				String s2=cg.getInitParameter("dburl");
				String s3=cg.getInitParameter("dbuser");
				String s4=cg.getInitParameter("dbpwd");
				//create jdbc object
				Class.forName(s1);
				con=DriverManager.getConnection(s2, s3, s4);
				//create preparedstatement
				ps=con.prepareStatement("select ename,esal from emp66 where empno=?");
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
		public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
			
					try {
						//read form data from form page
						int no=Integer.parseInt(req.getParameter("teno"));
						//get PrintWriter object
						PrintWriter pw=res.getWriter();
						// set value to parameter of the query
						ps.setInt(1, no);
						//Execute Query
						ResultSet rs=ps.executeQuery();
						//process th resultset object and dispaly emp details
						if(rs.next()) {
							pw.println("<b>Emp name is :</b>"+rs.getString(1));
							pw.println("<b> salary is  :</b>"+rs.getString(2));
						}
						
						rs.close();
						pw.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
					
		}
		public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
			doGet(req,res);
		}
		public void destroy() {
			try {
				if(ps!=null) 
					ps.close();
				}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			try {
				if(con!=null) 
				con.close();
			}
			catch(Exception e2) {
				e2.printStackTrace();
			}
			
			
		}
	}


