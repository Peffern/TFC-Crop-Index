TFC Crop Index
================

For TFC 0.79.29

This is an API mod to make it easier for people to add TFC crops that work with TFC's base worldgen and nutrient system.  This abstracts away TFC's hardcoded crop rendering with ASM and has conflict avoidance for the integer crop IDs. 

How to Use:
=============
The entry point for TFC Crop Index is the static method CropsRegistry.addCrop(). This method accepts as an argument an ICrop instance, which is how you define the crop you want to add. The registry will use information from the ICrop to create a new TFC crop with the appropriate parameters, as well as creating a seed item with the appropriate crop ID. In addition, when your crop is rendered in the world, the crop renderer will call the registry which will ask your ICrop instance how to render it. The seed item is returned from the addCrop call so you can store it in your mod.

Since ICrop is large and annoying, the implementation BaseCrop is provided, which tries has predefined rendering code and fills out the interface for you. All of the parameters are in the constructor, so you should be able to just pass in a load of values and then pass the BaseCrop to the CropsRegistry.

Note: if you are using this mod in your environment, make sure to add com.peffern.crops.asm.CropsIndexLoading plugin to your list of Coremods so that my ASM gets executed

If you want to change the rendering behavior you can override the appropriate method.

Reflective pass-throughs to TFC's crop rendering utility methods are static on RenderCustomCrop, so you can use them.

In practice this means you pass a bunch of parameters to the BaseCrop constructor, pass the result to the CropsRegistry, and the mod handles it from there.