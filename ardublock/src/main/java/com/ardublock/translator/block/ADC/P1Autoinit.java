package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class P1Autoinit  extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public static final String ProBlocksInfoString = "/******************************************************************************************"
							+ "\n   Automationdirect.com"
							+ "\nThis version of ProductivityBlocks supports P1AM-100 Library " + uiMessageBundle.getString("problocks.ui.P1AM_Version")
							+ "\nTo download this library, please visit " + uiMessageBundle.getString("problocks.p1_library_link")
							+ "\nFor information on the P1AM-100 hardware, please visit https://www.automationdirect.com"
							+ "\n"
							+ "******************************************************************************************/";
	
	public P1Autoinit (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("P1AM.h");
		translator.addHeaderFile("P1_HSC.h");
		translator.addPreprocessorCommand(ProBlocksInfoString);
		String ret = "while(!P1.init())\n{\n}\n";
		return ret;
	}
}
