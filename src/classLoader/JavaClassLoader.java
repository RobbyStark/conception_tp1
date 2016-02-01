/*
 * Class loader. Originally obtained from : http://examples.javacodegeeks.com/core-java/dynamic-class-loading-example/.
 */

package classLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class JavaClassLoader extends ClassLoader {
	
	public String invokeRunMethod(String classBinName, File file) {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
	        Class loadedMyClass = classLoader.loadClass(classBinName);
	        Constructor constructor = loadedMyClass.getConstructor();
	        Object myClassObject = constructor.newInstance();
	        Method method = loadedMyClass.getMethod("run", new Class[] { File.class });
	        return method.invoke(myClassObject, file).toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean invokeVerificationMethod(String classBinName, String methodName) {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
	        Class loadedMyClass = classLoader.loadClass(classBinName);
	        Constructor constructor = loadedMyClass.getConstructor();
	        Object myClassObject = constructor.newInstance();
	        Method method = loadedMyClass.getMethod(methodName);
	        return (boolean) method.invoke(myClassObject);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
