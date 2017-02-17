package com.peffern.crops;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropIndexJute;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomSeeds;
import com.bioxx.tfc.TileEntities.TECrop;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registry for all custom crops. They all live here and it maps ids to crops
 * @author peffern
 *
 */
public class CropsRegistry 
{
	/** The Map */
	private static Map<Integer,ICrop> CROPS_MAP;
	
	/** The Seed-Item Map */
	private static Map<Integer,Item> SEEDS_MAP;
	
	/** The current map index */
	private static AtomicInteger CROPS_ID_INDEX;
	
	/** initialize */
	static
	{
		CROPS_MAP = new HashMap<Integer,ICrop>();
		SEEDS_MAP = new HashMap<Integer,Item>();
		CROPS_ID_INDEX = new AtomicInteger(19);
	}
	
	/**
	 * Register a new ICrop
	 * @param crop the crop to add
	 * @return the generated Seed item
	 */
	public static Item addCrop(final ICrop crop)
	{
		//curren index
		int cropId = CROPS_ID_INDEX.getAndIncrement();
		//register the seeds item
		Item seed = new ItemCustomSeeds(cropId)
		{
			//override the icon registerer crops sedd register
			@Override
			public void registerIcons(IIconRegister registerer)
			{
				this.itemIcon = crop.getSeedIcon(registerer);
			}
		};
		//localization
		seed.setUnlocalizedName(crop.getSeedUnlocalizedName());
		GameRegistry.registerItem(seed, seed.getUnlocalizedName());
		
		//generate TFC crop index
		int[] nr = crop.getNutrientRestore();
		if(nr == null)
			nr = new int[]{0,0,0};
				
		CropIndex index = new CropIndex(cropId, crop.getName(), crop.getType(), crop.getTime(), crop.getStages(), crop.getMinGTemp(), crop.getMinATemp(), crop.getNutrientUsage(), seed, nr)
		{
			@Override
			public ItemStack getOutput1(TECrop te)
			{
				ItemStack is = crop.getOutput1();
				if (is != null)
					return is.copy();
				else
					return null;
			}
			
			@Override
			public ItemStack getOutput2(TECrop te)
			{
				ItemStack is = crop.getOutput2();
				if (is != null)
					return is.copy();
				else
					return null;
			}
		};
		ItemStack is = crop.getOutput1();
		if(is != null)
		{
			Item i1 = is.getItem();
			int c = is.stackSize;
			if (i1 != null)
				index.setOutput1(i1, c);
		}
		//register with TFC
		CropManager.getInstance().addIndex(index);
		//into the map
		CROPS_MAP.put(cropId, crop);
		SEEDS_MAP.put(cropId, seed);
		
		//add the seeds to the seeds array
		Item[] rs = Recipes.seeds;
		Item[] rsn = new Item[rs.length + 1];
		System.arraycopy(rs, 0, rsn, 0, rs.length);
		rsn[rsn.length-1] = seed;
		Recipes.seeds = rsn;
		
		//register seeds in ore dictionary
		final int WILD = OreDictionary.WILDCARD_VALUE;
		OreDictionary.registerOre("seedBag", new ItemStack(seed, 1, WILD));
		OreDictionary.registerOre(crop.getSeedOreName(), new ItemStack(seed, 1, WILD));
		
		
		return seed;
	}
	
	/**
	 * Gets an ICrop instance for a given ID (>19)
	 * @param cropId the crop id
	 * @return
	 */
	public static ICrop getCrop(int cropId)
	{
		return CROPS_MAP.get(cropId);
	}
	
	/**
	 * Registers the crops' icons in the register
	 * @param register the register
	 */
	public static void registerIcons(IIconRegister register)
	{
		for(ICrop c : CROPS_MAP.values())
		{
			c.registerIcons(register);
		}
	}
}
