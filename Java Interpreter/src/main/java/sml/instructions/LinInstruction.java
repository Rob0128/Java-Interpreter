package sml.instructions;

import sml.Instruction;
import sml.Machine;


public class LinInstruction extends Instruction {

    private String label;
    private int register;
    private int s1;

    public LinInstruction() {
        super("","lin");

    }

    public LinInstruction(String label, int register, int s1) {
        super(label, "lin");
        this.register = register;
        this.s1 = s1;

    }

    @Override
    public void execute(Machine m) {
        var regs = m.getRegisters();
        regs.setRegister(register, s1);
    }

    public void setAll(Object[] nums) {
        this.register = ((int) nums[0]);
        this.s1 = ((int) nums[1]);
    }

    public void setS1() {

    }

    @Override
    public String toString() {
        return getOpcode() + ": " + "load " + s1 + " into register " + register;
    }
}
