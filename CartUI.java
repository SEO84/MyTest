package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class CartUI extends JFrame {

    public CartUI() {
        setTitle("장바구니");
        setSize(600, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"상품명", "가격", "개수", "재고", "삭제", "수량 수정"};
        Object[][] data = {
            {"무선 마우스", 25900, 1, 10, "삭제", "수량 수정"},
            {"블루투스 헤드폰", 89900, 1, 5, "삭제", "수량 수정"},
            {"스마트폰 충전기", 15900, 1, 8, "삭제", "수량 수정"},
            {"4K 모니터", 299900, 1, 3, "삭제", "수량 수정"},
            {"게이밍 키보드", 59900, 1, 7, "삭제", "수량 수정"},
            {"남성용 티셔츠", 12900, 1, 15, "삭제", "수량 수정"},
            {"여성용 청바지", 49900, 1, 10, "삭제", "수량 수정"},
            {"가죽 재킷", 119900, 1, 4, "삭제", "수량 수정"},
            {"스니커즈", 69900, 1, 12, "삭제", "수량 수정"},
            {"손목시계", 99900, 1, 6, "삭제", "수량 수정"},
            {"진공청소기", 129900, 1, 5, "삭제", "수량 수정"},
            {"전자레인지", 89900, 1, 8, "삭제", "수량 수정"},
            {"공기청정기", 199900, 1, 3, "삭제", "수량 수정"},
            {"커피 메이커", 49900, 1, 9, "삭제", "수량 수정"},
            {"냉장고", 599900, 1, 2, "삭제", "수량 수정"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Set custom renderers for alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // 개수
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // 재고

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        rightRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number) {
                    setText(NumberFormat.getNumberInstance(Locale.KOREA).format(value) + " 원");
                }
                return c;
            }
        };
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer); // 가격

        // Set row height
        table.setRowHeight((int) (table.getRowHeight() * 1.5));

        // 버튼 렌더러 및 에디터 추가
        table.getColumn("삭제").setCellRenderer(new ButtonRenderer());
        table.getColumn("삭제").setCellEditor(new ButtonEditor(new JCheckBox(), model, "삭제"));
        
        table.getColumn("수량 수정").setCellRenderer(new ButtonRenderer());
        table.getColumn("수량 수정").setCellEditor(new ButtonEditor(new JCheckBox(), model, "수량 수정"));

        JScrollPane scrollPane = new JScrollPane(table);

        JButton checkoutButton = new JButton("결제하기");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(CartUI.this, "결제가 완료되었습니다.");
            }
        });

        JLabel totalLabel = new JLabel("총 구매 금액: 0원");
        updateTotalPrice(model, totalLabel);

        model.addTableModelListener(e -> updateTotalPrice(model, totalLabel));

        JPanel panel = new JPanel();
        panel.add(checkoutButton);
        panel.add(totalLabel);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void updateTotalPrice(DefaultTableModel model, JLabel totalLabel) {
        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            int price = (int) model.getValueAt(i, 1);
            int quantity = (int) model.getValueAt(i, 2);
            total += price * quantity;
        }
        totalLabel.setText("총 구매 금액: " + NumberFormat.getNumberInstance(Locale.KOREA).format(total) + " 원");
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private DefaultTableModel model;
        private int row;
        private String actionType;

        public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, String actionType) {
            super(checkBox);
            this.model = model;
            this.actionType = actionType;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ("삭제".equals(actionType)) {
                        model.removeRow(ButtonEditor.this.row);
                    } else if ("수량 수정".equals(actionType)) {
                        String newQuantityStr = JOptionPane.showInputDialog(CartUI.this, "새로운 수량을 입력하세요:", model.getValueAt(row, 2));
                        try {
                            int newQuantity = Integer.parseInt(newQuantityStr);
                            if (newQuantity > 0 && newQuantity <= (int) model.getValueAt(row, 3)) {
                                model.setValueAt(newQuantity, row, 2);
                            } else {
                                JOptionPane.showMessageDialog(CartUI.this, "유효한 수량을 입력하세요.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(CartUI.this, "숫자를 입력하세요.");
                        }
                    }
                }
            });
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
} 