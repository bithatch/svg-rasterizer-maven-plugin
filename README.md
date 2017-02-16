# svg-rasterizer-maven-plugin [![Build Status](https://travis-ci.org/Argelbargel/svg-maven-plugin.svg?branch=master)](https://travis-ci.org/Argelbargel/svg-maven-plugin)


## Summary

Maven SVG rasterization plugin.
This plugin converts SVG files to raster images like png, jpg. 
Also supports converting to multiple resolutions for Android.

This implementation is basically a rewrite of [uphy/svg-maven-plugin](https://github.com/uphy/svg-maven-plugin).
It uses the same rasterizer-core but the maven-mojo's were re-written to support easier an more flexible configuration
which i needed for my projects. There will not be much development here after the initial-release.  


## Setup

Edit your pom.xml as follows.
See also example [pom.xml](https://github.com/uphy/svg-maven-plugin/blob/develop/sample/pom.xml).

1.Add plugin repository.

```xml
<pluginRepositories>
    <pluginRepository>
        <id>svg-rasterizer-maven-plugin-mvn-repo</id>
        <url>https://raw.githubusercontent.com/Argelbargel/svg-rasterizer-maven-plugin/mvn-repo/</url>
    </pluginRepository>
</pluginRepositories>
```

2.Add plugin.

```xml
<plugin>
    <groupId>argelbargel.maven</groupId>
    <artifactId>svg-rasterizer-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>Rasterize</id>
            <goals>
                <goal>rasterize[-image|-directory|-android-image|-android-directory]</goal>
            </goals>
            <configuration>
                <!-- ... -->
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Goals
### Rasterize a single SVG image file
Convert a SVG image to raster images.

#### Goal: `rasterize-image`
```xml
<configuration>
    <!-- Input file path.  Must exist.[Required] -->
    <inputFile>svg/sample.svg</inputFile>
    <!-- Output directory [Required] -->
    <destDir>output/</destDir>
    <outputs>
        <output>
            <!-- Output file path. [Optiona, default: {name}-{width}x{height}{ext}] -->
            <path>sample128.png</path>
            <!-- width of output image. [Required] -->
            <width>128</width>
            <!-- height of output image. [Required] -->
            <height>128</height>
            <!-- Format for output image(png, jpg, pdf, tiff). [Optional, default:png] -->
            <format>png</format>
            <!-- Quality (Optional, default 0.99) -->
            <quality>0.85</quality>
        </output>
        <output>
            <path>sample256.pdf</path>
            <width>256</width>
            <height>256</height>
            <format>pdf</format>
        </output>
    </outputs>
</configuration>
```

##### Using Placeholders for output-path:

```xml
<configuration>
    <inputFile>svg/sample.svg</inputFile>
    <destDir>output/</destDir>
    <outputs>
        <output>
            <path>sample{width}{ext}</path>
            <width>128</width>
            <height>128</height>
            <format>png</format>
        </output>
        <output>
            <path>sample{width}{ext}</path>
            <width>256</width>
            <height>256</height>
            <format>pdf</format>
        </output>
    </outputs>
</configuration>
```

##### Using defaults-block for default-settings

```xml
<configuration>
    <inputFile>svg/sample.svg</inputFile>
    <destDir>output/</destDir>
    <defaults>
        <path>{name}-{width}x{height}{ext}</path>
        <format>png</format>
        <quality>0.8</quality>
    </defaults>
    <outputs>
        <output>
            <width>128</width>
            <height>128</height>
        </output>
        <output>
            <width>256</width>
            <height>256</height>
        </output>
    </outputs>
</configuration>
```

### Rasterize SVG all images in a directory (non-recursive!)
Convert SVG images in a directory at once.

#### Goal: `rasterize-directory`
```xml
<configuration>
    <!-- Input directory path.  Must exist.[Required] -->
    <inputDir>svg/images</inputDir>
    <!-- Output directory path [Required] -->
    <destDir>output/</destDir>
    <outputs>
        <output>
            <!-- output-path [Optional, default: {name}-{width}-{height}{ext}] -->
            <path>{width}x{height}/{name}{ext}</path>
            <width>128</width>
            <height>128</height>
            <format>png</format>
        </output>
        <output>
            <path>{width}x{height}/{name}{ext}</path>
            <width>256</width>
            <height>256</height>
            <format>png</format>
        </output>
    </outputs>
</configuration>
```

##### Using defaults-block for default-settings

```xml
<configuration>
    <inputDir>svg/sample.svg</inputDir>
    <destDir>output/</destDir>
    <defaults>
        <path>{name}-{width}x{height}{ext}</path>
        <format>png</format>
    </defaults>
    <outputs>
        <output>
            <width>128</width>
            <height>128</height>
        </output>
        <output>
            <width>256</width>
            <height>256</height>
        </output>
    </outputs>
</configuration>
```

### Rasterize a single SVG image for Android
Convert a SVG image to raster images for multiple resolutions of Android.

#### Goal: `rasterize-android-image`

```xml
<configuration>
    <!-- Input file path.  Must exist.[Required] -->
    <inputFile>svg/sample.svg</inputFile>
    <!-- Output directory path [Required] -->
    <destDir>output/</destDir>
    <outputs>
        <output>
            <!-- width of output image (this width is used for MDPI, other outputs get scaled accordingly). [Required] -->
            <width>128</width>
            <!-- height of output image (this width is used for MDPI, other outputs get scaled accordingly). [Required] -->
            <height>128</height>
            <!-- "res" directory of Android. [Optional, default:res] -->
            <resDirectory>android/res</resDirectory>
            <!-- name of the ouptut-file without extension (fixed to .png) below res-directory [Optional, default:{name}] -->
            <name>sample-icon</name>
            <!-- Output resolutions. [Optional, default:LDPI,MDPI,HDPI,XHDPI,XXHDPI] -->
            <!-- The size of MDPI equals to the base size(same as ${width},${height}}).-->
            <!-- Output location will be determined automatically. -->
            <!-- e.g., LDPI will be located in ${resDirectory}/drawable-ldpi/ -->
            <resolutions>
                <resolution>LDPI</resolution>
                <resolution>MDPI</resolution>
                <resolution>HDPI</resolution>
                <resolution>XHDPI</resolution>
                <resolution>XXHDPI</resolution>
            </resolutions>
        </output>        
    </outputs>
</configuration>
```

### Rasterize SVG images in a directory for Android
Convert SVG images in a directory to Android multiple resolution images.
Filename must be a special below format. 
 
#### Goal: `rasterize-android-directory`

```xml
<configuration>
    <!-- Input directory path.  Must exist.[Required] -->
    <inputDir>svg/images</inputDir>
    <!-- Output directory path [Required] -->
    <destDir>output/</destDir>
    <outputs>
        <output>
            <!-- width of output image (this width is used for MDPI, other outputs get scaled accordingly). [Required] -->
            <width>128</width>
            <!-- height of output image (this width is used for MDPI, other outputs get scaled accordingly). [Required] -->
            <height>128</height>
            <!-- "res" directory of Android. [Optional, default:res] -->
            <resDirectory>android/res</resDirectory>
            <!-- name of the ouptut-file without extension (fixed to .png) below res-directory [Optional, default:{name}] -->
            <name>sample-icon</name>
            <!-- Output resolutions. [Optional, default:LDPI,MDPI,HDPI,XHDPI,XXHDPI] -->
            <!-- The size of MDPI equals to the base size(same as ${width},${height}}).-->
            <!-- Output location will be determined automatically. -->
            <!-- e.g., LDPI will be located in ${resDirectory}/drawable-ldpi/ -->
            <resolutions>
                <resolution>LDPI</resolution>
                <resolution>MDPI</resolution>
                <resolution>HDPI</resolution>
                <resolution>XHDPI</resolution>
                <resolution>XXHDPI</resolution>
            </resolutions>
        </output>        
    </outputs>
</configuration>
```