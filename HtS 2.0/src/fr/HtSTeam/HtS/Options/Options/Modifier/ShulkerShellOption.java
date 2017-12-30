package fr.HtSTeam.HtS.Options.Options.Modifier;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.ItemStackManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class ShulkerShellOption extends OptionsManager {
	
	private HashMap<Player, Integer> shellUse = new HashMap<>();
	private boolean activate = false;
	
	public ShulkerShellOption() {
		super(Material.SHULKER_SHELL, "Shulker Shell", "§4Désactivé", "Désactivé", OptionsRegister.modifiers);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			CustomGUI.authorizedItem.add(new ItemStackManager(Material.SHULKER_SHELL, (short) 0, 1, "§rShulker Shell", "", false));
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			CustomGUI.authorizedItem.add(new ItemStackManager(Material.SHULKER_SHELL, (short) 0, 1, "§rShulker Shell", "", false));
		}
		parent.update(this);		
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(activate && e.getEntity() instanceof Player && ((Player) e.getEntity()).getInventory().contains(Material.SHULKER_SHELL)) {
			Player p = (Player) e.getEntity();
			World world = p.getWorld();
			if(Randomizer.RandRate(63)) {
				if(shellUse.containsKey(p)) {
					shellUse.replace(p, shellUse.get(p) + 1);
					if(shellUse.get(p) == 3) {
						world.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						shellUse.replace(p, 0);
						p.getInventory().removeItem(new ItemStack(Material.SHULKER_SHELL, 1));
					} else {
						world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
					}
				} else {
					shellUse.put(p, 1);
					world.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
				}
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
    public void onShulkerDeathInNether(EntityDeathEvent e){
        if(activate && e.getEntityType() == EntityType.SHULKER){
        	ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            e.getDrops().clear();
            if(Randomizer.RandRate(7)){
                ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta esm = (EnchantmentStorageMeta) book.getItemMeta();
                esm.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                book.setItemMeta(esm);
                drops.add(book);
            }
            if(Randomizer.RandRate(15)) {
            	ItemStack is = new ItemStack(Material.SHULKER_SHELL, 1, (short) 0);
        		ItemMeta isM = is.getItemMeta();
        		ArrayList<String> lore = new ArrayList<String>();
        		int rand = Randomizer.Rand(1000000000);
        		lore.add("A un pourcentage de chance de bloquer un coup.");
        		String res = Integer.toString(rand);
        		String news = "";
        		for(int i = 0; i < res.length(); i++)
        			news += "§" + res.charAt(i);
        		lore.add("3 utilisations" + news);
        		isM.setDisplayName("§rShulker Shell");
        		isM.setLore(lore);
        		is.setItemMeta(isM);
        		drops.add(is);
            }
            e.getDrops().addAll(drops);
        }
    }

}
