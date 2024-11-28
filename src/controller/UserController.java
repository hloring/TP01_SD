package controller;

import view.UserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class UserController {

    private UserView view;
    private GameService gameService;
    private String mySelection;

    public UserController(UserView view) {
        this.view = view;
        this.view.addPedraButtonListener(new PedraButtonListener());
        this.view.addPapelButtonListener(new PapelButtonListener());
        this.view.addTesouraButtonListener(new TesouraButtonListener());

        try {
            LocateRegistry.createRegistry(1099);
            gameService = new GameServiceImpl();
            Naming.rebind("rmi://localhost/GameService", gameService);
        } catch (Exception e) {
            e.printStackTrace();
            view.showAlert("Erro ao iniciar o serviço RMI: " + e.getMessage());
        }
    }

    class PedraButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Pedra");
        }
    }

    class PapelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Papel");
        }
    }

    class TesouraButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Tesoura");
        }
    }

    private void handleSelection(String selection) {
        mySelection = selection;
        try {
            gameService.sendSelection(selection);
            view.showAlert("Enviado via RMI: " + selection);
            determineWinner();
        } catch (RemoteException e) {
            e.printStackTrace();
            view.showAlert("Erro ao enviar seleção via RMI: " + e.getMessage());
        }
    }

    private void determineWinner() {
        try {
            String opponentSelection = gameService.getOpponentSelection();
            if (mySelection.equals(opponentSelection)) {
                view.showAlert("Empate!");
            } else if ((mySelection.equals("Pedra") && opponentSelection.equals("Tesoura")) ||
                    (mySelection.equals("Papel") && opponentSelection.equals("Pedra")) ||
                    (mySelection.equals("Tesoura") && opponentSelection.equals("Papel"))) {
                view.showAlert("Você venceu!");
            } else {
                view.showAlert("Você perdeu!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            view.showAlert("Erro ao obter seleção do oponente via RMI: " + e.getMessage());
        }
    }
}