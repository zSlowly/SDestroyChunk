package pt.slowly.destroychunk.events;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasDisplayName() && p.getItemInHand().getItemMeta().getDisplayName().equals("§eDestruidor de Chunks")) {
				e.setCancelled(true);
				
				if (p.getItemInHand().getAmount() > 1) {
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}else {
					p.getInventory().remove(p.getItemInHand());
				}
				
				MPlayer mp = MPlayer.get(p);
				Faction fac = mp.getFaction();
				PS ps = PS.valueOf(p.getLocation().getChunk());
				Set<PS> chunks = BoardColl.get().getChunks(fac);
				
				if (!chunks.contains(ps)) {
					p.sendMessage("§cEste terreno não pertence á sua facção.");
					return;
				}
				
				if (mp.getRole() != Rel.OFFICER && mp.getRole() != Rel.LEADER) {
					p.sendMessage("§cApenas Líder ou Capitão podem utilizar este item em territórios da sua facção.");
					return;
				}
				
				getChunkBlock(p.getLocation().getChunk());
				p.sendMessage("§aVocê utilizou o §eDestruidor de Chunks§a!");
				
			}
			
		}
		
	}
	
	public static void getChunkBlock(final Chunk chunk) {
        final int minX = chunk.getX() << 4;
        final int minZ = chunk.getZ() << 4;
        final int maxX = minX | 15;
        final int maxY = chunk.getWorld().getMaxHeight();
        final int maxZ = minZ | 15;

        for (int x = minX; x <= maxX; ++x) {
            for (int y = 0; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    if (chunk.getBlock(x, y, z).getType() != Material.BEDROCK && chunk.getBlock(x, y, z).getType() != Material.ENDER_STONE && chunk.getBlock(x, y, z).getType() != Material.OBSIDIAN) {
                    	chunk.getBlock(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }

}
