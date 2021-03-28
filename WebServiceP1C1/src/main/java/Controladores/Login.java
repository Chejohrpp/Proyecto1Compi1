/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import backend.Almacenamiento;
import backend.creacion.UsuarioFunctions;
import backend.objetos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sergi
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    Almacenamiento almacenamiento = new Almacenamiento();

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
         try {
             request.setAttribute("success", 1);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            System.out.println("Login Error: " + e.getMessage());
            e.printStackTrace();
        }
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
        String userName = request.getParameter("user");
        String pass = request.getParameter("pass");
        UsuarioFunctions funUser = new UsuarioFunctions(almacenamiento.getUsuarios());
        Usuario user = funUser.verificarUser(new Usuario(userName,pass,null,null));
        if (user != null) {            
            request.getSession().setAttribute("userName", String.valueOf(user.getUserName()));
            response.sendRedirect("http://localhost:8080/WebServiceP1C1/");
        }else{
            request.setAttribute("success", 0);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        
        
    }

}
