package net.coderbot.auxbiomes.init;

import net.coderbot.auxbiomes.AuxiliaryBiomes;
import net.coderbot.auxbiomes.ModConfig;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = "auxbiomes")
public class ModItems {
	public static ItemBlock CRACKED_SAND;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event) {
		if(!ModConfig.wasteland.registerCrackedSand) {
			return;
		}

		CRACKED_SAND = new ItemBlock(ModBlocks.CRACKED_SAND);
		CRACKED_SAND.setRegistryName(new ResourceLocation(AuxiliaryBiomes.MODID, "cracked_sand"))
				.setUnlocalizedName(AuxiliaryBiomes.MODID + ".cracked_sand");

		event.getRegistry().register(CRACKED_SAND);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if(!ModConfig.wasteland.registerCrackedSand) {
			return;
		}

		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			registerModel(event, CRACKED_SAND);
		}
	}

	private static void registerModel(ModelRegistryEvent event, Item item) {
		if(item.getRegistryName() == null) {
			return;
		}

		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
