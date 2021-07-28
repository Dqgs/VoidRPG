package net.voidmc.voidrpg.customItems;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemUtils {

    public static HashMap<Integer, CustomItem> ItemsId = new HashMap<>();
    public static HashMap<String, CustomItem> ItemsName = new HashMap<>();

    public static HashMap<Integer, CustomTools> ToolId = new HashMap<>();
    public static HashMap<String, CustomTools> ToolName = new HashMap<>();

    public static HashMap<Integer, CustomMaterial> MaterialId = new HashMap<>();
    public static HashMap<String, CustomMaterial> MaterialName = new HashMap<>();

    public static HashMap<Integer, CustomBlock> BlockId = new HashMap<>();
    public static HashMap<String, CustomBlock> BlockName = new HashMap<>();


    public static ItemStack makeItem(CustomItem item){
        return makeItem(item, 1);
    }

    public static ItemStack makeMaterial(CustomMaterial material){
        return makeMaterial(material, 1);
    }

    public static ItemStack makeBlock(CustomBlock block){
        return makeBlock(block, 1);
    }

    public static ItemStack makeItem(CustomItem customItem, int amount){


        List<Component> lorea = new ArrayList<>();
        lorea.add(Component.text(customItem.getDescription()));

        Tag<Integer> stringTag = Tag.Integer("CustomItemID");

        return ItemStack.builder(customItem.getMaterial())
                .displayName(Component.text(customItem.getName(), customItem.getRarity().getColor()))
                .lore(lorea)
                .amount(amount)
                .meta(itemMetaBuilder ->
                        itemMetaBuilder.set(stringTag, customItem.getId()))
                .build();
    }

    public static ItemStack makeMaterial(CustomMaterial customMaterial, int amount){

        System.out.println("Material: " + customMaterial.getName());

        List<Component> lorea = new ArrayList<>();
        lorea.add(Component.text(customMaterial.getDescription()));


        Tag<Integer> stringTag = Tag.Integer("CustomMaterialID");

        return ItemStack.builder(customMaterial.getMaterial())
                .displayName(Component.text(customMaterial.getName(), customMaterial.getRarity().getColor()))
                .lore(lorea)
                .amount(amount)
                .meta(itemMetaBuilder ->
                        itemMetaBuilder.set(stringTag, customMaterial.getId()))
                .build();
    }

    public static ItemStack makeBlock(CustomBlock customBlock, int amount){

        List<Component> lorea = new ArrayList<>();
        lorea.add(Component.text(customBlock.getDescription()));

        Tag<Integer> stringTag = Tag.Integer("CustomBlockID");

        return ItemStack.builder(customBlock.getMaterial())
                .displayName(Component.text(customBlock.getName(), customBlock.getRarity().getColor()))
                .lore(lorea)
                .amount(amount)
                .meta(itemMetaBuilder ->
                        itemMetaBuilder.set(stringTag, customBlock.getId()))
                .build();
    }

    public static ItemStack makeItemById(int id, int amount){
        return makeItem(getItemFromID(id), amount);
    }
    public static ItemStack makeMaterialById(int id, int amount){
        return makeMaterial(getMaterialFromID(id), amount);
    }

    public static ItemStack makeBlockById(int id, int amount){
        return makeBlock(getBlockFromID(id), amount);
    }

    public static ItemStack makeItemById(int id){
        return makeItem(getItemFromID(id));
    }
    public static ItemStack makeMaterialById(int id){
        return makeMaterial(getMaterialFromID(id));
    }

    public static ItemStack makeBlockById(int id){
        return makeBlock(getBlockFromID(id));
    }

    public static CustomItem getItemFromID(int id){
        return ItemsId.get(id);
    }

    public static CustomItem getItemFromName(String name){
        return ItemsName.get(name);
    }

    public static CustomTools getToolFromID(int id){
        return ToolId.get(id);
    }

    public static CustomTools getToolFromName(String name){
        return ToolName.get(name);
    }

    public static CustomMaterial getMaterialFromID(int id) {
        return MaterialId.get(id);
    }

    public static CustomMaterial getMaterialFromName(String name){
        return MaterialName.get(name);
    }

    public static CustomBlock getBlockFromID(int id){
        return BlockId.get(id);
    }

    public static CustomBlock getBlockFromName(String name){
        return BlockName.get(name);
    }

    public static Integer getID(ItemStack itemStack, String key){
        @NotNull Tag<Integer> tag = Tag.Integer(key);
        Integer nbtCompound = itemStack.getTag(tag);
        //System.out.println(nbtCompound.toString());
        return itemStack.getTag(tag);
    }

    public static boolean isCustomItem(ItemStack itemStack){
        if (getID(itemStack, "CustomItemID") != null)
            return getID(itemStack, "CustomItemID") != 0;
        else if (getID(itemStack, "CustomMaterialID") != null)
            return getID(itemStack, "CustomMaterialID") != 0;
        else if (getID(itemStack, "CustomBlockID") != null)
            return getID(itemStack, "CustomBlockID") != 0;
        else return false;
    }

    public static CustomItem getCustomItem(ItemStack itemStack){
        if (getID(itemStack, "CustomItemID") == null)
            return null;
        return getItemFromID(getID(itemStack, "CustomItemID"));
    }

    public static CustomTools getCustomTool(ItemStack itemStack){
        if (getID(itemStack, "CustomToolID") == null)
            return null;
        return getToolFromID(getID(itemStack, "CustomToolID"));
    }

    public static CustomMaterial getCustomMaterial(ItemStack itemStack){
        if (getID(itemStack, "CustomMaterialID") == null)
            return null;
        return getMaterialFromID(getID(itemStack, "CustomMaterialID"));
    }

    public static CustomBlock getCustomBlock(ItemStack itemStack){
        System.out.println(itemStack.getMeta().toNBT());
        if (getID(itemStack, "CustomBlockID") == null)
            return null;
        return getBlockFromID(getID(itemStack, "CustomBlockID"));
    }

    public static CustomBlock getCustomBlock(Point location){
        return Register.blockLocations.get(location);
    }

}
