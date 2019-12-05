package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

import com.ardublock.core.Context;
import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.NewButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import com.ardublock.ui.listener.SaveAsButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;

import edu.mit.blocks.codeblockutil.Explorer;
import edu.mit.blocks.codeblockutil.GlassExplorer;
import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;


public class OpenblocksFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841155965906223806L;
	private Color defaultBackground = new Color(250,250,250);
	private Context context;
	private JFileChooser fileChooser;
	private JFileChooser saveImageFileChooser;
	private FileFilter ffilter;
	private FileFilter saveImageFilter;
	private static JColorChooser chooser = new JColorChooser();
	private ResourceBundle uiMessageBundle;
	private JFrame aboutDialog;
	private URL iconURL;
	
	public void addListener(OpenblocksFrameListener ofl)
	{
		context.registerOpenblocksFrameListener(ofl);
	}
	
	public String makeFrameTitle()
	{
		String title = Context.APP_NAME + " " + context.getSaveFileName();
		if (context.isWorkspaceChanged())
		{
			title = title + " *";
		}
		return title;
		
	}
	
	public OpenblocksFrame()
	{
		context = Context.getContext();
		this.setTitle(makeFrameTitle());
		this.setSize(new Dimension(1024, 760));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		
		uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
		//fileChooser setup
		fileChooser = new JFileChooser();
		ffilter = new FileNameExtensionFilter(uiMessageBundle.getString("ardublock.file.suffix"), "abp");
		fileChooser.setFileFilter(ffilter);
		fileChooser.addChoosableFileFilter(ffilter);
		//save Image FileChooser setup
		saveImageFileChooser = new JFileChooser();
		saveImageFilter = new FileNameExtensionFilter("Image File (.png)", "png");
		saveImageFileChooser.setFileFilter(saveImageFilter);
		saveImageFileChooser.addChoosableFileFilter(saveImageFilter);
		initOpenBlocks();
	}
	
	private void initOpenBlocks()
	{
		final Context context = Context.getContext();
		final Color arduinoColor = new Color(0,151,157);
		/*
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspaceComponent = workspaceController.getWorkspacePanel();
		*/
		try {
			//this.iconURL = this.getClass().getResource("/com/ardublock/block/arduino/arduino.png");
			this.iconURL = this.getClass().getResource("/com/ardublock/block/ADC/smiley_99.png");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		final Workspace workspace = context.getWorkspace();
		workspace.setBackgroundColor(defaultBackground);
		workspace.addWorkspaceListener(new ArdublockWorkspaceListener(this));
		this.aboutDialog = new JFrame("About");
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		final JButton newButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.new"));
		newButton.addActionListener(new NewButtonListener(this));
		JButton saveButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.save"));
		saveButton.addActionListener(new SaveButtonListener(this));
		JButton saveAsButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.saveAs"));
		saveAsButton.addActionListener(new SaveAsButtonListener(this));
		JButton openButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.load"));
		openButton.addActionListener(new OpenButtonListener(this));
		JButton generateButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.upload"));
		generateButton.addActionListener(new GenerateCodeButtonListener(this, context, false));
		JButton verifyButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.verify"));
		verifyButton.addActionListener(new GenerateCodeButtonListener(this, context, true));
		JButton serialMonitorButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.serialMonitor"));
		serialMonitorButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				context.getEditor().handleSerial();
			}
		});
		JButton serialPlotterButton = new ProBlocksButton("Serial Plotter");
		serialPlotterButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				context.getEditor().handlePlotter();
			}
		});
		JButton saveImageButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.saveImage"));
		saveImageButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				Dimension size = workspace.getCanvasSize();
				System.out.println("size: " + size);
				int screenWidth = workspace.getBlockCanvas().getPageAt(0).getJComponent().getWidth();
				int screenHeight = workspace.getBlockCanvas().getPageAt(0).getJComponent().getHeight();
				BufferedImage bi = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D)bi.createGraphics();
				double theScaleFactor = 1f;//(300d/72d);  
				g.scale(theScaleFactor,theScaleFactor);
				
				workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
				try{
					saveImageFileChooser.setSelectedFile(new File("problocks_" + context.getSaveFileName().replace(".abp", "") +"_capture.png"));
					int returnVal = saveImageFileChooser.showSaveDialog(workspace.getBlockCanvas().getJComponent());
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = saveImageFileChooser.getSelectedFile();
			           	if(!file.getPath().endsWith(".png"))
			            	file = new File(file.getPath() + ".png");
						ImageIO.write(bi,"png",file);
			        }
				} catch (Exception e1) {
					
				} finally {
					g.dispose();
				}
			}
		});
		//set the button collection background
		buttons.setBackground(arduinoColor);
		buttons.add(newButton);
		buttons.add(saveButton);
		buttons.add(saveAsButton);
		buttons.add(openButton);
		buttons.add(verifyButton);
		buttons.add(generateButton);

		JButton aboutButton = new ProBlocksButton(uiMessageBundle.getString("ardublock.ui.aboutButton"));
		aboutButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		JButton backColorButton = new ProBlocksButton("Background Color");
		backColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doChangeBlockPanelBackground();
			}
		});
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(arduinoColor);
		bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(),38));
		bottomPanel.add(saveImageButton);
		bottomPanel.add(aboutButton);
		bottomPanel.add(backColorButton);
		bottomPanel.add(serialMonitorButton);
		bottomPanel.add(serialPlotterButton);
		
		
		this.add(buttons, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(workspace, BorderLayout.CENTER);
		
		//app icon
        try {
        	BufferedImage image = ImageIO.read(iconURL);
            this.setIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void closeOperation() {
		this.aboutDialog.dispose();
	}
	
	public void showAboutDialog() {
		//set up about dialog:
		Dimension aboutDialogDimension = new Dimension(600, 450);
		Color arduinoColor = new Color(0,151,157);
		aboutDialog.setMinimumSize(aboutDialogDimension);
		Point guiLocation = this.getLocation();
		Point dialogLocation = new Point(guiLocation.x + (this.getWidth() - aboutDialog.getWidth())/2, guiLocation.y + (this.getHeight() - aboutDialog.getHeight())/2);
		aboutDialog.setLocation(dialogLocation);
        try {
            BufferedImage image = ImageIO.read(iconURL);
            aboutDialog.setIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
		//set up text fields
		JLabel versionLabel = new JLabel("Version: " + uiMessageBundle.getString("problocks.ui.version"));
		versionLabel.setForeground(arduinoColor);
		versionLabel.setFont(new Font("Courier New",Font.PLAIN,12));
		versionLabel.setPreferredSize(new Dimension(aboutDialogDimension.width,20));
        JTextArea infoBox = new JTextArea();
        final String infoString = "ProductivityBlocks is an open source block programming plugin based on Ardublocks."
        		+ "\nCopyright (C) 2019  automationdirect.com\n" + 
        		"\n" + 
        		"    This program is free software: you can redistribute it and/or modify\n" + 
        		"    it under the terms of the GNU General Public License as published by\n" + 
        		"    the Free Software Foundation, either version 3 of the License, or\n" + 
        		"    (at your option) any later version.\n" + 
        		"\n" + 
        		"    This program is distributed in the hope that it will be useful,\n" + 
        		"    but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + 
        		"    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + 
        		"    GNU General Public License for more details.\n" + 
        		"\n" + 
        		"    You should have received a copy of the GNU General Public License\n" + 
        		"    along with this program.  If not, see https://www.gnu.org/licenses/.";
        infoBox.setText(infoString);
        infoBox.setFont(new Font("Ariel",14,14));
        infoBox.setEditable(false);
        infoBox.setLineWrap(true);
        infoBox.setWrapStyleWord(true);
        JScrollPane infoScroll = new JScrollPane(infoBox);
        infoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
		//set up hyper links:
		final String gnuPath = "\thttps://www.gnu.org/licenses/";
		JLabel gnuHyperlink = new JLabel(gnuPath);
		gnuHyperlink.setForeground(arduinoColor);
		gnuHyperlink.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
						url = new URL(gnuPath);
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
			    }
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {

		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {

		    }
		});
        
		final String ardublockPath = "\thttp://blog.ardublock.com/";
		JLabel ardublocksHyperlink = new JLabel(ardublockPath);
		ardublocksHyperlink.setForeground(arduinoColor);
		ardublocksHyperlink.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
						url = new URL(ardublockPath);
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
			    }
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {

		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {

		    }
		});
		
		final String websitePath = "\thttp://www.AutomationDirect.com";
		JLabel websiteHyperlink = new JLabel(websitePath);
		websiteHyperlink.setForeground(arduinoColor);
		websiteHyperlink.addMouseListener(new MouseAdapter() {
			 
		    @Override
		    public void mouseClicked(MouseEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
						url = new URL(websitePath);
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
			    }
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {

		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {

		    }
		});
		
		
		//add components to aboutDialog
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
		aboutPanel.add(versionLabel);
		aboutPanel.add(infoScroll);
		JPanel hyperlinkPanel = new JPanel();
		hyperlinkPanel.setLayout(new BoxLayout(hyperlinkPanel, BoxLayout.Y_AXIS));
		hyperlinkPanel.add(new JLabel("Links:"));
		hyperlinkPanel.add(gnuHyperlink);
		hyperlinkPanel.add(new JLabel("SOURCE CODE LINK TBD."));
		hyperlinkPanel.add(ardublocksHyperlink);
		hyperlinkPanel.add(websiteHyperlink);
		aboutPanel.add(hyperlinkPanel);
		aboutDialog.add(aboutPanel);
	    aboutDialog.setVisible(true);
	}
	
	public void doOpenArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.open_unsaved"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			if (optionValue == JOptionPane.YES_OPTION)
			{
				doSaveArduBlockFile();
				this.loadFile();
			}
			else
			{
				if (optionValue == JOptionPane.NO_OPTION)
				{
					this.loadFile();
				}
			}
		}
		else
		{
			this.loadFile();
		}
		this.setTitle(makeFrameTitle());
	}
	
	private void loadFile()
	{
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File savedFile = fileChooser.getSelectedFile();
			if (!savedFile.exists())
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				return ;
			}
			
			try
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				context.loadArduBlockFile(savedFile);
				context.setWorkspaceChanged(false);
			}
			catch (IOException e)
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				e.printStackTrace();
			}
			finally
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			context.getWorkspace().setBackgroundColor(context.getWorkspace().currentBackground);
		}
	}
	
	public boolean doSaveArduBlockFile()
	{
		if (!context.isWorkspaceChanged())
		{
			return true;
		}
		
		String saveString = getArduBlockString();
		
		if (context.getSaveFilePath() == null)
		{
			return chooseFileAndSave(saveString);
		}
		else
		{
			File saveFile = new File(context.getSaveFilePath());
			writeFileAndUpdateFrame(saveString, saveFile);
			return true;
		}
	}
	
	public void doSaveAsArduBlockFile()
	{
		if (context.isWorkspaceEmpty())
		{
			return ;
		}
		
		String saveString = getArduBlockString();
		
		chooseFileAndSave(saveString);
		
	}
	
	private boolean chooseFileAndSave(String ardublockString)
	{
		File saveFile = letUserChooseSaveFile();
		saveFile = checkFileSuffix(saveFile);
		if (saveFile == null)
		{
			return false;
		}
		
		if (saveFile.exists() && !askUserOverwriteExistedFile())
		{
			return false;
		}
		
		writeFileAndUpdateFrame(ardublockString, saveFile);
		return true;
	}
	
	private String getArduBlockString()
	{
		WorkspaceController workspaceController = context.getWorkspaceController();
		return workspaceController.getSaveString();
	}
	
	private void writeFileAndUpdateFrame(String ardublockString, File saveFile) 
	{
		try
		{
			saveArduBlockToFile(ardublockString, saveFile);
			context.setWorkspaceChanged(false);
			this.setTitle(this.makeFrameTitle());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private File letUserChooseSaveFile()
	{
		int chooseResult;
		chooseResult = fileChooser.showSaveDialog(this);
		if (chooseResult == JFileChooser.APPROVE_OPTION)
		{
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	private boolean askUserOverwriteExistedFile()
	{
		int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
		return (optionValue == JOptionPane.YES_OPTION);
	}
	
	private void saveArduBlockToFile(String ardublockString, File saveFile) throws IOException
	{
		context.saveArduBlockFile(saveFile, ardublockString);
		context.setSaveFileName(saveFile.getName());
		context.setSaveFilePath(saveFile.getAbsolutePath());
	}
	
	public void doNewArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.newfile_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			
			switch (optionValue)
			{
            	case JOptionPane.YES_OPTION:
            		doSaveArduBlockFile();
                    //break;
            	case JOptionPane.NO_OPTION:
            		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            		context.resetWorksapce();
            		context.setWorkspaceChanged(false);
            		this.setTitle(this.makeFrameTitle());
            		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            		break;
            	case JOptionPane.CANCEL_OPTION:
                    break;
			}
		}
		else
		{
			// If workspace unchanged just start a new Ardublock
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    		context.resetWorksapce();
    		context.setWorkspaceChanged(false);
    		this.setTitle(this.makeFrameTitle());
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		context.getWorkspace().setBackgroundColor(context.getWorkspace().currentBackground);
	}
	
	public void doCloseArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.close_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			switch (optionValue)
			{
            	case JOptionPane.YES_OPTION:
            		if (doSaveArduBlockFile())
            		{
            			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            		}
            		else
            		{
            			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            		}
                    break;
            	case JOptionPane.NO_OPTION:
            		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            		break;
            	case JOptionPane.CANCEL_OPTION:
            		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;
			}
		}
		else
		{
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
			
	}
	
	public void doChangeBlockPanelBackground() 
	{
		Color colorVal = null;
	    for (AbstractColorChooserPanel panel : chooser.getChooserPanels()) {
	    	if(!panel.getDisplayName().equals("HSV")) {
	    		chooser.removeChooserPanel(panel);
	    	}
	    }
	    //colorVal = chooser.showDialog(null, "Choose background color", Color.white);
        ColorTracker ok = new ColorTracker(chooser);
        JDialog dialog = JColorChooser.createDialog(null, "Choose Background Color", true, chooser, ok, null);

        dialog.show(); // blocks until user brings dialog down...
        colorVal = ok.getColor();
	    if(colorVal != null) {
	    	context.getWorkspace().setBackgroundColor(colorVal);
		}
	}
	
	private File checkFileSuffix(File saveFile)
	{
		if(saveFile == null)
		{
			return saveFile;
		}
		String filePath = saveFile.getAbsolutePath();
		if (filePath.endsWith(".abp"))
		{
			return saveFile;
		}
		else
		{
			return new File(filePath + ".abp");
		}
	}
	
	class ColorTracker implements ActionListener, Serializable {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JColorChooser chooser;
	    Color color;

	    public ColorTracker(JColorChooser c) {
	        chooser = c;
	    }

	    public void actionPerformed(ActionEvent e) {
	        color = chooser.getColor();
	    }

	    public Color getColor() {
	        return color;
	    }
	}
	
	public class ProBlocksButton extends JButton
	{
		private static final long serialVersionUID = 1L;
	
		private Color arduinoColor;
		private Color hoverColor;
		private Color pressedColor;
		
		public ProBlocksButton(String label) {
			super(label);
			this.arduinoColor = new Color(0,151,157);
			this.hoverColor = new Color(0,166,173);
			this.pressedColor = arduinoColor.darker();
			//this.setUI((ButtonUI) BasicButtonUI.createUI(this));
			this.setForeground(Color.WHITE);
			this.setBackground(arduinoColor);
			this.setBorderPainted(false);
			this.setFocusable(false);
			this.setFont(new Font(Font.DIALOG,Font.PLAIN,14));
		}
		@Override
		protected void paintComponent(Graphics gr) {
			Graphics2D g = (Graphics2D) gr;
			if(getModel().isPressed()) {
				g.setColor(pressedColor);
			}
			else if (getModel().isRollover()) {
				g.setColor(hoverColor);
			}
			else{
				g.setColor(arduinoColor);
			}
			g.fillRect(0,0,getWidth(),getHeight());
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.setFont(this.getFont());
			g.setColor(this.getForeground());
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(getText(), (getWidth() - metrics.stringWidth(getText()))/2 , (getHeight() - metrics.getHeight())/2 + metrics.getAscent());
		}
		
	}
}
