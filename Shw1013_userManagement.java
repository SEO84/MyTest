package ex_241023_cha15.homework;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Shw1013_userManagement {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private Shw1013_DAO userDAO;

    public Shw1013_userManagement() {
        userDAO = new Shw1013_DAO();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("회원 관리 프로그램");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 상단 입력 패널
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("이름:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("이메일:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("비밀번호:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);

        JButton addButton = new JButton("추가");
        addButton.addActionListener(new AddUserAction());
        inputPanel.add(addButton);

        JButton loadButton = new JButton("조회");
        loadButton.addActionListener(new LoadUsersAction());
        inputPanel.add(loadButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // 테이블 패널
        tableModel = new DefaultTableModel(new String[]{"이름", "이메일", "비밀번호"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(userTable);
        frame.add(tableScroll, BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private class AddUserAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Shw1013_DTO user = new Shw1013_DTO(name, email, password);
            userDAO.addUser(user);
            JOptionPane.showMessageDialog(null, "사용자가 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        }
    }

    private class LoadUsersAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Shw1013_DTO> users = userDAO.getAllUsers();
            tableModel.setRowCount(0); // 기존 데이터 초기화
            for (Shw1013_DTO user : users) {
                tableModel.addRow(new Object[]{user.getName(), user.getEmail(), user.getPassword()});
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        new Shw1013_userManagement();
    }
}
