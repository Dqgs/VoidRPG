package net.voidmc.voidrpg.customItems.blocks;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.timer.SchedulerManager;
import net.voidmc.voidrpg.block.BrokenBlocksService;
import net.voidmc.voidrpg.customItems.*;
import net.voidmc.voidrpg.sql.BlockLocations;

import java.util.concurrent.TimeUnit;

public class WoodNode extends CustomBlock {


    public WoodNode(Material material, String name, String description, int time, int id, int breakingPower, Rarity rarity, ToolType toolType) {
        super(material, name, description, time, id, breakingPower, rarity, toolType);
    }

    @Override
    public void onBlockBreak(Player player, Point point) {
        if (player.isSneaking() && player.hasPermission("VoidSky.Staff")) {
            BlockLocations.removeBlock(point);
            player.getInventory().addItemStack(ItemUtils.makeBlock(this));
        } else {
            ItemStack itemStack = player.getItemInMainHand();
            CustomTools customTools = ItemUtils.getCustomTool(itemStack);
            player.getInventory().addItemStack(ItemUtils.makeMaterial(ItemUtils.getMaterialFromID(2)));
            SchedulerManager manager = MinecraftServer.getSchedulerManager();

            player.getInstance().setBlock(point.blockX(), point.blockY(), point.blockZ(), Block.BEDROCK);
            System.out.println("Setting to bedrock");
            BrokenBlocksService.getInstance().brokenBlocks.add(point);
            manager.buildTask(new Runnable() {
                @Override
                public void run() {
                    player.getInstance().setBlock(point.blockX(), point.blockY(), point.blockZ(), Block.OAK_LOG);
                    BrokenBlocksService.getInstance().brokenBlocks.remove(point);
                }
            }).delay(5, TimeUnit.SECONDS.toChronoUnit());
        }
    }

    @Override
    public void onBlockPlace(Player player, Point point) {

    }

    @Override
    public void onInteract(PlayerBlockInteractEvent event) {

    }
}
