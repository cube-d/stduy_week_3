package com.cubed.reflection.ex1.service.impl;

import com.cubed.reflection.ex1.service.AlarmService;

public class CallAlarmService implements AlarmService {

	@Override
	public void alarm() {
		System.out.println("전 call alarm service 입니다.");
		
	}

}
