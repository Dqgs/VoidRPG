package net.voidmc.voidrpg.customItems;

import lombok.Getter;
import net.minestom.server.item.Material;

public class CustomMaterial {

    private final Material material;
    private final String name;
    private final String description;
    private final int id;
    private final Rarity rarity;

    public CustomMaterial(Material material, String name, String description, int id, Rarity rarity){
        this.material = material;
        this.name = name;
        this.description = description;
        this.id = id;
        this.rarity = rarity;
    }


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
