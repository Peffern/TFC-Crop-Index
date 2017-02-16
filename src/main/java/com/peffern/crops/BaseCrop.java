package com.peffern.crops;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

/**
 * Base bare-bones ICrop implementation.
 * Assumes one guaranteed output and no secondary output.
 * Draws with crossed squares.
 * If you just want to implement a crop so that it works with TFC, use this implementation.
 * If you want to change the rendering or change the output behavior, use a subclass of this.
 * If you want to make some kind of weird separate implementation, then write your own ICrop implementation. 
 * @author peffern
 *
 */
public class BaseCrop implements ICrop
{
	/** crop name */
	private String name;
	/** crop type A/B/C etc. */
	private int type;
	/** crop growing time */
	private int time;
	/** registerIcon arguments */
	private String[] iconNames;	
	/** registerIcon return vals */
	private IIcon[] icons;
	/** growth temp minimum */
	private int minGTemp;
	/** living temp minimum */
	private int minATemp;
	/** nutrient consumption */
	private float nutrientUsage;
	/** nutrient restoration values (A/B/C) */
	private int[] nutrientRestore;
	/** yield item instance */
	private Item cropItem;
	/** amount (count or food weight) of yield */
	private float cropAmount;
	/** seed oredict name */
	private String seedOreName;
	/** seed icon name */
	private String seedIcon;
	/** seed unlocalized name */
	private String seedUnlocalizedName;
	
	/**
	 * Constructs a BaseCrop from parameters
	 * @param name the crop name e.g. "pumpkin"
	 * @param type the crop type A/B/C e.g. 2
	 * @param time the crop growth time e.g. 36
	 * @param iconNames the icon names e.g. {"TerraFirmaPumpkins:plants/crops/pumpkin (1)", "TerraFirmaPumpkins:plants/crops/pumpkin (2)", ... } 
	 * @param gTemp minimum growth temp e.g. 8
	 * @param aTemp minimum alive temp e.g. 0
	 * @param nutrient nutrient consumption e.g. 0.9f
	 * @param crop the actual Item to be dropped upon harvesting the crop
	 * @param amount how much of the Itme to be dropped upon harvesting this crop (stack size for non - food, food weight for food)
	 * @param seedOreName oreDict name of the seed Item e.g. "seedsPumpkin"
	 * @param seedIcon the icon name for seeds e.g. "terrafirmacraft:food/unused/img131"
	 * @param seedUnlocalizedName the unlocalized (language) name for the seeds e.g. "Seeds Pumpkin"
	 */
	public BaseCrop(String name, int type, int time, String[] iconNames, int gTemp, int aTemp, float nutrient, Item crop, float amount, String seedOreName, String seedIcon, String seedUnlocalizedName)
	{
		this.name = name;
		this.type = type;
		this.time = time;
		this.iconNames = iconNames;
		this.icons = new IIcon[this.iconNames.length];
		this.minGTemp = gTemp;
		this.minATemp = aTemp;
		this.nutrientUsage = nutrient;
		this.cropItem = crop;
		this.cropAmount = amount;
		this.seedOreName = seedOreName;
		this.seedIcon = seedIcon;
		this.seedUnlocalizedName = seedUnlocalizedName;
		this.nutrientRestore = null;
	}
	
	public BaseCrop(String name, int type, int time, String[] iconNames, int gTemp, int aTemp, float nutrient, Item crop, float amount, String seedOreName, String seedIcon, String seedUnlocalizedName, int[] nutrientRestore)
	{
		this(name,type,time,iconNames,gTemp,aTemp,nutrient,crop,amount,seedOreName,seedIcon,seedUnlocalizedName);
		this.nutrientRestore = nutrientRestore;
	}

	@Override
	public Item getOutput1Item() {
		return cropItem;
	}

	@Override
	public float getOutput1Qty() {
		return cropAmount;
	}

	@Override
	public Item getOutput2Item() {
		return null;
	}

	@Override
	public float getOutput2Qty() {
		return 0;
	}

	@Override
	public boolean render(Block block, int x, int y, int z, RenderBlocks renderblocks) 
	{
		//use the crossed squares renderer with some parameters I learned from TFC's code. I guess those are height and width or something.
		RenderCustomCrop.drawCrossedSquares(block,x,y,z,renderblocks,1.0,2.0);
		return true;
	}

	@Override
	public void registerIcons(IIconRegister register) 
	{
		//register the icons
		for(int i = 0; i < icons.length; i++)
		{
			icons[i] = register.registerIcon(iconNames[i]);
		}
	}

	@Override
	public IIcon getSeedIcon(IIconRegister register)
	{
		return register.registerIcon(this.seedIcon);
	}
	
	@Override
	public IIcon getIcon(int stage) 
	{
		return icons[stage];
	}



	@Override
	public String getName() 
	{
		return name;
	}



	@Override
	public int getType() 
	{
		return type;
	}



	@Override
	public int getTime() 
	{
		return time;
	}


	//stages are spaces between icon phases
	@Override
	public int getStages() 
	{
		return icons.length - 1;
	}



	@Override
	public int getMinGTemp() 
	{
		return minGTemp;
	}



	@Override
	public int getMinATemp() 
	{
		return minATemp;
	}



	@Override
	public float getNutrientUsage() 
	{
		return nutrientUsage;
	}
	
	@Override
	public int[] getNutrientRestore()
	{
		return nutrientRestore;
	}

	@Override
	public String getSeedOreName()
	{
		return seedOreName;
	}
	
	@Override
	public String getSeedUnlocalizedName()
	{
		return seedUnlocalizedName;
	}

}
