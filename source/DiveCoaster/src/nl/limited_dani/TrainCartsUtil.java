package nl.limited_dani;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.bergerkiller.bukkit.common.utils.FaceUtil;
import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import com.bergerkiller.bukkit.tc.events.GroupCreateEvent;
import com.bergerkiller.bukkit.tc.events.MemberSpawnEvent;

public class TrainCartsUtil {
	public static ArrayList<CommonEntity<?>> minecarts = new ArrayList<CommonEntity<?>>();
	
    public static MinecartMember<?> spawn(Location at, EntityType type) {
        CommonEntity<?> entity = CommonEntity.create(type);
        MinecartMember<?> controller = MinecartMemberStore.createController(entity);
        if (controller == null) {
            throw new IllegalArgumentException("No suitable MinecartMember type for " + type);
        }
        entity.setController(controller);
        entity.spawn(at, MinecartMemberStore.createNetworkController(entity));
        controller.invalidateDirection();
        controller.updateDirection(FaceUtil.yawToFace(at.getYaw()));
        minecarts.add(entity);
        return MemberSpawnEvent.call(controller).getMember();
    }
}
