package net.voidmc.voidrpg.data;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.voidmc.voidrpg.VoidRPG;

public class DataPlayer {

    private final Player player;
    private double health, maxHealth;
    @Setter
    private double balance;
    @Setter
    private boolean staffMode;
    @Setter
    private long playTime;


    public DataPlayer(Player player){
        this.player = player;

       VoidRPG.getDataPlayerSql().createPlayer(player);

        maxHealth = 100;
        health = 100;

    }

    public void setHealth(double health){
        this.health = health;

        updateHealth();
    }

    public void setMaxHealth(double maxHealth){
        this.maxHealth = maxHealth;

    }

    public void updateHealth() {
        if (health <= 0) {
            player.setHealth(0);
            return;
        }

        if (health < maxHealth)
            player.setHealth((float) (health * 20 / maxHealth));
        else if (health > maxHealth){
            health = maxHealth;
            player.setHealth(player.getMaxHealth());
        }
        updateActionBar();
    }

    public void updateActionBar(){
        Component component = Component.text("Health: " + health + "/" + maxHealth);
        player.sendActionBar(component);
    }

    public Player getPlayer() {
        return player;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isStaffMode() {
        return staffMode;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStaffMode(boolean staffMode) {
        this.staffMode = staffMode;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }
}
