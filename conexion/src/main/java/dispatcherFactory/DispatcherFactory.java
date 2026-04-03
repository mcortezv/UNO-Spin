package dispatcherFactory;

import dispatcher.ColaDispatcher;
import dispatcher.Dispatcher;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import receptor.Receptor;

public class DispatcherFactory {
        public IDispatcher createDispatcher() {
            ColaDispatcher cola = new ColaDispatcher();
            return new Dispatcher(cola);
        }

        public IReceptor createReceptor(IReceptor receptorComponente) {
            return new Receptor(receptorComponente);
        }
    }

