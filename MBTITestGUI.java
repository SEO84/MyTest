package Shw1013_MBTI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MBTITestGUI {
    private int[] scores = new int[4]; // E-I, S-N, T-F, J-P
    private int currentStep = 0; // 현재 질문 단계
    private String userName;
    private String userPhone;
    private String userMBTI;
    private JFrame mainFrame;

    private static final String[] ANIMALS = { "독수리", "돌고래", "팬더", "늑대", "고래", "말", "문어", "개", 
            "고양이", "백조", "사자", "토끼", "코끼리", "여우", "앵무새", "올빼미" };

    private static final String[] PLANTS = { "소나무", "민들레", "데이지", "달리아", "라벤더", "해바라기", "아이비", 
            "백합", "선인장", "모란", "장미", "자스민", "카밀레", "히아신스", "튤립", "아마릴리스" };

    private String[] currentOptions;
    private boolean isAnimalStep = true;
    private int animalSelectionCount = 0;
    private int plantSelectionCount = 0;

    private MBTITestDAO dao = new MBTITestDAO(); // DAO 인스턴스

    public static void main(String[] args) {
        showSplashScreen();
        SwingUtilities.invokeLater(MBTITestGUI::showUserInputDialog);
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        ImageIcon splashIcon = new ImageIcon(MBTITestGUI.class.getResource("/images/splash.jpg"));
        if (splashIcon != null) {
            splash.getContentPane().add(new JLabel(splashIcon));
            splash.setSize(splashIcon.getIconWidth(), splashIcon.getIconHeight());
            splash.setLocationRelativeTo(null);
            splash.setVisible(true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            splash.dispose();
        }
    }

    private static void showUserInputDialog() {
        JFrame inputFrame = new JFrame("시작하려면 정보 기입 필수!");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(400, 300);
        inputFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("이름:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputFrame.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputFrame.add(nameField, gbc);

        JLabel phoneLabel = new JLabel("폰번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputFrame.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputFrame.add(phoneField, gbc);

        JButton startButton = new JButton("게임 시작");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputFrame.add(startButton, gbc);

        JButton viewButton = new JButton("조회");
        gbc.gridy = 3;
        inputFrame.add(viewButton, gbc);

        startButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            if (!name.isEmpty() && !phone.isEmpty()) {
                inputFrame.dispose();
                new MBTITestGUI(name, phone);
            } else {
                JOptionPane.showMessageDialog(inputFrame, "모든 필드를 입력하세요.");
            }
        });

        viewButton.addActionListener(e -> showSavedRecords());

        inputFrame.setLocationRelativeTo(null);
        inputFrame.setVisible(true);
    }

    public MBTITestGUI(String name, String phone) {
        this.userName = name;
        this.userPhone = phone;
        mainFrame = new JFrame("MBTI Test");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        updateUI(mainFrame);
        mainFrame.setVisible(true);
    }

    private void updateUI(JFrame frame) {
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        frame.add(panel);

        currentOptions = isAnimalStep ? ANIMALS : PLANTS;
        int offset = (currentStep % 4) * 4;

        for (int i = 0; i < 4; i++) {
            if (offset + i < currentOptions.length) {
                String option = currentOptions[offset + i];
                ImageIcon originalIcon = loadImageIcon(option);

                if (originalIcon != null) {
                    Image scaledImage = originalIcon.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    JPanel imagePanel = new JPanel(new BorderLayout());
                    JButton button = new JButton(scaledIcon);
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                    button.addActionListener(new ImageSelectionListener(option));

                    JLabel label = new JLabel(option, JLabel.CENTER);
                    label.setFont(new Font("SansSerif", Font.BOLD, 14));
                    label.setForeground(Color.BLACK);

                    imagePanel.add(button, BorderLayout.CENTER);
                    imagePanel.add(label, BorderLayout.SOUTH);
                    panel.add(imagePanel);
                }
            }
        }

        frame.revalidate();
        frame.repaint();
    }

    private ImageIcon loadImageIcon(String option) {
        String imagePath = "/images/" + option + ".jpg";
        java.net.URL imageUrl = getClass().getResource(imagePath);
        return imageUrl != null ? new ImageIcon(imageUrl) : null;
    }

    private class ImageSelectionListener implements ActionListener {
        private final String option;

        public ImageSelectionListener(String option) {
            this.option = option;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAnimalStep) {
                animalSelectionCount++;
            } else {
                plantSelectionCount++;
            }
            currentStep++;
            if (currentStep % 4 == 0) isAnimalStep = !isAnimalStep;
            if (animalSelectionCount + plantSelectionCount == 8) showResult();
            else updateUI(mainFrame);
        }
    }

    private void showResult() {
        StringBuilder result = new StringBuilder();
        result.append(scores[0] < 2 ? "E" : "I");
        result.append(scores[1] < 2 ? "S" : "N");
        result.append(scores[2] < 2 ? "T" : "F");
        result.append(scores[3] < 2 ? "J" : "P");
        userMBTI = result.toString();
        JOptionPane.showMessageDialog(null, "당신의 MBTI는: " + userMBTI);
        dao.addResult(new MBTITestDTO(userName, userPhone, userMBTI));
    }

    private static void showSavedRecords() {
        JFrame recordsFrame = new JFrame("저장된 목록");
        recordsFrame.setSize(400, 300);
        recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        recordsFrame.add(new JScrollPane(recordsArea), BorderLayout.CENTER);

        MBTITestDAO dao = new MBTITestDAO();
        for (MBTITestDTO record : dao.getAllResults()) {
            recordsArea.append("이름: " + record.getName() + ", 폰번호: " + record.getPhone() + ", MBTI: " + record.getMbti() + "\n");
        }

        recordsFrame.setVisible(true);
    }
}
