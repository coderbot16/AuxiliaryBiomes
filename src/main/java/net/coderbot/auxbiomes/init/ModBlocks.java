package net.coderbot.auxbiomes.init;

import net.coderbot.auxbiomes.AuxiliaryBiomes;
import net.coderbot.auxbiomes.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "auxbiomes")
public class ModBlocks {
	public static Block CRACKED_SAND;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		if(!ModConfig.wasteland.registerCrackedSand) {
			return;
		}

		CRACKED_SAND = new Block(Material.ROCK);
		CRACKED_SAND.setRegistryName(new ResourceLocation(AuxiliaryBiomes.MODID, "cracked_sand"))
				.setHardness(1.2F)
				.setUnlocalizedName(AuxiliaryBiomes.MODID + ".cracked_sand")
				.setHarvestLevel("pickaxe", 0);

		event.getRegistry().register(CRACKED_SAND);
	}
}
