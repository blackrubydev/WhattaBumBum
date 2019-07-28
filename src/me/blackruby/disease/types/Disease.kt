package me.blackruby.disease.types

enum class Disease {

    /**
     *  COLD:
     *
     *  (Checking player position every second) If the player is out in the open (PlayerManager.kt - checkPosition(), ln: 42-50)
     *  and it is currently nighttime in the player's world (DiseaseManager.kt - checkTime(), ln: 40-46) there is a chance to get
     *  cold (DiseaseManager - contract(), ln: 54-60). It is contagious, so a player with this illness can infect others as it is
     *  defined in (DiseaseManager - infectNearbyPlayers(), ln: 62-76).
     *
     *  WOUND:
     *
     *  (Event listener) Every time a player breaks a block with bare hand, there is a slight chance (DiseaseManager - contract(), ln: 54-60) of getting
     *  a wound.
     *
     *  BROKEN LEG:
     *
     *  (Event Listener) Every time a player takes fall damage there is a chance (DiseaseManager - contract(), ln: 54-60) for their leg to injure.
     *
     *  CHOKING:
     *
     *  (Event Listener) Every time a player eats there is a chance (DiseaseManager - contract(), ln: 54-60) to choke on the food.
     */

    COLD, CHOKING, WOUND, BROKEN_LEG, NON
}