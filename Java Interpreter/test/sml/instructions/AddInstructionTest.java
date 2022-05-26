package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Translator;


import static org.junit.jupiter.api.Assertions.*;

class AddInstructionTest {
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
    regs.setRegister(0, 10);
    regs.setRegister(1, 10);
    var ins = new AddInstruction("add", 2, 0, 1);
    int expected = 20;
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
    var ins = new AddInstruction("add", 2, 0, 1);
    int expected = 260;
    ins.execute(m);
    var actual = regs.getRegister(2);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void execute3() {
    //test negative numbers
    setUp();
    regs.setRegister(23, -29);
    regs.setRegister(1, 10);
    var ins = new AddInstruction("add", 9, 23, 1);
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
    var ins = new AddInstruction("add", 9, 23, 1);
    ins.setOpcode("i1");
    var expected = "i1: add 23 + 1 and load into 9";
    var actual = ins.toString();
    Assertions.assertEquals(expected, actual);
  }


}