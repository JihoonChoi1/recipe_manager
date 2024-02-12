package ui;

import model.Event;
import model.EventLog;


/**
 * Represents a console printer for printing event log to the console.
 */
public class ConsolePrinter implements LogPrinter {

    @Override
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}
