package me.blackruby.disease

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {

    override fun onEnable() {
        Run().runTaskTimer(this,0, 20)
    }

}