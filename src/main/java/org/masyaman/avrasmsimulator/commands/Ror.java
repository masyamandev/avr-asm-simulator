package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Ror extends Command.OneWord {

    public Ror(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        boolean carry = state.getRegisterBit(STATUS, STATUS_C);
        int result = state.getRegister(params[0]);
        state.setRegisterBit(STATUS, STATUS_C, (result & 1) != 0);
        result >>= 1;
        if (carry) {
            result |= 0x80;
        }
        state.setRegister(params[0], result);
        state.setRegisterBit(STATUS, STATUS_Z, result == 0);
        state.setRegisterBit(STATUS, STATUS_N, (result & 0x80) != 0); // TODO
        state.setRegisterBit(STATUS, STATUS_V, false); // TODO
        return 1;
    }
}
