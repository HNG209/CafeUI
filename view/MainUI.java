package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.lang.model.element.QualifiedNameable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import model.dao.DrinkDAO;
import model.entity.Category;
import model.entity.Drink;

public class MainUI extends JFrame implements ActionListener, MouseListener, DocumentListener, ItemListener {
	private JPanel pW;
	private JPanel pN;
	private JPanel pC;
	private JPanel pE;
	private JPanel pS;
	
	private DefaultTableModel modelDrink;
	private DefaultTableModel modelBill;
	private JTable tableDrink;
	private JTable tableBill;
	
	private Login login;
	
	private JTextField textSearch;
	private JButton searchBtn;
	private JComboBox<String> comboCate;
	
	//for bill table
	private JButton b1;//+
	private JTextField drinkQuantity;
	private JButton b2;//-
	private JButton b3;//x
	private JLabel total;
	private JTextField pay;
	private double payAmount;
	private JLabel charge;
	private JButton payVerify;
	private JButton cancelAll;
	
	//for drink table
	private JButton b4;//add
	private JButton b5;//<
	private JButton b6;//>
	
	//for displaying drink pictures
	private ImageIcon img;
	private JLabel imgLabel;
	private JLabel descriptionLabel;
	private JLabel priceLabel;
	
	//displaying employee's info
	private JLabel eName;
	private JLabel loginTime;
	
	private Category category;
	
