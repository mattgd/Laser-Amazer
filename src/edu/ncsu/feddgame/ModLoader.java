package edu.ncsu.feddgame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader extends ClassLoader{
	private String jarPath = "mods/";
	private ArrayList<String> jars = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	private Hashtable<String, Class> classes = new Hashtable<String, Class>();
	
	public ModLoader(){
		super(ModLoader.class.getClassLoader());
		loadMods();
	}
	
	public void loadMods(){
		File folder = new File(jarPath);
		File[] jarFiles = folder.listFiles();
		for (int i = 0; i < jarFiles.length; i++){
			if (jarFiles[i].isFile()){
				jars.add(jarFiles[i].getName());
			}
		}
		for (String s : jars){
			try {
				loadClass(s.substring(0, s.length()-4),jarPath + s).newInstance(); 	//Loads the class by the same name as the jar and constructs it
			} catch (Exception e) {}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Class loadClass(String className, String jarFile){
		try{
			return findClass(className, jarFile);
		}catch (Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Class findClass(String className, String jarFile) {
        byte classByte[];
        Class result = null;
        JarFile jar = null;
        try {
            jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(className + ".class");
            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);
            classes.put(className, result);
            return result;
        } catch (Exception e) {
            return null;
        }finally {
        	try {
				jar.close();
			} catch (IOException e) {
			}
        }
    }
}
