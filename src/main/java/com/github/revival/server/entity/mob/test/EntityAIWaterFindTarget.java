package com.github.revival.server.entity.mob.test;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.github.revival.server.entity.mob.EntityFishBase;

public class EntityAIWaterFindTarget extends EntityAIBase
{
	private EntityFishBase mob;
	private double shelterX;
	private double shelterY;
	private double shelterZ;
	private double movementSpeed;
	private World theWorld;

	public EntityAIWaterFindTarget(EntityFishBase mob, double speed){
		this.mob = mob;
		this.movementSpeed = speed;
		this.theWorld = mob.worldObj;
		this.setMutexBits(1);
	}

	public boolean shouldExecute(){
		if(mob.isDirectPathBetweenPoints(mob.getPosition(1.0F), Vec3.createVectorHelper(shelterX, shelterY, shelterZ))){
			mob.currentTarget = null;
		}

		if(mob.currentTarget != null && mob.getDistance(mob.currentTarget.posX, mob.currentTarget.posY, mob.currentTarget.posZ) < 10F){
			return false;
		}
		else
		{
			Vec3 vec3 = this.findWaterTarget();

			if (vec3 == null)
			{
				return false;
			}
			else
			{
				this.shelterX = vec3.xCoord;
				this.shelterY = vec3.yCoord;
				this.shelterZ = vec3.zCoord;
				mob.shelterX = this.shelterX;
				mob.shelterY = this.shelterY;
				mob.shelterZ = this.shelterZ;
				return true;
			}
		}
	}

	public boolean continueExecuting(){
		return mob.currentTarget != null;
	}

	public void startExecuting(){
		this.mob.currentTarget = new ChunkCoordinates((int)shelterX, (int)shelterY, (int)shelterZ);
	}

	public Vec3 findWaterTarget(){
		if(mob.getAttackTarget() == null || mob.ridingEntity != null && mob.getAttackTarget() != null){
			Random random = this.mob.getRNG();
			mob.setAttackTarget(null);
			ChunkCoordinates chunkCoordinates = getCoords();
			for (int i = 0; i < 10; ++i)
			{
				ChunkCoordinates chunkCoordinates1 = new ChunkCoordinates(chunkCoordinates.posX + random.nextInt(20) - 10, chunkCoordinates.posY + random.nextInt(6) - 3, chunkCoordinates.posZ + random.nextInt(20) - 10);
				if (mob.worldObj.getBlock(chunkCoordinates1.posX, chunkCoordinates1.posY, chunkCoordinates1.posZ).getMaterial() == Material.water)
				{
					return Vec3.createVectorHelper((double)chunkCoordinates1.posX, (double)chunkCoordinates1.posY, (double)chunkCoordinates1.posZ);
				}
			}
		}else{
			Random random = this.mob.getRNG();
			ChunkCoordinates ChunkCoordinates = getCoords();
			for (int i = 0; i < 10; ++i)
			{
				ChunkCoordinates blockpos1 = getCoords();
				if (mob.worldObj.getBlock(blockpos1.posX, blockpos1.posY, blockpos1.posZ).getMaterial() == Material.water)
				{
					return Vec3.createVectorHelper((double)blockpos1.posX, (double)blockpos1.posY, (double)blockpos1.posZ);
				}
			}
		}

		return null;
	}

	public ChunkCoordinates getCoords(){
		int i = MathHelper.floor_double(mob.posX);
		int j = MathHelper.floor_double(mob.posY);
		int k = MathHelper.floor_double(mob.posZ);
		boolean b = mob.worldObj.getBlock(i, j + 1, k).getMaterial() == Material.water;
		return new ChunkCoordinates((int)this.mob.posX, (int)this.mob.boundingBox.minY, (int)this.mob.posZ);
	}


}