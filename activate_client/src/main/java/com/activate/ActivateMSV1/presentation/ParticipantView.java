package com.activate.ActivateMSV1.presentation;

import com.activate.ActivateMSV1.infra.DTO.*;
import com.activate.ActivateMSV1.infra.util.GUIVerifier;
import com.activate.ActivateMSV1.service.EventService;
import com.activate.ActivateMSV1.service.NotificationConsumer;
import com.activate.ActivateMSV1.service.RecommendationService;
import com.activate.ActivateMSV1.service.UserService;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.activate.ActivateMSV1.infra.util.GUIVerifier.*;

public class ParticipantView {
    private JPanel ParticipantPanel;
    private JTabbedPane tbpParticipant;
    private JPanel pnlEvents;
    private JPanel pnlParticipanEvents;
    private JPanel pnlParticipantInfo;
    private JTextArea txaRecommendedEvents;
    private JComboBox cbxRecommendedEvents;
    private JButton btnJoinEvent;
    private JTextArea txaEventsNotification;
    private JButton btnCleanNotifications;
    private JComboBox cbxMyEvents;
    private JButton btnQuitEvent;
    private JComboBox cbxCalification;
    private JTextArea txaComment;
    private JButton btnSendEvaluation;
    private JTextField txtName;
    private JButton btnEditProfile;
    private JButton btnUpdateLocation;
    private JComboBox cbInterests;
    private JTextArea txaInterests;
    private JButton btnAddInterest;
    private JButton btnRemoveInterest;
    private JLabel lblStatus;
    private JTextField txtAge;
    private JTextField txtEmail;
    private JTextField txtLatitude;
    private JTextField txtLongitude;
    private JLabel lblRecommendedEvents;
    private JButton btnReloadRecommendedEvents;
    private JLabel lblJoinEvent;
    private JLabel lblNotifications;
    private JLabel ACTIVATELabel;
    private JLabel lblMyEvents;
    private JTextArea txaMyEvents;
    private JButton btnReloadMyEvents;
    private JLabel lblManageMyEvents;
    private JLabel lblEventEvaluation;
    private JLabel lblEvaluation;
    private JLabel lblComment;

    private ArrayList<EventInfoDTO> recommendedEvents;
    private ArrayList<EventInfoDTO> myEvents;
    NotificationConsumer notificationConsumer;

    private JFrame loginFrame;
    private JFrame frame;
    private UserDTO user;

    private Map<String, InterestDTO> interestMap = new HashMap<>();


