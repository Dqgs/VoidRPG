package net.voidmc.voidrpg.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.InstanceContainer;

public class SaveChunks {

    public SaveChunks(InstanceContainer instance){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerBlockPlaceEvent.class, (event) -> {

                    Chunk chunk =
                            instance.getChunk(event.getBlockPosition().blockX() / 16, event.getBlockPosition().blockZ() / 16);
                    instance.saveChunkToStorage(chunk);
                });

        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerBlockBreakEvent.class, (event) -> {

                    Chunk chunk =
                            instance.getChunk(event.getBlockPosition().blockX() / 16, event.getBlockPosition().blockZ() / 16);
                    instance.saveChunkToStorage(chunk);
                });

    }
}
