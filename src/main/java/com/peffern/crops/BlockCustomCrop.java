package com.peffern.crops;

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Modification of the TerraFirmaCraft BlockCrop class to handle the crop registry crops
 * @author peffern
 *
 */
public class BlockCustomCrop extends BlockCrop
{
	
	public BlockCustomCrop()
	{
		super();
		this.setBlockBounds(0, 0, 0, 1, 0.2f, 1);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		//normal BlockCrop behavior
		super.registerBlockIcons(register);
		
		//hook the registry to register its own icons
		CropsRegistry.registerIcons(register);
		
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		//get the crop
		TECrop te = (TECrop) access.getTileEntity(x, y, z);
		CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);

		//get the growth stage
		int stage = (int) Math.floor(te.growth);
		if(stage > crop.numGrowthStages)
			stage = crop.numGrowthStages;
		
		
		if(te.cropId <= 18)
		{
			//default TFC crops
			return super.getIcon(access, x,y,z,meta);
		}
		else
		{
			//try the crop register
			ICrop cropObj = CropsRegistry.getCrop(te.cropId);
			if(cropObj != null)
			{
				return cropObj.getIcon(stage);
			}
			else
			{
				return super.getIcon(access, x,y,z,meta);
			}
		}
		
	}
	
	//this may not be necessary, i wasnt sure how strict asm was being
	@Override
	public Block setBlockName(String s)
	{
		return super.setBlockName(s);
	}
}
