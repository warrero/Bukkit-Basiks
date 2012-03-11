/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksenonomy.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Oa10712
 */
public class remove {
    public void remove(Player player,Material mat, int remove){
        PlayerInventory inv = player.getInventory();
        ItemStack dirt = new ItemStack(mat);
        if(inv.contains(dirt)){
            if(dirt.getAmount()<10){
                player.sendMessage("You do not have enough!");
            }else{
                dirt.setAmount(dirt.getAmount()-remove);
            }
        }
    }
}
