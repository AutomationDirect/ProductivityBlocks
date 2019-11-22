package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class configureChannels_chan1_chan2 extends TranslatorBlock {

	public configureChannels_chan1_chan2(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String hscmodule;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			String chan1 = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			String chan2 = this.getRequiredTranslatorBlockAtSocket(2).toCode();
			hscmodule = translatorBlock.toCode();
			return hscmodule+".configureChannels(" + chan1 + ", " + chan2 + ");";
		}
}