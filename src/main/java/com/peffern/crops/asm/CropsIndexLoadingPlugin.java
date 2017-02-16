package com.peffern.crops.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

/**
 * ASM plugin for TFC Crop index - modifies the crop block and crop renderer to reference my custom versions.
 * @author peffern
 *
 */
@IFMLLoadingPlugin.TransformerExclusions({"com.peffern.crops"})
public class CropsIndexLoadingPlugin implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass() 
	{
		return new String[]{BlockSetupCT.class.getName(), BlockRenderHandlerCT.class.getName()};
	}

	@Override
	public String getModContainerClass() 
	{
		return null;
	}

	@Override
	public String getSetupClass() 
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) 
	{
		
	}

	@Override
	public String getAccessTransformerClass() 
	{
		return null;
	}
}
