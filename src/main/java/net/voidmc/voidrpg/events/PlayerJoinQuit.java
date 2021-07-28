package net.voidmc.voidrpg.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.voidmc.voidrpg.VoidRPG;

public class PlayerJoinQuit {

    public PlayerJoinQuit(InstanceContainer instance){
        login(instance);
        spawn(instance);
        disconnect(instance);
    }

    private void login(InstanceContainer instance){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerLoginEvent.class, (event) -> {
                    Player player = event.getPlayer();

                    event.setSpawningInstance(instance);
                    VoidRPG.getDataManager().add(player);

                });
    }
    private void spawn(InstanceContainer instance){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerSpawnEvent.class, (event) -> {
                    Player player = event.getPlayer();

                    player.teleport(Pos.fromPoint(new Pos(0, 124, 0)));
                    player.setPermissionLevel(4);
                    player.setAllowFlying(true);
                    player.setGameMode(GameMode.SURVIVAL);
                    Potion potion = new Potion(PotionEffect.MINING_FATIGUE, (byte) -1, 100000 * 20, false, false, false);
                    player.addEffect(potion);
                });
    }

    private void disconnect(InstanceContainer instance){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerDisconnectEvent.class, (event) -> {
                    Player player = event.getPlayer();

                    VoidRPG.getDataPlayerSql().saveInventory(player.getUuid(), player.getInventory());
                });
    }
}
