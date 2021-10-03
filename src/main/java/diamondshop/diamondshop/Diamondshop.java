package diamondshop.diamondshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Diamondshop extends JavaPlugin implements Listener {

        String prefix = ChatColor.GRAY  + "" + ChatColor.BOLD + "[" + ChatColor.BLUE  + "" + ChatColor.BOLD + "Diamond" + ChatColor.GOLD  + "" + ChatColor.BOLD + "Shop" + ChatColor.GRAY  + "" + ChatColor.BOLD + "] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        getLogger().info("DiamondShop Has Been Enabled");
        Bukkit.getPluginManager().registerEvents(this,this);
    }
    @Override
    public void onDisable() {
        getLogger().info("DiamondShop Has Been Disabled");
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission("sign.use")) {

            Block b = e.getClickedBlock();
            if (b.getType() == Material.OAK_SIGN || b.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) b.getState();
                if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(p.getDisplayName())) {
                    if(sign.getLine(2).equals("") || sign.getLine(2) == null) return;
                    if(sign.getLine(1) != null || !sign.getLine(1).equals("")) {
                              for(int i = 0; i < 35;i++) {
                                  if (p.getInventory().getItem(i) != null) {
                                      if (p.getInventory().getItem(i).getType() == Material.DIAMOND) {
                                          int amountofdiamonds = p.getInventory().getItem(i).getAmount();
                                          int amountofdiamondsNeeded = Integer.parseInt(sign.getLine(1));
                                          if (amountofdiamonds < amountofdiamondsNeeded) {
                                              p.sendMessage(prefix + "You Do Not Have Enough Diamonds!");
                                              return;
                                          }
                                          if(b != null && b.getState() instanceof Sign) {

                                              BlockData data = b.getBlockData();
                                              if(data instanceof Directional) {
                                                  Directional directional = (Directional) data;
                                                  Block blockBehind = b.getRelative(directional.getFacing().getOppositeFace());
                                                          if(blockBehind.getType() == Material.CHEST) {
                                                              Chest chest = (Chest) blockBehind.getState();
                                                              if(chest.getInventory().contains(Material.getMaterial(sign.getLine(2)))) {



                                                                  for(int c = 0; c < 27;c++) {
                                                                      if(chest.getInventory().getItem(c) != null) {



//                                                                          if(chest.getInventory().getItem(c).getType() == Material.DIAMOND) {
//
//                                                                              int amountofdiamondsinChest = chest.getInventory().getItem(c).getAmount();
//                                                                              int amountofdiamondsNeededinChest = Integer.parseInt(sign.getLine(1));
//                                                                              int maxStackSize = chest.getInventory().getItem(c).getMaxStackSize();
//                                                                              int totalMax = maxStackSize - amountofdiamondsNeededinChest;
//                                                                              if(!(amountofdiamondsinChest <= totalMax)) {
//                                                                                  p.sendMessage(prefix + "Shop Is Full");
//                                                                                  return;
//                                                                              }
//
//                                                                          }



                                                                          if(chest.getInventory().getItem(c).getType() == Material.getMaterial(sign.getLine(2))) {

                                                                              if(chest.getInventory().getItem(c).getAmount() >= Integer.parseInt(sign.getLine(3))) {
                                                                                  if(chest.getInventory().getItem(c).getAmount() == Integer.parseInt(sign.getLine(3))) {

                                                                                      chest.getInventory().remove(chest.getInventory().getItem(c));
                                                                                  } else {
                                                                                      ItemStack newDiamondAmount = new ItemStack(Material.getMaterial(sign.getLine(2)), chest.getInventory().getItem(c).getAmount() - Integer.parseInt(sign.getLine(3)));
                                                                                      chest.getInventory().setItem(c,newDiamondAmount);
                                                                                  }
                                                                                  p.getInventory().addItem(new ItemStack(Material.getMaterial(sign.getLine(2)), Integer.parseInt(sign.getLine(3))));
                                                                              } else {
                                                                                  p.sendMessage(prefix + "Not Enough Items To Give You");
                                                                                  return;
                                                                              }
                                                                          }
                                                                      }
                                                                  }
                                                              } else {
                                                                  p.sendMessage(prefix + "Does Not Contain The Item In The Chest!");
                                                                  return;
                                                              }


                                                              if(p.getInventory().getItem(i).getAmount() == Integer.parseInt(sign.getLine(1))) {

                                                                  p.getInventory().remove(p.getInventory().getItem(i));
                                                              } else {
                                                                  ItemStack newDiamondAmount = new ItemStack(Material.DIAMOND, p.getInventory().getItem(i).getAmount() - Integer.parseInt(sign.getLine(1)));
                                                                  p.getInventory().setItem(i,newDiamondAmount);
                                                              }
                                                              chest.getInventory().addItem(new ItemStack(Material.DIAMOND, Integer.parseInt(sign.getLine(1))));
                                                              p.sendMessage(prefix + "Gaved " + sign.getLine(1) + " Diamonds for " + sign.getLine(3) + " " + sign.getLine(2));
                                                              return;
                                                          }
                                              }
                                          }


                                        }
                                }

                              }

                              if(!p.getInventory().contains(Material.DIAMOND)) {
                                  p.sendMessage(prefix + "You Do Not Have Diamonds In Your Inventory!");
                              }

                    }
                } else {
                    if(!p.getInventory().getItemInMainHand().getType().isAir()) {
                        sign.setLine(2,p.getInventory().getItemInMainHand().getType().toString());
                        sign.update(true);
                    } else {
                        p.sendMessage("Hand has Nothing");
                    }
                }
            }

        }

    }


}
