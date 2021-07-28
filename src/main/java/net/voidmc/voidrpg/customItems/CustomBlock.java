package net.voidmc.voidrpg.customItems;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;

public abstract class CustomBlock {

    private final Material material;
    private final String name;
    private final String description;
    private final int time;
    private final int id;
    private final int breakingPower;
    private final Rarity rarity;
    private Block block;
    private final ToolType toolType;

    public CustomBlock(Material material, String name, String description, int time, int id, int breakingPower, Rarity rarity, ToolType toolType){
        this.material = material;
        this.name = name;
        this.description = description;
        this.time = time;
        this.id = id;
        this.breakingPower = breakingPower;
        this.rarity = rarity;
        this.toolType = toolType;
    }


    public abstract void onBlockBreak(Player player, Point point);

    public abstract void onBlockPlace(Player player, Point point);

    public abstract void onInteract(PlayerBlockInteractEvent event);

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

    public int getBreakingPower(){
        return breakingPower;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block){
        this.block = block;
    }

    public int getTime(){
        return time;
    }

    public ToolType getToolType() {
        return toolType;
    }

}
