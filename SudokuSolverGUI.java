import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {
    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    private JTextArea inputArea;
    private JTextField[][] outputFields;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputArea = new JTextArea(10, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        outputFields = new JTextField[BOARD_SIZE][BOARD_SIZE];
        JPanel outputPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField textField = new JTextField(1);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setEditable(false);
                outputFields[row][col] = textField;
                outputPanel.add(textField);
            }
        }

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void solve() {
        String input = inputArea.getText().trim();
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

        try {
            String[] rows = input.split("\\s+");
            int index = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    board[i][j] = Integer.parseInt(rows[index]);
                    index++;
                }
            }

            if (solveSudoku(board)) {
                updateOutput(board);
                JOptionPane.showMessageDialog(this, "Sudoku solved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists.");
                clearOutputFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input format.");
            clearOutputFields();
        }
    }

    private void updateOutput(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                outputFields[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private void clearOutputFields() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                outputFields[i][j].setText("");
            }
        }
    }

    private boolean solveSudoku(int[][] board) {
        int row = -1;
        int col = -1;
        boolean isVacant = true;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    row = i;
                    col = j;
                    isVacant = false;
                    break;
                }
            }
            if (!isVacant) {
                break;
            }
        }

        if (isVacant) {
            return true;
        }

        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                } else {
                    board[row][col] = EMPTY_CELL;
                }
            }
        }
        return false;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
               !usedInCol(board, col, num) &&
               !usedInBox(board, row - row % SUBSECTION_SIZE, col - col % SUBSECTION_SIZE, num);
    }

    private boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < SUBSECTION_SIZE; row++) {
            for (int col = 0; col < SUBSECTION_SIZE; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuSolverGUI();
            }
        });
    }
}
