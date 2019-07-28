package me.blackruby.disease

import me.blackruby.disease.types.Disease
import me.blackruby.disease.types.Time
import org.bukkit.Bukkit
import org.bukkit.entity.Entity


class DiseaseManager {

    var p = PlayerManager()

    /**
     *  These are the hashmaps for the diseases. Each disease's hashmap stores the player who has that disease and
     *  it's severity.
     */

    var cold: HashMap<String, Int> = HashMap()
    var wound: HashMap<String, Int> = HashMap()
    var brokenLeg: HashMap<String, Int> = HashMap()
    var choking: HashMap<String, Int> = HashMap()

    /**
     *  Kind of self-explanatory. If a player catches a disease then the player is getting added to the disease's
     *  hashmap.
     */

    fun addPlayer(player: String, diseaseType: Disease) {

        when (diseaseType) {
            Disease.COLD -> cold.put(player, 1)
            Disease.WOUND -> wound.put(player ,1)
            Disease.BROKEN_LEG -> brokenLeg.put(player, 1)
            Disease.CHOKING -> choking.put(player, 1)
        }

    }

    /**
     *  If a player dies while having a disease or the doctore cures the player then the player gets removed from
     *  the disease's hashmap.
     */

    fun removePlayer(player: String, diseaseType: Disease) {

        when (diseaseType) {
            Disease.COLD -> cold.remove(player)
            Disease.WOUND -> wound.remove(player)
            Disease.BROKEN_LEG -> brokenLeg.remove(player)
            Disease.CHOKING -> choking.remove(player)
        }

    }

    /**
     *  Returns (based on the in-game time) whether it is day or night time in th given world.
     */

    fun checkTime(world: String?): Time {

        if(Bukkit.getWorld(world!!)!!.time < 12300 || Bukkit.getWorld(world)!!.time > 23850) {
            return Time.NIGHT
        }
        return Time.DAY
    }

    /**
     *  Checks if given disease is contagious or not: if not, then the illness can't pass onto others.
     *  Later on will be more useful.
     */

    fun isContagious(disease: Disease) =
        when(disease) {
            Disease.COLD -> true
            else -> false
        }

    /**
     *  Chance of contracting a disease. 1 out of (chance) times.
     */

    fun contract(chance: Int): Boolean {

        if(chance == 0) return false

        val random = (1..(chance + 1)).random()
        return random == 1
    }

    /**
     *  Gets an infected player, checks for player entities nearby, and if contract returns true, then those entities
     *  get infected.
     */

    fun infectPlayerNearby(player: org.bukkit.entity.Player?, diseaseType: Disease, chance: Int) {

        if(isContagious(diseaseType) != true) return

        val nearby: List<Entity>? = player?.getNearbyEntities(5.0, 5.0, 5.0)
        if(nearby == null) return

        for(entity in nearby) {

            if(entity !is org.bukkit.entity.Player) return
            if(contract(chance) != true) return

            addPlayer(entity.name, diseaseType)

        }

    }

}