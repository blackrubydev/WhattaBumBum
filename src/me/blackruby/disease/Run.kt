package me.blackruby.disease

import me.blackruby.disease.types.Diseases
import me.blackruby.disease.types.Position
import me.blackruby.disease.types.Time
import org.bukkit.Bukkit
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class Run: BukkitRunnable() {

    var d = Disease()
    var p = Player()

    override fun run() {

        var random = (1..51).random()

        /**
         *  Megfázás: éjjel, ha a player feletti 20. blockig levegő.
         */

        if(d.checkTime("world") == Time.NIGHT) {

            for(online in Bukkit.getOnlinePlayers()) {

                /**
                 *  1 az 50-hez esély van arra, hogy valaki megfázzon.
                 */

                if(p.checkPosition(online) == Position.IN_OPEN && random == 1) {
                    d.addPlayer(online.name, Diseases.COLD)

                }

            }

        }



        for(online in Bukkit.getOnlinePlayers()) {

            if(d.cold.containsKey(online.name)) {

               d.infectPlayerNearby(online, Diseases.COLD, 20)

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

        /**
         *
         */

    }

}