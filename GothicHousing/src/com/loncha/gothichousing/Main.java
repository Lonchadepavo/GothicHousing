package com.loncha.gothichousing;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Main extends JavaPlugin implements Listener {	
	ControladorCasas cCasas;
	ControladorBloques cBloques;
	MisCasas mCasas;
	InterfazHousing iHousing;
	SetCampamento sCampamento;
	CargarBloques cargBloques;
	
	public static String[] listaPrefijosBarrios = {"cviejopobre", "cviejoentrada", "cviejoarena", "cviejorico", "cviejomercado"};
	public static String[] listaPrefijosAsentamientos = {"cviejo", "tienda"};
	public static String[] listaBloques = {"cviejo","tienda"};
	
	@Override
	public void onEnable() {
		cCasas = new ControladorCasas();
		cBloques = new ControladorBloques();
		iHousing = new InterfazHousing(this);
		CargarBloques cargBloques;
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(this.cCasas, this);
		getServer().getPluginManager().registerEvents(this.cBloques, this);
		
		//Lista de comandos
		getCommand("miscasas").setExecutor(new MisCasas(this));
		getCommand("setcampamento").setExecutor(sCampamento = new SetCampamento());
		getCommand("cargarbloques").setExecutor(cargBloques = new CargarBloques(sCampamento));
		
		File f = new File("plugins/GothicHousing");
		
		if (!f.exists()) {
			f.mkdir();
		}
		
		//sCampamento.cargarBloqueCampamento();
	}

	public ArrayList<ProtectedRegion> getRegionsCasas(String prefix, World w) {
		ArrayList<ProtectedRegion> arrayCasas = new ArrayList<ProtectedRegion>();
		
		WorldGuardPlugin wg = getWorldGuard();
		RegionManager rmg = wg.getRegionManager(w);
		
		ProtectedRegion region;
		
		Map<String, ProtectedRegion> regions = rmg.getRegions();
		Iterator it = regions.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();

			if (pair.getKey().toString().startsWith(prefix)) {
				region = (ProtectedRegion) pair.getValue();
				arrayCasas.add(region);
			}
		}
		
		return arrayCasas;
	}
	
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
	    return null;
	    }
	 
	    return (WorldGuardPlugin)plugin;
	}
}
