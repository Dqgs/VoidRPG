package net.voidmc.voidrpg.customItems;

import lombok.Getter;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.item.Material;
import net.voidmc.voidrpg.customItems.Rarity;

public abstract class CustomItem {

    private final Material material;
    private final String name;
    private final String description;

    private final int id;
    private final Rarity rarity;

    public CustomItem(Material material, String name, String description, int id, Rarity rarity){
        this.material = material;
        this.name = name;
        this.description = description;
        this.id = id;
        this.rarity = rarity;
    }


    public abstract void onBlockPlace(PlayerBlockPlaceEvent event);
    public abstract void onBlockBreak(PlayerBlockBreakEvent event);
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

}
