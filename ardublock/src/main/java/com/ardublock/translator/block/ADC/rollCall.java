package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class rollCall extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public rollCall (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String numberOfModules = this.getRequiredTranslatorBlockAtSocket(0).toCode();
			String module1 = this.getRequiredTranslatorBlockAtSocket(1).toCode();
			String module2 = this.getRequiredTranslatorBlockAtSocket(2).toCode();
			String module3 = this.getRequiredTranslatorBlockAtSocket(3).toCode();
			String module4 = this.getRequiredTranslatorBlockAtSocket(4).toCode();
			String module5 = this.getRequiredTranslatorBlockAtSocket(5).toCode();
			String module6 = this.getRequiredTranslatorBlockAtSocket(6).toCode();
			String module7 = this.getRequiredTranslatorBlockAtSocket(7).toCode();
			String module8 = this.getRequiredTranslatorBlockAtSocket(8).toCode();
			String module9 = this.getRequiredTranslatorBlockAtSocket(9).toCode();
			String module10 = this.getRequiredTranslatorBlockAtSocket(10).toCode();
			String module11 = this.getRequiredTranslatorBlockAtSocket(11).toCode();
			String module12 = this.getRequiredTranslatorBlockAtSocket(12).toCode();
			String module13 = this.getRequiredTranslatorBlockAtSocket(13).toCode();
			String module14 = this.getRequiredTranslatorBlockAtSocket(14).toCode();
			String module15 = this.getRequiredTranslatorBlockAtSocket(15).toCode();
			String defstring = "{ " + module1 + ", " + module2 + ", " + module3 + ", " + module4 + ", " +
					 module5 + ", " + module6 + ", " + module7 + ", " + module8 + ", " + module9 + ", " +
					 module10 + ", " + module11 + ", " + module12 + ", " + module13 + ", " + module14 + ", " +
					 module15 + "};";

			String internalVariableName = translator.getNumberVariable(label);
			if (internalVariableName == null)
			{
				internalVariableName = translator.buildVariableName(label);
				translator.addNumberVariable(label, internalVariableName);
				translator.addPreprocessorCommand("const char* " + internalVariableName + "[] = " + defstring);
//				translator.addSetupCommand(internalVariableName + " = 0;");
			}
			
			return  codePrefix + "P1.rollCall("+ internalVariableName + ", " + numberOfModules + ")" + codeSuffix;	
		}
}
