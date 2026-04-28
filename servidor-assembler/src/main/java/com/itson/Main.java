package com.itson;
import blackboard.Blackboard;
import factory.BlackboardFactory;
import factory.ConexionFactory;
import factory.ControlServidorFactory;
import interfaces.IBlackboardFactory;
import interfaces.IConexionFactory;
import interfaces.IControlServidorFactory;
import servidor.ControlServidor;

public class Main {

    public static void main() {

        IConexionFactory factoryConexion = new ConexionFactory();
        IBlackboardFactory factoryBlackboard = new BlackboardFactory();
        IControlServidorFactory factoryControlServidor = new ControlServidorFactory();

        Conexion conexion = factoryConexion.createConexion();
        Blackboard blackboard = factoryBlackboard.createBlackboard();
        ControlServidor controlServidor = factoryControlServidor.createControlServidor();

        blackboard.subscribe(controlServidor);
        conexion.subscribe(blackboard);

        controlServidor.iniciarServidor();
    }
}
