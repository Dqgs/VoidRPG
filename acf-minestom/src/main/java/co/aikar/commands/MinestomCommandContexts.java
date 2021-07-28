package co.aikar.commands;

import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinestomCommandContexts extends CommandContexts<MinestomCommandExecutionContext> {

    MinestomCommandContexts(MinestomCommandManager manager) {
        super(manager);

        registerIssuerAwareContext(CommandSender.class, MinestomCommandExecutionContext::getSender);
        registerIssuerAwareContext(Player.class, (c) -> {
            boolean isOptional = c.isOptional();
            CommandSender sender = c.getSender();
            boolean isPlayerSender = sender instanceof Player;
            if (!c.hasFlag("other")) {
                Player player = isPlayerSender ? (Player) sender : null;
                if (player == null && !isOptional) {
                    throw new InvalidCommandArgument(MessageKeys.NOT_ALLOWED_ON_CONSOLE, false);
                }
                PlayerInventory inventory = player != null ? player.getInventory() : null;
                if (inventory != null && c.hasFlag("itemheld") && !ACFMinestomUtil.isValidItem(inventory.getItemStack(player.getHeldSlot()))) {
                    throw new InvalidCommandArgument(MinecraftMessageKeys.YOU_MUST_BE_HOLDING_ITEM, false);
                }
                return player;
            } else {
                String arg = c.popFirstArg();
                if (arg == null && isOptional) {
                    if (c.hasFlag("defaultself")) {
                        if (isPlayerSender) {
                            return (Player) sender;
                        } else {
                            throw new InvalidCommandArgument(MessageKeys.NOT_ALLOWED_ON_CONSOLE, false);
                        }
                    } else {
                        return null;
                    }
                } else if (arg == null) {
                    throw new InvalidCommandArgument();
                }

                return getPlayer(c.getIssuer(), arg, isOptional);
            }
        });
        registerContext(ChatColor.class, c -> {
            String first = c.popFirstArg();
            ChatColor match = ChatColor.fromName(first);
            if (c.hasFlag("colorsonly")) {
                if (match.isSpecial()) {
                    match = ChatColor.NO_COLOR;
                }
            }
            if (match.equals(ChatColor.NO_COLOR)) {
                String valid = Stream.of(ACFMinestomUtil.getAllChatColors())
                        .map(color -> "<c2>" + ACFUtil.simplifyString(color.toString()) + "</c2>")
                        .collect(Collectors.joining("<c1>,</c1> "));
                throw new InvalidCommandArgument(MessageKeys.PLEASE_SPECIFY_ONE_OF, "{valid}", valid);
            }
            return match;
        });
        registerContext(Pos.class, c -> {
            String input = c.popFirstArg();
            CommandSender sender = c.getSender();
            Point sourceLoc = null;
            if (sender instanceof Player) {
                sourceLoc = ((Player) sender).getPosition();
            }

            boolean rel = input.startsWith("~");
            String[] split = ACFPatterns.COMMA.split(rel ? input.substring(1) : input);
            if (split.length < 3) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_PLEASE_SPECIFY_XYZ);
            }

            Double x = ACFUtil.parseDouble(split[0]);
            Double y = ACFUtil.parseDouble(split[1]);
            Double z = ACFUtil.parseDouble(split[2]);

            if (sourceLoc != null && rel) {
                x += sourceLoc.blockX();
                y += sourceLoc.blockY();
                z += sourceLoc.blockZ();
            } else if (rel) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_CONSOLE_NOT_RELATIVE);
            }

            if (x == null || y == null || z == null) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_PLEASE_SPECIFY_XYZ);
            }

            if (split.length >= 5) {
                Float yaw = ACFUtil.parseFloat(split[3]);
                Float pitch = ACFUtil.parseFloat(split[4]);

                if (pitch == null || yaw == null) {
                    throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_PLEASE_SPECIFY_XYZ);
                }
                return new Pos(x, y, z, yaw, pitch);
            } else {
                return new Pos(x, y, z);
            }
        });
        registerContext(Pos.class, c -> {
            String input = c.popFirstArg();
            CommandSender sender = c.getSender();
            String[] split = ACFPatterns.COMMA.split(input);
            if (split.length < 3) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_PLEASE_SPECIFY_XYZ);
            }

            Integer x = ACFUtil.parseInt(split[0]);
            Integer y = ACFUtil.parseInt(split[1]);
            Integer z = ACFUtil.parseInt(split[2]);

            if (x == null || y == null || z == null) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.LOCATION_PLEASE_SPECIFY_XYZ);
            }

            return new Pos(x, y, z);
        });
    }


    Player getPlayer(MinestomCommandIssuer issuer, String lookup, boolean allowMissing) throws InvalidCommandArgument {
        if(issuer.isPlayer() && (lookup.equalsIgnoreCase("@s") || lookup.equalsIgnoreCase("@p"))) {
            return issuer.getPlayer();
        }

        Player player = ACFMinestomUtil.findPlayerSmart(issuer, lookup);
        if (player == null) {
            if (allowMissing) {
                return null;
            }
            throw new InvalidCommandArgument(false);
        }
        return player;
    }
}
