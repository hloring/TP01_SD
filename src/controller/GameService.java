package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameService extends Remote {
    void sendSelection(String selection) throws RemoteException;
    String getOpponentSelection() throws RemoteException;
}