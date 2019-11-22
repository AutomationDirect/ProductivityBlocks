package com.ardublock.translator.block.Arduino.Serial;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class serialEvent extends TranslatorBlock {

	public serialEvent (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String code;
			code = "void serialEvent()\n{\n";
			TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
			while (translatorBlock != null)
			{
				code = code + translatorBlock.toCode();
				translatorBlock = translatorBlock.nextTranslatorBlock();
			}
			code = code + "}\n\n";
			//put the code in a declaration
			//translator.addDefinitionCommand(code);
			return code ;	
		}
}
