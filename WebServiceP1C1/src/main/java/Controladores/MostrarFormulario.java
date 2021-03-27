/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import backend.Almacenamiento;
import backend.creacion.FormularioFunctions;
import backend.objetos.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sergi
 */
@WebServlet(name = "Formulario", urlPatterns = {"/Formulario"})
public class MostrarFormulario extends HttpServlet {

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
            out.println("<title>Formulario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>El formulario: " +request.getParameter("id_form") + " No se enuentra disponible</h1>");
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
        String idForm = request.getParameter("id_form");
        Almacenamiento almacenamiento = new Almacenamiento();
        FormularioFunctions funFormulario = new FormularioFunctions(almacenamiento.getForms());
        Formulario formulario = funFormulario.getFormulario(idForm);
        if (formulario != null) {
            List<Componente> listaComponentes = formulario.getListaComponentes();
            response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+formulario.getTitulo()+"</title>");            
            out.println("<link rel=\"stylesheet\" href=\"estilos/estilosForms/"+formulario.getTema()+".css\">");            
            out.println("</head>");           
            out.println("<body>");
            out.println("<div id=\"Contenido\">");
            out.println("<div class=\"caja\">");
            out.println("<h1>" +formulario.getTitulo() + "</h1>");
            out.println("<form action=\"hola\" method=\"POST\">");
            for (Componente listaComponente : listaComponentes) {
                dibujar(out,listaComponente);
            }            
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }            
        }else{
            processRequest(request, response);
        }        
    }
    private void dibujar(PrintWriter out, Componente componente){
        String clase = componente.getClase();
        String alineacion = modAlineacion(componente.getAlineacion());
        String textoVisible = componente.getTextoVisible();
        String requerido = modRequerido(componente.getRequerido());
        switch(clase){
            case "CAMPO_TEXTO":{
                out.println("<label>"+textoVisible+"</label>");
                out.println(" <input type=\"text\" name=\""+componente.getNombreCampo()+"\" "+requerido+" />");                
                break;
            }
            case "AREA_TEXTO":{
                out.println("<label>"+textoVisible+"</label>");               
                out.println(" <textarea rows=\""+componente.getFilas()+"\" cols=\""+componente.getColumnas()+"\" name=\""+componente.getNombreCampo()+"\" "+requerido+" ></textarea>");                
                break;
            }
            case "CHECKBOX":{
                out.println("<label>"+textoVisible+"</label>");
                String[] opciones = opciones(componente.getOpciones());
                for (String opcione : opciones) {
                    out.println(" <input type=\"checkbox\" name=\""+componente.getNombreCampo()+"\"  value=\""+opcione+"\" "+requerido+"> "+opcione+""); 
                }
                break;
            }
            case "RADIO":{
                out.println("<label>"+textoVisible+"</label>");
                String[] opciones = opciones(componente.getOpciones());
                for (String opcione : opciones) {
                    out.println(" <input type=\"radio\" name=\""+componente.getNombreCampo()+"\"  value=\""+opcione+"\" "+requerido+" /> "+opcione+""); 
                }              
                break;
            }
            case "FICHERO":{
                out.println("<label>"+textoVisible+"</label>");           
                out.println("  <input type=\"file\" name=\""+componente.getNombreCampo()+"\"   "+requerido+"/>");                
                break;
            }
            case "IMAGEN":{
                out.println("<label>"+textoVisible+"</label>");               
                out.println(" <input type=\"image\" id=\"id_etiqueta\" src=\""+componente.getUrl()+"\" "+requerido+" />");                
                break;
            }
            case "COMBO":{
                out.println("<label>"+textoVisible+"</label>");                
                out.println("<select name=\""+componente.getNombreCampo()+"\" "+requerido+" >");
                String[] opciones = opciones(componente.getOpciones());
                for (String opcione : opciones) {
                    out.println(" <option value=\""+opcione+"\"> "+opcione+"</option>"); 
                }                
                out.println("</select>");
                break;
            }
            case "BOTON":{
                out.println("<input type=\"submit\" value=\""+textoVisible+"\"/>");                
                break;
            }            
        }
    }
    
    private String modAlineacion(String alineacion){
        if (alineacion.equals("IZQUIERDA")) {
            return "left";
        }
        
        return null;
    }
    private String modRequerido(String requerido){
        if (requerido.equalsIgnoreCase("SI")) {
            return "required";
        }
        return "";
    }
    private String[] opciones(String opciones){
        String separador = Pattern.quote("|");
        String[] args = opciones.split(separador);        
        return args;
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
        String link = request.getParameter("id_form");
        response.sendRedirect(link);
    }

}
