package DAO;

import Model.Decoration;

public interface DecorationDAO {

    public Decoration getDecorationFromId(Long decoId);

    public Decoration getDecorationFromDecoName(String decoName);

    public Decoration getDecorationFromSkillName(String skillName);

}
