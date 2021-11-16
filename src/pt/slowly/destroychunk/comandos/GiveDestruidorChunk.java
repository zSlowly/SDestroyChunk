package pt.slowly.destroychunk.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveDestruidorChunk implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		
		if (s instanceof Player) {
			Player p = (Player) s;
			
			if (c.getName().equalsIgnoreCase("givedestruidorchunk")) {
				
				if (p.hasPermission("destroychunk.admin")) {
					
					ItemStack i = new ItemStack(Material.ENDER_PORTAL_FRAME);
					ItemMeta meta = i.getItemMeta();
					meta.setDisplayName("§eDestruidor de Chunks");
					List<String> lore = new ArrayList<>();
					lore.add("§7Este item, quando utilizado em chunks da sua facção");
					lore.add("§7Irá destruir todos os blocos da chunk, menos os de proteção.");
					meta.setLore(lore);
					i.setItemMeta(meta);
					
					p.getInventory().addItem(i);
					p.sendMessage("§aVocê pegou o Destruidor de Chunks.");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					
				}else {
					p.sendMessage("§cVocê não possui permissão para executar este comando.");
				}
				
			}
		}else {
			s.sendMessage("§cApenas jogadores podem executar este comando.");
		}
		
		return false;
	}

}