    public ParticipantView(JFrame loginFrame,UserDTO user) {
        this.loginFrame = loginFrame;
        this.user = user;
        frame = new JFrame("Participant");
        frame.setContentPane(ParticipantPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        initMyInfo();
        initEventPanel();
        initMyEventsPanel();

        btnEditProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile();
            }
        });
        btnUpdateLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLocation();
            }
        });
        btnAddInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInterest();
            }
        });
        btnRemoveInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeInterest();
            }
        });
        btnReloadRecommendedEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadRecommendedEvents();
            }
        });
        btnJoinEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participate();
            }
        });
        btnCleanNotifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txaEventsNotification.setText("");
            }
        });
        btnReloadMyEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadMyEvents();
            }
        });
        btnQuitEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitEvent();
            }
        });
        btnSendEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEvaluation();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                notificationConsumer.closeConnection();
                loginFrame.setVisible(true);
            }
        });
    }

    private void initEventPanel(){
        reloadRecommendedEvents();
        notificationConsumer = new NotificationConsumer(user.getId());
        notificationConsumer.setTxaNotifications(txaEventsNotification);
        try {
            notificationConsumer.connectWithServer();
        } catch (Exception e) {
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void initMyEventsPanel(){
        reloadMyEvents();
        cbxCalification.addItem("Seleccione una calificación");
        cbxCalification.addItem("1");
        cbxCalification.addItem("2");
        cbxCalification.addItem("3");
        cbxCalification.addItem("4");
        cbxCalification.addItem("5");
    }

    private void reloadRecommendedEvents(){
        try{
            recommendedEvents = RecommendationService.getRecommendedEvents(user.getId());
        }catch (Exception e){
            cbxRecommendedEvents.removeAllItems();
            cbxRecommendedEvents.addItem("Seleccione un evento");
            txaRecommendedEvents.setText("");
            return;
        }

        String recommendedEventsString = "";
        cbxRecommendedEvents.removeAllItems();
        cbxRecommendedEvents.addItem("Seleccione un evento");

        for(EventInfoDTO event : recommendedEvents){
            recommendedEventsString += "El evento "+ event.getName()+" se realizará en ("+ event.getLocation().getLongitude() + " " + event.getLocation().getLatitude() +") el " + event.getDate() + ".\n\n";
            cbxRecommendedEvents.addItem(event.getName());
        }

        txaRecommendedEvents.setText("");
        txaRecommendedEvents.setText(recommendedEventsString);
    }

    private void participate(){
        if(GUIVerifier.isComboBoxNotSelected(cbxRecommendedEvents,"Seleccione un evento")) return;
        int index = cbxRecommendedEvents.getSelectedIndex();
        EventInfoDTO event = recommendedEvents.get(index-1);
        try {
            EventService.participate(user.getId(), event.getId());
            GUIVerifier.showMessage("Te has inscrito al evento "+ event.getName() + "\n");
            cbxMyEvents.setSelectedIndex(0);
        } catch (Exception e) {
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void quitEvent(){
        if(GUIVerifier.isComboBoxNotSelected(cbxMyEvents,"Seleccione un evento")) return;
        int index = cbxMyEvents.getSelectedIndex();
        EventInfoDTO event = myEvents.get(index-1);
        try {
            EventService.quitEvent(user.getId(), event.getId());
            GUIVerifier.showMessage("Te has desinscrito del evento "+ event.getName() + "\n");
            cbxMyEvents.setSelectedIndex(0);
        } catch (Exception e) {
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void sendEvaluation(){
        if(GUIVerifier.isComboBoxNotSelected(cbxMyEvents,"Seleccione un evento")) return;
        if(GUIVerifier.isComboBoxNotSelected(cbxCalification,"Seleccione una calificación")) return;
        if(txaComment.getText().isEmpty()){
            GUIVerifier.showMessage("Ingrese un comentario");
            return;
        }

        int index = cbxMyEvents.getSelectedIndex();
        EventInfoDTO event = myEvents.get(index-1);
        try {
            EventService.sendEvaluation(user.getId(), event.getId(), txaComment.getText(), Integer.parseInt(cbxCalification.getSelectedItem().toString()));
            GUIVerifier.showMessage("Evaluación enviada");
            txaComment.setText("");
            cbxCalification.setSelectedIndex(0);
            cbxMyEvents.setSelectedIndex(0);
        } catch (Exception e) {
            GUIVerifier.showMessage(e.getMessage());
        }
    }

    private void reloadMyEvents(){
        try {
            myEvents = EventService.getParticipantEvents(user.getId());
        } catch (Exception e) {
            cbxMyEvents.removeAllItems();
            cbxMyEvents.addItem("Seleccione un evento");
            txaMyEvents.setText("");
            return;
        }

        String nextEvents = "";
        String finishedEvents = "";

        cbxMyEvents.removeAllItems();
        cbxMyEvents.addItem("Seleccione un evento");

        for(EventInfoDTO event : myEvents){
            if(event.getState() == StateDTO.FINISHED){
                nextEvents += "El evento "+ event.getName()+" se realizó en ("+ event.getLocation().getLongitude() + " " + event.getLocation().getLatitude() +") el " + event.getDate() + ".\n\n";
            }else{
                finishedEvents += "El evento "+ event.getName()+" se realizará en ("+ event.getLocation().getLongitude() + " " + event.getLocation().getLatitude() +") el " + event.getDate() + ".\n\n";
            }
            cbxMyEvents.addItem(event.getName());
        }

        txaMyEvents.setText("");
        txaMyEvents.setText(nextEvents + "\n\n" + finishedEvents);
    }

    private void fillParticipantInfo(){
        txtName.setText(user.getName());
        txtAge.setText(String.valueOf(user.getAge()));
        txtEmail.setText(user.getEmail());
        txtLatitude.setText(String.valueOf(user.getLocation().getLatitude()));
        txtLongitude.setText(String.valueOf(user.getLocation().getLongitude()));
        String interestsString = "";
        for (InterestDTO interest : user.getInterests()) {
            interestsString += interest.toString() + "\n";
        }
        txaInterests.setText(interestsString);

    }
    private void initCBInterests(){
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
        cbInterests.setModel(interestModel);
    }
    private void initMyInfo()
    {
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
        fillParticipantInfo();
        initCBInterests();
    }

    private void editProfile(){
        UserDTO newProfile = new UserDTO();
        newProfile.setId(user.getId());
        if(isTextFieldEmpty(txtName,"El nombre es obligatorio"))return;
        if(isTextFieldEmpty(txtEmail,"El email es obligatorio"))return;
        if(isTextFieldNotPositiveNumeric(txtAge,"La edad debe ser numerica"))return;
        newProfile.setName(txtName.getText());
        newProfile.setAge(Integer.parseInt(txtAge.getText()));
        newProfile.setEmail(txtEmail.getText());
        try {
            UserService.updateProfile(newProfile);
            user.setName(newProfile.getName());
            user.setAge(newProfile.getAge());
            user.setEmail(newProfile.getEmail());
            fillParticipantInfo();
            lblStatus.setText("Perfil actualizado");
        } catch (Exception e) {
            lblStatus.setText(e.getMessage());
        }
    }

    private void updateLocation(){
        LocationDTO location = new LocationDTO();
        if(isTextFieldNotNumeric(txtLatitude,"La latitude debe ser numerica"))return;
        if(isTextFieldNotNumeric(txtLongitude,"La longitud debe ser numerica"))return;
        location.setLatitude(Double.parseDouble(txtLatitude.getText()));
        location.setLongitude(Double.parseDouble(txtLongitude.getText()));
        user.setLocation(location);
        try {
            UserService.updateLocation(user.getId(),location);
            fillParticipantInfo();
            lblStatus.setText("Ubicación actualizada");
        } catch (Exception e) {
            lblStatus.setText(e.getMessage());
        }
    }

    private void addInterest(){
        if(GUIVerifier.isComboBoxNotSelected(cbInterests,"Seleccione un interés"))return;
        String interest = cbInterests.getSelectedItem().toString();
        InterestDTO interestDTO = interestMap.get(interest);
        InterestRequestDTO request = new InterestRequestDTO();
        request.setInterest(interestDTO);
        try {
            UserService.addInterest(user.getId(),request);
            user.getInterests().add(interestDTO);
            fillParticipantInfo();
            lblStatus.setText("Interes agregado");
        } catch (Exception e) {
            lblStatus.setText(e.getMessage());
        }
    }

    private void removeInterest(){
        if(GUIVerifier.isComboBoxNotSelected(cbInterests,"Seleccione un interés"))return;
        String interest = cbInterests.getSelectedItem().toString();
        InterestDTO interestDTO = interestMap.get(interest);
        InterestRequestDTO request = new InterestRequestDTO();
        request.setInterest(interestDTO);
        try {
            UserService.removeInterest(user.getId(),request);
            user.getInterests().remove(interestDTO);
            fillParticipantInfo();
            lblStatus.setText("Interes eliminado");
        } catch (Exception e) {
            lblStatus.setText(e.getMessage());
        }
    }
}
