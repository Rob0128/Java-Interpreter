package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class OutInstruction extends Instruction {

    private String label;
    private int register;


    public OutInstruction(String label, int register) {
        super(label, "out");
        this.register = register;

    }

    public OutInstruction() {
        super("","out");

    }

    @Override
    public void execute(Machine m) {
        var regs = m.getRegisters();
        var printReg = regs.getRegister(register);
        System.out.println(printReg);
    }

    public void setAll(Object[] nums) {
        this.register = ((int) nums[0]);
    }

    @Override
    public String toString() {
        return getOpcode() + ": " + "print out the contents of register " + register;
    }
}
