package net.voidmc.voidrpg.customItems;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.item.ItemStack;
import net.voidmc.voidrpg.sql.BlockLocations;

public class CustomItemEvents {

    public CustomItemEvents(){
        MinecraftServer.getGlobalEventHandler()
                .addListener(PlayerBlockPlaceEvent.class, (event) -> {
                    Player player = event.getPlayer();
                    ItemStack mainHand = player.getItemInMainHand();
                    ItemStack offHand = player.getItemInOffHand();
                    System.out.println("Player Block Place event");
                    if (ItemUtils.isCustomItem(mainHand) || ItemUtils.isCustomItem(offHand)) {
                        CustomBlock skyBlock = null;
                        System.out.println("Player Block Place event 2");
                        if (ItemUtils.getCustomBlock(mainHand) != null) {
                            System.out.println("Player Block Place event 3");
                            skyBlock = ItemUtils.getCustomBlock(mainHand);
                        } 
                        if (ItemUtils.getCustomBlock(offHand) != null) {
                            skyBlock = ItemUtils.getCustomBlock(offHand);
                            System.out.println("Player Block Place event 4");
                        } 
                        if (skyBlock == null){
                            System.out.println("Return");
                            return;
                        }
                        if (skyBlock != null) {
                            System.out.println("Placing Block");
                            BlockLocations.saveBlock(event.getBlockPosition(), skyBlock);
                            Register.blockLocations.put(event.getBlockPosition(), skyBlock);
                            skyBlock.onBlockPlace(event.getPlayer(), event.getBlockPosition());
                        } else System.out.println("skyblock null");
                    } else System.out.println("Not a custom Item");
                });
    }
}
