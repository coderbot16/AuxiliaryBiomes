package net.coderbot.auxbiomes.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class BiomeWasteland extends Biome {
	private boolean ugly;

	public BiomeWasteland(BiomeProperties properties, IBlockState ground, boolean ugly) {
		super(properties);

		this.topBlock = ground;
		this.fillerBlock = ground;

		spawnableCreatureList.clear();

		this.ugly = ugly;
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		BiomeMarsh.makeBarren(this.decorator);
		this.decorator.decorate(world, rand, this, pos);
	}

	@Override
	public int getModdedBiomeGrassColor(int original) {
		if(ugly) {
			original = 0x9E7C41;
		}

		return super.getModdedBiomeGrassColor(original);
	}

	@Override
	public int getModdedBiomeFoliageColor(int original) {
		if(ugly) {
			original = 0x9E7C41;
		}

		return super.getModdedBiomeFoliageColor(original);
	}
}
