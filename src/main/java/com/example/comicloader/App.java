package com.example.comicloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class App {
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		Options options = new Options();
		options.addOption("c", "comic", true, "The comic id from cartoonmad.com");
		options.addOption("H", "hash", true, "The comic url hash from catoonmad.com");
		options.addOption("e", "episod", true, "The comic episod number");
		options.addOption("o", "output-dir", true, "Output directory path");
		options.addOption("h", "help", false, "Print command line manual");

		DefaultParser cmdParser = new DefaultParser();
		try {

			CommandLine cmdLine = cmdParser.parse(options, args);
			
			if(cmdLine.hasOption('h')){
				printHelp(options);
				System.exit(0);
			}
			
			if(cmdLine.getOptionValue('c') == null){
				System.out.println("Missing comic id");
				printHelp(options);
				System.exit(1);
			}
			if(cmdLine.getOptionValue('H') == null){
				System.out.println("Missing comic url hash");
				printHelp(options);
				System.exit(1);
			}
			if(cmdLine.getOptionValue('e') == null){
				System.out.println("Missing comic episod number");
				printHelp(options);
				System.exit(1);
			}
			if(cmdLine.getOptionValue('o') == null){
				System.out.println("Missing output directory path");
				printHelp(options);
				System.exit(1);
			}
			

			String comicId = cmdLine.getOptionValue('c');
			String hash = cmdLine.getOptionValue('H');
			String episod = cmdLine.getOptionValue('e');
			episod = StringUtils.leftPad(episod, 3,"0");
			
			File outputDir = new File(cmdLine.getOptionValue('o'));

			outputDir.mkdirs();

			for (int i = 1; i < 999; i++) {
				String picNum = StringUtils.leftPad(String.valueOf(i), 3,"0");
				String urlStr = "http://web.cartoonmad.com/"+hash+"/"+comicId+"/"+episod+"/"+picNum+".jpg";
				URL url = new URL(urlStr);
				File destFile = new File(outputDir, picNum+".jpg");
				
				
				
				try {
					FileUtils.copyURLToFile(url, destFile);
					System.out.println(urlStr + " --> " + destFile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				
				Thread.sleep(1000);
				
			}
			
			System.out.println("Download Complete");
			

		} catch (ParseException e) {
			printHelp(options);
			System.exit(1);
		}

	}

	public static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("cldr <options>", options);
	}
}
