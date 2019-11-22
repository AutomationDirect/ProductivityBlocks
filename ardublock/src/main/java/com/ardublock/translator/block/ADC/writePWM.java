package com.ardublock.translator.block.ADC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class writePWM  extends TranslatorBlock {

	public writePWM (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String dutyCycle;
			String frequency;
			String moduleNumber;
			String channelNumber ;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			dutyCycle = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			frequency = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			moduleNumber = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);			
			channelNumber = translatorBlock.toCode();
				
			return "P1.writePWM(" + dutyCycle + ", " + frequency + ", " + moduleNumber + ", " + channelNumber + ");" ;	
		}
}
