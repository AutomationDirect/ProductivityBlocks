package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class MicrosBlock extends TranslatorBlock
{
	public MicrosBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		String ret = "micros()";
		return codePrefix + ret + codeSuffix;
	}
	
}
