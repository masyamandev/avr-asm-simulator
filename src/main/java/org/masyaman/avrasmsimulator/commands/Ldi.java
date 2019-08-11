package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Ldi extends Command.OneWord {

    public Ldi(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        state.setRegister(params[0], Integer.parseInt(params[1]));
        return 1;
    }
}
