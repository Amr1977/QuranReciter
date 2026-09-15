# Building an Executable FAT JAR

This guide explains how to build a single executable JAR file that runs on any system with Java installed.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- ANT build tool

## Quick Start

### Option 1: Using ANT (Recommended)
```bash
ant -f build-fat-jar.xml build-fat-jar
```

This will create `QuranReciter-all.jar` in the project root.

### Option 2: Manual Build
If you prefer to build manually or ANT is not available:

1. **Compile the project:**
   ```bash
   ant clean build
   ```

2. **Create a FAT JAR:**
   Use your IDE or create a script to combine all JARs into one:
   ```bash
   jar -cvfm QuranReciter-all.jar manifest.mf -C bin com/ -C bin quran/
   ```

## Running the JAR

Once built, run the application on any system with Java:
```bash
java -jar QuranReciter-all.jar
```

## What's Inside?

The FAT JAR contains:
- ✅ All compiled application code
- ✅ All dependencies (jfxrt, commons-io, basicplayer, etc.)
- ✅ Proper manifest with Main-Class definition
- ✅ No external dependencies needed

## Troubleshooting

### "No main manifest attribute"
Make sure `manifest.mf` has the correct `Main-Class` entry pointing to your main application class.

### "ClassNotFoundException"
Ensure all dependency JARs are listed in the build-fat-jar.xml and extracted properly.

### "Out of Memory" errors
Run with more memory:
```bash
java -Xmx512m -jar QuranReciter-all.jar
```

## Clean Up

To remove the FAT JAR:
```bash
ant -f build-fat-jar.xml clean-fat-jar
```

## Cross-Platform Compatibility

The resulting JAR will run on:
- ✅ Windows (with JRE installed)
- ✅ macOS (with JRE installed)
- ✅ Linux (with JRE installed)

No platform-specific compilation needed!
