package org.masyaman.avrasmsimulator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplicationTest {

    private static Stream<Arguments> provideTwoValues() {
        return IntStream.range(0, 256)
                .mapToObj(a -> Arguments.of(a, IntStream.range(0, 256)));
    }

    @MethodSource("provideTwoValues")
    @ParameterizedTest
    public void testUnsignedMultiplicationSizeOptimized(int uMultiplicand, IntStream uMultipliers) {
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

        AtomicInteger steps = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();

        uMultipliers.forEach(uMultiplier -> {
            count.incrementAndGet();

            State state = new State();
            state.setRegister("r16", uMultiplicand);
            state.setRegister("r17", uMultiplier);

            steps.addAndGet(program.execute(state));

            assertEquals((uMultiplicand * uMultiplier) & 0xFF, state.getRegister("r17"));
            assertEquals((uMultiplicand * uMultiplier >> 8) & 0xFF, state.getRegister("r18"));

        });

        System.out.println("Average steps = " + (steps.doubleValue() / count.doubleValue()));
    }


    @MethodSource("provideTwoValues")
    @ParameterizedTest
    public void testUnsignedMultiplicationSpeedOptimized(int uMultiplicand, IntStream uMultipliers) {
        AsmParser asmParser = new AsmParser();
        Program program = asmParser.parse( "" +
                "clr %B[result]" + "\n" +
                "mov %A[result], %[muls]" + "\n" +
//                "sbrc %A[result], 7" + "\n" +
//                "neg %A[result]" + "\n" +

                "lsr %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                "brcc .+2" + "\n" +
                "add %B[result], %[mulu]" + "\n" +
                "ror %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                // Bit7 is 0. This optimized block will cause issue on muls = -128. To avoid this use block similar to above.
                "lsr %B[result]" + "\n" +
                "ror %A[result]" + "\n" +

                // Invert result sign
//                "tst %[muls]" + "\n" +
//                "brlt .+6" + "\n" +
//                "com %B[result]" + "\n" +
//                "neg %A[result]" + "\n" +
//                "sbci %B[result], 255" + "\n" +
                ""
        );

        AtomicInteger steps = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();

        uMultipliers.forEach(uMultiplier -> {
            count.incrementAndGet();

            State state = new State();
            state.setRegister("%[mulu]", uMultiplicand);
            state.setRegister("%[muls]", uMultiplier);

            steps.addAndGet(program.execute(state));

            String description = "" + uMultiplicand + "x" + uMultiplier;

            assertEquals((uMultiplicand * uMultiplier) & 0xFF, state.getRegister("%A[result]"), description);
            assertEquals((uMultiplicand * uMultiplier >> 8) & 0xFF, state.getRegister("%B[result]"), description);

        });

        System.out.println("Average steps = " + (steps.doubleValue() / count.doubleValue()));
    }
}