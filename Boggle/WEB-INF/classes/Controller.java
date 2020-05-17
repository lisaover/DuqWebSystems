import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Controller for Boggle Web Applicaiton
 * 
 */
 
 public class Controller extends HttpServlet
 {
  /**
   * If session is new display the sign-in by dispatching to sign-in.jspx
   */
   
   public void doGet (HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
    HttpSession session = request.getSession();
    session.setAttribute("wordsFound", "");
    session.setAttribute("showScores", false);
        
          if (session.isNew()) { 
            RequestDispatcher displaySignIn = getServletContext().getNamedDispatcher("signIn");
                displaySignIn.forward(request, response);
          }
          else {
            BoggleBean newGameRequest = (BoggleBean)session.getAttribute("boggleGame");
            newGameRequest.createBoard();
            RequestDispatcher displayGame = getServletContext().getNamedDispatcher("Game");
                displayGame.forward(request, response);
          }
   }
   
   public void doPost (HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     HttpSession session = request.getSession();
     String path = request.getPathInfo();
     //for unknown reason, the later setAttribute for wordsFound is empty without first setting it with ""
     session.setAttribute("wordsFound", "");
     session.setAttribute("showScores", false);
     
    if (session.isNew()) {
      RequestDispatcher displaySignIn = getServletContext().getNamedDispatcher("signIn");
                displaySignIn.forward(request, response);
    } else {
     if(path.equals("/login")) {
       String username = request.getParameter("username");
       session.setAttribute("username", username);
       //create instance of boggle class and save the board to the session
       BoggleBean boggleGame = new BoggleBean();
       session.setAttribute("boggleGame", boggleGame);
       //create requestDispatcher and forward to game.jspx to display boggle game
       RequestDispatcher displayGame = getServletContext().getNamedDispatcher("Game");
          displayGame.forward(request, response);
     } else if(path.equals("/score")) {
       String wordsFound = request.getParameter("wordsFound");
       session.setAttribute("wordsFound", wordsFound);
       session.setAttribute("showScores", true);
       BoggleBean gameToScore = (BoggleBean)session.getAttribute("boggleGame");
       gameToScore.setGameMap(wordsFound);       
       //score the game and display game scores/percentage
       RequestDispatcher displayGame = getServletContext().getNamedDispatcher("Game");
            displayGame.forward(request, response);
     } else {
       RequestDispatcher displaySignIn = getServletContext().getNamedDispatcher("signIn");
            displaySignIn.forward(request, response);
     }
    }

   }
   
 }