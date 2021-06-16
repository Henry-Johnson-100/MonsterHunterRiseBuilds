package Model;

import java.util.List;

public class Armor {
    private Long armorId;
    private String armorName;
    private int baseDefense;
    private List<Integer> decorationSlots;
    private List<Integer> resistances;
    private List<Skill> skills;
    private List<Decoration> decorations;

    public Armor(Long armorId, String armorName, int baseDefense, List<Integer> decorationSlots, List<Integer> resistances) {
        this.armorId = armorId;
        this.armorName = armorName;
        this.baseDefense = baseDefense;
        this.decorationSlots = decorationSlots;
        this.resistances = resistances;
    }

    public Long getArmorId() {
        return armorId;
    }

    public void setArmorId(Long armorId) {
        this.armorId = armorId;
    }

    public String getArmorName() {
        return armorName;
    }

    public void setArmorName(String armorName) {
        this.armorName = armorName;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public List<Integer> getDecorationSlots() {
        return decorationSlots;
    }

    public void setDecorationSlots(List<Integer> decorationSlots) {
        this.decorationSlots = decorationSlots;
    }

    public List<Integer> getResistances() {
        return resistances;
    }

    public void setResistances(List<Integer> resistances) {
        this.resistances = resistances;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }

    @Override
    public String toString() {
        return "Armor{" +
                "armorId=" + armorId +
                ", armorName='" + armorName + '\'' +
                ", baseDefense=" + baseDefense +
                ", decorationSlots=" + decorationSlots +
                ", resistances=" + resistances +
                ", skills=" + skills +
                ", decorations=" + decorations +
                '}';
    }
}
