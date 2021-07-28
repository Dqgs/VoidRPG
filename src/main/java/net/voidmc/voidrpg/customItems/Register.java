package net.voidmc.voidrpg.customItems;

import net.minestom.server.coordinate.Point;
import net.minestom.server.item.Material;
import net.voidmc.voidrpg.customItems.blocks.WoodNode;

import java.util.*;

public class Register {

    public static HashMap<Point, CustomBlock> blockLocations = new HashMap<>();


    public static void registerAll(){
        registerItems();
        registerMaterials();
        registerBlocks();
    }

    public static void registerItems(){
//        putItem(new WoodPickaxe(Material.WOODEN_PICKAXE, "Wood Pickaxe", "", 1, Rarity.COMMON));
//        putItem(new WoodAxe(Material.WOODEN_AXE, "Wood Axe", "", 2, Rarity.COMMON));
//        putItem(new StonePickaxe(Material.STONE_PICKAXE, "Stone Pickaxe", "", 3, Rarity.COMMON));
//        putItem(new StonePickaxe(Material.STONE_AXE, "Stone Axe", "", 4, Rarity.COMMON));
//        putItem(new StonePickaxe(Material.IRON_PICKAXE, "Iron Pickaxe", "", 5, Rarity.COMMON));
//        putItem(new StonePickaxe(Material.IRON_AXE, "Iron Axe", "", 6, Rarity.COMMON));
    }

    public static void registerMaterials(){
        putMaterial(new CustomMaterial(Material.STONE_BUTTON, "Cobble Scrap", "", 1, Rarity.COMMON));
        putMaterial(new CustomMaterial(Material.OAK_BUTTON, "Wood Scrap", "", 2, Rarity.COMMON));
        putMaterial(new CustomMaterial(Material.OAK_PLANKS, "Wood Plank", "", 3, Rarity.COMMON));
        putMaterial(new CustomMaterial(Material.COBBLESTONE, "CobbleStone", "", 4, Rarity.COMMON));
        putMaterial(new CustomMaterial(Material.GRAY_DYE, "Iron Scrap", "", 5, Rarity.COMMON));
        putMaterial(new CustomMaterial(Material.IRON_INGOT, "Iron Ingot", "", 6, Rarity.COMMON));
    }

    public static void registerBlocks(){
//        putBlock(new CobbleNode(Material.COBBLESTONE, "Cobble Node",  "Hint: Place down!", 1, Rarity.COMMON));
        putBlock(new WoodNode(Material.OAK_LOG, "Wood Node", "Hint: Place Down!", 3, 2, 0, Rarity.COMMON, ToolType.AXE));
//        putBlock(new IronNode(Material.IRON_ORE, "Iron Node", "Hint: Place Down!", 3, Rarity.COMMON));
    }

    public static void clearAllRegisters(){
        clearItemRegister();
        clearMaterialRegister();
        clearBlockRegister();
    }

    public static void clearItemRegister(){
        ItemUtils.ItemsId.clear();
        ItemUtils.ItemsName.clear();
    }

    public static void clearMaterialRegister(){
        ItemUtils.MaterialId.clear();
        ItemUtils.MaterialName.clear();
    }

    public static void clearBlockRegister(){
        ItemUtils.BlockName.clear();
        ItemUtils.BlockId.clear();
    }

    private static void putItem(CustomItem skyItem){
        ItemUtils.ItemsId.put(skyItem.getId(), skyItem);
        ItemUtils.ItemsName.put(skyItem.getName(), skyItem);
    }
    private static void putMaterial(CustomMaterial material){
        ItemUtils.MaterialId.put(material.getId(), material);
        ItemUtils.MaterialName.put(material.getName(), material);
    }

    private static void putBlock(CustomBlock block){
        ItemUtils.BlockId.put(block.getId(), block);
        ItemUtils.BlockName.put(block.getName(), block);
    }
}
