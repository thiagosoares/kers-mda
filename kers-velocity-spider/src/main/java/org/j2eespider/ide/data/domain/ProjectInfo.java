package org.j2eespider.ide.data.domain;

public class ProjectInfo {
	private static String location;
	private static String name;
	
	public static String getLocation() {
		return location;
	}
	public static void setLocation(String location) {
		ProjectInfo.location = location;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		ProjectInfo.name = name;
	}
}
