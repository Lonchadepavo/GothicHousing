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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MisCasas implements CommandExecutor {
	static Main m;

	public MisCasas(Main m) {
		this.m = m;
	}
	
	public static Inventory misCasas;

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Te abre el inventario con todas tus casas
		//En cada casa indica su nombre, sus miembros y donde está situada.
		//Cuando haces click en una de las casas te abre una interfaz con 3 opciones, invitar miembro, eliminar miembro y abandonar casa.
		Player p = (Player) arg0;
		if (arg1.getName().equals("miscasas")) {
			mostrarMisCasas(p);
			
			return true;
		}
		
		
 		return false;
	}
	
	public static void mostrarMisCasas(Player p) {
		ArrayList<ProtectedRegion> casasOwner = new ArrayList<ProtectedRegion>();
		
		for (String prefijo : Main.listaPrefijosBarrios) {
			ArrayList<ProtectedRegion> tempCasas = m.getRegionsCasas(prefijo, p.getWorld());

			for (ProtectedRegion casa : tempCasas) {
				Set<UUID> ddOwner = casa.getOwners().getUniqueIds();
				String idOwner = "";
				
				for (UUID ownerId : ddOwner) {
					if (ownerId.equals(p.getUniqueId())) {
						casasOwner.add(casa);
					}
				}
			}
		}
		
		misCasas = Bukkit.createInventory(p, 27, "Mis casas");
		
		for(ProtectedRegion rg : casasOwner) {
			
			String nombreCasaSaved = rg.getId();
			String nombreCasa = nombreCasaSaved.replaceAll("\\D+", "");
			
			List<String> descripcion = new ArrayList<String>();
			Set<UUID> ddOwner = rg.getMembers().getUniqueIds();
			
			String idMember = "";
			
			for (UUID ownerId : ddOwner) {
				Player member = Bukkit.getPlayer(ownerId);
				idMember = member.getDisplayName();
				String frase = "Miembro: " + idMember;
				descripcion.add(frase);
			}
			ItemStack casa = new ItemStack(Material.EMERALD_BLOCK);;
			
			ItemMeta casaInfo = casa.getItemMeta();
			
			casaInfo.setDisplayName(nombreCasa);
			casaInfo.setLore(descripcion);
			
			casa.setItemMeta(casaInfo);
			
			misCasas.addItem(casa);
			
		}
		
		p.openInventory(misCasas);
	}

}
