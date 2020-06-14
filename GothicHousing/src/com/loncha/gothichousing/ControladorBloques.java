package com.loncha.gothichousing;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ControladorBloques implements Listener{
	
	//Evento que detecta la interacción de un player con el bloque de housing
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			//Comprueba si el bloque está en la lista, y cual de ellos es.
			for(String metadata : Main.listaBloques) {
				if (b.hasMetadata(metadata)) {
					if (p.hasPermission(metadata)) {
						InterfazHousing.mostrarInventarioCasas(p, metadata); //Abre la interfaz de housing relacionada con el bloque pulsado.
					}
				}
			}
		}
	}
}
