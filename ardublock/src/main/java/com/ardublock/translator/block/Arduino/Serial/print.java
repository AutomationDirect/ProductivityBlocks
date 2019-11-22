package com.ardublock.translator.block.Arduino.Serial;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class print extends TranslatorBlock {
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	public print (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String value = this.getRequiredTranslatorBlockAtSocket(0).toCode();		
			String newlinebool = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(2);
			if(!(tb instanceof port0) && !(tb instanceof port1) && !(tb instanceof port2) && !(tb instanceof port3)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.serial_port"));
			}
			String portNumber = tb.toCode();
			if(portNumber.equals("0"))
				portNumber = "";
			if(newlinebool == "true") {
				return "Serial" + portNumber + ".println("+ value +");";
			}else
				return "Serial" + portNumber + ".print(" + value + ");" ;	
		}
}
