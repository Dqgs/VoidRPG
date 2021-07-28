package net.voidmc.voidrpg.customItems;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CustomTools {

    private final Material material;
    private final String name;
    private final String description;

    private final int id;
    private final Rarity rarity;
    private final int breakingPower;
    private final ArrayList<ToolType> types;

    public CustomTools(Material material, String name, String description, int id, Rarity rarity, int breakingPower, ToolType... type){
        this.material = material;
        this.name = name;
        this.description = description;
        this.id = id;
        this.rarity = rarity;
        this.breakingPower = breakingPower;
        this.types = new ArrayList(Arrays.asList(type));
    }


    public abstract void onBlockPlace(PlayerBlockPlaceEvent event);
    public abstract void onBlockBreak(Player player, Point point);
    public abstract void onBlockInteract(PlayerBlockInteractEvent event);


    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ArrayList<ToolType> getTypes() {
        return types;
    }

    public int getBreakingPower() {
        return breakingPower;
    }
}
