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
import java.util.Timer;
import java.util.TimerTask;

public class OrganizatorView {

    private JPanel OrganizatorPanel;
    private JComboBox cbxEvent;
    private JTextField tfName;
    private JButton btnCreateEvent;
    private JButton btnCancelEvent;
    private JButton btnUpdateEvent;
    private JButton btnAddInterest;
    private JButton btnSelectEvent;
    private JTextArea txaParticipants;
    private JButton btnStartEvent;
    private JButton btnFinishEvent;
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
    private JLabel lbOrganizerName;
    private JLabel lbState;
    private JTextField tfState;
    private JLabel tfParticipants;
    private JLabel lbEvaluations;
    private JTextArea txaEvaluations;
    private JButton btnCancelUpdate;

    private JFrame loginFrame;
    private JFrame frame;

    private ArrayList<InterestDTO> interests = new ArrayList<>();

    Map<String, InterestDTO> interestMap = new HashMap<>();

    Long organizerId;

    Long eventIdSelected;

    Boolean editMode;
    Boolean edditing;

    public OrganizatorView(JFrame loginFrame, Long organizerId, String organizerName) {

        this.loginFrame = loginFrame;
        this.organizerId = organizerId;
        this.lbOrganizerName.setText("Organizador: " + organizerName);
        this.eventIdSelected = -1L;
        this.editMode = false;
        this.edditing = false;

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

        tfState.setEditable(false);
        txaParticipants.setEditable(false);
        txaEvaluations.setEditable(false);
        btnCancelEvent.setVisible(false);


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
        btnSelectEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEvent();
            }
        });
        btnCancelEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelEvent();
            }
        });
        btnStartEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEvent();
            }
        });
        btnFinishEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishEvent();
            }
        });
        btnUpdateEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEvent();
            }
        });
        btnCancelUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelUpdate();
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
        typeModel.addElement("PRIVATE");
        typeModel.addElement("PUBLIC");
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

        initializeCbxEvent();
    }

    private void initializeCbxEvent(){
        try{
            ArrayList<EventDTO> events = EventService.getEventsByOrganizer(organizerId);
            DefaultComboBoxModel<String> eventModel = new DefaultComboBoxModel<>();
            eventModel.addElement("Seleccione un evento");
            for(EventDTO event : events){
                eventModel.addElement(event.getId());
           }
            cbxEvent.setModel(eventModel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
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
        LocalDateTime date = LocalDateTime.parse(tfDate.getText());
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
                date,
                location,
                StateDTO.OPEN,
                type,
                "",
                interests
        );

        try{
            boolean response = EventService.postEvent(event, organizerId);
            if(response){
                GUIVerifier.showMessage("Evento creado exitosamente");
                eventIdSelected = -1L;
                this.interests.clear();
                lbInterEvent.setText("");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        initializeCbxEvent();
                    }
                }, 6000);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void showEvent(){
        if(GUIVerifier.isComboBoxNotSelected(cbxEvent, "Seleccione un evento")){
            return;
        }
        Long selectedEvent = Long.parseLong(cbxEvent.getSelectedItem().toString());
        try{
            EventDTO event = EventService.getEvent(selectedEvent);
            if(event != null){
                eventIdSelected = selectedEvent;

                tfName.setText(event.getName());
                tfDescription.setText(event.getDescription());
                tfMaxCapacity.setText(String.valueOf(event.getMaxCapacity()));
                tfDuration.setText(String.valueOf(event.getDuration()));
                tfDate.setText(event.getDate().toString());
                cbxType.setSelectedItem(event.getType().toString());
                tfLatitude.setText(String.valueOf(event.getLocation().getLatitude()));
                tfLongitude.setText(String.valueOf(event.getLocation().getLongitude()));
                lbInterEvent.setText(event.getInterests().toString());
                tfState.setText(event.getState().toString());

                StringBuilder participantsNames = new StringBuilder();
                for (Participant participant : event.getParticipants()) {
                    participantsNames.append(participant.getName()).append("\n");
                }
                txaParticipants.setText(participantsNames.toString());

                StringBuilder evaluations = new StringBuilder();
                for (EvaluationDTO evaluation : event.getEvaluations()) {
                    evaluations.append("Autor: " + evaluation.getAuthor() + " Puntuación: " + evaluation.getScore() + " Comentario: " + evaluation.getComment()).append("\n");
                }
                txaEvaluations.setText(evaluations.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void cancelEvent(){
        if(eventIdSelected == -1L){
            GUIVerifier.showMessage("Seleccione un evento");
            return;
        }
        try{
            boolean response = EventService.cancelEvent(organizerId, eventIdSelected);
            if(response){
                GUIVerifier.showMessage("Evento cancelado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void updateEvent (){
        if(eventIdSelected == -1L){
            GUIVerifier.showMessage("Seleccione un evento");
            return;
        }
        if(!edditing){
            edditing = true;
            btnUpdateEvent.setText("Guardar");
            btnCancelUpdate.setVisible(true);

            tfName.setEditable(false);
            tfDescription.setEditable(false);
            tfDuration.setEditable(false);
            tfLatitude.setEditable(false);
            tfLongitude.setEditable(false);
            cbxInterests.setEnabled(false);
        }else{
            EventDTO event;
            try{
                event = EventService.getEvent(eventIdSelected);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return;
            }
            if(event.getMaxCapacity() != Integer.parseInt(tfMaxCapacity.getText())){
                try{
                    boolean response = EventService.updateMaxCapacity(eventIdSelected, Integer.parseInt(tfMaxCapacity.getText()));
                    if(response){
                        GUIVerifier.showMessage("Capacidad máxima actualizada exitosamente");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    GUIVerifier.showMessage(e.getMessage());
                }
            }
            if(!event.getDate().equals(LocalDateTime.parse(tfDate.getText()))){
                try{
                    boolean response = EventService.updateDate(eventIdSelected, LocalDateTime.parse(tfDate.getText()));
                    if(response){
                        GUIVerifier.showMessage("Fecha actualizada exitosamente");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    GUIVerifier.showMessage(e.getMessage());
                }
            }
            if(event.getType().toString() != cbxType.getSelectedItem()){
                try{
                    boolean response = EventService.updateEventType(eventIdSelected);
                    if(response){
                        GUIVerifier.showMessage("Tipo de evento actualizado exitosamente");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    GUIVerifier.showMessage(e.getMessage());
                }
            }
            cancelUpdate();
        }
    }

    private void cancelUpdate(){
        tfName.setEditable(true);
        tfDescription.setEditable(true);
        tfDuration.setEditable(true);
        tfLatitude.setEditable(true);
        tfLongitude.setEditable(true);
        cbxInterests.setEnabled(true);

        edditing = false;
        btnUpdateEvent.setText("<html>Editar<br>evento</html>");
        btnCancelUpdate.setVisible(false);
    }

    private void startEvent(){
        if(eventIdSelected == -1L){
            GUIVerifier.showMessage("Seleccione un evento");
            return;
        }
        try{
            boolean response = EventService.startEvent(eventIdSelected);
            if(response){
                GUIVerifier.showMessage("Evento iniciado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void finishEvent(){
        if(eventIdSelected == -1L){
            GUIVerifier.showMessage("Seleccione un evento");
            return;
        }
        try{
            boolean response = EventService.finishEvent(eventIdSelected);
            if(response){
                GUIVerifier.showMessage("Evento finalizado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            GUIVerifier.showMessage(e.getMessage());
        }
    }

}
