package com.peffern.crops;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Crop Interface for the registry – allows you to make your own custom crop if you really want to
 * @author peffern
 *
 */
public interface ICrop 
{
	/** crop name */
	public String getName();

	/** crop type A/B/C */
	public int getType();
	
	/** crop growth time */
	public int getTime();
	
	/** crop growth (icon) stages */
	public int getStages();
	
	/** minimum growth temp */
	public int getMinGTemp();
	
	/** minimum alive temp */
	public int getMinATemp();
	
	/** nutrient usage */
	public float getNutrientUsage();
	
	/** nutrient restore */
	public int[] getNutrientRestore();
	
	public ItemStack getOutput1();
	
	public ItemStack getOutput2();

	/** rendering code for this crop */
	public boolean render(Block block, int x, int y, int z, RenderBlocks renderblocks);
	
	/** register crop icons */
	public void registerIcons(IIconRegister register);
	
	/** get growth stage icon */
	public IIcon getIcon(int stage);
	
	/** register seed icons */
	public IIcon getSeedIcon(IIconRegister register);
	
	/** get seed orename */
	public String getSeedOreName();
	
	/** get seed unlocalized name */
	public String getSeedUnlocalizedName();
	
	
}
