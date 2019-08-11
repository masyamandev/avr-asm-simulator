package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.*;

public class Brne extends Command.OneWord /*TODO*/ {

    public Brne(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        if (state.getRegisterBit(STATUS, STATUS_Z)) {
            return 1;
        } else {
            state.setCommandPointer(program.getPointer(params[0], state.getCommandPointer()));
            return 2;
        }
    }
}
