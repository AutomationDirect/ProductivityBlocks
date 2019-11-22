package com.ardublock.translator.block.ADC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class writePWMFreq  extends TranslatorBlock {

	public writePWMFreq (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String frequency;
			String moduleNumber;
			String channelNumber ;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			frequency = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			moduleNumber = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);			
			channelNumber = translatorBlock.toCode();
				
			return "P1.writePWMFreq(" + frequency + ", " + moduleNumber + ", " + channelNumber + ");";	
		}
}
