/**
 * Server class GUI of server
 * Receives requests from client and 
 * provides service
 * Uses socket programming.
 * @author Sumanth lakshminarayana(1000830230)
 *
 */
package com.client.server;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server 
{
	private int port = 1235;
	private ServerSocket server = null;
	private Socket clientSocket;
	private JTextArea serverMonitorLog;
	private JFrame serverMonitorwindow;

	/*
	 * Creates server monitor GUI
	 */
	synchronized void createServer()
	{
		try
		{
			serverMonitorwindow = new JFrame("Server monitor");
			serverMonitorwindow.setSize(676,730);
			serverMonitorwindow.getContentPane().setLayout(null);
			serverMonitorwindow.getContentPane().setBackground(new Color(205,202,191));
			serverMonitorwindow.getContentPane().setForeground(Color.white);
			serverMonitorwindow.setResizable(false);
			serverMonitorwindow.setLocationRelativeTo(null);

			serverMonitorLog = new JTextArea(30,30);
			serverMonitorLog.setEditable(false);
			JScrollPane scrollserverMonitorWindow = new JScrollPane(serverMonitorLog);
			scrollserverMonitorWindow.setBounds(10, 10, 652, 600);
			serverMonitorwindow.add(scrollserverMonitorWindow);
			serverMonitorwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			serverMonitorwindow.setVisible(true);
			startServer();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(serverMonitorwindow,"Some problem with server, please try again later!","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} 
	}
	/*
	 * Starts socket server and listen for client input 
	 */
	private void startServer()
	{
		try
		{
			serverMonitorLog.append("Connecting to port " + port + " ....\n");
			server = new ServerSocket(port);
			serverMonitorLog.append("Server is now running ..\n");
			
			while(true)
			{
				clientSocket = server.accept();
				SocketHandler c = new SocketHandler(clientSocket);
				c.start();
			}
			
		}
		catch (SocketException se)
		{
			JOptionPane.showMessageDialog(serverMonitorwindow,"Server already running!","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(serverMonitorwindow,"Unable to connect to specified port, please try again!","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		finally
		{
			if(server!=null)
				try
			{
					server.close();
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(serverMonitorwindow,"Problem occured while running server, please try again!","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}       
	}
	
	/*
	 * Reads data from client side and searches in DB for the synonym,
	 * sends back the synonym for selected word. 
	 */
	private class SocketHandler extends Thread
	{
		private Socket clientSocket;

		public SocketHandler(Socket clientSocket)
		{            
			super("socketHandler");
			this.clientSocket = clientSocket;
		}

		public synchronized void run()
		{
			try
			{
				BufferedReader streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintStream streamOut = new PrintStream(clientSocket.getOutputStream(), true);
				while(true){
					String selectedWord = streamIn.readLine();
					StringBuilder synonmys=null;
					StringBuilder userGUIOutput;
					/*
					 * If no word is selected on client or 
					 * if the word is not found on database,
					 * append nothing to the string sent  
					 * to client. 
					 */
					if(selectedWord.equalsIgnoreCase("null"))
					{
						userGUIOutput=new StringBuilder("0");
					}
					else
					{
						synonmys= new ThesaurusDao().getSynonyms(selectedWord.trim());
						if (synonmys!=null)
						{
							userGUIOutput=new StringBuilder(synonmys);
						}
						else
						{
							userGUIOutput=new StringBuilder("");
						}
					}	
					streamOut.println(userGUIOutput);
					//Display the selected word on server GUI
					if(selectedWord.equalsIgnoreCase("null"))
					{
						 serverMonitorLog.append("\nSelected word : No word selected");
					}
					else
					{
					    serverMonitorLog.append("\nSelected word : "+selectedWord);
					}
					
				}
			}
			catch(Exception e)
			{
				serverMonitorLog.append("\n"+e.getMessage());
			}
			finally
			{
				try
				{
					clientSocket.close();
				}
				catch(IOException e){
					serverMonitorLog.append("\n"+e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) {
		new Server().createServer();
	}
}