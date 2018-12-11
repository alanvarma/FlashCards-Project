package view;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Card;
import model.FIFODeck;
import model.LIFODeck;




public class MainGUI extends JFrame implements ActionListener{
	
	private JTextArea txtCard;
	
	
	private JButton btnStart;
	
	
	private JButton btnNext;
	
	
	private JButton btnExit;
	
	
	private JMenuItem mnuFileOpen;
	
	
	private JMenuItem mnuFileSave;
	
	
	private JMenuItem mnuFileExit;

	
	private JMenuItem mnuPlayFIFO;

	
	private JMenuItem mnuPlayLIFO;

	
	private JMenuItem mnuManageCard;
	

	private List<Card> items = new ArrayList<>();
	

	private FIFODeck fifoDeck;

	private LIFODeck lifoDeck;
	

	private boolean isDescription = false;
	

	private Card currentCard;
	

	public MainGUI(){
		initialize();
	}
	

	private void initialize(){
		
		txtCard = new JTextArea();
		txtCard.setEditable(false);
		txtCard.setLineWrap(true);
		txtCard.setWrapStyleWord(true);
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(txtCard);
		add(jScrollPane, BorderLayout.CENTER);
		

		btnStart = new JButton("Start");
		btnNext = new JButton("Next");
		btnExit = new JButton("Exit Application");
		
		JPanel btnPannel = new JPanel();
		btnPannel.add(btnStart);
		btnPannel.add(btnNext);
		btnPannel.add(btnExit);
		
		btnNext.addActionListener(this);
		btnStart.addActionListener(this);
		btnExit.addActionListener(this);
		
		add(btnPannel, BorderLayout.SOUTH);
		
		btnNext.setEnabled(false);
		
		
		JMenuBar mnuBar = new JMenuBar();
		
		JMenu mnuFile = new JMenu("File");
		mnuFileOpen = new JMenuItem("Open Data File");
		mnuFileSave = new JMenuItem("Save Data File");
		mnuFileExit = new JMenuItem("Exit");
		
		mnuFile.add(mnuFileOpen);
		mnuFile.add(mnuFileSave);
		
		mnuFile.addSeparator();
		
		mnuFile.add(mnuFileExit);
		
		mnuBar.add(mnuFile);
		
		

		JMenu mnuPlay = new JMenu("Play");
		mnuPlayFIFO = new JRadioButtonMenuItem("FIFO mode");
		mnuPlayLIFO = new JRadioButtonMenuItem("LIFO mode");
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(mnuPlayFIFO);
		bg.add(mnuPlayLIFO);
		
		mnuPlay.add(mnuPlayFIFO);
		mnuPlay.add(mnuPlayLIFO);

		
		mnuBar.add(mnuPlay);
		
		JMenu mnuTools = new JMenu("Tools");
		mnuManageCard = new JMenuItem("Manage Cards");
		
		mnuTools.add(mnuManageCard);
		
		mnuBar.add(mnuTools);
		
		setJMenuBar(mnuBar);	
		
		mnuFileOpen.addActionListener(this);
		mnuFileSave.addActionListener(this);
		mnuFileExit.addActionListener(this);
		
		mnuPlayFIFO.addActionListener(this);
		mnuPlayLIFO.addActionListener(this);
		
		mnuManageCard.addActionListener(this);
		
		
		setTitle("Flash Card");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null); 
		
		mnuPlayFIFO.setSelected(true);
		btnNext.setEnabled(false);

		txtCard.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e)  
		    {  
		       flipCard();
		       
		    }  
		}); 
		
		txtCard.setFont(new Font(txtCard.getName(), Font.PLAIN, 30));
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnExit || e.getSource() == mnuFileExit){ //exit
			System.exit(0);
		}else if (e.getSource() == mnuManageCard){
			
			setVisible(false);
			CardManagerGUI manager = new CardManagerGUI(this, items);
			manager.setVisible(true);
			
		}else if (e.getSource() == mnuFileSave){
			
			if (items.size() == 0){
				JOptionPane.showMessageDialog(this, "There is no card. Please open data file or choose Tools -> Manage Cards");
				return;
			}
			
			JFileChooser fileChooser = new JFileChooser();

			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
				try {
					
					FileOutputStream f = new FileOutputStream(fileChooser.getSelectedFile());
					ObjectOutputStream o = new ObjectOutputStream(f);
		
					o.writeObject(items);
		
					o.close();
					f.close();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Could not save file");
				}
			}
			
		}else if (e.getSource() == mnuFileOpen){
			
			JFileChooser fileChooser = new JFileChooser();

			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				try {

					FileInputStream fi = new FileInputStream(fileChooser.getSelectedFile());
					
					ObjectInputStream oi = new ObjectInputStream(fi);

					items = (ArrayList<Card>)oi.readObject();

					oi.close();
					fi.close();
					
					resetGame();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Could not open file");
				}
			}
			
		}else if (e.getSource() == btnStart){
			
			if (items.size() == 0){
				JOptionPane.showMessageDialog(this, "There is no card. Please open data file or choose Tools -> Manage Cards");
				return;
			}
			if (mnuPlayFIFO.isSelected()){
				fifoDeck = new FIFODeck(items);
			}else if (mnuPlayLIFO.isSelected()){
				lifoDeck = new LIFODeck(items);
			}else{
			}	
			
			btnNext.setEnabled(true);
			btnStart.setEnabled(false);
			
			showCard();
			
		}else if (e.getSource() == btnNext){
			
			showCard();
			
		}else if (e.getSource() == mnuPlayFIFO){
			resetGame();
		}else if (e.getSource() == mnuPlayLIFO){
			resetGame();
		}
	}
	
	
	private void flipCard(){
		
		if (currentCard != null){
			if (isDescription){
				txtCard.setText(currentCard.getWord());
				isDescription = false;
			}else{
				txtCard.setText(currentCard.getDescription());
				isDescription = true;
			}
		}
	}
	
	private void showCard(){
		
		if (mnuPlayFIFO.isSelected()){
			if (fifoDeck.isEmpty()){
				JOptionPane.showMessageDialog(this, "You played all cards");
			}else{
				currentCard = fifoDeck.dequeue();
				txtCard.setText(currentCard.getDescription());
				
				isDescription = true;
			}
		}else if (mnuPlayLIFO.isSelected()){
			
			if (lifoDeck.isEmpty()){
				JOptionPane.showMessageDialog(this, "You played all cards");
			}else{
				currentCard = lifoDeck.pop();
				txtCard.setText(currentCard.getDescription());
				
				isDescription = true;
			}
			
		}else{
			

			}
		}	
	
	
	public void resetGame(){
		
		txtCard.setText("");
		btnStart.setEnabled(true);
		btnNext.setEnabled(false);		
		currentCard = null;
	}
}
