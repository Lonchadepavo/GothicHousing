package com.loncha.gothichousing;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class InterfazCasa {
	static ProtectedRegion rgCasa;
	static Player owner;
	
	Inventory invCasa;
	static Inventory invInvitarMiembro, invEcharMiembro;
	
	public InterfazCasa(ProtectedRegion rgCasa, Player owner) {
		this.rgCasa = rgCasa;
		this.owner = owner;
	}
	
	public void mostrarInventario() {
		invCasa = Bukkit.createInventory(owner, 9, rgCasa.getId().replaceAll("\\D+", ""));
		
		createDisplay(Material.BOOK, invCasa, 0, "Invitar miembro", "Invita a alguien para que viva en tu casa.");
		createDisplay(Material.BOOK, invCasa, 1, "Echar miembro", "Echa a un miembro de tu casa.");
		createDisplay(Material.BOOK, invCasa, 2, "Abandonar casa", "Abandona tu casa y echa a todos los miembros.");
		
		owner.openInventory(invCasa);
	}
	
	public static void mostrarInvitarMiembro() {
		invInvitarMiembro = Bukkit.createInventory(owner, 27, "Invitar personas");
		int contadorHueco = 0;
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			createDisplay(Material.PUMPKIN, invInvitarMiembro, contadorHueco, p.getDisplayName(),"");
			contadorHueco++;
		}
		
		owner.openInventory(invInvitarMiembro);
	}
	
	public static void mostrarEcharMiembro() {
		invEcharMiembro = Bukkit.createInventory(owner, 27, "Echar personas");
		int contadorHueco = 0;
		
		Set<UUID> members = rgCasa.getMembers().getUniqueIds();
		
		for (UUID member : members) {
			Player p = Bukkit.getPlayer(member);
			
			createDisplay(Material.PUMPKIN, invEcharMiembro, contadorHueco, p.getDisplayName(),"");
			contadorHueco++;
		}
		
		owner.openInventory(invEcharMiembro);
	}
	
	public static void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		
		 
		inv.setItem(Slot, item); 
		 
	}

}
