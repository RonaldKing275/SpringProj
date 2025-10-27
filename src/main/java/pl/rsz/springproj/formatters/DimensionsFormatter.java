package pl.rsz.springproj.formatters;

import org.springframework.format.Formatter;
import pl.rsz.springproj.domain.Dimensions;

import java.text.ParseException;
import java.util.Locale;

public class DimensionsFormatter implements Formatter<Dimensions> {

    @Override
    public String print(Dimensions object, Locale locale) {
        if (object == null || (object.getWidth() == null && object.getHeight() == null && object.getDepth() == null)) {
            return "";
        }
        return String.format(locale, "%.0fx%.0fx%.0f",
                object.getWidth(), object.getHeight(), object.getDepth());
    }

    @Override
    public Dimensions parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        String[] parts = text.split("x");
        if (parts.length != 3) {
            throw new ParseException("Nieprawidłowy format wymiarów. Oczekiwano: SzerokośćxWysokośćxGłębokość", 0);
        }

        try {
            Float width = Float.parseFloat(parts[0]);
            Float height = Float.parseFloat(parts[1]);
            Float depth = Float.parseFloat(parts[2]);
            return new Dimensions(width, height, depth);
        } catch (NumberFormatException e) {
            throw new ParseException("Nieprawidłowe wartości liczbowe w wymiarach.", 0);
        }
    }
}