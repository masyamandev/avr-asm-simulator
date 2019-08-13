package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Mov extends Command.OneWord {

    public Mov(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        int result = state.getRegister(params[1]);
        state.setRegister(params[0], result);
        return 1;
    }
}
