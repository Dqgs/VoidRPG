package net.voidmc.voidrpg.dungeons;

import java.util.*;

import static net.voidmc.voidrpg.dungeons.Directions.*;

public enum RoomType {
    STRAIGHT(
            new Directions[]{NORTH, SOUTH},
            new Directions[]{EAST, WEST}
    ),
    TURN(
            new Directions[]{NORTH, EAST},
            new Directions[]{NORTH, WEST},
            new Directions[]{SOUTH, EAST},
            new Directions[]{SOUTH, WEST}
    ),
    T_INTERSECTION(
            new Directions[]{NORTH, SOUTH, WEST},
            new Directions[]{NORTH, SOUTH, EAST},
            new Directions[]{NORTH, EAST, WEST},
            new Directions[]{SOUTH, EAST, WEST}
    ),
    CROSS(
            new Directions[]{NORTH, SOUTH, EAST, WEST}
    );


    Set<Integer> hashes = new HashSet<>();

    RoomType(Directions[]... directions){
        for (Directions[] direction : directions){
            hashes.add(Arrays.hashCode(direction));
        }
    }

    public static RoomType getFromDirection(Set<Directions> directions) {
        if (directions.isEmpty())
            throw new IllegalArgumentException("Direction needs atleast one");
        int hash = Arrays.hashCode(directions.stream()
                .sorted()
                .toArray(Directions[]::new));

        for (RoomType roomType : RoomType.values()){
            if (roomType.hashes.contains(hash)){
                return roomType;
            }
        }
        throw new IllegalArgumentException("Cannot find a RoomType");
    }
}
