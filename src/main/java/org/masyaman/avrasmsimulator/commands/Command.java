package org.masyaman.avrasmsimulator.commands;

import org.masyaman.avrasmsimulator.Program;
import org.masyaman.avrasmsimulator.State;

import java.util.Arrays;

public abstract class Command {

    protected String[] params;

    public Command(String[] params) {
        this.params = params;
    }

    public abstract int execute(State state, Program program);
    public abstract int byteLength();


    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + Arrays.toString(params);
    }

    abstract static class OneWord extends Command {
        public OneWord(String[] params) {
            super(params);
        }

        @Override
        public int byteLength() {
            return 2;
        }
    }
    abstract static class TwoWords extends Command {
        public TwoWords(String[] params) {
            super(params);
        }

        @Override
        public int byteLength() {
            return 4;
        }
    }
}
