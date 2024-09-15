package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author M.J.Wu
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine ironman = readConfig();
        System.setOut(_output);
        boolean set = false;
        while (_input.hasNext()) {
            if (_input.hasNext("\\*")) {
                String next = _input.nextLine();
                while (next.isEmpty()) {
                    next = _input.nextLine();
                    System.out.println();
                }
                next = next.replace('*', ' ');
                set = true;
                setUp(ironman, next);
            }
            if (!set) {
                throw new EnigmaException("Configuration Error!");
            }
            while (_input.hasNext() && !_input.hasNext("\\*")) {
                String code = "";
                String c = _input.nextLine();
                Scanner S = new Scanner(c);
                while (S.hasNext()) {
                    code = code.concat(ironman.convert(S.next()));
                }
                printMessageLine(code);
                System.out.println();
            }
        }
        while (_input.hasNextLine()) {
            _input.nextLine();
            System.out.println();
        }
    }



    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = _config.next();
            _alphabet = new Alphabet(alpha);
            int pawls;
            int rotors;
            ArrayList<Rotor> rotorsList = new ArrayList<Rotor>();
            if (!_config.hasNextInt()) {
                throw new EnigmaException("Error");
            } else {
                rotors = _config.nextInt();
                pawls = _config.nextInt();
            }
            while (_config.hasNext()) {
                rotorsList.add(readRotor());
            }
            return new Machine(_alphabet, rotors, pawls, rotorsList);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name;
            String notch;
            String s;
            Rotor r;
            name = _config.next();
            notch = _config.next();
            s = "";
            char word = notch.charAt(0);
            if (name.contains(")")) {
                throw new EnigmaException("Improper name");
            } else if (name.contains("(")) {
                throw new EnigmaException("Improper name");
            }
            while (_config.hasNext("[(].+[)]")) {
                s = s + _config.next();
            }
            Permutation perm = new Permutation(s, _alphabet);
            if (notch.equals("R")) {
                r = new Reflector(name, perm);
            } else if (notch.equals("N")) {
                r = new FixedRotor(name, perm);
            } else if (word == 'M') {
                r = new MovingRotor(name, perm, notch.substring(1));
            } else {
                throw error("bad rotor description");
            }
            return r;
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        int rot = M.numRotors();
        String [] venom = new String[rot];
        Scanner s = new Scanner(settings);
        for (int i = 0; i < M.numRotors(); i += 1) {
            String rotor = s.next();
            if (rotor.isEmpty()) {
                throw error("Empty, no Rotor");
            }
            venom[i] = rotor;
        }
        M.insertRotors(venom);
        boolean rotorsetting = false;
        M.tenRings();
        boolean ringsetting = false;
        String eninem = "";
        while (s.hasNext()) {
            String next = s.next();
            if (next.charAt(0) == '(') {
                eninem = eninem.concat(next);
                rotorsetting = true;
            } else if (next.charAt(0) != '(' && !rotorsetting) {
                M.setRotors(next);
                rotorsetting = true;
            } else if (next.charAt(0) != '('
                    && rotorsetting && !ringsetting) {
                M.tenRings(next);
                ringsetting = true;
            } else {
                throw error("Setting Error.");
            }
        }
        M.setPlugboard(new Permutation(eninem, _alphabet));
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        int l = msg.length();
        for (int i = 0; i < l; i++) {
            _output.print(msg.charAt(i));
            if (i != l - 1) {
                if ((i + 1) % 5 == 0) {
                    _output.print(' ');
                }
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;



    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
}
