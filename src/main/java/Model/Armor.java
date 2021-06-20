package Model;

import Util.DecoStruct;

import java.util.*;

public class Armor {
    private final Long armorId;
    private final String armorName;
    private final String pieceType;
    private final int baseDefense;
    private List<Integer> resistances;
    private Map<String, Skill> skillMap;
    private DecoStruct decorations;

    public Armor(Long armorId, String armorName, String pieceType, int baseDefense, List<Integer> decorationSlots, List<Integer> resistances) {
        this.armorId = armorId;
        this.armorName = armorName;
        this.pieceType = pieceType;
        this.baseDefense = baseDefense;
        this.resistances = resistances;
        this.skillMap = new HashMap<>();
        this.decorations = new DecoStruct(decorationSlots);
    }

    public Long getArmorId() {
        return armorId;
    }

    public String getArmorName() {
        return armorName;
    }


    public int getBaseDefense() {
        return baseDefense;
    }


    public List<Integer> getResistances() {
        return resistances;
    }

    public void setResistances(List<Integer> resistances) {
        this.resistances = resistances;
    }

    public Map<String, Skill> getSkills() {
        return skillMap;
    }

    public void initSkills(List<Skill> skills) {
        for (Skill elem : skills) {
            this.skillMap.put(elem.getSkillName(), elem);
        }
    }

    public String getPieceType() {
        return pieceType;
    }

    public DecoStruct getDecorations() {
        return decorations;
    }

    public boolean setDeco(Decoration deco) {
        return this.decorations.set(deco);
    }

    public boolean setDeco(Decoration deco, int slotLevel) {
        return this.decorations.set(deco,slotLevel);
    }

    public boolean unsetDeco(Decoration deco) {
        return this.decorations.unset(deco);
    }

    public boolean unsetDeco(int slotLevel) {
        return this.decorations.unset(slotLevel);
    }

    private String skillMapToString() {
        StringBuilder returnStr = new StringBuilder("Skills:\n");
        for (Map.Entry<String, Skill> kvp : this.skillMap.entrySet()) {
            returnStr.append(kvp.getValue().toString()).append("\n");
        }
        return returnStr.toString();
    }

    @Override
    public String toString() {
        return "Name: " + this.armorName + "\n" +
                "Base Defense: " + this.baseDefense + "\n" +
                skillMapToString() + "\n"
                ;
    }
}
