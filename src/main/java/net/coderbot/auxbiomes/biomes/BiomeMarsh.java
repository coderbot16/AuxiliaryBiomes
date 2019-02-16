package net.coderbot.auxbiomes.biomes;

import net.coderbot.auxbiomes.gen.WorldGenDisabled;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeMarsh extends Biome {
	private static final int PILLAR_CHANCE_NUMERATOR = 2;
	private static final int PILLAR_CHANCE_DENOMINATOR = 5;
	private static final int FLOOR_LEVEL = 55;

	public BiomeMarsh(BiomeProperties properties) {
		super(properties);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		this.decorator.grassPerChunk = 32;
		this.decorator.extraTreeChance = 0.0F;
		this.decorator.flowersPerChunk = 0;
		this.decorator.sandPatchesPerChunk = 0;
		this.decorator.gravelPatchesPerChunk = 0;
		this.decorator.clayPerChunk = 0;
		this.decorator.reedGen = WorldGenDisabled.DISABLED;
		this.decorator.mushroomRedGen = WorldGenDisabled.DISABLED;
		this.decorator.mushroomBrownGen = WorldGenDisabled.DISABLED;

		ChunkPos chunkPos = new ChunkPos(pos);

		if(TerrainGen.decorate(worldIn, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SAND)) {
			for (int i = 0; i < 3; ++i) {
				int dX = rand.nextInt(16) + 8;
				int dZ = rand.nextInt(16) + 8;
				this.decorator.sandGen.generate(worldIn, rand, pos.add(dX, FLOOR_LEVEL, dZ));
			}
		}

		if(TerrainGen.decorate(worldIn, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SAND_PASS2)) {
			int dX = rand.nextInt(16) + 8;
			int dZ = rand.nextInt(16) + 8;
			this.decorator.gravelGen.generate(worldIn, rand, pos.add(dX, FLOOR_LEVEL, dZ));
		}

		if(TerrainGen.decorate(worldIn, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.CLAY)) {
			int dX = rand.nextInt(16) + 8;
			int dZ = rand.nextInt(16) + 8;
			this.decorator.clayGen.generate(worldIn, rand, pos.add(dX, FLOOR_LEVEL, dZ));
		}

		this.decorator.decorate(worldIn, rand, this, pos);
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, @Nonnull ChunkPrimer chunk, int z, int x, double noiseVal) {
		if(rand.nextInt(PILLAR_CHANCE_DENOMINATOR) <= PILLAR_CHANCE_NUMERATOR - 1) {
			int cX = x & 15;
			int cZ = z & 15;

			for(int y = world.getSeaLevel() - 1; y >= 0; y--) {
				if(chunk.getBlockState(cX, y, cZ).getBlock() != Blocks.WATER) {
					break;
				}

				if(y == world.getSeaLevel() - 1) {
					chunk.setBlockState(cX, y, cZ, topBlock);
				} else {
					chunk.setBlockState(cX, y, cZ, fillerBlock);
				}
			}
		}

		super.genTerrainBlocks(world, rand, chunk, x, z, noiseVal);
	}
}
