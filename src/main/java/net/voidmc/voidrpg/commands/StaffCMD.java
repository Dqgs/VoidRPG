package net.voidmc.voidrpg.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.voidmc.voidrpg.VoidRPG;
import net.voidmc.voidrpg.data.DataPlayer;

@CommandAlias("Staff")
@Description("Puts you into staff mode")

//@CommandPermission("void.mod")
public class StaffCMD extends BaseCommand {

    @Default
    public void staff(Player player) {
        DataPlayer dataPlayer = VoidRPG.getDataManager().getDataPlayer(player);
        if (dataPlayer.isStaffMode()) {
            dataPlayer.setStaffMode(false);
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            dataPlayer.setStaffMode(true);
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    @Subcommand("help")
    public void help(Player player){
        player.sendMessage("============Staff Commands============\n- /staff help - Shows help page\n============Staff Commands============");
    }
}
