package Shw1013_MBTI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MBTITestGUI {
    private int[] ieScores = new int[2]; // [0]: I, [1]: E
    private int[] snScores = new int[2]; // [0]: S, [1]: N
    private int[] tfScores = new int[2]; // [0]: T, [1]: F
    private int[] jpScores = new int[2]; // [0]: J, [1]: P

    private static final char[][] MBTI_MAPPING = {
        {'I', 'S', 'T', 'J'}, // 독수리
        {'E', 'N', 'F', 'P'}, // 돌고래
        {'I', 'S', 'F', 'P'}, // 팬더
        {'E', 'N', 'T', 'J'}, // 늑대
        {'I', 'N', 'F', 'J'}, // 고래
        {'E', 'S', 'T', 'P'}, // 말
        {'I', 'N', 'T', 'P'}, // 문어
        {'E', 'S', 'F', 'J'}, // 개
        {'I', 'S', 'T', 'P'}, // 고양이
        {'E', 'N', 'F', 'J'}, // 백조
        {'E', 'S', 'T', 'J'}, // 사자
        {'I', 'N', 'F', 'P'}, // 토끼
        {'I', 'S', 'F', 'J'}, // 코끼리
        {'E', 'N', 'T', 'P'}, // 여우
        {'E', 'S', 'F', 'P'}, // 앵무새
        {'I', 'N', 'T', 'J'}  // 올빼미
    };

    private String userName;
    private String userPhone;
    private String userMBTI;
    private int currentStep = 0;
    private boolean isAnimalStep = true;
    private int animalSelectionCount = 0;
    private int plantSelectionCount = 0;
    private JFrame mainFrame;

    private static final String[] ANIMALS = { "독수리", "돌고래", "팬더", "늑대", "고래", "말", "문어", "개", "고양이", "백조", "사자", "토끼", "코끼리", "여우", "앵무새", "올빼미" };
    private static final String[] PLANTS = { "소나무", "민들레", "데이지", "달리아", "라벤더", "해바라기", "아이비", "백합", "선인장", "모란", "장미", "자스민", "카밀레", "히아신스", "튤립", "아마릴리스" };

    private MBTITestDAO dao = new MBTITestDAO();

    public MBTITestGUI(String name, String phone) {
        this.userName = name;
        this.userPhone = phone;
        mainFrame = new JFrame("MBTI Test");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        
        // 화면 중앙에 프레임 위치시키기
        mainFrame.setLocationRelativeTo(null);
        
        updateUI(mainFrame);
        mainFrame.setVisible(true);
    }

    private void updateUI(JFrame frame) {
        frame.getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        mainPanel.add(panel, BorderLayout.CENTER);
        frame.add(mainPanel);

        String[] currentOptions = isAnimalStep ? ANIMALS : PLANTS;
        int offset = (currentStep % 4) * 4;

        for (int i = 0; i < 4; i++) {
            if (offset + i < currentOptions.length) {
                String option = currentOptions[offset + i];
                ImageIcon originalIcon = loadImageIcon(option);

                if (originalIcon != null) {
                    // 각 옵션을 위한 패널 생성
                    JPanel optionPanel = new JPanel();
                    optionPanel.setLayout(new BorderLayout(5, 5));
                    
                    // 이미지 크기 조절
                    Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    
                    // 버튼 생성 및 설정
                    JButton button = new JButton(scaledIcon);
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                    button.addActionListener(new ImageSelectionListener(option));
                    
                    // 라벨 생성 및 설정
                    JLabel nameLabel = new JLabel(option, JLabel.CENTER);
                    nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                    
                    // 패널에 컴포넌트 추가
                    optionPanel.add(button, BorderLayout.CENTER);
                    optionPanel.add(nameLabel, BorderLayout.SOUTH);
                    
                    panel.add(optionPanel);
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
            updateScoreFromSelection(option);
            if (isAnimalStep) {
                animalSelectionCount++;
            } else {
                plantSelectionCount++;
            }
            currentStep++;
            if (currentStep % 4 == 0) {
                isAnimalStep = !isAnimalStep;
            }
            if (animalSelectionCount + plantSelectionCount == 8) {
                showResult();
            } else {
                updateUI(mainFrame);
            }
        }
    }

    private void updateScoreFromSelection(String option) {
        int index = Arrays.asList(isAnimalStep ? ANIMALS : PLANTS).indexOf(option);
        if (index != -1) {
            char[] mbtiChars = MBTI_MAPPING[index];
            updateScores(mbtiChars);
        }
    }

    private void updateScores(char[] mbtiChars) {
        if (mbtiChars[0] == 'I') ieScores[0]++;
        else ieScores[1]++;
        if (mbtiChars[1] == 'S') snScores[0]++;
        else snScores[1]++;
        if (mbtiChars[2] == 'T') tfScores[0]++;
        else tfScores[1]++;
        if (mbtiChars[3] == 'J') jpScores[0]++;
        else jpScores[1]++;
    }

    private void showResult() {
        StringBuilder result = new StringBuilder();
        
        // MBTI 결과 계산 (기존 코드)
        result.append(ieScores[0] == ieScores[1] ? 
            (Math.random() < 0.5 ? "I" : "E") : 
            (ieScores[0] > ieScores[1] ? "I" : "E"));
        
        result.append(snScores[0] == snScores[1] ? 
            (Math.random() < 0.5 ? "S" : "N") : 
            (snScores[0] > snScores[1] ? "S" : "N"));
        
        result.append(tfScores[0] == tfScores[1] ? 
            (Math.random() < 0.5 ? "T" : "F") : 
            (tfScores[0] > tfScores[1] ? "T" : "F"));
        
        result.append(jpScores[0] == jpScores[1] ? 
            (Math.random() < 0.5 ? "J" : "P") : 
            (jpScores[0] > jpScores[1] ? "J" : "P"));
        
        userMBTI = result.toString();
        
        // DB에 결과 저장
        MBTITestDTO resultDTO = new MBTITestDTO(userName, userPhone, userMBTI);
        int success = dao.addResult(resultDTO);
        
        // MBTI 결과와 궁합 정보를 보여주는 다이얼로그 생성
        showMBTICompatibilityDialog(userMBTI);
        
        // 초기화면으로 돌아가기
        resetTest();
        showUserInputDialog();
        mainFrame.dispose();
    }

    private void showMBTICompatibilityDialog(String mbti) {
        // DB에서 모든 결과 가져오기
        List<MBTITestDTO> allResults = dao.getAllResults();
        
        // 궁합이 잘 맞는 사람들과 도전적인 관계의 사람들을 저장할 리스트
        List<String> goodMatches = new ArrayList<>();
        List<String> challengingMatches = new ArrayList<>();
        
        // 각 저장된 결과에 대해 궁합 확인
        for (MBTITestDTO person : allResults) {
            if (!person.getName().equals(userName)) {  // 자기 자신 제외
                String compatibility = checkCompatibility(mbti, person.getMbti());
                if (compatibility.equals("good")) {
                    goodMatches.add(String.format("%s (%s)", person.getName(), person.getMbti()));
                } else if (compatibility.equals("challenging")) {
                    challengingMatches.add(String.format("%s (%s)", person.getName(), person.getMbti()));
                }
            }
        }
        
        // 결과 메시지 생성
        StringBuilder message = new StringBuilder();
        message.append("당신의 MBTI는: ").append(mbti).append("\n\n");
        
        message.append("▶ 잘 맞는 사람들:\n");
        if (goodMatches.isEmpty()) {
            message.append("아직 데이터가 없습니다.\n");
        } else {
            for (String match : goodMatches) {
                message.append("- ").append(match).append("\n");
            }
        }
        
        message.append("\n▶ 도전적인 관계의 사람들:\n");
        if (challengingMatches.isEmpty()) {
            message.append("아직 데이터가 없습니다.\n");
        } else {
            for (String match : challengingMatches) {
                message.append("- ").append(match).append("\n");
            }
        }
        
        // 메시지 다이얼로그 표시
        JOptionPane.showMessageDialog(null, message.toString(), 
            "MBTI 궁합 결과", JOptionPane.INFORMATION_MESSAGE);
    }

    private String checkCompatibility(String mbti1, String mbti2) {
        // MBTI 궁합 매핑
        Map<String, List<String>> goodMatches = new HashMap<>();
        Map<String, List<String>> challengingMatches = new HashMap<>();
        
        // 궁합 정보 초기화
        goodMatches.put("ESTJ", Arrays.asList("ISTP", "ISFP"));
        challengingMatches.put("ESTJ", Arrays.asList("ENFP", "INFP"));
        
        goodMatches.put("ENTJ", Arrays.asList("INTP", "INTJ"));
        challengingMatches.put("ENTJ", Arrays.asList("ISFP", "ESFP"));
        
        // ... 나머지 MBTI 유형들의 궁합 정보 추가 ...
        
        // 궁합 확인
        if (goodMatches.containsKey(mbti1) && 
            goodMatches.get(mbti1).contains(mbti2)) {
            return "good";
        } else if (challengingMatches.containsKey(mbti1) && 
                   challengingMatches.get(mbti1).contains(mbti2)) {
            return "challenging";
        }
        
        return "neutral";
    }

    // 테스트 초기화를 위한 메소드
    private void resetTest() {
        // 모든 점수 초기화
        ieScores = new int[2];
        snScores = new int[2];
        tfScores = new int[2];
        jpScores = new int[2];
        
        // 기타 변수들 초기화
        currentStep = 0;
        isAnimalStep = true;
        animalSelectionCount = 0;
        plantSelectionCount = 0;
        userName = null;
        userPhone = null;
        userMBTI = null;
    }

    // static 메소드로 변경하여 어디서든 호출 가능하도록 수정
    public static void showUserInputDialog() {
        JFrame inputFrame = new JFrame("시작���려면 정보 기입 필수!");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(400, 300);
        inputFrame.setLayout(new GridBagLayout());
        inputFrame.setLocationRelativeTo(null);

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

        JButton matchButton = new JButton("궁합 매칭");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputFrame.add(matchButton, gbc);

        matchButton.addActionListener(e -> showMatchingResults());

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

        inputFrame.setVisible(true);
    }

    private static void showSavedRecords() {
        JFrame recordsFrame = new JFrame("저장된 목록");
        recordsFrame.setSize(400, 300);
        recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recordsFrame.setLocationRelativeTo(null);

        JTextArea recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordsArea);
        recordsFrame.add(scrollPane, BorderLayout.CENTER);

        // DB에서 데이터 조회
        MBTITestDAO dao = new MBTITestDAO();
        List<MBTITestDTO> results = dao.getAllResults();
        
        for (MBTITestDTO record : results) {
            recordsArea.append(String.format("ID: %d, 이름: %s, 폰번호: %s, MBTI: %s\n",
                record.getId(),
                record.getName(),
                record.getPhone(),
                record.getMbti()));
        }

        recordsFrame.setVisible(true);
    }

    private static void showMatchingResults() {
        JFrame matchFrame = new JFrame("MBTI 궁합 매칭 결과");
        matchFrame.setSize(500, 400);
        matchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        matchFrame.setLocationRelativeTo(null);

        JTextArea matchArea = new JTextArea();
        matchArea.setEditable(false);
        matchArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(matchArea);
        matchFrame.add(scrollPane);

        // DB에서 데이터 가져오기
        MBTITestDAO dao = new MBTITestDAO();
        List<MBTITestDTO> allResults = dao.getAllResults();
        
        // 매칭 결과 계산 및 표시
        List<MatchPair> matches = findBestMatches(allResults);
        
        matchArea.append("🔥 MBTI 궁합 매칭 결과 🔥\n\n");
        for (MatchPair pair : matches) {
            if (pair.person2 != null) {
                matchArea.append(String.format("▶ 매칭 커플\n- %s (%s)\n- %s (%s)\n궁합 점수: %d점\n\n",
                    pair.person1.getName(), pair.person1.getMbti(),
                    pair.person2.getName(), pair.person2.getMbti(),
                    pair.compatibilityScore));
            } else {
                matchArea.append(String.format("▶ 매칭 대기\n- %s (%s)\n\n",
                    pair.person1.getName(), pair.person1.getMbti()));
            }
        }

        matchFrame.setVisible(true);
    }

    // 매칭 쌍을 저장할 클래스
    private static class MatchPair {
        MBTITestDTO person1;
        MBTITestDTO person2;
        int compatibilityScore;

        MatchPair(MBTITestDTO person1, MBTITestDTO person2, int score) {
            this.person1 = person1;
            this.person2 = person2;
            this.compatibilityScore = score;
        }
    }

    private static List<MatchPair> findBestMatches(List<MBTITestDTO> people) {
        List<MatchPair> matches = new ArrayList<>();
        List<MBTITestDTO> unmatched = new ArrayList<>(people);
        
        while (unmatched.size() >= 2) {
            int bestScore = -1;
            MBTITestDTO bestPerson1 = null;
            MBTITestDTO bestPerson2 = null;
            
            // 가장 궁합이 좋은 쌍 찾기
            for (int i = 0; i < unmatched.size(); i++) {
                for (int j = i + 1; j < unmatched.size(); j++) {
                    int score = calculateCompatibilityScore(
                        unmatched.get(i).getMbti(), 
                        unmatched.get(j).getMbti()
                    );
                    if (score > bestScore) {
                        bestScore = score;
                        bestPerson1 = unmatched.get(i);
                        bestPerson2 = unmatched.get(j);
                    }
                }
            }
            
            if (bestPerson1 != null && bestPerson2 != null) {
                matches.add(new MatchPair(bestPerson1, bestPerson2, bestScore));
                unmatched.remove(bestPerson1);
                unmatched.remove(bestPerson2);
            }
        }
        
        // 남은 한 명이 있다면 매칭 대기자로 추가
        if (!unmatched.isEmpty()) {
            matches.add(new MatchPair(unmatched.get(0), null, 0));
        }
        
        return matches;
    }

    private static int calculateCompatibilityScore(String mbti1, String mbti2) {
        // MBTI 궁합 점수 계산 (예시)
        int score = 0;
        
        // 같은 유형끼리는 보통 궁합
        if (mbti1.equals(mbti2)) {
            return 50;
        }
        
        // 잘 맞는 궁합인 경우 높은 점수
        Map<String, List<String>> goodMatches = getGoodMatches();
        if (goodMatches.containsKey(mbti1) && goodMatches.get(mbti1).contains(mbti2) ||
            goodMatches.containsKey(mbti2) && goodMatches.get(mbti2).contains(mbti1)) {
            score += 80;
        }
        
        // 도전적인 관계인 경우 낮은 점수
        Map<String, List<String>> challengingMatches = getChallengingMatches();
        if (challengingMatches.containsKey(mbti1) && challengingMatches.get(mbti1).contains(mbti2) ||
            challengingMatches.containsKey(mbti2) && challengingMatches.get(mbti2).contains(mbti1)) {
            score += 20;
        }
        
        return score;
    }

    private static Map<String, List<String>> getGoodMatches() {
        Map<String, List<String>> matches = new HashMap<>();
        matches.put("ESTJ", Arrays.asList("ISTP", "ISFP"));
        matches.put("ENTJ", Arrays.asList("INTP", "INTJ"));
        // ... 나머지 궁합 정보 추가
        return matches;
    }

    private static Map<String, List<String>> getChallengingMatches() {
        Map<String, List<String>> matches = new HashMap<>();
        matches.put("ESTJ", Arrays.asList("ENFP", "INFP"));
        matches.put("ENTJ", Arrays.asList("ISFP", "ESFP"));
        // ... 나머지 궁합 정보 추가
        return matches;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showSplashScreen();
        });
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        ImageIcon splashIcon = new ImageIcon(MBTITestGUI.class.getResource("/images/splash.jpg"));
        
        if (splashIcon != null) {
            // 이미지 크기 조절 (원본의 2/3 크기)
            Image scaledImage = splashIcon.getImage().getScaledInstance(
                splashIcon.getIconWidth() / 3 * 2,
                splashIcon.getIconHeight() / 3 * 2,
                Image.SCALE_SMOOTH
            );
            
            JLabel splashLabel = new JLabel(new ImageIcon(scaledImage));
            splash.getContentPane().add(splashLabel);
            splash.pack();
            splash.setLocationRelativeTo(null);  // 화면 중앙에 표시
            splash.setVisible(true);

            // 3초 후에 스플래시 화면을 닫고 메인 화면 표시
            Timer timer = new Timer(3000, e -> {
                splash.dispose();
                showUserInputDialog();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            // 스플래시 이미지를 찾을 수 없는 경우 바로 메인 화면 표시
            showUserInputDialog();
        }
    }
}
