/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import backend.Almacenamiento;
import backend.creacion.DatoRegistrosFunctions;
import backend.creacion.FormularioFunctions;
import backend.objetos.Componente;
import backend.objetos.DatoRegistros;
import backend.objetos.Formulario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "GuardarDatos", urlPatterns = {"/GuardarDatos"})
public class GuardarDatos extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Datos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Se guardaron los datos exitosamente</h1>");
            out.println("<a href=\"http://localhost:8080/WebServiceP1C1/\">Regresar a inicio</a>");
            out.println("</body>");
            out.println("</html>");
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
        
        String idForm = request.getParameter("id_form");
        Almacenamiento almacenamiento = new Almacenamiento();
        FormularioFunctions funFormulario = new FormularioFunctions(almacenamiento.getForms());
        Formulario formulario = funFormulario.getFormulario(idForm);
        List<Componente> listaComponentes = formulario.getListaComponentes();
        List<DatoRegistros> listaDatos = formulario.getListaDatos();
        DatoRegistrosFunctions funDatos = new DatoRegistrosFunctions(listaDatos);
        for (Componente componente : listaComponentes) {
            if (!componente.getNombreCampo().equals("null") && !componente.equals("")) {
                String nombreCampo = request.getParameter(componente.getNombreCampo());                
                try{
                    if (!nombreCampo.equals("")) {
                        funDatos.addRegistro(verificarNull(nombreCampo), componente.getNombreCampo());
                    }
                }catch(Exception e){
                    //System.out.println("error: " + e.getMessage());
                }             
            }
        }
        formulario.setListaDatos(listaDatos);
        funFormulario.modListaDatos(formulario);
        almacenamiento.setForms(funFormulario.getListaForms());       
        processRequest(request, response);
    }
    
    private String verificarNull(String s){
        if (s == null || s.equals("null")) {
            return "null";
        }
        return s;
    }
}
