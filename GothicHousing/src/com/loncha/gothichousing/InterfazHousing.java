package com.loncha.gothichousing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.domains.PlayerDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class InterfazHousing implements CommandExecutor {	
	static Main m;
	
	public static Inventory invCasas;
	public static ArrayList<ProtectedRegion> arrayCasas;
	
	public InterfazHousing(Main m) {
		this.m = m;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		if (arg1.getName().equals("casas")) {
			mostrarInventarioCasas(p, "cviejo");
			
			return true;
		}
		
		return false;
	}
	
	public static void mostrarInventarioCasas(Player p, String asentamiento) {
		arrayCasas = new ArrayList<ProtectedRegion>();
		
		for (String prefijoBarrio : Main.listaPrefijosAsentamientos) {
			if (prefijoBarrio.equals(asentamiento)) {
				ArrayList<ProtectedRegion> arrayCasasTemp = m.getRegionsCasas(prefijoBarrio, p.getWorld());
				
				if (arrayCasasTemp.size() > 0) {
					for (ProtectedRegion rgTemp : arrayCasasTemp) {
						arrayCasas.add(rgTemp);
					}
				}
			}
		}
		
		int numHuecos = 27;
		
		if (arrayCasas.size() > 27) {
			numHuecos = 90;
		}
		
		invCasas = Bukkit.createInventory(p, numHuecos, "Casas");
		
		for (int i = 0; i < arrayCasas.size(); i++) {
			for (int k = 0; k < arrayCasas.size()-i-1; k++) {
				int nombreCasa = Integer.valueOf(arrayCasas.get(k).getId().replaceAll("\\D+", ""));
				int nombreCasa2 = Integer.valueOf(arrayCasas.get(k+1).getId().replaceAll("\\D+", ""));
				
				if (nombreCasa > nombreCasa2) {
					ProtectedRegion temp = arrayCasas.get(k);
					arrayCasas.set(k, arrayCasas.get(k+1));
					arrayCasas.set(k+1, temp);
				}
			}
		}
		
		for(ProtectedRegion rg : arrayCasas) {
			Boolean bolOcupada = rg.hasMembersOrOwners();
			
			String nombreCasaSaved = rg.getId();
			String nombreCasa = nombreCasaSaved.replaceAll("\\D+", "");

			Set<UUID> ddOwner = rg.getOwners().getUniqueIds();
			
			String idOwner = "";
			
			for (UUID ownerId : ddOwner) {
				Player owner = Bukkit.getPlayer(ownerId);
				
				if (owner != null) {
					idOwner = owner.getDisplayName();
				} else {
					idOwner = Bukkit.getOfflinePlayer(ownerId).getName();
				}
			}
			List<String> descripcion;
			
			if (ddOwner.size() > 0 ) {
				descripcion = new ArrayList<String>(Arrays.asList("El dueño de la casa es: " + idOwner)) ;
			} else {
				descripcion = new ArrayList<String>(Arrays.asList("La casa no tiene dueño")) ;
			}
			
			ItemStack casa;
			
			if (bolOcupada) {
				casa = new ItemStack(Material.REDSTONE_BLOCK);
			} else {
				casa = new ItemStack(Material.EMERALD_BLOCK);
			}
			
			ItemMeta casaInfo = casa.getItemMeta();
			
			casaInfo.setDisplayName(nombreCasa);
			casaInfo.setLore(descripcion);
			
			casa.setItemMeta(casaInfo);
			
			invCasas.addItem(casa);
			
		}
		
		p.openInventory(invCasas);
	}

}
