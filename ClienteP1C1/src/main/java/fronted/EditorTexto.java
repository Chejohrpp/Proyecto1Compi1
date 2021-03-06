/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fronted;

import backend.Analizador;
import backend.toolsFronted.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author sergi
 */
public class EditorTexto extends javax.swing.JFrame {
    ArchivosOpciones archivoOpciones;
    private String userNameSesion=null;
    /**
     * Creates new form EditorTexto
     */
    public EditorTexto() {
        initComponents();
        LineNumberTextArea lineNumber = new LineNumberTextArea(jTextEditor);
        jScrollPane1.setRowHeaderView(lineNumber);
        jTextEditor.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lineNumber.updateLineNumbers();
            }
            
        });
        txtLineaColumna.setEnabled(false);
        LineaColumna lineaColumna = new LineaColumna(jTextEditor, txtLineaColumna);
        lineaColumna.actualizar();
        jTextRespuesta.setEnabled(false);
        archivoOpciones = new ArchivosOpciones(jTextEditor);
        this.setTitle(archivoOpciones.titulo());
        menuCerrarSesion.setVisible(false);
        modificarEdicion();
    }
    
    private void modificarEdicion(){
        menuCopiar.setAction(new DefaultEditorKit.CopyAction());
        menuCortar.setAction(new DefaultEditorKit.CutAction());
        menuPegar.setAction(new DefaultEditorKit.PasteAction());
        menuCopiar.setText("copiar           Ctrl+C");
        menuPegar.setText("Pegar            Ctrl+V");
        menuCortar.setText("Cortar           Ctrl+X");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextEditor = new javax.swing.JTextArea();
        txtLineaColumna = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextRespuesta = new javax.swing.JTextArea();
        lblResponse = new javax.swing.JLabel();
        lblSesion = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuCerrarSesion = new javax.swing.JMenuItem();
        menuEdicion = new javax.swing.JMenu();
        menuCopiar = new javax.swing.JMenuItem();
        menuPegar = new javax.swing.JMenuItem();
        menuCortar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSelectTodo = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        menuReportesAbrir = new javax.swing.JMenuItem();
        menuServidor = new javax.swing.JMenu();
        menuEnviarServidor = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1080, 720));

        jTextEditor.setColumns(20);
        jTextEditor.setRows(5);
        jScrollPane1.setViewportView(jTextEditor);

        txtLineaColumna.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextRespuesta.setColumns(20);
        jTextRespuesta.setRows(5);
        jScrollPane2.setViewportView(jTextRespuesta);

        lblResponse.setText("Respuestas:");

        jMenu4.setText("Archivo");

        menuNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoActionPerformed(evt);
            }
        });
        jMenu4.add(menuNuevo);

        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuAbrir.setText("Abrir ");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu4.add(menuAbrir);

        menuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        jMenu4.add(menuGuardar);
        jMenu4.add(jSeparator2);

        menuCerrarSesion.setText("Cerrar sesion");
        menuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarSesionActionPerformed(evt);
            }
        });
        jMenu4.add(menuCerrarSesion);

        jMenuBar2.add(jMenu4);

        menuEdicion.setText("Edicion");

        menuCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuCopiar.setText("Copiar");
        menuCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCopiarActionPerformed(evt);
            }
        });
        menuEdicion.add(menuCopiar);

        menuPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menuPegar.setText("Pegar");
        menuEdicion.add(menuPegar);

        menuCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menuCortar.setText("Cortar");
        menuEdicion.add(menuCortar);
        menuEdicion.add(jSeparator1);

        menuSelectTodo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuSelectTodo.setText("Seleccionar todo");
        menuSelectTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSelectTodoActionPerformed(evt);
            }
        });
        menuEdicion.add(menuSelectTodo);

        jMenuBar2.add(menuEdicion);

        menuReportes.setText("Reportes");
        menuReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReportesActionPerformed(evt);
            }
        });

        menuReportesAbrir.setText("Mostrar Datos");
        menuReportesAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReportesAbrirActionPerformed(evt);
            }
        });
        menuReportes.add(menuReportesAbrir);

        jMenuBar2.add(menuReportes);

        menuServidor.setText("Servidor");

        menuEnviarServidor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
        menuEnviarServidor.setText("Enviar Solicitud");
        menuEnviarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEnviarServidorActionPerformed(evt);
            }
        });
        menuServidor.add(menuEnviarServidor);

        jMenuBar2.add(menuServidor);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
                    .addComponent(txtLineaColumna)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblResponse)
                        .addGap(278, 278, 278)
                        .addComponent(lblSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLineaColumna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResponse)
                    .addComponent(lblSesion))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        try {
            // TODO add your handling code here:
            archivoOpciones.abrir(menuAbrir);
        } catch (IOException ex) {
            Logger.getLogger(EditorTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTitle(archivoOpciones.titulo());
        
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarSesionActionPerformed
        // TODO add your handling code here:
        menuCerrarSesion.setVisible(false);
        userNameSesion = null;
        lblSesion.setText("");
    }//GEN-LAST:event_menuCerrarSesionActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        // TODO add your handling code here:\
        archivoOpciones.guardar(menuGuardar);
        this.setTitle(archivoOpciones.titulo());
    }//GEN-LAST:event_menuGuardarActionPerformed

    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        // TODO add your handling code here:
        archivoOpciones.nuevo(menuNuevo);
        this.setTitle(archivoOpciones.titulo());
    }//GEN-LAST:event_menuNuevoActionPerformed

    private void menuSelectTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSelectTodoActionPerformed
        // TODO add your handling code here:
        jTextEditor.selectAll();
    }//GEN-LAST:event_menuSelectTodoActionPerformed

    private void menuCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCopiarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuCopiarActionPerformed

    private void menuReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReportesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuReportesActionPerformed

    private void menuReportesAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReportesAbrirActionPerformed
        // TODO add your handling code here:
        VisualReportes visualReportes = new VisualReportes();
        visualReportes.setLocationRelativeTo(null);
        visualReportes.setVisible(true);
    }//GEN-LAST:event_menuReportesAbrirActionPerformed

    private void menuEnviarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEnviarServidorActionPerformed
        // TODO add your handling code here:
        String datos = jTextEditor.getText();
        Analizador analizador = new Analizador(datos,jTextRespuesta,userNameSesion);
        analizador.analizar();
        userNameSesion = analizador.getUserNameSesion();
        if (userNameSesion != null) {
            menuCerrarSesion.setVisible(true);
            lblSesion.setText("Sesion iniciada: " + userNameSesion);
        }
        
    }//GEN-LAST:event_menuEnviarServidorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextArea jTextEditor;
    private javax.swing.JTextArea jTextRespuesta;
    private javax.swing.JLabel lblResponse;
    private javax.swing.JLabel lblSesion;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuCerrarSesion;
    private javax.swing.JMenuItem menuCopiar;
    private javax.swing.JMenuItem menuCortar;
    private javax.swing.JMenu menuEdicion;
    private javax.swing.JMenuItem menuEnviarServidor;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuPegar;
    private javax.swing.JMenu menuReportes;
    private javax.swing.JMenuItem menuReportesAbrir;
    private javax.swing.JMenuItem menuSelectTodo;
    private javax.swing.JMenu menuServidor;
    private javax.swing.JTextField txtLineaColumna;
    // End of variables declaration//GEN-END:variables
}
