/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniogestionvuelos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anton
 */
public class VuelosLogica {
    
    public Connection conexion = null;
    
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/gestionvuelos","root", "");
            System.out.println("Conexion realizada");
            
        } catch (SQLException ex) {
            System.out.println("Error de conexion "+ ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VuelosLogica.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return conexion;
    }
    
    public String informaBD(){
        
        String texto = "";
        
        try{
            DatabaseMetaData dbmd=conexion.getMetaData();
            ResultSet tablas = dbmd.getTables(null, "gestionvuelos", null, null);
            String tabla = "";
            while(tablas.next()){
                tabla += "TABLA: "+tablas.getString("TABLE_NAME")+"\n";
            }
            
            texto += "INFORMACION SOBRE LA BASE DE DATOS \n\n"
                    + "NOMBRE: "+dbmd.getSchemas()+"\n"
                    + "DRIVER: "+dbmd.getDriverName()+"\n"
                    + "URL: "+dbmd.getURL()+"\n"
                    + "USUARIO: "+dbmd.getUserName()+"\n"
                    + tabla;    
            
        } catch (SQLException ex) {
            Logger.getLogger(VuelosLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    
    public String informaTabla(){
        String texto = "";
        try{
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet tablas = dbmd.getTables(null, "gestionvuelos", null, null);
            String tabla = "";
            while(tablas.next()){
                tabla += "INFORMACION SOBRE LAS TABLAS \n"
                        + "CATALOGO: "+tablas.getString("TABLE_CAT")+"\n"
                        + "ESQUEMA: "+tablas.getString(2)+"\n"
                        + "NOMBRE TABLA: "+tablas.getString("TABLE_NAME")+"\n"
                        + "TIPO: "+tablas.getString("TABLE_TYPE")+"\n\n";
            }
            texto = tabla;
        } catch (SQLException ex) {
            Logger.getLogger(VuelosLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    
    public String informaColumnas(String nomTabla){
        String texto = "";
        try{
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columna = dbmd.getColumns(null, "gestionvuelos", nomTabla, null);
         
            while(columna.next()){
                texto += "INFORMACION SOBRE LAS COLUMNAS DE LA TABLA \n"
                        + "NOMBRE: "+columna.getString("COLUMN_NAME")+"\n"
                        + "TIPO: "+columna.getString("TYPE_NAME")+"\n"
                        + "TAMAÑO: "+columna.getString("COLUMN_SIZE")+"\n"
                        + "ES NULA: "+columna.getString("IS_NULLABLE")+"\n\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(VuelosLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    
    public void cargaLista(VentanaPasajeros form){
        try{
            //crea un ResulSet para almacenar los datos que necesites de la tabla vuelos
            Statement stmt = (Statement) conexion.createStatement();
            ResultSet resultado = (ResultSet) stmt.executeQuery("SELECT cod_vuelo FROM vuelos");
            
            while (resultado.next()){
                form.cbCod.addItem(resultado.getString("cod_vuelo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VuelosLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
