package com.ardublock.ui.listener;
//CHANGED
public interface OpenblocksFrameListener {
	public void didNew();
	public void didSave();
	public void didSaveAs();
	public void didLoad();	
	public void didGenerate(String source, boolean verifyOnly);
}
