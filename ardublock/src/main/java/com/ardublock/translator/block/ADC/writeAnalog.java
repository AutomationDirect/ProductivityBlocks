package com.ardublock.translator.block.ADC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class writeAnalog  extends TranslatorBlock {

	public writeAnalog (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String moduleNumber;
			String channelNumber ;
			String value;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			moduleNumber = translatorBlock.toCode();
			
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			channelNumber = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			value = translatorBlock.toCode();
				
			return "P1.writeAnalog(" + value + ", "+moduleNumber+", "+channelNumber+");" ;	
		}
}
