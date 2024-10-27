package com.activate.ActivateMSV1.presentation;

import com.activate.ActivateMSV1.infra.DTO.InterestDTO;
import com.activate.ActivateMSV1.infra.DTO.InterestRequestDTO;
import com.activate.ActivateMSV1.infra.DTO.LocationDTO;
import com.activate.ActivateMSV1.infra.DTO.UserDTO;
import com.activate.ActivateMSV1.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JTextArea textArea1;
    private JComboBox comboBox1;
    private JButton button2;
    private JTextArea textArea2;
    private JButton button3;
    private JComboBox comboBox2;
    private JButton button1;
    private JComboBox comboBox3;
    private JTextArea textArea3;
    private JButton button4;
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

    private void fillParticipantInfo(){
        txtName.setText(user.getName());
        txtAge.setText(String.valueOf(user.getAge()));
        txtEmail.setText(user.getEmail());
        txtLatitude.setText(String.valueOf(user.getLocation().getLatitude()));
        txtLongitude.setText(String.valueOf(user.getLocation().getLongitude()));
        String interestsString = "";
        for (InterestDTO interest : user.getInterests()) {
            interestsString += interest.toString() + "\n";
            System.out.println("Entra");
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
        if(isComboBoxNotSelected(cbInterests,"Seleccione un interés"))return;
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
        if(isComboBoxNotSelected(cbInterests,"Seleccione un interés"))return;
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
