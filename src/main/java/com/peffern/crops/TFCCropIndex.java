package com.peffern.crops;


import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.ItemFoodTFC;

import cpw.mods.fml.common.Mod;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
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
	public static final String VERSION = "1.1";

	/**
	 * Get the ItemStack to show in Waila when you look at a Crop
	 * @param accessor Waila block accessor
	 * @param config Waila config
	 * @return the display stack
	 */
	public static ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		int cropId = tag.getInteger("cropId");

		//default behavior from WAILAData.cropStack()
		if(cropId <= 18)
		{
			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
			ItemStack itemstack;

			if (crop.output2 != null)
				itemstack = new ItemStack(crop.output2);
			else
				itemstack = new ItemStack(crop.output1);

			ItemFoodTFC.createTag(itemstack);
			return itemstack;
		}
		else
		{
			ICrop crop = CropsRegistry.getCrop(cropId);
			ItemStack is = crop.getOutput1();
			if(is != null)
			{
				Item item = is.getItem();
				if(item != null)
				{
					//get the output stack instead of the item – supports metadata
					ItemStack ret = new ItemStack(item,1,is.getItemDamageForDisplay());
					if (item instanceof ItemFoodTFC)
						ItemFoodTFC.createTag(ret);
					return ret;
				}
			}
			return null;
		}
		
	}
}



