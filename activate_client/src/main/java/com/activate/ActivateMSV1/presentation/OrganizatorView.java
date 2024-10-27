package com.activate.ActivateMSV1.presentation;

import com.activate.ActivateMSV1.infra.DTO.*;
import com.activate.ActivateMSV1.infra.util.GUIVerifier;
import com.activate.ActivateMSV1.service.EventService;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OrganizatorView {

    private JPanel OrganizatorPanel;
    private JComboBox cbxEvent;
    private JTextField tfName;
    private JButton btnCreateEvent;
    private JButton button2;
    private JButton button3;
    private JButton btnAddInterest;
    private JButton button5;
    private JTextArea textArea1;
    private JButton button6;
    private JButton button7;
    private JLabel lbSelEvent;
    private JLabel lbName;
    private JLabel lbDescription;
    private JLabel lbMaxCapacity;
    private JLabel lbDuration;
    private JLabel lbDate;
    private JLabel lbType;
    private JLabel lbLatitude;
    private JLabel lbLongitude;
    private JLabel lbInterests;
    private JLabel lbInterAdded;
    private JLabel lbInterEvent;
    private JTextField tfDescription;
    private JTextField tfMaxCapacity;
    private JTextField tfDuration;
    private JTextField tfDate;
    private JComboBox cbxType;
    private JTextField tfLatitude;
    private JTextField tfLongitude;
    private JComboBox cbxInterests;

    private JFrame loginFrame;
    private JFrame frame;

    private ArrayList<InterestDTO> interests = new ArrayList<>();

    Map<String, InterestDTO> interestMap = new HashMap<>();

    Long organizerId;

    public OrganizatorView(JFrame loginFrame, Long organizerId) {

        this.loginFrame = loginFrame;
        this.organizerId = organizerId;

        frame = new JFrame("Organizator");
        frame.setContentPane(OrganizatorPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        interestMap.put("Videojuegos", InterestDTO.VIDEOGAMES);
        interestMap.put("Política", InterestDTO.POLITICS);
        interestMap.put("Gastronomía", InterestDTO.GASTRONOMY);
        interestMap.put("Deportes", InterestDTO.SPORTS);
        interestMap.put("Tecnología", InterestDTO.TECHNOLOGY);
        interestMap.put("Música", InterestDTO.MUSIC);
        interestMap.put("Cine", InterestDTO.CINEMA);
        interestMap.put("Literatura", InterestDTO.LITERATURE);
        interestMap.put("Ciencia", InterestDTO.SCIENCE);
        interestMap.put("Historia", InterestDTO.HISTORY);
        interestMap.put("Arte", InterestDTO.ART);

        initializeComponents();
        btnCreateEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEvent();
            }
        });

        btnAddInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInterest();
            }
        });
    }
    public void show() {
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginFrame.setVisible(true);
            }
        });
    }

    private void initializeComponents(){
        DefaultComboBoxModel<String> typeModel = new DefaultComboBoxModel<>();
        typeModel.addElement("Seleccione una opción");
        typeModel.addElement("Privado");
        typeModel.addElement("Publico");
        cbxType.setModel(typeModel);

        DefaultComboBoxModel<String> interestModel = new DefaultComboBoxModel<>();
        interestModel.addElement("Seleccione una opción");
        interestModel.addElement("Videojuegos");
        interestModel.addElement("Política");
        interestModel.addElement("Gastronomía");
        interestModel.addElement("Deportes");
        interestModel.addElement("Tecnología");
        interestModel.addElement("Música");
        interestModel.addElement("Cine");
        interestModel.addElement("Literatura");
        interestModel.addElement("Ciencia");
        interestModel.addElement("Historia");
        interestModel.addElement("Arte");
        cbxInterests.setModel(interestModel);
    }

    private void addInterest(){
        if(GUIVerifier.isComboBoxNotSelected(cbxInterests, "Seleccione un interés")){
            return;
        }
        String selectedInterest = (String) cbxInterests.getSelectedItem();
        InterestDTO interest = interestMap.get(selectedInterest);
        if (interest != null && !interests.contains(interest)) {
            interests.add(interest);
            lbInterEvent.setText(lbInterEvent.getText() + selectedInterest + ", ");
        }
    }

    private void createEvent(){

        if(GUIVerifier.isTextFieldEmpty(tfName, "Ingrese un nombre") ||
                GUIVerifier.isTextFieldEmpty(tfDescription, "Ingrese una descripción") ||
                GUIVerifier.isTextFieldEmpty(tfMaxCapacity, "Ingrese una capacidad máxima") ||
                GUIVerifier.isTextFieldNotNumeric(tfMaxCapacity, "La capacidad máxima debe ser un número") ||
                GUIVerifier.isTextFieldEmpty(tfDuration, "Ingrese una duración") ||
                GUIVerifier.isTextFieldNotNumeric(tfDuration, "La duración debe ser un número") ||
                GUIVerifier.isTextFieldEmpty(tfDate, "Ingrese una fecha") ||
                GUIVerifier.isComboBoxNotSelected(cbxType, "Seleccione un tipo de evento")  ||
                GUIVerifier.isTextFieldEmpty(tfLatitude, "Ingrese una latitud") ||
                GUIVerifier.isTextFieldNotNumeric(tfLatitude, "La latitud debe ser un número") ||
                GUIVerifier.isTextFieldEmpty(tfLongitude, "Ingrese una longitud") ||
                GUIVerifier.isTextFieldNotNumeric(tfLongitude, "La longitud debe ser un número")){
            return;
        }

        String name = tfName.getText();
        String description = tfDescription.getText();
        int maxCapacity = Integer.parseInt(tfMaxCapacity.getText());
        int duration = Integer.parseInt(tfDuration.getText());
        //LocalDateTime date = LocalDateTime.parse(tfDate.getText());
        double latitude = Double.parseDouble(tfLatitude.getText());
        double longitude = Double.parseDouble(tfLongitude.getText());

        LocationDTO location = new LocationDTO(latitude, longitude);

        String selectedType = (String) cbxType.getSelectedItem();
        EventTypeDTO type = selectedType.equals("Privado") ? EventTypeDTO.PRIVATE : EventTypeDTO.PUBLIC;

        HashSet<InterestDTO> interests = new HashSet<>(this.interests);

        EventInfoDTO event = new EventInfoDTO(
                1L,
                maxCapacity,
                duration,
                name,
                description,
                LocalDateTime.now().plusDays(1),
                location,
                StateDTO.OPEN,
                type,
                "",
                interests
        );

        try{
            boolean response = EventService.postEvent(event, 1L);
            if(response){
                GUIVerifier.showMessage("Evento creado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

}
