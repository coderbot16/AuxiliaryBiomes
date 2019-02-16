package net.coderbot.auxbiomes.init;

import net.coderbot.auxbiomes.AuxiliaryBiomes;
import net.coderbot.auxbiomes.biomes.BiomeMarsh;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "auxbiomes")
public class ModBiomes {
	public static Biome MARSH;

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		Biome.BiomeProperties marshProperties = new Biome.BiomeProperties("Marsh")
				.setTemperature(Biomes.SWAMPLAND.getDefaultTemperature())
				.setRainfall(Biomes.SWAMPLAND.getRainfall())
				.setBaseHeight(-0.75F)
				.setHeightVariation(0F);

		MARSH = registerBiome(event, new BiomeMarsh(marshProperties), BiomeManager.BiomeType.WARM, "marsh", 10);
		BiomeDictionary.addTypes(MARSH, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
	}

	private static Biome registerBiome(RegistryEvent.Register<Biome> event, Biome biome, BiomeManager.BiomeType type, String name, int weight) {
		biome.setRegistryName(AuxiliaryBiomes.MODID, name);
		event.getRegistry().register(biome);

		BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));

		return biome;
	}
}
