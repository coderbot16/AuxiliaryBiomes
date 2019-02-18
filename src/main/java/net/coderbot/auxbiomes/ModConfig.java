package net.coderbot.auxbiomes;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid = AuxiliaryBiomes.MODID)
@Config.LangKey("auxbiomes.config.title")
public class ModConfig {
	@Config.Comment("Settings for the Marsh biome")
	public static final BiomeConfig marsh = new BiomeConfig(10, true);

	@Config.Comment("Settings for the Wasteland biome")
	public static final BiomeConfigWasteland wasteland = new BiomeConfigWasteland(5, true);

	@Config.Comment("Settings for the Ice Wasteland biome")
	public static final BiomeConfig iceWasteland = new BiomeConfig(5, true);

	@Config.Comment("Settings for the Forested Island biome")
	public static final BiomeConfig forestedIsland = new BiomeConfig(10, true);

	@Config.Comment("Settings for the White Forest biome")
	public static final BiomeConfig whiteForest = new BiomeConfig(10, true);

	public static class BiomeConfig {
		@Config.Comment("The weight of this biome in the biome generation list, 0 to disable generation")
		public int weight = 10;

		@Config.Comment("Whether this biome will be registered at all. This will break existing worlds if changed!")
		public boolean register = true;

		public BiomeConfig(int weight, boolean register) {
			this.weight = weight;
			this.register = register;
		}
	}

	public static class BiomeConfigWasteland{
		@Config.Comment("The weight of this biome in the biome generation list, 0 to disable generation")
		public int weight = 10;

		@Config.Comment("Whether this biome will be registered at all. This will break existing worlds if changed!")
		public boolean register = true;

		@Config.Comment("Whether Cracked Sand will be used for the surface. If false, Sandstone will be used instead.")
		public boolean useCrackedSand = true;

		@Config.Comment("Whether Cracked Sand will be registered with the game. If false, Sandstone will be used for the biome surface.")
		public boolean registerCrackedSand = true;

		public BiomeConfigWasteland(int weight, boolean register) {
			this.weight = weight;
			this.register = register;
		}
	}

	@Mod.EventBusSubscriber(modid = AuxiliaryBiomes.MODID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(AuxiliaryBiomes.MODID)) {
				ConfigManager.sync(AuxiliaryBiomes.MODID, Config.Type.INSTANCE);

				if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					Minecraft.getMinecraft().renderGlobal.loadRenderers();
				}
			}
		}
	}
}
