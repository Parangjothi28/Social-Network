package org.example.services;

import org.example.Application;
import org.example.entities.Post;
import org.example.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import javax.swing.*;

public class PostService extends JFrame {
    private JTextArea messageTextArea = new JTextArea(10, 30);
    private JTextField contentTitle = new JTextField(20);
    private JTextField contentField = new JTextField(20);
    private JButton postButton = new JButton("Post");

    public PostService(){
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(new JLabel("Social Network App"), BorderLayout.NORTH);

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
            }
        });

        myPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle My Posts button click
                // JOptionPane.showMessageDialog(frame, "Navigating to My Posts");
                Application.getInstance().getPricingService().setVisible(true);
                Application.getInstance().getPostService().setVisible(false);
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Profile Update button click
                // JOptionPane.showMessageDialog(frame, "Navigating to Profile Update");
                Application.getInstance().getProfileUpdateService().setVisible(true);
                Application.getInstance().getPostService().setVisible(false);
            }
        });

        // Add buttons to the navigation panel
        navigationPanel.add(homeButton);
        navigationPanel.add(myPostsButton);
        navigationPanel.add(profileButton);

        // Add navigation panel to the top of the frame
        this.getContentPane().add(navigationPanel, BorderLayout.NORTH);

        // Message Section
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createTitledBorder("Messages"));

        // Existing Posts
        JPanel postsPanel = new JPanel(new BorderLayout());
        postsPanel.setBorder(BorderFactory.createTitledBorder("Existing Posts"));
        JScrollPane postsScrollPane = new JScrollPane(messageTextArea);
        messageTextArea.setEditable(false);
        postsPanel.add(postsScrollPane, BorderLayout.CENTER);
        messagePanel.add(postsPanel, BorderLayout.CENTER);

        // Write Message
        JPanel writeMessagePanel = new JPanel(new FlowLayout());
        JLabel contentLabel = new JLabel("Content: "); // Label for content field
        JLabel titleLabel = new JLabel("Title: "); // Label for title field

        writeMessagePanel.add(titleLabel); // Add title label
        writeMessagePanel.add(contentTitle);
        writeMessagePanel.add(contentLabel); // Add content label
        writeMessagePanel.add(contentField);
        writeMessagePanel.add(postButton);
        postButton.addActionListener(e -> postMessage());
        messagePanel.add(writeMessagePanel, BorderLayout.SOUTH);

        this.getContentPane().add(messagePanel, BorderLayout.EAST);

        // Advertisements (Placeholder)
        JPanel adsPanel = new JPanel();
        adsPanel.setPreferredSize(new Dimension(200, 1)); // Adjust the size for ads
        adsPanel.setBorder(BorderFactory.createTitledBorder("Advertisements"));
        this.getContentPane().add(adsPanel, BorderLayout.WEST);
    }

    private void postMessage() {
        String message = contentField.getText().trim();
        String title = contentTitle.getText().trim();
        System.out.println("Trying to post "+message);
        try{
            if (!message.isEmpty()) {
                int userID = Application.getInstance().getCurrentUser().getId();
                System.err.println(userID);
                String encodedTitle = URLEncoder.encode(title, "UTF-8");
                String encodedContent = URLEncoder.encode(message, "UTF-8");

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/social-network/post?title=" + encodedTitle + "&content=" + encodedContent+"&userid="+userID))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .GET()
                        .build();
                System.out.println(request);
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.err.println(response.statusCode());
                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    boolean authenticated = jsonResponse.getBoolean("authenticated");
                    if(authenticated){
                        updateMessageTextArea();
                        contentField.setText("");
                        contentTitle.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Could not post!");
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateMessageTextArea(){
        try {
            int userID = Application.getInstance().getCurrentUser().getId();
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/social-network/getpost?userid="+userID))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JSONObject data = new JSONObject(responseBody);
                if (data.getBoolean("authenticated")) {
                    JSONArray postsArray = data.getJSONArray("posts");
                    StringBuilder sb = new StringBuilder();
    
                    for (int i = 0; i < postsArray.length(); i++) {
                        JSONObject post = postsArray.getJSONObject(i);
                        int username = post.getInt("userId");
                        sb.append("User: " + username).append("\n");
                        sb.append("Title: " + post.getString("title")).append("\n");
                        sb.append("Content: " + post.getString("content")).append("\n");
                        sb.append("---------------------------------------------------\n");
                    }
        
                        messageTextArea.setText(sb.toString());
                } else {
                    // Handle unauthenticated state
                    System.out.println("User not authenticated");
                }
            } else {
                // Handle non-200 status code
                System.out.println("Error fetching posts. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public void updateMessageTextArea() {
    //     StringBuilder sb = new StringBuilder();
    //     for (Post post : Application.getInstance().getDataBaseService().loadAllPosts()) {
    //         String username = Application.getInstance().getDataBaseService().loadUserById(post.getUserId()).get().getFullName();
    //         sb.append("User : "+username).append("\n");
    //         sb.append("Title: "+post.getTitle()).append("\n");
    //         sb.append("Content: "+post.getContent()).append("\n");
    //         sb.append("---------------------------------------------------\n");
    //     }
    //     messageTextArea.setText(sb.toString());
    // }
}
