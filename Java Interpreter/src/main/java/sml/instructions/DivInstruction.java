package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Add instruction from the language.
 *
 * @author ...
 */
public class DivInstruction extends Instruction {

    private String label;
    private int register;
    private int s1;
    private int s2;

    public DivInstruction(String label, int register, int s1, int s2){
        super(label, "div");
        this.register = register;
        this.s1 = s1;
        this.s2 = s2;

    }

    public DivInstruction() {
        super("","div");

    }

    @Override
    public void execute(Machine m) {
        // TODO: Implement

        var regs = m.getRegisters();
        var divNo = regs.getRegister(s1) / regs.getRegister(s2);
        regs.setRegister(register, divNo);

    }

    public void setAll(Object[] nums) {
        this.register = ((int) nums[0]);
        this.s1 = ((int) nums[1]);
        this.s2 = ((int) nums[2]);
    }


    @Override
    public String toString() {
        return getLabel()  + getOpcode() + " " +s1 + " / " + s2  + " and load into " + register;
    }

}

