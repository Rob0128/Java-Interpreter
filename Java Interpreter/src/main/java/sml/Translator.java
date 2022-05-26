package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.instructions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.ApplicationContext;



/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    public Translator(String file) {
        fileName = PATH + file;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(Labels lab, List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), "UTF-8")) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (label.length() > 0) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction
    public Instruction getInstruction(String label) throws Exception {
        int s1; // Possible operands of the instruction
        int s2;
        int r;
//        String lbl;
        Class c;

        if (line.equals("")) {
            return null;
        }
        var opCode = scan();


        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Instruction inst = (Instruction) context.getBean(opCode);

        var thisClass = inst.getClass();
        var constructors = thisClass.getConstructors();
//        for (var constructor: constructors) {
//            System.out.println(constructor);
//        }


//        var cc = Class.forName(inst.getOpcode());
//        System.out.println(inst.getOpcode());
//        System.out.println(cc);
//        Instruction inst = (Instruction) factory.getBean(opCode);



        //transform opcode to classname
        //Instruction returnIns = getInstructionClass(opcode).getInstance().
        String InstructionString = opCode + "Instruction";
        String CapInstructionString = InstructionString.substring(0, 1).toUpperCase() + InstructionString.substring(1);
        String FinalPathInstruction = "sml.instructions." + CapInstructionString;


        try {
            c = inst.getClass();
            var cons = c.getConstructors();
            var paramVals = new ArrayList<>();
            for (var con : cons) {
                if (con.getParameterCount() > 0) {
                    var parms = con.getParameters();
                    for (var p : parms){
//                        System.out.println(p.getAnnotatedType().getType().getTypeName());
                        if (p.getType().equals(Integer.TYPE) || p.getAnnotatedType().getType().getTypeName().equals("int") ){
                            int temp = scanInt();
                            paramVals.add(temp);
                        }
                        else if (opCode.equals("bnz") && p.getAnnotatedType().getType().getTypeName().equals("java.lang.String")){
                            String temp = scan();
//                            System.out.println(temp);
                            paramVals.add(temp);
                        }
                    }
                }
            }
            var x = cons[0].getParameterTypes();

                //getParameterCount
                var count = cons[0].getParameterCount();

//                paramVals.add(label);

                for (var s : x) {
//                    System.out.println(s.getTypeName());
                    if (s.getTypeName().equals("int")) {
                        int temp = scanInt();
                        paramVals.add(temp);
//                        System.out.println(temp);
                    } else if (s.getTypeName() == "java.lang.String") {
                        String temp = scan();
                        paramVals.add(temp);
                    }

                }

                Object[] finalArgs = paramVals.toArray();
//            System.out.println(finalArgs);
                inst.setAll(finalArgs);
                Object retIns = cons[0].newInstance();
//                for (var gg : finalArgs){
//                    System.out.println(gg);
//                }
//            System.out.println(finalArgs);
               return inst;

        }catch(Exception e){
            System.out.println(e);
        }

//        switch (opCode) {
//            case "add" -> {
//                r = scanInt();
//                s1 = scanInt();
//                s2 = scanInt();
//                return new AddInstruction(label, r, s1, s2);
//            }
//            case "sub" -> {
//                r = scanInt();
//                s1 = scanInt();
//                s2 = scanInt();
//                return new SubtractInstruction(label, r, s1, s2);
//            }
//            case "mul" -> {
//                r = scanInt();
//                s1 = scanInt();
//                s2 = scanInt();
//                return new MulInstruction(label, r, s1, s2);
//            }
//            case "div" -> {
//                r = scanInt();
//                s1 = scanInt();
//                s2 = scanInt();
//                return new DivInstruction(label, r, s1, s2);
//            }
//            case "lin" -> {
//                r = scanInt();
//                s1 = scanInt();
//                return new LinInstruction(label, r, s1);
//            }
//            case "out" -> {
//                r = scanInt();
//                return new OutInstruction(label, r);
//            }
//            // TODO: You will have to write code here for the other instructions.
//
//            default -> {
//                System.out.println("Unknown instruction: " + opCode);
//            }
//        }
        return inst; // FIX THIS
    }

//    private static BeanFactory getBeanFactory() throws Exception {
//
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//
//        PropertiesBeanDefinitionReader rdr = new PropertiesBeanDefinitionReader(factory);

//        Properties props = new Properties();
//        File f = new File("beans");
//        System.out.println(f.exists());
//        try (var fis = new FileInputStream("beans")){
//            props.load(fis);
//        }

//        rdr.registerBeanDefinitions(props);

//        return factory;
//    }

    /*
     * Return the first word of line and remove it from line. If there is no word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}