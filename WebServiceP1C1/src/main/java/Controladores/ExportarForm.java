/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import backend.Almacenamiento;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sergi
 */
@WebServlet(name = "ExportarForm", urlPatterns = {"/ExportarForm"})
public class ExportarForm extends HttpServlet {
    private final int ARBITARY_SIZE = 1048;


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
        String idForm = request.getParameter("id_form");
        Almacenamiento almacenamiento = new Almacenamiento();
        String form = almacenamiento.getEstructuraForm(idForm);        
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=Estructura_Form_"+idForm+".form");           

        try(InputStream in = new ByteArrayInputStream(form.getBytes(StandardCharsets.UTF_8));
          OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[ARBITARY_SIZE];
        
            int numBytesRead;
            
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
        //download="Estructura_Form_${solicitud}.txt"
    }
}
