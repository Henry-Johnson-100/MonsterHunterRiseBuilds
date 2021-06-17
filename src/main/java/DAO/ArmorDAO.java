package DAO;

import Model.Armor;
import Model.Skill;

import java.util.List;

public interface ArmorDAO {

    public Armor getArmorFromId(Long armorId);

    public Armor getArmorFromName(String armorName);

    public Armor getOptimalArmorFromSkills(List<Skill> searchSkills, String[] excludePieceTypes);

}
