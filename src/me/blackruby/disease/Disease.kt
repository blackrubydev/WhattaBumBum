package me.blackruby.disease

import me.blackruby.disease.types.Diseases
import me.blackruby.disease.types.Time
import org.bukkit.Bukkit
import org.bukkit.entity.Entity


class Disease {

    /**
     *  Cold: Megfázás, éjjel nagyobb esély van a megfázásra, a tünetek köhögés, szédülés. Antibiotikummal kezelhető.
     *  ha nincs kezelve magától elmúlik egy idő után.
     *
     *  Would: Blockot kézzel ütve sebesülhet meg a kéz, ez kötéssel orvosolható, gyenge marad a beteg és nem tud
     *  blockot kiütni.
     *
     *  Broken Leg: Magasról ugorva eltörhet a lába a betegnek, ez sínnel orvosolható. A beteg belassul, és lassú
     *  marad egészen addig, ameddig meg nem gyógyul.
     *
     *  Choking: Evés vagy ivás közben félrenyelhet a beteg, ha nem üti meg valaki, akkor megfullad.
     */
    var p = Player()

    var cold: HashMap<String, Int> = HashMap()
    var wound: HashMap<String, Int> = HashMap()
    var brokenLeg: HashMap<String, Int> = HashMap()
    var choking: HashMap<String, Int> = HashMap()

    fun addPlayer(player: String, diseaseType: Diseases) {

        when (diseaseType) {
            Diseases.COLD -> cold.put(player, 1)
            Diseases.WOUND -> wound.put(player ,1)
            Diseases.BROKEN_LEG -> brokenLeg.put(player, 1)
            Diseases.CHOKING -> choking.put(player, 1)
        }

    }

    fun removePlayer(player: String, diseaseType: Diseases) {

        when (diseaseType) {
            Diseases.COLD -> cold.remove(player)
            Diseases.WOUND -> wound.remove(player)
            Diseases.BROKEN_LEG -> brokenLeg.remove(player)
            Diseases.CHOKING -> choking.remove(player)
        }

    }

    fun checkTime(world: String?): Time {

        if(Bukkit.getWorld(world!!)!!.time < 12300 || Bukkit.getWorld(world)!!.time > 23850) {
            return Time.NIGHT
        }
        return Time.DAY
    }

    fun isContagious(disease: Diseases): Boolean {

        when(disease) {
            Diseases.COLD -> return true
        }

        return false
    }

    private fun contract(chance: Int): Boolean {

        if(chance == 0) return false

        var random = (1..(chance + 1)).random()
        if (random == 1) {
            return true
        }

        return false
    }

    fun infectPlayerNearby(player: org.bukkit.entity.Player?, diseaseType: Diseases, chance: Int) {

        var nearby: List<Entity>? = player?.getNearbyEntities(5.0, 5.0, 5.0)

        if(isContagious(diseaseType) != true) return
        if(nearby == null) return

        for(entity in nearby) {

            if(entity !is org.bukkit.entity.Player) return
            if(contract(chance) != true) return

            addPlayer(entity.name, diseaseType)

        }

    }

}