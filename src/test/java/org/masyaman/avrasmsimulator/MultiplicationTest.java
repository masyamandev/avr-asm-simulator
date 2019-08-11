package org.masyaman.avrasmsimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplicationTest {

    @Test
    public void testUnsignedMultiplicationSizeOptimized() {
        AsmParser asmParser = new AsmParser();
        Program program = asmParser.parse( "" +
                    ".def  mc8u  =r16    ;multiplicand\n" +
                    ".def  mp8u  =r17    ;multiplier\n" +
                    ".def  m8uL  =r17    ;result Low byte\n" +
                    ".def  m8uH  =r18    ;result High byte\n" +
                    ".def  mcnt8u  =r19    ;loop counter\n" +
                    "\n" +
                    ";***** Code\n" +
                    "\n" +
                    "\n" +
                    "mpy8u:  clr  m8uH    ;clear result High byte\n" +
                    "ldi  mcnt8u,8  ;init loop counter\n" +
                    "lsr  mp8u    ;rotate multiplier\n" +
                    "\n" +
                    "m8u_1:  brcc  m8u_2    ;carry set\n" +
                    "add   m8uH,mc8u  ;   add multiplicand to result High byte\n" +
                    "m8u_2:  ror  m8uH    ;rotate right result High byte\n" +
                    "ror  m8uL    ;rotate right result L byte and multiplier\n" +
                    "dec  mcnt8u    ;decrement loop counter\n" +
                    "brne  m8u_1    ;if not done, loop more"
        );

        int steps = 0;
        int count = 0;

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                count++;

                State state = new State();
                state.setRegister("r16", i);
                state.setRegister("r17", j);

                steps += program.execute(state);

                assertEquals((i * j) & 0xFF, state.getRegister("r17"));
                assertEquals((i * j >> 8) & 0xFF, state.getRegister("r18"));
            }
        }

        System.out.println("Average steps = " + (steps * 1.0 / count));
    }

    @Test
    public void testUnsignedMultiplicationSpeedOptimized() {
        AsmParser asmParser = new AsmParser();
        Program program = asmParser.parse( "" +
                    ".def  mc8u  =r16    ;multiplicand\n" +
                    ".def  mp8u  =r17    ;multiplier\n" +
                    ".def  m8uL  =r17    ;result Low byte\n" +
                    ".def  m8uH  =r18    ;result High byte\n" +
                    ".def  mcnt8u  =r19    ;loop counter\n" +
                    "\n" +
                    ";***** Code\n" +
                    "\n" +
                    "\n" +
                    "mpy8u:  clr  m8uH    ;clear result High byte\n" +
                    "ldi  mcnt8u,8  ;init loop counter\n" +
                    "lsr  mp8u    ;rotate multiplier\n" +
                    "\n" +
                    "m8u_1:  brcc  m8u_2    ;carry set\n" +
                    "add   m8uH,mc8u  ;   add multiplicand to result High byte\n" +
                    "m8u_2:  ror  m8uH    ;rotate right result High byte\n" +
                    "ror  m8uL    ;rotate right result L byte and multiplier\n" +
                    "dec  mcnt8u    ;decrement loop counter\n" +
                    "brne  m8u_1    ;if not done, loop more"
        );

        int steps = 0;
        int count = 0;

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                count++;

                State state = new State();
                state.setRegister("r16", i);
                state.setRegister("r17", j);

                steps += program.execute(state);

                assertEquals((i * j) & 0xFF, state.getRegister("r17"));
                assertEquals((i * j >> 8) & 0xFF, state.getRegister("r18"));
            }
        }

        System.out.println("Average steps = " + (steps * 1.0 / count));
    }

}