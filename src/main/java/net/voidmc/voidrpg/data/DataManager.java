package net.voidmc.voidrpg.data;



import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class DataManager {

    private final Set<DataPlayer> dataSet = new HashSet<>();

    public DataManager() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(this::add);
    }

    public DataPlayer getDataPlayer(Player player) {
        return dataSet.stream().filter(dataPlayer -> dataPlayer.getPlayer() == player).findFirst().orElse(null);
    }

    public void add(Player player) {
        dataSet.add(new DataPlayer(player));
    }

    public void remove(Player player) {
        dataSet.removeIf(dataPlayer -> dataPlayer.getPlayer() == player);
    }
}
