package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDWrite2Block extends TranslatorBlock
{
	public static final String SDWriteRoutine = "void __ProBlocksSDWrite(String fileName, String writeString, boolean newLine, int chipSelect) {\n"
			+ "File writeFile = SD.open(fileName, FILE_WRITE);\n"
			+ "if (writeFile){\n"
			+ "writeFile.print(writeString);\n"
			+ "if (newLine){\n"
			+ "\twriteFile.println(\"\");\n"
			+ "}\n"
			+ "}\n"
			+ "writeFile.close();\n"
			+ "}\n";
	
	public SDWrite2Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String fileName = this.getRequiredTranslatorBlockAtSocket(0).toCode();
		String writeString = this.getRequiredTranslatorBlockAtSocket(1).toCode();
		String newLine = this.getRequiredTranslatorBlockAtSocket(2).toCode();
		String Cs = this.getRequiredTranslatorBlockAtSocket(3).toCode();
		
		translator.addHeaderFile("SD.h");
	    translator.addSetupCommand("SD.begin(" + Cs +");\n");
	    translator.addDefinitionCommand(SDWriteRoutine);
	    
		return  "__ProBlocksSDWrite(" + fileName + ", " + writeString + ", " + newLine + ", " + Cs + ");";
	}
}
