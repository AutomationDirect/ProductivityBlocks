package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class P1_HSC_Module  extends TranslatorBlock {

	public P1_HSC_Module (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String hscmodule;
			String slot ;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			hscmodule = translatorBlock.toCode();
			
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			slot = translatorBlock.toCode();
				
			translator.addDefinitionCommand("P1_HSC_Module " + hscmodule + "("+slot+");");
			return "";
		}
}