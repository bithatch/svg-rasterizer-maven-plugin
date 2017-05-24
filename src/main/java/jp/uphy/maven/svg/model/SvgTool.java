/*
 * Copyright (C) 2014 uphy.jp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.uphy.maven.svg.model;


import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;


/**
 * @author Yuhi Ishikura
 */
class SvgTool {
    private final SVGConverter svgConverter;

    SvgTool() {
        this.svgConverter = new SVGConverter();
    }

    void rasterize(File svgFile, File output, int width, int height, float quality, DestinationType destinationType) throws SVGConverterException {
        if (width > height) {
            this.svgConverter.setWidth(width);
        } else {
            this.svgConverter.setHeight(height);
        }
        this.svgConverter.setDestinationType(destinationType);
        this.svgConverter.setSources(new String[] {svgFile.getAbsolutePath()});
        this.svgConverter.setQuality(quality);
        this.svgConverter.setDst(output);
        final PrintStream oldSysOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream())); // 不要な出力が出るので抑止
        try {
            this.svgConverter.execute();
        } finally {
            System.setOut(oldSysOut);
        }
    }
}
