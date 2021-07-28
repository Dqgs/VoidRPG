package net.voidmc.voidrpg.dungeons;

import de.articdive.jnoise.JNoise;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.chunk.ChunkUtils;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Dungeon implements ChunkGenerator {

    ConcurrentHashMap<Long, RoomData> rooms = new ConcurrentHashMap<>();
    HashMap<Long, ChunkData> blockPosition = new HashMap<>();
    HashMap<Long, RoomType> roomTypes = new HashMap<>();

    int roomAmounts;

    private static final JNoise noise = JNoise.newBuilder().fastSimplex().setFrequency(1000.0).build();

    double nextRoomChance = .8, branchChance = -.9;
    int x = 0, z = 0;
    double noiseValue = 0;

    public Dungeon() {

        generateRoom(x, z, nextRoomChance, branchChance);

        for (Map.Entry<Long, RoomData> dataEntry : rooms.entrySet()) {
            if (dataEntry.getValue().getDirections().size() == 1) {
                new Dungeon();
            }

            int x = ChunkUtils.getChunkCoordX(dataEntry.getKey());
            int z = ChunkUtils.getChunkCoordZ(dataEntry.getKey());

            for (Directions directions : dataEntry.getValue().getDirections()) {
                System.out.println("Directions " + directions);
                int newX = x + directions.getXOffSet();

                int newZ = z + directions.getZOffSet();

                long chunkIndex = ChunkUtils.getChunkIndex(newX, newZ);

                if (!rooms.containsKey(chunkIndex)) {
                    System.out.println("Generating a new Room");
                    generateRoom(newX, newZ);
                }
            }

            RoomType roomType = RoomType.getFromDirection(dataEntry.getValue().getDirections());
            roomTypes.put(dataEntry.getKey(), roomType);

            Block block = switch (roomType) {
                case STRAIGHT -> Block.SLIME_BLOCK;
                case T_INTERSECTION -> Block.GREEN_WOOL;
                case TURN -> Block.DIAMOND_BLOCK;
                case CROSS -> Block.HAY_BLOCK;
            };

            setBlock(x * 16, z * 16, block);

        }
    }

    public void generateRoom(int x, int z, double nextRoomChance, double branchChance) {
        noiseValue = noise.getNoise(x, z);
        Directions direction = getNextDirection();

        roomAmounts += 1;

        int newX = ChunkUtils.getChunkCoordX(x + direction.getXOffSet());
        int newZ = ChunkUtils.getChunkCoordZ(z + direction.getZOffSet());

        long oldChunkIndex = ChunkUtils.getChunkIndex(x, z);
        long newChunkIndex = ChunkUtils.getChunkIndex(newX, newZ);

        rooms.computeIfAbsent(oldChunkIndex, (index) -> new RoomData());
        rooms.computeIfAbsent(newChunkIndex, (index) -> new RoomData());

        RoomData oldRoomData = rooms.get(oldChunkIndex);
        RoomData newRoomData = rooms.get(newChunkIndex);

        oldRoomData.getDirections().add(direction);
        newRoomData.getDirections().add(direction.getReverseDirection());

        if (noiseValue < branchChance) {
            generateRoom(newX, newZ, nextRoomChance, branchChance);
        }

        if (noiseValue < nextRoomChance) {
            generateRoom(newX, newZ, nextRoomChance, branchChance);
        }
    }

    public void generateRoom(int x, int z) {
        noiseValue = noise.getNoise(x, z);
        Directions direction = getNextDirection();

        roomAmounts += 1;

        int newX = ChunkUtils.getChunkCoordX(x) + direction.getXOffSet();
        int newZ = ChunkUtils.getChunkCoordZ(z) + direction.getZOffSet();

        long oldChunkIndex = ChunkUtils.getChunkIndex(x, z);
        long newChunkIndex = ChunkUtils.getChunkIndex(newX, newZ);

        rooms.computeIfAbsent(oldChunkIndex, (index) -> new RoomData());
        rooms.computeIfAbsent(newChunkIndex, (index) -> new RoomData());

        RoomData oldRoomData = rooms.get(oldChunkIndex);
        RoomData newRoomData = rooms.get(newChunkIndex);

        oldRoomData.getDirections().add(direction);
        newRoomData.getDirections().add(direction.getReverseDirection());
    }

    private long getChunkIndex(int x,int z){
        int chunkX = ChunkUtils.getChunkCoordinate(x);
        int chunkZ = ChunkUtils.getChunkCoordinate(z);
        return ChunkUtils.getChunkIndex(chunkX, chunkZ);
    }

    private void setBlock(int x, int z, Block block){
        long chunk = getChunkIndex(x, z);

        blockPosition.computeIfAbsent(chunk, (index) -> new ChunkData());
        ChunkData data = blockPosition.get(chunk);
        data.blockIDs.add((short) block.id());
        data.arrayX.add(x);
        data.arrayY.add(5);
        data.arrayZ.add(z);
    }

    public Directions getNextDirection(){
        return Directions.values()[new Random().nextInt(Directions.values().length)];
    }

    @Override
    public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
        long chunkIndex = ChunkUtils.getChunkIndex(chunkX, chunkZ);
        ChunkData chunkData = blockPosition.get(chunkIndex);
        if (chunkData == null)
            return;
        for (int i = 0; i < chunkData.blockIDs.size(); i++){
            short blockId = chunkData.blockIDs.get(i);
            int x = chunkData.arrayX.get(i);
            int y = chunkData.arrayY.get(i);
            int z = chunkData.arrayZ.get(i);

            x-= chunkX * 16;
            z-= chunkZ * 16;
            batch.setBlock(x, y, z, Block.fromBlockId(blockId));
        }
    }

    @Override
    public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {
        Arrays.fill(biomes, Biome.PLAINS);
    }

    @Override
    public @Nullable List<ChunkPopulator> getPopulators() {
        return null;
    }
}

