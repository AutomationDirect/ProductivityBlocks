package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class string_add extends TranslatorBlock
{
	public string_add(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb1 = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock tb2 = this.getRequiredTranslatorBlockAtSocket(1);
		
		String ret = "";
		if( (tb1 instanceof MessageBlock) && (tb2 instanceof MessageBlock) ) {
			ret = "strcat(" + tb1.toCode() + ", " + tb2.toCode() + ");";
		}
		else {
			ret = tb1.toCode() + " + " + tb2.toCode();
		}
		ret = codePrefix + ret + codeSuffix;
		return ret;
	}

}
