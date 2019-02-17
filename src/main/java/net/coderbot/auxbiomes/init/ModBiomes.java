package net.coderbot.auxbiomes.init;

import net.coderbot.auxbiomes.AuxiliaryBiomes;
import net.coderbot.auxbiomes.biomes.BiomeMarsh;
import net.coderbot.auxbiomes.biomes.BiomeWasteland;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "auxbiomes")
public class ModBiomes {
	public static Biome MARSH;
	public static Biome WASTELAND;
	public static Biome ICE_WASTELAND;

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		{
			Biome.BiomeProperties marshProperties = new Biome.BiomeProperties("Marsh")
					.setTemperature(Biomes.SWAMPLAND.getDefaultTemperature())
					.setRainfall(Biomes.SWAMPLAND.getRainfall())
					.setBaseHeight(-0.75F)
					.setHeightVariation(0F);

			MARSH = registerBiome(event, new BiomeMarsh(marshProperties), BiomeManager.BiomeType.WARM, "marsh", 10);
			BiomeDictionary.addTypes(MARSH, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
		}

		{
			Biome.BiomeProperties wastelandProperties = new Biome.BiomeProperties("Wasteland")
					.setTemperature(Biomes.DESERT.getDefaultTemperature())
					.setRainfall(Biomes.DESERT.getRainfall())
					.setBaseHeight(0.1F)
					.setHeightVariation(0.0F)
					.setRainDisabled()
					.setWaterColor(0xF08000);

			// TODO: Cracked Sand
			WASTELAND = registerBiome(event, new BiomeWasteland(wastelandProperties, Blocks.STONE.getDefaultState(), true), BiomeManager.BiomeType.DESERT, "wasteland", 5);
			BiomeDictionary.addTypes(WASTELAND, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY);
		}

		{
			Biome.BiomeProperties iceWastelandProperties = new Biome.BiomeProperties("Ice Wasteland")
					.setTemperature(-0.5F)
					.setRainfall(0.1F)
					.setBaseHeight(0.35F)
					.setHeightVariation(0.05F)
					.setSnowEnabled();

			ICE_WASTELAND = registerBiome(event, new BiomeWasteland(iceWastelandProperties, Blocks.SNOW.getDefaultState(), false), BiomeManager.BiomeType.ICY, "ice_wasteland", 5);
			BiomeDictionary.addTypes(ICE_WASTELAND, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD);
		}
	}

	private static Biome registerBiome(RegistryEvent.Register<Biome> event, Biome biome, BiomeManager.BiomeType type, String name, int weight) {
		biome.setRegistryName(AuxiliaryBiomes.MODID, name);
		event.getRegistry().register(biome);

		BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));

		return biome;
	}
}
