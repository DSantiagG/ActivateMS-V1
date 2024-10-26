package com.activate.ActivateMSV1.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    private JTextField textField1;
    private JButton btnLoginAsParticipant;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton button2;
    private JButton button3;
    private JPanel loginPanel;
    private JButton btnLoginAsOrganizator;

    private JFrame frame;

    public LoginView() {
        frame = new JFrame("Login");
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        btnLoginAsParticipant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Poner metodo que verifique si el usuario es participante, etc, etc

                ParticipantView participantView = new ParticipantView(frame);
                participantView.show();
                frame.setVisible(false);
            }
        });

        btnLoginAsOrganizator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Poner metodo que verifique si el usuario es participante, etc, etc

                OrganizatorView organizatorViewView = new OrganizatorView(frame, 1L);   //TODO: Cambiar el 1L por el id del organizador
                organizatorViewView.show();
                frame.setVisible(false);
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

}
