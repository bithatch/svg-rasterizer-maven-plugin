package jp.uphy.maven.svg.mojo;


import java.io.File;
import java.util.regex.Pattern;

import static jp.uphy.maven.svg.mojo.AbstractRasterizeMojo.getFilenameOf;


enum Replacers {
    WIDTH("width", new Value() {
        @Override
        public Object get(File inFile, File outFile, AbstractOutput output) {
            return output.getSize(outFile).width;
        }
    }),
    HEIGHT("height", new Value() {
        @Override
        public Object get(File inFile, File outFile, AbstractOutput output) {
            return output.getSize(outFile).height;
        }
    }),
    NAME("name", new Value() {

        @Override
        public Object get(File inFile, File outFile, AbstractOutput output) {
            return getFilenameOf(inFile);
        }
    }),
    EXTENSION("ext", new Value() {

        @Override
        public Object get(File inFile, File outFile, AbstractOutput output) {
            return "." + output.getFormat();
        }
    });


    static File replaceAll(File outFile, File inFile, AbstractOutput output) {
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

    private String replace(String path, File inFile, File outFile, AbstractOutput output) {
        return path.replaceAll(Pattern.quote(pattern), String.valueOf(value.get(inFile, outFile, output)));
    }

    private interface Value {
        Object get(File inFile, File outFile, AbstractOutput output);
    }
}
