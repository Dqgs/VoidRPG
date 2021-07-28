package net.voidmc.voidrpg.block;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket;
import net.voidmc.voidrpg.customItems.CustomBlock;
import net.voidmc.voidrpg.customItems.CustomTools;
import net.voidmc.voidrpg.customItems.ItemUtils;
import net.voidmc.voidrpg.customItems.Register;

import java.util.Date;

public class BreakingBlocks {

    private int time;
    private int oldAnimation;
    private double damage = -1;
    private Date lastDamage;
    private final Point position;
    private final Instance instance;

    public BreakingBlocks(Instance instance, Point position, int time){
        this.instance = instance;
        this.position = position;
        this.time = time;
        lastDamage = new Date();
    }

    public void incrementDamage(Player player){
        incrementDamage(player, 1);
    }

    public void incrementDamage(Player player, double multiplier){
        if (isBroken()){
            System.out.println("Block is already broken");
            return;
        }

        damage += multiplier;
        int animation = getAnimation();

        if (animation != oldAnimation){
            if (animation < 10){
                sendBreakPacket(player, position, animation);
                lastDamage = new Date();
            } else {
                breakBlock(player, position);
            }
        }
        oldAnimation = animation;
    }

    public boolean isBroken() {
        return getAnimation() >= 10;
    }

    public int getAnimation() {
        return (int) (damage / time * 11) - 1;
    }

    int blockNumbers;
    public void sendBreakPacket(Player player, Point blockLocation, int animation) {
        BlockBreakAnimationPacket blockBreakAnimationPacket = new BlockBreakAnimationPacket(blockNumbers, blockLocation, (byte) animation);
        player.sendPacketToViewersAndSelf(blockBreakAnimationPacket);
        player.sendMessage(String.valueOf(blockBreakAnimationPacket.destroyStage));
        blockNumbers += 1;
    }

    public void breakBlock(Player player, Point blockLocation) {
        if (player == null) return;
        destroyBlockObject(player, blockLocation);
    }

    public void destroyBlockObject(Player player, Point position) {
        System.out.println("Destroying block object");
        sendBreakPacket(player, position, -1);
        CustomBlock customBlock = Register.blockLocations.get(position);
        if (customBlock != null) {
            customBlock.onBlockBreak(player, position);
            BrokenBlocksService.getInstance().removeBrokenBlock(position);
            System.out.println("block broken");
        } else System.out.println("Custom block == null");
        CustomTools skyItem = ItemUtils.getCustomTool(player.getInventory().getItemInMainHand());
        if (skyItem != null)
            skyItem.onBlockBreak(player, position);
    }
}
