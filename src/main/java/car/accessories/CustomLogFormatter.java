package car.accessories;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord logRecord) {
     return logRecord.getMessage() + System.lineSeparator();
    }
}