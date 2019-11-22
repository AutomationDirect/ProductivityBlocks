package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDRead2Block extends TranslatorBlock
{
	
	public static final String SDReadRoutine = "String __ProBlocksSDRead(String fileName, int seekTo, int length, int chipSelect) {\n"
			+ "char result[length];\n"
			+ "File readFile = SD.open(fileName);\n"
			+ "int index = 0;\n"
			+ "if (!readFile.seek(seekTo)){\n"
			+ "	return \"\";\n"
			+ "}\n"
			+ "while (readFile.available() && (index < length)){\n"
			+ "result[index++] = readFile.read();"
			+ "}\n"
			+ "readFile.close();\n"
			+ "return result;"
			+ "}";
	
	public SDRead2Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{	
	    String fileName =  this.getRequiredTranslatorBlockAtSocket(0).toCode();
	    String seek = this.getRequiredTranslatorBlockAtSocket(1).toCode();
	    String readLength = this.getRequiredTranslatorBlockAtSocket(2).toCode();
		String Cs = this.getRequiredTranslatorBlockAtSocket(3).toCode();
		
		translator.addDefinitionCommand(SDReadRoutine);
		translator.addHeaderFile("SD.h");
	    translator.addSetupCommand("SD.begin("+ Cs +");\n");
        return codePrefix + "__ProBlocksSDRead(\""+ fileName +"\", " + seek + ", " + readLength +", " + Cs + ")" + codeSuffix;
	}
}
