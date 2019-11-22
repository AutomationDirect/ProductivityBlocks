package com.ardublock.translator.block.Arduino.Serial;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class printMonitor extends TranslatorBlock {

	public printMonitor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String value = this.getRequiredTranslatorBlockAtSocket(0).toCode();		
			String newlinebool = this.getRequiredTranslatorBlockAtSocket(1).toCode();

			if(newlinebool == "true") {
				return "Serial.println("+ value +");";
			}else
				return "Serial.print(" + value + ");" ;	
		}
}
