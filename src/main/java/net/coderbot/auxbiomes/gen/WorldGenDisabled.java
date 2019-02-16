package net.coderbot.auxbiomes.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenDisabled extends WorldGenerator {
	public static final WorldGenDisabled DISABLED = new WorldGenDisabled();

	public WorldGenDisabled() {
		super(false);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		return false;
	}
}
