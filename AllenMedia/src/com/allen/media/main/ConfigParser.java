package com.allen.media.main;

public class ConfigParser {
	private final static String TAG	= "ConfigParser";
	
	private static ConfigParser INSTANCE = null;
	private DirectoryLists mDirectoryLists = null;

	public static ConfigParser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConfigParser();
		}
		return INSTANCE;
	}
	
	public void setDirectoryLists(DirectoryLists config){
		this.mDirectoryLists = config;
	}
	
	public DirectoryLists getDirectoryLists(){
		return this.mDirectoryLists;
	}
}
