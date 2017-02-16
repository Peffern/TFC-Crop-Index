package com.peffern.crops;


import cpw.mods.fml.common.Mod;


/**
 * Make TFCs crop registry actually work.
 * @author peffern
 *
 */
@Mod(modid = TFCCropIndex.MODID, name = TFCCropIndex.MODNAME, version = TFCCropIndex.VERSION, dependencies = "required-after:" + "terrafirmacraft" + ";")
public class TFCCropIndex 
{	
	/** Mod ID String */
	public static final String MODID = "tfccrops";
	
	/** Mod Name */
	public static final String MODNAME = "TFC Crop Index";
	
	/** Mod Version */
	public static final String VERSION = "1.0";
}
