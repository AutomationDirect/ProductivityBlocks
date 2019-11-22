package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class pinMode extends TranslatorBlock
{
	
	public pinMode(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String pinNum = this.getRequiredTranslatorBlockAtSocket(0).toCode();
		String setting = this.getRequiredTranslatorBlockAtSocket(1).toCode();
		return "pinMode("+pinNum+", "+setting+");";
	}
	
}