/**
 * 
 * Client class provides user interface to enter 
 * the text and selecting a word for Synonyms 
 * Uses swings Jframe for the User interface 
 * @author Sumanth lakshminarayana(1000830230)
 *
 */
package com.client.server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Client implements WindowListener 
{
	private int port = 1235;
	private String host="localhost";
	JTextArea userInputArea ;
	private JList jList;
	JFrame userInputWindow;
	private PrintStream streamOut;
	JLabel userInputAreaLabel,synonymAreaLabel;
	JScrollPane scrollUserInputArea,scrollSynonymArea;
	int start,end,length;
	String totalInput,selectedInput;

	/*
	 * Method to create client GUI and read user input.
	 */
	synchronized void createClient()
	{
		try
		{
			userInputWindow = new JFrame("User GUI");
			JButton sendBox = new JButton("Get Synonyms");
			userInputWindow.setSize(676,730);
			userInputWindow.getContentPane().setLayout(null);
			userInputWindow.getContentPane().setBackground(new Color(205,202,191));
			userInputWindow.getContentPane().setForeground(Color.white);
			userInputWindow.setResizable(false);
			userInputWindow.setLocationRelativeTo(null);

			ImageIcon applicationLogo = new ImageIcon("appLogo.jpg");
			JLabel appLogoLabel = new JLabel(applicationLogo);  
			userInputWindow.getContentPane().add(appLogoLabel);

			userInputAreaLabel = new JLabel("Enter Word To find Synonyms");
			userInputWindow.getContentPane().add(userInputAreaLabel);
			userInputArea = new JTextArea(30,30);
			scrollUserInputArea = new JScrollPane(userInputArea);
			scrollUserInputArea.setBounds(50, 350, 600, 100);
			userInputWindow.add(scrollUserInputArea);
			userInputAreaLabel.setForeground(Color.blue);

			jList=new JList();

			synonymAreaLabel = new JLabel("Following are synonyms for selected word");
			userInputWindow.getContentPane().add(synonymAreaLabel);
			scrollSynonymArea = new JScrollPane(jList);
			scrollSynonymArea.setBounds(50, 550, 600, 100);
			userInputWindow.add(scrollSynonymArea);
			synonymAreaLabel.setForeground(Color.blue);

			userInputWindow.getContentPane().add(sendBox);

			appLogoLabel.setBounds(10,0,650,290);
			sendBox.setBounds(250,470,165,34);
			userInputAreaLabel.setBounds(50,310,500,50);
			synonymAreaLabel.setBounds(50,510,500,50);

			sendBox.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					selectedInput=userInputArea.getSelectedText();
					streamOut.println(selectedInput);
					start=userInputArea.getSelectionStart();
					end=userInputArea.getSelectionEnd();
					length=userInputArea.getText().length();
					totalInput=userInputArea.getText();
				}
			});
			userInputWindow.setVisible(true);
			userInputWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			userInputWindow.addWindowListener(this);
			executeClient();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(userInputWindow,"Unable to Connect server, please try again later","ERROR",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/*
	 * Method creates client and binds to server
	 */
	private synchronized void executeClient() throws IOException,ConnectException
	{
		try
		{
			Socket clientSocket = new Socket(host, port);
			BufferedReader streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			streamOut = new PrintStream(clientSocket.getOutputStream(), true);

			while(true)
			{
				String line = streamIn.readLine();
				/*
				 * If some exception occurs in DB side or DB is not running then
				 * throw SQL exception, which is caught and a useful error message is shown to user.
				 */
				if(line == null)
					throw new SQLException("Problem with DB!");
				/*
				 * If the synonym is not there in DB for the selected word,
				 * then display appropriate message to user and reset synonym area
				 */
				if(line.equalsIgnoreCase(""))
				{
					Object[] o=new Object[0]; 

					try
					{
						JOptionPane.showMessageDialog(userInputWindow,"No synonym found for the selected word!","Result",JOptionPane.ERROR_MESSAGE);
						jList.setListData(o);
					}catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				/*
				 * If the no word is selected,
				 * then display appropriate message to user and reset synonym area
				 */
				else if((line.equalsIgnoreCase("0")))
				{
					Object[] o=new Object[0]; 

					try
					{
						JOptionPane.showMessageDialog(userInputWindow,"Please select a word to find the synonyms","Result",JOptionPane.ERROR_MESSAGE);
						jList.setListData(o);
					}catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
				/*
				 * If synonyms are found then modify DB data to user readable format and attach it to jList
				 * so that user can select synonym for selected word.
				 */
				else
				{
					String[] syn=line.split(",");
					jList.setListData(syn);

					jList.addListSelectionListener(new ListSelectionListener()
					{ 
						public void valueChanged(ListSelectionEvent e)
						{
							JList list = (JList) e.getSource(); 

							String selectedValue = (String) list.getSelectedValue(); 

							try
							{
								String before=totalInput.substring(0,start);
								String after="";
								after=totalInput.substring(end,length);
								String finalResult;
								if(selectedValue==null  || selectedValue.equalsIgnoreCase("null"))
									finalResult=totalInput;
								else
									finalResult=before+selectedValue+after;
								userInputArea.setText(finalResult);
							}
							catch (Exception e1)
							{
								e1.printStackTrace();
							}

						} 
					});
				}
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(userInputWindow,"Unable to Connect to DB, please try again later","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(userInputWindow,"Unable to Connect server, please try again later","ERROR",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 * closes the client window on click of "X"
	 */
	public void windowClosing(WindowEvent e)
	{
		userInputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	public static void main(String[] args){
		new Client().createClient();
	}
}