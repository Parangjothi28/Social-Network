package org.example.services;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;

public class TransactionService extends JFrame implements ActionListener {
    private JTextField txtCreditCardNumber  = new JTextField(10);
    private JTextField txtExpMonth  = new JTextField(10);
    private JTextField txtExpYear  = new JTextField(10);
    private JTextField txtCVV  = new JTextField(10);
    private JTextField txtName  = new JTextField(10);
    private JButton btnPay = new JButton("Finish Payment");
    private JLabel lblPrice = new JLabel("Price: $0");

    public TransactionService(int price) {
        this.setTitle("Payment Details");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 450);
        lblPrice.setText("Price: $" + price);
        JPanel panelPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPrice.add(lblPrice);

        JLabel label1 = new JLabel("Credit Card Details");
        label1.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size and style
        label1.setHorizontalAlignment(JLabel.LEFT); // Center align the text
        label1.setBorder(new EmptyBorder(0, 1, 1, 1));
        this.getContentPane().add(label1, BorderLayout.AFTER_LAST_LINE); // Add the label to the top of the content pane
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        this.getContentPane().add(separator1, BorderLayout.CENTER);

        JPanel panelCardName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCardName.add(new JLabel("Name on Credit Card: "));
        panelCardName.add(txtName);
        this.getContentPane().add(panelPrice);
        this.getContentPane().add(panelCardName);

        JPanel panelCardNo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCardNo.add(new JLabel("Credit Card Number: "));
        panelCardNo.add(txtCreditCardNumber);
        this.getContentPane().add(panelCardNo);

        JPanel panelExpiryYear = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelExpiryYear.add(new JLabel("Expiry Year: "));
        panelExpiryYear.add(txtExpYear);
        this.getContentPane().add(panelExpiryYear);

        JPanel panelExpiryMonth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelExpiryMonth.add(new JLabel("Expiry Month: "));
        panelExpiryMonth.add(txtExpMonth);
        this.getContentPane().add(panelExpiryMonth);

        JPanel panelCVV = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCVV.add(new JLabel("CVV: "));
        panelCVV.add(txtCVV);
        this.getContentPane().add(panelCVV);

        JPanel panelButton = new JPanel();
        panelButton.add(btnPay);
        this.getContentPane().add(panelButton);

        this.getBtnPay().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getBtnPay()) {
            finishPayment();
            storeOrderDetails();
        }
    }

    private void finishPayment() {
        String creditCardNumber = this.getTxtCreditCardNumber().getText();
        String creditCardName = this.getTxtName().getText();
        int creditCardExpiryMonth;
        int creditCardExpiryYear;
        int creditCardCVV;
        try {
            creditCardExpiryMonth = Integer.parseInt(this.getTxtExpMonth().getText());
            creditCardExpiryYear = Integer.parseInt(this.getTxtExpYear().getText());
            creditCardCVV = Integer.parseInt(this.getTxtCVV().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please provide valid entries!");
            return;
        }

        
    



        String content = "Your order is successfully completed. Details";
        JOptionPane.showMessageDialog(null, "Receipt: "+content);

        this.setVisible(false);
    }

    private void storeOrderDetails() {
    }

    public JTextField getTxtCreditCardNumber() {
        return txtCreditCardNumber;
    }

    public void setTxtCreditCardNumber(JTextField txtCreditCardNumber) {
        this.txtCreditCardNumber = txtCreditCardNumber;
    }

    public JTextField getTxtExpMonth() {
        return txtExpMonth;
    }

    public void setTxtExpMonth(JTextField txtExpMonth) {
        this.txtExpMonth = txtExpMonth;
    }

    public JTextField getTxtExpYear() {
        return txtExpYear;
    }

    public void setTxtExpYear(JTextField txtExpYear) {
        this.txtExpYear = txtExpYear;
    }

    public JTextField getTxtCVV() {
        return txtCVV;
    }

    public void setTxtCVV(JTextField txtCVV) {
        this.txtCVV = txtCVV;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JButton getBtnPay() {
        return btnPay;
    }

    public void setBtnPay(JButton btnPay) {
        this.btnPay = btnPay;
    }
}
