package DAO;

import Model.Armor;

public interface ArmorDAO {

    public Armor getArmorFromId(Long armorId);

    public Armor getArmorFromName(String armorName);

}
