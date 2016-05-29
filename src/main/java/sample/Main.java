package sample;

import sample.ui.CLI;
import sample.ui.GUI;
import sample.ui.Shell;

/**
 * The Main starts the program.
 */
public class Main
{
    public static void main(String[] args) {
        // select UI
        if(args.length==0)
            new GUI().start();
        else if (args[0].equals("shell"))
            new Shell().start();
        else
            new CLI().start(args);
    }
}
