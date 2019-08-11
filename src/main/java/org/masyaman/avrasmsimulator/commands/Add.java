package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Add extends Command.OneWord {

    public Add(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        int result =
                state.getRegister(params[0]) +
                state.getRegister(params[1]);
        state.setRegister(params[0], result);
        state.setRegisterBit(STATUS, STATUS_Z, result == 0);
        state.setRegisterBit(STATUS, STATUS_C, result > 255 || result < 0);
        state.setRegisterBit(STATUS, STATUS_N, (result & 0x80) != 0);
        state.setRegisterBit(STATUS, STATUS_V, false); // TODO
        state.setRegisterBit(STATUS, STATUS_H, false); // TODO
        return 1;
    }
}
