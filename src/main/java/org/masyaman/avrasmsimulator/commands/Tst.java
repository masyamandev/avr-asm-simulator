package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Tst extends Command.OneWord {

    public Tst(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        int result = state.getRegister(params[0]);
        state.setRegisterBit(STATUS, STATUS_Z, result == 0);
        state.setRegisterBit(STATUS, STATUS_N, (result & 0x80) != 0);
        state.setRegisterBit(STATUS, STATUS_V, false); // TODO
        return 1;
    }
}
