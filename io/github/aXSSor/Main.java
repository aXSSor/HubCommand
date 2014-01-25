package io.github.aXSSor;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin
  implements Listener
  {

	

	
  private String lobby;
  private String alreadyThere;
  private String commandName;
  
  

  public void onEnable()
  {
    System.out.println("HubCommand Enabled v0.1");
    

    
    
    loadConfig("plugins/" + getDescription().getName(), "/settings.ini");
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command(this.commandName)
    {
      public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (!p.getServer().getInfo().getName().equals(Main.this.lobby)) {
          if (ProxyServer.getInstance().getServers().containsKey(Main.this.lobby))
          {
            p.connect(ProxyServer.getInstance().getServerInfo(Main.this.lobby));
          }
          else p.sendMessage("§cCannot find §f'" + Main.this.lobby + "'§c at your BungeeCord Config!");
        }
        else
          p.sendMessage(Main.this.alreadyThere);
      }
    });
  }

  
  
  
  
  
  
  private void loadConfig(String folder, String filename)
  {
    Properties config = new Properties();
    try {
      File dir = new File(folder);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      File file = new File(folder + filename);
      if (!file.exists()) {
        file.createNewFile();
        config.load(new FileReader(file));
        config.setProperty("defaultServer", "yourlobbyserver");
        config.setProperty("yourlobbyCommand", "hub");
        config.setProperty("alreadyConnected", "You are already at the default server!");
      } else {
        config.load(new FileReader(file));
      }
      this.lobby = config.getProperty("defaultServer", "yourlobbyserver");
      this.commandName = config.getProperty("yourlobbyCommand", "hub");
      this.alreadyThere = config.getProperty("alreadyConnected", "You are already at the default server!");
    } catch (Exception e) {
      ProxyServer.getInstance().getLogger().severe("Failed to Load " + getDescription().getName() + " settings file, please make sure its available! ");
    }
  }
}