import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;

import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.parser.CompModule;

import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;

public class CountAlloyInstances {
    public static void main(String[] args) throws Err {
        if (args.length < 1) {
            System.err.println("Usage: java CountAlloyInstances <model.als> [symmetry]");
            System.err.println("Example: java CountAlloyInstances BinaryTree.als 0");
            System.exit(1);
        }

        String filename = args[0];

        int symmetry = 20; // Alloy default-style setting
        if (args.length >= 2) {
            symmetry = Integer.parseInt(args[1]);
        }

        A4Reporter reporter = new A4Reporter();

        CompModule world = CompUtil.parseEverything_fromFile(
            reporter,
            null,
            filename
        );

        A4Options options = new A4Options();
        
        options.symmetry = symmetry;

        System.out.println("Using symmetry = " + options.symmetry);

        int commandIndex = 0;

        for (Command command : world.getAllCommands()) {
            commandIndex++;

            System.out.println("Running command #" + commandIndex + ": " + command);

            A4Solution solution = TranslateAlloyToKodkod.execute_command(
                reporter,
                world.getAllReachableSigs(),
                command,
                options
            );

            int count = 0;

            while (solution.satisfiable()) {
                count++;

                if (count % 100 == 0) {
                    System.out.println("Count so far = " + count);
                }

                solution = solution.next();
            }

            System.out.println("Total instances for command #" + commandIndex + " = " + count);
        }
    }
}
