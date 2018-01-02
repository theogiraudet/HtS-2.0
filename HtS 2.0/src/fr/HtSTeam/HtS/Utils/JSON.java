package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class JSON {
	
	public static void sendJsonSuggestCommand(Player p, String message, String cmd) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + cmd + "\"}}")));
    }
	
	public static void sendJsonRunCommand(Player p, String message, String cmd) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + cmd + "\"}}")));
    }
	
	public static void send(Player p, String title, String subtitle, int time) {
		PacketPlayOutTitle packet_title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}"));
		PacketPlayOutTitle packet_subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"));
		PacketPlayOutTitle length = new PacketPlayOutTitle(5, time * 20, 5);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet_title);
		if (subtitle != null)
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet_subtitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
	}
	
	public static void sendAll(String title, String subtitle, int time) {
		PacketPlayOutTitle packet_title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}"));
		PacketPlayOutTitle packet_subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"));
		PacketPlayOutTitle length = new PacketPlayOutTitle(5, time * 20, 5);
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet_title);
			if (subtitle != null)
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet_subtitle);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
		}
	}

}
