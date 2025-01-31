package org.example.services;

import oracle.swing.SpringUtilities;
import org.example.Application;
import org.example.UserType;
import org.example.entities.User;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Random;

public class LoginService extends JFrame implements ActionListener {
    private JTextField txtUserEmailId = new JTextField(10);
    private JTextField txtUserPassword = new JTextField(10);
    private JTextField txtPassword = new JTextField(10);
    private JTextField txtUserName = new JTextField(10);
    private JTextField txtEmailId = new JTextField(10);
    private JTextField txtAddress = new JTextField(10);
    private JTextField txtDOBirth = new JTextField(10);

    private JButton btnSignIn = new JButton("SignIn");
    private JButton btnSignUp = new JButton("SignUp");

    public JTextField getTxtUserEmailId() {
        return txtUserEmailId;
    }

    public LoginService() {
        this.setSize(400, 500); // Increased height to accommodate both sections.
        this.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Social Network App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Main panel containing sign-in and sign-up sections
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sign In Section
        JPanel signInPanel = new JPanel(new SpringLayout());
        signInPanel.setBorder(BorderFactory.createTitledBorder("Sign In"));
        signInPanel.add(new JLabel("User Email Id:"));
        signInPanel.add(txtUserEmailId);
        signInPanel.add(new JLabel("Password:"));
        signInPanel.add(txtUserPassword);
        SpringUtilities.makeCompactGrid(signInPanel,
                2, 2, // rows, cols
                4, 6, // initX, initY
                6, 6); // xPad, yPad
        mainPanel.add(signInPanel);

        // Sign Up Section
        JPanel signUpPanel = new JPanel(new SpringLayout());
        signUpPanel.setBorder(BorderFactory.createTitledBorder("Sign Up"));
        signUpPanel.add(new JLabel("User Name:"));
        signUpPanel.add(txtUserName);
        signUpPanel.add(new JLabel("Password:"));
        signUpPanel.add(txtPassword);
        signUpPanel.add(new JLabel("Email ID:"));
        signUpPanel.add(txtEmailId);
        signUpPanel.add(new JLabel("Address:"));
        signUpPanel.add(txtAddress);
        SpringUtilities.makeCompactGrid(signUpPanel,
                4, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad
        mainPanel.add(signUpPanel);

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Sign In and Sign Up Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnSignIn);
        buttonPanel.add(btnSignUp);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        btnSignIn.addActionListener(this);
        btnSignUp.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Social Network Application");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getBtnSignIn()) {
            signIn();
        } else {
            signUp();
        }
    }

    private void signIn() {
        String userEmail = this.getTxtUserEmailId().getText().trim();
        String password = this.getTxtUserPassword().getText().trim();

        System.out.println("Login with userEmail = " + userEmail + " and password = " + password);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/social-network/signin"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("signinEmail=" + userEmail + "&signinPassword=" + password))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

        // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(responseBody);
                boolean authenticated = jsonResponse.getBoolean("authenticated");

                if (authenticated) {
                    JSONObject jsonUser = jsonResponse.getJSONObject("user");
                    String userName = jsonUser.getString("userName");
                    String userpassword = jsonUser.getString("password");
                    String address = jsonUser.getString("address");
                    String emailId = jsonUser.getString("emailId");
                    String userType = jsonUser.getString("userType");
                    String dateOfBirth = jsonUser.getString("dateOfBirth");
                    int id = jsonUser.getInt("id");
                    System.err.println("ID:"+id);
                    // Create a User object with the extracted information
                    User user = new User();
                    user.setUserName(userName);
                    user.setAddress(address);
                    user.setPassword(userpassword);
                    user.setFullName(userName);
                    user.setEmailId(emailId);
                    user.setUserType(userType);
                    user.setDateOfBirth(dateOfBirth);
                    user.setId(id);


                    Application.getInstance().setCurrentUser(user);
                    this.setVisible(false);
                    Application.getInstance().setPostService(new PostService());
                    Application.getInstance().getPostService().setVisible(true);
                    Application.getInstance().getPostService().updateMessageTextArea();
                } else {
                    // Handle sign-in failure
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void signUp() {
        String userName = this.getTxtUserName().getText().trim();
        String address = this.getTxtAddress().getText().trim();
        String userEmail = this.getTxtEmailId().getText().trim();
        String password = this.getTxtPassword().getText().trim();

        System.out.println("Signup with userEmail = " + userEmail + " and password = " + password);
        // Optional<User> optionalUser = Application.getInstance().getDataBaseService().loadUser(userEmail);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/social-network/signup"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("name=" + userName + "&email=" + userEmail+"&password="+ password))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

            // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(responseBody);
                boolean authenticated = jsonResponse.getBoolean("authenticated");

                if (!authenticated) {
                    JOptionPane.showMessageDialog(null, "The user with same email id exist!");
                } else {
                    JSONObject jsonUser = jsonResponse.getJSONObject("user");
                    String userType = jsonUser.getString("userType");
                    String dateOfBirth = jsonUser.getString("dateOfBirth");
                    int id = jsonUser.getInt("id");

                    // Create a User object with the extracted information
                    User user = new User();
                    user.setUserName(userName);
                    user.setAddress(address);
                    user.setPassword(password);
                    user.setFullName(userName);
                    user.setEmailId(userEmail);
                    user.setUserType(userType);
                    user.setDateOfBirth(dateOfBirth);
                    user.setId(id);

                    Application.getInstance().setCurrentUser(user);
                    this.setVisible(false);
                    Application.getInstance().setPostService(new PostService());
                    Application.getInstance().getPostService().setVisible(true);
                    Application.getInstance().getPostService().updateMessageTextArea();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public JButton getBtnSignIn() {
        return btnSignIn;
    }

    public JButton getBtnSignUp() { return btnSignUp; }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtUserEmailId(JTextField txtUserEmailId) {
        this.txtUserEmailId = txtUserEmailId;
    }

    public void setTxtPassword(JTextField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JTextField getTxtEmailId() {
        return txtEmailId;
    }

    public void setTxtEmailId(JTextField txtEmailId) {
        this.txtEmailId = txtEmailId;
    }

    public JTextField getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(JTextField txtAddress) {
        this.txtAddress = txtAddress;
    }

    public JTextField getTxtDOBirth() {
        return txtDOBirth;
    }

    public void setTxtDOBirth(JTextField txtDOBirth) {
        this.txtDOBirth = txtDOBirth;
    }

    public void setBtnSignIn(JButton btnSignIn) {
        this.btnSignIn = btnSignIn;
    }

    public void setBtnSignUp(JButton btnSignUp) {
        this.btnSignUp = btnSignUp;
    }

    public JTextField getTxtUserPassword() {
        return txtUserPassword;
    }

    public void setTxtUserPassword(JTextField txtUserPassword) {
        this.txtUserPassword = txtUserPassword;
    }
}
