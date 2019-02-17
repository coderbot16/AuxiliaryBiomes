package net.coderbot.auxbiomes.biomes;

import net.coderbot.auxbiomes.gen.WorldGenDisabled;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeMarsh extends Biome {
	private static final int PILLAR_CHANCE_NUMERATOR = 2;
	private static final int PILLAR_CHANCE_DENOMINATOR = 5;
	private static final int FLOOR_LEVEL = 55;
	private IBlockState tallGrass = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

	public BiomeMarsh(BiomeProperties properties) {
		super(properties);
	}

	public static void makeBarren(BiomeDecorator decorator) {
		decorator.grassPerChunk = 0;
		decorator.extraTreeChance = 0.0F;
		decorator.flowersPerChunk = 0;
		decorator.sandPatchesPerChunk = 0;
		decorator.gravelPatchesPerChunk = 0;
		decorator.clayPerChunk = 0;
		decorator.reedGen = WorldGenDisabled.DISABLED;
		decorator.mushroomRedGen = WorldGenDisabled.DISABLED;
		decorator.mushroomBrownGen = WorldGenDisabled.DISABLED;
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		makeBarren(decorator);

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
		super.genTerrainBlocks(world, rand, chunk, x, z, noiseVal);

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

			if(rand.nextInt(3) <= 1 && chunk.getBlockState(cX, 62, cZ).getBlock() == Blocks.GRASS && chunk.getBlockState(cX, 63, cZ).getBlock() == Blocks.AIR) {
				chunk.setBlockState(cX, 63, cZ, tallGrass);
			}
		}
	}
}
