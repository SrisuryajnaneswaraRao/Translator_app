package translate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TranslateRecordServlet
 */
@WebServlet("/TranslateRecordServlet")
public class TranslateRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pst = null;
		PrintWriter out=response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<title>Translator App Records</title>");
		out.println("<link\r\n"
				+ "	href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\r\n"
				+ "	rel=\"stylesheet\"\r\n"
				+ "	integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\"\r\n"
				+ "	crossorigin=\"anonymous\">\r\n");
		out.println("<style>");
		out.println("body {");
		out.println("	dispaly:flex;");
		out.println("	justify-content:center;");
		out.println("	align-items:center;");
		out.println("	height:100vh; } ");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='container mt-5'>");
		out.println("<div class = card text-center border-primary shadow'>");
		out.println("<div class='card-body'>");
		
		out.println("<h2 class='card-title text text-center'>Translate App Records</h2>");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Translator_app","root","root");
			pst=con.prepareStatement("select * from translator");
			ResultSet rs=pst.executeQuery();
			
			out.println("<table class = 'table table-bordered'>");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th>Original</th>");
			out.println("<th>Language</th>");
			out.println("<th>Result</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			
			while (rs.next()) {
				String text=rs.getString("originalStr");
				String langCode=rs.getString("lang");
				String result=rs.getString("resultStr");
				out.println("<tr>");
				out.println("<td>"+text+"</td>");
				out.println("<td>"+langCode+"</td>");
				out.println("<td>"+result+"</td>");
				out.println("</tr>");
			}
			out.println("</tbody>");
			out.println("</table>");
			out.println("<div class=\"d-flex justify-content-center\">");
			out.println("<a href='/Translator_app/index.jsp' class ='btn btn-primary mx-2 px-5'>Go Back</a>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
			
	}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
