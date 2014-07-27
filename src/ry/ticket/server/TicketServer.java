package ry.ticket.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import ry.ticket.Compute;
import ry.ticket.Task;

/**
 *
 * @author ry
 */
public class TicketServer implements Compute {

    public static void main(String[] args) {
        String name = "Compute";
        String ud = System.getProperty("user.dir");
        System.setProperty("java.rmi.server.codebase", "http://192.168.0.10:4242/compute.jar");
        System.setProperty("java.security.policy", ud + "\\rmipolicy.txt");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Compute engine = new TicketServer();
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.getRegistry();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T executeTask(Task<T> task) throws RemoteException {
        return task.execute();
    }
}
