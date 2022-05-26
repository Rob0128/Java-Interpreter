package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Translator;

class DivInstructionTest {
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
//    operate on same register
    setUp();
    regs.setRegister(5, 6);
    var ins = new DivInstruction("div", 3, 5, 5);
    int expected = 1;
    ins.execute(m);
    var actual = regs.getRegister(3);
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(3));
    Assertions.assertEquals(expected, actual);
    tearDown();


  }
  @Test
  void execute2() {
    setUp();
    regs.setRegister(0, 250);
    regs.setRegister(1, 10);
    var ins = new DivInstruction("div", 2, 0, 1);
    int expected = 25;
    ins.execute(m);
    var actual = regs.getRegister(2);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void execute3() {
    //test negative numbers and rounding
    setUp();
    regs.setRegister(23, 29);
    regs.setRegister(1, -10);
    var ins = new DivInstruction("div", 9, 1, 23);
    int expected = 0;
    ins.execute(m);
    var actual = regs.getRegister(9);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void execute4() {
    //test negative numbers
    setUp();
    regs.setRegister(23, 2);
    regs.setRegister(1, -10);
    var ins = new DivInstruction("div", 9, 1, 23);
    int expected = -5;
    ins.execute(m);
    var actual = regs.getRegister(9);
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(1));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void executeNotEqual() {
    setUp();
    regs.setRegister(0, 60);
    regs.setRegister(31, 10);
    var ins = new DivInstruction("div", 3, 0, 31);
    int unexpected = 0;
    ins.execute(m);
    var actual = regs.getRegister(3);
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotNull(regs.getRegister(3));
    Assertions.assertNotEquals(unexpected, actual);
    tearDown();
  }

  @Test
  void testToString() {
    setUp();
    var ins = new DivInstruction("div", 9, 21, 1);
    ins.setOpcode("3Rt");
    var expected = "3Rt: div 21 / 1 and load into 9";
    var actual = ins.toString();
    Assertions.assertEquals(expected, actual);
  }


}