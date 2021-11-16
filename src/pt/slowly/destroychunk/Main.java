package pt.slowly.destroychunk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pt.slowly.destroychunk.comandos.GiveDestruidorChunk;
import pt.slowly.destroychunk.events.PlayerInteract;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		registro();
	}
	
	@Override
	public void onDisable() {
	}
	
	public void registro() {
		getCommand("givedestruidorchunk").setExecutor(new GiveDestruidorChunk());
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
	}

}
