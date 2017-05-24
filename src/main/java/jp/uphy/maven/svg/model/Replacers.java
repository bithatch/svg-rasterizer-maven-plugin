package jp.uphy.maven.svg.model;


import java.io.File;
import java.util.regex.Pattern;


public enum Replacers {
    WIDTH("width", new Value() {
        @Override
        public Object get(File inFile, File outFile, OutputDefinition output) {
            return output.getSize(outFile).width;
        }
    }),
    HEIGHT("height", new Value() {
        @Override
        public Object get(File inFile, File outFile, OutputDefinition output) {
            return output.getSize(outFile).height;
        }
    }),
    NAME("name", new Value() {

        @Override
        public Object get(File inFile, File outFile, OutputDefinition output) {
            int i = inFile.getName().lastIndexOf('.');
            return (i > 0) ? inFile.getName().substring(0, i) : inFile.getName();
        }
    }),
    EXTENSION("ext", new Value() {

        @Override
        public Object get(File inFile, File outFile, OutputDefinition output) {
            return "." + output.getFormat();
        }
    });


    public static File replaceAll(File outFile, File inFile, OutputDefinition output) {
        String result = outFile.getAbsolutePath();
        for (Replacers r : values()) {
            result = r.replace(result, inFile, outFile, output);
        }

        return new File(result);
    }


    private final String pattern;
    private final Value value;

    Replacers(String pattern, Value value) {
        this.pattern = "{" + pattern + "}";
        this.value = value;
    }

    public String replace(String path, String value) {
        return path.replaceAll(Pattern.quote(pattern), value);
    }

    private String replace(String path, File inFile, File outFile, OutputDefinition output) {
        return replace(path, String.valueOf(value.get(inFile, outFile, output)));
    }

    private interface Value {
        Object get(File inFile, File outFile, OutputDefinition output);
    }
}
