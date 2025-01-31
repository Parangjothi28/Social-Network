package org.example.services;

import org.example.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class PricingService extends JFrame{
    public PricingService() {
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
                Application.getInstance().getPricingService().setVisible(false);
            }
        });

        myPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle My Posts button click
                // JOptionPane.showMessageDialog(frame, "Navigating to My Posts");
                Application.getInstance().getPricingService().setVisible(true);
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Profile Update button click
                // JOptionPane.showMessageDialog(frame, "Navigating to Profile Update");
                Application.getInstance().getProfileUpdateService().setVisible(true);
                Application.getInstance().getPricingService().setVisible(false);
            }
        });

        // Add buttons to the navigation panel
        navigationPanel.add(homeButton);
        navigationPanel.add(myPostsButton);
        navigationPanel.add(profileButton);

        // Add navigation panel to the top of the frame
        this.getContentPane().add(navigationPanel, BorderLayout.NORTH);

        // Data for three sets of columns
        String[] columnSets = {
            // Set 1
            "<div><h4>Free</h4></div><div><h1>$0 <small'>/mo</small></h1><ul><li>10 follows included</li><li>15 GB of posts</li><li>Email support</li><li>Help center access</li></ul></div>",
            // Set 2
            "<div><h4>Pro</h4></div><div><h1>$10<small'>/mo</small></h1><ul><li>20 follows included</li><li>25 GB of posts</li><li>Premium email support</li><li>Help center access</li></ul></div>",
            // Set 3
            "<div><h4>Super Pro</h4></div><div><h1>$25<small'>/mo</small></h1><ul><li>Unlimited follows included</li><li>50 GB of posts</li><li>Priority phone and email support</li><li>Exclusive help center access</li></ul></div>"
        };

        // Create panels for each column set
        JPanel pricingDetails = new JPanel(new GridLayout(1, 3));

        for (int i=0; i<columnSets.length; i++) {
            String columnSet = columnSets[i];
            JButton button = new JButton("<html>" + columnSet + "</html>"); // Render HTML content
            button.setFocusPainted(false); // Remove button border
            button.setContentAreaFilled(false); 
            final int subscriptionAmount; // Variable to hold the subscription amount

    // Set subscription amount based on the button index
            switch (i) {
                case 0:
                    subscriptionAmount = 0;
                    break;
                case 1:
                    subscriptionAmount = 10;
                    break;
                case 2:
                    subscriptionAmount = 25;
                    break;
                default:
                    subscriptionAmount = 0; // Default to 0 if an unexpected index is encountered
            }

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                     Handle button click here
//                    String message = columnSet.substring(columnSet.indexOf("$")+1, columnSet.indexOf("$")+3);
//                    JOptionPane.showMessageDialog(Application.getInstance().getPricingService().getContentPane(), "Subscribed to : $" + message + "Package");
                    TransactionService transactionService = new TransactionService(subscriptionAmount);
                    transactionService.setVisible(true);
                }
            });
            pricingDetails.add(button);
        }
        this.getContentPane().add(pricingDetails);
        this.pack();
    }
}
