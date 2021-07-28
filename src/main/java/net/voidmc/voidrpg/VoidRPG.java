package net.voidmc.voidrpg;

import co.aikar.commands.MinestomCommandManager;
import net.minestom.server.Bootstrap;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.voidmc.voidrpg.block.BrokenBlocksService;
import net.voidmc.voidrpg.commands.GiveCMD;
import net.voidmc.voidrpg.commands.StaffCMD;
import net.voidmc.voidrpg.customItems.CustomItemEvents;
import net.voidmc.voidrpg.customItems.Register;
import net.voidmc.voidrpg.data.DataManager;
import net.voidmc.voidrpg.events.PlayerJoinQuit;
import net.voidmc.voidrpg.events.SaveChunks;
import net.voidmc.voidrpg.loader.AnvilLoader;
import net.voidmc.voidrpg.sql.BlockLocations;
import net.voidmc.voidrpg.sql.DataPlayerSql;
import net.voidmc.voidrpg.sql.MySql;
import net.voidmc.voidrpg.sql.SqlGetter;
import net.querz.mca.MCAFile;

import java.sql.SQLException;
import java.util.*;

public final class VoidRPG {

    private static DataManager dataManager;
    private static SqlGetter data;
    private static MySql sql;
    public static DataPlayerSql dataPlayerSql;
    public static String prefix = "Void: ";

    private static HashMap<String, MCAFile> mcaFiles = new HashMap<>();
    private static HashMap<String, Block> blocks = new HashMap<>();

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();

        MojangAuth.init();

        sql = new MySql();
        System.out.println("SQL: " + sql);
        data = new SqlGetter();

        try {
            sql.initializeHikari();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database Connected");
        if (sql.isConnected()) {
            System.out.println(prefix + "Database has been created");
            data.createTable();
        }
        dataManager = new DataManager();

        Register.registerAll();
        Register.blockLocations = BlockLocations.getMissingBlocks();
        reigsterEvents(instanceContainer);
        registerCommands();

        AnvilLoader anvilLoader = new AnvilLoader("./world");
        instanceContainer.setChunkLoader(anvilLoader);
        instanceContainer.enableAutoChunkLoad(true);

       // Bootstrap.bootstrap("net.voidmc.voidrpg.VoidRPG", new String[0]);
        server.start("0.0.0.0", 25565);
    }

    private static void reigsterEvents(InstanceContainer instance) {
        new PlayerJoinQuit(instance);
        new SaveChunks(instance);
        new CustomItemEvents();
        BrokenBlocksService.getInstance();
    }

    private static void registerCommands() {
        MinestomCommandManager commandManager = new MinestomCommandManager();

        commandManager.registerCommand(new GiveCMD());
        commandManager.registerCommand(new StaffCMD());
    }

    public static DataManager getDataManager() {
        if (dataManager == null)
            dataManager = new DataManager();
        return dataManager;
    }

    public static SqlGetter getData() {
        if (data == null)
            new SQLException("SQLGetter has not been properly set and failed getting it").printStackTrace();
        return data;
    }

    public static MySql getSQL() {
        if (sql == null)
            new SQLException("SQL has not been properly set and has failed getting it").printStackTrace();
        return sql;
    }

    public static DataPlayerSql getDataPlayerSql(){
        if (dataPlayerSql == null)
            dataPlayerSql = new DataPlayerSql();
        return dataPlayerSql;
    }

}
