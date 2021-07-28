package net.voidmc.voidrpg.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import org.jglrxavpok.hephaistos.nbt.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemSerialization {


    public static String serilie(ItemStack[] items) throws IllegalStateException {
        try {

            List<JsonObject> jsonObjects = new ArrayList<>();

            for (ItemStack itemStack : items) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("MaterialID", itemStack.getMaterial().getId());
                jsonObject.addProperty("Amount", itemStack.getAmount());
                jsonObject.addProperty("NBT", String.valueOf(itemStack.getMeta().toNBT()));
                jsonObjects.add(jsonObject);
            }

            return Arrays.toString(jsonObjects.toArray());

        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] deserilize(String data) throws IOException, NBTException {
        if (data == null)
            return null;
        JsonParser parser = new JsonParser();
        JsonArray json = (JsonArray) parser.parse(data);

        List<ItemStack> itemStacks = new ArrayList<>();

        for (int i = 0; i < 41; i++) {
            JsonElement jsonElement = json.get(i);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            SNBTParser parsers = new SNBTParser(new StringReader(String.valueOf(jsonObject.get("NBT"))));

            NBTCompound list = null;

            if (parsers.parse() instanceof NBTCompound) {
                System.out.println("NBT Compound!");
                list = (NBTCompound) parsers.parse();
            }

            final Map<Integer, Material> idToMaterial = Arrays.stream(Material.values())
                    .filter(x -> x.getBlock() != null)
                    .map(x -> Map.entry(x.getBlock().id(), x))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Material material = idToMaterial.get(Integer.parseInt(String.valueOf(jsonObject.get("MaterialID"))));

            if (material != null) {
                ItemStack itemStack = ItemStack.fromNBT(material,
                        list, Integer.parseInt(String.valueOf(jsonObject.get("Amount"))));

                itemStacks.add(itemStack);
            }
        }

        return itemStacks.toArray(new ItemStack[41]);
    }
}
