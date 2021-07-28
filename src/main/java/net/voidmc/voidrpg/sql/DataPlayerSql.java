package net.voidmc.voidrpg.sql;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import net.voidmc.voidrpg.VoidRPG;
import net.voidmc.voidrpg.customItems.ItemUtils;
import org.jglrxavpok.hephaistos.nbt.NBTException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class DataPlayerSql {

    public void createPlayer(Player player){
        try {
            UUID uuid = player.getUuid();
            if (!VoidRPG.getData().existsStats(uuid)){
                PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("INSERT IGNORE INTO stats " +
                        "(NAME,UUID) VALUES (?,?)");
                ps.setString(1, player.getName().toString());
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        giveInventory(player);
    }

    public void saveInventory(UUID uuid, PlayerInventory inventory){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("UPDATE stats SET " +
                    "Inventory=? WHERE UUID=?");
            ps.setString(1, ItemSerialization.serilie(inventory.getItemStacks()));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void giveInventory(Player player){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT Inventory FROM stats WHERE UUID=?");
            ps.setString(1, player.getUuid().toString());
            ResultSet rs = ps.executeQuery();
            String XP;
            if (rs.next()) {
                XP = rs.getString("Inventory");
                ItemStack[] itemList = ItemSerialization.deserilize(XP);
                PlayerInventory inventory = player.getInventory();

                inventory.clear();

                if (itemList != null){
                    for (int i = 0; i < 41; i++) {
                        ItemStack itemStack = itemList[i];
                        if (itemStack != null) {
                            int amount = itemStack.getAmount();

                            int itemID = 0;
                            int materialID = 0;
                            int blockID = 0;

                            if (itemStack.hasTag(Tag.Integer("CustomItemID")))
                                itemID = itemStack.getTag(Tag.Integer("CustomItemID"));

                            if (itemStack.hasTag(Tag.Integer("CustomMaterialID")))
                                materialID = itemStack.getTag(Tag.Integer("CustomMaterialID"));

                            if (itemStack.hasTag(Tag.Integer("CustomBlockID")))
                                blockID = itemStack.getTag(Tag.Integer("CustomBlockID"));


                            if (itemID != 0) {
                                itemStack = ItemUtils.makeItem(ItemUtils.getItemFromID(itemID), amount);
                            } else if (materialID != 0) {
                                itemStack = ItemUtils.makeMaterial(ItemUtils.getMaterialFromID(materialID), amount);
                            } else if (blockID != 0) {
                                itemStack = ItemUtils.makeBlock(ItemUtils.getBlockFromID(blockID), amount);
                            }

                            System.out.println(itemStack.getMaterial());
                            inventory.setItemStack(i, itemStack);
                        }
                    }
                }
            }
        } catch (SQLException | IOException | NBTException e) {
            e.printStackTrace();
        }
    }

    public void setPlayTime(UUID uuid, int xp){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("UPDATE stats SET PlayTime=? WHERE UUID=?");
            ps.setInt(1, xp);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public long getPlayerTime(UUID uuid){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT PlayTime FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            long XP;
            if (rs.next()){
                XP = rs.getInt("PlayTime");
                return XP;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void setBalance(UUID uuid, int xp){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("UPDATE stats SET Balance=? WHERE UUID=?");
            ps.setInt(1, xp);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public long getBalance(UUID uuid){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT Balance FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            long XP;
            if (rs.next()){
                XP = rs.getInt("Balance");
                return XP;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
