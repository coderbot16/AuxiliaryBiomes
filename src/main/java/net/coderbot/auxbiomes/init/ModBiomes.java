package net.coderbot.auxbiomes.init;

import net.coderbot.auxbiomes.AuxiliaryBiomes;
import net.coderbot.auxbiomes.ModConfig;
import net.coderbot.auxbiomes.biomes.BiomeMarsh;
import net.coderbot.auxbiomes.biomes.BiomeWasteland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
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
	public static Biome FORESTED_ISLAND;
	public static Biome WHITE_FOREST;

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		if(ModConfig.marsh.register) {
			Biome.BiomeProperties marshProperties = new Biome.BiomeProperties("Marsh")
					.setTemperature(Biomes.SWAMPLAND.getDefaultTemperature())
					.setRainfall(Biomes.SWAMPLAND.getRainfall())
					.setBaseHeight(-0.75F)
					.setHeightVariation(0F);

			MARSH = registerBiome(event, new BiomeMarsh(marshProperties), BiomeManager.BiomeType.WARM, "marsh", ModConfig.marsh.weight);
			BiomeDictionary.addTypes(MARSH, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
		}

		if(ModConfig.wasteland.register) {
			Biome.BiomeProperties wastelandProperties = new Biome.BiomeProperties("Wasteland")
					.setTemperature(Biomes.DESERT.getDefaultTemperature())
					.setRainfall(Biomes.DESERT.getRainfall())
					.setBaseHeight(0.1F)
					.setHeightVariation(0.0F)
					.setRainDisabled()
					.setWaterColor(0xF08000);

			IBlockState surface;

			if(ModConfig.wasteland.registerCrackedSand && ModConfig.wasteland.useCrackedSand) {
				surface = ModBlocks.CRACKED_SAND.getDefaultState();
			} else {
				surface = Blocks.SANDSTONE.getDefaultState();
			}

			WASTELAND = registerBiome(event, new BiomeWasteland(wastelandProperties, surface, true), BiomeManager.BiomeType.DESERT, "wasteland", ModConfig.wasteland.weight);
			BiomeDictionary.addTypes(WASTELAND, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY);
		}

		if(ModConfig.iceWasteland.register) {
			Biome.BiomeProperties iceWastelandProperties = new Biome.BiomeProperties("Ice Wasteland")
					.setTemperature(-0.5F)
					.setRainfall(0.1F)
					.setBaseHeight(0.35F)
					.setHeightVariation(0.05F)
					.setSnowEnabled();

			ICE_WASTELAND = registerBiome(event, new BiomeWasteland(iceWastelandProperties, Blocks.SNOW.getDefaultState(), false), BiomeManager.BiomeType.ICY, "ice_wasteland", ModConfig.iceWasteland.weight);
			BiomeDictionary.addTypes(ICE_WASTELAND, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD);
		}

		if(ModConfig.forestedIsland.register) {
			Biome.BiomeProperties forestedIslandProperties = new Biome.BiomeProperties("Forested Island")
					.setTemperature(Biomes.FOREST.getDefaultTemperature() + 0.1F)
					.setRainfall(Biomes.FOREST.getRainfall())
					.setBaseHeight(-1.0F)
					.setHeightVariation(0.8F);

			FORESTED_ISLAND = registerBiome(event, new BiomeForest(BiomeForest.Type.NORMAL, forestedIslandProperties), BiomeManager.BiomeType.WARM, "forested_island", ModConfig.forestedIsland.weight);
			BiomeDictionary.addTypes(FORESTED_ISLAND, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER);
		}

		if(ModConfig.whiteForest.register) {
			Biome.BiomeProperties whiteForestProperties = new Biome.BiomeProperties("White Forest")
					.setTemperature(Biomes.COLD_TAIGA_HILLS.getDefaultTemperature())
					.setRainfall(Biomes.COLD_TAIGA_HILLS.getRainfall())
					.setBaseHeight(0.3F)
					.setHeightVariation(0.2F)
					.setSnowEnabled();

			WHITE_FOREST = registerBiome(event, new BiomeForest(BiomeForest.Type.NORMAL, whiteForestProperties), BiomeManager.BiomeType.ICY, "white_forest", ModConfig.whiteForest.weight);
			BiomeDictionary.addTypes(WHITE_FOREST, BiomeDictionary.Type.COLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.HILLS);
		}
	}

	private static Biome registerBiome(RegistryEvent.Register<Biome> event, Biome biome, BiomeManager.BiomeType type, String name, int weight) {
		biome.setRegistryName(AuxiliaryBiomes.MODID, name);
		event.getRegistry().register(biome);

		if(weight > 0) {
			BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));
		}

		return biome;
	}
}
