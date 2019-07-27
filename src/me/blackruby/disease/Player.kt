package me.blackruby.disease

import me.blackruby.disease.types.Diseases
import me.blackruby.disease.types.Position
import org.bukkit.Material
import org.bukkit.entity.Player

class Player {

    var d = Disease()

    fun increaseDiseaseSeverity(player: String, diseaseType: Diseases) {

        when (diseaseType) {
            Diseases.COLD -> d.cold.put(player, d.cold.get(player)!! + 1)
            Diseases.WOUND -> d.wound.put(player, d.wound.get(player)!! + 1)
            Diseases.BROKEN_LEG -> d.brokenLeg.put(player, d.brokenLeg.get(player)!! + 1)
            Diseases.CHOKING -> d.choking.put(player, d.choking.get(player)!! + 1)
        }

    }

    fun checkDisease(player: String): ArrayList<Diseases>? {

        var playerDiseases: ArrayList<Diseases>? = ArrayList()

        if(d.cold.containsKey(player)) playerDiseases?.add(Diseases.COLD)
        if(d.wound.containsKey(player)) playerDiseases?.add(Diseases.WOUND)
        if(d.choking.containsKey(player)) playerDiseases?.add(Diseases.CHOKING)
        if(d.brokenLeg.containsKey(player)) playerDiseases?.add(Diseases.BROKEN_LEG)

        return playerDiseases
    }

    fun checkSeverity(player: String, disease: HashMap<String, Int>): Int {

        var severity = disease.get(player)!!

        return severity
    }

    fun checkPermission(player: Player, permission: String, negate: Boolean) {

        when (negate) {
            true -> if(!player.hasPermission(permission)) return
            false -> if(player.hasPermission(permission)) return
        }

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