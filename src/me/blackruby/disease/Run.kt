package me.blackruby.disease

import me.blackruby.disease.types.Disease
import me.blackruby.disease.types.Position
import me.blackruby.disease.types.Time
import org.bukkit.Bukkit
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class Run: BukkitRunnable() {

    var d = DiseaseManager()
    var p = PlayerManager()

    override fun run() {

        // THIS NEEDS SOME CLEANUP      v

        if(d.checkTime("world") == Time.NIGHT) {

            for(online in Bukkit.getOnlinePlayers()) {

                if(p.checkPosition(online) == Position.IN_OPEN && d.contract(100) == true) {

                    d.addPlayer(online.name, Disease.COLD)

                }

            }

        }

        // THIS NEEDS SOME CLEANUP      ^

        /**
         *  Disease symptoms system.
         *
         *  Every second it loops through all the online players, checks whether anyone from the online players have
         *  any disease.
         *
         *  If given player doesn't then wohooo, he is healthy.
         *  But when given player does have a disease then it gives them the disease's symptoms.
         */

        for(online in Bukkit.getOnlinePlayers()) {

            if(d.cold.containsKey(online.name)) {

               d.infectPlayerNearby(online, Disease.COLD, 20)

               when(p.checkSeverity(online.name, d.cold)) {
                   1 -> online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 1))
                   2 -> {
                       online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 2))
                       online.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 20, 1))
                   }
                   3 -> online.isDead
               }

            }

            if(d.brokenLeg.containsKey(online.name)) {

                when(p.checkSeverity(online.name, d.brokenLeg)) {
                    1 -> online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 2))
                    2 -> online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 3))
                    3 -> online.isDead
                }

            }

            if(d.choking.containsKey(online.name)) {

                when(p.checkSeverity(online.name, d.choking)) {
                    1 -> {
                        online.damage(1.0)
                        online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 1))
                    }
                }

            }

            if(d.wound.containsKey(online.name)) {

                when(p.checkSeverity(online.name, d.wound)) {
                    1 -> {
                        online.damage(2.0)
                        online.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20, 1))
                    }
                }

            }

        }

    }

}