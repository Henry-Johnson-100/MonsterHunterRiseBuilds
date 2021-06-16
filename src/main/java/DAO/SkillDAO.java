package DAO;

import Model.Skill;

import java.util.List;

public interface SkillDAO {

    public Skill getSkillFromId(Long skillId);

    public Skill getSkillFromName (String skillName);

    public List<Skill> getSkillsFromArmorId(Long armorId);

    public List<Skill> getSkillsFromArmorName(String armorName);
}
