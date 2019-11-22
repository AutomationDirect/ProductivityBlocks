package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class readRollOver extends TranslatorBlock {

	public readRollOver (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String channel;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			channel = translatorBlock.toCode();
			return codePrefix + channel + ".readRollOver()" + codeSuffix;
		}
}