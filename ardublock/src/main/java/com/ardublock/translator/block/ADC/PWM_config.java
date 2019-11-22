package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PWM_config  extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public PWM_config (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			TranslatorBlock slotblock = this.getRequiredTranslatorBlockAtSocket(0);
			String chan1 = this.getRequiredTranslatorBlockAtSocket(1).toCode().replace("UL", "");
			String chan2 = this.getRequiredTranslatorBlockAtSocket(2).toCode().replace("UL", "");
			String chan3 = this.getRequiredTranslatorBlockAtSocket(3).toCode().replace("UL", "");
			String chan4 = this.getRequiredTranslatorBlockAtSocket(4).toCode().replace("UL", "");
			String defstring = "{ " + chan1 + ", " + chan2 + ", " + chan3 + ", " + chan4 + " };";

			String internalVariableName = translator.getNumberVariable(label);
			if (internalVariableName == null)
			{
				internalVariableName = translator.buildVariableName(label);
				translator.addNumberVariable(label, internalVariableName);
				translator.addPreprocessorCommand("char " + internalVariableName + "[4] = " + defstring);
//				translator.addSetupCommand(internalVariableName + " = 0;");
			}
			
			return "P1.configureModule("+ internalVariableName + ", " + slotblock.toCode()+");";	
			
		}
}
