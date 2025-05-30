package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// Get path and convert to class name
			String path = req.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/', '.');
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// Call the execute method of the action
			action.execute(req, res);

		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);

	}
}
