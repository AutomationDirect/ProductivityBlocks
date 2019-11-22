package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class runstopswitch  extends TranslatorBlock {
	
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE =
			/* The charge left on the pin if it was previously OUTPUT does affect
			 *  the likely result of digitalRead **if the pin is floating**.
			 * I can find nothing to justify a need for a 'settling period' delay
			 *  if the pin mode has been changed.
			 *  and delay 5ms if the pinMode has changed */
			"boolean __proBlocksDigitalRead(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT);\n" + 
			"return digitalRead(pinNumber);\n" + 
			"}\n\n";
	public static final String callString = "__proBlocksDigitalRead(31)";
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public runstopswitch (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
			return codePrefix + callString + codeSuffix;
		}
}