package com.itson;

public class Main {

    public static void main() {

        IMVCFactory factoryMVC = new MVCFactory();
        IFactoryConexion factoryConexion = new FactoryConexion();

        MVC mvc = factoryMVC.createMVC();
        Conexion conexion = factoryConexion.createConexion();

        mvc.subscribe(conexion);
        conexion.subscribe(mvc);

        mvc.iniciar();

    }
}
