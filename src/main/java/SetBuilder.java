import DAO.ArmorDAO;
import DAO.DecorationDAO;
import DAO.JDBCArmorDAO;
import DAO.SkillDAO;
import Model.Armor;
import Model.Skill;

import java.util.ArrayList;
import java.util.List;

public class SetBuilder {
    private Armor head;
    private Armor chest;
    private Armor arms;
    private Armor waist;
    private Armor legs;
    private final List<Skill> skillSearchList;
    private final ArmorDAO armorDAO;
    private final SkillDAO skillDAO;
    private final DecorationDAO decoDAO;


    public SetBuilder(ArmorDAO armorDAO, SkillDAO skillDAO, DecorationDAO decoDAO, List<String> skillNames) {
        this.head = null;
        this.chest = null;
        this.arms = null;
        this.waist = null;
        this.legs = null;
        this.armorDAO = armorDAO;
        this.skillDAO = skillDAO;
        this.decoDAO = decoDAO;
        this.skillSearchList = getSkillSearchListFromNames(skillNames);
    }

    private List<Skill> getSkillSearchListFromNames(List<String> skillNames) {
        List<Skill> tempSkillList = new ArrayList<>();
        for (String skillName : skillNames) {
            tempSkillList.add(this.skillDAO.getSkillFromName(skillName));
        }
        return tempSkillList;
    }

    private List<String> getOccupiedPieceTypes() {
        List<String> occupiedPieceTypes = new ArrayList<>();
        for (Armor piece : new Armor[]{this.head, this.chest, this.arms, this.waist, this.legs}) { //TODO make this a hashset instead of doing this gross
            if (piece != null) {
                occupiedPieceTypes.add(piece.getPieceType());
            }
        }
        return occupiedPieceTypes;
    }

    private void assignArmorPiece(Armor armorPiece) {
        switch (armorPiece.getPieceType()) {
            case "HEAD" -> this.head = armorPiece;
            case "CHEST" -> this.chest = armorPiece;
            case "ARMS" -> this.arms = armorPiece;
            case "WAIST" -> this.waist = armorPiece;
            case "LEGS" -> this.legs = armorPiece;
        }
    }

    private void searchForArmorPiece() {
        //TODO finish this method, and probably the stored procedures that go before it
        Long armorId = -1L;
        Armor newPiece = this.armorDAO.getArmorFromId(armorId);
        newPiece.setSkills(this.skillDAO.getSkillsFromArmorId(armorId));
        this.assignArmorPiece(newPiece);
    }

    public void generateArmorSet() { //TODO think about it if this is void or if it should return a set object
        while (this.getOccupiedPieceTypes().size() <= 5) {
            this.searchForArmorPiece(); //if all goes well this will return a distinct piece to fit in a slot, will have to run some calculations in between queries though so this is really a big oversimplification
        }
    }


}
