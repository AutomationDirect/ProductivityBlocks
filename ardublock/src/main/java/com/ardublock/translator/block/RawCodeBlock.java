package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RawCodeBlock extends TranslatorBlock
{
	public RawCodeBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String code = translator.getBlock(blockId).getCodeBlockText();
		return "\n/*BEGIN CODE BLOCK INJECTED CODE*/ \n" + code + "\n/*END CODE BLOCK INJECTED CODE*/\n";
	}
}
