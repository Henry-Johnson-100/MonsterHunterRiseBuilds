package Model;

public class Skill {
    private final Long skillId;
    private final String skillName;
    private final String skillDescription;
    private int skillLevel;
    private final int maxSkillLevel;

    public Skill(Long skillId, String skillName, String skillDescription, int maxSkillLevel) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.maxSkillLevel = maxSkillLevel;
        this.skillLevel = 1;
    }

    public Long getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getMaxSkillLevel() {
        return maxSkillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void incrSkillLevel() {
        if (this.skillLevel >= this.maxSkillLevel) {
            return;
        }
        this.skillLevel++;
    }

    public void decrSkillLevel() {
        if (this.skillLevel <= 0) {
            return;
        }
        this.skillLevel--;
    }
}
