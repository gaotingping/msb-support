package com.mvw.china.common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mvw.china.bus.annotation.ApiDescribe;

/**
 * 反射工具
 * 
 * @author gaotingping@cyberzone.cn
 */
public class ReflectUtils {

	public static JSONObject allFields(Class<?> c) {
		JSONObject result = new JSONObject();
		if (isBaseType(c)) {
			ApiDescribe desc = c.getAnnotation(ApiDescribe.class);
			JSONObject tmp1 = new JSONObject();
			tmp1.put("type", c.getSimpleName());
			tmp1.put("fields", null);
			if (desc != null) {
				tmp1.put("desc", desc.value());
			} else {
				tmp1.put("desc", "");
			}
			result.put(c.getSimpleName(), tmp1);
		} else {
			do {
				Field[] fs = c.getDeclaredFields();
				for (Field f : fs) {
					HashMap<String, String> circuleRef = new HashMap<String, String>();
					circuleRef.put(c.getName(), null);
					ApiDescribe desc = f.getAnnotation(ApiDescribe.class);
					if (desc == null) {
						continue;
					}
					if (isBaseType(f.getType())) {
						result.put(f.getName(), desc.value());
						JSONObject tmp2 = new JSONObject();
						tmp2.put("type", f.getType().getSimpleName());
						tmp2.put("fields", null);
						tmp2.put("desc", desc.value());
						result.put(f.getName(), tmp2);
					} else if (f.getType() == List.class) {
						Type type = f.getGenericType();
						if (type instanceof ParameterizedType) {
							Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
							Class<?> tmpC = (Class<?>) actualTypes[0];
							if (circuleRef.containsKey(tmpC.getName())) {
								JSONObject tmp3 = new JSONObject();
								tmp3.put("type", f.getType().getSimpleName());
								tmp3.put("fields", "$ref_" + tmpC.getSimpleName());
								tmp3.put("desc", desc.value());
								result.put(f.getName(), tmp3);
							} else {
								JSONArray data = new JSONArray();
								data.add(innerAllFields(tmpC, circuleRef));
								JSONObject tmp4 = new JSONObject();
								tmp4.put("type", f.getType().getSimpleName());
								tmp4.put("fields", data);
								tmp4.put("desc", desc.value());
								result.put(f.getName(), tmp4);
								circuleRef.remove(tmpC.getName());
							}
						}
					} else {
						if (circuleRef.containsKey(f.getType().getName())) {
							JSONObject tmp5 = new JSONObject();
							tmp5.put("type", f.getType().getSimpleName());
							tmp5.put("fields", "$ref_" + f.getType().getSimpleName());
							tmp5.put("desc", desc.value());
							result.put(f.getName(), tmp5);
						} else {
							JSONObject tmp6 = new JSONObject();
							tmp6.put("type", f.getType().getSimpleName());
							tmp6.put("fields", innerAllFields(f.getType(), circuleRef));
							tmp6.put("desc", desc.value());
							result.put(f.getName(), tmp6);
							circuleRef.remove(f.getType().getName());
						}
					}
				}
				c = c.getSuperclass();
			} while (c != null && c != Object.class);
		}

		return result;
	}

	private static JSONObject innerAllFields(Class<?> c, HashMap<String, String> circuleRef) {
		JSONObject result = new JSONObject();
		circuleRef.put(c.getName(), null);
		do {
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				ApiDescribe desc = f.getAnnotation(ApiDescribe.class);
				if (desc == null) {
					continue;
				}
				if (isBaseType(f.getType())) {
					JSONObject tmp1 = new JSONObject();
					tmp1.put("type", f.getType().getSimpleName());
					tmp1.put("fields", null);
					tmp1.put("desc", desc.value());
					result.put(f.getName(), tmp1);
				} else if (f.getType() == List.class) {
					Type type = f.getGenericType();
					if (type instanceof ParameterizedType) {
						Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
						Class<?> tmpC = (Class<?>) actualTypes[0];
						JSONArray data = new JSONArray();
						data.add(innerAllFields(tmpC, circuleRef));
						JSONObject tmp2 = new JSONObject();
						tmp2.put("type", f.getType().getSimpleName());
						tmp2.put("fields", data);
						tmp2.put("desc", desc.value());
						result.put(f.getName(), tmp2);
						circuleRef.remove(tmpC.getName());
					}
				} else {
					if (circuleRef.containsKey(f.getType().getName())) {
						JSONObject tmp3 = new JSONObject();
						tmp3.put("type", f.getType().getSimpleName());
						tmp3.put("fields", "$ref_" + f.getType().getSimpleName());
						tmp3.put("desc", desc.value());
						result.put(f.getName(), tmp3);
					} else {
						JSONObject tmp4 = new JSONObject();
						tmp4.put("type", f.getType().getSimpleName());
						tmp4.put("fields", innerAllFields(f.getType(), circuleRef));
						tmp4.put("desc", desc.value());
						result.put(f.getName(), tmp4);
					}
					circuleRef.remove(f.getType().getName());
				}
			}
			c = c.getSuperclass();
		} while (c != null && c != Object.class);

		return result;
	}

	public static boolean isBaseType(Class<?> c) {
		if (c.isPrimitive()) {
			return true;
		} else if (c == String.class) {
			return true;
		} else if (c == Integer.class) {
			return true;
		} else if (c == Long.class) {
			return true;
		} else if (c == Double.class) {
			return true;
		} else if (c == Float.class) {
			return true;
		} else if (c == Boolean.class) {
			return true;
		}else if (c == Short.class) {
			return true;
		}else if (c == Byte.class) {
			return true;
		}else if (c == Character.class) {
			return true;
		}
		return false;
	}
}
