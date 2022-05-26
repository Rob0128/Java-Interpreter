package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import sml.Machine;
import sml.Registers;
import sml.Translator;

public class BnzInstructionTest {

    private Machine m;
    private Translator t;
    Registers regs;

    @BeforeEach
    void setUp() {
        m = new Machine();
        m.setRegisters(new Registers());
        regs = m.getRegisters();

    }

    @AfterEach
    void tearDown() {
        m.setRegisters(new Registers());
    }


    void execute() {
        setUp();
        regs.setRegister(0, 11);
        regs.setRegister(1, 10);
        var ins = new BnzInstruction("bnz", 1, "ins");
        int expected = 0;
        ins.execute(m);
        var actual = m.getPc();
        Assertions.assertEquals(expected, actual);
        tearDown();

    }

    void testToString() {
        setUp();
        var ins = new BnzInstruction("bnz", 2, "l1");
        ins.setOpcode("BnzIns");
        var expected = "BnzIns: Jump to instruction l1";
        var actual = ins.toString();
        Assertions.assertEquals(expected, actual);
    }


}
