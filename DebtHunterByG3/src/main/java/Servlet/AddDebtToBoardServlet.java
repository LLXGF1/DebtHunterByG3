/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;



import Entity.Debts;
import Entity.Users;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GuideKai
 */
@WebServlet(name = "AddDebtToBoardServlet", urlPatterns = {"/AddDebtToBoard"})
public class AddDebtToBoardServlet extends HttpServlet {

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_DebtHunterByG3_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        String debtName = request.getParameter("name") ;
        String debtMail = request.getParameter("email") ;
        String description = request.getParameter("desc") ;
        String c = request.getParameter("cost") ;
        int cost = Integer.parseInt(c) ;
//        Users u = em.createQuery("SELECT u from Users u WHERE u.email = :email", Users.class)
//                .setParameter("email", debtMail).getSingleResult() ;        
        Users u = em.find(Users.class, 1) ;
        if (u != null) {
//            em.createNamedQuery("INSERT INTO Debts (debt_name, debtor_mail, Description, Cost) VALUES (?,?,?,?)")
//                .setParameter("debtname", debtName)
//                .setParameter("debtmail", debtMail)
//                .setParameter("description", description)
//                .setParameter("cost", cost) 
//                .executeUpdate() ;  
            em.getTransaction().begin() ; 
            Debts d = new Debts() ;
            d.setDebtName(debtName);
            d.setDebtorMail(debtMail);
            d.setDescription(description);
            d.setCost(cost);
            d.setUsersId(u); ;
            em.persist(d) ;
            em.close() ;
            request.setAttribute("debtname", debtName);
            request.setAttribute("debtmail", debtMail);
            request.setAttribute("description", description);
            request.setAttribute("cost", cost);            
            request.getRequestDispatcher("/WEB-INF/TestAdd.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Try again!!");
            request.getRequestDispatcher("/WEB-INF/Debt.jsp").forward(request, response);
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
