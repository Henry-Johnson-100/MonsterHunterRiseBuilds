package DAO;

import Model.Armor;

public interface ArmorDAO {

    public Armor getArmorFromId(Long armor_id);

    public Armor getArmorFromName(String armor_name);

}
