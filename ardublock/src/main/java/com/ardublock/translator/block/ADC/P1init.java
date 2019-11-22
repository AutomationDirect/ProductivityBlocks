package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class P1init  extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public P1init (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String infostring = "/******************************************************************************************"
							+ "\n   Automationdirect.com"
							+ "\nThis version supports P1AM.h Library " + uiMessageBundle.getString("ardublock.ui.P1AM_Version")
							+ "\nFor information on the P1AM-1xx hardware, please visit https://www.automationdirect.com"
							+ "\nTODO: Add 'ABOUT' information"
							+ "\n"
							+ "******************************************************************************************/";
		translator.addHeaderFile("P1AM.h");
		translator.addPreprocessorCommand(infostring);
		/*get the RSS, LED and SDCS names
		TranslatorBlock tb;
		for(int i = 0; i<3;i++) {
			tb = this.getRequiredTranslatorBlockAtSocket(i);		
			if (!(tb instanceof const_int)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
			}
		}
		String RSS = this.getRequiredTranslatorBlockAtSocket(0).toCode() + " = 31;\n";
		String LED = this.getRequiredTranslatorBlockAtSocket(1).toCode() + " = 32;\n";
		String SDCS = this.getRequiredTranslatorBlockAtSocket(2).toCode() + " = 28;\n";
		
		translator.addSetupCommand(RSS+LED+SDCS);*/
		return codePrefix + "P1.init()" + codeSuffix;
	}
}
