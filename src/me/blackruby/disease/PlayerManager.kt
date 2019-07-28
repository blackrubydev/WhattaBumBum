package me.blackruby.disease

import me.blackruby.disease.types.Disease
import me.blackruby.disease.types.Position
import org.bukkit.Material
import org.bukkit.entity.Player

class PlayerManager {

    var d = DiseaseManager()

    fun increaseDiseaseSeverity(player: String, diseaseType: Disease) {

        when (diseaseType) {
            Disease.COLD -> d.cold.put(player, d.cold.get(player)!! + 1)
            Disease.WOUND -> d.wound.put(player, d.wound.get(player)!! + 1)
            Disease.BROKEN_LEG -> d.brokenLeg.put(player, d.brokenLeg.get(player)!! + 1)
            Disease.CHOKING -> d.choking.put(player, d.choking.get(player)!! + 1)
        }

    }

    fun checkDisease(player: String): ArrayList<Disease>? {

        var playerDiseases: ArrayList<Disease>? = ArrayList()

        if(d.cold.containsKey(player)) playerDiseases?.add(Disease.COLD)
        if(d.wound.containsKey(player)) playerDiseases?.add(Disease.WOUND)
        if(d.choking.containsKey(player)) playerDiseases?.add(Disease.CHOKING)
        if(d.brokenLeg.containsKey(player)) playerDiseases?.add(Disease.BROKEN_LEG)

        return playerDiseases
    }

    fun checkSeverity(player: String, disease: HashMap<String, Int>): Int {

        var severity = disease.get(player)!!

        return severity
    }

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