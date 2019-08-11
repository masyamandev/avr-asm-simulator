package org.masyaman.avrasmsimulator;

import org.masyaman.avrasmsimulator.commands.Command;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

    private List<Command> commands = new ArrayList<>();
    private Map<String, Integer> labels = new HashMap<>();


    public void addCommand(Command command) {
        commands.add(command);
    }

    public void addLabel(String label) {
        labels.put(label, commands.size());
    }

    public int execute(State state) {
        int executionLength = 0;
        while (state.getCommandPointer() < commands.size()) {
            Command command = commands.get(state.getCommandPointer());
            state.setCommandPointer(state.getCommandPointer() + 1);
            try {
                executionLength += command.execute(state, this);
            } catch (Exception e) {
                throw new RuntimeException("Error in executing command \"" + command + "\"", e);
            }
        }
        return executionLength;
    }

    public int getPointer(String toPointer, int currentPointer) {
        if (labels.containsKey(toPointer)) {
            return labels.get(toPointer);
        }
        throw new NotImplementedException();
    }
}
