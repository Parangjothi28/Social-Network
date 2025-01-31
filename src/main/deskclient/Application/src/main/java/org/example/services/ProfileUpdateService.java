package org.example.services;

import org.example.Application;
import org.example.entities.User;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProfileUpdateService extends JFrame{
    private JTextField displayNameField;
    private JTextField fullnameField;
    // private JLabel profilePictureLabel;
    private JTextField dobField;

    public ProfileUpdateService() {
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        JPanel navigationPanel = new JPanel();
        JButton homeButton = new JButton("Posts");
        JButton myPostsButton = new JButton("Pricing");
        JButton profileButton = new JButton("Profile Update");

        // Action listeners for buttons
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Home button click
                Application.getInstance().getPostService().setVisible(true);
                Application.getInstance().getProfileUpdateService().setVisible(false);
            }
        });

        myPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle My Posts button click
                // JOptionPane.showMessageDialog(frame, "Navigating to My Posts");
                Application.getInstance().getPricingService().setVisible(true);
                Application.getInstance().getProfileUpdateService().setVisible(false);
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Profile Update button click
                // JOptionPane.showMessageDialog(frame, "Navigating to Profile Update");
                Application.getInstance().getProfileUpdateService().setVisible(true);
            }
        });

        // Add buttons to the navigation panel
        navigationPanel.add(homeButton);
        navigationPanel.add(myPostsButton);
        navigationPanel.add(profileButton);

        // Add navigation panel to the top of the frame
        this.getContentPane().add(navigationPanel, BorderLayout.NORTH);
        
        JPanel profileUpdatePanel = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel displayNameLabel = new JLabel("Display Name:");
        displayNameField = new JTextField(30);
        panel1.add(displayNameLabel);
        panel1.add(displayNameField);
        JPanel panel2 = new JPanel();
        JLabel fullnameLabel = new JLabel("Full Name:");
        fullnameField = new JTextField(30);
        panel1.add(fullnameLabel);
        panel1.add(fullnameField);
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobField = new JTextField(30);
        JPanel panel3 = new JPanel();
        panel3.add(dobLabel);
        panel3.add(dobField);

        // frame.add(displayNameLabel);
        // frame.add(displayNameField);
        // frame.add(new JLabel());
        // frame.add(profilePictureTextLabel);
        // frame.add(profilePictureLabel);
        // frame.add(uploadButton); 
        // frame.add(dobLabel);
        // frame.add(dobField);
        
        profileUpdatePanel.add(panel1);
        profileUpdatePanel.add(panel2);
        profileUpdatePanel.add(panel3);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String displayName = displayNameField.getText().trim();
                String fullName = fullnameField.getText().trim();
                String dob = dobField.getText();
                // System.out.println("Display Name: " + displayName);
                // System.out.println("Profile Picture: " + profilePicture);
                // System.out.println("Date of Birth: " + dob);
                User user =  Application.getInstance().getCurrentUser();
                int userid = user.getId();
                String userEmail = user.getEmailId();
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/social-network/profile"))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString("name=" + displayName + "&full_name=" + fullName+"&dob="+dob+"&useremail="+userEmail+"&userid="+userid))
                            .build();
        
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);
                    if (response.statusCode() == 200) {
                        String responseBody = response.body();
        
                // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        boolean authenticated = jsonResponse.getBoolean("profilestatus");
        
                        if (authenticated) {
                            JOptionPane.showMessageDialog(null, "Profile has been updated!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Could not update profile!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Could not update profile!");
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        profileUpdatePanel.add(updateButton);
        this.getContentPane().add(profileUpdatePanel, BorderLayout.CENTER);

        this.pack();
    }
}
