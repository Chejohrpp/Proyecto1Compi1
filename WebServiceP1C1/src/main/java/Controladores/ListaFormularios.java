/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import backend.Almacenamiento;
import backend.creacion.FormularioFunctions;
import backend.objetos.Formulario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sergi
 */
@WebServlet(name = "ListaFormularios", urlPatterns = {"/ListaFormularios"})
public class ListaFormularios extends HttpServlet {
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
        doPost(request,response);
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
        String userName = (String) request.getSession().getAttribute("userName");     
        if (userName == null) {
            response.sendRedirect("http://localhost:8080/WebServiceP1C1/");
        }else{
            List<String> nombreFormularios = new ArrayList();
            List<Formulario> formularios = almacenamiento.getForms();
            for (Formulario formulario : formularios) {
                if (formulario.getUsuarioCreacion().equals(userName)) {
                    nombreFormularios.add(formulario.getId());
                }
            }
            request.setAttribute("formularios", nombreFormularios);
            request.getRequestDispatcher("/admin/showFormularios.jsp").forward(request, response); 
        }
        
    }
}
