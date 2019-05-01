package com.cubed.reflection.ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.cubed.reflection.ex1.service.AlarmService;

public class AlarmServiceTest {
	public static void main(String[] args)
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final String PACKAGE_PATH = "com.cubed.reflection.ex1.service.impl.";
		
		List<String> serviceList = getServiceList();

		for (String serviceName : serviceList) {

			Class cls = Class.forName(PACKAGE_PATH + serviceName + "AlarmService");
			Constructor ct = cls.getConstructor();
			AlarmService alarmObj = (AlarmService) ct.newInstance();
			alarmObj.alarm();

		}

	}

	public static List<String> getServiceList() throws IOException {
		List<String> serviceList = new ArrayList<>();

		// 파일 객체 생성
		File file = new File("/Users/jaehyun/Desktop/AlarmServiceList.txt");
		// 입력 스트림 생성
		FileReader filereader = new FileReader(file);
		// 입력 버퍼 생성
		BufferedReader bufReader = new BufferedReader(filereader);
		String line = "";

		while ((line = bufReader.readLine()) != null) {
			serviceList.add(line);
		}
		bufReader.close();

		return serviceList;
	}
}
