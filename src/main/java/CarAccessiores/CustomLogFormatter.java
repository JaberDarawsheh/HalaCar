package CarAccessiores;



import java.util.logging.Formatter;

import java.util.logging.LogRecord;



public class CustomLogFormatter extends Formatter {

    @Override

    public String format(LogRecord record) {

        // TODO Auto-generated method stub

        return record.getMessage() + System.lineSeparator();

    }

}