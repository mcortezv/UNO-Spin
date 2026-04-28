package com.itson;
import factory.ConexionFactory;
import factory.MVCFactory;
import interfaces.IConexionFactory;
import interfaces.IMVCFactory;

public class Main {

    public static void main() {

        IMVCFactory factoryMVC = new MVCFactory();
        IConexionFactory factoryConexion = new ConexionFactory();

        MVC mvc = factoryMVC.createMVC();
        Conexion conexion = factoryConexion.createConexion();

        mvc.subscribe(conexion);
        conexion.subscribe(mvc);

        mvc.iniciar();

    }
}
