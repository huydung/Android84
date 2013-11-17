package com.hdway.utils;

public class StringUtils {
	public static String convertVietnameseToLatin(String input) {
		String output = input;		
		output = output.replaceAll("[ăâắằẳẵặấầẩẫậáàảãạ]", "a");
		output = output.replaceAll("[úùủũụưứừửữự]", "u");
		output = output.replaceAll("[éèẻẽẹế�?ểễệê]", "e");
		output = output.replaceAll("[íìỉĩị]", "i");
		output = output.replaceAll("[óò�?õ�?ớ�?ởỡợơốồổỗộô]", "o");
		output = output.replaceAll("[đ]", "d");
		output = output.replaceAll("[ýỳỷỹỵ]", "y");
		output = output.replaceAll("[ĂẮẰẲẴẶÂẤẦẨẪẬ�?ÀẢÃẠ]", "A");
		output = output.replaceAll("[ÚÙỦŨỤƯỨỪỬỮỰ]", "U");
		output = output.replaceAll("[ÉÈẺẼẸÊẾỀỂỄỆ]", "E");
		output = output.replaceAll("[�?ÌỈĨỊ]", "I");
		output = output.replaceAll("[ÓÒỎÕỌƠỚỜỞỠỢÔ�?ỒỔỖỘ]", "O");
		output = output.replaceAll("[�?]", "D");
		output = output.replaceAll("[�?ỲỶỸỴ]", "Y");
		return output;
	}
}
