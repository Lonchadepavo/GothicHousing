package com.loncha.gothichousing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class SetCampamento implements CommandExecutor, Plugin{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		if (arg1.getName().equals("setcampamento")) {
			if (p.hasPermission("ghousing.admin")) {
				if (arg3.length > 0) {
					Location l = p.getLocation();
					crearBloqueCampamento(l, arg3[0], true);
					
				} else {
					p.sendMessage("Uso: /setcampamento <nombre>");
				}
			} else {
				p.sendMessage("Comando solo para administradores");
			}
				
			return true;
		}
		
		return false;
	}
	
	public void crearBloqueCampamento(Location loc, String nombre, boolean primeravez) {
		String rutaArchivo = "plugins/GothicHousing/localizacion.txt";

		Location posicion = loc;
		Block b = posicion.getBlock();
		b.setType(Material.EMERALD_BLOCK);
		b.setMetadata(nombre, new FixedMetadataValue(this, "campamento"));
		
		if (primeravez) {
			guardarBloqueCampamento(b, rutaArchivo, nombre);
		}

	}
	
	public void guardarBloqueCampamento(Block b, String ruta, String nombre) {
		File f  = new File(ruta);
		
		try {
			if (!f.exists()) {
				f.createNewFile();

			}
			//Nombre x y z
			String datos[] = {nombre, String.valueOf(b.getX()), String.valueOf(b.getY()), String.valueOf(b.getZ())};
			
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (String dato : datos) {
				bw.write(dato+" ");
			}
			bw.newLine();
			
			bw.close();
			fw.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cargarBloqueCampamento() {
		String rutaArchivo = "plugins/GothicHousing/localizacion.txt";
		File f = new File(rutaArchivo);
		System.out.println("entra0");
		try {
			if (f.exists()) {
				System.out.println("entra1");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while((line = br.readLine()) != null) {
					System.out.println("entra2");
					String[] datos = line.split(" ");
					
					String nombre = datos[0];
					for (String s : datos) {
						System.out.println(s);
					}
					Location l = new Location(Bukkit.getWorld("Gothic"), Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Integer.valueOf(datos[3]));
					Block b = l.getBlock();
					System.out.println(b.getType().toString());
					crearBloqueCampamento(l, nombre, false);
				}
				
				br.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileConfiguration getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDataFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginDescriptionFile getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginLoader getPluginLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNaggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDefaultConfig() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveResource(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNaggable(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

}
