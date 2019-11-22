package com.ardublock.translator.block;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class ProgramBlock_Full extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	private List<String> setupCommand;
	
	public ProgramBlock_Full(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
		setupCommand = new LinkedList<String>();
	}
	
	

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    String ret="";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		while (translatorBlock != null)
		{
			if(!(translatorBlock instanceof RawCodeBlock) && !(translatorBlock instanceof CodeLineBlock)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.definition_block"));
			}
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
			
		}
		translator.addDefinitionCommand(ret);
		translatorBlock = getTranslatorBlockAtSocket(1);
		while (translatorBlock != null)
		{
			
			ret = translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
			this.setupCommand.add(ret);
		}
		
		translator.registerBodyTranslateFinishCallback(this);
//		return "";
		ret="";
		ret = "void loop()\n{\n";
		translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\n\n";
		translatorBlock = getTranslatorBlockAtSocket(3);
		while (translatorBlock != null)
		{
			if(!(translatorBlock instanceof RawCodeBlock) && !(translatorBlock instanceof CodeLineBlock)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.subprogram_block"));
			}
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}

		return ret;
	}
	
	@Override
	public void onTranslateBodyFinished()
	{
		for (String command : setupCommand)
		{
			translator.addSetupCommandForced(command);
		}
	}
	
}
