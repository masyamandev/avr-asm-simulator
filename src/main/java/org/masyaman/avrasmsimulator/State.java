package org.masyaman.avrasmsimulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class State {

    private Random random = new Random();
    private int commandPointer = 0;


    private Map<String, Byte> registers = new HashMap<>();


    public void setRegister(String register, int value) {
        registers.put(register, (byte) value);
    }

    public int getRegister(String register) {
        return registers.computeIfAbsent(register, this::defaultRegister) & 0xFF;
    }

    public int getRegister(String register, int defaultValue) {
        return registers.getOrDefault(register, (byte) defaultValue) & 0xFF;
    }

    public void setRegisterBit(String register, int bitNumber, boolean bitValue) {
        getRegister(register);
        if (bitValue) {
            registers.compute(register, (reg, value) -> (byte) (value | (1 << bitNumber)));
        } else {
            registers.compute(register, (reg, value) -> (byte) (value & ~(1 << bitNumber)));
        }
    }

    public boolean getRegisterBit(String register, int bitNumber) {
        return (getRegister(register) & (1 << bitNumber)) != 0;
    }


    public int getCommandPointer() {
        return commandPointer;
    }

    public void setCommandPointer(int commandPointer) {
        this.commandPointer = commandPointer;
    }

    private byte defaultRegister(String register) {
        return (byte) random.nextInt();
    }
}
