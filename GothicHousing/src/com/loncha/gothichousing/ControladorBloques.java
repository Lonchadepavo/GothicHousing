package com.loncha.gothichousing;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ControladorBloques implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			for(String metadata : Main.listaBloques) {
				if (b.hasMetadata(metadata)) {
					if (p.hasPermission(metadata)) {
						InterfazHousing.mostrarInventarioCasas(p, metadata);
					}
				}
			}
		}
	}
}
