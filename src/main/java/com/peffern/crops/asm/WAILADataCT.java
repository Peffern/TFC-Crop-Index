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
 * ASM plugin to alter the WAILAData to use the custom WAILA function
 * @author peffern
 *
 */
public class WAILADataCT  implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if(name.equals("com.bioxx.tfc.WAILA.WAILAData"))
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
			if(m.name.equals("getWailaStack") && m.desc.equals("(Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Lnet/minecraft/item/ItemStack;"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				//iterate over the bytecode instructions
				while(it.hasNext())
				{
					AbstractInsnNode insn = it.next();
					if(insn instanceof MethodInsnNode)
					{
						//find the static call to WAILAData.cropStack and replace it with a call to TFCCropIndex.getWailaStack (desc are the same)
						MethodInsnNode minsn = (MethodInsnNode)insn;
						if(minsn.name.equals("cropStack"))
						{
							MethodInsnNode newMinsn = new MethodInsnNode(INVOKESTATIC, "com/peffern/crops/TFCCropIndex", "getWailaStack", minsn.desc, minsn.itf);
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
