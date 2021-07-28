package net.voidmc.voidrpg.dungeons;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoomData {

    Set<Directions> directions = ConcurrentHashMap.newKeySet();

    public RoomData(Directions... directions){
        Collections.addAll(this.directions, directions);
    }

    public Set<Directions> getDirections(){
        return directions;
    }
}
