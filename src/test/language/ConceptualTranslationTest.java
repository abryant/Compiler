package test.language;

import java.io.File;

import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.parser.LanguageParser;
import compiler.language.translator.conceptual.ConceptualTranslator;

/*
 * Created on 30 Apr 2011
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslationTest
{

  public static void main(String[] args)
  {
    if (args.length < 1)
    {
      System.err.println("Usage: java test.language.LanguageParserTest <SourceFile>");
      System.exit(1);
    }

    File file = new File(args[0]);
    if (!file.isFile())
    {
      System.err.println("Could not find source file: " + file.getAbsolutePath());
      System.exit(1);
    }

    LanguageParser parser = new LanguageParser();

    ConceptualTranslator translator = new ConceptualTranslator(parser);

    ConceptualFile conceptualFile = translator.parseFile(file);

    if (conceptualFile != null)
    {
      System.out.println("Successfully parsed " + file.getAbsolutePath());
      System.out.println(conceptualFile);
    }
  }

}
