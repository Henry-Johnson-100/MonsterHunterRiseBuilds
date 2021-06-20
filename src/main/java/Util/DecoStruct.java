package Util;

import Model.Decoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DecoStruct {
    private final int lv1Slots;
    private final int lv2Slots;
    private final int lv3Slots;
    private List<Decoration> lv1Decos;
    private List<Decoration> lv2Decos;
    private List<Decoration> lv3Decos;

    private static final int MAX_DECO_LEVEL = 3;

    public DecoStruct(int lv1Slots, int lv2Slots, int lv3Slots) {
        this.lv1Slots = lv1Slots;
        this.lv2Slots = lv2Slots;
        this.lv3Slots = lv3Slots;
        initAllDecoLists();
    }

    public DecoStruct(List<Integer> decoSlots) {
        this.lv1Slots = decoSlots.get(0);
        this.lv2Slots = decoSlots.get(1);
        this.lv3Slots = decoSlots.get(2);
        initAllDecoLists();
    }

    public void initAllDecoLists() {
        this.lv1Decos = initLvDecoList(this.lv1Slots);
        this.lv2Decos = initLvDecoList(this.lv2Slots);
        this.lv3Decos = initLvDecoList(this.lv3Slots);
    }

    public boolean set(Decoration deco) {
        int decoLevel = deco.getSlotLevel();
        List<Decoration> decoList = new ArrayList<>(); //I think since lists are mutable I can do this

        while (decoLevel <= MAX_DECO_LEVEL) {
            decoList = this.getLvDecoList(decoLevel);

            if (decoList.contains(null)) {
                decoList.remove(null);
                decoList.add(deco);
                return true;
            }

            decoLevel++;
        }

        return false; //deco could not be added
    }

    public boolean set(Decoration deco, int slotLevel) {
        List<Decoration> decoList = this.getLvDecoList(slotLevel);

        if (decoList.contains(null)) {
            decoList.remove(null);
            decoList.add(deco);
            return true;
        }

        return false; //deco could not be added
    }

    public boolean unset(Decoration deco) {
        int decoLevelFloor = deco.getSlotLevel();
        int decoLevel = MAX_DECO_LEVEL;
        List<Decoration> decoList;

        while (decoLevel >= decoLevelFloor) {//Want to remove decorations from the highest level slots first
            decoList = this.getLvDecoList(decoLevel);

            if (decoList.contains(deco)) {
                decoList.remove(deco);
                decoList.add(null);
                return true;
            }

            decoLevel--;
        }

        return false;
    }

    public boolean unset(int slotLevel) { //Unset the first deco in a given slot, indiscriminately. Returns true if deco was removed, false if no decos were removed.
        return this.getLvDecoList(slotLevel).remove(0) == null;
    }

    @Override
    public String toString() {
        StringBuilder returnStr = new StringBuilder("Decorations").append("\n");
        String tempDecoName;

        for (List<Decoration> decoList : Arrays.asList(this.lv1Decos, this.lv2Decos, this.lv3Decos)) {

            for (Decoration deco : decoList) {

                if (deco == null) {
                    tempDecoName = "_____";//Don't really like this but w/e for now
                } else {
                    tempDecoName = deco.getDecoName();
                }

                returnStr.append("\n\t").append(tempDecoName);
            }

        }

        return returnStr.toString();
    }

    private List<Decoration> initLvDecoList(int emptySlots) {//The size of any given lvDecoList should always == lvSlots, so they are filled with null values if not Decorations
        List<Decoration> tempDecoList = new ArrayList<>();

        for (int i = 0; i < emptySlots; i++) {
            tempDecoList.add(null);
        }

        return tempDecoList;
    }


    private List<Decoration> getLvDecoList(int slotLevel) {
        return switch (slotLevel) {
            //Level 1 is taken care of by default
            case 2 -> this.lv2Decos;
            case 3 -> this.lv3Decos;
            default -> this.lv1Decos;
        };
    }


}
