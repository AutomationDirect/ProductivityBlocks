package com.ardublock.translator.block.ADC;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TrueBlock;
import com.ardublock.translator.block.FalseBlock;
import com.ardublock.translator.block.exception.BlockException;
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
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		//check if the first argument is true or false:
		boolean usep1mod = true;
		if(!(translatorBlock instanceof TrueBlock) && !(translatorBlock instanceof FalseBlock)) {
			throw new BlockException(blockId, "The 'Use P1 Modules' option must be 'true' or 'false' block.");
		}
		if(translatorBlock instanceof FalseBlock) {
			usep1mod = false;
		}
		
	    //initialize the P1 modules
		translatorBlock = this.getTranslatorBlockAtSocket(1);
		if (usep1mod) {
			translator.addHeaderFile("P1AM.h");
			translator.addHeaderFile("P1_HSC.h");
			translator.addPreprocessorCommand(P1Autoinit.ProBlocksInfoString);
			this.setupCommand.add("while(!P1.init())\n{\n}\n");
		}
	    String ret="";
		while (translatorBlock != null)
		{
			ret += translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		this.setupCommand.add(ret);
		
		translator.registerBodyTranslateFinishCallback(this);

		ret="";
		ret = "void loop()\n{\n";
		TranslatorBlock translatorBlock2 = getTranslatorBlockAtSocket(2);
		while (translatorBlock2 != null)
		{
			
			ret = ret + translatorBlock2.toCode();
			translatorBlock2 = translatorBlock2.nextTranslatorBlock();
		}
		
		if (translator.isScoopProgram())
		{
			ret += "yield();\n";
		}
		
		ret = ret + "}\n\n";
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
