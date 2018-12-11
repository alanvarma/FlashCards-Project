package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Card;
import model.ItemTableModel;



public class CardManagerGUI extends JFrame implements ActionListener{
	
	
	private ItemTableModel tableModel;
	
	private JButton btnUpdate;
	
	private JButton btnAdd;
	
	private JButton btnDelete;
	
	private JButton btnClose;
	
	private int selection = -1;
	
	private JTable cardTable;

	private List<Card> items;
	

	private MainGUI mainGUI;
	
	public CardManagerGUI(MainGUI mainGUI, List<Card> items){
		this.items = items;
		this.mainGUI = mainGUI;
		initialize();
	}
	

	private void initialize(){
		
		
		JScrollPane jScrollPane = new JScrollPane();
		tableModel = new ItemTableModel(items);
		cardTable = new JTable(tableModel);
		cardTable.setRowHeight(100);
		
	
		cardTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		cardTable.getTableHeader().setReorderingAllowed(false);
		cardTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				selection = cardTable.getSelectedRow();
			}
		});

		jScrollPane.setViewportView(cardTable);
		add(jScrollPane, BorderLayout.CENTER);
		
		btnAdd = new JButton("Add Card");
		btnUpdate = new JButton("Update Card");
		btnDelete = new JButton("Delete Card");
		btnClose = new JButton("Close");
		
		JPanel btnPannel = new JPanel();
		btnPannel.add(btnAdd);
		btnPannel.add(btnUpdate);
		btnPannel.add(btnDelete);
		btnPannel.add(btnClose);
		
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnClose.addActionListener(this);
				
		add(btnPannel, BorderLayout.SOUTH);
		
		setTitle("Card Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnClose){
			mainGUI.setVisible(true);
			dispose();
		}else if (e.getSource() == btnAdd) {
			
			
			String word = JOptionPane.showInputDialog("Enter the word: ");
			if (word != null && !word.equals("")){
				String description = JOptionPane.showInputDialog("Enter the description of word: ");
				if (description != null && !description.equals("")){
					tableModel.add(new Card(description, word));
					selection = -1;
				}
			}			
		}else if (e.getSource() == btnUpdate){
		if (selection == -1){
			JOptionPane.showMessageDialog(this, "Please choose a row");
			}else {
				Card card = items.get(selection);
				
				
				String word = JOptionPane.showInputDialog("Enter the word: ", card.getWord());
				if (word != null && !word.equals("")){
					String description = JOptionPane.showInputDialog("Enter the description of word: ",
							card.getDescription());
					if (description != null && !description.equals("")){
						card.setWord(word);
						card.setDescription(description);
			
					tableModel.fireTableDataChanged();
						
						mainGUI.resetGame();
					}
				}		
			}
		}else if (e.getSource() == btnDelete){
			if (selection == -1){
				JOptionPane.showMessageDialog(this, "Please choose a row");
			}else{
				
				tableModel.delete(selection);
				selection = -1;

				tableModel.fireTableDataChanged();
				
				mainGUI.resetGame();
				
			}
		}
		
	}
}
