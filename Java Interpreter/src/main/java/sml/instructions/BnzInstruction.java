package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class BnzInstruction extends Instruction{

    private String label;
    private int s1;
    private String newIns;

    public BnzInstruction(String label, int s1, String newInstruction){
        super(label, "bnz");
        this.s1 = s1;
        this.newIns = newInstruction;

    }

    public BnzInstruction() {
        super("","bnz");

    }

    @Override
    public void execute(Machine m) {
        // TODO: Implement

        if (s1 != 0) {
//            var regs = m.getRegisters();
            System.out.println(newIns);
            var progCounter = m.getProg().indexOf(newIns);
            m.setPc(progCounter);
        }

    }

    @Override
    public void setAll(Object[] nums) {
        this.s1 = ((int) nums[0]);
        this.newIns = ((String) nums[1]);

    }

    @Override
    public String toString() {
        return getOpcode() + ": " + getLabel() + " Jump to instruction " + s1;
    }

}

