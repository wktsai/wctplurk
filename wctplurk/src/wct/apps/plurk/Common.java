package wct.apps.plurk;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import wct.apps.plurk.Database.DBHelper;
import android.content.Context;
import android.util.Log;

public final class Common {
	public static void setFinalStatic(Field field, Object value)
	{
		field.setAccessible(true);
		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field,  field.getModifiers() & ~Modifier.FINAL);
			
			field.set(null,  value);
		} catch (Exception e) {
			Log.e("Common.setFinalStatic", e.getMessage());
			e.printStackTrace();
		}
	}
}
