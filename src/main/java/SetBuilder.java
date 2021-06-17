import DAO.ArmorDAO;
import DAO.DecorationDAO;
import DAO.SkillDAO;
import Model.Armor;
import Model.Skill;

import java.util.*;

public class SetBuilder {
    private Map<String, Armor> setMap;
    private List<Skill> skillSearchList; //Needs to stay as a list because the order matters
    private final ArmorDAO armorDAO;
    private final SkillDAO skillDAO;
    private final DecorationDAO decoDAO;


    public SetBuilder(ArmorDAO armorDAO, SkillDAO skillDAO, DecorationDAO decoDAO, List<String> skillNames) {
        this.setMap = new HashMap<>();
        this.armorDAO = armorDAO;
        this.skillDAO = skillDAO;
        this.decoDAO = decoDAO;
        this.skillSearchList = getSkillSearchListFromNames(skillNames);
    }

    private List<Skill> getSkillSearchListFromNames(List<String> skillNames) {
        List<Skill> tempSkillList = new ArrayList<>();
        Skill tempSkill;
        for (String skillName : skillNames) {
            tempSkill = this.skillDAO.getSkillFromName(skillName);
            tempSkill.setSkillLevel(0); //initialize all search skill levels to zero
            tempSkillList.add(tempSkill);
        }
        return tempSkillList;
    }

    private Set<String> getOccupiedPieceTypes() {
        return new HashSet<>(this.setMap.keySet());
    }

    private String[] getOccupiedPieceTypeArray() {
        String[] typeArray = new String[]{"", "", "", "", ""};
        int i = 0;
        for (String type: this.getOccupiedPieceTypes()) {
            typeArray[i] = type;
            i++;
        }
        return typeArray;
    }

    private boolean isMaxLevel(Skill searchSkill) {
        if (searchSkill.getSkillLevel() == searchSkill.getMaxSkillLevel()) {
            return true;
        }
        return false;
    }

    /**
     * For the skills in the list skillSearchList, updates their levels with the armor's skills passed as an arg.
     * Will remove a skill from skillSearchList if its maximum skill is reached or exceeded.
     * @param armorSkillMap
     */
    private void updateSearchSkillLevels(Map<String, Skill> armorSkillMap) {
        List<Skill> removeList = new ArrayList<>();
        for (Skill searchSkill : this.skillSearchList) {
            if (!armorSkillMap.containsKey(searchSkill.getSkillName())) {
                continue;
            }
            searchSkill.setSkillLevel(searchSkill.getSkillLevel() + armorSkillMap.get(searchSkill.getSkillName()).getSkillLevel());
            if(isMaxLevel(searchSkill)) {
                removeList.add(searchSkill);
            }
        }
        this.skillSearchList.removeAll(removeList);
    }

    private void assignArmorPiece(Armor armorPiece) {
        switch (armorPiece.getPieceType()) {
            case "HEAD" -> this.setMap.put("HEAD", armorPiece);
            case "CHEST" -> this.setMap.put("CHEST", armorPiece);
            case "ARMS" -> this.setMap.put("ARMS", armorPiece);
            case "WAIST" -> this.setMap.put("WAIST", armorPiece);
            case "LEGS" -> this.setMap.put("LEGS", armorPiece);
        }
    }

    private void searchForArmorPiece() {
        Armor newPiece = this.armorDAO.getOptimalArmorFromSkills(this.skillSearchList,getOccupiedPieceTypeArray());
        newPiece.setSkills(this.skillDAO.getSkillsFromArmorId(newPiece.getArmorId()));
        this.assignArmorPiece(newPiece);
        updateSearchSkillLevels(newPiece.getSkills());
    }
    //TODO work on stored procedures. Need to think about adding 'AND armor_piece_type NOT IN []' and 'AND ...' (skill level + current searchSkill level < max Skill Level) to fully optimize the searches

    public void generateArmorSet() { //TODO think about it if this is void or if it should return a set object
        while (this.getOccupiedPieceTypes().size() < 5) {
            this.searchForArmorPiece(); //if all goes well this will return a distinct piece to fit in a slot, will have to run some calculations in between queries though so this is really a big oversimplification
        }
    }

    @Override
    public String toString() {
        return "SetBuilder{" +
                "setMap=" + setMap +
                '}';
    }
}
