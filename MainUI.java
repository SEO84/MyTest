package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {

    private JPanel centerPanel;
    private String[][] products = {
        {"무선 마우스", "블루투스 헤드폰", "스마트폰 충전기", "4K 모니터", "게이밍 키보드"},
        {"남성용 티셔츠", "여성용 청바지", "가죽 재킷", "스니커즈", "손목시계"},
        {"진공청소기", "전자레인지", "공기청정기", "커피 메이커", "냉장고"}
    };

    public MainUI() {
        setTitle("메인 화면");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 상단 패널: 카테고리 선택
        JPanel topPanel = new JPanel();
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"전자기기", "패션", "가전제품"});
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                updateProductList(selectedIndex);
            }
        });
        topPanel.add(categoryComboBox);

        // 중앙 패널: 카테고리별 상품 목록
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 3)); // 3열로 구성
        updateProductList(0); // 디폴트로 전자기기 카테고리

        // 하단 패널: 장바구니 버튼만 남김
        JPanel bottomPanel = new JPanel();
        JButton cartButton = new JButton("장바구니");
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartUI().setVisible(true);
            }
        });
        bottomPanel.add(cartButton);

        // 패널 추가
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateProductList(int categoryIndex) {
        centerPanel.removeAll();
        for (String product : products[categoryIndex]) {
            JButton productButton = new JButton(product);
            productButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, product + " 상세 내용");
                }
            });
            centerPanel.add(productButton);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }
}
