package controller;

import view.UserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.ServerSocket;

public class UserController {

    private UserView view;
    private String mySelection;
    private String opponentSelection;

    public UserController(UserView view) {
        this.view = view;
        this.view.addPedraButtonListener(new PedraButtonListener());
        this.view.addPapelButtonListener(new PapelButtonListener());
        this.view.addTesouraButtonListener(new TesouraButtonListener());

        // Thread to handle incoming connections
        new Thread(() -> startTcpServer()).start();
    }

    // Listener para o botão Pedra
    class PedraButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Pedra");
        }
    }

    // Listener para o botão Papel
    class PapelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Papel");
        }
    }

    // Listener para o botão Tesoura
    class TesouraButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSelection("Tesoura");
        }
    }

    private void handleSelection(String selection) {
        mySelection = selection;
        String ip = view.getTextFieldValue();

        // Verificação básica do IP
        if (!isValidIPAddress(ip)) {
            view.showAlert("Endereço IP inválido!");
            return;
        }

        boolean tcpSelected = view.isCheckbox1Selected();
        boolean udpSelected = view.isCheckbox2Selected();

        if (tcpSelected) {
            handleTcpConnection(ip, selection);
        } else if (udpSelected) {
            handleUdpConnection(ip, selection);
        } else {
            view.showAlert("Nenhum protocolo foi selecionado!");
        }
    }

    private void handleTcpConnection(String ip, String selection) {
        try (Socket socket = new Socket(ip, 12345); // Porta padrão para o jogo
             OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write(selection.getBytes());
            view.showAlert("Enviado via TCP: " + selection + " para " + ip);
        } catch (IOException ex) {
            view.showAlert("Erro ao conectar via TCP: " + ex.getMessage());
        }
    }

    private void handleUdpConnection(String ip, String selection) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(ip);
            byte[] buf = selection.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 12345); // Porta padrão para o jogo
            socket.send(packet);
            view.showAlert("Enviado via UDP: " + selection + " para " + ip);
        } catch (IOException ex) {
            view.showAlert("Erro ao conectar via UDP: " + ex.getMessage());
        }
    }

    private void startTcpServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClientSocket(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.showAlert("Erro ao iniciar o servidor TCP: " + e.getMessage());
        }
    }

    private void handleClientSocket(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream()) {
            byte[] buffer = new byte[256];
            int bytesRead = inputStream.read(buffer);
            opponentSelection = new String(buffer, 0, bytesRead);
            view.showAlert("Recebido via TCP: " + opponentSelection);
            determineWinner();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void determineWinner() {
        if (mySelection != null && opponentSelection != null) {
            if (mySelection.equals(opponentSelection)) {
                view.showAlert("Empate!");
            } else if ((mySelection.equals("Pedra") && opponentSelection.equals("Tesoura")) ||
                    (mySelection.equals("Papel") && opponentSelection.equals("Pedra")) ||
                    (mySelection.equals("Tesoura") && opponentSelection.equals("Papel"))) {
                view.showAlert("Você venceu!");
            } else {
                view.showAlert("Você perdeu!");
            }

            // Reset choices
            mySelection = null;
            opponentSelection = null;
        }
    }

    // Verificação básica do endereço IP
    private boolean isValidIPAddress(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException ex) {
            return false;
        }
    }
}