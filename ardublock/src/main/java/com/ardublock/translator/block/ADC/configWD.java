package com.ardublock.translator.block.ADC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class configWD  extends TranslatorBlock {

	public configWD (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String milliseconds;
			String toggle ;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			milliseconds = translatorBlock.toCode();
			
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			toggle = translatorBlock.toCode();
				
			return "P1.configWD(" + milliseconds + ", (int)" + toggle + ");";	
		}
}
