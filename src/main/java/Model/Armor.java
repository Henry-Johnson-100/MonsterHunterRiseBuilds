package Model;

import java.util.*;

public class Armor {
    private Long armorId;
    private String armorName;
    private String pieceType;
    private int baseDefense;
    private List<Integer> decorationSlots;
    private List<Integer> resistances;
    private List<Skill> skills;
    private Map<String, Skill> skillMap;
    private List<Decoration> decorations;

    public Armor(Long armorId, String armorName,String pieceType, int baseDefense, List<Integer> decorationSlots, List<Integer> resistances) {
        this.armorId = armorId;
        this.armorName = armorName;
        this.pieceType = pieceType;
        this.baseDefense = baseDefense;
        this.decorationSlots = decorationSlots; //TODO make a method to generate a deco map depending on the number of slots
        this.resistances = resistances;
        this.skillMap = new HashMap<>();
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

    public Map<String,Skill> getSkills() {
        return skillMap;
    }

    public void setSkills(List<Skill> skills) {
        for (Skill elem : skills) {
            this.skillMap.put(elem.getSkillName(), elem);
        }
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String skillMapToString() {
        StringBuilder returnStr = new StringBuilder("Skills:\n");
        for (Map.Entry<String,Skill> kvp:this.skillMap.entrySet()) {
            returnStr.append(kvp.getValue().toString()).append("\n");
        }
        return returnStr.toString();
    }

    @Override
    public String toString() {
        return "Name: " + this.armorName + "\n" +
                "Base Defense: " + this.baseDefense + "\n" +
                "Decoration Slots: " + this.decorationSlots.toString() + "\n" +
                skillMapToString() + "\n"
                ;
    }
}
