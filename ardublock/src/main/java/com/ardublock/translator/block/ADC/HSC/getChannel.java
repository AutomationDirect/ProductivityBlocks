package com.ardublock.translator.block.ADC.HSC;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class getChannel extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public getChannel(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String hscmodule = this.getRequiredTranslatorBlockAtSocket(0).toCode();
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
			if(!(tb instanceof channel1) && !(tb instanceof channel2)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.getChanel"));
			}
			return hscmodule+"."+tb.toCode();
		}
}