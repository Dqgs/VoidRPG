package net.voidmc.voidrpg.dungeons;

public enum Directions {

    NORTH(0, 1),
    SOUTH(1, 0),
    EAST(-1, 0),
    WEST(0, -1);

    int xOffSet, zOffSet;


    Directions(int xOffSet, int zOffSet) {
        this.xOffSet = xOffSet;
        this.zOffSet = zOffSet;
    }

    public Directions getReverseDirection() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public int getXOffSet() {
        return xOffSet;
    }

    public int getZOffSet() {
        return zOffSet;
    }
}
