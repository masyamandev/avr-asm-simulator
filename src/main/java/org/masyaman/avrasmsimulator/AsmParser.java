package org.masyaman.avrasmsimulator;

import org.masyaman.avrasmsimulator.commands.*;

import java.util.HashMap;
import java.util.Map;

public class AsmParser {

    public static Map<String, Class<? extends Command>> COMMANDS = new HashMap<String, Class<? extends Command>>() {{
        put("add", Add.class);
        put("sub", Sub.class);
        put("sbci", Sbci.class);
        put("com", Com.class);
        put("neg", Neg.class);
        put("dec", Dec.class);
        put("tst", Tst.class);
        put("clr", Clr.class);

        put("sbrc", Sbrc.class);
        put("brne", Brne.class);
        put("brcc", Brcc.class);
        put("brlt", Brlt.class);

        put("mov", Mov.class);
        put("ldi", Ldi.class);

        put("lsr", Lsr.class);
        put("ror", Ror.class);
    }};

    private Map<String, String> defines = new HashMap<>();


    public Program parse(String asm) {
        Program program = new Program();

        String[] lines = asm.split("\n");

        for (String line : lines) {
            line = line.split(";", 2)[0].trim(); // Remove comment
            if (line.contains(":")) {
                String[] labelAndCommand = line.split(":", 2);
                program.addLabel(labelAndCommand[0].trim());
                line = labelAndCommand[1].trim();
            }
            String[] commandParams = line.split("[ \t]", 2);

            if (commandParams[0].isEmpty()) {
                continue;
            }

            if (commandParams[0].equalsIgnoreCase(".def")) {
                String[] params = params(commandParams[1]);
                defines.put(params[0], params[1]);
                continue;
            }

            Class<? extends Command> commandClass = COMMANDS.get(commandParams[0].toLowerCase());
            if (commandClass == null) {
                throw new ParsingException("Command " + commandParams[0] + " not found!");
            }

            String[] params = params(commandParams[1]);

            try {
                program.addCommand(commandClass.getConstructor(String[].class).newInstance((Object) params));
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Unable to create instance of " + commandClass, e);
            }
        }

        return program;
    }




    private String[] params(String p) {
        String[] params = p.split("[,=]");
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].trim().toLowerCase();
            params[i] = defines.getOrDefault(params[i], params[i]);
        }
        return params;
    }


}
