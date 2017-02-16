package com.peffern.crops;

import java.lang.reflect.Method;

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Render.Blocks.RenderCrop;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

/**
 * Custom Crop Renderer
 * @author peffern
 *
 */
public class RenderCustomCrop extends RenderCrop
{
	/*
	 * Much of this code is taken from RenderCrop. I've had bad luck ASMing switch statements
	 * so I went with this instead.It still calls RenderCrop in the case of everything but pumpkins/melons
	 */
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		
		//generic rendering code
		IBlockAccess blockaccess = renderblocks.blockAccess;
		TECrop cropTE = (TECrop)blockaccess.getTileEntity(x, y, z);

		if(cropTE != null)
			CropManager.getInstance().getCropFromId(cropTE.cropId);
		else
			return false;

		Tessellator var9 = Tessellator.instance;
		var9.setBrightness(block.getMixedBrightnessForBlock(blockaccess, x, y, z));
		
		if(cropTE.cropId <= 18)
		{
			//use default rendering code
			return RenderCrop.render(block, x, y, z, renderblocks);
		}
		else
		{
			//try the crops registry
			ICrop crop = CropsRegistry.getCrop(cropTE.cropId);
			if (crop != null)
			{
				return crop.render(block,x,y,z,renderblocks);
			}
		}
		
		//generic rendering code
		TileEntity te = blockaccess.getTileEntity(x, y-1, z);
		TEFarmland tef = null;
		if (te instanceof TEFarmland)
			tef = (TEFarmland) te;
		if(tef != null && tef.isInfested)
		{

			Tessellator tessellator = Tessellator.instance;
			tessellator.addVertexWithUV(x+0, y+0.001, z+1, ((BlockCrop)block).iconInfest.getMinU(), ((BlockCrop)block).iconInfest.getMaxV());
			tessellator.addVertexWithUV(x+1, y+0.001, z+1, ((BlockCrop)block).iconInfest.getMaxU(), ((BlockCrop)block).iconInfest.getMaxV());
			tessellator.addVertexWithUV(x+1, y+0.001, z+0, ((BlockCrop)block).iconInfest.getMaxU(), ((BlockCrop)block).iconInfest.getMinV());
			tessellator.addVertexWithUV(x+0, y+0.001, z+0, ((BlockCrop)block).iconInfest.getMinU(), ((BlockCrop)block).iconInfest.getMinV());

		}
		return true;
	}
	
	//invokes the drawCrossedSquares method of RenderCrop, which has been made private for a silly reason, using reflection
	public static void drawCrossedSquares(Block block, double a, double b, double c, RenderBlocks rb, double d, double e)
	{
		try
		{
			//invoke the drawCrossedSquares method from RenderCrop with reflection
			Method draw = RenderCrop.class.getDeclaredMethod("drawCrossedSquares", Block.class,double.class,double.class,double.class,RenderBlocks.class, double.class, double.class);
			draw.setAccessible(true);
			draw.invoke(RenderCrop.class, block,a,b,c,rb,d,e);
		}
		catch(Exception ex)
		{
		}	
	}
	
	//invokes the renderBlockCropsImpl method of RenderCrop, which has been made private for a silly reason, using reflection
	public static void renderBlockCropsImpl(Block block, double i, double j, double k, RenderBlocks rb, double d, double e)
	{
		try
		{
			//invoke the renderBLockCropsImpl method from RenderCrop with reflection
			Method draw = RenderCrop.class.getDeclaredMethod("renderBlockCropsImpl", Block.class,double.class,double.class,double.class,RenderBlocks.class, double.class, double.class);
			draw.setAccessible(true);
			draw.invoke(RenderCrop.class, block,i,j,k,rb,d,e);
		}
		catch(Exception ex)
		{
		}
	}
}
