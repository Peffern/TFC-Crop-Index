package com.peffern.crops.asm;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

import static org.objectweb.asm.Opcodes.*;

/**
 * ASM plugin to alter the BlockRenderHandler to use the custom crop renderer
 * @author peffern
 *
 */
public class BlockRenderHandlerCT  implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if(name.equals("com.bioxx.tfc.Handlers.Client.BlockRenderHandler"))
		{
			return asmify(basicClass);
		}
		else
			return basicClass;
	}
	
	private byte[] asmify(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		for(MethodNode m : classNode.methods)
		{
			//find the render method
			if(m.name.equals("renderWorldBlock") && m.desc.equals("(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraft/block/Block;ILnet/minecraft/client/renderer/RenderBlocks;)Z"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				//iterate over the bytecode instructions
				while(it.hasNext())
				{
					AbstractInsnNode insn = it.next();
					if(insn instanceof MethodInsnNode)
					{
						//find the static call to RenderCrop.render and replace it with a call to RenderCustomCrop.render
						MethodInsnNode minsn = (MethodInsnNode)insn;
						if(minsn.getOpcode() == INVOKESTATIC && minsn.owner.equals("com/bioxx/tfc/Render/Blocks/RenderCrop"))
						{
							MethodInsnNode newMinsn = new MethodInsnNode(minsn.getOpcode(), "com/peffern/crops/RenderCustomCrop", minsn.name, minsn.desc, minsn.itf);
							m.instructions.insert(minsn,newMinsn);
							m.instructions.remove(minsn);
						}
					}
				}
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
	}
}
