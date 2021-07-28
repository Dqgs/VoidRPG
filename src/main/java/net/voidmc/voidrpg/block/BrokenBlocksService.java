package net.voidmc.voidrpg.block;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.item.ItemStack;
import net.voidmc.voidrpg.customItems.CustomBlock;
import net.voidmc.voidrpg.customItems.CustomTools;
import net.voidmc.voidrpg.customItems.ItemUtils;
import net.voidmc.voidrpg.customItems.Register;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BrokenBlocksService {


    public BrokenBlocksService(){
        events();
    }

    private static BrokenBlocksService brokenBlocksService;
    public static Map<Point, BreakingBlocks> breakingBlocks = new HashMap();
    public Set<Point> brokenBlocks = new HashSet<>();

    public void createBrokenBlock(Player player, Point position) {
        createBrokenBlock(player,position, -1);
    }

    public void createBrokenBlock(Player player, Point position, int time) {
        if (isBrokenBlock(position)) return;
        BreakingBlocks breakingBlocks;
        CustomBlock customBlock = Register.blockLocations.get(position);
        if (customBlock != null && !brokenBlocks.contains(position)) {

            CustomTools customTools = ItemUtils.getCustomTool(player.getItemInMainHand());

            if (customTools != null) {
                if ((customTools.getBreakingPower() >= customBlock.getBreakingPower() && customTools.getTypes().contains(customBlock.getToolType()))
                        || customBlock.getBreakingPower() == 0) {
                    breakingBlocks = new BreakingBlocks(player.getInstance(), position, time);
                    BrokenBlocksService.breakingBlocks.put(position, breakingBlocks);
                }
            } else if (customBlock.getBreakingPower() == 0){
                breakingBlocks = new BreakingBlocks(player.getInstance(), position, time);
                BrokenBlocksService.breakingBlocks.put(position, breakingBlocks);
            }
        }
    }

    public void removeBrokenBlock(Point location) {
        breakingBlocks.remove(location);
    }

    public BreakingBlocks getBrokenBlock(Point location) {
        return breakingBlocks.get(location);
    }

    public boolean isBrokenBlock(Point position) {
        return breakingBlocks.containsKey(position);
    }

    public static BrokenBlocksService getInstance(){
        if (brokenBlocksService == null)
            brokenBlocksService = new BrokenBlocksService();
        return brokenBlocksService;
    }

    private static void events(){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerStartDiggingEvent.class, (event) -> {
                    CustomBlock customBlock = Register.blockLocations.get(event.getBlockPosition());
                    if (customBlock == null) {
                        System.out.println("Not a custom block");
                        return;
                    }
                    BrokenBlocksService block = getInstance();
                    block.createBrokenBlock(event.getPlayer(), event.getBlockPosition(), 20 * customBlock.getTime());
                });

        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerHandAnimationEvent.class, (event) ->{
                    Player player = event.getPlayer();

                    Point block = player.getTargetBlockPosition(5);
                    BrokenBlocksService brokenBlocksService = getInstance();

                    if (!brokenBlocksService.isBrokenBlock(block)) return;

                    ItemStack itemStack = player.getItemInMainHand();

                    brokenBlocksService.getBrokenBlock(block).incrementDamage(player);
        });
    }
}
