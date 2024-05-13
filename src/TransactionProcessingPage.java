import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessingPage extends JFrame {
    private JTextField amountField, nameField, rateField, yearsField;
    private JLabel monthlyPaymentLabel;
    private JTextArea transactionTextArea;

    // Store transaction history
    private List<String> transactionHistory;

    public TransactionProcessingPage() {
        setTitle("Transaction Processing");
        setSize(600, 400); // Increased size to accommodate transaction history
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize transaction history
        transactionHistory = new ArrayList<>();

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.GRAY);

        // Title label
        JLabel titleLabel = new JLabel("Transaction Processing");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center panel with GridBagLayout for input fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Applicant Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        centerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Loan Amount (MYR):"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(15);
        centerPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Interest Rate (% per annum):"), gbc);
        gbc.gridx = 1;
        rateField = new JTextField(15);
        centerPanel.add(rateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(new JLabel("Years:"), gbc);
        gbc.gridx = 1;
        yearsField = new JTextField(15);
        centerPanel.add(yearsField, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // South panel for buttons
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Load Funds button
        JButton loadFundsButton = new JButton("Load Funds");
        loadFundsButton.setForeground(Color.WHITE);
        loadFundsButton.setBackground(Color.BLUE);
        loadFundsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Process fund loading logic
                String amountText = amountField.getText();
                double amount = Double.parseDouble(amountText);
                // Perform fund loading operation (e.g., update account balance)
                transactionHistory.add("Funds loaded: MYR " + amount);
                updateTransactionHistory();
                JOptionPane.showMessageDialog(null, "Funds loaded successfully: MYR " + amount);
                // Clear amount field
                amountField.setText("");
            }
        });
        southPanel.add(loadFundsButton);

        // Calculate Monthly Payment button
        JButton calculateButton = new JButton("Calculate Monthly Payment");
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setBackground(Color.BLUE);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMonthlyPayment();
            }
        });
        southPanel.add(calculateButton);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // East panel for the result label and transaction history
        JPanel eastPanel = new JPanel(new BorderLayout());
        monthlyPaymentLabel = new JLabel("");
        monthlyPaymentLabel.setForeground(Color.WHITE);
        eastPanel.add(monthlyPaymentLabel, BorderLayout.NORTH);

        // Text area for transaction history
        transactionTextArea = new JTextArea();
        transactionTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionTextArea);
        eastPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(eastPanel, BorderLayout.EAST);

        // Add main panel to frame
        add(mainPanel);
    }

    private void calculateMonthlyPayment() {
        String name = nameField.getText();
        double amount = Double.parseDouble(amountField.getText());
        double rate = Double.parseDouble(rateField.getText());
        int years = Integer.parseInt(yearsField.getText());

        double monthlyInterestRate = rate / 12 / 100;
        int numberOfPayments = years * 12;
        double monthlyPayment = (amount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        monthlyPaymentLabel.setText("Monthly Payment (MYR): MYR " + String.format("%.2f", monthlyPayment));
        transactionHistory.add("Monthly Payment Calculated: MYR " + String.format("%.2f", monthlyPayment));
        updateTransactionHistory();
    }

    private void updateTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        for (String transaction : transactionHistory) {
            sb.append(transaction).append("\n");
        }
        transactionTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TransactionProcessingPage().setVisible(true);
            }
        });
    }
}
