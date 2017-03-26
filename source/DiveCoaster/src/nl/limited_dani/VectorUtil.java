package nl.limited_dani;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.entity.CommonEntity;

public class VectorUtil {

    public static Vector getRightHeadDirection(Location player) {
        Vector direction = player.getDirection().normalize();
        return new Vector(direction.getZ(), 0.0, -direction.getX() + 1.5);
    }
 
    public static Vector getLeftHeadDirection(Location player) {
        Vector direction = player.getDirection().normalize();
        return new Vector(direction.getZ() - 2.0, 0.0, -direction.getX() - 1.5);
    }
	public static Vector rotateY(Vector v, double radians){
		double x = v.getX();
		double z = v.getZ();
		Vector p = v.clone();
		
		p.setX(x*Math.cos(radians) - z*Math.sin(radians));
		p.setZ(x*Math.sin(radians) + z*Math.cos(radians));

		return p;
	}

	public static Location rotateY(Location v, double radians){
		double x = v.getX();
		double z = v.getZ();
		
		v.setX(x*Math.cos(radians) - z*Math.sin(radians));
		v.setZ(x*Math.sin(radians) + z*Math.cos(radians));
		v.setYaw((float) (v.getYaw()+Math.toDegrees(radians)));

		return v;
	}
	
	public static Vector rotateX(Vector v, double radians){
		double y = v.getY();
		double z = v.getZ();
		Vector p = v.clone();
		
		p.setY(y*Math.cos(radians) - z*Math.sin(radians));
		p.setZ(y*Math.sin(radians) + z*Math.cos(radians));

		return p;
	}
	public static Vector rotateZ(Vector v, double radians){
		double y = v.getY();
		double x = v.getX();
		Vector p = v.clone();
		
		p.setX(x*Math.cos(radians) - y*Math.sin(radians));
		p.setY(x*Math.sin(radians) + y*Math.cos(radians));

		return p;
	}
}
