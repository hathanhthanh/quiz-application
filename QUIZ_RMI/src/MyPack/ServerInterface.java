package MyPack;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
	public Details[] getDetails() throws RemoteException;
}