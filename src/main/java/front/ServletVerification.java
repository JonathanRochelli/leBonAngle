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
@WebServlet(name = "ServletVerification", urlPatterns = {"/verification"})
public class ServletVerification extends HttpServlet {
    
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
            
            String email = request.getParameter("EMAIL");
            String password = request.getParameter("PASSWORD");
            
            DataSource ds = getDataSource();
            Connection con = ds.getConnection();
            Statement ps = con.createStatement();
            
            try
            {
                //ps.executeUpdate("DROP TABLE UTILISATEUR");
                ps.executeUpdate("CREATE TABLE UTILISATEUR (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, NOM VARCHAR(30), PRENOM VARCHAR(30), EMAIL VARCHAR(30) NOT NULL UNIQUE, PASSWORD VARCHAR(30))");
                con.createStatement();
            }
            catch (Exception ex)
            {
                // Table d?j? existante
                System.out.println("La table n'existait pas");
            }

            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) as nb FROM UTILISATEUR WHERE EMAIL = ? AND PASSWORD = ?");
            ps2.setString(1, email);
            ps2.setString(2, password);
          
            ResultSet rs = ps2.executeQuery();
            rs.next();
            String chaine = String.valueOf(rs.getString("nb"));
            
            if (Integer.parseInt(chaine) >= 1) request.getSession(true);
            else {
                
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
                out.println("<h1 class=\"text-light\">Erreur de connexion</h1>");
                out.println("<p class=\"lead text-light\">Email ou mot de passe est incorrect</p>");
                out.println("<a class=\"btn btn-lg btn-light text-danger\" href=\"./\" role=\"button\">Se connecter</a>");
                out.println("<a class=\"btn btn-lg btn-light text-danger\" href=\"front/register.html\" role=\"button\">S'inscrire</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }          

            
        } catch (SQLException ex) {
            Logger.getLogger(ServletVerification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletVerification.class.getName()).log(Level.SEVERE, null, ex);
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