	private DrinkDAO drinkList;
	public MainUI() {
		super("MainUI");
		
		payAmount = 0.0;
		drinkList = new DrinkDAO();
		drinkList.add(new Drink("D1", "Tra sua tran chau", 20000.0, "Trà sữa trân châu", Category.TRA_SUA, "D:\\CafeUI\\CafeProject\\src\\assets\\IMG_Java\\traSua.jpg"));
		drinkList.add(new Drink("D2", "chanh day", 35000.0, "chanh dây", Category.NUOC_TRAI_CAY, "D:\\CafeUI\\CafeProject\\src\\assets\\IMG_Java\\chanhDay.jpg"));
		drinkList.add(new Drink("D3", "Cafe sua", 15000.0, "cafe sữa", Category.CAFE, "D:\\CafeUI\\CafeProject\\src\\assets\\IMG_Java\\coffeeSua.jpg"));
		drinkList.add(new Drink("D4", "Mocha", 45000.0, "Mocha", Category.TRA_SUA, "D:\\CafeUI\\CafeProject\\src\\assets\\IMG_Java\\mocha.jpg"));
		drinkList.add(new Drink("D5", "sinh to bo", 21000.0, "sinh tố bơ", Category.SINH_TO, "D:\\CafeUI\\CafeProject\\src\\assets\\IMG_Java\\sinhToBo.jpg"));

		pW = new JPanel();
		pN = new JPanel();
		pC = new JPanel();
		pE = new JPanel();
		
		pW.setPreferredSize(new Dimension(350, 0));
		pW.setBorder(BorderFactory.createTitledBorder("mô tả"));
		//pictures
		Box drinkImgGroup = Box.createVerticalBox();
		
		img = new ImageIcon();
		drinkImgGroup.add(imgLabel = new JLabel("click to show image"));
		priceLabel = new JLabel();
		descriptionLabel = new JLabel();
		priceLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		descriptionLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
		
		drinkImgGroup.createRigidArea(new Dimension(0, 400));
		drinkImgGroup.add(descriptionLabel);
		drinkImgGroup.add(priceLabel);
		
		pW.add(drinkImgGroup);
		add(pW,BorderLayout.WEST);
		
		Box box = Box.createVerticalBox();
		
		Box boxSearch = Box.createHorizontalBox();
		
		Font ft = new Font(Font.SERIF, Font.PLAIN, 16);
		Label lblSearch;
		boxSearch.add(lblSearch = new Label("Search:    "));
		lblSearch.setFont(ft);
		boxSearch.add(textSearch = new JTextField(20));
		boxSearch.add(searchBtn = new JButton("search"));
		searchBtn.addActionListener(this);
		
		Box boxCate = Box.createHorizontalBox();
		Label lblCate;
		
		boxCate.add(lblCate = new Label("Category:"));
		lblCate.setFont(ft);
		boxCate.add(comboCate = new JComboBox<String>());
		
		//load values to category
		for(Category i : category.values()) {
			comboCate.addItem(i.toString());
		}
		
		comboCate.addItemListener(this);
		comboCate.setPreferredSize(new Dimension(320, 0));
		box.add(boxSearch);
		box.add(Box.createVerticalStrut(20));
		box.add(boxCate);
		
		
		
		pN.add(box);
		//pN.setBorder(BorderFactory.createTitledBorder("tìm món"));
		pN.setBackground(Color.CYAN);
		add(pN,BorderLayout.NORTH);
		
		
		
		Box boxTable = Box.createHorizontalBox();
		Box drinkGroup = Box.createVerticalBox();
		
		drinkGroup.setBorder(BorderFactory.createTitledBorder("Danh sách các món"));
		
		boxTable.add(Box.createHorizontalStrut(10));
		
		String [] colDrink = {"ID", "Tên món", "giá"};
		
		//initialize tableDrink
		tableDrink = new JTable(modelDrink = new DefaultTableModel(colDrink, 0));
		loadDrinksToTable();
		drinkGroup.add(new JScrollPane(tableDrink,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		

		JPanel pAdd = new JPanel();
		pAdd.add(b5 = new JButton("<"));
		pAdd.add(b4 = new JButton("Thêm"));
		pAdd.add(b6 = new JButton(">"));
		drinkGroup.add(pAdd);
		
		b4.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		
		b4.addActionListener(this);
		
		boxTable.add(drinkGroup);
		boxTable.add(Box.createHorizontalStrut(10));

		
		String [] colBill = {"Tên món", "giá", "số lượng"};
		//initialize tableBill
		tableBill = new JTable(modelBill = new DefaultTableModel(colBill, 0));
		Box billGroup = Box.createVerticalBox();
		
		billGroup.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
		billGroup.add(new JScrollPane(tableBill,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		tableBill.addMouseListener(this);
		tableDrink.addMouseListener(this);
		JPanel billBtnGroup = new JPanel();//group 3 (+ - x) buttons together in a panel
		billBtnGroup.add(b1 = new JButton("+"));
		billBtnGroup.add(drinkQuantity = new JTextField(3)); 
		drinkQuantity.getDocument().addDocumentListener(this);
		b1.addActionListener(this);
		billBtnGroup.add(b2 = new JButton("-"));
		b2.addActionListener(this);
		billBtnGroup.add(b3 = new JButton("x"));
		b3.addActionListener(this);
		drinkQuantity.setEditable(false);
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		JPanel payPanel = new JPanel();
		billGroup.add(billBtnGroup);
		
		payPanel.setLayout(new GridLayout(4, 2));
		payPanel.setBorder(BorderFactory.createTitledBorder("Thanh toán"));
		payPanel.add(new JLabel("Tổng:"));
		payPanel.add(total = new JLabel("0.0"));
		payPanel.add(new JLabel("Tiền khách đưa:"));
		payPanel.add(pay = new JTextField(10)); 
		pay.getDocument().addDocumentListener(this);
		payPanel.add(new JLabel("Tiền trả lại khách:"));
		payPanel.add(charge = new JLabel("0.0"));
		payPanel.add(payVerify = new JButton("Xác nhận thanh toán"));//in hoá đơn
		payPanel.add(cancelAll = new JButton("Huỷ tất cả"));
		cancelAll.addActionListener(this);
		billGroup.add(payPanel);

		
		boxTable.add(billGroup);
		boxTable.add(Box.createHorizontalStrut(10));
				
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				returnLogin();
			}
		});
		add(boxTable, BorderLayout.CENTER);

		//displaying employee's info
		pS = new JPanel();
		eName = new JLabel("ten nhan vien: Tran Phuc Hung");
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout(2, 3));
		
		tmp.add(new JLabel("Tên nhân viên:"));
		tmp.add(new JPanel());
		tmp.add(eName = new JLabel("Tran Phuc Hung"));
		tmp.add(new JLabel("Thời gian đăng nhập:"));
		tmp.add(new JPanel());
		tmp.add(new JLabel("11:00"));
		
		pS.add(tmp);
		add(pS, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1100,720);
		setVisible(true);
	}
	private void returnLogin() {
		login = new Login();
	}
	public static void main(String[] args) {
		new MainUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(b1)) {//+
			int selectedRow = tableBill.getSelectedRow();
			int quantity = Integer.parseInt(modelBill.getValueAt(selectedRow, 2).toString());
			double price = Double.parseDouble(modelBill.getValueAt(selectedRow, 1).toString());
			
			//recalculate the price
			double updatedPrice = (price / quantity) * (quantity + 1); 
			tableBill.setValueAt(String.valueOf(quantity + 1), selectedRow, 2);
			tableBill.setValueAt(String.valueOf(updatedPrice), selectedRow, 1);
			drinkQuantity.setText(modelBill.getValueAt(tableBill.getSelectedRow(), 2).toString());
			updateTotal();
		}
		if(o.equals(b2)) {//-
			int selectedRow = tableBill.getSelectedRow();
			int quantity = Integer.parseInt(modelBill.getValueAt(selectedRow, 2).toString());
			if(quantity - 1 <= 0) return;
			double price = Double.parseDouble(modelBill.getValueAt(selectedRow, 1).toString());
			
			//recalculate the price
			double updatedPrice = (price / quantity) * (quantity - 1); 
			tableBill.setValueAt(String.valueOf(quantity - 1), selectedRow, 2);
			tableBill.setValueAt(String.valueOf(updatedPrice), selectedRow, 1);
			drinkQuantity.setText(modelBill.getValueAt(tableBill.getSelectedRow(), 2).toString());
			updateTotal();
		}
		if(o.equals(b3)) {//x
			int selectedRow = tableBill.getSelectedRow();
			modelBill.removeRow(selectedRow);
			drinkQuantity.setEditable(false);
			b1.setEnabled(false);
			b2.setEnabled(false);
			b3.setEnabled(false);
			updateTotal();
		}
		if(o.equals(b4)) {//them tu drink qua bill
			int selectedRow = tableDrink.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "chưa chọn món cần thêm!");
				return;
			}
			for(int i = 0; i < modelBill.getRowCount(); i++) {//check to see if any duplicate items
				if(modelBill.getValueAt(i, 0).toString().equals(modelDrink.getValueAt(selectedRow, 1).toString())) {
					JOptionPane.showMessageDialog(this, "thức uống đã được thêm!");
					return;
				}
			}
			modelBill.addRow(new String[] {
				modelDrink.getValueAt(selectedRow, 1).toString(),
				modelDrink.getValueAt(selectedRow, 2).toString(),
				"1"
			});
			updateTotal();
		}
		if(o.equals(cancelAll)) {
			int opt = JOptionPane.showConfirmDialog(this, "Bạn có muốn huỷ tất cả?", "Xác nhận", JOptionPane.YES_NO_OPTION);
			if(opt == JOptionPane.YES_OPTION) {
				modelBill.setRowCount(0);
				updateTotal();
			}
		}
		if(o.equals(searchBtn)) {
			comboCate.setSelectedItem("tất cả");
			if(!textSearch.getText().equals("")) {
				String txt = textSearch.getText();
				for(int i = 0;i < modelDrink.getRowCount();i++) {
					if(modelDrink.getValueAt(i, 1).toString().toLowerCase().contains(txt.toLowerCase())) {
						tableDrink.setRowSelectionInterval(i, i);
					}
				}
			}
		}
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tableBill)) {
			drinkQuantity.setText(modelBill.getValueAt(tableBill.getSelectedRow(), 2).toString());
			drinkQuantity.setEditable(true);
			b1.setEnabled(true);
			b2.setEnabled(true);
			b3.setEnabled(true);
		}
		if(o.equals(tableDrink)) {
			//set image when choosing items
			Drink tmp = drinkList.find(tableDrink.getValueAt(tableDrink.getSelectedRow(), 0).toString());
			imgLabel.setIcon(new ImageIcon(tmp.getImgPath()));
			imgLabel.setText("");
			descriptionLabel.setText(tmp.getDescription());
			priceLabel.setText("Giá:  " + tmp.getPrice());
			b4.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);
		}
		
	}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument().equals(pay.getDocument())) {
			if(pay.getText().matches("\\d+"))
				payAmount = Double.parseDouble(pay.getText());
			updateTotal();
		}
		if(e.getDocument().equals(drinkQuantity.getDocument())) {
			textFieldChange();			
		}
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument().equals(pay.getDocument())) {
			if(pay.getText().matches("\\d+"))
				payAmount = Double.parseDouble(pay.getText());
			updateTotal();
		}
		if(e.getDocument().equals(drinkQuantity.getDocument())) {
			textFieldChange();			
		}
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument().equals(pay.getDocument())) {
			if(pay.getText().matches("\\d+"))
				payAmount = Double.parseDouble(pay.getText());
			updateTotal();
		}
		if(e.getDocument().equals(drinkQuantity.getDocument())) {
			textFieldChange();			
		}
	}
	
	private void loadDrinksToTable() {
		for(Drink i : drinkList.getList()) {
			modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())});
		}
	}
	   
	private void textFieldChange() {
		String value = drinkQuantity.getText();//new quantity
		int selectedRow = tableBill.getSelectedRow();
		if(selectedRow != -1) {
			if(value.matches("\\d+")) {
				int quantity = Integer.parseInt(modelBill.getValueAt(selectedRow, 2).toString());
				double price = Double.parseDouble(modelBill.getValueAt(selectedRow, 1).toString());
				double updatedPrice = (price / quantity) * Integer.parseInt(value); 
				
				tableBill.setValueAt(String.valueOf(updatedPrice), selectedRow, 1);
				modelBill.setValueAt(value, selectedRow, 2);
				updateTotal();
			}
		}
	}
	public void updateTotal() {//update total of all selected items and calculate the charge amount(tien thua)
		double totalPrice = 0.0;
		for(int i = 0;i < modelBill.getRowCount(); i++) {
			totalPrice += Double.parseDouble(modelBill.getValueAt(i, 1).toString());
		}
		
		charge.setText("" + (payAmount - totalPrice));
		total.setText("" + totalPrice);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method 
		if(e.getStateChange() == ItemEvent.SELECTED) {
			switch(comboCate.getSelectedItem().toString()) {
			case "tất cả":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.ALL).forEach(i -> modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			case "trà sữa":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.TRA_SUA).forEach(i -> modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			case "cà phê":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.CAFE).forEach(i -> modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			case "trà":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.TRA).forEach(i -> modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			case "sinh tố":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.SINH_TO).forEach(i -> modelDrink.addRow(new String[] {i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			case "nước trái cây":
				modelDrink.setRowCount(0);
				drinkList.returnListbyCategory(Category.NUOC_TRAI_CAY).forEach(i -> modelDrink.addRow(new String[] { i.getID(), i.getName(), String.valueOf(i.getPrice())}));
				break;
			}
		}
	}
}
