package nl.limited_dani;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.limited_dani.karren.Kar11;
import nl.limited_dani.karren.Speler;

public class Main extends JavaPlugin implements Listener{

	public static Main r;
	boolean bar1already = false;
	boolean speler = false;
    public static ArrayList<Minecart> carts = new ArrayList<Minecart>();
    public static ArrayList<Player> canexit = new ArrayList<Player>();
	public void onEnable() {
		r = this;
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		TrainCartsUtil.spawn((new Location(Bukkit.getWorld("world"), 43, 71, 298)), EntityType.MINECART);
		Speler.rideLocation = new Location(Bukkit.getWorld("world"), 43, 71, 298);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!bar1already) {
					bar1already = true;
					Speler.start();
				} else {
					if(TrainCartsUtil.minecarts.get(0).isMovingVertically()) {
						Location loc = TrainCartsUtil.minecarts.get(0).getLocation();
						
						Kar11 kar11 = new Kar11(TrainCartsUtil.minecarts.get(0));
						Kar11 kar12 = new Kar11(TrainCartsUtil.minecarts.get(0));
						Kar11 kar13 = new Kar11(TrainCartsUtil.minecarts.get(0));
						
						Speler.rideLocation = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), 90f);
					} else {
						Speler.rideLocation = TrainCartsUtil.minecarts.get(0).getLocation();
					}
				}
			}
		}.runTaskTimer(this, 0, 1);	
	}
}
