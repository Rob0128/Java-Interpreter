package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Translator;

class SubtractInstructionTest {
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

  @Test
  void execute() {
    setUp();
    regs.setRegister(0, 11);
    regs.setRegister(1, 10);
    var ins = new SubInstruction("sub", 2, 0, 1);
    int expected = 1;
    ins.execute(m);
    var actual = regs.getRegister(2);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();


  }
  @Test
  void execute2() {
    setUp();
    regs.setRegister(0, 250);
    regs.setRegister(1, 10);
    var ins = new SubInstruction("sub", 2, 0, 1);
    int expected = 240;
    ins.execute(m);
    var actual = regs.getRegister(2);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void execute3() {
    //test negative result
    setUp();
    regs.setRegister(23, 29);
    regs.setRegister(1, 10);
    var ins = new SubInstruction("sub", 9, 1, 23);
    int expected = -19;
    ins.execute(m);
    var actual = regs.getRegister(9);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }


  @Test
  void testToString() {
    setUp();
    var ins = new SubInstruction("sub", 9, 23, 1);
    ins.setOpcode("minusIns");
    var expected = "minusIns: sub 23 - 1 and load into 9";
    var actual = ins.toString();
    Assertions.assertEquals(expected, actual);
  }


}