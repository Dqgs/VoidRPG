package net.voidmc.voidrpg.customItems;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.chat.ChatColor;

import static net.kyori.adventure.text.format.NamedTextColor.*;

public enum Rarity {

    COMMON(WHITE),
    UNCOMMON(GREEN),
    ELITE(BLUE),
    ULTIMATE(YELLOW),
    LEGENDARY(GOLD);

    private final NamedTextColor color;

    Rarity(NamedTextColor color){
        this.color = color;
    }

    public NamedTextColor getColor() {
        return color;
    }
}
