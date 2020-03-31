/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ServletRegister", urlPatterns = {"/register"})
public class ServletRegister extends HttpServlet {
    
    private DataSource getDataSource () throws NamingException
    {
        // Chargement du service de nommage
        Context initCtx=null;
        try {
            initCtx = new InitialContext();
        } catch (NamingException ex) {
            System.out.println("Erreur de chargement du service de nommage");
        } 

        Object refRecherchee = initCtx.lookup("jdbc/__default");
        return (DataSource)refRecherchee;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            String prenom = request.getParameter("PRENOM");
            String nom = request.getParameter("NOM");
            String email = request.getParameter("EMAIL");
            String password = request.getParameter("PASSWORD");
            
            DataSource ds = getDataSource();
            Connection con = ds.getConnection();
            Statement ps = con.createStatement();
            
            try {
                
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO UTILISATEUR (NOM, PRENOM, EMAIL, PASSWORD) VALUES ( ?, ?, ?, ?)");
                ps2.setString(1, prenom);
                ps2.setString(2, nom);
                ps2.setString(3, email.toString());
                ps2.setString(4, password);

                ps2.executeUpdate();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
                out.println("<meta name=\"description\" content=\"\">");

                out.println("<title>Lebonangle - Inscription</title>");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("</head>");

                out.println("<body>");
                out.println("<div class=\"container\">");
                out.println("<div class=\"bg-success jumbotron mt-3\">");
                out.println("<h1 class=\"text-light\">Bienvenue sur lebonangle</h1>");
                out.println("<p class=\"lead text-light\">Votre inscription a été accéptée</p>");
                out.println("<a class=\"btn btn-lg btn-light text-success\" href=\"./\" role=\"button\">Se connecter</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
                
            
            }catch (Exception ex)
            {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
                out.println("<meta name=\"description\" content=\"\">");

                out.println("<title>Lebonangle - Inscription</title>");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("</head>");

                out.println("<body>");
                out.println("<div class=\"container\">");
                out.println("<div class=\"bg-danger jumbotron mt-3\">");
                out.println("<h1 class=\"text-light\">Une erreur est survenue</h1>");
                out.println("<p class=\"lead text-light\">Un compte est déjà associé à cette adresse email</p>");
                out.println("<a class=\"btn btn-lg btn-light text-danger\" href=\"./\" role=\"button\">Se connecter</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
  
        } catch (SQLException ex) {
            Logger.getLogger(ServletRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
