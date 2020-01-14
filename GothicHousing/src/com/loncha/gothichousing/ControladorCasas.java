package com.loncha.gothichousing;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ControladorCasas implements Listener{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		Player p = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		Inventory inventory = event.getInventory(); 
		
		switch(inventory.getName()) {	
			case "Casas":
				for (ProtectedRegion rg : InterfazHousing.arrayCasas) {
					String nombreCasa = rg.getId();
					
					for (String prefijo : Main.listaPrefijosBarrios) {
						String nombreclick = prefijo+"_"+clicked.getItemMeta().getDisplayName();
						if (nombreclick.equals(nombreCasa)) {
							if (clicked.getType() == Material.EMERALD_BLOCK) {
								if (!comprobarCasas(p,prefijo)) {
									DefaultDomain dd = rg.getOwners();
									dd.addPlayer(p.getUniqueId());
									rg.setOwners(dd);
									
									recargarInventarios();
								} else {
									p.sendMessage("Solo puedes tener alquilada una casa por asentamiento");
								}
								
							}
						}
					}
				}
				event.setCancelled(true);
				break;
			
			case "Mis casas":
				for (ProtectedRegion rg : InterfazHousing.arrayCasas) {
					String nombreCasa = rg.getId();
					
					for (String prefijo : Main.listaPrefijosBarrios) {
						String nombreclick = prefijo+"_"+clicked.getItemMeta().getDisplayName();
						if (nombreclick.equals(nombreCasa)) {
							ProtectedRegion casaSeleccionada = rg;
							InterfazCasa casa = new InterfazCasa(rg, p);
							casa.mostrarInventario();
							
						}
					}
				}
				event.setCancelled(true);
				break;
				
			case "Invitar personas":
				for (Player persona : Bukkit.getOnlinePlayers()) {
					if (clicked.getItemMeta().getDisplayName().equals(persona.getDisplayName())) {
						DefaultDomain members = InterfazCasa.rgCasa.getMembers();
						members.addPlayer(persona.getUniqueId());
						InterfazCasa.rgCasa.setMembers(members);
					}
				}
				
				event.setCancelled(true);
				break;
				
			case "Echar personas":
				for (Player persona : Bukkit.getOnlinePlayers()) {
					if (clicked.getItemMeta().getDisplayName().equals(persona.getDisplayName())) {
						DefaultDomain members = InterfazCasa.rgCasa.getMembers();
						members.removePlayer(persona.getUniqueId());
						InterfazCasa.rgCasa.setMembers(members);
					}
				}
				
				InterfazCasa.mostrarEcharMiembro();
				event.setCancelled(true);
				break;
			
			default:
				if (clicked != null) {
					if (clicked.hasItemMeta()) {
						switch(clicked.getItemMeta().getDisplayName()) {
							case "Invitar miembro":
								InterfazCasa.mostrarInvitarMiembro();
								event.setCancelled(true);
								break;
								
							case "Echar miembro":
								InterfazCasa.mostrarEcharMiembro();
								event.setCancelled(true);
								break;
								
							case "Abandonar casa":
								for (ProtectedRegion rg : InterfazHousing.arrayCasas) {
									
									for (String prefijo : Main.listaPrefijosBarrios) {
										String nombreCasa = prefijo+"_"+inventory.getTitle();
										if (nombreCasa.equals(rg.getId())) {
											DefaultDomain owners = rg.getOwners();
											owners.removeAll();
											rg.setOwners(owners);
											
											DefaultDomain members = rg.getMembers();
											members.removeAll();
											rg.setMembers(members);
										}
									}
								}
								event.setCancelled(true);
								p.closeInventory();
								break;
							
							default:
								System.out.println("entra");
								break;
						}
					}
				}
				break;
		}
	}
	
	public void recargarInventarios() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getOpenInventory().getTitle().equals("Casas")) {
				InterfazHousing.mostrarInventarioCasas(p, "cviejo");	
			}
		}
	}
	
	//Método para comprobar si un player ya tiene una casa en un asentamiento
	public boolean comprobarCasas(Player p, String prefijo) {
		for (ProtectedRegion rg : InterfazHousing.arrayCasas) {
			Set<UUID> owners = rg.getOwners().getUniqueIds();
			
			for(UUID owner : owners) {
				Player tempowner = Bukkit.getPlayer(owner);
				
				if (tempowner.getUniqueId().equals(p.getUniqueId())) {
					if (rg.getId().startsWith(prefijo)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
