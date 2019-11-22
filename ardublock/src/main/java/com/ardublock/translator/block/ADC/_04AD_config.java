package com.ardublock.translator.block.ADC;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableFakeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class _04AD_config  extends TranslatorBlock {

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public _04AD_config (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			TranslatorBlock slotblock = this.getRequiredTranslatorBlockAtSocket(0);
			String chanEnable = this.getRequiredTranslatorBlockAtSocket(1).toCode().replace("UL", "");

			String chan1Range = this.getRequiredTranslatorBlockAtSocket(2).toCode().replace("UL", "");
			String chan1filter = this.getRequiredTranslatorBlockAtSocket(3).toCode().replace("UL", "");
			String chan2Range = this.getRequiredTranslatorBlockAtSocket(4).toCode().replace("UL", "");
			String chan2filter = this.getRequiredTranslatorBlockAtSocket(5).toCode().replace("UL", "");
			String chan3Range = this.getRequiredTranslatorBlockAtSocket(6).toCode().replace("UL", "");
			String chan3filter = this.getRequiredTranslatorBlockAtSocket(7).toCode().replace("UL", "");
			String chan4Range = this.getRequiredTranslatorBlockAtSocket(8).toCode().replace("UL", "");
			String chan4filter = this.getRequiredTranslatorBlockAtSocket(9).toCode().replace("UL", "");
			String defstring = "{\n 0x40, " + chanEnable +", " + "0x00, 0x02, "+
					"\n 0x20, " + chan1Range +", 0xC0, " + chan1filter + ", 0x00, 0x02," +
					"\n " + "0x21, " + chan2Range +", 0xC1, " + chan2filter + ", 0x00, 0x02," +
					"\n " + "0x22, " + chan3Range +", 0xC2, " + chan3filter + ", 0x00, 0x02," +
					"\n " + "0x23, " + chan4Range +", 0xC3, " + chan4filter +
					"\n};";

			String internalVariableName = translator.getNumberVariable(label);
			if (internalVariableName == null)
			{
				internalVariableName = translator.buildVariableName(label);
				translator.addNumberVariable(label, internalVariableName);
				translator.addPreprocessorCommand("char " + internalVariableName + "[26] = " + defstring);
//				translator.addSetupCommand(internalVariableName + " = 0;");
			}
			
			return "P1.configureModule("+ internalVariableName + ", " + slotblock.toCode()+");";	
		}
}
