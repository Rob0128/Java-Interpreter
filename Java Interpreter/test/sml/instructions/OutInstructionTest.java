package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;
import sml.Translator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class OutInstructionTest {
  private Machine m;
  private Translator t;
  Registers regs;

  //sout specific setup
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
    //sout specific setup
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    m.setRegisters(new Registers());
    //sout specific teardown
    System.setOut(standardOut);
  }

  @Test
  void execute() {
    setUp();
    regs.setRegister(5, 6);
    var ins = new OutInstruction("out", 5);
    ins.execute(m);
    Assertions.assertEquals("6", outputStreamCaptor.toString().trim());
    tearDown();
  }

  @Test
  void execute2() {
//  operate empty register
    setUp();
    var ins = new OutInstruction("out", 28);
    ins.execute(m);
    Assertions.assertEquals("0", outputStreamCaptor.toString().trim());
    tearDown();
  }

  @Test
  void execute3() {
    //test exception thrown for inputs outside accepted register numbers
    setUp();
    Exception exception = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      var ins = new OutInstruction("out", 102);
      ins.execute(m);
    });
    var actualException = exception.getMessage();
    System.out.println(actualException);
    var expectedMessage = "Index 102 out of bounds for length 32";
    //no registers 0-31 should be null all should be at least 0
    Assertions.assertNotNull(regs.getRegister(31));
    Assertions.assertTrue(actualException.contains(expectedMessage));
    tearDown();
  }

  @Test
  void executeNotEqual() {
    // operate on same register
    setUp();
    regs.setRegister(5, 6);
    regs.setRegister(5, 10);
    var ins = new OutInstruction("out", 5);
    ins.execute(m);
    Assertions.assertNotEquals("6", outputStreamCaptor.toString().trim());
    tearDown();

  }

  @Test
  void testToString() {
    setUp();
    regs.setRegister(31, -20);
    var ins = new OutInstruction("out", 31);
    ins.setOpcode("printIns");
    var expected = "printIns: print out the contents of register 31";
    var actual = ins.toString();
    Assertions.assertEquals(expected, actual);
  }


}