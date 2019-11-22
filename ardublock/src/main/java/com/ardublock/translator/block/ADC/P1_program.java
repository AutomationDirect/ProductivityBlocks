package com.ardublock.translator.block.ADC;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class P1_program extends TranslatorBlock
{
	
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	private List<String> setupCommand;
	
	public P1_program(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
		setupCommand = new LinkedList<String>();
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    //initialize the P1 modules
		translator.addHeaderFile("P1AM.h");
		translator.addHeaderFile("P1_HSC.h");
		translator.addPreprocessorCommand(P1Autoinit.ProBlocksInfoString);
		this.setupCommand.add("while(!P1.init())\n{\n}\n");
		
	    String ret="";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		while (translatorBlock != null)
		{
			ret += translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		this.setupCommand.add(ret);
		
		translator.registerBodyTranslateFinishCallback(this);
		translator.addDefinitionCommand(runstopswitch.ARDUBLOCK_DIGITAL_READ_DEFINE);

		ret="";
		ret = "void loop()\n{\n"
				+ "if(" + runstopswitch.callString + ")\n{\n";
		TranslatorBlock translatorBlock2 = getTranslatorBlockAtSocket(2);
		while (translatorBlock2 != null)
		{
			
			ret = ret + translatorBlock2.toCode();
			translatorBlock2 = translatorBlock2.nextTranslatorBlock();
		}
		ret += "\n}\n else \n{\n";
		TranslatorBlock translatorBlock3 = getTranslatorBlockAtSocket(3);
		while (translatorBlock3 != null)
		{
			
			ret = ret + translatorBlock3.toCode();
			translatorBlock3 = translatorBlock3.nextTranslatorBlock();
		}
		ret = ret + "}\n";
		
		ret += "}\n";
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
