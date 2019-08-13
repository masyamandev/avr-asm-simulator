package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import static org.masyaman.avrasmsimulator.registers.Status.STATUS;
import static org.masyaman.avrasmsimulator.registers.Status.STATUS_C;

public class Sbrc extends Command.OneWord /*TODO*/ {

    public Sbrc(String[] params) {
        super(params);
    }

    @Override
    public int execute(State state, Program program) {
        if (state.getRegisterBit(params[0], Integer.parseInt(params[1]))) {
            return 1;
        } else {
            Command nextCommand = program.getCommand(state.getCommandPointer());
            state.setCommandPointer(state.getCommandPointer() + 1);
            return nextCommand.byteLength() / 2 + 1;
        }
    }
}
