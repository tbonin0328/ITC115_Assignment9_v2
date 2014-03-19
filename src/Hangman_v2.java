import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman_v2 extends JFrame implements ActionListener
{
	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 800;
	private JLabel choiceLabel;
	private JLabel labelTop1;
	private JLabel labelTop2;
	private JLabel labelTop3;
	
	private char[] guessThis = null;
	private char[] hideThis = null;
	private char[] letters;
	private char[] wordUpdate;
	private JButton startButton;
	private JButton btn;
	private JPanel alphaPanel, mainPanel; 
	
	JPanel leftPanel;
	JPanel rightPanel;

	
	private boolean match = false;
	private boolean start = false;
	private boolean update = false;
	
	public Hangman_v2()
	{
		super("Hangman Game");
		setSize(APP_WIDTH, APP_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(2,2));
		
		//add panels
		leftPanel = createPanel(Color.RED);
		rightPanel = createPanel(Color.GREEN);
		
		//add labels to panels
//		labelTop1 = new JLabel("Top Left");
//		labelTop1.setForeground(Color.RED);
//		mainPanel.add(labelTop1);
//		
//		labelTop2 = new JLabel("Top Center");
//		labelTop2.setForeground(Color.WHITE);
//		mainPanel.add(labelTop2);
//		
//		labelTop3 = new JLabel("Top Right");
//		labelTop3.setForeground(Color.YELLOW);
//		mainPanel.add(labelTop3);
		
		
		startButton = new JButton("New Word");
		leftPanel.add(startButton, BorderLayout.NORTH);
		startButton.addActionListener(new ActionListener(){
      		
			@Override
        	public void actionPerformed(ActionEvent e) 
    		{
			    if (e.getSource() == startButton)  
				{
			    	start = !start;
			    	addAlphaPanel(4, 7, 2, 2);
				   	repaint();
				}
    		}
			
		});
		
		choiceLabel = new JLabel();
		//mainPanel.add(choiceLabel, BorderLayout.WEST);
		choiceLabel.setVisible(false);
		//leftPanel.add(choiceLabel, BorderLayout.SOUTH);
		


	    //add(mainPanel,BorderLayout.CENTER);
	    //add(leftPanel, BorderLayout.WEST);
	    //add(rightPanel, BorderLayout.EAST);
	    //add(alphaPanel, BorderLayout.CENTER);
	    //pack();
		add(leftPanel);
		add(rightPanel);
	}
	
	public JPanel createPanel(Color color)
	{
		JPanel myPanel = new JPanel();
		myPanel.setBackground(color);
		add(myPanel);
		myPanel.setVisible(false);
		return myPanel;
	}
	
	public void makeMenu()
	{
		//create a menu bar which can hold lots of menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//create a new menu to add to the menu bar
		JMenu menu = new JMenu("Options");
		menuBar.add(menu);
		
		addMenuItem("New Game", menu);
		addMenuItem("Quit", menu);
	}
	
	public void addMenuItem(String label, JMenu menu)
	{
		JMenuItem menuItem = new JMenuItem(label);
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}
	
//	@Override
//	public void actionPerformed(ActionEvent e) 
//	{
//		String menuText = e.getActionCommand().toLowerCase();
//		
//		switch(menuText)
//		{
//			case "red":
//				redPanel.setVisible(!redPanel.isVisible());
//				break;
//			case "green":
//				greenPanel.setVisible(!greenPanel.isVisible());
//				break;
//			case "blue":
//				bluePanel.setVisible(!bluePanel.isVisible());
//				break;
//		}
//	}
	
	private JPanel addAlphaPanel(int rows, int cols, int hgap, int vgap)
	{
		alphaPanel = new JPanel(new GridLayout(rows, cols, hgap, vgap));
		
		String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", 
    		"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for(int i = 0;i < alpha.length; i++)
        {
    	  btn=new JButton(alpha[i]);
          alphaPanel.add(btn);
          btn.addActionListener(new ActionListener(){
          		@Override
		        public void actionPerformed(ActionEvent e) 
	        		{
		          		if (e.getSource() == btn)  
		          		{
		          			choiceLabel.setVisible(true);
		        			JButton choiceBtn = (JButton)e.getSource();
		        			choiceLabel.setText(choiceBtn.getText());
		        			
		        			String cue = choiceBtn.getText();
		        			char c = cue.charAt(0); 
		        			letters = replace(letters, c);
		        			
		        			//labelTop1.setText(wordUpdate.toString());
		          		}  	  
	        		}
          		});
        }
		return alphaPanel;
	}
	
	public void paint(Graphics g)
	{
		if(!start)
		{
			
		}
		else
		{
			super.paint(g);
			g.drawString(String.valueOf(getWord()), 30, 90);
			
			Font font = new Font("Serif", Font.BOLD|Font.ITALIC, 24);
			g.setFont(font);
			g.setColor(Color.ORANGE);
			g.drawString("Welcome to the Hangman Game!", 25, 60);
			
			g.drawOval(100, 100, 200, 200);	//face
			

			//draw eyes
			g.setColor(Color.BLUE);
			
			if(!update)
			{
				
			}
			else
			{
				
			}
			
			if(!match)
			{
				g.fillOval(155, 160, 20, 20);	//left eye
			}
			else
			{
				for(int i=0; i< letters.length; i++)
				{
					 g.drawString(String.valueOf(wordUpdate), 30, 90);
				}
			}
			
			g.fillOval(230, 160, 20, 20);	//right eye
			
			//draw mouth
			g.setColor(Color.RED);
			g.drawLine(150, 250, 250, 250);
		}
	}
	
	public char[] getWord()
	{
		Random rand = new Random(System.currentTimeMillis());
		String randWord = "";
		List<String> words = new ArrayList<String>();
		Scanner fileIn = getFileReader("input/dictionary.txt");
		while(fileIn.hasNext())
		{
			String line = fileIn.nextLine();
			String[] wordsLine = line.split(", ");
		    for(String word : wordsLine) 
		    {
		    	words.add(word);
		    }
	    }
		randWord = words.get(rand.nextInt(words.size()));
		letters = randWord.toCharArray();
		hideThis = getHidden(letters);
		
		System.out.println(randWord);
		System.out.println(hideThis);
		System.out.println(letters);
		startButton.setVisible(false);
		
		return letters;
	}
	
	public char[] replace(char[] word, char replace)
	{
		//the letter comes in
		//check to see if the letter matches the first letter in the word
		//if so, replace the char at that index point in the hideThis
		//if not, leave it alone (no else statement needed)
		//return the new hideThis array, not the word array.
		
		for (int i = 0; i < word.length; i++)
		{
			if (replace == word[i])
			{
				hideThis[i] = replace;
			}
		}
		
		return hideThis;
	}
	
	public Scanner getFileReader(String fileName)
	{
		File file = new File(fileName);

		try
		{
			return new Scanner(file);
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
			return null;
		}
	}
	
	public char[] getHidden(char[]word)
	{
		char[] hideVersion = new char[word.length];
		for (int i = 0; i < word.length; i++)
		{
			if (Character.isLetter(word[i]))
			{
				hideVersion[i] = '_';
			}
		}
		return hideVersion;
	}
	
	public static void main (String[] args)
	{
		Hangman_v2 myFrame = new Hangman_v2();
		myFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

