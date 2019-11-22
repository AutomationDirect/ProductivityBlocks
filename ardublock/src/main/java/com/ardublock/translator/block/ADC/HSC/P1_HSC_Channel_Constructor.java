package com.ardublock.translator.block.ADC.HSC;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class P1_HSC_Channel_Constructor extends TranslatorBlock {

	public P1_HSC_Channel_Constructor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
		
			//add the HSC library to header
			translator.addHeaderFile("P1_HSC.h");
			String hscmodule;
			String slot =  this.getRequiredTranslatorBlockAtSocket(1).toCode();
			String chann = this.getRequiredTranslatorBlockAtSocket(2).toCode();
			String isRotary = this.getRequiredTranslatorBlockAtSocket(3).toCode();
			String enableZReset = this.getRequiredTranslatorBlockAtSocket(4).toCode();
			String inhibitOn = this.getRequiredTranslatorBlockAtSocket(5).toCode();
			String mode = this.getRequiredTranslatorBlockAtSocket(6).toCode();
			String polarity = this.getRequiredTranslatorBlockAtSocket(7).toCode();
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			hscmodule = translatorBlock.toCode();
			
			String setupString = hscmodule + ".isRotary = " + isRotary + ";\n"
					+ hscmodule + ".enableZReset = " + enableZReset +";\n"
					+ hscmodule + ".inhibitOn = " + inhibitOn + ";\n"
					+ hscmodule + ".mode = " + mode + ";\n"
					+ hscmodule + ".polarity = " + polarity + ";\n";
					
					
			translator.addDefinitionCommand("P1_HSC_Channel " + hscmodule + "("+slot+", "+chann+");");
			translator.addSetupCommand(setupString);
			return "";
		}
}