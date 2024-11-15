package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import dao.DAOKjh_0313;
import dto.ProductDTOKjh_0313;
import dto.UserDTO;

public class UiKjh_0313 extends JFrame {
	private DAOKjh_0313 dao;
	private JPanel topPanel, listPanel, infoPanel;
	private JLabel productNameLabel, productPriceLabel, productDescriptionLabel;
	private UserDTO loggedInUser;

	public UiKjh_0313(UserDTO user) {
		this.loggedInUser = user; // 로그인된 사용자 정보 저장
		dao = new DAOKjh_0313();

		setTitle("상품 목록");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTopPanel();
		createListPanel();
		createInfoPanel();

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.WEST);
		add(infoPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	private void createTopPanel() {
		topPanel = new JPanel(new FlowLayout());
		JLabel titleLabel = new JLabel("상품 목록");
		topPanel.add(titleLabel);

		// 버튼 생성 및 이벤트 연결
		topPanel.add(createTopPanelButton("장바구니", e -> new CartUIShw1013(getUserId()).setVisible(true)));
		topPanel.add(createTopPanelButton("회원정보", e -> new MyProfileFrame(loggedInUser).setVisible(true)));
		topPanel.add(createTopPanelButton("주문내역", e -> showOrderHistory()));
	}

	private JButton createTopPanelButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		return button;
	}

	private void showOrderHistory() {
		// 주문내역 표시 로직 추가
		JOptionPane.showMessageDialog(this, "주문내역 기능은 아직 구현되지 않았습니다.");
	}

	private void createListPanel() {
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		JPanel searchPanel = new JPanel(new FlowLayout());
		JTextField searchField = new JTextField(10);
		JButton searchButton = new JButton("검색");
		searchButton.addActionListener(e -> updateProductList(dao.searchProducts(searchField.getText())));

		JButton viewAllButton = new JButton("모두 보기");
		viewAllButton.addActionListener(e -> updateCategoryList());

		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		searchPanel.add(viewAllButton);

		listPanel.add(searchPanel);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
		listPanel.add(categoryPanel);

		updateCategoryList();
	}

	private void updateProductList(List<String> products) {
		JPanel productPanel = new JPanel();
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
		listPanel.removeAll();

		for (String product : products) {
			JButton productButton = new JButton(product);
			productButton.addActionListener(e -> showProductInfo(product));
			productPanel.add(productButton);
		}

		listPanel.add(productPanel);
		listPanel.revalidate();
		listPanel.repaint();
	}

	private void updateCategoryList() {
		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));

		List<String> categories = dao.getCategories();
		for (String category : categories) {
			JButton categoryButton = new JButton(category);
			categoryButton.addActionListener(e -> updateProductList(dao.getProductsByCategory(category)));
			categoryPanel.add(categoryButton);
		}

		listPanel.removeAll();
		listPanel.add(categoryPanel);
		listPanel.revalidate();
		listPanel.repaint();
	}

	private void createInfoPanel() {
		infoPanel = new JPanel(new GridLayout(4, 1));
		productNameLabel = new JLabel();
		productPriceLabel = new JLabel();
		productDescriptionLabel = new JLabel();

		JButton addToCartButton = new JButton("장바구니에 추가");
		addToCartButton.addActionListener(e -> {
			ProductDTOKjh_0313 product = dao.getProductInfo(productNameLabel.getText());
			dao.addToCart(getUserId(), product.getProductId());
			JOptionPane.showMessageDialog(this, "장바구니에 상품이 추가되었습니다.");
		});

		infoPanel.add(productNameLabel);
		infoPanel.add(productPriceLabel);
		infoPanel.add(productDescriptionLabel);
		infoPanel.add(addToCartButton);
	}

	private void showProductInfo(String productName) {
		ProductDTOKjh_0313 product = dao.getProductInfo(productName);
		if (product != null) {
			productNameLabel.setText("상품 이름: " + product.getName());
			productPriceLabel.setText("가격: " + product.getPrice());
			productDescriptionLabel.setText("설명: " + product.getDescription());
		}
	}

	private int getUserId() {
		return loggedInUser.getUserId();
	}

	public static void main(String[] args) {
		UserDTO loggedInUser = new UserDTO(1, "홍길동", "hong@domain.com", "1234", null, false);
		new UiKjh_0313(loggedInUser);
	}
}
