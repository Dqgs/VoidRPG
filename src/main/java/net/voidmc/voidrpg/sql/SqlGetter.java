package net.voidmc.voidrpg.sql;

import net.minestom.server.coordinate.Point;
import net.voidmc.voidrpg.VoidRPG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlGetter {

    public void createTable(){
        PreparedStatement stats;
        PreparedStatement blockLocations;
        try {
            stats = VoidRPG.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS stats "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),XP INT(255),Level INT(255),PRIMARY KEY (UUID))");
            stats.execute();

            blockLocations = VoidRPG.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS blockLocations "
             + "(LOCATION TEXT(1000000), BlockID INT, PRIMARY KEY (LOCATION))");

            blockLocations.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsStats(UUID uuid){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT * FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsBlockLocation(Point blockPosition){
        try {
            PreparedStatement ps = VoidRPG.getSQL().getConnection().prepareStatement("SELECT * FROM blockLocations WHERE Location=?");
            ps.setString(1, blockPosition.toString());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
