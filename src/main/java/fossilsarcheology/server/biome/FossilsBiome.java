package fossilsarcheology.server.biome;

import fossilsarcheology.server.entity.mob.EntitySentryPigman;
import fossilsarcheology.server.gen.feature.HellMushroomWorldGen;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class FossilsBiome extends BiomeGenBase {
    public int genSelector;

    public FossilsBiome(int id, Block topBlock, Block fillerBlock, boolean clearAnimals, int lifeSelector, int genSelector) {
        super(id);
        this.topBlock = topBlock;
        this.fillerBlock = fillerBlock;
        this.genSelector = genSelector;
        if (clearAnimals) {
            this.spawnableMonsterList.clear();
            this.spawnableCreatureList.clear();
            this.spawnableWaterCreatureList.clear();
            this.spawnableCaveCreatureList.clear();

        }
        if (lifeSelector == 0) {
            this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 200, 1, 8));
            this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySentryPigman.class, 200, 1, 4));
        }
    }

    @Override
    public void decorate(World world, Random rand, int x, int z) {
        super.decorate(world, rand, x, z);
        if (genSelector == 0) {
            int generateX = x + rand.nextInt(16) + 8;
            int generateZ = z + rand.nextInt(16) + 8;
            if (rand.nextInt(1) == 0) {
                if (world.getHeightValue(generateX, generateZ) < 57) {
                    HellMushroomWorldGen hellMushroom = new HellMushroomWorldGen();
                    hellMushroom.generate(world, rand, generateX, world.getHeightValue(generateX, generateZ) + 1, generateZ);
                }
            }
        }
    }
}
