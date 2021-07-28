package net.voidmc.voidrpg.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.voidmc.voidrpg.customItems.CustomMaterial;
import net.voidmc.voidrpg.customItems.ItemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandAlias("Give")
@Description("Lets you give your self items")

//@CommandPermission("void.Admin")
public class GiveCMD extends BaseCommand {




    @Default
    public void give(Player player){
        player.sendMessage("do /give (block/material/item)");
    }


    @Subcommand("material")
    public void material(Player player, int id) {
        CustomMaterial customMaterial = ItemUtils.getMaterialFromID(id);
        System.out.println(customMaterial.getName());
        player.getInventory().addItemStack(ItemUtils.makeMaterial(ItemUtils.getMaterialFromID(id)));
    }

    @Subcommand("material")
    public void material(Player player, int id, int amount) {
        CustomMaterial customMaterial = ItemUtils.getMaterialFromID(id);
        System.out.println(customMaterial);
        player.getInventory().addItemStack(ItemUtils.makeMaterial(ItemUtils.getMaterialFromID(id), amount));
    }

    @Subcommand("material")
    public void material(Player player, String name) {
        player.getInventory().addItemStack(ItemUtils.makeMaterial(ItemUtils.getMaterialFromName(name)));
    }

    @Subcommand("material")
    public void material(Player player, String name, int amount) {
        player.getInventory().addItemStack(ItemUtils.makeMaterial(ItemUtils.getMaterialFromName(name), amount));
    }

    @Subcommand("block")
    public void block(Player player, int id) {
        player.getInventory().addItemStack(ItemUtils.makeBlock(ItemUtils.getBlockFromID(id)));
    }

    @Subcommand("block")
    public void block(Player player, int id, int amount) {
        player.getInventory().addItemStack(ItemUtils.makeBlock(ItemUtils.getBlockFromID(id), amount));
    }

//    @Subcommand("block")
//    public void block(Player player, String name) {
//        player.getInventory().addItemStack(ItemUtils.makeBlock(ItemUtils.getBlockFromName(name)));
//    }
//
//    @Subcommand("block")
//    public void block(Player player, String name, int amount) {
//        player.getInventory().addItemStack(ItemUtils.makeBlock(ItemUtils.getBlockFromName(name), amount));
//    }

    @Subcommand("item")
    public void item(Player player, int id) {
        player.getInventory().addItemStack(ItemUtils.makeItem(ItemUtils.getItemFromID(id)));
    }

    @Subcommand("item")
    public void item(Player player, int id, int amount) {
        player.getInventory().addItemStack(ItemUtils.makeItem(ItemUtils.getItemFromID(id), amount));
    }

//    @Subcommand("item")
//    public void item(Player player, String name) {
//        player.getInventory().addItemStack(ItemUtils.makeItem(ItemUtils.getItemFromName(name)));
//    }
//
//    @Subcommand("item")
//    public void item(Player player, String name, int amount) {
//        player.getInventory().addItemStack(ItemUtils.makeItem(ItemUtils.getItemFromName(name), amount));
//    }

}
