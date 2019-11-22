package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class setpolarity extends TranslatorBlock {

	public setpolarity (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String channel;
			channel = this.getRequiredTranslatorBlockAtSocket(0).toCode();
			String value = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			return channel + ".polarity = " + value + ";";
		}
}