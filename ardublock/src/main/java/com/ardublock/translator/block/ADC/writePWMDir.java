package com.ardublock.translator.block.ADC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class writePWMDir extends TranslatorBlock {

	public writePWMDir (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String data;
			String moduleNumber;
			String channelNumber ;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			data = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			moduleNumber = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);			
			channelNumber = translatorBlock.toCode();
				
			return "P1.writePWMDir(" + data + ", " + moduleNumber + ", " + channelNumber + ");" ;	
		}
}
