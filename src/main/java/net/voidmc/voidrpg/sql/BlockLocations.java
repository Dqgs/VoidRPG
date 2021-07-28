package net.voidmc.voidrpg.sql;

import net.minestom.server.coordinate.Point;
import net.voidmc.voidrpg.VoidRPG;
import net.voidmc.voidrpg.customItems.CustomBlock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BlockLocations {

    public static  void saveBlock(Point location, CustomBlock block) {

        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("UPDATE blockLocations SET "
                    + "Location=?,BlockID=?");
            ps.setString(1, location.toString());
            ps.setInt(2, block.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeBlock(Point location){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("UPDATE blockLocations SET "
                    + "Location=?,BlockID=? WHERE Location=?");
            ps.setString(1, null);
            ps.setInt(2, 0);
            ps.setString(3, location.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Point, CustomBlock> getMissingBlocks() {
        try {
            HashMap<Point, CustomBlock> list = new HashMap<>();

            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT * FROM blockLocations");
            ResultSet resultSet = ps.getResultSet();

            System.out.println(resultSet);

            return list;
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
