package com.ardublock;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

import processing.app.Editor;
import processing.app.EditorTab;
import processing.app.I18n;
import processing.app.SketchFile;
import processing.app.helpers.PreferencesMapException;
import processing.app.tools.Tool;
import processing.app.i18n.*;
import processing.app.tools.*;

import processing.app.debug.RunnerException;
import processing.app.debug.TargetBoard;
import processing.app.forms.PasswordAuthorizationDialog;
import processing.app.helpers.DocumentTextChangeListener;
import processing.app.helpers.Keys;
import processing.app.helpers.OSUtils;
import processing.app.helpers.StringReplacer;
import processing.app.legacy.PApplet;
import processing.app.syntax.PdeKeywords;
import processing.app.syntax.SketchTextArea;
import processing.app.tools.MenuScroller;
import static processing.app.I18n.tr;
import static processing.app.Theme.scale;

import com.ardublock.core.Context;
import com.ardublock.ui.ProBlocksToolFrame;
import com.ardublock.ui.listener.OpenblocksFrameListener;

public class ProductivityBlocks implements Tool, OpenblocksFrameListener
{
	static Editor editor;
	static ProBlocksToolFrame openblocksFrame;
	
	public void init(Editor editor) {
		if (ProductivityBlocks.editor == null )
		{
			ProductivityBlocks.editor = editor;
			ProductivityBlocks.openblocksFrame = new ProBlocksToolFrame();
			ProductivityBlocks.openblocksFrame.addListener(this);
			Context context = Context.getContext();
			String arduinoVersion = this.getArduinoVersion();
			context.setInArduino(true);
			context.setArduinoVersionString(arduinoVersion);
			context.setEditor(editor);
			System.out.println("Arduino Version: " + arduinoVersion);
			
			// Don't just "close" Ardublock, see if there's something to save first.
			// Note to self: Code here only affects behaviour when we're an Arduino Tool,
			// not when run directly - See Main.java for that.
			//ArduBlockTool.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			ProductivityBlocks.openblocksFrame.addWindowListener( new WindowAdapter()
			{
			    public void windowClosing(WindowEvent e)
			    {		        
			    	ProductivityBlocks.openblocksFrame.doCloseArduBlockFile();
			        ProductivityBlocks.openblocksFrame.closeOperation();
			    }
			});
		}
	}

	public void run() {
		try {
			ProductivityBlocks.editor.toFront();
			ProductivityBlocks.openblocksFrame.setVisible(true);
			ProductivityBlocks.openblocksFrame.toFront();
			ProductivityBlocks.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		} catch (Exception e) {
			
		}
	}

	public String getMenuTitle() {
		return Context.APP_NAME;
	}

	public void didSave() {
		
	}
	
	public void didLoad() {
		
	}
	
	public void didSaveAs()
	{
		
	}
	
	public void didNew()
	{
		
	}
	//CHANGED
	public void didGenerate(String source, boolean verifyOnly) {
		java.lang.reflect.Method method;
		try {
			// pre Arduino 1.6.12
			Class ed = ProductivityBlocks.editor.getClass();
			Class[] cArg = new Class[1];
			cArg[0] = String.class;
			method = ed.getMethod("setText", cArg);
			method.invoke(ProductivityBlocks.editor, source);
		}
		catch (NoSuchMethodException e) {
			ProductivityBlocks.editor.getCurrentTab().setText(source);
		} catch (IllegalAccessException e) {
			ProductivityBlocks.editor.getCurrentTab().setText(source);
		} catch (SecurityException e) {
			ProductivityBlocks.editor.getCurrentTab().setText(source);
		} catch (InvocationTargetException e) {
			ProductivityBlocks.editor.getCurrentTab().setText(source);
		}
		if(verifyOnly) {
			ProductivityBlocks.editor.handleRun(false,new BuildHandler(true),new BuildHandler());
		}
		else {
			ProductivityBlocks.editor.handleExport(false);
		}


	}
	
	private String getArduinoVersion()
	{
		Context context = Context.getContext();
		File versionFile = context.getArduinoFile("lib/version.txt");
		if (versionFile.exists())
		{
			try
			{
				InputStream is = new FileInputStream(versionFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String line = reader.readLine();
				reader.close();
				if (line == null)
				{
					return Context.ARDUINO_VERSION_UNKNOWN;
				}
				line = line.trim();
				if (line.length() == 0)
				{
					return Context.ARDUINO_VERSION_UNKNOWN;
				}
				return line;
				
			}
			catch (FileNotFoundException e)
			{
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
			catch (UnsupportedEncodingException e)
			{
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
		}
		else
		{
			return Context.ARDUINO_VERSION_UNKNOWN;
		}
		
	}
	
	  class BuildHandler implements Runnable {

		    private final boolean verbose;
		    private final boolean saveHex;

		    public BuildHandler() {
		      this(false);
		    }

		    public BuildHandler(boolean verbose) {
		      this(verbose, false);
		    }

		    public BuildHandler(boolean verbose, boolean saveHex) {
		      this.verbose = verbose;
		      this.saveHex = saveHex;
		    }

		    public void run() {
		      try {
		        ProductivityBlocks.editor.removeAllLineHighlights();
		        ProductivityBlocks.editor.getSketchController().build(verbose, saveHex);
		        ProductivityBlocks.editor.statusNotice(tr("Done compiling."));
		      } catch (PreferencesMapException e) {
		    	  ProductivityBlocks.editor.statusError(I18n.format(
		                tr("Error while compiling: missing '{0}' configuration parameter"),
		                e.getMessage()));
		      } catch (Exception e) {
		    	//ProBlocks.editor.status.unprogress();
		    	ProductivityBlocks.editor.statusError(e);
		      }

		      //status.unprogress();
		      //toolbar.deactivateRun();
		    }
		  }
	
}
