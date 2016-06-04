package cn.edu.fudan.live.service;

import java.util.Set;

import cn.edu.fudan.anniversary.exception.IllegalArgumentsException;

public class ValidateService {
	/**
	 * 验证必须的参数， 传入参数：集合
	 */
	public static void ValidateNecessaryArguments(Set<Object> params) {
		Object[] params_array = params.toArray();
		int length = params_array.length;
		for (int i = 0; i < length; i++) {
			Object o = params_array[i];
			if (!(o != null && !o.equals(""))) {
				throw new IllegalArgumentsException();
			}
		}
	}

	/**
	 * 参数不能全为空， 传入参数：集合
	 */
	public static void isAllEmpty(Set<Object> params) {
		boolean bool = true;
		Object[] params_array = params.toArray();
		int length = params_array.length;
		for (int i = 0; i < length; i++) {
			Object o = params_array[i];
			if (o != null && !o.equals("")) {
				bool = false;
			}
		}

		if (bool) {
			throw new IllegalArgumentsException();
		}
	}

}
