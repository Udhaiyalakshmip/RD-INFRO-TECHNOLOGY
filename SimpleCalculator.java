import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    // Components of the calculator
    private JTextField textField1, textField2, resultField;
    private JButton addButton, subButton, mulButton, divButton;

    // Constructor
    public SimpleCalculator() {
        // Frame settings
        setTitle("Simple Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for the input fields and result
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel label1 = new JLabel("First Number:");
        JLabel label2 = new JLabel("Second Number:");
        JLabel resultLabel = new JLabel("Result:");

        textField1 = new JTextField();
        textField2 = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        inputPanel.add(label1);
        inputPanel.add(textField1);
        inputPanel.add(label2);
        inputPanel.add(textField2);
        inputPanel.add(resultLabel);
        inputPanel.add(resultField);

        // Buttons for operations
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");

        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);

        // Add components to the main panel
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(this);
        subButton.addActionListener(this);
        mulButton.addActionListener(this);
        divButton.addActionListener(this);

        // Add the panel to the frame
        add(panel);
    }

    // Action listener method
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Get the numbers from the text fields
            double num1 = Double.parseDouble(textField1.getText());
            double num2 = Double.parseDouble(textField2.getText());
            double result = 0;

            // Perform the operation based on the button clicked
            if (e.getSource() == addButton) {
                result = num1 + num2;
            } else if (e.getSource() == subButton) {
                result = num1 - num2;
            } else if (e.getSource() == mulButton) {
                result = num1 * num2;
            } else if (e.getSource() == divButton) {
                result = num1 / num2;
            }

            // Display the result
            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }
    }

    // Main method to run the calculator
    public static void main(String[] args) {
        // Create the calculator and make it visible
        SimpleCalculator calculator = new SimpleCalculator();
        calculator.setVisible(true);
    }
}
