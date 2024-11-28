package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServiceImpl extends UnicastRemoteObject implements GameService {

    private String opponentSelection;

    protected GameServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendSelection(String selection) throws RemoteException {
        this.opponentSelection = selection;
    }

    @Override
    public String getOpponentSelection() throws RemoteException {
        return opponentSelection;
    }
}