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

    /**
     *Set a skill's level. Does not allow skills to be set outside of their maximum range.
     * @param skillLevel -> The level to set the skill to.
     * @return -> 0 if the skill level is set appropriately.
     *          -> 1 if the skill level is set too high.
     *          -> -1 if the skill level is set too low.
     */
    public int setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
        if (this.skillLevel > this.maxSkillLevel) {
            this.skillLevel = this.maxSkillLevel;
            return 1;
        }
        if (this.skillLevel < 0) {
            this.skillLevel = 0;
            return -1;
        }
        return 0;
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

    @Override
    public String toString() {
        return "Name: " + this.skillName + "\n" +
                "Description: " + this.skillDescription + "\n" +
                "Skill Level: " + this.skillLevel + "/" + this.maxSkillLevel + "\n";
    }
}
