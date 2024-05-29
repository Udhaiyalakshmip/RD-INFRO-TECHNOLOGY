import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankDetails {
    private String accno;
    private String name;
    private String acc_type;
    private long balance;

    public void openAccount(String accno, String acc_type, String name, long balance) {
        this.accno = accno;
        this.acc_type = acc_type;
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String showAccount() {
        return "Account Details\n" +
                "Name: " + name + "\n" +
                "Account No: " + accno + "\n" +
                "Account Type: " + acc_type + "\n" +
                "Balance: " + balance;
    }

    public void deposit(long amt) {
        balance += amt;
    }

    public String withdrawal(long amt) {
        if (balance >= amt) {
            balance -= amt;
            return "Amount withdrawn successfully. Balance after withdrawal: " + balance;
        } else {
            return "Your balance is less than " + amt + ". Transaction failed!";
        }
    }

    public boolean search(String ac_no) {
        return accno.equals(ac_no);
    }
}

public class BankingApp {
    private static BankDetails[] accounts;
    private static int accountCount = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Indian Bank,Pattabhiram,Chennai-55");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JLabel accNoLabel = new JLabel("Account No:");
        JTextField accNoField = new JTextField();
        JLabel accTypeLabel = new JLabel("Account Type:");
        JTextField accTypeField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel balanceLabel = new JLabel("Balance:");
        JTextField balanceField = new JTextField();
        JButton addAccountButton = new JButton("Add Account");

        panel.add(accNoLabel);
        panel.add(accNoField);
        panel.add(accTypeLabel);
        panel.add(accTypeField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(new JLabel()); // Empty space for alignment
        panel.add(addAccountButton);

        JLabel totalAccountsLabel = new JLabel("Total Accounts: 0"); // Label to display total accounts
        panel.add(totalAccountsLabel); // Add total accounts label to the panel

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 5, 5));

        JButton displayAllButton = new JButton("Display All");
        JButton searchButton = new JButton("Search");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton exitButton = new JButton("Exit");

        controlPanel.add(displayAllButton);
        controlPanel.add(searchButton);
        controlPanel.add(depositButton);
        controlPanel.add(withdrawButton);
        controlPanel.add(exitButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        accounts = new BankDetails[10]; // assuming a maximum of 10 accounts for simplicity

        addAccountButton.addActionListener(e -> {
            try {
                String accNo = accNoField.getText();
                String accType = accTypeField.getText();
                String name = nameField.getText();
                long balance = Long.parseLong(balanceField.getText());
                accounts[accountCount] = new BankDetails();
                accounts[accountCount].openAccount(accNo, accType, name, balance);
                accountCount++;
                displayArea.setText("Account added successfully!");
                accNoField.setText("");
                accTypeField.setText("");
                nameField.setText("");
                balanceField.setText("");
                totalAccountsLabel.setText("Total Accounts: " + accountCount); // Update total accounts label
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid balance. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        displayAllButton.addActionListener(e -> {
            StringBuilder details = new StringBuilder();
            for (int i = 0; i < accountCount; i++) {
                details.append(accounts[i].getName()).append("\n");
            }
            displayArea.setText(details.toString());
        });

        searchButton.addActionListener(e -> {
            String accNo = JOptionPane.showInputDialog(frame, "Enter Account No:");
            if (accNo != null && !accNo.isEmpty()) {
                boolean found = false;
                for (int i = 0; i < accountCount; i++) {
                    if (accounts[i].search(accNo)) {
                        displayArea.setText(accounts[i].showAccount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    displayArea.setText("Search failed! Account doesn't exist.");
                }
            }
        });

        depositButton.addActionListener(e -> {
            String accNo = JOptionPane.showInputDialog(frame, "Enter Account No:");
            if (accNo != null && !accNo.isEmpty()) {
                boolean found = false;
                for (int i = 0; i < accountCount; i++) {
                    if (accounts[i].search(accNo)) {
                        long amt;
                        try {
                            amt = Long.parseLong(JOptionPane.showInputDialog(frame, "Enter amount:"));
                            accounts[i].deposit(amt);
                            displayArea.setText("Transaction successful. Updated Balance: " + accounts[i].showAccount());
                            found = true;
                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (!found) {
                    displayArea.setText("Search failed! Account doesn't exist.");
                }
            }
        });

        withdrawButton.addActionListener(e -> {
            String accNo = JOptionPane.showInputDialog(frame, "Enter Account No:");
            if (accNo != null && !accNo.isEmpty()) {
                boolean found = false;
                for (int i = 0; i < accountCount; i++) {
                    if (accounts[i].search(accNo)) {
                        long amt;
                        try {
                            amt = Long.parseLong(JOptionPane.showInputDialog(frame, "Enter amount:"));
                            displayArea.append(accounts[i].withdrawal(amt) + "\n");
                            displayArea.append("Updated Balance: " + accounts[i].showAccount());
                            found = true;
                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (!found) {
                    displayArea.setText("Search failed! Account doesn't exist.");
                }
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}
