package Model;

public class Decoration {
    private final Long decoId;
    private final Long skillId;
    private final String decoName;
    private final int slotLevel;

    public Decoration(Long decoId, Long skillId, String decoName, int slotLevel) {
        this.decoId = decoId;
        this.skillId = skillId;
        this.decoName = decoName;
        this.slotLevel = slotLevel;
    }

    public Long getDecoId() {
        return decoId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public String getDecoName() {
        return decoName;
    }

    public int getSlotLevel() {
        return slotLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return ((Decoration) obj).getDecoName().equals(this.getDecoName());
    } //have no idea if this will actually work

    @Override
    public String toString() {
        return "Decoration{" +
                "decoId=" + decoId +
                ", skillId=" + skillId +
                ", decoName='" + decoName + '\'' +
                ", slotLevel=" + slotLevel +
                '}';
    }
}
