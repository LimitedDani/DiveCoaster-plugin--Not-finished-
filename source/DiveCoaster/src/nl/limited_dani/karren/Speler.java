package nl.limited_dani.karren;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.controller.EntityController;
import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.bergerkiller.bukkit.common.sf.cglib.core.Block;
import com.bergerkiller.bukkit.tc.properties.TrainProperties;

import nl.limited_dani.Main;
import nl.limited_dani.TrainCartsUtil;
import nl.limited_dani.VectorUtil;

public class Speler {

	static int runnableID;
	static double height = 14;
	static double radius = 1.5;
	public static Location rideLocation;
	public static Location rideLocation1;
	public static Location rideLocation2;
	public static int minecartCount = 2;
	public static Minecart[] minecarts;
	static double minecartRotation;
	static double minecartIncrementDegrees;
	static double phi = 2*Math.PI / minecartCount;
	
	public static void spawn(){
		minecarts = new Minecart[minecartCount];
		World world = rideLocation.getWorld();
		Vector offset = new Vector(radius, 0, 0);

		
		for (int i = 0; i < minecartCount; i++) {
			Location mLocation = rideLocation.clone();
			Vector mOffset = VectorUtil.rotateY(offset.clone(), phi * i);
			mLocation.add(mOffset);
			mLocation.setYaw((float) (+90+(minecartIncrementDegrees)*i));
			//m.teleport(mLocation);
			//minecartOffsets[i] = new Location(mLocation.getWorld(), mOffset.getX(), mOffset.getY(), mOffset.getZ(), mLocation.getYaw(), 0);
			Minecart m = world.spawn(mLocation, Minecart.class);
			minecarts[i] = m;
			m.setCustomName("Kar" + i);
			Main.carts.add(m);
			CommonEntity<?> entity = CommonEntity.get(m);
			entity.setController(new EntityController<CommonEntity<?>>() {
				public boolean onEntityCollision(Entity entity) {
					return false;
				}

				@SuppressWarnings("unused")
				public boolean onBlockCollision(Block block, BlockFace hitFace) {
					return false;
				}
			});//entity.setPosition(mLocation.toVector().getX(), mLocation.toVector().getY(), mLocation.toVector().getZ());
		}
	}
	public static void animate(double swing, double spin){
		Vector rideVector = rideLocation.toVector();
		
		int currentHeight = 0;
		for (int i = 0; i < minecartCount; i++) {
			Minecart m = minecarts[i];
			Vector mVector = new Vector(radius, 0, 0);
			mVector = VectorUtil.rotateY(mVector, Math.toRadians(spin) + phi*i);
			mVector.setY(mVector.getY() - currentHeight);
			mVector = VectorUtil.rotateZ(mVector, Math.toRadians(swing));
			mVector.setY(mVector.getY() + currentHeight);
			mVector.add(rideVector);
			CommonEntity<?> cm = CommonEntity.get(m);
			cm.setLocation(mVector.getX(), (rideLocation.getY() + 0.5), mVector.getZ(), rideLocation.getYaw(), rideLocation.getPitch());
			cm.setRotation(rideLocation.getYaw(), rideLocation.getPitch());
		}
	}
	public static void start() {
		spawn();
		new BukkitRunnable() {
			int spin = 0;
			int swing = 0;
		    
			int stage = 0;
			
			@Override
			public void run() {
				runnableID = this.getTaskId();
				swing %= 360;
				if(swing == 0 || swing == 180){
					stage += 1;
					if(stage == 0){
						Bukkit.getScheduler().cancelTask(this.getTaskId());
						return;
					}
				}
				double pendulum = Math.sin(Math.toRadians(swing)) * 90;
				animate(pendulum, spin);
				spin = (int) (rideLocation.getYaw() + 90);
			}
		}.runTaskTimer(Main.r, 0, 1);	
	}
}
