package me.blackruby.disease

import me.blackruby.disease.types.Disease
import me.blackruby.disease.types.Position
import org.bukkit.Material
import org.bukkit.entity.Player

class PlayerManager {

    var d = DiseaseManager()

    /**
     *  Increases the player's illness' severity by one, which makes the symptoms more severe, eventually
     *  (if not treated) killing the player.
     */

    fun increaseDiseaseSeverity(player: String, diseaseType: Disease) {

        when (diseaseType) {
            Disease.COLD -> d.cold.put(player, d.cold.get(player)!! + 1)
            Disease.WOUND -> d.wound.put(player, d.wound.get(player)!! + 1)
            Disease.BROKEN_LEG -> d.brokenLeg.put(player, d.brokenLeg.get(player)!! + 1)
            Disease.CHOKING -> d.choking.put(player, d.choking.get(player)!! + 1)
        }

    }

    /**
     *  Returns what diseases does a player have since they can have multiple of them at the same time.
     */

    fun checkDisease(player: String): ArrayList<Disease>? {

        var playerDiseases: ArrayList<Disease>? = ArrayList()

        if(d.cold.containsKey(player)) playerDiseases?.add(Disease.COLD)
        if(d.wound.containsKey(player)) playerDiseases?.add(Disease.WOUND)
        if(d.choking.containsKey(player)) playerDiseases?.add(Disease.CHOKING)
        if(d.brokenLeg.containsKey(player)) playerDiseases?.add(Disease.BROKEN_LEG)

        return playerDiseases
    }

    /**
     *  Returns a specific player's disease's severity. It can be either 1, 2 or 3 (3 being the lethal).
     */

    fun checkSeverity(player: String, disease: HashMap<String, Int>): Int {

        var severity = disease.get(player)!!

        return severity
    }

    /**
     *  Checks the player's position. If all the 20 blocks above the player equal Material.AIR then it means the player
     *  is out in the open. This is neccessary, so that people don't get cold in their houses and they don't get
     *  heatstrokes while isnide their homes.
     */

    fun checkPosition(player: Player): Position {

        for(i in 1..20) {
            if(player.location.add(0.0, 0.0 + i,0.0).block.type != Material.AIR) {
                break
            }

            return Position.IN_OPEN
        }

        if(player.location.subtract(0.0, 1.0, 0.0).block.type == Material.WATER) return Position.IN_WATER

        return Position.NON
    }

}