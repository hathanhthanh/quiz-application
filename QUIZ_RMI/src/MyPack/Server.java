package MyPack;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;



public class Server extends UnicastRemoteObject implements ServerInterface {
	private static final long serialVersionUID = 1L;
	public Server() throws RemoteException
	{		
	}
	public static void main(String [] args)
	{
		try
		{
			LocateRegistry.createRegistry(1099);
			Server server = new Server();
			Naming.rebind("QuizServer", server);
			System.out.println("Server is ready and running.....");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	@Override
	public Details[] getDetails()
	{
		Details[] data = Details.getInstance();
		System.out.println(data[0].question);
		return data;
	}
}