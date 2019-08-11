package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.STATUS;
import static org.masyaman.avrasmsimulator.registers.Status.STATUS_C;

public class Brcc extends Command.OneWord /*TODO*/ {

    public Brcc(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        if (state.getRegisterBit(STATUS, STATUS_C)) {
            return 1;
        } else {
            state.setCommandPointer(program.getPointer(params[0], state.getCommandPointer()));
            return 2;
        }
    }
}
