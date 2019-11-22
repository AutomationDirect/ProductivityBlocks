package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class configureChannels extends TranslatorBlock {

	public configureChannels(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String hscmodule;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			hscmodule = translatorBlock.toCode();
			return hscmodule+".configureChannels();";
		}
}