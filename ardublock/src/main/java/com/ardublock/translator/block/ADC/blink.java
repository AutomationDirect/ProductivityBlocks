package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class blink extends TranslatorBlock {
	
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE =
			"boolean __proBlocksDigitalRead(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT);\n" + 
			"return digitalRead(pinNumber);\n" + 
			"}\n\n";
	public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = "void __proBlocksDigitalWrite(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n}\n";

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public blink (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String pinNum = this.getRequiredTranslatorBlockAtSocket(0).toCode();
			String onDelay = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			String offDelay = this.getRequiredTranslatorBlockAtSocket(2).toCode();
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_WRITE_DEFINE);
			String blinkCode =  "__proBlocksDigitalWrite(" + pinNum + ",HIGH);\n"
					+ "delay(" + onDelay + ");\n"
					+ "__proBlocksDigitalWrite(" + pinNum + ",LOW);\n"
					+ "delay(" + offDelay + ");";
			return blinkCode;
		}
}
