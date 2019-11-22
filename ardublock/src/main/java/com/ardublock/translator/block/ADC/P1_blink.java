package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class P1_blink extends TranslatorBlock {
	
	public P1_blink (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String slot = this.getRequiredTranslatorBlockAtSocket(0).toCode();
			String channel = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			String onTime = this.getRequiredTranslatorBlockAtSocket(2).toCode();
			String offTime = this.getRequiredTranslatorBlockAtSocket(3).toCode();

			String blinkCode =  "P1.writeDiscrete(HIGH, "+ slot +", " + channel +");\n"
					+ "delay(" + onTime + ");\n"
					+ "P1.writeDiscrete(LOW, "+ slot +", " + channel +");\n"
					+ "delay(" + offTime + ");\n";
			return blinkCode;
		}
}
