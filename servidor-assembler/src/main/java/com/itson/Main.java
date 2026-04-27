package com.itson;
import blackboard.Blackboard;
import factory.BlackboardFactory;
import factory.IBlackboardFactory;
import servidor.ControlServidor;

public class Main {

    public static void main() {

        IConexionFactory factoryConexion = new FactoryConexion();
        IBlackboardFactory factoryBlackboard = new BlackboardFactory();
        IFactoryControlServidor factoryControlServidor = new FactoryControlServidor();

        Conexion conexion = factoryConexion.createConexion();
        Blackboard blackboard = factoryBlackboard.createBlackboard();
        ControlServidor controlServidor = factoryControlServidor.createControlServidor();

        blackboard.subscribe(controlServidor);
        conexion.subscribe(blackboard);

        controlServidor.iniciarServidor();
    }
}
