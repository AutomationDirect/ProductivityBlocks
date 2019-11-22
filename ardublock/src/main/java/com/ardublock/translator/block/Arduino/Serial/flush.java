package com.ardublock.translator.block.Arduino.Serial;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class flush extends TranslatorBlock {
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	public flush (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{	
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
			if(!(tb instanceof port0) && !(tb instanceof port1) && !(tb instanceof port2) && !(tb instanceof port3)) {
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.serial_port"));
			}
			String portNumber = tb.toCode();
			if(portNumber.equals("0"))
				portNumber = "";
			return "Serial" + portNumber + ".flush();" ;	
		}
}
