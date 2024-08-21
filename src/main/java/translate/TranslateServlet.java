package translate;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Servlet implementation class TranslateServlet
 */
@WebServlet("/TranslateServlet")
public class TranslateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String texttoTranslate = request.getParameter("texttotranslate");
        String languageSelected = request.getParameter("languageselected");

        // Set response content type to UTF-8
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Construct the body of the POST request
        String requestBody = String.format(
            "-----011000010111000001101001\r\n" +
            "Content-Disposition: form-data; name=\"source_language\"\r\n\r\n" +
            "en\r\n" + // Assuming source language is English; adjust if needed
            "-----011000010111000001101001\r\n" +
            "Content-Disposition: form-data; name=\"target_language\"\r\n\r\n" +
            "%s\r\n" +
            "-----011000010111000001101001\r\n" +
            "Content-Disposition: form-data; name=\"text\"\r\n\r\n" +
            "%s\r\n" +
            "-----011000010111000001101001--\r\n\r\n",
            languageSelected, texttoTranslate
        );
        //Integrating API
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("https://text-translator2.p.rapidapi.com/translate"))
                .header("x-rapidapi-key", "397438b542msha5240a6aeb3d394p10760djsnd27c860ff592")
                .header("x-rapidapi-host", "text-translator2.p.rapidapi.com")
                .header("Content-Type", "multipart/form-data; boundary=---011000010111000001101001")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> apiResponse = HttpClient.newHttpClient().send(request1, HttpResponse.BodyHandlers.ofString());
            String translatedText = apiResponse.body();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Translated Text</title>");
            out.println("<link\r\n"
            		+ "	href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\r\n"
            		+ "	rel=\"stylesheet\"\r\n"
            		+ "	integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\"\r\n"
            		+ "	crossorigin=\"anonymous\">");
            out.println("<style>");
            out.println("body { background-color:#f8f9fa;}");
            out.println(".container { max-width : 500px ;}");
            out.println("</style>");
            out.println("</head>");
            
            out.println("<body>");
            
            //Bottstrap-styled container
            out.println("<div class='container mt-5 border border-secondary rounded shadow p-5 bg-white style= width: 60vw;'>");
            out.println("<h2 class=\"text-primary text-center mb-2\">Translated Text:</h2>");
            out.println("<p class=\"text-center mt-4\">The Entered String is: <b> "+texttoTranslate + "</b></p>");
            
            decode(translatedText, out,languageSelected,texttoTranslate);
            
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    private void decode(String translatedText, PrintWriter out,String languageSelected,String texttoTranslate) throws Exception {
        JSONObject jsonObject = new JSONObject(translatedText);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        String resultStr = dataObject.getString("translatedText");
        out.println("<p class='text-center'>Translated Text: <b>"+resultStr+"</b></p>");
        
        out.println("<div class = 'd-flex justify-content-center mx-auto'>");
        out.println("<a href='/Translator_app/index.jsp' class ='btn btn-primary mx-2 px-5'>Go Back</a>");
        out.println("<a href='/Translator_app/TranslateRecordServlet' class ='btn btn-success mx-2 px-5'>View All Records</a>");
        out.println("</div>");
       
        insertIntoDatabase(texttoTranslate,languageSelected,resultStr);
    }

	private void insertIntoDatabase(String texttoTranslate, String languageSelected, String resultStr) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Translator_app","root","root");
			String sql=("insert into translator(originalStr, lang, resultStr) values (?,?,?)");
		    pst = con.prepareStatement(sql);
			pst.setString(1, texttoTranslate);
			pst.setString(2,languageSelected);
			pst.setString(3, resultStr);
			
			int i=pst.executeUpdate();
			if (i>0) {
				System.out.println("Records Inserted Successfully");
			} else {
				System.out.println("Records are not inserted");
			}
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
