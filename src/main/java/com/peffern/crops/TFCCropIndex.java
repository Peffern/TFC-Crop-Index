package com.peffern.crops;


import java.lang.reflect.Method;

import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.ItemFoodTFC;

import cpw.mods.fml.common.Mod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


/**
 * Make TFCs crop registry extensible
 * @author peffern
 *
 */
@Mod(modid = TFCCropIndex.MODID, name = TFCCropIndex.MODNAME, version = TFCCropIndex.VERSION, dependencies = "required-after:" + "terrafirmacraft" + ";" + "required-after:" + "Waila" + ";")
public class TFCCropIndex 
{	
	/** Mod ID String */
	public static final String MODID = "tfccrops";
	
	/** Mod Name */
	public static final String MODNAME = "TFC Crop Index";
	
	/** Mod Version */
	public static final String VERSION = "1.2";

	/**
	 * Get the ItemStack to show in Waila when you look at a Crop
	 * @param accessor Waila block accessor
	 * @param config Waila config
	 * @return the display stack
	 */
	
}



