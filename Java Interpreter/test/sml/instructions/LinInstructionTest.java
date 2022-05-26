package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Translator;

class LinInstructionTest {
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
    var ins = new LinInstruction("lin", 5, 5);
    int expected = 5;
    ins.execute(m);
    var actual = regs.getRegister(5);
    Assertions.assertNotNull(regs.getRegister(5));
    Assertions.assertEquals(expected, actual);
    tearDown();
  }

  @Test
  void execute2() {
    //check overwriting of current number in register
    setUp();
    regs.setRegister(1, 6);
    regs.setRegister(31, -16);
    var ins = new LinInstruction("lin", 1, 31);
    int expected = 31;
    ins.execute(m);
    var actual = regs.getRegister(1);
    //no registers 0-31 should be null all should be at least 0
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertEquals(expected, actual);
    tearDown();

  }

  @Test
  void execute3() {
    //test exception thrown for inputs outside accepted register numbers
    setUp();
    regs.setRegister(4, 6);
    Exception exception = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      var ins = new LinInstruction("lin", 35, 3);
      ins.execute(m);
    });
    var actualException = exception.getMessage();
    var expectedMessage = "Index 35 out of bounds for length 32";

    //no registers 0-31 should be null all should be at least 0
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertTrue(actualException.contains(expectedMessage));
    tearDown();

  }

  @Test
  void executeNotEqual() {
    //check correct number and registers are being loaded
    setUp();
    regs.setRegister(0, 60);
    regs.setRegister(31, 10);
    var ins = new LinInstruction("lin", 0, 31);
    int unexpected = 10;
    ins.execute(m);
    var actual = regs.getRegister(0);
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertNotNull(regs.getRegister(0));
    Assertions.assertNotEquals(unexpected, actual);
    tearDown();
  }

  @Test
  void testToString() {
    setUp();
    var ins = new LinInstruction("lin", 5, 2);
    ins.setOpcode("1st");
    var expected = "1st: load the contents of register 2 into register 5";
    var actual = ins.toString();
    Assertions.assertEquals(expected, actual);
  }


}