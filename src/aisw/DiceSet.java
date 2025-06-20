package aisw;

public class DiceSet {
    private Dice[] dices = new Dice[5];

    public DiceSet() {
        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Dice();
        }
    }

    public void rollAll() {
        for (Dice dice : dices) {
            dice.roll();
        }
    }

    public void rollSelected(boolean[] toRoll) {
        for (int i = 0; i < dices.length; i++) {
            if (toRoll[i]) dices[i].roll();
        }
    }

    public int[] getValues() {
        int[] values = new int[5];
        for (int i = 0; i < dices.length; i++) {
            values[i] = dices[i].getValue();
        }
        return values;
    }

    public void printDices() {
        int[] values = getValues();
        System.out.print("주사위: ");
        for (int i = 0; i < values.length; i++) {
            System.out.print("[" + values[i] + "] ");
        }
        System.out.println();
    }
}
