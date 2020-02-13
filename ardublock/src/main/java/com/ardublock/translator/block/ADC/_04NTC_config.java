package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class _04NTC_config  extends TranslatorBlock {
	
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public _04NTC_config (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			TranslatorBlock slotblock = this.getRequiredTranslatorBlockAtSocket(0);
			String chanEnable = this.getRequiredTranslatorBlockAtSocket(1).toCode().replace("UL", "");
			String burnout = this.getRequiredTranslatorBlockAtSocket(2).toCode().replace("UL", "");
			String range = this.getRequiredTranslatorBlockAtSocket(3).toCode().replace("UL", "");
			String filter = this.getRequiredTranslatorBlockAtSocket(4).toCode().replace("UL", "");
			
			String defstring = "{\n 0x40, " + chanEnable +", " +
					"\n 0x60, " + burnout +
					",\n" + "0x20, " + range +
					",\n" + "0x80, " + filter +
					"\n};";

			String arrayName = label + "_" + slotblock.toCode();
			String internalVariableName = translator.getNumberVariable(arrayName);
			if (internalVariableName == null)
			{
				internalVariableName = translator.buildVariableName(arrayName);
				translator.addNumberVariable(arrayName, internalVariableName);
				translator.addPreprocessorCommand("const char " + internalVariableName + "[] = " + defstring);
//				translator.addSetupCommand(internalVariableName + " = 0;");
			}
			
			return "P1.configureModule("+ internalVariableName + ", " + slotblock.toCode()+");";	
		}
}
